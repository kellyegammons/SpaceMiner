package com.kg.spaceminer.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.kg.spaceminer.IProcess;
import com.kg.spaceminer.MainActivity;
import com.kg.spaceminer.MiningCartAdapter;
import com.kg.spaceminer.SpaceMiner;
import com.kg.spaceminer.database.DataSource;
import com.kg.spaceminer.model.Item;
import com.kg.spaceminer.model.Player;

import java.util.ArrayList;
import java.util.Random;

public class MineTask extends AsyncTask<String, Void, String> {

        //context
        private Context mContext;

        //non-public, non-static
        private Player mPlayer = Player.getInstance();
        private SpaceMiner mGame = SpaceMiner.getInstance();
        private IProcess mScrollToBottom;
        private MiningCartAdapter mMiningCart;
        private DataSource mDataSource;

        //static
        private static final Random sRandom = new Random();

        //constructor
        public MineTask(MiningCartAdapter mMiningCart, IProcess mScrollToBottom, Context mContext) {
            this.mMiningCart = mMiningCart;
            this.mScrollToBottom = mScrollToBottom;
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            mDataSource = new DataSource(mContext);
            mDataSource.open();//open datasource
            /*

            while fuel is available (more than 0), reduce the fuel by 1.
            find a new ore (results based on the modulus of a random integer)
            add the ore to the mining cart array list for the game singleton
            publish the progress(refreshes the in the main activity).

             */
            while(mPlayer.getFuel() > 0) {//while the fuel is above zero
                mPlayer.setFuel(mPlayer.getFuel() - 1);//deduct one from the fuel
                try {//try to create an item using a random modulus and referencing the items table
                    Item ore = mGame.findOre(sRandom.nextInt(), mDataSource.getItemsDirectory());
                    mGame.addOreToMiningCart(ore);//add the created ore to the mining cart
                    result = "Ores were found!";
                } catch (Exception e) {
                    e.printStackTrace();
                    result = "Failed to find ore...";
                }
                publishProgress();//show progress on screen
                try {
                    Thread.sleep(1000);//try sleep for a second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    result = "Something went wrong while mining...";
                }
                mDataSource.updateFuel(mPlayer.getFuel());
            }

            mDataSource.close();//close datasource

            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            mMiningCart.notifyDataSetChanged();
            mScrollToBottom.update(null);

        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            mDataSource.close();
        }

}
