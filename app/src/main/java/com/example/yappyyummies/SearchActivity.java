package com.example.yappyyummies;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchBar;
    private RecyclerView searchRecyclerView;
    ArrayList<ProductModel> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchBar = findViewById(R.id.search_bar);
        searchRecyclerView = findViewById(R.id.product_recycler_view);

        products = new ArrayList<>();
        SQLiteDatabase database = new SqlDatabaseHelper(this).getReadableDatabase();
        Cursor cursor = database.query("Products", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
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
            } while (cursor.moveToNext());

            cursor.close();
        }

        P_RecycleViewAdapter adapter = new P_RecycleViewAdapter(this,products);
        searchRecyclerView.setAdapter(adapter);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor cursor = database.rawQuery("SELECT * FROM Products WHERE name LIKE ?", new String[]{"%" + newText + "%"});

                products = new ArrayList<>();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
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
                    } while (cursor.moveToNext());

                    cursor.close();
                }
                adapter.updateProductList(products);
                return false;
            }
        });
    }
}