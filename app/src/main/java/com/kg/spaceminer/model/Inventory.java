package com.kg.spaceminer.model;

import com.kg.spaceminer.MainActivity;
import com.kg.spaceminer.database.DataSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Inventory {
    private static final Inventory sInstance = new Inventory();
    private int mInventorySize;
    private HashMap<Integer, Slot> mSlots;

    public static Inventory getInstance() {
        return sInstance;
    }

    private Inventory() {
        this.mInventorySize = 32;
        this.mSlots = new HashMap<Integer, Slot>();
        //for (int i = 0; i < this.mInventorySize ; i++) {
        //    mSlots.put(i, new Slot(i, null));
        //}
    }

    public HashMap<Integer, Slot> getSlots() {
        return this.mSlots;
    }

    public boolean addItem(Item newItem){//returns true if the item was added to a slot
        boolean status = false;
        for (int i = 0; i < this.mInventorySize - 1; i++) {
            Slot slot = this.mSlots.get(i);
            if(slot.getContents().size() > 0) {
                if(slot.getOccupiedItemTypeId().equals(newItem.getItemTypeId()) && slot.getContents().size() < slot.getSlotCapacity()){
                    slot.addItem(newItem);
                    status = true;
                    break;
                }
            } else if(slot.getContents().size() == 0){
                slot.setOccupiedItemTypeId(newItem.getItemTypeId());
                slot.addItem(newItem);
                status = true;
                break;
            }

        }
        return status;
    }

    public int checkInventory(String itemTypeId){
        int numberOfItemsFound = 0;

        /*
        this checks if the inventory contains a certain amount of items
        this check is used before anything is done with items in the inventory
         */

        //loop through all the slots in the inventory
        for (int i = 0; i < this.mInventorySize - 1; i++) {
            Slot slot = this.mSlots.get(i);
            ArrayList<Item> contents = slot.getContents();

            //if a slot is found that houses the desired item type, and its size is above 0
            if (slot.getOccupiedItemTypeId().equals(itemTypeId) && contents != null) {

                //add the size of the slot to the number of items found
                numberOfItemsFound = (numberOfItemsFound + contents.size());
            }

        }

        return numberOfItemsFound;
    }


    public float removeItem(String itemTypeId, int qty) {//returns the total value of items removed (float)
        float totalValueOfItemsRemoved = 0.0F;

        /*
        1. loop and get slots until a slot is found containing items (more than zero) of the type we want
        2. while the qty is still above zero OR the size of the slot is above zero, remove items and reduce qty by 1
        3. add to the total value found
        3. if qty is still above zero, repeat the loop
        4. if after a loop, the total value found has not increased, then no suitable item has been found; exit the loop and return 0
         */

        //while the qty requested is still not fulfilled
        while(qty > 0){

            //get the total value of items removed as of this loop
            float valueOfItemsForThisLoop = 0.0F;

            //loop through each slot in the inventory
            for (int i = 0; i < this.mInventorySize - 1; i++) {
                Slot slot = this.mSlots.get(i);

                try {
                        ArrayList<Item> contents = slot.getContents();

                        //if the type item that is in the inventory slot matches that of the items we are looking for, and there are items in the slot
                        if (slot.getOccupiedItemTypeId().equals(itemTypeId) && contents.size() >= 1) {

                            //while the slot still has objects, remove them
                            while(contents.size() > 0 && qty > 0){

                                try {
                                    Item item = contents.get(contents.size() -1);//get the item
                                    float itemValue = item.getItemValue();//get the value of the item
                                    valueOfItemsForThisLoop = valueOfItemsForThisLoop + itemValue;//add the value to the current total as of this loop
                                    slot.removeItem(contents.size() - 1);//remove the item
                                    qty--;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    break;
                                }
                            }

                            //if the slot we just removed items from is now empty, change its item type to "no_item"
                            if(slot.getContents().size() == 0){
                                slot.setOccupiedItemTypeId("no_item");
                            }
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if((totalValueOfItemsRemoved + valueOfItemsForThisLoop) <= totalValueOfItemsRemoved){//break on any loop if the value of items removed has not increased for this loop, indicating that no items were found
                break;
            } else {
                totalValueOfItemsRemoved = totalValueOfItemsRemoved + valueOfItemsForThisLoop; //otherwise add the value to the grand total
            }
        }

        return totalValueOfItemsRemoved;
    }

    public float clearSlot(int slotId){
        float valueOfItemsRemoved = 0;

        Slot slot = this.mSlots.get(slotId);
        int slotSize = slot.getContents().size();
        ArrayList<Item> contents = slot.getContents();
        if(slotSize > 0){
            for (int i = 0; i < slotSize; i++) {
                float value = contents.get(i).getItemValue();
                valueOfItemsRemoved = (valueOfItemsRemoved + value);
            }
        }
        contents.clear();
        slot.setOccupiedItemTypeId("no_item");

        return valueOfItemsRemoved;
    }

    public int getmInventorySize() {
        return mInventorySize;
    }

    public void setmSlots(HashMap<Integer, Slot> mSlots) {
        this.mSlots = mSlots;
    }


    public static class Slot {
        private int slotId;
        private String occupiedItemTypeId;
        private int slotCapacity;
        private ArrayList<Item> contents;

        public Slot() {
            this.slotId = 0;
            this.occupiedItemTypeId = "no_item";
            this.slotCapacity = 12;
            this.contents = new ArrayList<Item>(slotCapacity);
        }

        public Slot(int slotId, String occupiedItemTypeId) {
            if(occupiedItemTypeId == null){
                occupiedItemTypeId = "no_item";
            }
            this.slotId = slotId;
            this.occupiedItemTypeId = occupiedItemTypeId;
            this.slotCapacity = 12;
            this.contents = new ArrayList<Item>(slotCapacity);
        }

        public ArrayList<Item> getContents() {
            return this.contents;
        }

        public void addItem(Item item) {
            this.contents.add(item);
        }

        public void removeItem(int index) {
            this.contents.remove(this.contents.get(index));
        }

        public int getSlotCapacity() {
            return this.slotCapacity;
        }

        public String getOccupiedItemTypeId(){
            return occupiedItemTypeId;
        }
        public void setOccupiedItemTypeId(String occupiedItemTypeId) {
            this.occupiedItemTypeId = occupiedItemTypeId;
        }

        public int getSlotId() {
            return slotId;
        }

        public void setSlotId(int slotId) {
            this.slotId = slotId;
        }

        public void setSlotCapacity(int slotCapacity) {
            this.slotCapacity = slotCapacity;
        }
    }
}
