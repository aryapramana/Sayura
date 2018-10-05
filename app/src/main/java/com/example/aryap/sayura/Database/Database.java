package com.example.aryap.sayura.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.aryap.sayura.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper{

    private static final String DB_NAME = "sayura_db.db";
    private static final int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public List<Order> getCart()   {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        String[] sqlSelect = {"productId", "productName", "quantity", "price", "discount"};
        String sqlTabel = "OrderDetails";

        sqLiteQueryBuilder.setTables(sqlTabel);

        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase,sqlSelect, null, null, null, null, null);

        final List<Order> result = new ArrayList<>();

        if(cursor.moveToFirst())    {
            do {
                result.add(new Order(cursor.getString(cursor.getColumnIndex("productId")),
                        cursor.getString(cursor.getColumnIndex("productName")),
                        cursor.getInt(cursor.getColumnIndex("quantity")),
                        cursor.getString(cursor.getColumnIndex("price")),
                        cursor.getString(cursor.getColumnIndex("discount"))
                ));

            }while (cursor.moveToNext());
        }

        return result;
    }

    public void addToCart(Order order)  {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetails(productId, productName, quantity, price, discount) " +
                "VALUES('%s', '%s', '%s', '%s', '%s');",
                order.getProductId(), order.getProductName(), order.getQuantity(), order.getPrice(), order.getDiscount());

        sqLiteDatabase.execSQL(query);
    }

    public void deleteCart()    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetails");
        sqLiteDatabase.execSQL(query);
    }
}
