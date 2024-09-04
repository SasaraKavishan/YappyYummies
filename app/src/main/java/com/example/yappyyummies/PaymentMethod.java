package com.example.yappyyummies;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class PaymentMethod extends AppCompatActivity {


    TextView cardHolderNameInput;
    TextView cardnumberInput;
    TextView ExpiryDateInput;
    TextView CVVInput;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_method);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cardHolderNameInput=findViewById(R.id.EnterCardHoderName);
        cardnumberInput=findViewById(R.id.EnterCardNumber);
        ExpiryDateInput=findViewById(R.id.EnterExpiration);
        CVVInput=findViewById(R.id.EnterCVN);

        SQLiteOpenHelper databaseHelper = new SqlDatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();
    }
    public void updatePaymentMethod(View view){
      String cardHolderName =cardHolderNameInput.getText().toString();
      String cardNumber = cardnumberInput.getText().toString();
      String ExpiryDate = ExpiryDateInput.getText().toString();
      String CVV = CVVInput.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE);
        int userId = sharedPreferences.getInt(MainActivity.SHARED_PREF_USER_ID, 0);

        ContentValues values = new ContentValues();
        values.put("cardholder_name", cardHolderName);
        values.put("card_number", cardNumber);
        values.put("expiry_date", ExpiryDate);
        values.put("CVV", CVV);

        Cursor cursor = db.query("PaymentMethods", new String[]{"id"}, "user_id = ?", new String[]{String.valueOf(userId)}, null, null, null);
        if(cursor.moveToFirst()){
            db.update("PaymentMethods", values, "user_id = ?", new String[]{String.valueOf(userId)});
        }else {

        values.put("user_id", userId);

        db.insert("PaymentMethods", null, values);
        }
        cursor.close();
        Toast.makeText(this, "Payment method updated successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, User_Profile.class);
        startActivity(intent);
    }



}