package com.app_services.mr_kaushik.c5platformgame.abstracts;

import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsGameObject;

public abstract class AbsTile extends AbsGameObject {
    protected AbsTile(float worldStartX, float worldStartY) {
        //make tile traverse-able
        setTraversable();
        //set tile height & width
        final float HEIGHT = 1;
        final float WIDTH = 1;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        //set object location
        setObjectLocation(worldStartX, worldStartY, 0);
        //set object hit box
        setObjectHitBox();
    }

    @Override
    public void update(long fps, float gravity) {
        // No need to add anything as they will not animate
    }
}
