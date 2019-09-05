package com.example.shopay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserMainMenu extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    TextView loggedin_user_email, main_menu;
    Button btnlogout, btnpay, btnaddpaymethod,btngivefeedback,btnpaymenthistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main_menu);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = mAuth.getCurrentUser();

        String a = mAuth.getCurrentUser().getEmail();

        loggedin_user_email = findViewById(R.id.loggedin_user_email);
        loggedin_user_email.setText("Welcome, "+a+"!");
        //getString(R.string.welcome_user, user.getEmail());
        btnaddpaymethod = findViewById(R.id.btnaddpaymethod);

        btnlogout = findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(this);
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserMainMenu.this, QR_generator.class));
            }
        });

        btnaddpaymethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserMainMenu.this, PaymentMethod.class));
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view == btnlogout)
        {
         mAuth.signOut();
         finish();
         startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
