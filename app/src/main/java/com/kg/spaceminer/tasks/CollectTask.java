package com.kg.spaceminer.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.kg.spaceminer.IProcess;
import com.kg.spaceminer.InventoryAdapter;
import com.kg.spaceminer.MiningCartAdapter;
import com.kg.spaceminer.SpaceMiner;
import com.kg.spaceminer.database.DataSource;
import com.kg.spaceminer.model.Inventory;
import com.kg.spaceminer.model.Item;

import java.util.ArrayList;
import java.util.Iterator;

//collect ore that has been mined
public class CollectTask extends AsyncTask<String, Void, String> {

    //non-public, non-static
    private SpaceMiner mGame = SpaceMiner.getInstance();
    private Inventory mInventory = Inventory.getInstance();

   //private InventoryAdapter mInventoryView;
    private MiningCartAdapter mMiningCart;
    private IProcess mToastResult;

    //context
    Context mContext;

    //data
    DataSource mDataSource;


    //constructor
    public CollectTask(MiningCartAdapter mMiningCart, IProcess mToastResult, Context mContext) {
        //this.mInventoryView = mInventoryView;
        this.mMiningCart = mMiningCart;
        this.mToastResult = mToastResult;
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... strings) {

        mDataSource = new DataSource(mContext);
        mDataSource.open();

        String result = null;
        boolean successful = false;
            /*
            1 Take an the arraylist from the mining cart (game singleton)and loop through it
            2 Add each item using the addItem() method from the inventory (singleton)
            3 mark each successful transfer for removal from the mining cart
            3 loop through the list of indexes marked for removal and remove them
            4 Notify adapters
             */
        ArrayList<Item> cart = mGame.getMiningCartOres();
        ArrayList<String> itemsToRemoveFromCart = new ArrayList<String>();
        for (int i = 0; i < cart.size(); i++) {
            Item ore = cart.get(i);
            successful = mInventory.addItem(ore);
            if(successful){
                itemsToRemoveFromCart.add(ore.getUniqueId());
            }
        }

        for(Iterator<Item> removalIterator = cart.iterator(); removalIterator.hasNext();) {
            Item item = removalIterator.next();
            for (String uniqueId : itemsToRemoveFromCart ) {
                if(uniqueId.equals(item.getUniqueId())){
                    removalIterator.remove();
                }
            }
        }

        if(successful) {
            result = "Ores added to inventory";
        } else {
            result = "There is no room in your inventory. Sell items to make room.";
        }

        mDataSource.saveInventory(mInventory.getSlots());
        mDataSource.close();

        return result;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        //mInventoryView.notifyDataSetChanged();
        mMiningCart.refreshOre(mGame.getMiningCartOres());
        mToastResult.update(string);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        mDataSource.close();
    }
}
