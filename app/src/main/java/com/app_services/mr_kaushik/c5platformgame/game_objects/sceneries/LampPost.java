package com.app_services.mr_kaushik.c5platformgame.game_objects.sceneries;

import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;
import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsScenery;

public class LampPost extends AbsScenery {
    public LampPost(float worldStartX, float worldStartY) {
        super(worldStartX, worldStartY);
        final float HEIGHT = 3f;
        final float WIDTH = 1f;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setGameObjectType(GameObjectType.LAMP_POST);
        setBitmapName("lampost");
    }
}
