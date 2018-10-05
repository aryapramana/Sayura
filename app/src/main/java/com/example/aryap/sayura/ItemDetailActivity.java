package com.example.aryap.sayura;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzaitsev.meternumberpicker.MeterNumberPicker;
import com.alexzaitsev.meternumberpicker.MeterView;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.aryap.sayura.Database.Database;
import com.example.aryap.sayura.Model.Items;
import com.example.aryap.sayura.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

public class ItemDetailActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference item_detail;

    private TextView itemDetailName;
    private TextView itemDetailDescription;
    private TextView itemDetailPrice;

    private ImageView itemDetailImage;

    private CollapsingToolbarLayout collapsingToolbarLayout;

    private FloatingActionButton btnCart;

    // private ElegantNumberButton numberButton;
    // private MaterialNumberPicker number_counter;
    private MeterView meterView;

    private String itemId="";

    private Items current_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // firebase
        database = FirebaseDatabase.getInstance();
        item_detail = database.getReference("Items");

        // view
        itemDetailName = findViewById(R.id.itemDetailName);
        itemDetailDescription = findViewById(R.id.itemDetailDescription);
        itemDetailImage = findViewById(R.id.itemDetailImage);
        itemDetailPrice = findViewById(R.id.itemDetailPrice);

        btnCart = findViewById(R.id.btnCartDetail);

        // numberButton = findViewById(R.id.number_counter);
        // number_counter = findViewById(R.id.number_counter);
        meterView = findViewById(R.id.meterView);
        meterView.setValue(1);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleColor(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppBar);

        // get extras from intent
        if(getIntent() != null) {
            itemId = getIntent().getStringExtra("itemId");
        }

        if(!itemId.isEmpty()) {
            loadItemDetail(itemId);
        }

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        itemId,
                        current_item.getName(),
                        meterView.getValue(),
                        current_item.getPrice(),
                        current_item.getDiscount()
                ));

                Toast.makeText(ItemDetailActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadItemDetail(String itemId) {
        item_detail.child(itemId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                current_item = dataSnapshot.getValue(Items.class);

                // load data
                Picasso.get()
                        .load(current_item.getImage())
                        .into(itemDetailImage);

                collapsingToolbarLayout.setTitle(current_item.getName());

                itemDetailPrice.setText(current_item.getPrice());

                itemDetailDescription.setText(current_item.getDescription());

                itemDetailName.setText(current_item.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
