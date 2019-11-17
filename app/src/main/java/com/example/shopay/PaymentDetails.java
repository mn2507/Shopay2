package com.example.shopay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.shopay.Retrofit.NodeJs;
import com.example.shopay.Retrofit.RetrofitClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.base.Joiner;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaymentDetails extends AppCompatActivity {

    public static final String VISA_PREFIX = "4";
    public static final String MASTERCARD_PREFIX = "51,52,53,54,55,";
    public static final String NONE = "";

    private static final String VISA ="Visa";
    private static final String MASTERCARD ="Mastercard";
    private Stripe stripe;
    NodeJs myAPI;

    CardMultilineWidget cardWidget;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        cardWidget=findViewById(R.id.card_widget);

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(NodeJs.class);

//        PaymentConfiguration.init(this,"pk_test_Oxfl5PYBAAZCCpYAhOywJZh100N9gC1qVA");
//        stripe = new Stripe(this, PaymentConfiguration.getInstance(this).getPublishableKey());

        PaymentConfiguration.init("pk_test_Oxfl5PYBAAZCCpYAhOywJZh100N9gC1qVA");
        stripe = new Stripe(this,
                PaymentConfiguration.getInstance().getPublishableKey());

//        Spinner spinner =(Spinner)findViewById(R.id.spinner_expymth);
//        String[] month = {"Expiry Month","January","February","March","April","May","June","July","August","September","October","November","December"};
//        spinner.setAdapter(new SpinnerStyle(this, R.layout.spinner_item, month){
//        });
//        Spinner spinner2 =(Spinner)findViewById(R.id.spinner_expyyr);
//        String[] year = {"Expiry Year","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031"};
//        spinner2.setAdapter(new SpinnerStyle(this, R.layout.spinner_item, year){
//        });


    }
    public static String getCardType(String cardNumber) {

        if (cardNumber.substring(0, 1).equals(VISA_PREFIX))
            return VISA;
        else if (MASTERCARD_PREFIX.contains(cardNumber.substring(0, 2) + ","))
            return MASTERCARD;

        return NONE;
    }

    public void addcard(View view) {

        onCardSaved();

    }

    private void onCardSaved() {
        final Card cardToSave = cardWidget.getCard();

        if (!cardToSave.validateCard()) {
            Toast.makeText(PaymentDetails.this, "invalid payment details", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (cardToSave != null) {

                tokenizeCard(cardToSave);
            }
        }
    }

    private void tokenizeCard(@NonNull Card card) {

        stripe.createToken(
                card,
                new ApiResultCallback<Token>() {
                    @Override
                    public void onError(@NotNull Exception e) {
                        Toast.makeText(PaymentDetails.this, "Connection error", Toast.LENGTH_SHORT).show();
                    }

                    public void onSuccess(@NonNull Token token) {

                       //pass here token

                        Call<user> call = myAPI.adduser(user.getToken(),token.getId(),user.getEmail());

                        call.enqueue(new Callback<user>() {
                            @Override
                            public void onResponse(Call<user> call, Response<user> response) {

                                if (response.body().getStatus1().equals("Session timeout.")) {


                                    Toast.makeText(PaymentDetails.this, "" + response.body().getStatus1(), Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent i = new Intent(getApplicationContext(), UserMainMenu.class);
                                    startActivity(i);

                                }
                                else
                                {
                                    Toast.makeText(PaymentDetails.this, "" + response.body().getStatus1(), Toast.LENGTH_LONG).show();

                                    Map<String,Object> payment = new HashMap<>();
                                    payment.put("username",user.getEmail());
                                    payment.put("details",response.body().getCusid1());
                                    payment.put("last4",response.body().getLast41());
                                    String a=response.body().getLast41();

                                    db.collection("PaymentDetails")
                                            .add(payment)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                  //  Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                  //  Log.w(TAG, "Error adding document", e);
                                                }
                                            });

                                    Intent i = new Intent(getApplicationContext(), PaymentMethod.class);
                                    startActivity(i);
                                    finish();

                                }

                            }

                            @Override
                            public void onFailure(Call<user> call, Throwable t) {

                                Toast.makeText(PaymentDetails.this, "Connection error 12: "+ t, Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                }
        );
    }
}
