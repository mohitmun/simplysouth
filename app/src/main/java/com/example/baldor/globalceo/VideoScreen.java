package com.example.baldor.globalceo;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by baldor on 30/9/16.
 */

public class VideoScreen extends AppCompatActivity {
    public void myOnCreate(int res_id, final Class activityClass){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        VideoView videoHolder = (VideoView)findViewById(R.id.videoview);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + res_id);
        videoHolder.setVideoURI(video);
        MediaController mc = new MediaController(this);
        mc.setAnchorView(videoHolder);
        mc.setMediaPlayer(videoHolder);
        Button skip = (Button) findViewById(R.id.skip);
        final Button mute = (Button) findViewById(R.id.mute);
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(m == null){
//                    return;
//                }

                if(mute.getText().toString().equalsIgnoreCase("mute")){
//                    m.setVolume(0F, 0F);
                    mute();
                    mute.setText("Unmute");
                }else{
//                    m.setVolume(75.0F,75.0F);
                    unmute();
                    mute.setText("Mute");
                }
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(activityClass);
            }
        });
        videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                jump(activityClass);
            }
        });
//        videoHolder.setOnPreparedListener(PreparedListener);
        videoHolder.start();
    }

    private void mute() {
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamMute(AudioManager.STREAM_MUSIC, true);
    }

    public void unmute() {
        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        am.setStreamMute(AudioManager.STREAM_MUSIC, false);
    }
    public static AudioManager am;
    private MediaPlayer m;
//    MediaPlayer.OnPreparedListener PreparedListener = new MediaPlayer.OnPreparedListener(){
//
//        @Override
//        public void onPrepared(MediaPlayer m) {
//            try {
//                if (m.isPlaying()) {
//                    m.stop();
//                    m.release();
//                    m = new MediaPlayer();
//                }
//
////                m.setVolume(0f, 0f);
//                m.setLooping(false);
//                m.start();
//                VideoScreen.this.m = m;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };


    public void jump(Class activityClass) {
        if (isFinishing())
            return;
        startActivity(new Intent(this, activityClass));
        finish();
    }
}
