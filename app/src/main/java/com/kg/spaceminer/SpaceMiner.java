package com.kg.spaceminer;

import android.os.Looper;

import com.kg.spaceminer.model.Item;
import com.kg.spaceminer.model.ItemDefinition;
import com.kg.spaceminer.sampledata.SampleItemDirectory;

import java.util.ArrayList;
import java.util.HashMap;

/*this is the application singleton*/
public class SpaceMiner {
    private static SpaceMiner sInstance = new SpaceMiner();
    private float maxAllowableCredits = 1000000.00F;
    private ArrayList<Item> miningCartOres = new ArrayList<Item>();

    //empty constructor
    private SpaceMiner() {
        //
    }

    //get instance
    public static SpaceMiner getInstance() {
        return sInstance;
    }

    public float getMaxAllowableCredits() {
        return maxAllowableCredits;
    }

    public void setMaxAllowableCredits(float maxAllowableCredits) {
        this.maxAllowableCredits = maxAllowableCredits;
    }

    public void addOreToMiningCart(Item ore) {
        this.miningCartOres.add(ore);
    }

    public ArrayList<Item> getMiningCartOres() {
        return miningCartOres;
    }

    public void clearMiningCartOres() {
        this.miningCartOres.clear();
    }

    public Item findOre(int oreNumber, HashMap<String, ItemDefinition> itemDefinitionMap) {
        Item ore = null;
        String itemTypeId = null;
        String itemName = null;
        String itemCategory = null;
        float itemValue = 0.0F;

        if(oreNumber%50 == 0 ) {//if oreNumber is divisible by 50 it is silver (2 in 100 chance)
            itemTypeId = "ore_gold";
        } else if(oreNumber%25 == 0 ) {//if oreNumber is divisible by 25 it is silver (4 in 100 chance)
            itemTypeId = "ore_silver";
        } else if(oreNumber%10 == 0) {//if oreNumber is divisible by 10 it is copper (10 in 100 chance)
            itemTypeId = "ore_copper";
        } else if(oreNumber%3 == 0) {//if oreNumber is divisible by 3 it is iron (33.3 in 100 chance)
            itemTypeId = "ore_iron";
        } else {//anything else is garbage
            itemTypeId = "ore_debris";
        }

        ItemDefinition definition = itemDefinitionMap.get(itemTypeId);

        itemName = definition.getItemName();
        itemCategory = definition.getItemCategory();
        itemValue = definition.getItemValue();

        //create new ore object
        ore = new Item(null, itemTypeId, itemName, itemCategory, itemValue);
        return ore;
    }
}
