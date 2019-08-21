package com.example.shopay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

public class RegisterActivity <FirebaseFirestore> extends AppCompatActivity {
    com.google.firebase.firestore.FirebaseFirestore db = com.google.firebase.firestore.FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private ArrayList<String> followings;
    Button btnaccount;
    EditText input_fullname, input_email, input_password, input_address, input_city, input_state, input_zip, input_number;
    TextView sign_up;
    CheckBox text_terms;



    public RegisterActivity(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);

        btnaccount = findViewById(R.id.btnaccount);
        input_fullname = findViewById(R.id.input_fullname);
        input_address = findViewById(R.id.input_address);
        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        input_city = findViewById(R.id.input_city);
        input_state = findViewById(R.id.input_state);
        input_zip = findViewById(R.id.input_zip);
        input_number = findViewById(R.id.input_number);
        sign_up = findViewById(R.id.sign_up);
        text_terms = findViewById(R.id.text_terms);
        btnaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void infoDatabase() throws Exception {
    String username = input_fullname.getText().toString().trim();
    String password = input_password.getText().toString().trim();
    String email = input_email.getText().toString().trim();
    String address = input_address.getText().toString().trim();
    String city = input_city.getText().toString().trim();
    String state = input_state.getText().toString().trim();
    String zip = input_zip.getText().toString().trim();
    String number = input_number.getText().toString().trim();

    CollectionReference dbUsers = db.collection
    }

    }

