package com.example.yappyyummies;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Products_Page extends  AppCompatActivity {

    ArrayList<ProductModel> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_page);

        RecyclerView recyclerView = findViewById(R.id.PRecycleView);

        setUpproducts();

        P_RecycleViewAdapter adapter =new P_RecycleViewAdapter(this, products);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) );
    }


    private void setUpproducts(){
        SQLiteOpenHelper databaseHelper = new SqlDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM products",null);

        cursor.moveToFirst();
        for (int i = 0;i<cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            ProductModel product = new ProductModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow("product_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("brand")),
                    cursor.getString(cursor.getColumnIndexOrThrow("type")),
                    cursor.getString(cursor.getColumnIndexOrThrow("age")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                    cursor.getString(cursor.getColumnIndexOrThrow("image_url"))
            );
            products.add(product);
        }

    }
}

