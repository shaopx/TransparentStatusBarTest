package com.spx.transparentstatusbartest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/7/22.
 */

public class Actvity1 extends Activity {

    private static final String TAG = "Actvity1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_1_layout);

        RelativeLayout rootLayout = findViewById(R.id.rootRl);
        ImageView bgiv = findViewById(R.id.fullActivitybg);
        KApp application = (KApp) getApplication();
        Log.d(TAG, "onCreate: bitmap:"+application.getBitmap());
        bgiv.setImageBitmap(application.getBitmap());
//        rootLayout.setBackgroundResource(R.drawable.test_bg);
    }
}
