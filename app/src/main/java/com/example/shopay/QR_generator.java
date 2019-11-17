package com.example.shopay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QR_generator extends AppCompatActivity {

    EditText qr_code;
    Button btngenerate;
    ImageView qr_image;
    String text2Qr;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generator);


        qr_image = findViewById(R.id.qr_image);

        final int random = new Random().nextInt(61) + 20; // [0, 60] + 20 => [20, 80

//        btngenerate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                //text2Qr = qr_code.getText().toString().trim();

        db.collection("PaymentDetails").whereEqualTo("username", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                            try{
                                BitMatrix bitMatrix = multiFormatWriter.encode(task.getResult().getDocuments().get(0).get("details")+ " dbaesufh4siofhu4sidhfewiofheriuofuhfy7f67gi7c6egdh78iwuaehvsriuhf83fge4ufhdwiaoehcvzrsiuhfio3jdi:OSztr6767yu8i7", BarcodeFormat.QR_CODE,200,200);
                                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                qr_image.setImageBitmap(bitmap);
                            }
                            catch (WriterException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }
                });



//            }
//        });
    }
}
