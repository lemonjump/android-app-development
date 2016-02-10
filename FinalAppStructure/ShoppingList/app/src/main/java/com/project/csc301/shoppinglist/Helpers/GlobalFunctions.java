package com.project.csc301.shoppinglist.Helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.project.csc301.shoppinglist.Models.ItemModel;
import com.project.csc301.shoppinglist.Models.ListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GlobalFunctions {

    //A basic set of units.
    public static String[] arraySpinnerS = new String[]{
            "unit", "liter", "g"
    };

    //A basic set of units.
    public static String[] arraySpinnerP = new String[]{
            "units", "liters", "g",
    };

    public static void showInformativeDialog(Context c, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(c).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Continue",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static String transform(String s) {
        s = s.toLowerCase();
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static String transformUpper(String s) {
        return s.toUpperCase();
    }

    public static void model_sort(List<ListItem> products) {
        List<ListItem> final_list = new ArrayList<>();
        HashMap<String, List<ListItem>> final_lists = new HashMap<>();

        for(ListItem item : products){
            String store;
            try {
                store = ((ItemModel) item).getLastPurchaseHistory().getStore();
            }
            catch (Exception e){
                store = null;
            }

            if(!final_lists.containsKey(store)){
                final_lists.put(store, new ArrayList<ListItem>());
            }
            final_lists.get(store).add(item);
        }
        List<ListItem> null_stores = final_lists.get(null);

        final_lists.remove(null);

        while(!final_lists.isEmpty()){
            int max_size = 0;
            String max_key = null;
            for(String key : final_lists.keySet()){
                if(final_lists.get(key).size() > max_size){
                    max_size = final_lists.get(key).size();
                    max_key = key;
                }
            }
            final_list.addAll(final_lists.get(max_key));
            final_lists.remove(max_key);
        }
        if(null_stores != null)
            final_list.addAll(null_stores);
        products.clear();
        products.addAll(final_list);
    }
}
