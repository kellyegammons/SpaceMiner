package com.kg.spaceminer.database;

public class ItemsTable {

    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ID = "itemId";
    public static final String COLUMN_NAME = "itemName";
    public static final String COLUMN_CATEGORY = "itemCategory";
    public static final String COLUMN_VALUE = "itemValue";

    public static final String[] ALL_COLUMNS =
            {COLUMN_ID, COLUMN_NAME, COLUMN_CATEGORY, COLUMN_VALUE};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_ITEMS + "(" +
                    COLUMN_ID + " TEXT NOT NULL PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_CATEGORY + " TEXT," +
                    COLUMN_VALUE + " INTEGER" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_ITEMS;

    public static final String SQL_CREATE_ITEM_DIRECTORY =
            "INSERT INTO " + TABLE_ITEMS + " (" + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_CATEGORY + ", " + COLUMN_VALUE + ") VALUES\n" +
                    "('no_item', 'Empty', 'misc', 0.0),\n" +
                    "('ore_debris', 'Debris', 'ores', 1.0),\n" +
                    "('ore_iron', 'Iron', 'ores', 10.0),\n" +
                    "('ore_copper', 'Copper', 'ores', 25.0),\n" +
                    "('ore_silver', 'Silver', 'ores', 100.0),\n" +
                    "('ore_gold', 'Gold', 'ores', 500.0);";



}
