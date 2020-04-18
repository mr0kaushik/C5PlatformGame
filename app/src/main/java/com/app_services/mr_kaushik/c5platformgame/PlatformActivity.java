package com.app_services.mr_kaushik.c5platformgame;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

public class PlatformActivity extends AppCompatActivity {
    private PlatformView platformView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point resolution = new Point();
        display.getSize(resolution);

        platformView = new PlatformView(this, resolution.x, resolution.y);

        setContentView(platformView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        platformView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        platformView.pause();
    }
}