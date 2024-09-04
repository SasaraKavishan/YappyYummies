package com.example.yappyyummies;

import static com.example.yappyyummies.MainActivity.SHARED_PREF_EMAIL;
import static com.example.yappyyummies.MainActivity.SHARED_PREF_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Front_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

       SharedPreferences sharedPreferences =getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);


        String email = sharedPreferences.getString(SHARED_PREF_EMAIL, null);

        if (email != null) {
            Intent intent = new Intent(this, Welcome_Page.class);
            startActivity(intent);
            finish();
        }

    }
    public void onLoginButton (View view) {
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void onRegisterButton (View view) {
        Intent intent= new Intent(this,Register.class);
        startActivity(intent);
    }

}