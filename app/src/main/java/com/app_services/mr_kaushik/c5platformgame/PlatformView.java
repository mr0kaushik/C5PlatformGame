package com.app_services.mr_kaushik.c5platformgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsGameObject;
import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsPlatformView;
import com.app_services.mr_kaushik.c5platformgame.backgrounds.Background;
import com.app_services.mr_kaushik.c5platformgame.enums.Direction;
import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;
import com.app_services.mr_kaushik.c5platformgame.enums.Levels;
import com.app_services.mr_kaushik.c5platformgame.enums.PlayerCollision;
import com.app_services.mr_kaushik.c5platformgame.game_objects.enemies.Drone;
import com.app_services.mr_kaushik.c5platformgame.game_objects.enemies.Guard;
import com.app_services.mr_kaushik.c5platformgame.guns.MachineGun;
import com.app_services.mr_kaushik.c5platformgame.level_utils.LevelManager;
import com.app_services.mr_kaushik.c5platformgame.model.PlayerState;
import com.app_services.mr_kaushik.c5platformgame.model.Vector2Point5D;
import com.app_services.mr_kaushik.c5platformgame.utils.InputController;
import com.app_services.mr_kaushik.c5platformgame.utils.RectHitBox;
import com.app_services.mr_kaushik.c5platformgame.utils.SoundManager;
import com.app_services.mr_kaushik.c5platformgame.utils.Viewport;

public class PlatformView extends AbsPlatformView implements Runnable {


    private boolean isDebugging = true;
    private volatile boolean isRunning;
    private Thread mGameThread = null;

    private Paint mPaint;

    private Canvas mCanvas;
    private Context mContext;
    private SurfaceHolder mSurfaceHolder;
    private int mScreenWidth, mScreenHeight;

    private PlayerState mPlayerState;

    long mFrameStartTime;
    long mThisFrameTime;
    long mFps;

    private LevelManager mLevelManager;
    private Viewport mViewport;
    private InputController mInputController;
    private SoundManager mSoundManager;

    PlatformView(Context context, int x, int y) {
        super(context);
        this.mContext = context;
        this.mScreenWidth = x;
        this.mScreenHeight = y;

        mPaint = new Paint();
        mSurfaceHolder = getHolder();

        mViewport = new Viewport(x, y);

        //load sound manager
        mSoundManager = new SoundManager();
        mSoundManager.loadSound(context);

        mPlayerState = new PlayerState();

        loadLevel(Levels.CAVE, 10, 2);

    }

    /**
     * This method will load the current level of player
     *
     * @param cLevel current level of player
     * @param pX     x coordinate of player
     * @param pY     y coordinate of player
     */
    private void loadLevel(Levels cLevel, int pX, int pY) {
        mInputController = new InputController(mScreenWidth, mScreenHeight);
        // saving player initial state
        mPlayerState.saveLocation(new PointF(pX, pY));


        mLevelManager = new LevelManager(mContext, mViewport.getPixelsPerMetreX(),
                mViewport.getScreenWidth(), mInputController, cLevel,
                pX, pY);

        // Set the players location as the world centre
        Vector2Point5D location = mLevelManager.getGameObjects()
                .get(mLevelManager.getPlayerIndex()).getObjectLocation();
        mViewport.setViewportWorldCentre(location.x, location.y);
    }


    @Override
    public void run() {
        while (isRunning) {
            mFrameStartTime = System.currentTimeMillis();
            update();
            draw();
            mThisFrameTime = System.currentTimeMillis() - mFrameStartTime;
            if (mThisFrameTime >= 1) {
                mFps = 1000 / mThisFrameTime;
            }
        }

    }

    @Override
    public void resume() {
        isRunning = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }


