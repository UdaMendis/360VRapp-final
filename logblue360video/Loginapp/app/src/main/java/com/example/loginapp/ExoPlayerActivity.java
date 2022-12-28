package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.firebase.auth.FirebaseAuth;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class ExoPlayerActivity extends AppCompatActivity {
    private static final String TAG = "ExoPlayerActivity";
    private PlayerView videoView;
    private Button bplay,stop,speed;
    public SimpleExoPlayer player;
    public Float variablespeed=1.0f;
    public boolean state = false;
    public String userId;
    public String video;


    //  private final ComponentListener componentListener;
    public MqttAndroidClient client;
    private ComponentListener componentListener;
    public Register register;
    public PhysciologicalParameters physciologicalParameters;
    public String Weight, Height, Age, Gender;
    //public String username;
    FirebaseAuth fAuth;
    int starttime = 1;
    List slopelist;
    // private SlopeHandlerThread handlerThread = new SlopeHandlerThread();
    //private GvrView gvrView;

    //Handler timerHandler = new Handler();
    // private final UiUpdater uiUpdater = new UiUpdater();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);
        fAuth = FirebaseAuth.getInstance();
        physciologicalParameters = new PhysciologicalParameters();
        videoView = (PlayerView) findViewById(R.id.videoView);
       /* bplay = (Button) findViewById(R.id.Play);
        stop = (Button) findViewById(R.id.Pause);
        speed = (Button) findViewById(R.id.speed);*/
        //componentListener = new ComponentListener();
        //setting the media controller.
        //MediaController mediaController = new MediaController(this);
        //mediaController.setAnchorView(videoView);
       // Intent intent = getIntent();
        /*if (intent == null || getIntent().length < 1 || intent[0] == null || intent[0].getData() == null) {
            // This happens if the Activity wasn't started with the right intent.
            errorText = "No URI specified. Using default panorama.";
            Log.e(TAG, "No URI specified. Using default panorama.");
            return null;
        }*/
        // Based on the Intent's data, load the appropriate media from disk.
        Uri videouri = seturi(getIntent());

        InputStream inputStream = getResources().openRawResource(R.raw.virtual_cycling);
        CSVFile csvFile = new CSVFile(inputStream);
        slopelist = csvFile.read();

        userId = fAuth.getCurrentUser().getUid();
        video = getIntent().getExtras().getString("video");

        Weight = physciologicalParameters.Weight;
        Height = physciologicalParameters.Height;
        Age = physciologicalParameters.Age;
        Gender = physciologicalParameters.Gender;

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), /*"tcp://broker.hivemq.com:1883"*/"tcp://192.168.8.175:1883",
                clientId);
        //Toast.makeText(this, "New MQTT client created.", Toast.LENGTH_SHORT).show();
        // Log.d(TAG, "MQTT client created.");

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    //  Log.d(TAG, "onSuccess");
                    Toast.makeText(ExoPlayerActivity.this, "Connected to MQTT!", Toast.LENGTH_SHORT).show();
                    publishparameters(Weight, Height,Age,Gender);
                    publishusername(userId, video);
                    publishflag(Integer.parseInt(Weight),2);
                    publishflag(0,1);
                    setSubcription();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    // Log.d(TAG, "onFailure");
                    Toast.makeText(ExoPlayerActivity.this, "Not Connected to MQTT!", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        //  Uri videouri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myfirstproject-f379a.appspot.com/o/virtual_cycling_subtitle_15min.mp4?alt=media&token=0ede6a09-0624-477c-86d8-9779ce1f065a");
        //  Uri subtitleuri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myfirstproject-f379a.appspot.com/o/virtual_cycling_subtitle_15min.srt");
        //Uri videouri = Uri.parse("http://192.168.8.175:8080/virtual_cycling.mp4");
        //  Uri subtitleuri = Uri.parse("http://192.168.8.175:8080/virtual_cycling_subtitle_15min.srt");

        // MediaSource subtitlesource = new SingleSampleMediaSource(null,subtitleuri,dataSourceFactory,);
        //  MergingMediaSource mergedsource = new MergingMediaSource(videosource,subtitlesource);
        player = new SimpleExoPlayer.Builder(this).build();
        videoView.setPlayer(player);
        videoView.onResume();

        /*MediaItem.Subtitle subtitle = new MediaItem.Subtitle(
                subtitleuri,
                APPLICATION_SUBRIP,
                null);*/

        MediaItem mediaItem = new MediaItem.Builder()
                .setUri(videouri)
                // .setSubtitles(Lists.newArrayList(subtitle))
                .build();

        player.setMediaItem(mediaItem);
        //player.addListener(componentListener);

        //  player.setMediaSource(mergedsource);

        // MergingMediaSource mediaSource = new MergingMediaSource(subtitlesource);
        // player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(player.getPlayWhenReady());
        // handlerThread.start();

        //  handlerThread.getHandler().postDelayed(timerRunnable,0);

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Toast.makeText(ExoPlayerActivity.this, "Disconnected from MQTT!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Toast.makeText(ExoPlayerActivity.this,String.valueOf(Float.parseFloat(new String(message.getPayload()))),Toast.LENGTH_LONG).show();
                variablespeed = 0.07f*Float.parseFloat(new String(message.getPayload()));
                if (variablespeed==0.0f){
                    pausePlayer();
                    state = false;
                }else if (player!=null && !state){
                    state = true;
                    resumePlayer();
                    PlaybackParameters params = new PlaybackParameters(variablespeed);
                    player.setPlaybackParameters(params);
                }else{
                    PlaybackParameters params = new PlaybackParameters(variablespeed);
                    player.setPlaybackParameters(params);
                }
                if (starttime==1){
                    long position = (int) player.getCurrentPosition();
                    publishslope(position,1);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }

    private Uri setcsvname(Intent... intent) {
        Uri csvname = intent[1].getData();
        return csvname;
    }


    private void publishusername(String username, String video){
        JSONObject slopeflag = new JSONObject();
        try {
            slopeflag.put("username",username);
            slopeflag.put("video",video);
            // slopeflag.put("video","virtual_cycling.csv");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        byte[] encodedpayload = new byte[0];
        try {
            String jsonstring = slopeflag.toString();
            encodedpayload = jsonstring.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedpayload);
            try {
                client.publish("db/userdata",message);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void publishparameters(String Weight, String Height, String Age, String Gender){
        JSONObject slopeflag = new JSONObject();
        try {
            slopeflag.put("weight",Weight);
            slopeflag.put("height",Height);
            slopeflag.put("age",Age);
            slopeflag.put("gender",Gender);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        byte[] encodedpayload = new byte[0];
        try {
            String jsonstring = slopeflag.toString();
            encodedpayload = jsonstring.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedpayload);
            try {
                client.publish("db/params",message);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void publishflag(int slope, int flag) {
        String payload = "1";
        JSONObject slopeflag = new JSONObject();
        try {
            slopeflag.put("slope",slope);
            slopeflag.put("flag",flag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        byte[] encodedpayload = new byte[0];
        try {
            String jsonstring = slopeflag.toString();
            encodedpayload = jsonstring.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedpayload);
            try {
                client.publish("slope/flag",message);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void setSubcription(){
        try {
            client.subscribe("speed",1);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private Uri seturi(Intent... intent){
        Uri videouri = intent[0].getData();
        return videouri;
    }

     private void publishslope(long position, int flag){
        if (position/400<=2249 && flag>0){
           // Toast.makeText(this,String.valueOf(position),Toast.LENGTH_LONG).show();
//            int index = (int) position/400;
         //   int[] slope = (int[]) slopelist.get((int) (position/400));

           // String[] slope = (String[]) slopelist.get((int) (position/400));
            //int value = Integer.parseInt(String.valueOf(slope));
            JSONObject slopeflag = new JSONObject();
            try {
                slopeflag.put("slope", position/400);
                slopeflag.put("flag", flag);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            byte[] encodedpayload = new byte[0];
            try {
                String jsonstring = slopeflag.toString();
                encodedpayload = jsonstring.getBytes("UTF-8");
                MqttMessage mqttMessage = new MqttMessage(encodedpayload);
                try {
                    client.publish("pstn/flag",mqttMessage);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else if(flag == 0){
            JSONObject slopeflag = new JSONObject();
            try {
                slopeflag.put("slope", position);
                slopeflag.put("flag",flag);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            byte[] encodedpayload = new byte[0];
            try {
                String jsonstring = slopeflag.toString();
                encodedpayload = jsonstring.getBytes("UTF-8");
                MqttMessage mqttMessage = new MqttMessage(encodedpayload);
                try {
                    client.publish("pstn/flag",mqttMessage);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            //String[] slope = (String[]) slopelist.get(2249);
            JSONObject slopeflag = new JSONObject();
            try {
                slopeflag.put("slope", 2250);
                slopeflag.put("flag",flag);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            byte[] encodedpayload = new byte[0];
            try {
                String jsonstring = slopeflag.toString();
                encodedpayload = jsonstring.getBytes("UTF-8");
                MqttMessage mqttMessage = new MqttMessage(encodedpayload);
                try {
                    client.publish("pstn/flag",mqttMessage);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    private void pausePlayer(){
        player.setPlayWhenReady(false);
        player.getPlaybackState();
    }

    private void resumePlayer(){
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }

   /* @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {

        }
    }*/

    @Override
    protected void onStop() {
        super.onStop();
        starttime = 0;
        player.stop();
        player.release();
        publishslope((int) (player.getCurrentPosition())/60000,0);
        publishflag(0,0);
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        // startActivity(new Intent(ExoPlayerActivity.this,display.class));
    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player!=null){
            player.stop();
            player.release();
        }
        if (client!=null){
            try {
                client.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
        startActivity(new Intent(ExoPlayerActivity.this,display.class));
    }*/

    private class ComponentListener implements Player.EventListener, TextOutput {

        public ComponentListener(){}

        @Override
        public void onCues(List<Cue> cues) {
            /*JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("slope",cues);
                jsonObject.put("flag",1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            byte[] encodedpayload;
            try {
                String jsonstring = jsonObject.toString();
                encodedpayload = jsonstring.getBytes("UTF-8");
                MqttMessage message = new MqttMessage(encodedpayload);
                try {
                    client.publish("slope/flag",message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
        }
        @Override
        public void onEvents(Player player1, Player.Events events){
            if (events.containsAny(Player.EVENT_PLAYBACK_STATE_CHANGED,Player.EVENT_IS_PLAYING_CHANGED,Player.EVENT_PLAY_WHEN_READY_CHANGED)){
                //publishslope();
            }
        }

    }
}

