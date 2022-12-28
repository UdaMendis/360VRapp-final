package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class display extends AppCompatActivity {

    ImageButton mprofileBtn, mperformBtn, imageButton1,imageButton2,imageButton3,imageButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mprofileBtn = findViewById(R.id.profileBtn);
        mperformBtn = findViewById(R.id.performBtn);
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myfirstproject-f379a.appspot.com/o/virtual_cycling.mp4?alt=media&token=433c7691-2b5a-4e4f-aa90-114c68ef3ec3");
                Uri uri = Uri.parse("http://192.168.8.175:8080/virtual_cycling.mp4");
                String csvfile = "virtual_cycling";
                Intent playVideo = new Intent(display.this, ExoPlayerActivity.class);
                playVideo.setData(uri);
                playVideo.putExtra("video", csvfile);
                startActivity(playVideo);
              //  startActivity(new Intent(display.this,ExoPlayerActivity.class));
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myfirstproject-f379a.appspot.com/o/virtual_cycling.mp4?alt=media&token=433c7691-2b5a-4e4f-aa90-114c68ef3ec3");
                Uri uri = Uri.parse("http://192.168.8.175:8080/virtual_cycling.mp4");
                String csvfile = "virtual_cycling.csv";
                Intent playVideo = new Intent(display.this, ExoPlayerActivity.class);
                playVideo.setData(uri);
                playVideo.setData(Uri.parse(csvfile));
                startActivity(playVideo);
                //  startActivity(new Intent(display.this,ExoPlayerActivity.class));
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myfirstproject-f379a.appspot.com/o/virtual_cycling.mp4?alt=media&token=433c7691-2b5a-4e4f-aa90-114c68ef3ec3");
                Uri uri = Uri.parse("http://192.168.8.175:8080/virtual_cycling.mp4");
                String csvfile = "virtual_cycling.csv";
                Intent playVideo = new Intent(display.this, ExoPlayerActivity.class);
                playVideo.setData(uri);
                playVideo.setData(Uri.parse(csvfile));
                startActivity(playVideo);
                //  startActivity(new Intent(display.this,ExoPlayerActivity.class));
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myfirstproject-f379a.appspot.com/o/virtual_cycling.mp4?alt=media&token=433c7691-2b5a-4e4f-aa90-114c68ef3ec3");
                Uri uri = Uri.parse("http://192.168.8.175:8080/virtual_cycling.mp4");
                String csvfile = "virtual_cycling.csv";
                Intent playVideo = new Intent(display.this, ExoPlayerActivity.class);
                playVideo.setData(uri);
                playVideo.setData(Uri.parse(csvfile));
                startActivity(playVideo);
                //  startActivity(new Intent(display.this,ExoPlayerActivity.class));
            }
        });


        mprofileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(display.this, ProfileActivity.class));
            }
        });

        mperformBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(display.this, Dates.class));
            }
        });
    }
}