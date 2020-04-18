package com.app_services.mr_kaushik.c5platformgame.game_objects;

import android.content.Context;

import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsGameObject;
import com.app_services.mr_kaushik.c5platformgame.enums.Direction;
import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;
import com.app_services.mr_kaushik.c5platformgame.enums.PlayerCollision;
import com.app_services.mr_kaushik.c5platformgame.guns.MachineGun;
import com.app_services.mr_kaushik.c5platformgame.model.Vector2Point5D;
import com.app_services.mr_kaushik.c5platformgame.utils.RectHitBox;
import com.app_services.mr_kaushik.c5platformgame.utils.SoundManager;

public class Player extends AbsGameObject {

    private static final String PLAYER_NAME = "player";
    private static final int PLAYER_HEIGHT = 2; // 2m tall
    private static final int PLAYER_WIDTH = 1; // 1m wide
    final int ANIMATION_FPS = 16;
    final int ANIMATION_FRAME_COUNT = 5;

    final float MAX_X_VELOCITY = 10;
    private boolean isPressingRight = false;
    private boolean isPressingLeft = false;

    public boolean isFalling, isJumping;
    private long jumpTime;
    private long maxJumpTime = 1000;

    public MachineGun gun;

    RectHitBox mHeadHitBox, mFeetHitBox;
    RectHitBox mLeftHitBox, mRightHitBox;

    public Player(Context context, float worldStartX, float worldStartY, int pixelsPerMetre) {
        setHeight(PLAYER_HEIGHT);
        setWidth(PLAYER_WIDTH);

        gun = new MachineGun();

        setXVelocity(0);
        setYVelocity(0);
        setFacing(Direction.LEFT);
        isFalling = false;

        setMove(true);
        setActive(true);
        setVisible(true);

        // Set object type
        setGameObjectType(GameObjectType.PLAYER);
        // Set player name
        setBitmapName(PLAYER_NAME);
        // Set animation
        setAnimationFps(ANIMATION_FPS);
        setAnimationFrameCount(ANIMATION_FRAME_COUNT);
        setAnimated(context, pixelsPerMetre, true);
        // Set object location
        setObjectLocation(worldStartX, worldStartY, 0);

        //set hit boxes
        mHeadHitBox = new RectHitBox();
        mFeetHitBox = new RectHitBox();
        mLeftHitBox = new RectHitBox();
        mRightHitBox = new RectHitBox();

    }

    @Override
    public void update(long fps, float gravity) {
        // changing object velocity
        if (isPressingRight) {
            this.setXVelocity(MAX_X_VELOCITY);
        } else if (isPressingLeft) {
            this.setXVelocity(-1 * MAX_X_VELOCITY);
        } else {
            this.setXVelocity(0);
        }
        // Changing face direction and movement in x axis
        if (this.getXVelocity() > 0) {
            setFacing(Direction.RIGHT);
        } else if (this.getXVelocity() < 0) {
            setFacing(Direction.LEFT);
        }

        // movement along y asix
        if (isJumping) {
            long timeJumping = System.currentTimeMillis() - jumpTime;
            if (timeJumping < maxJumpTime) {
                if (timeJumping < maxJumpTime / 2) {
                    this.setYVelocity(-gravity); // going up
                } else if (timeJumping > maxJumpTime / 2) {
                    this.setYVelocity(gravity); //going down
                }
            } else {
                isJumping = false;
            }
        } else {
            this.setYVelocity(gravity);
            // Remove this next line to make the game easier
            // it means the long jumps are less punishing
            // because the player can take off just after the platform
            // They will also be able to cheat by jumping in thin air
            isFalling = true;
        }
        gun.update(fps, gravity);

        this.move(fps);

        Vector2Point5D location = getObjectLocation();
        float lX = location.x;
        float lY = location.y;

        // FEET Hit Box
        mFeetHitBox.top = lY + getHeight() * .95f;
        mFeetHitBox.left = lX + getWidth() * .2f;
        mFeetHitBox.bottom = lY + getHeight() * .98f;
        mFeetHitBox.right = lX + getWidth() * .8f;


        // HEAD Hit Box
        mHeadHitBox.top = lY;
        mHeadHitBox.left = lX + getWidth() * .4f;
        mHeadHitBox.bottom = lY + getHeight() * .2f;
        mHeadHitBox.right = lX + getWidth() * .6f;

        // LFFt Hit Box
        mLeftHitBox.top = lY + getHeight() * .2f;
        mLeftHitBox.left = lX + getWidth() * .2f;
        mLeftHitBox.bottom = lY + getHeight() * .8f;
        mLeftHitBox.right = lX + getWidth() * .3f;

        // RIGHT Hit Box
        mRightHitBox.top = lY + getHeight() * .2f;
        mRightHitBox.left = lX + getWidth() * .8f;
        mRightHitBox.bottom = lY + getHeight() * .8f;
        mRightHitBox.right = lX + getWidth() * .7f;


    }

    public PlayerCollision checkCollisions(RectHitBox hitbox) {
        // Collision with left
        if (this.mLeftHitBox.intersects(hitbox)) {
            // Move player just to right of current hitbox
            this.setObjectLocationX(hitbox.right - getWidth() * .2f);
            return PlayerCollision.LEFT;
        }
        // Collision with right
        if (this.mRightHitBox.intersects(hitbox)) {
            // Move player just to left of current hit box
            this.setObjectLocationX(hitbox.left - getWidth() * .8f);
            return PlayerCollision.RIGHT;
        }
        // Collision with feet
        if (this.mFeetHitBox.intersects(hitbox)) {
            // Move feet to just above current hit box
            this.setObjectLocationY(hitbox.top - getHeight());
            return PlayerCollision.FEET;
        }
        // Collision with head
        if (this.mHeadHitBox.intersects(hitbox)) {
            // Move head to just below current hit box bottom
            this.setObjectLocationY(mHeadHitBox.bottom);
            return PlayerCollision.HEAD;
        }


        return PlayerCollision.NONE;
    }

    public boolean isPressingRight() {
        return isPressingRight;
    }

    public void setPressingRight(boolean pressingRight) {
        isPressingRight = pressingRight;
    }

    public boolean isPressingLeft() {
        return isPressingLeft;
    }

    public void setPressingLeft(boolean pressingLeft) {
        isPressingLeft = pressingLeft;
    }

    public void startJump(SoundManager sm) {
        if (!isFalling) {//can't jump if falling
            if (!isJumping) {//not already jumping
                isJumping = true;
                jumpTime = System.currentTimeMillis();
                sm.playSound(SoundManager.SoundType.JUMP);
            }
        }
    }

    public boolean pullTrigger(){
        return  gun.shoot(this.getObjectLocation().x, this.getObjectLocation().y, this.getFacing(), getHeight());
    }

    /**
     * Use it when player collides with collectable items
     */
    public void restorePreviousVelocity() {
        if (!isJumping && !isFalling) {
            if (getFacing() == Direction.LEFT) {
                isPressingLeft = true;
                setXVelocity(-MAX_X_VELOCITY);
            } else {
                isPressingRight = true;
                setXVelocity(MAX_X_VELOCITY);
            }
        }
    }
}
