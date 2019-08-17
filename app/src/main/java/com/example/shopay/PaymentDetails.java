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
        String[] month = {"Expiry Month","January","February","March","April","May","June","July","August","September","October","November","December"};
        spinner.setAdapter(new SpinnerStyle(this, R.layout.spinner_item, month){
        });
        Spinner spinner2 =(Spinner)findViewById(R.id.spinner_expyyr);
        String[] year = {"Expiry Year","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031"};
        spinner2.setAdapter(new SpinnerStyle(this, R.layout.spinner_item, year){
        });
        //hiiiiiiiiiiiiiiii
        //jack was here

    }
}
