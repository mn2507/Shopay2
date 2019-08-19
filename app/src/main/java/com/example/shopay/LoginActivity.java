package com.example.shopay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
Button btnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        btnregister =findViewById(R.id.btncrtaccount);
        //leaving space coz here only u will be calling all the variable u created up there then u will call ur xml file

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class ));
            }
        });






    }


    public void loginfunc(View view) {

        Intent i=new Intent(getApplicationContext(),PaymentDetails.class);
        startActivity(i);
    }}