    @Override
    public void pause() {
        isRunning = false;
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("error", "failed to pause thread");
        }
    }

    @Override
    protected void update() {

        for (AbsGameObject object : mLevelManager.getGameObjects()) {
            if (object.isActive()) {
                Vector2Point5D oLocation = object.getObjectLocation();
                if (mViewport.isObjectClipable(oLocation.x, oLocation.y, object.getWidth(), object.getHeight())) {
                    object.setVisible(false); // do not render object
                } else {
                    object.setVisible(true); // render object

                    // check collisions with player
                    PlayerCollision collision = mLevelManager.getPlayer().checkCollisions(object.getHitBox());


                    if (collision != PlayerCollision.NONE) {
                        switch (object.getGameObjectType()) {
                            case COIN:
                                mSoundManager.playSound(SoundManager.SoundType.COIN_PICKUP);
                                object.setActive(false);
                                object.setVisible(false);
                                mPlayerState.gotCredit();
                                restoreVelocity(collision);
                                break;

                            case MACHINE_GUN_UPGRADE:
                                mSoundManager.playSound(SoundManager.SoundType.GUN_UPGRADE);
                                object.setActive(false);
                                object.setVisible(false);
                                mLevelManager.getPlayer().gun.upgradeRateOfFire();
                                mPlayerState.increaseFireRate();
                                restoreVelocity(collision);
                                break;

                            case EXTRA_LIFE:
                                mSoundManager.playSound(SoundManager.SoundType.EXTRA_LIFE);
                                object.setActive(false);
                                object.setVisible(false);
                                mPlayerState.addLife();
                                restoreVelocity(collision);
                                break;

                            case GUARD: // hit by guard
                            case DRONE: // hit by drone
                                PointF location;
                                mSoundManager.playSound(SoundManager.SoundType.PLAYER_BURN);
                                mPlayerState.loseLife();
                                location = new PointF(mPlayerState.loadLocation().x, mPlayerState.loadLocation().y);
                                mLevelManager.getPlayer().setObjectLocationXY(location.x, location.y);
                                mLevelManager.getPlayer().setXVelocity(0);
                                break;

                            case FIRE: // hit by fire
                                mSoundManager.playSound(SoundManager.SoundType.PLAYER_BURN);
                                mPlayerState.loseLife();
                                location = new PointF(mPlayerState.loadLocation().x, mPlayerState.loadLocation().y);
                                mLevelManager.getPlayer().setObjectLocationXY(location.x, location.y);
                                mLevelManager.getPlayer().setXVelocity(0);
                                break;

                            default:
                                switch (collision) {
                                    case LEFT:
                                    case RIGHT:
                                        mLevelManager.getPlayer().setXVelocity(0);
                                        mLevelManager.getPlayer().setPressingRight(false);
                                        break;
                                    case FEET:
                                        mLevelManager.getPlayer().isFalling = false;
                                        break;
//                                    case HEAD:
//                                        mLevelManager.getPlayer().isJumping = true;
//                                        break;
                                }
                                break;

                        }
                    }

                    // bullet collisions
                    for (int i = 0; i < mLevelManager.getPlayer().gun.getNumBullets(); i++) {
                        //creating hit box of current bullet
                        RectHitBox box = new RectHitBox();
                        box.setLeft(mLevelManager.getPlayer().gun.getBulletX(i));
                        box.setTop(mLevelManager.getPlayer().gun.getBulletY(i));
                        box.setRight(mLevelManager.getPlayer().gun.getBulletX(i) + .1f);
                        box.setBottom(mLevelManager.getPlayer().gun.getBulletY(i) + .1f);

                        if (object.getHitBox().intersects(box)) {
                            //collision detected make bullet disappear until it
                            // is re-spawned as a new bullet
                            mLevelManager.getPlayer().gun.hideBullet(i);

                            if (object.getGameObjectType() == GameObjectType.GUARD) {
//                                object.setObjectLocationX();
                                Guard guard = (Guard) object;
                                if (--guard.health == 0) {
                                    object.setObjectLocation(-100, -100, 0);
                                    mSoundManager.playSound(SoundManager.SoundType.EXPLODE);
//                                    mLevelManager.removeGameObject(guard.ge.getObjectLocation().x, guard.getObjectLocation().y);
                                } else {
                                    mSoundManager.playSound(SoundManager.SoundType.HIT_GUARD);
                                }

                            } else if (object.getGameObjectType() == GameObjectType.DRONE) {
                                mSoundManager.playSound(SoundManager.SoundType.EXPLODE);
                                object.setObjectLocation(-100, -100, 0);
                            } else {
                                mSoundManager.playSound(SoundManager.SoundType.RICOCHET);
                            }

                        }

                    }


                    if (mLevelManager.isPlaying()) {
                        object.update(mFps, mLevelManager.getGravity());

                        if (object.getGameObjectType() == GameObjectType.DRONE) {
                            ((Drone) object).setWaypoint(mLevelManager.getPlayer().getObjectLocation());
                        }

                    }
                }
            }
        }

        if (mLevelManager.isPlaying()) {
            Vector2Point5D playerLocation = mLevelManager.getGameObjects()
                    .get(mLevelManager.getPlayerIndex()).getObjectLocation();
            mViewport.setViewportWorldCentre(playerLocation.x, playerLocation.y);

        }

    }

    private void restoreVelocity(PlayerCollision collision) {
        if (collision != PlayerCollision.FEET) {
            mLevelManager.getPlayer().restorePreviousVelocity();
        }
    }

    @Override
    protected void draw() {
        if (mSurfaceHolder.getSurface().isValid()) {
            //locking canvas
            mCanvas = mSurfaceHolder.lockCanvas();

            mPaint.setColor(Color.argb(255, 0, 0, 255));
            mCanvas.drawColor(Color.argb(255, 0, 0, 255));

            //remaining code

            // Draw all the GameObjects
            Rect _2Dscreen = new Rect();
            // Draw a layer at a time
            for (int layer = -1; layer <= 1; layer++) {
                for (AbsGameObject object : mLevelManager.getGameObjects()) {
                    //render object if visible and this layer
                    if (object.isVisible() && object.getObjectLocation().z
                            == layer) {
                        Vector2Point5D location = object.getObjectLocation();
                        _2Dscreen.set(mViewport.worldToScreen(location.x,
                                location.y, object.getWidth(), object.getHeight()));


                        if (object.isAnimated()) {
                            // Rotate if necessary
                            if (object.getFacing() == Direction.RIGHT) {
                                // Rotate
                                Matrix flipper = new Matrix();
                                flipper.preScale(-1, 1);
                                Rect rect = object.getRectToDraw(System.currentTimeMillis());
                                Bitmap b = Bitmap.createBitmap(
                                        mLevelManager.getBitmaps()[mLevelManager.getBitmapIndex(object.getGameObjectType())],
                                        rect.left, rect.top, rect.width(),
                                        rect.height(), flipper, true);
                                mCanvas.drawBitmap(b, _2Dscreen.left, _2Dscreen.top, mPaint);
                            } else {
                                // draw it the regular way round
                                mCanvas.drawBitmap(mLevelManager.getBitmaps()[mLevelManager.getBitmapIndex(object.getGameObjectType())],
                                        object.getRectToDraw(System.currentTimeMillis()), _2Dscreen, mPaint);
                            }
                        } else {
                            // Draw the object bitmap
                            mCanvas.drawBitmap(
                                    mLevelManager.getBitmaps()[mLevelManager.getBitmapIndex(object.getGameObjectType())],
                                    _2Dscreen.left,
                                    _2Dscreen.top, mPaint);

                        }
                    }
                }
            }
            // draw bullets on fire
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            MachineGun gun = mLevelManager.getPlayer().gun;
            for (int i = 0; i < gun.getNumBullets(); i++) {
                _2Dscreen.set(mViewport.worldToScreen(
                        gun.getBulletX(i), gun.getBulletY(i), 0.25f, 0.05f
                ));
            }


            // Text for debugging
            if (isDebugging) {
                mPaint.setTextSize(16);
                mPaint.setTextAlign(Paint.Align.LEFT);
                mPaint.setColor(Color.argb(255, 255, 255, 255));
                mCanvas.drawText("fps:" + mFps, 10, 60, mPaint);

                mCanvas.drawText("num objects:" +
                        mLevelManager.getGameObjects().size(), 10, 80, mPaint);

                mCanvas.drawText("num clipped:" +
                        mViewport.getClippedObjectsCount(), 10, 100, mPaint);

                Vector2Point5D location = mLevelManager.getGameObjects().get(mLevelManager.getPlayerIndex()).getObjectLocation();
                mCanvas.drawText("playerX:" + location.x, 10, 120, mPaint);
                mCanvas.drawText("playerY:" + location.y, 10, 140, mPaint);
                mCanvas.drawText("Gravity:" + mLevelManager.getGravity(), 10, 160, mPaint);
                mCanvas.drawText("X velocity:" + mLevelManager.getGameObjects().get(mLevelManager.getPlayerIndex()).getXVelocity(),
                        10, 180, mPaint);
                mCanvas.drawText("Y velocity:" + mLevelManager.getGameObjects().get(mLevelManager.getPlayerIndex()).getYVelocity(),
                        10, 200, mPaint);

                //reset the number of clipped objects each frame
                mViewport.resetClippedObjectCounts();
            }

            // draw buttons
            mPaint.setColor(Color.argb(80, 255, 255, 255));
            for (Rect btn : mInputController.getButtons()) {
                RectF rf = new RectF(btn.left, btn.top,
                        btn.right, btn.bottom);
                mCanvas.drawRoundRect(rf, 15f, 15f, mPaint);
            }

            // draw pause btn
            if (!this.mLevelManager.isPlaying()) {
                mPaint.setTextAlign(Paint.Align.CENTER);
                mPaint.setColor(Color.argb(255, 255, 255, 255));
                mPaint.setTextSize(120);
                int x = mViewport.getScreenWidth() / 2;
                int y = mViewport.getScreenHeight() / 2;
                mCanvas.drawText("Paused", x, y, mPaint);
            }


            //unlocking canvas
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mLevelManager != null) {
            mInputController.handleInput(event, mLevelManager, mSoundManager, mViewport);
        }
        return true;
        /*
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mLevelManager.switchPlayingStatus();
                break;
        }
        return true;*/
    }

    private void drawBackgrounds(int start, int stop) {
        Rect fromRect1 = new Rect();
        Rect toRect1 = new Rect();
        Rect fromRect2 = new Rect();
        Rect toRect2 = new Rect();

        for (Background background : mLevelManager.getBackgroundList()) {
            if (background.z < start && background.z > stop) {
                if (!mViewport.isObjectClipable(-1, background.y,
                        1000, background.height)) {
                    float startY = mViewport.getCentreY() -
                            ((mViewport.getViewportWorldCentreY() - background.y) * mViewport.getPixelsPerMetreX());
                    float endY = mViewport.getCentreY() -
                            ((mViewport.getViewportWorldCentreY() - background.endY) * mViewport.getPixelsPerMetreX());

                }
            }
        }

    }
}
