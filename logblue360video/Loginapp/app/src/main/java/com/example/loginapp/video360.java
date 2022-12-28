package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class video360 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video360);


        // ImageButton img2 = (ImageButton) findViewById(R.id.imageButton2);
        // ImageButton img3 = (ImageButton) findViewById(R.id.imageButton3);

        //set the url of the video.

        //img2.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //  public void onClick(View view) {
        Uri uri = Uri.parse("http://192.168.8.202:8080/hls/bbb.m3u8");
        Intent playVideo = new Intent(video360.this, VrVideoActivity.class);
        playVideo.setData(uri);
        startActivity(playVideo);
        //  }
        //});
        //img3.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //   public void onClick(View view) {
        //      Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myfirstproject-f379a.appspot.com/o/360_VR%20Master%20Series%20_%20Free%20Download%20_%20View%20On%20Low%20Waterfall%20with%20Nice%20City.mp4?alt=media&token=f4691ee5-8724-4ed4-b2bf-27a6bc8de608");
        //      Intent playVideo = new Intent(MainActivity.this, VrVideoActivity.class);
        //      playVideo.setData(uri);
        //      startActivity(playVideo);
        //  }
        // });
    }
}