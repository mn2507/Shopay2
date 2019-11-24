package com.example.shopay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PaymentHistory extends AppCompatActivity {

    ListView mListView;
    ArrayAdapter<history> arrayAdapter;
    DocumentReference documentReference;
    private static final String TAG = "MyActivity";
    RecyclerView recyclerView;
    DataAdapter dataAdapter;
    List<history> histories;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        histories=new ArrayList<>();

        dataAdapter= new DataAdapter(histories);

        recyclerView = findViewById(R.id.listView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dataAdapter);

         arrayAdapter = new ArrayAdapter<history>(this,android.R.layout.simple_list_item_1);

//        final ListView mListView = (ListView) findViewById(R.id.listView);

         db = FirebaseFirestore.getInstance();


        documentReference=db.collection("history").document();

        db.collection("history").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange d: queryDocumentSnapshots.getDocumentChanges()) {
                    String email;
                    String price;

                    history history1=d.getDocument().toObject(com.example.shopay.history.class);

                    if (history1.getEmail().equals(user.getEmail())) {

                        histories.add(history1);

                        dataAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

}

