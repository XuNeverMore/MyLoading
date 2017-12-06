package com.xunevermore.myloading.customdrawable;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.LinearInterpolator;
import android.widget.ViewAnimator;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class RingDrawable extends Drawable implements Animatable {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int[] colors;
    private boolean isRunning = false;
    private int degree;
    private ObjectAnimator animator;

    public RingDrawable(int[] colors) {
        this.colors = colors;
        initPaint();
    }

    private void initPaint() {
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(24);


        animator = ObjectAnimator.ofInt(this, "degree", 0, 360);

        animator.setDuration(700);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());


    }

    public void setDegree(int degree) {
        this.degree = degree;
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if(colors==null||colors.length==0){
            return;
        }
        Rect bounds = getBounds();


        canvas.save();
        canvas.rotate(degree,bounds.centerX(),bounds.centerY());
        RectF rectF = new RectF(bounds);


        float d = degree<=180?degree%180:359-degree;
        rectF.inset(20+d,20+d);
        float degree = 360f/colors.length;
        for (int i = 0; i < colors.length; i++) {

            mPaint.setColor(colors[i]);
            canvas.drawArc(rectF,i*degree,degree,true,mPaint);

        }

        canvas.restore();

    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }


    @Override
    public void start() {
        isRunning=true;
        animator.start();
    }

    @Override
    public void stop() {

        animator.cancel();
        isRunning=false;
        setDegree(0);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
