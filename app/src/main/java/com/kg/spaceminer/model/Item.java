package com.kg.spaceminer.model;

import java.text.NumberFormat;
import java.util.UUID;

public class Item {
    private String uniqueId;
    private String itemTypeId;
    private String itemName;
    private String itemCategory;
    private float itemValue;

    public Item() {
        if(uniqueId == null) {
            uniqueId = UUID.randomUUID().toString();
        }
        this.uniqueId = uniqueId;
        this.itemTypeId = "item0";
        this.itemName = "Item";
        this.itemCategory = "Misc";
        this.itemValue = 0.0F;
    }

    public Item(String uniqueId, String itemTypeId, String itemName, String itemCategory, float itemValue) {
        if(uniqueId == null) {
            uniqueId = UUID.randomUUID().toString();
        }
        this.uniqueId = uniqueId;
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

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public float getItemValue() {
        return itemValue;
    }

    public void setItemValue(float itemValue) {
        this.itemValue = itemValue;
    }
}
