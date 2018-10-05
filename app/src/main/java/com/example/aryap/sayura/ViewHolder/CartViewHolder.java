package com.example.aryap.sayura.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aryap.sayura.Interface.ItemClickListener;
import com.example.aryap.sayura.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtCartItemName;
    public TextView txtCartItemPrice;

    public ImageView imgCartItemCount;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        txtCartItemName = itemView.findViewById(R.id.txtCartItemName);
        txtCartItemPrice = itemView.findViewById(R.id.txtCartItemPrice);

        imgCartItemCount = itemView.findViewById(R.id.imgCartItemCount);
    }

    @Override
    public void onClick(View v) {

    }
}

