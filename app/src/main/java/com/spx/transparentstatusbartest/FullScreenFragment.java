package com.spx.transparentstatusbartest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        view = inflater.inflate(R.layout.picture_fragment_layout, container, false);
        imageView = view.findViewById(R.id.imageView);
        Log.d(TAG, "onCreateView: ....position:" + position+", picturePath:"+picturePath);
        KApp application = (KApp) getActivity().getApplication();

        int reqWidth = application.getScreenWidth() / 3;
        int reqHeight = application.getScreenHeight() / 3;
        bitmap = Utils.decodeSampleBitmapFromFile(picturePath, reqWidth, reqHeight);
        if (picturePath != null && bitmap != null) {
            imageView.setImageBitmap(bitmap);
            application.setBitmap(bitmap);
        }


//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: ..." + view);
//                KApp application = (KApp) getActivity().getApplication();
//                application.setBitmap(bitmap);
//                ActivityOptionsCompat compat =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imageView, "sss");
//                Intent intent = new Intent(getActivity(), Actvity1.class);
//                intent.putExtra("album", albumName);
//                ActivityCompat.startActivity(getContext(), intent,
//                        compat.toBundle());
//            }
//        });

        return view;
    }

    private void launch() {

    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
