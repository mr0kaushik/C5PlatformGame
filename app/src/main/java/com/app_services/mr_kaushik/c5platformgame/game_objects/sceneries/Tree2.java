package com.app_services.mr_kaushik.c5platformgame.game_objects.sceneries;

import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;
import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsTile;

public class Tree2 extends AbsTile {
    public Tree2(float worldStartX, float worldStartY) {
        super(worldStartX, worldStartY);
        final float HEIGHT = 4f;
        final float WIDTH = 2f;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setGameObjectType(GameObjectType.TREE_2);
        setBitmapName("tree2");
    }
}
