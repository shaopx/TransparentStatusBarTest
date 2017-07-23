package com.spx.transparentstatusbartest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/7/22.
 */

public class MainActivityFragment extends Fragment {
    private static final String TAG = "PictureFragment";
    String picturePath = null;
    String albumName = null;

    private View view;
    private ImageView imageView;

    public MainActivityFragment() {

    }

    public static MainActivityFragment createInstance(String albumName) {
        MainActivityFragment fragment = new MainActivityFragment();

        fragment.albumName = albumName;
        fragment.picturePath = GalleryModel.getInstance().getFirstImagePathOfAlbum(albumName);
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

        KApp application = (KApp) getActivity().getApplication();

        int reqWidth = application.getScreenWidth() / 3;
        int reqHeight = application.getScreenHeight() / 3;
        final Bitmap bitmap = Utils.decodeSampleBitmapFromFile(picturePath, reqWidth, reqHeight);
        if (picturePath != null && bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ..." + view);
                KApp application = (KApp) getActivity().getApplication();
                application.setBitmap(bitmap);
                ActivityOptionsCompat compat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imageView, "sss");
                Intent intent = new Intent(getActivity(), FullscreenActivity.class);
                intent.putExtra("album", albumName);
                ActivityCompat.startActivity(getContext(), intent,
                        compat.toBundle());
            }
        });

        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                Bitmap bitmap = createViewBitmap(imageView);
                bitmap = Utils.createRoundConerImage(bitmap, 9);
                imageView.setImageBitmap(bitmap);

                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });

        return view;
    }

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private void launch() {

    }

}
