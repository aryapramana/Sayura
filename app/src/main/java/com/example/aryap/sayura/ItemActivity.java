package com.example.aryap.sayura;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aryap.sayura.Interface.ItemClickListener;
import com.example.aryap.sayura.Model.Items;
import com.example.aryap.sayura.ViewHolder.ItemListViewHolder;
import com.example.aryap.sayura.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class ItemActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference table_items;

    private RecyclerView recyler_item;
    private RecyclerView.LayoutManager layoutManager;

    private String categoryId = "";

    private FirebaseRecyclerAdapter<Items, ItemListViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // firebase
        database = FirebaseDatabase.getInstance();
        table_items = database.getReference("Items");

        recyler_item = findViewById(R.id.recycler_item);
        recyler_item.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyler_item.setLayoutManager(layoutManager);

        // get extras from HomeActivity
        if(getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryId");
        }

        if(!categoryId.isEmpty()) {
            loadItemList(categoryId);
        }



    }

    private void loadItemList(String categoryId) {
        Query query = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Items")
                .orderByChild("menuId")
                .equalTo(categoryId);

        FirebaseRecyclerOptions<Items> options =
                new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(query, Items.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Items, ItemListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ItemListViewHolder holder, int position, @NonNull Items model) {
                holder.txtItemName.setText(model.getName());
                Picasso.get()
                        .load(model.getImage())
                        .into(holder.imageItemView);

                final Items clickItem = model;

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // Toast.makeText(ItemActivity.this, "" + clickItem.getName(), Toast.LENGTH_LONG).show();
                        Intent ItemDetailIntent = new Intent(ItemActivity.this, ItemDetailActivity.class);
                        ItemDetailIntent.putExtra("itemId", adapter.getRef(position).getKey());
                        startActivity(ItemDetailIntent);
                    }
                });
            }

            @NonNull
            @Override
            public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_itemlist, parent, false);

                return new ItemListViewHolder(view);
            }
        };

        recyler_item.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
