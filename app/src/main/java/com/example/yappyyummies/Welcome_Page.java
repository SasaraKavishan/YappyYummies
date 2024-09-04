package com.example.yappyyummies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Welcome_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);


    }

    public void productButton(View view){
        Intent intent = new Intent(this, Products_Page.class);
        startActivity(intent);

    }
    public void profileButton(View view){
        Intent intent = new Intent(this, User_Profile.class);
        startActivity(intent);
    }
    public void searchButton(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void cartButton(View view){
        Intent intent = new Intent(this, Cart.class);
        startActivity(intent);
    }

    public void eduContentButton(View view){
        Intent intent = new Intent(this, Education.class);
        startActivity(intent);
    }

}