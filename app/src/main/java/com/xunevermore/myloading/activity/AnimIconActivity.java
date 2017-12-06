package com.xunevermore.myloading.activity;

import android.animation.ObjectAnimator;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xunevermore.myloading.R;

public class AnimIconActivity extends AppCompatActivity {

    private static final String TAG = "AnimIconActivity";
    private float diatance;
    private float translationY;
    private float translationX;
    private int[] bigLocation;
    private FrameLayout fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_icon);

        final ImageView ivSmall = (ImageView) findViewById(R.id.iv_small);
        final ImageView ivBig = (ImageView) findViewById(R.id.iv_big);

        final NestedScrollView nestScrollView = (NestedScrollView) findViewById(R.id.nest_scroll_view);

        ivBig.post(new Runnable() {
            @Override
            public void run() {
                int[] smallLocaion = new int[2];
                ivSmall.getLocationInWindow(smallLocaion);

                bigLocation = new int[2];
                ivBig.getLocationInWindow(bigLocation);

                diatance = Math.abs(smallLocaion[1]- bigLocation[1]);
//                diatance=ivBig.getTop();
                Log.i(TAG, "diatance: "+diatance);

                Log.i(TAG, "smallLocaion[1]: "+smallLocaion[1]);

                translationX = smallLocaion[0] - bigLocation[0];
                Log.i(TAG, "run: translationX"+translationX);

                translationY = smallLocaion[1] - bigLocation[1];


            }
        });

        ivSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivBig.setTranslationX(translationX);
                ivBig.setTranslationY(translationY);
            }
        });
        ivBig.setPivotX(0);
        ivBig.setPivotY(0);

        fl = (FrameLayout) findViewById(R.id.fl_bar);
        fl.setAlpha(0);
        nestScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {



            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                View child = nestScrollView.getChildAt(0);
                int height = child.getHeight();
                int height1 = nestScrollView.getHeight();

                int top = Math.abs(nestScrollView.getScrollY());
                if (height - height1 >= diatance) {

                    if (top <= diatance) {
                        Log.i(TAG, "onScrollChange: " + scrollY);

                        float percent = top / diatance;

                        float widthBig = ivBig.getWidth();
                        float heightBig = ivBig.getHeight();


                        float widthSmall = ivSmall.getWidth();
                        float heightSmall = ivSmall.getHeight();


                        ivBig.setScaleX(percent * (widthSmall / widthBig - 1) + 1);
                        ivBig.setScaleY(percent * (heightSmall / heightBig - 1) + 1);

                        Log.i(TAG, "percent: "+percent);

                        int[] bigLocation = new int[2];
                        ivBig.getLocationInWindow(bigLocation);

                        Log.i(TAG, "bigLocation:y "+bigLocation[1]);

                        fl.setAlpha(percent);
                        ivBig.setAlpha(1-percent+0.2f);
                        float x = (translationX) * percent;
                        ivBig.setTranslationX(x);



                        ivSmall.setVisibility(View.INVISIBLE);
                    }else {

                        if(ivSmall.getVisibility()!=View.VISIBLE){
                            fl.setAlpha(1);
                            ivSmall.setVisibility(View.VISIBLE);
                            ObjectAnimator rotation = ObjectAnimator.ofFloat(ivSmall, "rotation", -60, 60, 0)
                                    .setDuration(400);
                            rotation.setInterpolator(new OvershootInterpolator());
                            rotation.start();

                        }

                    }
                }
            }
        });
    }



}
