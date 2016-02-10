package com.project.csc301.shoppinglist.Controllers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.project.csc301.shoppinglist.Database.DatabaseHelper;
import com.project.csc301.shoppinglist.Helpers.GlobalFunctions;
import com.project.csc301.shoppinglist.Models.PurchaseHistory;
import com.project.csc301.shoppinglist.R;

public class ProductInfo extends AppCompatActivity
        implements IntraComponentListeners.DeleteListener {

    private String product_string = "";
    ListView info;

    Menu menu;
    TextView averageprice, estimatedavg, savings;
    double theaverageprice = 0;

    PurchaseItemAdapter ph;
    List<PurchaseHistory> purchaseHistories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        Intent intent = getIntent();

        purchaseHistories = new ArrayList<>();
        ph = new PurchaseItemAdapter(this, purchaseHistories, this);
        product_string = intent.getStringExtra("product_string");
        this.setTitle(product_string);
        TextView productText = (TextView) findViewById(R.id.product_name);

        estimatedavg = (TextView) findViewById(R.id.estimated_avg);
        averageprice = (TextView) findViewById(R.id.details_average);
        savings = (TextView) findViewById(R.id.details_comment);

        productText.setText("Product information");

        info = (ListView) findViewById(R.id.info);
        info.setAdapter(ph);

        updateScreen();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.purchasedetailsmenu, menu);
        super.onCreateOptionsMenu(menu);
        this.menu = menu;

        return true;
    }

    public void clearHistory(MenuItem m) {

        if (purchaseHistories.size() == 1 && purchaseHistories.get(0).getPrice() == null) {
            purchaseHistories.clear();
        }

        while (purchaseHistories.size() != 0) {
            if (ShoppingListMain.dbh.deletePreviousPurchase((int) purchaseHistories.get(0).getID())) {
                purchaseHistories.remove(0);
            } else {
                break;
            }
        }

        updateScreen();
    }

    private void updateScreen() {
        purchaseHistories.clear();
        ph.notifyDataSetChanged();

        List<PurchaseHistory> all_purchases = ShoppingListMain.dbh.getAllPurchasesFor
                (ShoppingListMain.dbh.getPIDbyName(product_string));

        for (PurchaseHistory ph : all_purchases)
            purchaseHistories.add(ph);

        Collections.sort(purchaseHistories, new PurchaseHistoryComparator());

        if (purchaseHistories.isEmpty())
            purchaseHistories.add(new PurchaseHistory(null, null, "No previous purchases recorded :(", null, -1));

        ph.notifyDataSetChanged();

        if (purchaseHistories.size() > 0) {
            if (purchaseHistories.get(0).getPrice() != null) {
                averageprice.setText(getAveragePrice());
                savings.setText(getComment());
            } else {
                averageprice.setVisibility(View.GONE);
                savings.setVisibility(View.GONE);
                estimatedavg.setVisibility(View.GONE);
            }
        }

    }

    private String getAveragePrice() {
        String average = "";
        String price = "";
        try {
            double total = 0;
            for (PurchaseHistory ph : purchaseHistories) {
                total += Double.parseDouble(ph.getPrice());
            }
            price = String.format("%.2f", total / purchaseHistories.size());
            theaverageprice = total / purchaseHistories.size();

        } catch (Exception e) {
            price = "XX.XX";
        }
        return String.format(" $%s", price);
    }

    private String getComment() {
        String comment = "There is not enough data to calculate savings.";
        try {
            if (purchaseHistories.size() > 1) {
                double price_min = Double.parseDouble(
                        purchaseHistories.get(0).getPrice());
                double price_max = Double.parseDouble(
                        purchaseHistories.get(purchaseHistories.size() - 1).getPrice());

                double saved = price_max - price_min;
                double perc_savings = (saved / price_min) * 100;

                double saved_avg = theaverageprice - price_min;
                double perc_savings_avg = (saved_avg / price_min) * 100;

                comment = String.format("%s saves you up to:\n• $%.2f (%.0f%%) versus %s.\n• $%.2f (%.0f%%) versus the average price.",
                        purchaseHistories.get(0).getStore().toUpperCase(),
                        saved,
                        perc_savings,
                        "the most expensive store",
                        saved_avg,
                        perc_savings_avg);
            }
        } catch (Exception e) {
            Log.i("String", e.getMessage());
        }
        return comment;
    }

    @Override
    public void onDeleteItem(int pos) {
        if (ShoppingListMain.dbh.deletePreviousPurchase((int) purchaseHistories.get(pos).getID()))
            updateScreen();
        else
            GlobalFunctions.showInformativeDialog(this, "Error", "Couldn't delete element.");
    }
}

class PurchaseHistoryComparator implements Comparator<PurchaseHistory> {
    public int compare(PurchaseHistory p1, PurchaseHistory p2) {
        try {
            if (Double.parseDouble(p1.getPrice()) == Double.parseDouble(p2.getPrice()))
                return 0;
            return Double.parseDouble(p1.getPrice()) > Double.parseDouble(p2.getPrice()) ? 1 : -1;
        } catch (Exception e) {
            return 0;
        }
    }
}