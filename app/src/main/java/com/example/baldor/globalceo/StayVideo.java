package com.example.baldor.globalceo;

import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Created by baldor on 30/9/16.
 */

public class StayVideo extends VideoScreen{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myOnCreate(R.raw.stay_intro, MainActivity.class);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        jump(MainActivity.class);
        return true;
    }

}
