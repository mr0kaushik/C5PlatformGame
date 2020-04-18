package com.app_services.mr_kaushik.c5platformgame.game_objects.enemies;

import android.graphics.PointF;

import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsGameObject;
import com.app_services.mr_kaushik.c5platformgame.enums.Direction;
import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;
import com.app_services.mr_kaushik.c5platformgame.model.Vector2Point5D;

public class Drone extends AbsGameObject {
    long lastWaypointSetTime;
    PointF currentWaypoint;
    final float MAX_X_VELOCITY = 3;
    final float MAX_Y_VELOCITY = 3;

    public Drone(float worldStartX, float worldStartY) {
        final float HEIGHT = 1;
        final float WIDTH = 1;
        setHeight(HEIGHT); // 1 metre tall
        setWidth(WIDTH); // 1 metres wide

        setGameObjectType(GameObjectType.DRONE);
        setBitmapName("drone");
        setMove(true);

        setActive(true);
        setVisible(true);
        currentWaypoint = new PointF();

        // Where does the drone start
        // X and y locations from constructor parameters
        setObjectLocation(worldStartX, worldStartY, 0);
        setObjectHitBox();
        setFacing(Direction.RIGHT);

    }


    @Override
    public void update(long fps, float gravity) {
        if (currentWaypoint.x > getObjectLocation().x) {
            setXVelocity(MAX_X_VELOCITY);
        } else if (currentWaypoint.x < getObjectLocation().x) {
            setXVelocity(-MAX_X_VELOCITY);
        } else {
            setXVelocity(0);
        }
        if (currentWaypoint.y >= getObjectLocation().y) {
            setYVelocity(MAX_Y_VELOCITY);
        } else if (currentWaypoint.y < getObjectLocation().y) {
            setYVelocity(-MAX_Y_VELOCITY);
        } else {
            setYVelocity(0);
        }
        move(fps);
        // update the drone hitbox
        setObjectHitBox();

    }

    public void setWaypoint(Vector2Point5D playerLocation) {
        if (System.currentTimeMillis() > lastWaypointSetTime + 2000) {//Has 2 seconds passed
            lastWaypointSetTime = System.currentTimeMillis();
            currentWaypoint.x = playerLocation.x;
            currentWaypoint.y = playerLocation.y;
        }
    }
}