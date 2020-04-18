package com.app_services.mr_kaushik.c5platformgame.game_objects.tiles;

import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;
import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsTile;

public class Scorched extends AbsTile {
    public Scorched(float worldStartX, float worldStartY) {
        super(worldStartX, worldStartY);
        setGameObjectType(GameObjectType.SCORCHED);
        setBitmapName("scorched");
    }
}
