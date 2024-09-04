package com.example.yappyyummies;

import static com.example.yappyyummies.MainActivity.SHARED_PREF_NAME;
import static com.example.yappyyummies.MainActivity.SHARED_PREF_USER_ID;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class C_RecycleViewAdapter extends RecyclerView.Adapter<C_RecycleViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<CartModel> cartItems;
    SqlDatabaseHelper databaseHelper;
    SQLiteDatabase db;



    public C_RecycleViewAdapter (Context context, ArrayList<CartModel> cartModel){
        this.context = context;
        this.cartItems = cartModel;
        this.databaseHelper = new SqlDatabaseHelper(context);
        this.db = databaseHelper.getWritableDatabase();

    }

    @NonNull
    @Override
    public C_RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_recycle_view,parent,false);
        return new C_RecycleViewAdapter.MyViewHolder(view);
    }

    public void updateProductList(ArrayList<CartModel> productList) {
        this.cartItems = productList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull C_RecycleViewAdapter.MyViewHolder holder, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        CartModel cartItem = cartItems.get(position);
        ProductModel product = getProduct(cartItem.getProductId());
        holder.CIName.setText(product.getName());
        holder.CIPrice.setText(String.valueOf(product.getPrice()));
//       holder.Cquantity.setText(cartItem.getQuantity());
        Glide.with(context).load(product.getImage()).into(holder.CIimageView);

        holder.delete.setOnClickListener(view -> {
            db.delete("Cart", "cart_id = ?", new String[]{String.valueOf(cartItem.getCartId())});
           Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        });
        holder.addQuantity.setOnClickListener(view -> {
            ContentValues values = new ContentValues();
            values.put("quantity", cartItem.getQuantity() + 1);
            db.update("Cart", values, "cart_id = ?", new String[]{String.valueOf(cartItem.getCartId())});

        });
        holder.removeQuantity.setOnClickListener(view -> {
            ContentValues values = new ContentValues();
            values.put("quantity", cartItem.getQuantity() - 1);
            db.update("Cart", values, "cart_id = ?", new String[]{String.valueOf(cartItem.getCartId())});

        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView CIimageView;
        TextView CIPrice, CIName ,Cquantity;
        Button delete  ;
        Button addQuantity;
        Button removeQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            CIName = itemView.findViewById(R.id.cartN1);
            CIimageView = itemView.findViewById(R.id.imageView);
            CIPrice = itemView.findViewById(R.id.cartPrice1);
            Cquantity = itemView.findViewById(R.id.Cquantity1);
            delete = itemView.findViewById(R.id.delete1);
            addQuantity = itemView.findViewById(R.id.addQuantity1);
            removeQuantity = itemView.findViewById(R.id.removeQuantity1);
        }
    }

    private ProductModel getProduct(int id){
        SQLiteOpenHelper databaseHelper = new SqlDatabaseHelper(context);
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
