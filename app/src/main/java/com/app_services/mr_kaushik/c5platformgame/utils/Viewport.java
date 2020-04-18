package com.app_services.mr_kaushik.c5platformgame.utils;

import android.graphics.Rect;

import com.app_services.mr_kaushik.c5platformgame.model.Vector2Point5D;
/*
 * View port will define the area of the game world,
 * that has to be shown to the player
 * */


public class Viewport {

    private Vector2Point5D mCurrentViewportCentreLocation;
    private Rect mConvertedRect;

    private int mPixelsPerMetreX;
    private int mPixelsPerMetreY;

    private int mScreenWidth;
    private int mScreenHeight;

    private int mScreenCentreX;
    private int mScreenCentreY;

    private int mMetresToShowX;
    private int mMetresToShowY;

    private int mNumClipped = 0;

    public Viewport(int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;
        mScreenCentreX = mScreenWidth / 2;
        mScreenCentreY = mScreenHeight / 2;
        mPixelsPerMetreX = mScreenWidth / 32;
        mPixelsPerMetreY = mScreenHeight / 18;
        mMetresToShowX = 34;
        mMetresToShowY = 20;

        mConvertedRect = new Rect();

    }

    public void setViewportWorldCentre(float x, float y) {
        mCurrentViewportCentreLocation = new Vector2Point5D(x, y);
    }


    public int getPixelsPerMetreX() {
        return mPixelsPerMetreX;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }

    public Rect worldToScreen(float objectX, float objectY, float objectWidth, float objectHeight) {

        int left = (int) (mScreenCentreX - ((mCurrentViewportCentreLocation.x - objectX) * mPixelsPerMetreX));
        int top = (int) (mScreenCentreY - ((mCurrentViewportCentreLocation.y - objectY) * mPixelsPerMetreY));
        int right = (int) (left + (objectWidth * mPixelsPerMetreX));
        int bottom = (int) (top + (objectHeight * mPixelsPerMetreY));
        mConvertedRect.set(left, top, right, bottom);
        return mConvertedRect;
    }

    /**
     * check for clipping of an object
     *
     * @param objectX x coordinate of object
     * @param objectY y coordinate of object
     * @param objectWidth width of object
     * @param objectHeight height of object
     * @return true if object can be clipped, else false
     */
    public boolean isObjectClipable(float objectX, float objectY, float objectWidth, float objectHeight) {
        boolean clipped = true;
        double midX = mMetresToShowX / 2.0;
        double midY = mMetresToShowY / 2.0;
        if ((objectX - objectWidth < mCurrentViewportCentreLocation.x + midX)
                && (objectX + objectWidth > mCurrentViewportCentreLocation.x - midX)
                && (objectY - objectHeight < mCurrentViewportCentreLocation.y + midY)
                && (objectY + objectHeight > mCurrentViewportCentreLocation.y - midY)) {
            clipped = false;
        }

        if (clipped) {
            mNumClipped++;
        }
        return clipped;
    }

    public int getCentreY(){
        return mScreenCentreY;
    }
    public float getViewportWorldCentreY(){
        return mCurrentViewportCentreLocation.y;
    }


    public int getClippedObjectsCount(){
        return mNumClipped;
    }
    public void resetClippedObjectCounts(){
        mNumClipped = 0;
    }

}
