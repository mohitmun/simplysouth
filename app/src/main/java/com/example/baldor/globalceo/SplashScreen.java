package com.example.baldor.globalceo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;

/**
 * Created by baldor on 30/9/16.
 */

public class SplashScreen extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.splash);
            VideoView videoHolder = (VideoView)findViewById(R.id.videoview);
                Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.welcome_intro);
                videoHolder.setVideoURI(video);
                Button skip = (Button) findViewById(R.id.skip);
                skip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jump();
                    }
                });
                videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        jump();
                    }
                });
                videoHolder.start();

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            jump();
            return true;
        }

        private void jump() {
            if (isFinishing())
                return;
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
}
