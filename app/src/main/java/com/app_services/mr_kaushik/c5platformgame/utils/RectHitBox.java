package com.app_services.mr_kaushik.c5platformgame.utils;

public class RectHitBox {


    public float top;
    public float left;
    public float bottom;
    public float right;
    public float height;

    public boolean intersects(RectHitBox rectHitbox) {
        boolean hit = false;
        if (this.right > rectHitbox.left && this.left < rectHitbox.right
                && this.top < rectHitbox.bottom && this.bottom > rectHitbox.top) {
            // Collision
            hit = true;
        }
        return hit;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}