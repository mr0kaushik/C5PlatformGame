package com.app_services.mr_kaushik.c5platformgame.game_objects.enemies;

import android.content.Context;

import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsGameObject;
import com.app_services.mr_kaushik.c5platformgame.enums.Direction;
import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;

public class Guard extends AbsGameObject {
    private static final String TAG = "Guard";
    // Guards just move on x axis between 2 waypoints
    private float leftWayPoint;// always on left
    private float rightWayPoint;// always on right
    private Direction currentDirection;
    final float MAX_X_VELOCITY = 3;
    public int health = 3;
    private int pixelsPerMetre;

    public Guard(Context context, float worldStartX,
                 float worldStartY,
                 int pixelsPerMetre) {
        this.pixelsPerMetre = pixelsPerMetre;
        final int ANIMATION_FPS = 8;
        final int ANIMATION_FRAME_COUNT = 5;
        final float HEIGHT = 2f;
        final float WIDTH = 1;
        setHeight(HEIGHT); // 2 metre tall
        setWidth(WIDTH); // 1 metres wide

        setGameObjectType(GameObjectType.GUARD);
        setBitmapName("guard");
        // Now for the player's other attributes
        // Our game engine will use these
        setMove(true);
        setActive(true);
        setVisible(true);

        setAnimationFps(ANIMATION_FPS);
        setAnimationFrameCount(ANIMATION_FRAME_COUNT);
        setAnimated(context, pixelsPerMetre, true);

        setObjectLocation(worldStartX, worldStartY, 0);
        setXVelocity(-MAX_X_VELOCITY);
        currentDirection = Direction.LEFT;
    }

    @Override
    public void update(long fps, float gravity) {

        if (currentDirection == Direction.LEFT) {
            if (getObjectLocation().x <= leftWayPoint) {
                currentDirection = Direction.RIGHT;
                setXVelocity(MAX_X_VELOCITY);
                setFacing(Direction.RIGHT);
            }
        }

        if (currentDirection == Direction.RIGHT) {
            if (getObjectLocation().x >= rightWayPoint) {
                currentDirection = Direction.LEFT;
                setXVelocity(-MAX_X_VELOCITY);
                setFacing(Direction.LEFT);
            }
        }
        // if there is only tile i.e left == right == (x of guard) then
        // set guard velocity to 0
        if(leftWayPoint == rightWayPoint && rightWayPoint == (int) getObjectLocation().x){
            setXVelocity(0);
        }
        move(fps);
        setObjectHitBox();
    }

    public void setWayPoints(float x1, float x2) {
        leftWayPoint = x1;
        rightWayPoint = x2;
    }

}