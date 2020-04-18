package com.app_services.mr_kaushik.c5platformgame.backgrounds;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Background {
    public Bitmap bitmap, reversedBitmap;
    public int height, width;
    public boolean isReversedFirst;
    public int xClip;
    public float y, endY;
    public int z;
    public float speed;
    public boolean isParallax;

    public Background(Context context, int yPixelsPerMetre,
                      int screenWidth, BackgroundData data) {
        int resID = context.getResources()
                .getIdentifier(data.bitmapName, "drawable", context.getPackageName());

        bitmap = BitmapFactory.decodeResource(context.getResources(), resID);

        // Which version of background (reversed or regular) is
        // currently drawn first (on left)
        isReversedFirst = false;

        //Initialize animation variables.
        xClip = 0; //always start at zero
        y = data.startY;
        endY = data.endY;
        z = data.layer;
        isParallax = data.isParallax;

        //Scrolling background speed
        speed = data.speed;
        //Scale background to fit the screen.
        bitmap = Bitmap.createScaledBitmap(bitmap, screenWidth,
                data.height * yPixelsPerMetre, true);

        width = bitmap.getWidth();
        height = bitmap.getHeight();
        // Create a mirror image of the background
        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1); //Horizontal mirror effect.
        reversedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                height, matrix, true);
    }
}
