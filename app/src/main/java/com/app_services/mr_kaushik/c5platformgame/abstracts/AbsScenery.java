package com.app_services.mr_kaushik.c5platformgame.abstracts;

import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsGameObject;

import java.util.Random;

public abstract class AbsScenery extends AbsGameObject {
    protected AbsScenery(float worldStartX, float worldStartY) {
        //set object active
        setActive(false);
        //set object location
        Random random = new Random();
        if (random.nextBoolean()) {
            setObjectLocation(worldStartX, worldStartY, -1);
        } else {
            setObjectLocation(worldStartX, worldStartY, 1);
        }
    }

    @Override
    public void update(long fps, float gravity) {
        // No need to add anything as they will not animate
    }
}
