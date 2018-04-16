package com.kg.spaceminer.model;

import java.text.NumberFormat;

public class ItemDefinition {
    String itemTypeId;
    String itemName;
    String itemCategory;
    float itemValue;

    public ItemDefinition() {
        this.itemTypeId = null;
        this.itemName = null;
        this.itemCategory = null;
        this.itemValue = 0;
    }

    public ItemDefinition(String itemTypeId, String itemName, String itemCategory, float itemValue) {
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

    public String getValueFormatted(){
        String formattedCredits = null;
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        formattedCredits = currencyFormatter.format(this.itemValue);
        return formattedCredits;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }
}
