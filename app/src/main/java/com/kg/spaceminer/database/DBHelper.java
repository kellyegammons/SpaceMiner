package com.kg.spaceminer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kg.spaceminer.model.Inventory;

public class DBHelper extends SQLiteOpenHelper {
    private Inventory inventory = Inventory.getInstance();
    public static final String DB_FILE_NAME = "spaceminergame.db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PlayerTable.SQL_CREATE);
        sqLiteDatabase.execSQL(PlayerTable.SQL_CREATE_PLAYER_SETTINGS);
        sqLiteDatabase.execSQL(ItemsTable.SQL_CREATE);
        sqLiteDatabase.execSQL(ItemsTable.SQL_CREATE_ITEM_DIRECTORY);
        sqLiteDatabase.execSQL(InventoryTable.SQL_CREATE);

        for (int i = 0; i < inventory.getmInventorySize() - 1; i++) {
            ContentValues values = new ContentValues(3);
            values.put(InventoryTable.COLUMN_SLOT_ID, i);
            values.put(InventoryTable.COLUMN_ITEM_TYPE_ID, "no_item");
            values.put(InventoryTable.COLUMN_ITEM_QTY, 0);
            sqLiteDatabase.replace(InventoryTable.TABLE_INVENTORY, null, values);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(ItemsTable.SQL_DELETE);
        onCreate(sqLiteDatabase);
    }

}
