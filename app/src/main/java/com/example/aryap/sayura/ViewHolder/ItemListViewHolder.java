package com.example.aryap.sayura.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aryap.sayura.Interface.ItemClickListener;
import com.example.aryap.sayura.Model.Items;
import com.example.aryap.sayura.R;

public class ItemListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtItemName;
    public ImageView imageItemView;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ItemListViewHolder(View itemView) {
        super(itemView);

        txtItemName = itemView.findViewById(R.id.item_name);
        imageItemView = itemView.findViewById(R.id.item_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
