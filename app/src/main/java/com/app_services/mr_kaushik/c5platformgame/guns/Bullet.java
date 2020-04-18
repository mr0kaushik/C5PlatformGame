package com.app_services.mr_kaushik.c5platformgame.guns;

import com.app_services.mr_kaushik.c5platformgame.enums.Direction;

public class Bullet {
    private float x, y;
    private float mSpeed;
    private Direction mDirection;

    Bullet(float x, float y, int speed, Direction direction){
        this.x = x;
        this.y = y;
        this.mSpeed = speed;
        this.mDirection = direction;
    }

    public Direction getDirection(){
        return mDirection;
    }

    public void update(long fps, float gravity){
        x += mSpeed / fps;
    }

    public void hideBullet(){
        this.x = -100;
        this.mSpeed = 0;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
}
