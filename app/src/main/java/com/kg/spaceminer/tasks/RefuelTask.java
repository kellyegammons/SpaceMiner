package com.kg.spaceminer.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.kg.spaceminer.IProcess;
import com.kg.spaceminer.InventoryAdapter;
import com.kg.spaceminer.SpaceMiner;
import com.kg.spaceminer.database.DataSource;
import com.kg.spaceminer.model.Inventory;
import com.kg.spaceminer.model.Player;

public class RefuelTask extends AsyncTask<String, Void, String> {

    //non-public, non-static
    private Player mPlayer = Player.getInstance();
    private Inventory mInventory = Inventory.getInstance();
    private IProcess mToastResult;

    //context
    Context mContext;

    //data
    DataSource mDataSource;

    //constructor
    public RefuelTask(IProcess mToastResult, Context mContext) {
        //this.mInventoryView = mInventoryView;
        this.mToastResult = mToastResult;
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {

        mDataSource = new DataSource(mContext);
        mDataSource.open();

        String result = null;
        boolean status = false;
        if(mInventory.checkInventory("ore_debris") >= 2 ){//if the check finds out that enough items are available...

            if(mInventory.removeItem("ore_debris", 2) > 0.0F){//and items were removed...
                status = true;//...then it worked
            }
        }
        if(status){
            result = "Refueled";
            mPlayer.setFuel(10);
            mDataSource.updateFuel(10);
        } else {
            result = "2 Space Debris are needed to refuel.";
        }

        mDataSource.saveInventory(mInventory.getSlots());
        mDataSource.close();

        return result;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        //mInventoryView.notifyDataSetChanged();
        mToastResult.update(string);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        mDataSource.close();
    }
}