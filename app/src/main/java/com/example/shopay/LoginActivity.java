package com.example.shopay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

    }

    public void loginfunc(View view) {

        Intent i=new Intent(getApplicationContext(),PaymentDetails.class);
        startActivity(i);
    }
}
