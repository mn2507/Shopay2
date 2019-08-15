package com.example.shopay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class PaymentDetails extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);


        Spinner spinner =(Spinner)findViewById(R.id.spinner_expymth);
        String[] years = {"1996","1997","1998","1998"};
        spinner.setAdapter(new SpinnerStyle(this, R.layout.spinner_item, years) {
        });
    }
}
