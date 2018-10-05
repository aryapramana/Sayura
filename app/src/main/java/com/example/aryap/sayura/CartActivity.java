package com.example.aryap.sayura;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aryap.sayura.Common.Common;
import com.example.aryap.sayura.Database.Database;
import com.example.aryap.sayura.Model.Order;
import com.example.aryap.sayura.Model.Request;
import com.example.aryap.sayura.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference request_order;

    private RecyclerView recycler_cart;
    private RecyclerView.LayoutManager layoutManager;

    private TextView txtGrandTotal;
    private Button btnPlaceOrder;

    private List<Order> cart;

    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // firebase
        database = FirebaseDatabase.getInstance();
        request_order = database.getReference("Requests");

        // list
        cart = new ArrayList<>();

        // initialise
        recycler_cart = findViewById(R.id.recycler_cart);
        recycler_cart.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_cart.setLayoutManager(layoutManager);

        txtGrandTotal = findViewById(R.id.txtGrandTotal);

        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add new Request
                if(recycler_cart != null) {
                    showAlertDialog();
                } else {
                    Toast.makeText(CartActivity.this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                } // not tested - part 6 view order
            }
        });

        loadListItem();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your address");

        final EditText editTxtAddress = new EditText(CartActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT,
          LinearLayout.LayoutParams.MATCH_PARENT
        );

        editTxtAddress.setLayoutParams(layoutParams);
        alertDialog.setView(editTxtAddress); // add EditText to dialog
        alertDialog.setIcon(R.drawable.ic_shopping_cart_white_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(
                        Common.current_user.getPhone(),
                        Common.current_user.getName(),
                        editTxtAddress.getText().toString(),
                        txtGrandTotal.getText().toString(),
                        cart
                );

                // send to firebase
                // use System.CurrentMili
                request_order.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);

                // delete cart after submit to firebase
                new Database(getBaseContext()).deleteCart();

                // alert after order confirmation
                Toast.makeText(CartActivity.this, "Order submitted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void loadListItem() {
        cart = new Database(this).getCart();
        adapter = new CartAdapter(cart, this);
        recycler_cart.setAdapter(adapter);

        // count grand total in cart
        int grand_total = 0;
        for(Order order:cart)   {
            grand_total += (Integer.parseInt(order.getPrice())) *
                    (order.getQuantity());
        }
        Locale locale = new Locale("id", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        txtGrandTotal.setText(numberFormat.format(grand_total));
    }
}
