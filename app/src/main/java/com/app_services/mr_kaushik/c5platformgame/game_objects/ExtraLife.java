package com.app_services.mr_kaushik.c5platformgame.game_objects;

import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsGameObject;
import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;

public class ExtraLife extends AbsGameObject {

    public ExtraLife(float worldStartX, float worldStartY) {
        final float HEIGHT = .8f;
        final float WIDTH = .65f;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setGameObjectType(GameObjectType.EXTRA_LIFE);
        setBitmapName("life");
        setObjectLocation(worldStartX, worldStartY, 0);
        setObjectHitBox();
    }


    @Override
    public void update(long fps, float gravity) {

    }
}
