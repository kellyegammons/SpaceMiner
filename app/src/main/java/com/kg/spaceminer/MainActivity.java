package com.kg.spaceminer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kg.spaceminer.database.DataSource;
import com.kg.spaceminer.model.*;
import com.kg.spaceminer.tasks.CollectTask;
import com.kg.spaceminer.tasks.MineTask;
import com.kg.spaceminer.tasks.RefuelTask;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private IProcess mScrollToBottom;
    private IProcess mToastResult;
    SpaceMiner mGame = SpaceMiner.getInstance();
    Player mPlayer = Player.getInstance();
    Inventory inventory = Inventory.getInstance();

    //data
    DataSource mDataSource;

    //gui
    RecyclerView viewMiner;
    Button buttonMine;
    Button buttonRefuel;
    Button buttonCollect;
    Button buttonInventory;
    TextView viewCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //data
        mDataSource = new DataSource(MainActivity.this);
        mDataSource.open();
        inventory.setmSlots(mDataSource.getInventory());
        mPlayer.setFuel(mDataSource.getFuel());
        mPlayer.setCredits(mDataSource.getCredits());

        //linear layout manager for recycler views
        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        linearManager.setStackFromEnd(false);

        //miner adapter
        final MiningCartAdapter miningCart = new MiningCartAdapter(mGame.getMiningCartOres(), MainActivity.this);
        viewMiner = (RecyclerView) findViewById(R.id.miner);
        viewMiner.setLayoutManager(linearManager);
        viewMiner.setAdapter(miningCart);

        buttonMine = (Button) findViewById(R.id.buttonMine);
        buttonRefuel = (Button) findViewById(R.id.buttonRefuel);
        buttonCollect = (Button) findViewById(R.id.buttonCollect);
        buttonInventory = (Button) findViewById(R.id.buttonInventory);

        //async callbacks
        mScrollToBottom = new IProcess() {

            @Override
            public void update(String pException) {
                viewMiner.smoothScrollToPosition(miningCart.getItemCount() - 1);
            }
        };

        mToastResult = new IProcess() {

            @Override
            public void update(String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        };

        viewCredits = (TextView) findViewById(R.id.viewCredits);
        viewCredits.setText(mPlayer.getCreditsFormatted());

        //button listeners
        buttonMine.setOnClickListener(//Mine
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mGame.getMiningCartOres().size() == 0){
                            if(mPlayer.getFuel() > 0 && mGame.getMiningCartOres().size() == 0) {
                                MineTask mine = new MineTask(miningCart, mScrollToBottom, MainActivity.this);
                                mine.execute("mine");
                            } else {
                                Toast.makeText(MainActivity.this, "You are out of fuel", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Clean out the mining cart first! Sell items to make inventory space.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        buttonRefuel.setOnClickListener(//Refuel
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mPlayer.getFuel() == 0) {
                            RefuelTask refuel = new RefuelTask(mToastResult, MainActivity.this);
                            refuel.execute("refuel");
                        } else {
                            Toast.makeText(MainActivity.this, "Fuel is already full!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        buttonCollect.setOnClickListener(//Collect ore from mining cart
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mGame.getMiningCartOres().size() != 0){
                            CollectTask collect = new CollectTask(miningCart, mToastResult, MainActivity.this);
                            collect.execute("collect");
                        }

                    }
                }
        );

        buttonInventory.setOnClickListener(//go to inventory
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, InventoryActivity.class);
                        MainActivity.this.startActivity(intent);
                    }
                }
        );

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
