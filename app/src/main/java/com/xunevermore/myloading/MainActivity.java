package com.xunevermore.myloading;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.xunevermore.myloading.activity.AnimIconActivity;
import com.xunevermore.myloading.customdrawable.RingDrawable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    public void goIcon(View view) {
        startActivity(new Intent(this, AnimIconActivity.class));
    }
}
