package com.spx.transparentstatusbartest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/22.
 */

public class Actvity1 extends FragmentActivity {

    private static final String TAG = "Actvity1";

    ImageView bgiv;
    ViewPager viewPager;
    FragmentAdapter adapter;
    List<String> imagePathList = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_1_layout);

        RelativeLayout rootLayout = findViewById(R.id.rootRl);
        bgiv = findViewById(R.id.fullActivitybg);
        final KApp application = (KApp) getApplication();
        Log.d(TAG, "onCreate: bitmap:" + application.getBitmap());
        bgiv.setImageBitmap(application.getBitmap());
//        rootLayout.setBackgroundResource(R.drawable.test_bg);
        String album = getIntent().getStringExtra("album");
        imagePathList = GalleryModel.getInstance().getImageListOfAlbum(album);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: ..." + position);
                FullScreenFragment fragment = (FullScreenFragment) adapter.getItem(position);
                bgiv.setImageBitmap(fragment.getBitmap());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bgiv.setVisibility(View.INVISIBLE);
            }
        }, 500);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ...");
        if (viewPager.getCurrentItem() > 0) {
            finish();
            overridePendingTransition(0, 0);
        } else {
            bgiv.setVisibility(View.VISIBLE);

            super.onBackPressed();
        }

    }

    class FragmentAdapter extends FragmentStatePagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String picturePath = imagePathList.get(position);

            Fragment fragment = FullScreenFragment.createInstance(picturePath, position);
            return fragment;
        }

        @Override
        public int getCount() {
            return imagePathList.size();
        }
    }
}
