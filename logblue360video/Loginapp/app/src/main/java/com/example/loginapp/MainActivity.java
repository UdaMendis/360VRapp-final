package com.example.loginapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
//public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, connectionfailed {

public class MainActivity extends AppCompatActivity{
    ImageButton imageButton1,imageButton2,imageButton3,imageButton4,imageButton5,imageButton6;
    Button mprofileBtn, mperformBtn, logout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mprofileBtn = findViewById(R.id.profileBtn);
        mperformBtn = findViewById(R.id.performBtn);
        logout = findViewById(R.id.Logout);
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);
        imageButton5 = findViewById(R.id.imageButton5);
        imageButton6 = findViewById(R.id.imageButton6);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(getApplicationContext(),Login.class));
        }

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myfirstproject-f379a.appspot.com/o/virtual_cycling.mp4?alt=media&token=433c7691-2b5a-4e4f-aa90-114c68ef3ec3");
                Uri uri = Uri.parse("http://192.168.8.175:8080/virtual_cycling.mp4");
                String csvfile = "virtual_cycling";
                Intent playVideo = new Intent(MainActivity.this, ExoPlayerActivity.class);
                playVideo.setData(uri);
                playVideo.putExtra("video", "virtual_cycling");
                startActivity(playVideo);
                //  startActivity(new Intent(display.this,ExoPlayerActivity.class));
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myfirstproject-f379a.appspot.com/o/virtual_cycling.mp4?alt=media&token=433c7691-2b5a-4e4f-aa90-114c68ef3ec3");
               // Uri uri = Uri.parse("http://192.168.8.175:8080/virtual_cycling.mp4");
                String csvfile = "virtual_cycling";
                Intent playVideo = new Intent(MainActivity.this, ExoPlayerActivity.class);
                playVideo.setData(uri);
                playVideo.putExtra("video", "virtual_cycling");
                startActivity(playVideo);
                //  startActivity(new Intent(display.this,ExoPlayerActivity.class));
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myfirstproject-f379a.appspot.com/o/virtual_cycling.mp4?alt=media&token=433c7691-2b5a-4e4f-aa90-114c68ef3ec3");
                Uri uri = Uri.parse("http://192.168.8.175:8080/virtual_cycling.mp4");
                String csvfile = "virtual_cycling";
                Intent playVideo = new Intent(MainActivity.this, ExoPlayerActivity.class);
                playVideo.setData(uri);
                playVideo.putExtra("video", csvfile);
                startActivity(playVideo);
                //  startActivity(new Intent(display.this,ExoPlayerActivity.class));
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myfirstproject-f379a.appspot.com/o/virtual_cycling.mp4?alt=media&token=433c7691-2b5a-4e4f-aa90-114c68ef3ec3");
                //Uri uri = Uri.parse("http://192.168.8.175:8080/virtual_cycling.mp4");
                String csvfile = "virtual_cycling";
                Intent playVideo = new Intent(MainActivity.this, ExoPlayerActivity.class);
                playVideo.setData(uri);
                playVideo.putExtra("video", csvfile);
                startActivity(playVideo);
                //  startActivity(new Intent(display.this,ExoPlayerActivity.class));
            }
        });

        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myfirstproject-f379a.appspot.com/o/virtual_cycling.mp4?alt=media&token=433c7691-2b5a-4e4f-aa90-114c68ef3ec3");
                Uri uri = Uri.parse("http://192.168.8.175:8080/virtual_cycling.mp4");
                String csvfile = "virtual_cycling";
                Intent playVideo = new Intent(MainActivity.this, ExoPlayerActivity.class);
                playVideo.setData(uri);
                playVideo.putExtra("video", csvfile);
                startActivity(playVideo);
                //  startActivity(new Intent(display.this,ExoPlayerActivity.class));
            }
        });

        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myfirstproject-f379a.appspot.com/o/virtual_cycling.mp4?alt=media&token=433c7691-2b5a-4e4f-aa90-114c68ef3ec3");
                //Uri uri = Uri.parse("http://192.168.8.175:8080/virtual_cycling.mp4");
                String csvfile = "virtual_cycling";
                Intent playVideo = new Intent(MainActivity.this, ExoPlayerActivity.class);
                playVideo.setData(uri);
                playVideo.putExtra("video", csvfile);
                startActivity(playVideo);
                //  startActivity(new Intent(display.this,ExoPlayerActivity.class));
            }
        });


        mprofileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        mperformBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, Dates.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });
    }


}
