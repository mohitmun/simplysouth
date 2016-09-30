package com.example.baldor.globalceo;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by baldor on 30/9/16.
 */
public class Food extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myOnCreate("file:///android_asset/globalceo/food-internal/index.html", R.raw.food);

    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
