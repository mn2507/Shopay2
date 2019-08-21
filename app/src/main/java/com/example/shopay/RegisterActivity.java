package com.example.shopay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.shopay.ShopayClass.Userclass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.auth.User;

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
    String fullname = input_fullname.getText().toString().trim();
    String password = input_password.getText().toString().trim();
    String username = input_email.getText().toString().trim();
    String address = input_address.getText().toString().trim();
    String city = input_city.getText().toString().trim();
    String state = input_state.getText().toString().trim();
    String zip = input_zip.getText().toString().trim();
    String number = input_number.getText().toString().trim();

    CollectionReference dbUserdata = db.collection("Userdata");
    Userclass userclass = new Userclass(fullname,password,username,address,city,state,zip,number);
    dbUserdata.add(userclass)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(RegisterActivity.this,"Not Register",Toast.LENGTH_LONG).show();
        }
    });
    }
    private boolean EmailValid(String email) {
        //check whether Email has "@"
        return email.contains("@");
    }
    private boolean PasswordValid(String password) {
        //check whether Password has minimum characters
        return password.length() > 8;
    }
    private void Registeruser() {

        input_email.setError(null);
        input_password.setError(null);
        input_fullname.setError(null);
        input_number.setError(null);
        input_address.setError(null);
        input_state.setError(null);
        input_city.setError(null);
        input_zip.setError(null);
        final String username = input_email.getText().toString().trim();
        final String password = input_email.getText().toString().trim();
        String fullname = input_fullname.getText().toString().trim();
        String number = input_number.getText().toString().trim();
        String address = input_address.getText().toString().trim();
        String state = input_state.getText().toString().trim();
        String city = input_city.getText().toString().trim();
        String zip = input_zip.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter your username/email", Toast.LENGTH_SHORT).show();
            return;
        }  else if (!PasswordValid(password)) {
            input_password.setError("Invalid username/email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        } else if (!PasswordValid(password)) {
            input_password.setError("Weak password");
            return;
        }

        if (TextUtils.isEmpty(fullname)) {
            Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "Please enter your contact number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please enter your address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(state)) {
            Toast.makeText(this, "Please enter your state", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(city)) {
            Toast.makeText(this, "Please enter your city", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(zip)) {
            Toast.makeText(this, "Please enter your zip code", Toast.LENGTH_SHORT).show();
            return;
        }
        else {Toast.makeText(this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
    }
        firebaseAuth.createUserWithEmailAndPassword(email, password)
    }
}

