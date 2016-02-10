package com.project.csc301.shoppinglist.Database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.project.csc301.shoppinglist.Models.*;
import com.project.csc301.shoppinglist.Controllers.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.util.Log;

import com.project.csc301.shoppinglist.Controllers.ShoppingListMain;

/**This class is responsible for all database related back end functions.
 * Main app will create an instance of this, the constructor takes the app context (this).
 * After it is constructed, GUI can call functions in here to act on database and return or store information to and from it.
 * Created by memon on 18/11/15.
 */
public class DatabaseHelper extends Activity {
    Context context;
    DatabaseHandler DBHelper;

    public DatabaseHelper(Context context) {
        this.context = context;
        this.DBHelper = new DatabaseHandler(context);
    }

    public void main(){

        Log.i(DatabaseHelper.class.getName(), "Main method for testing");

        //test adding new products, this function is never actually called directly, only from addProductShoppingList;
        addProductShoppingList("Pistachios");
        addProductShoppingList("Dates");
        addProductShoppingList("Watermelon");
        addProductShoppingList("Peanuts");
        addProductShoppingList("Almonds");
        addProductShoppingList("Apples");
        addProductShoppingList("Steak");
        addProductShoppingList("Chicken");
        addProductShoppingList("Juice");
        addProductShoppingList("Fish");
        addProductShoppingList("Brocolli");
        addProductShoppingList("Snow Peas");
        addProductShoppingList("Hershey's");
        addProductShoppingList("Dorritos");
        addProductShoppingList("Eggs");


        addNewStore("Longos");
        addNewStore("Bulk Barn");
        addNewStore("Walmart");
        addNewStore("No Frills");
        addNewStore("Metro");
        addNewStore("Smart Store");
        addNewStore("Loblaws");



        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = df.format(c.getTime());

        int _id = addPurchase(getPIDbyName("Peanuts"), getSIDbyName("Bulk Barn"), 1.4, "g" , 0, time);
        _id = addPurchase(getPIDbyName("Brocolli"), getSIDbyName("Walmart"), 2.4, "g" , 0, time);
        _id = addPurchase(getPIDbyName("Peanuts"), getSIDbyName("Longos"), 5.8,  "g" , 0, time);
        _id = addPurchase(getPIDbyName("Hershey's"), getSIDbyName("Smart Store"), 1.85, "g" , 0, time);
        _id = addPurchase(getPIDbyName("Dates"), getSIDbyName("Metro"), 3.7, "g" , 0, time);
        _id = addPurchase(getPIDbyName("Hershey's"), getSIDbyName("Metro"), 1.5, "g" , 0, time);
        _id = addPurchase(getPIDbyName("Apples"), getSIDbyName("Metro"), 2.86, "g" , 0, time);
        _id = addPurchase(getPIDbyName("Brocolli"), getSIDbyName("Metro"), 3.2, "g" , 0, time);
        _id = addPurchase(getPIDbyName("Chicken"), getSIDbyName("No Frills"), 2.97, "g" , 0, time);
        _id = addPurchase(getPIDbyName("Apples"), getSIDbyName("Loblaws"), 3.54, "g" , 0, time);
        _id = addPurchase(getPIDbyName("Brocolli"), getSIDbyName("Metro"), 2.5, "g" , 0, time);
        _id = addPurchase(getPIDbyName("Eggs"), getSIDbyName("Metro"), 3.7, "unit" , 0, time);
        _id = addPurchase(getPIDbyName("Peanuts"), getSIDbyName("Metro"), 2.47, "unit" , 0, time);
        _id = addPurchase(getPIDbyName("Dates"), getSIDbyName("Loblaws"), 5.57, "unit" , 0, time);
        _id = addPurchase(getPIDbyName("Brocolli"), getSIDbyName("Metro"), 1.7, "unit" , 0, time);
        _id = addPurchase(getPIDbyName("Hershey's"), getSIDbyName("Walmart"), 7.7, "unit" , 0, time);
        _id = addPurchase(getPIDbyName("Eggs"), getSIDbyName("No Frills"), 4.8, "unit" , 0, time);
        _id = addPurchase(getPIDbyName("Dates"), getSIDbyName("Metro"), 2.7, "unit" , 0, time);
        _id = addPurchase(getPIDbyName("Eggs"), getSIDbyName("Smart Store"), 2.87, "unit" , 0, time);
        _id = addPurchase(getPIDbyName("Juice"), getSIDbyName("Metro"), 6.7, "l" , 0, time);
        _id = addPurchase(getPIDbyName("Steak"), getSIDbyName("Metro"), 6.37, "unit" , 0, time);
        _id = addPurchase(getPIDbyName("Juice"), getSIDbyName("Longos"), 3.27, "unit" , 0, time);
        _id = addPurchase(getPIDbyName("Brocolli"), getSIDbyName("No Frills"), 7.87, "unit" , 0, time);
        _id = addPurchase(getPIDbyName("Steak"), getSIDbyName("Smart Store"), 4.10, "unit" , 0, time);
        _id = addPurchase(getPIDbyName("Hershey's"), getSIDbyName("Longos"), 3.87, "unit" , 0, time);
        _id = addPurchase(getPIDbyName("Brocolli"), getSIDbyName("Walmart"), 3.45, "unit" , 0, time);



        //test addProductShoppingList();
        addProductShoppingList("Pistachios");
        addProductShoppingList("Dates");
        addProductShoppingList("Candy", 3.5, "units");

        //test updateCheck();
        updateCheck(1, Boolean.TRUE);
        updateCheck(1, Boolean.FALSE);
        updateCheck(2, Boolean.TRUE);
        updateCheck(7, Boolean.TRUE);


        //test getAllProducts();
        getAllUncheckedProduct();
        getAllCheckedProduct();

        LogProductsTable();
        LogShoppingListTable();


        //test getPIDbyName()
        int pid = getPIDbyName("Pistachios");
        Log.d(DatabaseHelper.class.getName(), "getPIDbyName(\"Pistachios\"):" + String.valueOf(pid));
        pid = getPIDbyName("Candy!");
        Log.d(DatabaseHelper.class.getName(), "getPIDbyName(\"Candy!\"):" + String.valueOf(pid));
        pid = getPIDbyName("Watermelon");
        Log.d(DatabaseHelper.class.getName(), "getPIDbyName(\"Watermelon\"):" + String.valueOf(pid));
        pid = getPIDbyName("DNE");
        Log.d(DatabaseHelper.class.getName(), "getPIDbyName(\"DNE\"):" + String.valueOf(pid));

        //test deleteProductShoppingList()
        Boolean del = deleteProductShoppingList(1);
        Log.d(DatabaseHelper.class.getName(), "deleteProductShoppingList(1):" + String.valueOf(del));
        LogShoppingListTable();

        del = deleteProductShoppingList(4);
        Log.d(DatabaseHelper.class.getName(), "deleteProductShoppingList(4):" + String.valueOf(del));
        del = deleteProductShoppingList(6);
        Log.d(DatabaseHelper.class.getName(), "deleteProductShoppingList(6):" + String.valueOf(del));
        LogShoppingListTable();

        //test clearShoppingList
        //clearShoppingList();
        LogShoppingListTable();
        LogProductsTable();

        //test addNewStore();
        addNewStore("Longos");
        addNewStore("Bulk Barn");
        LogStoresTable();

        //Test getSIDbyName()
        int sid = getSIDbyName("Bulk Barn");
        Log.d(DatabaseHelper.class.getName(), "getSIDbyName(\"Bulk Barn\"):" + String.valueOf(sid));
        sid = getSIDbyName("DNE");
        Log.d(DatabaseHelper.class.getName(), "getSIDbyName(\"DNE\"):" + String.valueOf(sid));


        //Test addPurchase()
        _id = addPurchase(getPIDbyName("Pistachios"), getSIDbyName("Bulk Barn"), 3.4, "units" , 0, time);
        LogPurchaseHistoryTable();
        _id = addPurchase(getPIDbyName("Watermelon"), getSIDbyName("Longos"), 3.4, "litres" , 0, time);
        LogPurchaseHistoryTable();

        //test getMinPriceFor();
        addPurchase(getPIDbyName("Pistachios"), getSIDbyName("Bulk Barn"), 3.4, "units", 0, time);
        addPurchase(getPIDbyName("Watermelon"), getSIDbyName("Longos"), 1.4, "numbers", 0, time);

        PurchaseHistory ph = getMinPriceFor(getPIDbyName("Pistachios"));
        Log.d(DatabaseHelper.class.getName(), "getMinPriceFor Pistachios:" + ph.toString());
        ph = getMinPriceFor(getPIDbyName("Watermelon"));
        Log.d(DatabaseHelper.class.getName(), "getMinPriceFor Watermelon:" + ph.toString());

        //test getNameby_ID(_ID)
        String name = getNameBySID(getSIDbyName("Bulk Barn"));
        Log.d(DatabaseHelper.class.getName(), "getNamebySID getSIDbyName(\"Bulk Barn\"):" + name);
        name = getNameByPID(getPIDbyName("Pistachios"));
        Log.d(DatabaseHelper.class.getName(), "getNamebyPID getSIDbyName(\"Pistachios\"):" + name);


        //test getAllPurchasesFor(PID)
        getAllPurchasesFor(getPIDbyName("Watermelon"));

        //test deleteHistoryBefore
        LogPurchaseHistoryTable();
        deleteHistoryOlderThan("2015-12-05 10:24:03", 1);
        deleteHistoryOlderThan("2015-12-05 10:24:03",-1);

    }

