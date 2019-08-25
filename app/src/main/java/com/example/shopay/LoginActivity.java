package com.example.shopay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private DocumentReference db;
    private FirebaseFirestore firebaseFirestore;
    Button btnregister, btnlogin;
    EditText login_username, login_password;
    TextView txtforgotpass;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        com.google.firebase.firestore.FirebaseFirestore db = com.google.firebase.firestore.FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), UserMainMenu.class));
            //profile activity here
        }

        btnregister = findViewById(R.id.btncrtaccount);
        btnlogin = findViewById(R.id.btnlogin);
        login_username = findViewById(R.id.login_username);
        login_password = findViewById(R.id.login_password);
        progressBar = new ProgressBar(this);
        CollectionReference dbUserdata = db.collection("Userdata");
        firebaseFirestore = FirebaseFirestore.getInstance();



        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        txtforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, UserMainMenu.class));

            }
        });

    }


    private void userLogin(){

        final String username = login_username.getText().toString().trim();
        String password = login_password.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {

            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;}

        progressBar.setVisibility(View.VISIBLE);



        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.INVISIBLE);

                        if (task.isSuccessful()) {
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), UserMainMenu.class));

                            //it have to go to the respective user home page depending on the type of role
                        }

                    }
                });
    }

}