package com.app_services.mr_kaushik.c5platformgame.abstracts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.app_services.mr_kaushik.c5platformgame.enums.Direction;
import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;
import com.app_services.mr_kaushik.c5platformgame.model.Vector2Point5D;
import com.app_services.mr_kaushik.c5platformgame.utils.ObjectAnimation;
import com.app_services.mr_kaushik.c5platformgame.utils.RectHitBox;


public abstract class AbsGameObject {

    private Vector2Point5D objectLocation;
    private float mWidth;
    private float mHeight;

    private boolean isActive = true; // Game Object isActive
    private boolean isVisible = true; // Game Object isVisible
    private int mAnimationFrameCount = 1; // Default assigned FrameAnimationCount is 1
    private GameObjectType mObjectType;
    private float xVelocity, yVelocity;
    private Direction facing;
    private boolean canMove = false;

    private RectHitBox mHitBox = new RectHitBox();

    private String mBitmapName;

    private ObjectAnimation mAnimation = null;
    private boolean isAnimated;
    private int mAnimationFps = 1;


    private boolean traversable = false;


    public abstract void update(long fps, float gravity);


    public String getBitmapName() {
        return mBitmapName;
    }

    public Bitmap prepareBitmap(Context context, String bitmapName, int pixelsPerMetre) {
        int resId = context.getResources().getIdentifier(bitmapName, "drawable", context.getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (mWidth * mAnimationFrameCount * pixelsPerMetre), (int) (mHeight * pixelsPerMetre), false);
        return bitmap;
    }

    public Vector2Point5D getObjectLocation() {
        return objectLocation;
    }

    public void setObjectLocation(float x, float y, int z) {
        this.objectLocation = new Vector2Point5D(x, y, z);
    }

    public void setObjectHitBox() {
        mHitBox.setTop(objectLocation.y);
        mHitBox.setLeft(objectLocation.x);
        mHitBox.setBottom(objectLocation.y + mHeight);
        mHitBox.setRight(objectLocation.x + mWidth);
    }

    public RectHitBox getHitBox() {
        return mHitBox;
    }

    public void setBitmapName(String bitmapName) {
        this.mBitmapName = bitmapName;
    }

    public float getWidth() {
        return mWidth;
    }

    public void setWidth(float width) {
        this.mWidth = width;
    }

    public float getHeight() {
        return mHeight;
    }

    public void setHeight(float mHeight) {
        this.mHeight = mHeight;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public GameObjectType getGameObjectType() {
        return mObjectType;
    }

    public void setGameObjectType(GameObjectType mObjectType) {
        this.mObjectType = mObjectType;
    }

    public void move(long fps) {
        if (xVelocity != 0) {
            this.objectLocation.x += xVelocity / fps;
        }

        if (yVelocity != 0) {
            this.objectLocation.y += yVelocity / fps;
        }
    }

    public void setTraversable(){
        traversable = true;
    }
    public boolean isTraversable(){
        return traversable;
    }

    public Direction getFacing() {
        return facing;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    public float getXVelocity() {
        return xVelocity;
    }

    public void setXVelocity(float xVelocity) {
        // Only allow for objects that can move
        if (canMove) {
            this.xVelocity = xVelocity;
        }
    }

    public float getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(float yVelocity) {
        // Only allow for objects that can move
        if (canMove) {
            this.yVelocity = yVelocity;
        }
    }

    public boolean canMove() {
        return canMove;
    }

    public void setMove(boolean move) {
        this.canMove = move;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void setObjectLocationY(float y) {
        this.objectLocation.y = y;
    }

    public void setObjectLocationX(float x) {
        this.objectLocation.x = x;
    }

    public void setObjectLocationXY(float x, float y){
        this.objectLocation.x = x;
        this.objectLocation.y = y;
    }

    public void setObjectLocationZ(int z){
        this.objectLocation.z = z;
    }

    public void setAnimationFps(int animFps) {
        this.mAnimationFps = animFps;
    }

    public void setAnimationFrameCount(int animFrameCount) {
        this.mAnimationFrameCount = animFrameCount;
    }

    public boolean isAnimated() {
        return isAnimated;
    }
    public void setAnimated(Context context, int pixelsPerMetre,
                            boolean animated){

        this.isAnimated = animated;
        this.mAnimation = new ObjectAnimation(context, mBitmapName,
                mHeight,
                mWidth,
                mAnimationFps,
                mAnimationFrameCount,
                pixelsPerMetre );
    }

    public Rect getRectToDraw(long deltaTime){
        return mAnimation.getCurrentFrame(
                deltaTime,
                xVelocity,
                canMove);
    }

    public static boolean isTile(char ch) {
        if (ch != GameObjectType.SPACE.getCharacter()) {
            int value = Integer.parseInt(String.valueOf(ch));
            return value >= 1 && value <= 7;
        }
        return false;
    }
}

