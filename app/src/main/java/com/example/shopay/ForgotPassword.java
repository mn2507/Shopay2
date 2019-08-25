package com.example.shopay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    Button btnsendemail;
    TextView txtforgotpass;
    FirebaseAuth mAuth;
    EditText input_reset_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth = FirebaseAuth.getInstance();

    txtforgotpass = findViewById(R.id.txtforgotpass);
    btnsendemail = findViewById(R.id.btnsendemail);
    input_reset_email = findViewById(R.id.input_reset_email);

    btnsendemail.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            String userEmail = input_reset_email.getText().toString();

            if (TextUtils.isEmpty(userEmail))
            {
                Toast.makeText(ForgotPassword.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            }
            else
            {
                mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(ForgotPassword.this, "Please check your email for the link to reset your password", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                        }
                        else
                        {
                            String message = task.getException().getMessage();
                            Toast.makeText(ForgotPassword.this, "Unexpected error occurred: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }
    });
    }
}
