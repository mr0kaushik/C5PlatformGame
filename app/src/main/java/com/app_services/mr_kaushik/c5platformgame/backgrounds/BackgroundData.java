package com.app_services.mr_kaushik.c5platformgame.backgrounds;

public class BackgroundData {
    String bitmapName;
    boolean isParallax;
    int layer; // layer 0 is map
    float startY, endY;
    float speed;
    int height, width;

    public BackgroundData(String bitmapName, boolean isParallax, int layer,
                          float startY, float endY, float speed, int height) {
        this.bitmapName = bitmapName;
        this.isParallax = isParallax;
        this.layer = layer;
        this.startY = startY;
        this.endY = endY;
        this.speed = speed;
        this.height = height;
    }
}
