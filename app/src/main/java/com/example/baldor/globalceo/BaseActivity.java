package com.example.baldor.globalceo;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by baldor on 30/9/16.
 */

public class BaseActivity extends AppCompatActivity {
    public void myOnCreate(String url, int small_video){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        copyAssets("globalceo");
        setContentView(R.layout.activity_main);
        WebView mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl(url);
        WebChromeClient chromeClient = new WebChromeClient(){
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                if (view instanceof FrameLayout){
                    FrameLayout frame = (FrameLayout) view;
                    if (frame.getFocusedChild() instanceof VideoView){
                        VideoView video = (VideoView) frame.getFocusedChild();
                        frame.removeView(video);
                        BaseActivity.this.setContentView(video);
                        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                Log.d("TAG", "Video completo");
                                BaseActivity.this.setContentView(R.layout.activity_main);
                                WebView wb = (WebView) BaseActivity.this.findViewById(R.id.webview);
//                                MainActivity.this.initWebView();
                            }
                        });
//                        video.setOnErrorListener(this);
                        video.start();
                    }
                }
            }
        };
        mWebView.setWebChromeClient(chromeClient);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("shopping")) {
                    // magic
                    startActivity(new Intent(BaseActivity.this, ShoppingVideo.class));
                    return true;
                }else if(url.contains("stay")){
                    startActivity(new Intent(BaseActivity.this, StayVideo.class));
                    return true;
                }else if(url.contains("food")){
                    startActivity(new Intent(BaseActivity.this, FoodVideo.class));
                    return true;
                }else if(url.contains("sightseeing")){
                    startActivity(new Intent(BaseActivity.this, SightVideo.class));
                    return true;
                }
                return false;
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setpl
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
//        mSectionsPagerAdapter = new MainActivity.SectionsPagerAdapter(getSupportFragmentManager());
//
//        // Set up the ViewPager with the sections adapter.
//        mViewPager = (ViewPager) findViewById(R.id.container);
//        mViewPager.setAdapter(mSectionsPagerAdapter);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        if(small_video != 0){
            View v = findViewById(R.id.small_videoview_wrapper);
            v.setVisibility(View.VISIBLE);
            VideoView videoHolder = (VideoView)findViewById(R.id.small_videoview);
            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + small_video);
            videoHolder.setVideoURI(video);
            MediaController mc = new MediaController(this);
            mc.setAnchorView(videoHolder);
            mc.setMediaPlayer(videoHolder);
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
            videoHolder.start();
        }

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
}
