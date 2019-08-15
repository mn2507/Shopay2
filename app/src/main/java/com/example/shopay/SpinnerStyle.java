package com.example.shopay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SpinnerStyle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_item);
        Spinner spinner = findViewById(R.id.spinner_expymth);

        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.expiry_month_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
    }
}
