package com.example.loginapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import javax.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class Leaderboard extends AppCompatActivity {
    TextView weightpara,heightpara,distancepara,timepara,caloriespara,powerpara,speedpara,heartratepara;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;
    FirebaseUser user;
    StorageReference storageReference;
    Button speedBtn,hrtBtn;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        weightpara = findViewById(R.id.weightnum);
        heightpara = findViewById(R.id.heightnum);
        distancepara = findViewById(R.id.distnum);
        timepara = findViewById(R.id.timenum);
        caloriespara = findViewById(R.id.calnum);
        powerpara = findViewById(R.id.pwrnum);
        speedpara = findViewById(R.id.spdnum);
        heartratepara = findViewById(R.id.hrtnum);

        speedBtn = findViewById(R.id.speed);
        hrtBtn = findViewById(R.id.heartrate);


        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();


        DocumentReference doc_ref_speed = fstore.collection("users").document(userId).collection("parameters").document("Speed");
        DocumentReference doc_ref_weight = fstore.collection("users").document(userId).collection("parameters").document("Weight");
        DocumentReference doc_ref_height = fstore.collection("users").document(userId).collection("parameters").document("Height");
        DocumentReference doc_ref_dist = fstore.collection("users").document(userId).collection("parameters").document("Distance");
        DocumentReference doc_ref_time = fstore.collection("users").document(userId).collection("parameters").document("Time");
        DocumentReference doc_ref_cal = fstore.collection("users").document(userId).collection("parameters").document("Calories");
        DocumentReference doc_ref_pwr = fstore.collection("users").document(userId).collection("parameters").document("Power");
        DocumentReference doc_ref_hrt = fstore.collection("users").document(userId).collection("parameters").document("Heartrate");


        doc_ref_speed.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    speedpara.setText(documentSnapshot.getString("Speed"));


                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        doc_ref_weight.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    weightpara.setText(documentSnapshot.getString("Weight"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
        doc_ref_height.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    heightpara.setText(documentSnapshot.getString("Height"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
        doc_ref_dist.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    distancepara.setText(documentSnapshot.getString("Distance"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        doc_ref_time.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    timepara.setText(documentSnapshot.getString("Time"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        doc_ref_cal.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    caloriespara.setText(documentSnapshot.getString("Calories"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        doc_ref_pwr.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    powerpara.setText(documentSnapshot.getString("Power"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        doc_ref_hrt.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    heartratepara.setText(documentSnapshot.getString("Heartrate"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });


        speedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Leaderboard.this,speedgraph.class));
            }
        });

        hrtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Leaderboard.this,hrtgraph.class));
            }
        });


    }
}

/*
        String[] array = {"Weight","Heaight","Distance","Time","Calories","Power","Speed","Heartrate"};
        for (int i=0 ; i<array.length;i++){

            DocumentReference documentReference = fstore.collection("users").document(userId).collection("parameters").document(array[i]);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if(documentSnapshot.exists()){
                        //weight.setText(documentSnapshot.getString("Weight"));
                        //height.setText(documentSnapshot.getString("Height"));
                        distancepara.setText(documentSnapshot.getString("Distance"));
                        timepara.setText(documentSnapshot.getString("Time"));
                        caloriespara.setText(documentSnapshot.getString("Calories"));
                        powerpara.setText(documentSnapshot.getString("Power"));
                        speedpara.setText(documentSnapshot.getString("Speed"));
                        heartratepara.setText(documentSnapshot.getString("Heartrate"));

                    }else {
                        Log.d("tag", "onEvent: Document do not exists");
                    }
                }
            });


        }

 */