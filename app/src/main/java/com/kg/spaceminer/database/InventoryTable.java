package com.kg.spaceminer.database;

import com.kg.spaceminer.model.Inventory;

public class InventoryTable {

    public static final String TABLE_INVENTORY = "inventory";
    public static final String COLUMN_SLOT_ID = "inventorySlotID";
    public static final String COLUMN_ITEM_TYPE_ID = "itemTypeId";
    public static final String COLUMN_ITEM_QTY = "itemQty";


    public static final String[] ALL_COLUMNS =
            {COLUMN_SLOT_ID, COLUMN_ITEM_TYPE_ID, COLUMN_ITEM_QTY};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_INVENTORY + "(" +
                    COLUMN_SLOT_ID + " TEXT," +
                    COLUMN_ITEM_TYPE_ID + " TEXT," +
                    COLUMN_ITEM_QTY + " INTEGER" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_INVENTORY;
}
