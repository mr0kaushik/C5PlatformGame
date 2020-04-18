package com.app_services.mr_kaushik.c5platformgame.game_objects.enemies;

import android.content.Context;

import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsGameObject;
import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;

public class Fire extends AbsGameObject {




    public Fire(Context context, float worldStartX,
                 float worldStartY,
                 int pixelsPerMetre) {

        final int ANIMATION_FPS = 3;
        final int ANIMATION_FRAME_COUNT = 3;
        final String BITMAP_NAME = "fire";
        final float HEIGHT = 1f;
        final float WIDTH = 1f;
        setHeight(HEIGHT); // 1 metre tall
        setWidth(WIDTH); // 1 metres wide

        setGameObjectType(GameObjectType.FIRE);
        setBitmapName(BITMAP_NAME);
        // Now for the player's other attributes
        // Our game engine will use these
        setMove(false);
        setActive(true);
        setVisible(true);

        setAnimationFps(ANIMATION_FPS);
        setAnimationFrameCount(ANIMATION_FRAME_COUNT);
        setAnimated(context, pixelsPerMetre, true);

        setObjectLocation(worldStartX, worldStartY, 0);
        setObjectHitBox();
    }


    @Override
    public void update(long fps, float gravity) {

    }
}
