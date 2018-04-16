package com.kg.spaceminer.sampledata;

import java.util.HashMap;

public class SampleItemDirectory {

    private static HashMap<String, ItemDefinition> itemDirectory;

    static{//static initializer
        itemDirectory = new HashMap<>();

        itemDirectory.put("ore_debris", new ItemDefinition("ore_debris", "ores", "Debris", 1.0F));
        itemDirectory.put("ore_iron", new ItemDefinition("ore_iron", "ores", "Iron", 10.0F));
        itemDirectory.put("ore_copper", new ItemDefinition("ore_copper", "ores", "Copper", 25.0F));
        itemDirectory.put("ore_silver", new ItemDefinition("ore_silver", "ores", "Silver", 100.0F));
        itemDirectory.put("ore_gold", new ItemDefinition("ore_gold", "ores", "Gold", 500.0F));
        itemDirectory.put("no_item", new ItemDefinition("no_item", "misc", "Empty", 0.0F));

    }

    public static HashMap<String, ItemDefinition> getItemDirectory() {
        return itemDirectory;
    }

    public static void setItemDirectory(HashMap<String, ItemDefinition> itemDirectory) {
        SampleItemDirectory.itemDirectory = itemDirectory;
    }

    public static class ItemDefinition {
        String itemTypeId;
        String itemName;
        String itemCategory;
        float itemValue;

        public ItemDefinition(String itemTypeId, String itemCategory, String itemName, float itemValue) {
            this.itemTypeId = itemTypeId;
            this.itemName = itemName;
            this.itemCategory = itemCategory;
            this.itemValue = itemValue;
        }

        public String getItemTypeId() {
            return itemTypeId;
        }

        public void setItemTypeId(String itemTypeId) {
            this.itemTypeId = itemTypeId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public float getItemValue() {
            return itemValue;
        }

        public void setItemValue(float itemValue) {
            this.itemValue = itemValue;
        }

        public String getItemCategory() {
            return itemCategory;
        }

        public void setItemCategory(String itemCategory) {
            this.itemCategory = itemCategory;
        }
    }
}
