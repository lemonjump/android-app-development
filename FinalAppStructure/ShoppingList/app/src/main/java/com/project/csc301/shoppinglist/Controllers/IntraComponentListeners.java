package com.project.csc301.shoppinglist.Controllers;

import com.project.csc301.shoppinglist.Models.ItemModel;

import java.util.List;

public class IntraComponentListeners {

    public enum ConfirmationType{
        DELETE_ALL,
    }

    public interface DeleteListener{
        void onDeleteItem(int pos);
    }

    public interface ListItemsListener {
        void onDeleteItem(int pos);
        void OnCheckAction(int pos, boolean checked);
        void onLongClick(ItemModel m);
    }

    public interface AddItemDialogListener {
        void onAddDialogPositiveClick(String product, String amount, String unit);
        List<String> getListOfAllProducts();
    }

    public interface SavePriceDialogListener {
        void onSavePricePositiveClick(String store, String price, String unit_type);
        List<String> getListOfAllStores();
    }

    public interface ConfirmationDialogListener{
        void onConfirmation(ConfirmationType type);
    }

}