    //Testing function to print to Log contents of Products table.
    private void LogProductsTable() {
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_PRODUCTS, null);
        Log.d(DatabaseHelper.class.getName(), "Contents of Products Table:");

        c.moveToFirst();
        while (!c.isAfterLast()) {
            Log.d(DatabaseHelper.class.getName(), "\t _id:" + String.valueOf(c.getInt(c.getColumnIndex(DBHelper.PRODUCTS_ID))) + ", pname:" + String.valueOf(c.getString(c.getColumnIndex(DBHelper.PRODUCTS_PNAME))));
            c.moveToNext();
        }
        db.close();
    }

    //Testing function to print to Log contents of ShoppingList table.
    private void LogShoppingListTable(){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_SHOPPINGLIST, null);
        Log.d(DatabaseHelper.class.getName(), "Contents of ShoppingList Table:");

        c.moveToFirst();
        while (!c.isAfterLast()) {
            Log.d(DatabaseHelper.class.getName(), "\t _id:" + String.valueOf(c.getInt(c.getColumnIndex(DBHelper.SHOPPINGLIST_ID))) +
                    ", pid:" + String.valueOf(c.getInt(c.getColumnIndex(DBHelper.SHOPPINGLIST_PID))) +
                    ", checkmark:" + String.valueOf(c.getInt(c.getColumnIndex(DBHelper.SHOPPINGLIST_CHECKMARK))) +
                    ", quantity:" + String.valueOf(c.getDouble(c.getColumnIndex(DBHelper.SHOPPINGLIST_QUANTITY))) +
                    ", unit:" + String.valueOf(c.getString(c.getColumnIndex(DBHelper.SHOPPINGLIST_UNIT))) +
                    ", quality:" + String.valueOf(c.getInt(c.getColumnIndex(DBHelper.SHOPPINGLIST_QUALITY))) );
            c.moveToNext();
        }

        db.close();

    }

    /**
     * Given a product, add that product to the Products table,
     * (ie user adds item to shopping list that has not existed in DB before),
     * return PID of the newly added product.
     * This function will mainly be used internally by AddProductShoppingList(name) when a user
     * adds a new item to the shopping list
     * Return newly generated PID of new product, or -1 if Product already exists in table.
     */

    //Testing function to print to Log contents of Stores table.
    private void LogStoresTable() {
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_STORES, null);
        Log.d(DatabaseHelper.class.getName(), "Contents of Stores Table:");

        c.moveToFirst();
        while (!c.isAfterLast()) {
            Log.d(DatabaseHelper.class.getName(), "\t _id:" + String.valueOf(c.getInt(c.getColumnIndex(DBHelper.STORES_ID))) + ", sname:" + String.valueOf(c.getString(c.getColumnIndex(DBHelper.STORES_SNAME))) + ", location:" + String.valueOf(c.getString(c.getColumnIndex(DBHelper.STORES_LOCATION))));
            c.moveToNext();
        }
        db.close();
    }

    //Testing function to print to Log contents of PurchaseHistory table.
    private void LogPurchaseHistoryTable() {
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_PURCHASEHISTORY, null);
        Log.d(DatabaseHelper.class.getName(), "Contents of PurchaseHistory Table:");

        c.moveToFirst();
        while (!c.isAfterLast()) {
            Log.d(DatabaseHelper.class.getName(), "\t _id:" + String.valueOf(c.getInt(c.getColumnIndex(DBHelper.PURCHASEHISTORY_ID))) +
                    ", pid:" + String.valueOf(c.getInt(c.getColumnIndex(DBHelper.PURCHASEHISTORY_PID))) +
                    ", sid:" + String.valueOf(c.getInt(c.getColumnIndex(DBHelper.PURCHASEHISTORY_SID))) +
                    ", price:" + String.valueOf(c.getDouble(c.getColumnIndex(DBHelper.PURCHASEHISTORY_PRICE))) +
                    ", quality:" + String.valueOf(c.getInt(c.getColumnIndex(DBHelper.PURCHASEHISTORY_QUALITY))) +
                    ", timestamp:" + String.valueOf(c.getString(c.getColumnIndex(DBHelper.PURCHASEHISTORY_DATE))) );
            c.moveToNext();
        }

        db.close();
    }


    private long addNewProduct(String name) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.PRODUCTS_PNAME, name);

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        long pid = db.insert(DBHelper.TABLE_PRODUCTS, null, cv);
        db.close();

        if (pid>0) {
            Log.i(DatabaseHelper.class.getName(), "AddNewProduct: name '" + name + "' and autoincrement _id='" + pid + "' into database.");
        } else {
            Log.i(DatabaseHelper.class.getName(), "AddNewProduct: name '" + name + "' But already in database.");
        }
        return pid;
    }

    /**
     * Given name of a product, add product to shopping list. Returns PID of that product
     * As a user, I want to add a new item to purchase to my shopping list.
     * Returns PID of inserted product or -1 is product is already in the ShoppingList.
     *
     * Given name of a product, add product to shopping list. Returns PID of that product
     * @param name of the product
     * @param quality of the product
     * @param quantity amount of the product
     * @param unit of the quantity
     * @return the PID generated for the product, -1 if already in ShoppingList table.
     */
    public long addProductShoppingList(String name, int quality, double quantity, String unit) {
        long pid = addNewProduct(name);
        Cursor c = null;

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        if (pid == -1) {

            String[] args = new String[1];
            args[0] = name;
            c = db.rawQuery("SELECT * FROM Products WHERE pname=?", args);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                pid=c.getInt(c.getColumnIndex("_id"));
                c.moveToNext();
            }
        }
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.SHOPPINGLIST_PID, (int) pid);
        cv.put(DBHelper.SHOPPINGLIST_QUALITY, quality);
        cv.put(DBHelper.SHOPPINGLIST_QUANTITY, quantity);
        cv.put(DBHelper.SHOPPINGLIST_UNIT, unit);

        long _id = db.insert(DBHelper.TABLE_SHOPPINGLIST, null, cv);

        if (_id > 0) {
            Log.d(DatabaseHelper.class.getName(), "AddProductShoppingList: _id:" + String.valueOf(_id) + ", pid:" + String.valueOf(pid) + ", pname:" + name);
        } else {
            Log.d(DatabaseHelper.class.getName(), "AddProductShoppingList: pname:'" + name + "', But already in ShoppingList table.");
        }
        db.close();
        return _id;
    }

    //as above, if no quality,or quantity and unit given, use default quality of 0.
    public long addProductShoppingList(String name){
        return addProductShoppingList(name, 0, 0, "");

    }

    //as above, if no quality given, use default quality of 0.
    public long addProductShoppingList(String name, double quantity, String unit){
        return addProductShoppingList(name, 0, quantity, unit);

    }

    /* Clear the ShoppingList table
     * delete everything from the table
     * @return number of deleted rows.
     */
    public int clearShoppingList() {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        int deletes = db.delete(DBHelper.TABLE_SHOPPINGLIST, null, null);
        db.close();
        Log.d(DatabaseHelper.class.getName(), "clearShoppingList(): Deleted all entries in ShoppingList Table");
        return deletes;
    }

    /**
     * Returns PID of product 'pname' from Products table or NULL if product is not in the table.
     * Used by AddProductShoppingList to check if product exists beofre calling AddNewProduct.
     * @param pname pname of the product as string
     * @return PID of product or -1 if product not in table.
     */
    public int getPIDbyName(String pname) {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String query = "SELECT "+DBHelper.PRODUCTS_ID+" FROM "+DBHelper.TABLE_PRODUCTS+" WHERE "+ DBHelper.PRODUCTS_PNAME +" = ? ";
        String[] arg = new String[]{pname};
        Cursor cursor =db.rawQuery(query,arg);
        if(cursor.moveToNext()) {
            int pid = cursor.getInt(cursor.getColumnIndex(DBHelper.PRODUCTS_ID));
            db.close();
            return pid;
        }
        else{
            return -1;
        }

    }

    /**
     * Returns SID of store 'sname' from Stores table or NULL if product is not in the table.
     * @param sname name of the product as string
     * @return SID of store
     */
    public int getSIDbyName(String sname) {
        String query = "SELECT "+DBHelper.STORES_ID+" FROM "+DBHelper.TABLE_STORES+" WHERE "+DBHelper.STORES_SNAME+" =?";
        String[] arg = new String[]{sname};

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, arg);

        if(cursor.moveToNext()) {
            int sid = cursor.getInt(cursor.getColumnIndex(DBHelper.STORES_ID));
            db.close();
            return sid;
        }
        else{
            return -1;
        }
    }

    /**
     * Returns name of a store from Stores table or NULL if product is not in the table.
     * @param sid of the store as string
     * @return name of store
     */
    public String getNameBySID(int sid) {
        String query = "SELECT "+DBHelper.STORES_SNAME+" FROM "+DBHelper.TABLE_STORES+" WHERE "+DBHelper.STORES_ID+" =?";
        String[] arg = new String[]{String.valueOf(sid)};

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, arg);

        if(cursor.moveToNext()) {
            String sname = cursor.getString(cursor.getColumnIndex(DBHelper.STORES_SNAME));
            db.close();
            return sname;
        }
        else{
            return null;
        }
    }

    /**
     * Returns name of a product from Product table or NULL if product is not in the table.
     * @param pid of the product as string
     * @return name of product
     */
    public String getNameByPID(int pid) {
        String query = "SELECT "+DBHelper.PRODUCTS_PNAME+" FROM "+DBHelper.TABLE_PRODUCTS+" WHERE "+DBHelper.PRODUCTS_ID+" =?";
        String[] arg = new String[]{String.valueOf(pid)};

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, arg);

        if(cursor.moveToNext()) {
            String pname = cursor.getString(cursor.getColumnIndex(DBHelper.PRODUCTS_PNAME));
            db.close();
            return pname;
        }
        else{
            return null;
        }
    }

    /**
     *  Check off when I've picked up an item in my shopping list.
     * @param PID  product ID
     * @param check if the product should be checked or unchecked
     * @return number of rows updated (should be 1 or 0 if PID not in ShoppingList table.)
     */
    public int updateCheck(int PID, Boolean check) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.SHOPPINGLIST_CHECKMARK, check);

        String wheresql = DBHelper.SHOPPINGLIST_PID + "=?";
        String[] args = new String[]{String.valueOf(PID)};

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        int updates = db.update(DBHelper.TABLE_SHOPPINGLIST, cv, wheresql, args);
        db.close();

        return updates;
    }


    /**
     * Given a PID, remove sid item from the Shopping List, return True on success, False on fail.
     * @param PID product ID
     * @return True on success, False on fail.
     */
    public boolean deleteProductShoppingList(int PID) {
        String wheresql = DBHelper.SHOPPINGLIST_PID + "=?";
        String args[] = new String[]{String.valueOf(PID)};

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        int deletes = db.delete(DBHelper.TABLE_SHOPPINGLIST, wheresql, args);
        return deletes > 0;
    }

    /**
     * Given store name, add a new store to the Store table. Return the newly generated SID of given store.
     * @param storeName store name as a string
     * @return the SID generated for the store
     */
    public int addNewStore(String storeName) {
        ContentValues cv=new ContentValues();
        cv.put(DBHelper.STORES_SNAME, storeName);

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        long sid = db.insert(DBHelper.TABLE_STORES, null, cv);
        db.close();
        return (int) sid;
    }

    /**
     * Get all stores from the database in a list of Strings
     * @return all the stores' names in a list of strings
     */
    public ArrayList<String> getAllStoreNames() {
        ArrayList<String> names = new ArrayList<String>();
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String query = "SELECT "+DBHelper.STORES_SNAME+" FROM "+DBHelper.TABLE_STORES;
        Cursor cursor =db.rawQuery(query, null);
        int count =0;
        while(cursor.moveToNext()) {
            String name = cursor.getString(count);
            names.add(name);
        }
        return names;
    }

    /**
     * Get all products from the database in a list of Strings
     * @return all the products' names in a list of strings
     */
    public ArrayList<String> getAllProductNames() {
        ArrayList<String> names = new ArrayList<String>();
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String query = "SELECT "+DBHelper.PRODUCTS_PNAME+" FROM "+DBHelper.TABLE_PRODUCTS;
        Cursor cursor =db.rawQuery(query, null);

        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.PRODUCTS_PNAME));
            names.add(name);
        }
        return names;
    }

    /**
     *  Get all products from the database, no matter checked or unchecked
     * @return List of  products as ItemModel
     */
    public  ArrayList<ItemModel> getAllProducts() {
        ArrayList<ItemModel> products = new ArrayList<ItemModel>();
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_SHOPPINGLIST, null);

        c.moveToFirst();
        Log.d(DatabaseHelper.class.getName(), "getAllProducts:");

        while (!c.isAfterLast()) {
            String product = String.valueOf(c.getInt(c.getColumnIndex(DBHelper.SHOPPINGLIST_PID)));
            String quantity=String.valueOf(c.getDouble(c.getColumnIndex(DBHelper.SHOPPINGLIST_QUANTITY)));
            String unit = String.valueOf(c.getString(c.getColumnIndex(DBHelper.SHOPPINGLIST_UNIT)));
            int che = c.getInt(c.getColumnIndex(DBHelper.SHOPPINGLIST_CHECKMARK));
            boolean check;
            if (che==0) {check=Boolean.FALSE;} else {check = Boolean.TRUE;}

            ItemModel model = new ItemModel(product, quantity, unit, check);
            products.add(model);


            c.moveToNext();
        }

        db.close();

        for (ItemModel ph: products) {
            ph.setProduct(getNameByPID(Integer.parseInt(ph.getProductName())));
            Log.d(DatabaseHelper.class.getName(), "\tgetAllProducts:" + ph.toString());
        }

        return products;
    }



    /**
     * Get all checked products from the database
     * @return List of checked products as ItemModel
     */
    public ArrayList<ItemModel> getAllCheckedProduct() {
        ArrayList<ItemModel> allProducts;
        ArrayList<ItemModel> checkedProducts = new ArrayList<ItemModel>();
        allProducts = getAllProducts();
        Log.d(DatabaseHelper.class.getName(), "getAllCheckedProducts:");
        for (ItemModel item : allProducts) {
            if (item.isAcquired()) {
                Log.d(DatabaseHelper.class.getName(), "\tgetAllCheckedProducts:" + item.toString());

                checkedProducts.add(item);
            }
        }

        return checkedProducts;
    }

    /**
     * Get all unchecked products from the database
     * @return List of unchecked products as ItemModel
     */
    public ArrayList<ItemModel> getAllUncheckedProduct() {
        ArrayList<ItemModel> uncheckedProducts = new ArrayList<ItemModel>();
        ArrayList<ItemModel> allProducts = getAllProducts();
        Log.d(DatabaseHelper.class.getName(), "getAllUncheckedProducts:");
        for (ItemModel item : allProducts) {
            if (!item.isAcquired()) {
                Log.d(DatabaseHelper.class.getName(), "\tgetAllUncheckedProducts:" + item.toString());
                uncheckedProducts.add(item);
            }
        }

        return uncheckedProducts;
    }

    /**
     * Adds new purchase price point to Purchase History table
     * As a user, I want to save the price of an item I have purchased for future reference.
     * @param PID product ID
     * @param SID store ID
     * @param price price
     * @param units 
     * @param quality good or bad?
     * @param timestamp current time
     * @return Newly generated purchaseID
     */
    public int addPurchase(int PID, int SID, double price, String units, int quality, String timestamp) {
        //TODO: ISSUE # implement this method
        ContentValues cv=new ContentValues();
        cv.put(DBHelper.PURCHASEHISTORY_PID, PID);
        cv.put(DBHelper.PURCHASEHISTORY_SID, SID);
        cv.put(DBHelper.PURCHASEHISTORY_PRICE, price);
        cv.put(DBHelper.PURCHASEHISTORY_UNIT, units);
        cv.put(DBHelper.PURCHASEHISTORY_QUALITY, quality);
        cv.put(DBHelper.PURCHASEHISTORY_DATE, timestamp);

        Log.d(DatabaseHelper.class.getName(), "addPurchase: Getting writable database");

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        long _id = db.insert(DBHelper.TABLE_PURCHASEHISTORY, null, cv);
        db.close();


        Log.i(DatabaseHelper.class.getName(), "addPurchase: autoincrement _id='" + _id + "' pid '" + PID + "' sid='" + SID + "' price='" + price + "' units='" + units +
                "' quality='" + quality + "' time='" + timestamp + "'  into PurchaseHistory table.");

        return (int) _id;
    }

    /**
     * Returns a list of ProductHistory objects for purchases of given PID
     * @param PID product ID
     * @return a list of PurchaseHistory objects
     */
    public ArrayList<PurchaseHistory> getAllPurchasesFor(int PID) {

        ArrayList<PurchaseHistory> names = new ArrayList<PurchaseHistory>();
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String query = "SELECT * FROM "+DBHelper.TABLE_PURCHASEHISTORY+" WHERE "+DBHelper.PURCHASEHISTORY_PID+" = ?"+" ORDER BY " + DBHelper.PURCHASEHISTORY_PRICE+" ASC";
        Log.i("LOL", query);
        String[] arg = new String[1];
        arg[0]= Integer.toString(PID);
        Cursor cursor =db.rawQuery(query,arg);
        Log.d(DatabaseHelper.class.getName(), "getAllPurchasesFor("+PID+"):");

        while(cursor.moveToNext()) {
            double price = cursor.getDouble(cursor.getColumnIndex(DBHelper.PURCHASEHISTORY_PRICE));
            String date = cursor.getString(cursor.getColumnIndex(DBHelper.PURCHASEHISTORY_DATE));
            String store = cursor.getString(cursor.getColumnIndex(DBHelper.PURCHASEHISTORY_SID));
            String unit = cursor.getString(cursor.getColumnIndex(DBHelper.PURCHASEHISTORY_UNIT));
            long _id = (long) cursor.getInt(cursor.getColumnIndex(DBHelper.PURCHASEHISTORY_ID));
            PurchaseHistory history= new PurchaseHistory(price,unit,store,date,_id);
            names.add(history);
        }

        for (PurchaseHistory ph: names) {
            ph.setName(getNameBySID(Integer.parseInt(ph.getStore())));
            Log.d(DatabaseHelper.class.getName(), "\t getAllPurchasesFor(" + PID + "):" + ph.toString());

        }

        //sorting
        try {
        Collections.sort(names, new Comparator<PurchaseHistory>() {
            @Override
            public int compare(PurchaseHistory lhs, PurchaseHistory rhs) {
                return lhs.getPrice().compareTo(rhs.getPrice());
            }
        });}
        catch (Exception e) {
            return names;
        }

        return names;
    }
    public ArrayList<PurchaseHistory> getAllPurchasesForbydate(int PID) {

        ArrayList<PurchaseHistory> names = new ArrayList<PurchaseHistory>();
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String query = "SELECT * FROM "+DBHelper.TABLE_PURCHASEHISTORY+" WHERE "+DBHelper.PURCHASEHISTORY_PID+" = ?"+" ORDER BY " + DBHelper.PURCHASEHISTORY_DATE+" DESC" ;
        String[] arg = new String[1];
        arg[0]= Integer.toString(PID);
        Cursor cursor =db.rawQuery(query,arg);
        Log.d(DatabaseHelper.class.getName(), "getAllPurchasesFor("+PID+"):");

        while(cursor.moveToNext()) {
            double price = cursor.getDouble(cursor.getColumnIndex(DBHelper.PURCHASEHISTORY_PRICE));
            String date = cursor.getString(cursor.getColumnIndex(DBHelper.PURCHASEHISTORY_DATE));
            String store = cursor.getString(cursor.getColumnIndex(DBHelper.PURCHASEHISTORY_SID));
            String unit = cursor.getString(cursor.getColumnIndex(DBHelper.PURCHASEHISTORY_UNIT));
            long _id = (long) cursor.getInt(cursor.getColumnIndex(DBHelper.PURCHASEHISTORY_ID));
            PurchaseHistory history= new PurchaseHistory(price,unit,store,date,_id);
            names.add(history);
        }

        for (PurchaseHistory ph: names) {
            ph.setName(getNameBySID(Integer.parseInt(ph.getStore())));
            Log.d(DatabaseHelper.class.getName(), "\t getAllPurchasesFor(" + PID + "):" + ph.toString());

        }

        //sorting
        try {
            Collections.sort(names, new Comparator<PurchaseHistory>() {
                @Override
                public int compare(PurchaseHistory lhs, PurchaseHistory rhs) {
                    return lhs.getPrice().compareTo(rhs.getPrice());
                }
            });}
        catch (Exception e) {
            return names;
        }

        return names;
    }

    /**
     * Return a ProductHistory object that correlates to is the lowest previous purchase price of a minimum 'quality'
     * As a user, I want the cheapest place to buy HIGH quality steaks for a special dinner
     * As a user, I want the cheapest place to buy ANY (low as minimum) quality apples as my budget is tight.
     * @param PID product ID
     * @param quality quality
     * @return a ProductHistory object
     */
    public PurchaseHistory getMinPriceFor(int PID, int quality){
        //TODO: implement this method
        String sql = "SELECT "+DBHelper.PURCHASEHISTORY_ID+", min("+DBHelper.PURCHASEHISTORY_PRICE+") FROM " + DBHelper.TABLE_PURCHASEHISTORY + " WHERE " + DBHelper.PURCHASEHISTORY_PID + "=" + PID + " GROUP BY " + DBHelper.PURCHASEHISTORY_PID;
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        if (!c.isAfterLast()) {
            int _id=c.getInt(c.getColumnIndex("_id"));
            Log.d(DatabaseHelper.class.getName(), "getMinPriceFor("+PID+"):" + _id + ",minprice:"+c.getDouble(1));

            c = db.query(DBHelper.TABLE_PURCHASEHISTORY, null, DBHelper.PURCHASEHISTORY_ID+"="+_id,null,null,null,null);
            c.moveToFirst();
            double price = c.getDouble(c.getColumnIndex(DBHelper.PURCHASEHISTORY_PRICE));
            String unit = c.getString(c.getColumnIndex(DBHelper.PURCHASEHISTORY_UNIT));
            String store = String.valueOf(c.getInt(c.getColumnIndex(DBHelper.PURCHASEHISTORY_SID)));
            String time = c.getString(c.getColumnIndex(DBHelper.PURCHASEHISTORY_DATE));

            db.close();
            store =  getNameBySID(Integer.parseInt(store));
            return new PurchaseHistory(price, unit, store, time,_id);
        } else {
            return null;
        }
    }

    public PurchaseHistory getMinPriceFor(int PID) {
        return getMinPriceFor(PID, 0);
    }


    /**
     * For each item in the ShoppingList Table:
     *call GetMinPrice(PID, quality) <- Returns ProductHistory object for that item at the given min quality
     *add it to ArrayList
     *sort ArrayList by Store
     * @return  sorted ArrayList of ProdcutHistory
     */
    public  ArrayList<PurchaseHistory> CreateShoppingTrip() {
        //TODO: implement this method
        return null;
    }

    /**
     * Delete from PurcahseHistory table, tuple with key as purchaseID, return true/false based on success
     * @param purchaseID purchase ID
     * @return true on success, false on fail *raising exception suggested
     */
    public boolean deletePreviousPurchase(int purchaseID) {
        String wheresql = DBHelper.PURCHASEHISTORY_ID + "=?";
        String args[] = new String[]{String.valueOf(purchaseID)};

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        int deletes = db.delete(DBHelper.TABLE_PURCHASEHISTORY, wheresql, args);
        db.close();
        return deletes > 0;
        
    }

    /**
     * Delete all purchase history older than a give date for a certain PID, or -1 for all history.
     * Return int, # of deleted Rows.
     * As a user, I want to remove old purchase history from the app easily as to keep up with up to date pricing.
     * to delete a specific product history older than.
     * @param timestamp time to delete history older than
     * @param PID of item to remove purchase history for or -1 for all items
     * @return # of deleted Rows.
     */
    public int deleteHistoryOlderThan(String timestamp, int PID) {
        //TODO: implement this method'
        String wheresql;
        String args[];
        if (PID == -1) {
            wheresql = DBHelper.PURCHASEHISTORY_DATE + "<=?";
            args = new String[]{timestamp};
        } else {
            wheresql = DBHelper.PURCHASEHISTORY_DATE + "<=? AND " + DBHelper.PURCHASEHISTORY_PID + "=?";
            args = new String[]{timestamp, String.valueOf(PID)};
        }

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        int deletes = db.delete(DBHelper.TABLE_PURCHASEHISTORY, wheresql, args);
        db.close();

        Log.d(DatabaseHelper.class.getName(), "deleteHistoryOlderThan(" + timestamp+","+PID+"): Num Items deleted:"+deletes);
        LogPurchaseHistoryTable();
        return deletes;
    }

}
