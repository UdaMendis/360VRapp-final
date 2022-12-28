package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

public class speedgraph extends AppCompatActivity {

    public static final String TAG = "speedgraph";

    LineChart lineChart;
    LineData lineData;
    List<Entry> entryList = new ArrayList<>();

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;
    FirebaseUser user;
    StorageReference storageReference;
    TextView tx;
    public Register r;

    final Calendar myCalendar = Calendar.getInstance();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedgraph);

        final EditText edittext= (EditText) findViewById(R.id.Birthday);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updatelabel();
            }

            private void updatelabel() {
                    String myFormat = "MM/dd/yy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    edittext.setText(sdf.format(myCalendar.getTime()));
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(speedgraph.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


//        lineChart  = findViewById(R.id.linechart);

//        tx = findViewById(R.id.txt);
//
//        fAuth = FirebaseAuth.getInstance();
//        fstore = FirebaseFirestore.getInstance();
//        storageReference = FirebaseStorage.getInstance().getReference();
//        userId = fAuth.getCurrentUser().getUid();
//        user = fAuth.getCurrentUser();
//
//        DocumentReference docRef = fstore.collection("users").document(userId).collection("parameters").document("Speed").collection("History").document("2021-02-28 12:51:50.548232");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @SuppressLint("ResourceType")
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    List<String> list = new ArrayList<>();
//                    Map<String, Object> map = Objects.requireNonNull(task.getResult()).getData();
//                    for (Map.Entry<String, Object> entry : map.entrySet()) {
//                        list.add(entry.getKey());              //to get field
//                        list.add(entry.getValue().toString());
//                        entryList.add(new Entry(Integer.parseInt(entry.getKey()),Integer.parseInt((String) entry.getValue())));
//                    }
//                    LineDataSet lineDataSet = new LineDataSet(entryList,"speed");
//                    lineDataSet.setFillAlpha(110);
//                    lineData = new LineData(lineDataSet);
//                    lineChart.setData(lineData);
//                }
//            }
//        });

    }



}