package com.kg.spaceminer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kg.spaceminer.model.Item;

import java.util.ArrayList;

public class MiningCartAdapter extends RecyclerView.Adapter<MiningCartAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Item> mMiningCartOres;

    public MiningCartAdapter(ArrayList<Item> mMiningCartOres, Context mContext) {
        this.mContext = mContext;
        this.mMiningCartOres = mMiningCartOres;
    }

    @NonNull
    @Override
    public MiningCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View oreView = inflater.inflate(R.layout.ore, parent, false);
        ViewHolder viewHolder = new ViewHolder(oreView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MiningCartAdapter.ViewHolder holder, int position) {
        final Item ore = mMiningCartOres.get(position);

        //get the drawable
        String oreUri = "@drawable/" + ore.getItemTypeId();
        int imageResource = mContext.getResources().getIdentifier(oreUri, null, mContext.getPackageName());

        //set gui output
        holder.viewOreName.setText(ore.getItemName());
        holder.viewOreImage.setImageResource(imageResource);

    }

    @Override
    public int getItemCount() {
        return mMiningCartOres.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView viewOreName;
        public ImageView viewOreImage;
        public View mView;

        public ViewHolder(View oreView) {
            super(oreView);
            viewOreName = (TextView) oreView.findViewById(R.id.viewOreName);
            viewOreImage = (ImageView) oreView.findViewById(R.id.viewOreImage);
            mView = oreView;
        }
    }


    public void refreshOre(ArrayList<Item> mMiningCartOres) {
        this.mMiningCartOres = mMiningCartOres;
        notifyDataSetChanged();
    }
}
