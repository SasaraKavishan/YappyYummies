package com.example.yappyyummies;

import static com.example.yappyyummies.MainActivity.SHARED_PREF_NAME;
import static com.example.yappyyummies.MainActivity.SHARED_PREF_USER_ID;

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

public class P_RecycleViewAdapter extends RecyclerView.Adapter<P_RecycleViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<ProductModel> products;
    SqlDatabaseHelper databaseHelper;
    SQLiteDatabase db;



    public P_RecycleViewAdapter (Context context, ArrayList<ProductModel> productModel){
        this.context = context;
        this.products = productModel;
        this.databaseHelper = new SqlDatabaseHelper(context);
        this.db = databaseHelper.getWritableDatabase();

    }

    @NonNull
    @Override
    public P_RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row,parent,false);
        return new P_RecycleViewAdapter.MyViewHolder(view);
    }

    public void updateProductList(ArrayList<ProductModel> productList) {
        this.products = productList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull P_RecycleViewAdapter.MyViewHolder holder, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    ProductModel product = products.get(position);
        holder.tvName.setText(product.getName());
        holder.tvBrand.setText(product.getBrand());
        holder.tvType.setText(product.getType());
        holder.tvAge.setText(product.getAge());
        holder.tvPrice.setText(String.valueOf(product.getPrice()));
        Glide.with(context).load(product.getImage()).into(holder.imageView);

        holder.addtocart.setOnClickListener(view -> {
            db.execSQL("INSERT INTO Cart (user_id, product_id, quantity) VALUES (?,?,?)",
                    new String[]{
                            String.valueOf(sharedPreferences.getInt(SHARED_PREF_USER_ID, 0)),
                            String.valueOf(product.getId()),
                            "1"

                    });
            Toast.makeText(context, "ADD CART SUCCESS", Toast.LENGTH_SHORT).show();
                    });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tvBrand,tvType,tvAge,tvPrice, tvName;
        Button addtocart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.Name1);
            imageView = itemView.findViewById(R.id.imageView10);
            tvBrand = itemView.findViewById(R.id.Brand1);
            tvType = itemView.findViewById(R.id.Type1);
            tvAge = itemView.findViewById(R.id.Age1);
            tvPrice = itemView.findViewById(R.id.Price1);
            addtocart = itemView.findViewById(R.id.addtocart);
        }
    }


}
