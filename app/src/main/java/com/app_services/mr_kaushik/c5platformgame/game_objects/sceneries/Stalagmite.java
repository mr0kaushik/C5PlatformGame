package com.app_services.mr_kaushik.c5platformgame.game_objects.sceneries;

import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;
import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsScenery;

public class Stalagmite extends AbsScenery {
    public Stalagmite(float worldStartX, float worldStartY) {
        super(worldStartX, worldStartY);
        final float HEIGHT = 3f;
        final float WIDTH = 2f;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setGameObjectType(GameObjectType.STALAGMITE);
        setBitmapName("stalagmite");
    }
}
