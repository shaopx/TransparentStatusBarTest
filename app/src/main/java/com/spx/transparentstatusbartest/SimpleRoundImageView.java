package com.spx.transparentstatusbartest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @auther shaopx
 * @date 2017/7/23.
 * @// TODO: 2017/7/31  问题是会出现锯齿问题
 */

public class SimpleRoundImageView extends android.support.v7.widget.AppCompatImageView {

    private static final float RADIUS = 12F;
    private float[] rids = {RADIUS,RADIUS,RADIUS,RADIUS,RADIUS,RADIUS,RADIUS,RADIUS,};

    public SimpleRoundImageView(Context context) {
        super(context);
    }

    public SimpleRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
        path.addRoundRect(new RectF(0,0,w,h),rids,Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
