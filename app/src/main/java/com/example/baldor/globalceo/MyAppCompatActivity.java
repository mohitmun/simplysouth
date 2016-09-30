package com.example.baldor.globalceo;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by baldor on 30/9/16.
 */
public class MyAppCompatActivity extends AppCompatActivity{
    @Override
    protected void onRestart() {
        super.onRestart();

        BaseActivity.unmute(this);
    }
}
