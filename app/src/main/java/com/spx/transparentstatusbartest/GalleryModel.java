package com.spx.transparentstatusbartest;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/22.
 */

public class GalleryModel {
    private static final String TAG = "GalleryModel";
    private static GalleryModel instance = new GalleryModel();

    private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
    private List<String> groupList = new ArrayList<>();
    private List<ImageBean> list = new ArrayList<ImageBean>();

    public List<String> getImageListOfAlbum(String album) {
        return mGruopMap.get(album);
    }

    public String getFirstImagePathOfAlbum(String albumName) {
        List<String> paths = mGruopMap.get(albumName);
        if (paths == null || paths.size() == 0) return null;
        return paths.get(0);
    }


    public interface Callback {
        void onLoadFinished();
    }


    private GalleryModel() {
    }

    public static GalleryModel getInstance() {
        return instance;
    }

    public void loadImages(final Context context, final Callback callback) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = context.getContentResolver();

                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);

                if (mCursor == null) {
                    return;
                }

                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    Log.d(TAG, "run: path:" + path);

                    //获取该图片的父路径名
                    String parentName = new File(path).getParentFile().getName();


                    //根据父路径名将图片放入到mGruopMap中
                    if (!mGruopMap.containsKey(parentName)) {
                        groupList.add(parentName);
                        List<String> chileList = new ArrayList<String>();
                        chileList.add(path);
                        mGruopMap.put(parentName, chileList);
                    } else {
                        mGruopMap.get(parentName).add(path);
                    }
                }

                //通知Handler扫描图片完成
                if (callback != null) {
                    callback.onLoadFinished();
                }
                mCursor.close();
            }
        }).start();
    }

    public List<String> getAlbumList() {
        Log.d(TAG, "handleMessage: group size:" + mGruopMap.size() + ", groupList.size:" + groupList);
//        List<String> list = new ArrayList<>();
//        for (String s : groupList) {
//            list.add(mGruopMap.get(s).get(0));
//        }

        return groupList;
    }
}
