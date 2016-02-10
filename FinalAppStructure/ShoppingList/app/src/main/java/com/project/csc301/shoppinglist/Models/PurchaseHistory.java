package com.project.csc301.shoppinglist.Models;

public class PurchaseHistory {
    private String price;
    private String unit_type;
    private String store;
    private String time;
    private long _id;

    public  PurchaseHistory(String price, String unit_type, String store, String time, long _id){
        this.unit_type = unit_type;
        this.price = price;
        this.store = store;
        this.time = time;
        this._id = _id;
    }

    public  PurchaseHistory(double price, String unit_type, String store, String time, long _id){
        this.unit_type = unit_type;
        this.price =   String.format("%.2f", price);
        this.store = store;
        this.time = time;
        this._id = _id;
    }

    public void setName(String name){this.store=name;}
    public String getPrice(){
        return this.price;
    }
    public String getUnit_type() {return this.unit_type; }
    public String getStore(){
        return this.store;
    }
    public String getTime(){
        return this.time;
    }
    public long getID(){
        return this._id;
    }
    public String toString(){
        return String.format("Acquired at %s for $%s/%s on %s.", store, price, unit_type, time);
    }

}
