package com.app_services.mr_kaushik.c5platformgame.game_objects;

import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsGameObject;
import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;

public class Coin extends AbsGameObject {

    public Coin(float worldStartX, float worldStartY) {
        final String COIN_BITMAP_NAME = "coin";
        final float HEIGHT = .5f;
        final float WIDTH = .5f;

        setHeight(HEIGHT);
        setWidth(WIDTH);
        setGameObjectType(GameObjectType.COIN);
        setBitmapName(COIN_BITMAP_NAME);
        setObjectLocation(worldStartX, worldStartY, 0);
        setObjectHitBox();
    }


    @Override
    public void update(long fps, float gravity) {

    }
}
