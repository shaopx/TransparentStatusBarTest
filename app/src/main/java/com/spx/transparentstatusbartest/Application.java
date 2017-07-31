package com.spx.transparentstatusbartest;

import android.graphics.Bitmap;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Administrator on 2017/7/22.
 */

public class Application extends android.app.Application {

    private static Application sInstance = null;

    private int screenWidth, screenHeight;
    public Bitmap cachedBitmap;

    public static Application getApplication() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Fresco.initialize(this);
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public Bitmap getBitmap() {
        return cachedBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.cachedBitmap = bitmap;
    }
}

