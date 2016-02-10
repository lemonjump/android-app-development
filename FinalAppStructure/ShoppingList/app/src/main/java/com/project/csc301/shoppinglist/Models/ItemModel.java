package com.project.csc301.shoppinglist.Models;

import android.util.Log;

import java.io.Serializable;
import com.project.csc301.shoppinglist.Controllers.*;

public class ItemModel implements ListItem, Serializable{
    private String product, amount, unit;
    private boolean acquired;
    private PurchaseHistory ph;

    public ItemModel(String product, String amount, String unit, boolean acquired) {
        this(product, amount, unit, null, acquired);
    }

    public ItemModel(String product,
                     String amount,
                     String unit,
                     PurchaseHistory last_purchase,
                     boolean acquired) {
        this.product = product;
        this.amount = amount;
        this.ph = last_purchase;
        this.unit = unit;
        this.acquired = acquired;

        try{
            int am = (int) (Double.parseDouble(amount));
            if (am == 0){
                throw new Exception();
            }
            this.amount = Integer.toString(am);
        }catch (Exception e){
            this.amount = null;
            this.unit = null;
        }
    }

    public void toggle() {
        this.acquired = !acquired;
    }

    public void setProduct(String product){this.product=product;}
    public boolean isAcquired() {
        return acquired;
    }

    public boolean isAmountAvailable() {
        return this.amount != null;
    }

    public void setLastPurchaseHistory(PurchaseHistory ph) {
        this.ph = ph;
    }

    public PurchaseHistory getLastPurchaseHistory() {
        return this.ph;
    }

    public String getProductName() {
        return this.product;
    }

    public String getAmount() {
        return this.amount;
    }

    public String getUnit() {
        return this.unit;
    }

    @Override
    public String toString() {
        return "product:" + this.product + ", quantity:" + this.amount + ", unit:" + this.unit + ", checked:" + this.acquired;
    }
    @Override
    public ListItemType getViewType() {
        return ListItemType.PRODUCT;
    }

}
