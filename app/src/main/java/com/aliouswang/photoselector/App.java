package com.aliouswang.photoselector;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by aliouswang on 16/1/15.
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
