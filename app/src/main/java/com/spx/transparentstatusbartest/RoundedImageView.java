package com.spx.transparentstatusbartest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * @auther shaopx
 * @date 2017/7/23.
 * @deprecated
 */

public class RoundedImageView extends android.support.v7.widget.AppCompatImageView {

    private static final String TAG = "RoundedImageView";
    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] rids = {9.0f, 9.0f, 19.0f, 9.0f, 9.0f, 9.0f, 9.0f, 9.0f,};

    /**
     * 圆角大小的默认值
     */
    private static final int BODER_RADIUS_DEFAULT = 10;
    /**
     * 圆角的大小
     */
    private int mBorderRadius;

    /**
     * 绘图的Paint
     */
    private Paint mBitmapPaint;
    /**
     * 圆角的半径
     */
    private int mRadius = 9;
    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private Matrix mMatrix;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader mBitmapShader;

    /**
     * view的宽度
     */
    private int mWidth;
    private RectF mRoundRect;

    public RoundedImageView(Context context) {
        this(context, null);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);

        mBorderRadius = 30;
    }


    public int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 画图
     * by Hankkin at:2015-08-30 21:15:53
     *
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        setUpShader();

        canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius,
                mBitmapPaint);
    }

    private void setUpShader() {
        if(mBitmapShader!=null){
            return ;
        }
        Drawable drawable = getDrawable();
        Log.d(TAG, "setUpShader: drawable:"+drawable);
        if (drawable == null) {
            return;
        }

        Bitmap bmp = drawableToBitamp(drawable);
        Bitmap newBitmap = big(bmp);
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        mBitmapShader = new BitmapShader(newBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
        scale = Math.max(getWidth() * 1.0f / newBitmap.getWidth(), getHeight()
                * 1.0f / newBitmap.getHeight());
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        Log.d(TAG, "setUpShader: scale:"+scale);
        mMatrix.setScale(scale, scale);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        // 设置shader
        mBitmapPaint.setShader(mBitmapShader);
    }

    private  Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        float scale = 1.0f;
        scale = Math.max(getWidth() * 1.0f / bitmap.getWidth(), getHeight()
                * 1.0f / bitmap.getHeight());
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        Log.d(TAG, "big: scale:"+scale);
        matrix.postScale(scale,scale); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 圆角图片的范围
        mRoundRect = new RectF(0, 0, getWidth(), getHeight());
    }
}
