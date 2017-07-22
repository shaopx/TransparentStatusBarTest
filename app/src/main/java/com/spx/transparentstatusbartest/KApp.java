package com.spx.transparentstatusbartest;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/7/22.
 */

public class KApp extends Application {

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

    private int screenWidth, screenHeight;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap bitmap;
}

