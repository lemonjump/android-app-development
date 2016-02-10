package com.project.csc301.shoppinglist.Controllers;

import android.app.DialogFragment;
import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.project.csc301.shoppinglist.Database.DatabaseHelper;
import com.project.csc301.shoppinglist.Helpers.GlobalFunctions;
import com.project.csc301.shoppinglist.Models.ItemModel;
import com.project.csc301.shoppinglist.Controllers.IntraComponentListeners.*;
import com.project.csc301.shoppinglist.Models.ListItem;
import com.project.csc301.shoppinglist.Models.PurchaseHistory;
import com.project.csc301.shoppinglist.Models.Separator;
import com.project.csc301.shoppinglist.Models.ListItemType;
import com.project.csc301.shoppinglist.R;

public class ShoppingListMain
        extends AppCompatActivity
        implements ListItemsListener,
        AddItemDialogListener,
        ConfirmationDialogListener,
        SavePriceDialogListener {

    protected static DatabaseHelper dbh;

    private ListView list;
    private ItemAdapter adapter;
    private DialogFragment add_item_dialog, delete_all_dialog, save_price_dialog;

    private Menu menu;
    private MenuItem additem, delete, delete_all,
            canceldelete, delete_all_checked, show_hide_trip;

    private boolean ON_DELETE_MODE, MENU_ONLY, SHOWING_STORES;

    private List<ListItem> items_to_buy;
    private List<ListItem> items_acquired;
    private Set<String> stores_being_shown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ON_DELETE_MODE = false;
        MENU_ONLY = false;
        SHOWING_STORES = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_main);

        add_item_dialog = new AddNewItemDialog();
        save_price_dialog = new SavePriceDialog();
        createDeleteAllDialog();

        list = (ListView) findViewById(R.id.listView);

        try {
            dbh = new DatabaseHelper(this);
            //dbh.main();
        } catch (Exception e) {
            GlobalFunctions.showInformativeDialog(this, "Error", e.getMessage());
        }

        initializeLists();
        setAdapterForList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_list_actions, menu);
        super.onCreateOptionsMenu(menu);

        this.menu = menu;

        additem = this.menu.findItem(R.id.action_add);
        delete = this.menu.findItem(R.id.action_delete);
        delete_all = this.menu.findItem(R.id.action_delete_all);
        delete_all_checked = this.menu.findItem(R.id.action_clear_all_checked);
        canceldelete = this.menu.findItem(R.id.action_cancel);
        show_hide_trip = this.menu.findItem(R.id.action_best_stores);
        show_hide_trip.setChecked(SHOWING_STORES);

        refreshData();
        return true;
    }

    private void initializeLists() {
        items_to_buy = new ArrayList<>();
        items_acquired = new ArrayList<>();

        try {
            items_to_buy.addAll(dbh.getAllUncheckedProduct());
            items_acquired.addAll(dbh.getAllCheckedProduct());
        } catch (Exception e) {
            GlobalFunctions.showInformativeDialog(this, "Error", e.getMessage());
        }

        addToBuySeparatorToPendingItems();
        addAcquiredSeparatorToAcquiredItems(!items_acquired.isEmpty());
    }

    private void setCheapestStores() {
        try {
            for (ListItem m : items_to_buy) {
                if (m.getViewType() == ListItemType.PRODUCT) {
                    ItemModel item = (ItemModel) m;
                    item.setLastPurchaseHistory(
                            dbh.getMinPriceFor(dbh.getPIDbyName(item.getProductName()), 0));
                }
            }
        } catch (Exception e) {
            GlobalFunctions.showInformativeDialog(this, "Error", e.getMessage());
        }
    }

    private void createDeleteAllDialog() {
        delete_all_dialog = new ConfirmationDialog();
        ((ConfirmationDialog) delete_all_dialog).setArgs(
                R.string.delete_all_items,
                R.string.confirm_delete,
                R.string.clear_list,
                R.string.cancel,
                ConfirmationType.DELETE_ALL);
    }


    /* Data Adapter */
    private void setAdapterForList() {
        adapter = new ItemAdapter(getApplicationContext(),
                this,
                items_to_buy,
                items_acquired);
        list.setAdapter(adapter);
    }

    public ItemAdapter getAdapter() {
        return this.adapter;
    }

    private void addItem(String item, String amount, String unit) {
        ItemModel m = new ItemModel(item, amount, unit, false);
        determineLastPurchaseHistory(m);
        items_to_buy.add(m);
        refreshData();
    }

    private void determineLastPurchaseHistory(ItemModel m) {
        m.setLastPurchaseHistory(null);
    }

    private void removeItem(int position) {

        ItemModel m = (ItemModel) adapter.getActualListItem(position);
        if (dbh.deleteProductShoppingList(dbh.getPIDbyName(m.getProductName()))) {
            removeFromActualPos(position);
            clearPendingItemsSeparatorsIfEmpty();
            if (!adapter.hasValidElements())
                ON_DELETE_MODE = false;
            refreshData();
        }
    }

    private void removeAll() {
        if (dbh.clearShoppingList() > 0) {
            ON_DELETE_MODE = false;
            //Clear all lists.
            items_to_buy.clear();
            clearChecked(null);
        } else {
            GlobalFunctions.showInformativeDialog(this, "Error", "Error clearing list.");
        }
    }

    private void removeFromActualPos(int position) {
        int num_pending_items = items_to_buy.size();
        if (position < num_pending_items)
            items_to_buy.remove(position);
        else {
            items_acquired.remove(position - num_pending_items);
        }
    }

    private void swapItemBetweenLists(List<ListItem> from,
                                      List<ListItem> to,
                                      int pos) {
        ListItem m = from.get(pos);
        to.add(m);
        from.remove(m);
    }

    /* Menu Functionalities*/
    public void refreshData() {
        if (!MENU_ONLY) {
            adapter.notifyDataSetChanged();
        } else {
            MENU_ONLY = false;
        }
        showStoresProcess(SHOWING_STORES);
        if(SHOWING_STORES)
            removeEmptyStoreSeparators(items_to_buy);

        toggleDeleteAvailability();
        toggleDeletionMenu();
    }

    public void showMenuItem(MenuItem m, boolean show) {
        m.setVisible(show);
        invalidateOptionsMenu();
    }

    public void showNewItem(MenuItem m) {
        add_item_dialog.show(this.getFragmentManager(), "Add item");
    }

    public void showDelete(MenuItem m) {
        ON_DELETE_MODE = true;
        MENU_ONLY = true;
        refreshData();
    }

    public void toggleDeleteAvailability() {
        delete.setEnabled(adapter.hasValidElements());
        if(delete.isEnabled())
            delete.setIcon(R.drawable.minus_100);
        else
            delete.setIcon(R.drawable.minus_1002);
        delete_all_checked.setEnabled(!items_acquired.isEmpty());
    }

    public void cancelDelete(MenuItem m) {
        ON_DELETE_MODE = false;
        MENU_ONLY = true;
        refreshData();
    }

    public void toggleDeletionMenu() {
        showMenuItem(delete, !ON_DELETE_MODE);
        showMenuItem(additem, !ON_DELETE_MODE);
        showMenuItem(delete_all, ON_DELETE_MODE);
        showMenuItem(canceldelete, ON_DELETE_MODE);
        showMenuItem(delete_all_checked, false);
    }

    public void deleteAll(MenuItem m) {
        delete_all_dialog.show(this.getFragmentManager(), "Delete item");
    }

    public void clearChecked(MenuItem m) {
        items_acquired.clear();
        if (adapter.getCount() == 0)
            ON_DELETE_MODE = false;
        refreshData();
    }

    public void clearPendingItemsSeparatorsIfEmpty() {
        if (items_acquired.size() == 1)
            items_acquired.clear();
    }

    public boolean isOnDeleteMode() {
        return this.ON_DELETE_MODE;
    }

    public void showHideStores(MenuItem m) {
        m.setChecked(!m.isChecked());
        SHOWING_STORES = !SHOWING_STORES;
        refreshData();
    }

    public void showStoresProcess(boolean add_sepparators) {
        removeAllSeparators(items_to_buy);
        if (add_sepparators) {
            setCheapestStores();
            GlobalFunctions.model_sort(items_to_buy);
            addAllStoreSeparatorsToPendingItems();
        }
        addToBuySeparatorToPendingItems();
    }

    public void addAllStoreSeparatorsToPendingItems() {
        if (stores_being_shown == null) {
            stores_being_shown = new HashSet<>();
        }
        stores_being_shown.clear();
        int i = 0;
        while (i < items_to_buy.size()) {
            ItemModel curr = (ItemModel) items_to_buy.get(i);
            String store;
            try {
                store = curr.getLastPurchaseHistory().getStore();
            } catch (Exception e) {
                store = "";
            }

            if (!stores_being_shown.contains(store)) {
                stores_being_shown.add(store);
                if (store.equals(""))
                    store = "ITEMS WITHOUT ANY DATA SAVED";
                else
                    store = "CHEAPER AT " + store;
                Separator s = new Separator(-1, -1, ListItemType.SEPARATOR_STORE);
                s.setActualTitle(store.toUpperCase());
                items_to_buy.add(i, s);
                i++;
            }
            i++;
        }
    }

    public void removeAllSeparators(List<ListItem> l) {
        int i = 0;
        while (i < l.size()) {
            if (l.get(i).getViewType() != ListItemType.PRODUCT)
                l.remove(i);
            else
                i++;
        }
    }

    public void removeEmptyStoreSeparators(List<ListItem> l) {
        int i = 0;
        while (i < l.size()) {
            if (l.get(i).getViewType() == ListItemType.SEPARATOR_STORE
                    && (
                    i == l.size() - 1 || l.get(i + 1).getViewType() == ListItemType.SEPARATOR_STORE)
                    )
                l.remove(i);
            else
                i++;
        }
    }

    /*Listen to signals from other components.*/
    @Override
    public void onAddDialogPositiveClick(String product, String amount, String unit) {

        long result;
        product = GlobalFunctions.transform(product);
        if (amount == null) {
            result = dbh.addProductShoppingList(product);
        } else {
            result = dbh.addProductShoppingList(product, 0, Double.parseDouble(amount), unit);
        }
        if (result >= 0) {
            addItem(product, amount, unit);
        } else {
            GlobalFunctions.showInformativeDialog(this, "Error", "Couldn't add item.");
        }
        add_item_dialog.getDialog().dismiss();

    }

    @Override
    public void onDeleteItem(int pos) {
        removeItem(pos);
    }

    @Override
    public void onConfirmation(ConfirmationType type) {
        switch (type) {
            case DELETE_ALL:
                delete_all_dialog.getDialog().dismiss();
                removeAll();
                addToBuySeparatorToPendingItems();
                break;
        }
    }

    private int last_relevant_pos;

    @Override
    public void OnCheckAction(int pos, boolean iscurrentychecked) {
        last_relevant_pos = pos;
        ItemModel mod = (ItemModel) adapter.getActualListItem(pos);
        int result = dbh.updateCheck(dbh.getPIDbyName(mod.getProductName()), !iscurrentychecked);
        if (result == 1) {
            if (!iscurrentychecked) {
                ((ItemModel) adapter.getActualListItem(pos)).setLastPurchaseHistory(null);
                save_price_dialog.show(this.getFragmentManager(), "Save price");
                swapItemBetweenLists(items_to_buy, items_acquired, pos);
                adapter.toggleLastItem(adapter.getCount() - 1);
                addAcquiredSeparatorToAcquiredItems(items_acquired.size() == 1);
                refreshData();
            } else {
                unCheckItem();
            }
        } else {
            GlobalFunctions.showInformativeDialog(this, "Error", "Error updating status.");
        }
    }

    private void addAcquiredSeparatorToAcquiredItems(boolean condition) {
        if (condition) {
            Separator m = new Separator(R.string.separator_acquired,
                    -1, ListItemType.SEPARATOR_ITEMS_ACQUIRED);
            items_acquired.add(0, m);
        }
    }

    private void unCheckItem() {
        swapItemBetweenLists(items_acquired, items_to_buy,
                last_relevant_pos - items_to_buy.size());
        if (items_acquired.size() == 1)
            items_acquired.clear();
        adapter.toggleLastItem(items_to_buy.size() - 1);

        refreshData();
    }

    private void addToBuySeparatorToPendingItems() {
        items_to_buy.add(0, new Separator(R.string.separator_pending,
                R.string.nothinghere,
                ListItemType.SEPARATOR_ITEMS_PENDING));
    }

    @Override
    public void onSavePricePositiveClick(String store, String price, String unit_type) {
        save_price_dialog.getDialog().dismiss();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = df.format(c.getTime());

        store = GlobalFunctions.transformUpper(store);

        ItemModel model = ((ItemModel) items_acquired.get(items_acquired.size() - 1));

        int SID = dbh.addNewStore(store);
        if (SID == -1)
            SID = dbh.getSIDbyName(store);

        int insertStatus = dbh.addPurchase(dbh.getPIDbyName(model.getProductName()),
                SID,
                Double.parseDouble(price),
                unit_type,
                0,
                time);

        if (insertStatus >= 0) {
            PurchaseHistory ph = new PurchaseHistory(
                    price,
                    unit_type,
                    store,
                    time,
                    insertStatus);
            model.setLastPurchaseHistory(ph);
            refreshData();
        } else {
            GlobalFunctions.showInformativeDialog(this, "Error", "Error saving purchase details. Result: " + insertStatus);
        }
    }

    @Override
    public void onLongClick(ItemModel m) {
        if (!ON_DELETE_MODE) {
            Intent intent = new Intent(this, ProductInfo.class);
            intent.putExtra("product_string", m.getProductName());
            startActivity(intent);
        }
    }

    @Override
    public List<String> getListOfAllProducts() {
        return dbh.getAllProductNames();
    }

    @Override
    public List<String> getListOfAllStores() {
        return dbh.getAllStoreNames();
    }

}
