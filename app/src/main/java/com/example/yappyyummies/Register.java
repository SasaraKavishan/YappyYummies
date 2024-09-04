package com.example.yappyyummies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    SQLiteDatabase database;
    public EditText firstName ;
    public EditText lastName;
    public EditText email;
    public EditText address;
    public EditText phoneNumber;
    public EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        database= new SqlDatabaseHelper(this).getReadableDatabase();

        firstName=findViewById(R.id.FName);
        lastName=findViewById(R.id.LName);
        email=findViewById(R.id.REmail);
        address=findViewById(R.id.RAddress);
        phoneNumber=findViewById(R.id.PNumber);
        password=findViewById(R.id.RPassword);

    }

    public void registerButton(View view) {
        if (firstName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter your First Name", Toast.LENGTH_SHORT).show();
        }
        if (lastName.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter your Last Name", Toast.LENGTH_SHORT).show();
        }


        if (email.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter your Email", Toast.LENGTH_SHORT).show();
        }

        if (address.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter your Address", Toast.LENGTH_SHORT).show();
        }

        if (phoneNumber.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter your Phone Number", Toast.LENGTH_SHORT).show();
        }

        else if (password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter your Password", Toast.LENGTH_SHORT).show();

        }
        else {
            ContentValues values = new ContentValues();
            values.put("first_name", firstName.getText().toString());
            values.put("last_name", lastName.getText().toString());
            values.put("email", email.getText().toString());
            values.put("address",address.getText().toString());
            values.put("phone_number",phoneNumber.getText().toString());
            values.put("password", password.getText().toString());

             database.insert("users", null, values);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
        }
    }


}