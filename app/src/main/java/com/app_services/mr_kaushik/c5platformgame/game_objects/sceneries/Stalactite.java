package com.app_services.mr_kaushik.c5platformgame.game_objects.sceneries;

import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;
import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsScenery;

public class Stalactite extends AbsScenery {
    public Stalactite(float worldStartX, float worldStartY) {
        super(worldStartX, worldStartY);
        final float HEIGHT = 3f;
        final float WIDTH = 2f;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setGameObjectType(GameObjectType.STALACTITE);
        setBitmapName("stalactite");
        setObjectLocationZ(-1);
    }
}
