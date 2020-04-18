package com.app_services.mr_kaushik.c5platformgame.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class ObjectAnimation {
    Bitmap mBitmapSheet;
    String mBitmapName;
    private Rect mSourceRect;
    private int mFrameCount;
    private int mCurrentFrame;
    private long mFrameTicker;
    private int mFramePeriod;
    private int mFrameWidth;
    private int mFrameHeight;
    int mPixelsPerMetre;

    public ObjectAnimation(Context context, String bitmapName, float frameHeight,
                           float frameWidth, int animFps, int frameCount, int pixelsPerMetre) {
        this.mCurrentFrame = 0;
        this.mFrameCount = frameCount;
        this.mFrameWidth = (int) frameWidth * pixelsPerMetre;
        this.mFrameHeight = (int) frameHeight * pixelsPerMetre;
        mSourceRect = new Rect(0, 0, this.mFrameWidth,
                this.mFrameHeight);
        mFramePeriod = 1000 / animFps;
        mFrameTicker = 0L;
        this.mBitmapName = "" + bitmapName;
        this.mPixelsPerMetre = pixelsPerMetre;

    }

    public Rect getCurrentFrame(long time,
                                float xVelocity, boolean moves) {
        if (xVelocity != 0 || !moves) {
            // Only animate if the object is moving
            // or it is an object which doesn't move
            // but is still animated (like fire)

            if (time > mFrameTicker + mFramePeriod) {
                mFrameTicker = time;
                mCurrentFrame++;
                if (mCurrentFrame >= mFrameCount) {
                    mCurrentFrame = 0;
                }
            }
        }

        //update the left and right values of the source of
        //the next frame on the spritesheet
        this.mSourceRect.left = mCurrentFrame * mFrameWidth;
        this.mSourceRect.right = this.mSourceRect.left + mFrameWidth;
        return mSourceRect;

    }



}
