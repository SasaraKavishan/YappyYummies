package com.example.yappyyummies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class User_Profile extends AppCompatActivity {
    TextView firstNameInput;
    TextView emailInput;
    TextView addressInput;
    TextView phoneInput;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        SQLiteOpenHelper databaseHelper = new SqlDatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE);

        int userId =sharedPreferences.getInt(MainActivity.SHARED_PREF_USER_ID,0);

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE user_id=?", new String[]{String.valueOf(userId)});
        cursor.moveToFirst();

        emailInput=findViewById(R.id.UEmailUPEnter);
        emailInput.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));

        firstNameInput=findViewById(R.id.UNameUPEnter);
        firstNameInput.setText(cursor.getString(cursor.getColumnIndexOrThrow("first_name")));

        addressInput=findViewById(R.id.UAddressUPEnter);
        addressInput.setText(cursor.getString(cursor.getColumnIndexOrThrow("address")));

        phoneInput = findViewById(R.id.UPNumberUPEnter);
        phoneInput.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone_number")));

        cursor.close();
    }

    public void updateProfile(View view) {
        String firstName = firstNameInput.getText().toString();
        String email = emailInput.getText().toString();
        String address = addressInput.getText().toString();
        String phone = phoneInput.getText().toString();

        ContentValues values = new ContentValues();
        values.put("first_name", firstName);
        values.put("email", email);
        values.put("address", address);
        values.put("phone_number", phone);

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE);
        int userId = sharedPreferences.getInt(MainActivity.SHARED_PREF_USER_ID, 0);
       db.update("users", values, "user_id=?", new String[]{String.valueOf(userId)});
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }

    public void logout(View view){
        SharedPreferences preferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE);
        preferences.edit().clear().apply();
        Intent intent = new Intent(this, Front_Page.class);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
    }

    public void updatePaymentMethods(View view){
        Intent intent = new Intent(this, PaymentMethod.class);
        startActivity(intent);


    }
}