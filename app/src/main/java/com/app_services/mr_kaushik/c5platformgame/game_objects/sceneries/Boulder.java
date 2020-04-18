package com.app_services.mr_kaushik.c5platformgame.game_objects.sceneries;

import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;
import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsScenery;

public class Boulder extends AbsScenery {
    public Boulder(float worldStartX, float worldStartY) {
        super(worldStartX, worldStartY);
        final float HEIGHT = 1f;
        final float WIDTH = 3f;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setGameObjectType(GameObjectType.BOULDERS);
        setBitmapName("boulder");
//        setTraversable();
//        setObjectLocation(worldStartX, worldStartY, 0);
//        setObjectHitBox();
    }
}
