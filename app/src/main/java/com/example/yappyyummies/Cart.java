package com.example.yappyyummies;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    ArrayList<CartModel> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.CrecyclerView);
        TextView textView = findViewById(R.id.Totale1);
        TextView textView1 = findViewById(R.id.CountOfItems1);

        getCartItems();
        double totalAmount = 0;
        for(CartModel item:cartItems){
            ProductModel product = getProduct(item.getProductId());
            totalAmount = totalAmount + (product.getPrice()*item.getQuantity());
        }
        textView.setText(String.valueOf(totalAmount));
        textView1.setText(String.valueOf(cartItems.size()));

        C_RecycleViewAdapter adapter =new C_RecycleViewAdapter(this, cartItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) );


    }
    public void confirmOnClick(View view){
        SQLiteOpenHelper databaseHelper = new SqlDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

            db.delete("Cart","user_id=?",new String[]{String.valueOf(getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE).getInt(MainActivity.SHARED_PREF_USER_ID,0))});
        double totalAmount = 0;
        for(CartModel item:cartItems){
            ProductModel product = getProduct(item.getProductId());
            totalAmount = totalAmount + (product.getPrice()*item.getQuantity());
        }

            ContentValues values = new ContentValues();
            values.put("user_id",getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE).getInt(MainActivity.SHARED_PREF_USER_ID,0));
            values.put("total_amount",totalAmount);
            values.put("status","PENDING");
            long id = db.insert("Orders",null,values);
        for(CartModel item:cartItems){
            ProductModel product = getProduct(item.getProductId());
            ContentValues orderItemData = new ContentValues();
            orderItemData.put("order_id",id);
            orderItemData.put("product_id",item.getProductId());
            orderItemData.put("quantity",item.getQuantity());
            orderItemData.put("price",product.getPrice());
            db.insert("OrderItems",null,orderItemData);
        }
        Toast.makeText(this,"Order Placed",Toast.LENGTH_SHORT).show();
    }


    private void getCartItems() {
        SQLiteOpenHelper databaseHelper = new SqlDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Cart",null);

        cursor.moveToFirst();
        for (int i = 0;i<cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            CartModel product = new CartModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow("cart_id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("product_id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("quantity"))
            );
            cartItems.add(product);
        }


    }


    private ProductModel getProduct(int id){
        SQLiteOpenHelper databaseHelper = new SqlDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor cursor = db.query("Products", null, "product_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();


        ProductModel product = new ProductModel(
                cursor.getInt(cursor.getColumnIndexOrThrow("product_id")),
                cursor.getString(cursor.getColumnIndexOrThrow("name")),
                cursor.getString(cursor.getColumnIndexOrThrow("brand")),
                cursor.getString(cursor.getColumnIndexOrThrow("type")),
                cursor.getString(cursor.getColumnIndexOrThrow("age")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                cursor.getString(cursor.getColumnIndexOrThrow("image_url"))
        );

        cursor.close();
        return product;

    }
}