package com.app_services.mr_kaushik.c5platformgame.game_objects.sceneries;

import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;
import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsScenery;

public class Tree extends AbsScenery {
    public Tree(float worldStartX, float worldStartY) {
        super(worldStartX, worldStartY);
        final float HEIGHT = 4f;
        final float WIDTH = 2f;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setGameObjectType(GameObjectType.TREE);
        setBitmapName("tree1");
    }
}
