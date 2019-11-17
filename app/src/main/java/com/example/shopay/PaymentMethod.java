package com.example.shopay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopay.Retrofit.NodeJs;
import com.example.shopay.Retrofit.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PaymentMethod extends AppCompatActivity {

    NodeJs myAPI;
    TextView last4;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(NodeJs.class);

        last4=findViewById(R.id.last4);

        db.collection("PaymentDetails").whereEqualTo("username", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            last4.setText(task.getResult().getDocuments().get(0).get("last4").toString());

                        }
                    }
                });

    }

    public void addFunc(View view) {
        startActivity(new Intent(PaymentMethod.this, PaymentDetails.class));
    }

    public void removecard(View view) {

        db.collection("PaymentDetails").whereEqualTo("username", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Call<user> call = myAPI.removecard(user.getToken(), task.getResult().getDocuments().get(0).get("details").toString());
                            final String id = task.getResult().getDocuments().get(0).getId();
                            call.enqueue(new Callback<user>() {
                                @Override
                                public void onResponse(Call<user> call, Response<user> response) {

                                    if (response.body().getStatus1().equals("Session timeout.")) {


                                        Toast.makeText(PaymentMethod.this, "" + response.body().getStatus1(), Toast.LENGTH_SHORT).show();
                                        finish();
                                        Intent i = new Intent(getApplicationContext(), UserMainMenu.class);
                                        startActivity(i);

                                    } else {
                                        Toast.makeText(PaymentMethod.this, "" + response.body().getStatus1(), Toast.LENGTH_LONG).show();

                                        db.collection("PaymentDetails").document(id)
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void documentReference) {
                                                        Log.d("Delete", "Document Deleted");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("Delete", "Error adding document", e);
                                                    }
                                                });


                                        Intent i = new Intent(getApplicationContext(), PaymentMethod.class);
                                        startActivity(i);
                                        finish();

                                    }
                                }

                                @Override
                                public void onFailure(Call<user> call, Throwable t) {

                                    Toast.makeText(PaymentMethod.this, "Connection error", Toast.LENGTH_LONG).show();

                                }
                            });
                        } else {
                            Toast.makeText(PaymentMethod.this, "No card is stored", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }
}
