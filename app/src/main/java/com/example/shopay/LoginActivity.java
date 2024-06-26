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

import com.example.shopay.Retrofit.NodeJs;
import com.example.shopay.Retrofit.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DocumentReference db;
    private FirebaseFirestore firebaseFirestore;
    Button btnregister, btnlogin;
    EditText login_username, login_password;
    TextView txtforgotpass;
    private ProgressBar progressBar;
    private int i = 0;
    NodeJs myAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        com.google.firebase.firestore.FirebaseFirestore db = com.google.firebase.firestore.FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(NodeJs.class);



        btnregister = findViewById(R.id.btncrtaccount);
        txtforgotpass = findViewById(R.id.txtforgotpass);
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
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this, UserMainMenu.class));
                userLogin();
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


    }

    private void userLogin() {



        final String username = login_username.getText().toString().trim();
        String password = login_password.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {

            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        i = progressBar.getProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (i < 100) {
                    i += 1;

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        progressBar.setVisibility(View.VISIBLE);


        if (!login_username.getText().equals("") && !login_password.getText().equals("")) {
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.INVISIBLE);

                            if (task.isSuccessful()) {
                                //start the profile activity
                                finish();

                                Call<user> call = myAPI.gettoken(login_username.getText().toString());

                                call.enqueue(new Callback<user>() {
                                    @Override
                                    public void onResponse(Call<user> call, Response<user> response) {

                                        user user1=new user();


                                       user.setToken(response.body().getToken1());

                                       user.setEmail(login_username.getText().toString());

                                       String a = user.getToken();

                                        Toast.makeText(LoginActivity.this, ""+response.body().getStatus1(), Toast.LENGTH_LONG).show();


//                                        db.collection("Userdata").whereEqualTo("username",login_username.getText().toString())
//                                                .get()
//                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                        if (task.isSuccessful()) {
//
//
//                                                            user.setEmail( task.getResult().getDocuments().get(0).get("username").toString());
//                                                        } else {
//
//                                                        }
//                                                    }
//                                                });



                                    }

                                    @Override
                                    public void onFailure(Call<user> call, Throwable t) {

                                        Toast.makeText(LoginActivity.this, "Connection error : "+ t.getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                });


                                startActivity(new Intent(getApplicationContext(), UserMainMenu.class));

                                //it have to go to the respective user home page depending on the type of role
                            }else{
                                Toast.makeText(LoginActivity.this, "Wrong email/password", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

    }

}