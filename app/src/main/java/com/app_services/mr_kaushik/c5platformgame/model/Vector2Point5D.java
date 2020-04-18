package com.app_services.mr_kaushik.c5platformgame.model;

public class Vector2Point5D {
    // x, y co-ordinate of object
    public float x, y;
    // z is for depth, the lowest z will draw behind
    public int z;


    public Vector2Point5D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2Point5D(float x, float y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
