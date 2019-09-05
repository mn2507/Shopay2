package com.example.shopay;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QR_generator extends AppCompatActivity {

    EditText qr_code;
    Button btngenerate;
    ImageView qr_image;
    String text2Qr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generator);

        qr_code = findViewById(R.id.qr_code);
        btngenerate = findViewById(R.id.btngenerate);
        qr_image = findViewById(R.id.qr_image);
        btngenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2Qr = qr_code.getText().toString().trim();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    qr_image.setImageBitmap(bitmap);
                }
                catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
