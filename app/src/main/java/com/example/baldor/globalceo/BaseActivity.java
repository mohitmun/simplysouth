package com.example.baldor.globalceo;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
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

public class BaseActivity extends MyAppCompatActivity {
    WebView mWebView;
    private String url;
    private int small_video;
    boolean reload = false;

    public void myOnCreate(String url, int small_video){
        this.url = url;
        this.small_video = small_video;
        try{

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }catch (Exception e){

        }
//        copyAssets("globalceo");
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl(url);

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

    }
    VideoView videoHolder;


    public static void mute(Context context) {
        am = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        am.setStreamMute(AudioManager.STREAM_MUSIC, true);
    }

    public static void unmute(Context context) {
        Log.d("====", "unmuted");
        am = (AudioManager)context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        am.setStreamMute(AudioManager.STREAM_MUSIC, false);
    }

    @Override
    protected void onResume() {
        if(reload && mWebView !=null && url.equals("/globalceo/events/index.html")){
            Log.d("====", "LOLOL");
            mWebView.loadUrl(url);
        }
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    boolean showed = false;
    @Override
    protected void onStart() {
        if(!showed && small_video != 0){
            showed = true;
            videoHolder = (VideoView)findViewById(R.id.small_videoview);
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
                        mute(BaseActivity.this);
                        mute.setText("Unmute");
                    }else{
                        //                    m.setVolume(75.0F,75.0F);
                        unmute(BaseActivity.this);
                        mute.setText("Mute");
                    }
                }
            });
            small_video = 0;


        }else{
            if(video_wrapper != null){
                video_wrapper.setVisibility(View.GONE);
            }
        }

        unmute(BaseActivity.this);
        small_video = 0;
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
            public void onPageFinished(WebView view, String url) {
                if(videoHolder != null){
                     video_wrapper = findViewById(R.id.small_videoview_wrapper);
                    video_wrapper.setVisibility(View.VISIBLE);
                    videoHolder.start();
                    videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            video_wrapper.setVisibility(View.GONE);
                        }
                    });
                }
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if(reload && url.contains("/globalceo/events/index.html")){
//                    startActivity(new Intent(BaseActivity.this, MainActivity.class));
//                    finish();
//                }
                if (url.contains("shopping-internal")) {
                    // magic
                    startActivity(new Intent(BaseActivity.this, ShoppingVideo.class));
                    reload = false;
                    return true;
                }else if(url.contains("stay-internal")){
                    startActivity(new Intent(BaseActivity.this, StayVideo.class));
                    reload = false;
                    return true;
                }else if(url.contains("food-internal")){
                    startActivity(new Intent(BaseActivity.this, FoodVideo.class));
                    reload = false;
                    return true;
                }else if(url.contains("sightseeing-internal")){
                    startActivity(new Intent(BaseActivity.this, SightVideo.class));
                    reload = false;
                    return true;
                }
                if(video_wrapper!= null){
                    video_wrapper.setVisibility(View.GONE);
                }
                unmute(BaseActivity.this);
                small_video = 0;
                reload = false;
                return false;
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setClickable(true);
        mWebView.setEnabled(true);
        super.onStart();

    }
    View video_wrapper;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        super.onBackPressed();
    }

    public static AudioManager am;
}
