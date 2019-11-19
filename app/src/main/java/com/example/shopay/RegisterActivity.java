package com.example.shopay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.shopay.ShopayClass.Userclass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity <FirebaseFirestore> extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private FirebaseAuth mAuth;
    com.google.firebase.firestore.FirebaseFirestore db = com.google.firebase.firestore.FirebaseFirestore.getInstance();
    private boolean isUserExist = false;
    private ArrayList<String> followings;
    Button btnaccount;
    EditText input_fullname, input_email, input_password, input_address, input_city, input_state, input_zip, input_number;
    TextView sign_up, text_hash;
    CheckBox text_terms;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);
        mAuth  = FirebaseAuth.getInstance();



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
        text_hash = findViewById(R.id.text_hash);

        btnaccount .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeMD5Hash(input_password.toString());
                isCheckEmail();
            }
        });


        followings = new ArrayList<String>();

    }

    private void addDatatoDatabase() throws Exception {

        String fullname = input_fullname.getText().toString();
        //String password = input_password.getText().toString();
        String username = input_email.getText().toString();
        String address = input_address.getText().toString();
        String city = input_city.getText().toString();
        String state = input_state.getText().toString();
        String zip = input_zip.getText().toString();
        String number = input_number.getText().toString();


        CollectionReference dbUserdata = db.collection("Userdata");
        Userclass userclass = new Userclass(fullname, username, address, city, state, zip, number);
        dbUserdata.add(userclass)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(RegisterActivity.this,"User Added Muthu",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean EmailValid(String email) {
        return email.contains("@");
    }

    private boolean PasswordValidity(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
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
        text_terms.setError(null);
        final String username = input_email.getText().toString().trim();
        final String password = input_password.getText().toString().trim();
        String fullname = input_fullname.getText().toString().trim();
        String number = input_number.getText().toString().trim();
        String address = input_address.getText().toString().trim();
        String state = input_state.getText().toString().trim();
        String city = input_city.getText().toString().trim();
        String zip = input_zip.getText().toString().trim();
        String hashPass = text_hash.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter your username/email", Toast.LENGTH_SHORT).show();
            return;
        } else if (!EmailValid(username)) {
            input_email.setError("Invalid username/email");
            return;
        }


        if(input_password.getText().toString().length()<8 &&!PasswordValidity(input_password.getText().toString())){
            input_password.setError("Password should be at least 8 characters long and include special characters");
        }
        /*else{
            System.out.println("Valid");
        }*/

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        /*else if (!PasswordValidity(password)) {
            input_password.setError("Weak password");
            return;
        }*/

        if (TextUtils.isEmpty(fullname)) {
            input_fullname.setError("Please enter your full name");
            //Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(number)) {
            input_number.setError("Please enter your contact number");
            //Toast.makeText(this, "Please enter your contact number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(address)) {
            input_address.setError("Please enter your Street address");
            //Toast.makeText(this, "Please enter your Street address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(state)) {
            input_state.setError("Please enter your state");
            //Toast.makeText(this, "Please enter your state", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(city)) {
            input_city.setError("Please enter your city");
            //Toast.makeText(this, "Please enter your city", Toast.LENGTH_SHORT).show();
            return;
        }



        if (TextUtils.isEmpty(zip)) {
            input_zip.setError("Please enter your zip code");
            //Toast.makeText(this, "Please enter your zip code", Toast.LENGTH_SHORT).show();
            return;
        } /*else {
            Toast.makeText(this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
        }*/

        if(!text_terms.isChecked()){
            text_terms.setError("CheckBox is unchecked");
            Toast.makeText(this, "Please accept the Privacy Policy to continue.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(username, password)

                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//
                                Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();

                                try {
                                    addDatatoDatabase();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                               // ClearAll();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Unable to Register, Please try again!", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

        }



    public void computeMD5Hash(String password){
        try{
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5Hash.append(h);
            }

            text_hash.setText(MD5Hash);
            text_hash.setVisibility(View.INVISIBLE);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
    public void isCheckEmail() {

        if(EmailValid(input_email.getText().toString())) {
            mAuth.fetchSignInMethodsForEmail(input_email.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            boolean check = !task.getResult().getSignInMethods().isEmpty();
                            if (!check) {
                                Registeruser();
                            } else {
                                input_email.setError("Email already exist, please enter a different email.");
                                //Toast.makeText(getApplicationContext(), "Email already exist, please try again.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }else{
            input_email.setError("Invalid email address");
        }
    }
    private void ClearAll(){
        input_email.setText("");
        input_password.setText("");
        input_number.setText("");
        input_fullname.setText("");
        input_address.setText("");
        input_state.setText("");
        input_city.setText("");
        input_zip.setText("");
        input_email.requestFocus();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}