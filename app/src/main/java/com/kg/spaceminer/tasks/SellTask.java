package com.kg.spaceminer.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.kg.spaceminer.IProcess;
import com.kg.spaceminer.InventoryAdapter;
import com.kg.spaceminer.SpaceMiner;
import com.kg.spaceminer.database.DataSource;
import com.kg.spaceminer.model.Inventory;
import com.kg.spaceminer.model.Player;

public class SellTask extends AsyncTask<String, Void, String> {

    //non-public, non-static
    private Player mPlayer = Player.getInstance();
    private SpaceMiner mGame = SpaceMiner.getInstance();
    private Inventory mInventory = Inventory.getInstance();
    private String itemTypeId;

    //slot
    int slotId;
    int slotSize;

    //context
    Context mContext;

    //adapter
    InventoryAdapter mAdapter;

    //data
    DataSource mDataSource;

    //constructor
    public SellTask(int slotId, int slotSize, InventoryAdapter mAdapter, Context mContext) {
        this.slotId = slotId;
        this.slotSize = slotSize;
        this.mAdapter = mAdapter;
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {

        mDataSource = new DataSource(mContext);
        mDataSource.open();

        String result = null;
        //int numberOfItemsToSell = mInventory.checkInventory(itemTypeId);
        if(slotSize > 0){

            float totalValueOfRemovedItems = mInventory.clearSlot(slotId);
            System.out.println("###################### " + totalValueOfRemovedItems);
            mPlayer.addCredits(totalValueOfRemovedItems);
            result = "Transaction successful";

        } else {
            result = "Transaction failed";
        }

        mDataSource.saveInventory(mInventory.getSlots());
        mDataSource.updateCredits(mPlayer.getCredits());
        mDataSource.close();

        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        mDataSource.close();
    }
}
