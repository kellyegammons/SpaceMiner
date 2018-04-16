package com.kg.spaceminer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kg.spaceminer.database.DataSource;
import com.kg.spaceminer.model.Inventory;
import com.kg.spaceminer.model.ItemDefinition;
import com.kg.spaceminer.model.Player;

import java.util.HashMap;

public class InventoryActivity extends AppCompatActivity {

    //non-public, non-static
    private Inventory mInventory = Inventory.getInstance();
    RecyclerView viewInventory;

    //data
    DataSource mDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        //data
        mDataSource = new DataSource(InventoryActivity.this);
        mDataSource.open();
        HashMap<String, ItemDefinition> itemDirectory = mDataSource.getItemsDirectory();

        //recycler view
        InventoryAdapter inventoryView = new InventoryAdapter(mInventory.getSlots(), itemDirectory,InventoryActivity.this);
        viewInventory = (RecyclerView) findViewById(R.id.inventory);
        viewInventory.setAdapter(inventoryView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }

}
