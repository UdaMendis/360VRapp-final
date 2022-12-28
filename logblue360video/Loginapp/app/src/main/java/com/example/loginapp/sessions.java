package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class sessions extends AppCompatActivity {

    LineChart lineChart1;
    LineChart lineChart2;
    LineChart lineChart3;
    LineData lineData1;
    LineData lineData2;
    LineData lineData3;
    List<Entry> entryList1 = new ArrayList<>();
    List<Entry> entryList2 = new ArrayList<>();
    List<Entry> entryList3 = new ArrayList<>();


    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;
    FirebaseUser user;
    StorageReference storageReference;
    TextView tx;
    public Register r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        lineChart1  = findViewById(R.id.linecharta);
        lineChart2 = findViewById(R.id.linechartb);
        lineChart3 = findViewById(R.id.linechartc);
        tx = findViewById(R.id.textView);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        Intent intent = getIntent();
        String d = intent.getExtras().getString("sessions");
        String doc = intent.getExtras().getString("button");
        tx.setText(doc+d);

        DocumentReference docRef1 = fstore.collection("users").document(userId).collection(d).document(doc).collection("parameters").document("HR");
        DocumentReference docRef2 = fstore.collection("users").document(userId).collection(d).document(doc).collection("parameters").document("power");
        DocumentReference docRef3 = fstore.collection("users").document(userId).collection(d).document(doc).collection("parameters").document("speed");

        docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("ResourceType")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    Map<String, Object> map = Objects.requireNonNull(task.getResult()).getData();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        list.add(entry.getKey());              //to get field
                        list.add(entry.getValue().toString());
                        entryList1.add(new Entry(Integer.parseInt(entry.getKey()),Integer.parseInt((String) entry.getValue())));
                    }
                    LineDataSet lineDataSet = new LineDataSet(entryList1,"speed");
                    lineDataSet.setFillAlpha(110);
                    lineData1 = new LineData(lineDataSet);
                    lineChart1.setData(lineData1);
                }
            }
        });

        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("ResourceType")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    Map<String, Object> map = Objects.requireNonNull(task.getResult()).getData();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        list.add(entry.getKey());              //to get field
                        list.add(entry.getValue().toString());
                        entryList2.add(new Entry(Integer.parseInt(entry.getKey()),Integer.parseInt((String) entry.getValue())));
                    }
                    LineDataSet lineDataSet = new LineDataSet(entryList2,"HR");
                    lineDataSet.setFillAlpha(110);
                    lineData2 = new LineData(lineDataSet);
                    lineChart2.setData(lineData2);
                }
            }
        });

        docRef3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("ResourceType")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    Map<String, Object> map = Objects.requireNonNull(task.getResult()).getData();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        list.add(entry.getKey());              //to get field
                        list.add(entry.getValue().toString());
                        entryList3.add(new Entry(Integer.parseInt(entry.getKey()),Integer.parseInt((String) entry.getValue())));
                    }
                    LineDataSet lineDataSet = new LineDataSet(entryList3,"power");
                    lineDataSet.setFillAlpha(110);
                    lineData3 = new LineData(lineDataSet);
                    lineChart3.setData(lineData3);
                }
            }
        });


    }
}