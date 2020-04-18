package com.app_services.mr_kaushik.c5platformgame.game_objects.tiles;

import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;
import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsTile;

public class Coal extends AbsTile {
    public Coal(float worldStartX, float worldStartY) {
        super(worldStartX, worldStartY);
        setGameObjectType(GameObjectType.COAL);
        setBitmapName("coal");
    }
}
