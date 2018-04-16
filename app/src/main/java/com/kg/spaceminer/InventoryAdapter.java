package com.kg.spaceminer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kg.spaceminer.model.Inventory;
import com.kg.spaceminer.model.Item;
import com.kg.spaceminer.model.ItemDefinition;
import com.kg.spaceminer.sampledata.SampleItemDirectory;
import com.kg.spaceminer.tasks.SellTask;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>{

    private Context mContext;
    private HashMap<Integer, Inventory.Slot> mInventorySlots;
    private HashMap<String, ItemDefinition> mItemDirectory;

    public InventoryAdapter(HashMap<Integer, Inventory.Slot> mInventorySlots, HashMap<String, ItemDefinition> mItemDirectory, Context mContext) {
        this.mContext = mContext;
        this.mItemDirectory = mItemDirectory;
        this.mInventorySlots = mInventorySlots;
    }

    @NonNull
    @Override
    public InventoryAdapter.InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View slotView = inflater.inflate(R.layout.inventory_slot, parent, false);
        InventoryViewHolder viewHolder = new InventoryViewHolder(slotView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryAdapter.InventoryViewHolder holder, int position) {
        final Inventory.Slot slot = mInventorySlots.get(position);
        final String occupiedItemTypeId = slot.getOccupiedItemTypeId();
        final int slotSize = slot.getContents().size();

        //get the drawable
        String oreUri = "@drawable/" + occupiedItemTypeId;
        int imageResource = mContext.getResources().getIdentifier(oreUri, null, mContext.getPackageName());

        String sizeDisplay = "x " + slotSize;
        String valueDisplay = mItemDirectory.get(occupiedItemTypeId).getValueFormatted() + " / unit";

        holder.viewItemImage.setImageResource(imageResource);
        holder.viewItemName.setText(mItemDirectory.get(occupiedItemTypeId).getItemName());
        holder.viewItemValue.setText(valueDisplay);

        holder.viewItemAmount.setText(sizeDisplay);
        holder.buttonSell.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(!(slot.getOccupiedItemTypeId().equals("no_item")) && slotSize > 0){//if the slot isn't empty
                        SellTask sell = new SellTask(slot.getSlotId(), slotSize, InventoryAdapter.this, mContext);
                        sell.execute("sell");
                    }
                }
        });


    }

    @Override
    public int getItemCount() {
        return mInventorySlots.size();
    }

    public static class InventoryViewHolder extends RecyclerView.ViewHolder {
        public ImageView viewItemImage;
        public TextView viewItemAmount;
        public TextView viewItemName;
        public TextView viewItemValue;
        public Button buttonSell;
        public View mView;

        public InventoryViewHolder(View slotView) {
            super(slotView);
            viewItemImage = (ImageView) slotView.findViewById(R.id.viewItemImage);
            viewItemAmount = (TextView) slotView.findViewById(R.id.viewItemAmount);
            viewItemName = (TextView) slotView.findViewById(R.id.viewItemName);
            viewItemValue = (TextView) slotView.findViewById(R.id.viewItemValue);
            buttonSell = (Button) slotView.findViewById(R.id.buttonSell);
            mView = slotView;
        }
    }
}
