package com.spx.transparentstatusbartest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/22.
 */

public class FullScreenFragment extends Fragment {
    private static final String TAG = "PictureFragment";
    String picturePath = null;
    int position = 0;

    private View view;
    private ImageView imageView;

    private Bitmap bitmap;


    public FullScreenFragment() {

    }

    public static Fragment createInstance(String picturePath, int position) {
        FullScreenFragment fragment = new FullScreenFragment();
        fragment.picturePath = picturePath;
        fragment.position = position;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fullscreen_fragment_layout, container, false);
        imageView = view.findViewById(R.id.imageView);
        Log.d(TAG, "onCreateView: ....position:" + position + ", picturePath:" + picturePath);
        KApp application = (KApp) getActivity().getApplication();

        int reqWidth = application.getScreenWidth() / 3;
        int reqHeight = application.getScreenHeight() / 3;
        bitmap = Utils.decodeSampleBitmapFromFile(picturePath, reqWidth, reqHeight);
        if (picturePath != null && bitmap != null) {
            imageView.setImageBitmap(bitmap);
            application.setBitmap(bitmap);
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ..." + view);

                String title = picturePath;
                int index = picturePath.lastIndexOf("/");
                if (index > 0) {
                    title = picturePath.substring(index + 1);
                }

                saveImageToGallery(getActivity(), picturePath, title);

            }
        });

        return view;
    }


    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File("/sdcard/jingxuan");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        String path = "/sdcard/jingxuan";
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }

    public static void saveImageToGallery(Context context, String sourceFile, String fileName) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "jingxuan");

        if (!appDir.exists()) {
            appDir.mkdir();
        }
//        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        Log.d(TAG, "saveImageToGallery: file :" + file.getAbsolutePath());

        try {
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(file);
            Utils.copyStream(fis, fos);
            fos.flush();
            fos.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        // 最后通知图库更新
        String path = "/sdcard/jingxuan";
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + appDir.getAbsolutePath())));

    }


    private void launch() {

    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
