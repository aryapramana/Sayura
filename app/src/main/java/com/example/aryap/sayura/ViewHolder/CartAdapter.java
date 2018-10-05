package com.example.aryap.sayura.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.aryap.sayura.Model.Order;
import com.example.aryap.sayura.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> orderList = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_layout, parent, false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        TextDrawable textDrawable = TextDrawable.builder()
                .buildRound(""+ orderList.get(position).getQuantity(), Color.RED);
        holder.imgCartItemCount.setImageDrawable(textDrawable);

        Locale locale = new Locale("id", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        int total_price = (Integer.parseInt(orderList.get(position).getPrice()))
                * (orderList.get(position).getQuantity());
        holder.txtCartItemPrice.setText(numberFormat.format(total_price));

        holder.txtCartItemName.setText(orderList.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
