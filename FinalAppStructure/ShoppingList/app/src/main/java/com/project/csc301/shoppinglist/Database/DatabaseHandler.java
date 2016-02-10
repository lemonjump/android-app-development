package com.project.csc301.shoppinglist.Database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

/**
 * Created by memon on 17/11/15.
 * USAGE: SQLiteDatabase DB = DatabaseHandler.getWriteableDatabase or getReadableDatabase.
 * This should be a global variable created on program startup.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shoppingapp.db";
    private static final int DATABASE_VERSION = 4;

    public static final String TABLE_PRODUCTS = "Products";
    public static final String TABLE_SHOPPINGLIST = "ShoppingList";
    public static final String TABLE_STORES = "Stores";
    public static final String TABLE_PURCHASEHISTORY = "PurchaseHistory";

    public static final String PRODUCTS_ID = "_id";
    public static final String PRODUCTS_PNAME = "pname";


    public static final String SHOPPINGLIST_ID = "_id";
    public static final String SHOPPINGLIST_PID = "pid";
    public static final String SHOPPINGLIST_CHECKMARK = "checkmark";
    public static final String SHOPPINGLIST_QUALITY = "quality";
    public static final String SHOPPINGLIST_QUANTITY = "quantity";
    public static final String SHOPPINGLIST_UNIT = "unit";

    public static final String STORES_ID = "_id";
    public static final String STORES_SNAME = "sname";
    public static final String STORES_LOCATION = "location";

    public static final String PURCHASEHISTORY_ID = "_id";
    public static final String PURCHASEHISTORY_PID = "pid";
    public static final String PURCHASEHISTORY_SID = "sid";
    public static final String PURCHASEHISTORY_PRICE = "price";
    public static final String PURCHASEHISTORY_UNIT = "unit";
    public static final String PURCHASEHISTORY_QUALITY = "quality";
    public static final String PURCHASEHISTORY_DATE = "date";

    // Database creation sql statement
    private static final String DATABASE_CREATE_PRODUCTS = "CREATE TABLE IF NOT EXISTS "+ TABLE_PRODUCTS +
            "(" + PRODUCTS_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
            PRODUCTS_PNAME +" VARCHAR NOT NULL UNIQUE);";

    private static final String DATABASE_CREATE_SHOPPING = "CREATE TABLE IF NOT EXISTS " + TABLE_SHOPPINGLIST +
            "(" + SHOPPINGLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            SHOPPINGLIST_PID + " INTEGER NOT NULL UNIQUE," +
            SHOPPINGLIST_CHECKMARK + " BOOLEAN NOT NULL DEFAULT 0," +
            SHOPPINGLIST_QUALITY + " INTEGER NOT NULL DEFAULT 0," +
            SHOPPINGLIST_QUANTITY + " DOUBLE DEFAULT 0," +
            SHOPPINGLIST_UNIT + " VARCHAR DEFAULT ''," +
            "FOREIGN KEY("+SHOPPINGLIST_PID+") REFERENCES "+TABLE_PRODUCTS+"("+ PRODUCTS_ID +") ON DELETE CASCADE);";

    private static final String DATABASE_CREATE_STORES = "CREATE TABLE IF NOT EXISTS " + TABLE_STORES +
            "(" + STORES_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
            STORES_SNAME + " VARCHAR NOT NULL UNIQUE," +
            STORES_LOCATION + " VARCHAR DEFAULT '');";

    private static final String DATABASE_CREATE_PURCHASE_HISTORY = "CREATE TABLE IF NOT EXISTS " + TABLE_PURCHASEHISTORY +
            "(" + PURCHASEHISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PURCHASEHISTORY_PID + " INTEGER NOT NULL," +
            PURCHASEHISTORY_SID + " INTEGER NOT NULL," +
            PURCHASEHISTORY_PRICE + " DOUBLE NOT NULL," +
            PURCHASEHISTORY_UNIT + " VARCHAR NOT NULL," +
            PURCHASEHISTORY_QUALITY + " INTEGER NOT NULL DEFAULT 0," +
            PURCHASEHISTORY_DATE + " TIMESTAMP NOT NULL," +
            "FOREIGN KEY("+ PURCHASEHISTORY_PID+") REFERENCES "+TABLE_PRODUCTS+"("+PRODUCTS_ID+") ON DELETE CASCADE," +
            "FOREIGN KEY("+PURCHASEHISTORY_SID+") REFERENCES "+TABLE_STORES+"("+STORES_ID+") ON DELETE CASCADE);";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.i(DatabaseHandler.class.getName(),
                "Creating table 'Products' with \n\t'" + DATABASE_CREATE_PRODUCTS +"'");
        database.execSQL(DATABASE_CREATE_PRODUCTS);
        Log.i(DatabaseHandler.class.getName(),
                "Creating table 'ShoppingList' with \n\t'" + DATABASE_CREATE_SHOPPING + "'");
        database.execSQL(DATABASE_CREATE_SHOPPING);
        Log.i(DatabaseHandler.class.getName(),
                "Creating table 'Stores' with \n\t'" + DATABASE_CREATE_STORES + "'");
        database.execSQL(DATABASE_CREATE_STORES);
        Log.i(DatabaseHandler.class.getName(),
                "Creating table 'PurchaseHistory' with \n\t'" + DATABASE_CREATE_PURCHASE_HISTORY + "'");
        database.execSQL(DATABASE_CREATE_PURCHASE_HISTORY);

    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        //onUpgrade(db, 1, 1);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(DatabaseHandler.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS PurchaseHistory;");
        db.execSQL("DROP TABLE IF EXISTS Stores;");
        db.execSQL("DROP TABLE IF EXISTS ShoppingList;");
        db.execSQL("DROP TABLE IF EXISTS Products;");

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,newVersion, oldVersion);
    }
}


