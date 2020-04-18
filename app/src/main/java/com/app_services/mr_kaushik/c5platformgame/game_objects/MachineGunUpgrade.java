package com.app_services.mr_kaushik.c5platformgame.game_objects;

import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsGameObject;
import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;

public class MachineGunUpgrade extends AbsGameObject {


    public MachineGunUpgrade(float worldStartX, float worldStartY) {
        final float HEIGHT = .5f;
        final float WIDTH = .5f;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setGameObjectType(GameObjectType.MACHINE_GUN_UPGRADE);
        setBitmapName("clip");
        setObjectLocation(worldStartX, worldStartY, 0);
        setObjectHitBox();
    }

    @Override
    public void update(long fps, float gravity) {

    }
}
