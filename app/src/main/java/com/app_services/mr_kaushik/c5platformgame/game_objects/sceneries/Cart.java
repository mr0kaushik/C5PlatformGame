package com.app_services.mr_kaushik.c5platformgame.game_objects.sceneries;

import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;
import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsScenery;

public class Cart extends AbsScenery {
    public Cart(float worldStartX, float worldStartY) {
        super(worldStartX, worldStartY);
        final float HEIGHT = 2f;
        final float WIDTH = 3f;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setGameObjectType(GameObjectType.MINE_CART);
        setBitmapName("cart");
//        setTraversable();
//        setObjectLocation(worldStartX, worldStartY, 0);
//        setObjectHitBox();
    }
}
