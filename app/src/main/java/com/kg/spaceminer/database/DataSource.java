package com.kg.spaceminer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kg.spaceminer.model.Inventory;
import com.kg.spaceminer.model.Item;
import com.kg.spaceminer.model.ItemDefinition;

import java.util.HashMap;

public class DataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    SQLiteOpenHelper mDbHelper;
    private Inventory inventory = Inventory.getInstance();

    public DataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    //item directory
    public HashMap<String, ItemDefinition> getItemsDirectory() {
        HashMap<String, ItemDefinition> itemsDirectory = new HashMap<>();

        Cursor cursor = mDatabase.query(ItemsTable.TABLE_ITEMS, ItemsTable.ALL_COLUMNS,
                null, null, null, null, null);

        while(cursor.moveToNext()) {
            ItemDefinition definition = new ItemDefinition();
            definition.setItemTypeId(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
            definition.setItemName(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_NAME)));
            definition.setItemCategory(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_CATEGORY)));
            definition.setItemValue(cursor.getFloat(cursor.getColumnIndex(ItemsTable.COLUMN_VALUE)));
            itemsDirectory.put(definition.getItemTypeId(), definition);
        }

        cursor.close();

        return itemsDirectory;
    }

    //inventory
    /*
    getInventory
    1. return a hashmap of slots, with the key being slot id
    2. create a cursor, querying the inventory table, all columns
    3. while loop through each row
        3a. create a new Slot object
        3b. set the slotId as the id of the row
        3c. set the slotItemTypeId
        3d. set the qty
            3i. loop qty times
            3ii. each loop make a new Item object
            3iii. use the getItemsDirectory and assign the Item values based on the TypeID key from the hashmap
            3iv. add the item to the slot contents arraylist
    4. put the slot into the hashmap using the id as the key
     */

    public HashMap<Integer, Inventory.Slot> getInventory() {
        HashMap<Integer, Inventory.Slot> inventory = new HashMap<>();
        HashMap<String, ItemDefinition> itemDirectory = getItemsDirectory();//directory

        Cursor cursor = mDatabase.query(InventoryTable.TABLE_INVENTORY, InventoryTable.ALL_COLUMNS,
                null, null, null, null, null);

        while(cursor.moveToNext()) {

            Inventory.Slot slot = new Inventory.Slot();//new slot

            int slotId = cursor.getInt(cursor.getColumnIndex(InventoryTable.COLUMN_SLOT_ID));
            String itemTypeId = cursor.getString(cursor.getColumnIndex(InventoryTable.COLUMN_ITEM_TYPE_ID));
            slot.setSlotId(slotId);
            slot.setOccupiedItemTypeId(itemTypeId);

            int qty = cursor.getInt(cursor.getColumnIndex(InventoryTable.COLUMN_ITEM_QTY));
            for (int i = 0; i < qty; i++) {//make i items and put them in the slot
                Item item = new Item();
                item.setItemTypeId(itemTypeId);
                ItemDefinition definition = itemDirectory.get(itemTypeId);
                item.setItemName(definition.getItemName());
                item.setItemCategory(definition.getItemCategory());
                item.setItemValue(definition.getItemValue());
                slot.addItem(item);
            }

            inventory.put(slotId, slot);

        }

        cursor.close();

        return inventory;
    }

    public boolean saveInventory(HashMap<Integer, Inventory.Slot> inventory) {
        boolean status = false;
        try {
            for (int i = 0; i < inventory.size() - 1; i++) {
                Inventory.Slot slot = inventory.get(i);
                ContentValues values = new ContentValues(3);
                values.put(InventoryTable.COLUMN_SLOT_ID, slot.getSlotId());
                values.put(InventoryTable.COLUMN_ITEM_TYPE_ID, slot.getOccupiedItemTypeId());
                values.put(InventoryTable.COLUMN_ITEM_QTY, slot.getContents().size());
                mDatabase.update(InventoryTable.TABLE_INVENTORY, values, InventoryTable.COLUMN_SLOT_ID + " = '" + slot.getSlotId() + "'", null);
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean resetInventory() {
        boolean status = false;
        for (int i = 0; i < inventory.getmInventorySize() - 1; i++) {

            ContentValues values = new ContentValues(3);
            values.put(InventoryTable.COLUMN_SLOT_ID, i);
            values.put(InventoryTable.COLUMN_ITEM_TYPE_ID, "no_item");
            values.put(InventoryTable.COLUMN_ITEM_QTY, 0);
            mDatabase.replace(InventoryTable.TABLE_INVENTORY, null, values);
            status = true;
        }
        return status;
    }

    //fuel
    public void updateFuel(int fuel){
        ContentValues values = new ContentValues(1);
        values.put(PlayerTable.COLUMN_FUEL, fuel);
        mDatabase.update(PlayerTable.TABLE_PLAYER, values, PlayerTable.COLUMN_ID + " = 1", null);
    }

    public int getFuel(){
        int fuel = 0;

        Cursor cursor = null;
        try {
            cursor = mDatabase.query(PlayerTable.TABLE_PLAYER, PlayerTable.ALL_COLUMNS,
                    null, null, null, null, null);
            if(cursor.moveToFirst()){
               fuel = cursor.getInt(cursor.getColumnIndex(PlayerTable.COLUMN_FUEL));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return fuel;
    }

    //fuel
    public void updateCredits(float credits){
        ContentValues values = new ContentValues(1);
        values.put(PlayerTable.COLUMN_CREDITS, credits);
        mDatabase.update(PlayerTable.TABLE_PLAYER, values, PlayerTable.COLUMN_ID + " = 1", null);
    }

    public int getCredits(){
        int fuel = 0;

        Cursor cursor = null;
        try {
            cursor = mDatabase.query(PlayerTable.TABLE_PLAYER, PlayerTable.ALL_COLUMNS,
                    null, null, null, null, null);
            if(cursor.moveToFirst()){
                fuel = cursor.getInt(cursor.getColumnIndex(PlayerTable.COLUMN_CREDITS));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fuel;
    }

}
