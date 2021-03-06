package com.example.baldor.globalceo;

import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Created by baldor on 30/9/16.
 */

public class FoodVideo extends VideoScreen{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myOnCreate(R.raw.food_intro, Food.class);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        jump(Food.class);
        return true;
    }

}
