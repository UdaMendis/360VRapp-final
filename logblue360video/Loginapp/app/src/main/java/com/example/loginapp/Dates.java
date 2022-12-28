package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Dates extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;
    FirebaseUser user;
    StorageReference storageReference;
    public Register r;
    public String date;
    TextView sp;
    TextView distance;
    TextView pwer;
    TextView calories;

    final Calendar myCalendar = Calendar.getInstance();
    public SimpleDateFormat sdf;
    String d="";
    double dst = 0;
    double cal = 0;
    double powr =0;
    double speed = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates);


        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);



        final LinearLayout verticalLayout= findViewById(R.id.buttonlayout);
        final ScrollView sv = new ScrollView(this);

        sp = findViewById(R.id.speedn);
        distance = findViewById(R.id.distn);
        calories = findViewById(R.id.calon);
        pwer = findViewById(R.id.pwrn);
        final String[] strdate = new String[1];

        r = new Register();
        final EditText edittext= (EditText) findViewById(R.id.Birthday);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updatelabel();


                fstore.collection("users").document(userId).collection(d)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @SuppressLint("WrongConstant")
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    List<String> list = new ArrayList<>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                        list.add(document.getId());
                                        dst = dst +Double.parseDouble(document.getString("dist"));
                                        cal = cal +Double.parseDouble(document.getString("cal"));
                                        powr = powr +Double.parseDouble(document.getString("pwr"));
                                        speed = speed +Double.parseDouble(document.getString("spd"));



                                    }
                                    distance.setText("Distance: "+Double.toString(dst));
                                    sp.setText("Speed: "+Double.toString(speed));
                                    pwer.setText("Power: "+Double.toString(powr));
                                    calories.setText("Calories: "+Double.toString(cal));
//                            tx.setText(String.valueOf(list.size()));
//                                    int dista = dist.stream().mapToInt(Integer::intValue).sum();
//                                    sp.setText(dist.size());
//                                    distance.setText(dista);
                                    sv.setLayoutParams(params);
                                    LinearLayout ll = new LinearLayout(Dates.this);
                                    ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                    ll.setOrientation(1);
                                    sv.addView(ll);

                                    for(int i = 0; i <list.size(); i++)
                                    {
                                        Button b = new Button(Dates.this);
//                                        b.setText("Button "+i);
                                        ll.addView(b);
                                        b.setId(i);
                                        b.setText(String.valueOf(list.get(i)));
                                        b.setLayoutParams(params);
                                        b.setMinimumHeight(200);
                                        b.setMinimumWidth(800);
                                        String id = String.valueOf(list.get(i));



                                        b.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View view) {
//                                                startActivity(new Intent(Dates.this,sessions.class));

                                                Intent j = new Intent(Dates.this, sessions.class);
                                                j.putExtra("sessions",d);
                                                j.putExtra("button",id);
                                                startActivity(j);


                                            }
                                        });
                                    }

                                    verticalLayout.addView(sv);


                                }
//                        else {
////                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
                            }
                        });
            }

            private void updatelabel() {
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                edittext.setText(sdf.format(myCalendar.getTime()));
                d= d+sdf.format(myCalendar.getTime());


            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Dates.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });










    }
}