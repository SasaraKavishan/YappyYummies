package com.example.yappyyummies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase database;
    public EditText emailInput;
    public EditText passwordInput;
    Button button;

    public SharedPreferences sharedPreferences;

    public static final String SHARED_PREF_NAME = "mypref";
    public static final String SHARED_PREF_EMAIL = "email";
    public static final String SHARED_PREF_USER_ID = "user_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=new SqlDatabaseHelper(this).getReadableDatabase();

        emailInput=findViewById(R.id.LoginEmail);
        passwordInput=findViewById(R.id.LoginPassword);
        button=findViewById(R.id.OnLoginButton);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String email = sharedPreferences.getString(SHARED_PREF_EMAIL, null);

        if (email != null) {
            Intent intent = new Intent(MainActivity.this, Welcome_Page.class);
            startActivity(intent);
        }

    }

    public void loginButton(View view) {


        if (emailInput.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();


        }
        else if (passwordInput.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        }
        else {
            Cursor cursor = database.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{emailInput.getText().toString(), passwordInput.getText().toString()});
            boolean result = (cursor != null && cursor.moveToFirst());

            if (result){
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                sharedPreferences.edit().putString(SHARED_PREF_EMAIL, emailInput.getText().toString()).putInt(SHARED_PREF_USER_ID, userId)
                .apply();

                Intent intent = new Intent(this, Welcome_Page.class);
                startActivity(intent);

                Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Email or Password Incorrect", Toast.LENGTH_SHORT).show();
            }
            if (cursor != null) {
                cursor.close();
            }

        }

    }
}