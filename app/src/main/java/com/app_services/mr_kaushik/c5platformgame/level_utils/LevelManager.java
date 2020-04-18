package com.app_services.mr_kaushik.c5platformgame.level_utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import com.app_services.mr_kaushik.c5platformgame.abstracts.AbsGameObject;
import com.app_services.mr_kaushik.c5platformgame.backgrounds.Background;
import com.app_services.mr_kaushik.c5platformgame.backgrounds.BackgroundData;
import com.app_services.mr_kaushik.c5platformgame.enums.GameObjectType;
import com.app_services.mr_kaushik.c5platformgame.enums.Levels;
import com.app_services.mr_kaushik.c5platformgame.game_objects.Coin;
import com.app_services.mr_kaushik.c5platformgame.game_objects.ExtraLife;
import com.app_services.mr_kaushik.c5platformgame.game_objects.MachineGunUpgrade;
import com.app_services.mr_kaushik.c5platformgame.game_objects.Player;
import com.app_services.mr_kaushik.c5platformgame.game_objects.enemies.Drone;
import com.app_services.mr_kaushik.c5platformgame.game_objects.enemies.Fire;
import com.app_services.mr_kaushik.c5platformgame.game_objects.enemies.Guard;
import com.app_services.mr_kaushik.c5platformgame.game_objects.sceneries.Boulder;
import com.app_services.mr_kaushik.c5platformgame.game_objects.sceneries.Cart;
import com.app_services.mr_kaushik.c5platformgame.game_objects.sceneries.LampPost;
import com.app_services.mr_kaushik.c5platformgame.game_objects.sceneries.Stalactite;
import com.app_services.mr_kaushik.c5platformgame.game_objects.sceneries.Stalagmite;
import com.app_services.mr_kaushik.c5platformgame.game_objects.sceneries.Tree;
import com.app_services.mr_kaushik.c5platformgame.game_objects.sceneries.Tree2;
import com.app_services.mr_kaushik.c5platformgame.game_objects.tiles.Brick;
import com.app_services.mr_kaushik.c5platformgame.game_objects.tiles.Coal;
import com.app_services.mr_kaushik.c5platformgame.game_objects.tiles.Concrete;
import com.app_services.mr_kaushik.c5platformgame.game_objects.tiles.Grass;
import com.app_services.mr_kaushik.c5platformgame.game_objects.tiles.Scorched;
import com.app_services.mr_kaushik.c5platformgame.game_objects.tiles.Snow;
import com.app_services.mr_kaushik.c5platformgame.game_objects.tiles.Stone;
import com.app_services.mr_kaushik.c5platformgame.levels.LevelCave;
import com.app_services.mr_kaushik.c5platformgame.utils.InputController;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    private static final String TAG = "LevelManager";

    private static final int MAX_WAY_POINT_IN_EITHER_DIRECTION = 8;

    private Levels mCurrentLevel;
    private int mMapWidth, mMapHeight;
    private Player mPlayer;
    private int mPlayerIndex;
    private boolean isPlaying;
    private float mGravity;
    private LevelData mLevelData;
    private List<AbsGameObject> mGameObjects;
    private List<Background> backgroundList;
    private List<Rect> mCurrentButtons;
    private Bitmap[] mBitmaps;

    /**
     * @param context        context
     * @param pixelsPerMetre helps to determine viewport
     * @param screenWidth    screen width to map world
     * @param ic             input controller
     * @param level          holds the current level of player
     * @param pX             x coordinate of player
     * @param pY             y coordinate of player
     */
    public LevelManager(Context context,
                        int pixelsPerMetre, int screenWidth,
                        InputController ic,
                        Levels level,
                        float pX, float pY) {
        this.mCurrentLevel = level;
        switch (level) {
            case CAVE:
                mLevelData = new LevelCave();
                break;
        }
        // To hold all our GameObjects
        mGameObjects = new ArrayList<>();
        // To hold 1 of every Bitmap
        mBitmaps = new Bitmap[25];

        // load all game objects
        loadMapData(context, pixelsPerMetre, pX, pY);

        // load background objects
        loadBackgrounds(context, pixelsPerMetre, screenWidth);
    }

    /**
     * Load all the GameObjects and Bitmaps
     *
     * @param context        store the context of objects
     * @param pixelsPerMetre helps to determine viewport
     * @param pX             x coordinate of player
     * @param pY             y coordinate of player
     */
    private void loadMapData(Context context, int pixelsPerMetre, float pX, float pY) {

        char ch;
        int cIndex = -1;

        mMapHeight = mLevelData.tiles.size();
        mMapWidth = mLevelData.tiles.get(0).length();

        int row = mLevelData.tiles.size();
        int col = mLevelData.tiles.get(0).length();
        for (int y = 0; y < row; y++) {
            for (int x = 0; x < col; x++) {
                ch = mLevelData.tiles.get(y).charAt(x);
                if (ch != '.') {
                    cIndex++;
                    switch (GameObjectType.get(ch)) {
                        case GRASS:
                            mGameObjects.add(new Grass(x, y));
                            break;
                        case SNOW:
                            mGameObjects.add(new Snow(x, y));
                            break;
                        case BRICK:
                            mGameObjects.add(new Brick(x, y));
                            break;
                        case COAL:
                            mGameObjects.add(new Coal(x, y));
                            break;
                        case CONCRETE:
                            mGameObjects.add(new Concrete(x, y));
                            break;
                        case SCORCHED:
                            mGameObjects.add(new Scorched(x, y));
                            break;
                        case STONE:
                            mGameObjects.add(new Stone(x, y));
                            break;

                        //PLAYER PROPERTIES
                        case PLAYER:
                            mGameObjects.add(new Player(context, pX, pY, pixelsPerMetre));
                            mPlayerIndex = cIndex;
                            mPlayer = (Player) mGameObjects.get(mPlayerIndex);
                            break;
                        case TELEPORT:
                            break;
                        case COIN:
                            mGameObjects.add(new Coin(x, y));
                            break;
                        case EXTRA_LIFE:
                            mGameObjects.add(new ExtraLife(x, y));
                            break;
                        case MACHINE_GUN_UPGRADE:
                            mGameObjects.add(new MachineGunUpgrade(x, y));
                            break;

                        // ENEMIES
                        case GUARD:
                            Guard guard = new Guard(context, x, y, pixelsPerMetre);
                            setWayPoints(guard, x, y);
                            mGameObjects.add(guard);
                            break;
                        case DRONE:
                            mGameObjects.add(new Drone(x, y));
                            break;
                        case FIRE:
                            mGameObjects.add(new Fire(context, x, y, pixelsPerMetre));
                            break;

                        // IN ACTIVE OBJECTS:
                        case TREE:
                            mGameObjects.add(new Tree(x, y));
                            break;
                        case TREE_2:
                            mGameObjects.add(new Tree2(x, y));
                            break;
                        case LAMP_POST:
                            mGameObjects.add(new LampPost(x, y));
                            break;
                        case STALACTITE:
                            mGameObjects.add(new Stalactite(x, y));
                            break;
                        case STALAGMITE:
                            mGameObjects.add(new Stalagmite(x, y));
                            break;
                        case MINE_CART:
                            mGameObjects.add(new Cart(x, y));
                            break;
                        case BOULDERS:
                            mGameObjects.add(new Boulder(x, y));
                            break;
                    }
                    // if bitmap is not present, create one
                    if (mBitmaps[getBitmapIndex(ch)] == null) {
                        mBitmaps[getBitmapIndex(ch)] = mGameObjects.get(cIndex).prepareBitmap(
                                context, mGameObjects.get(cIndex).getBitmapName(),
                                pixelsPerMetre);
                    }
                }
            }
        }
    }

    /**
     * This will set the max path for the guards, guards will move
     * either in left direction or right direction. MAX left and right from its
     * current position is {@link #MAX_WAY_POINT_IN_EITHER_DIRECTION}
     *
     * @param guard Guard to set way points
     * @param x     Current x coordinate of guard
     * @param y     Current y coordinate of guard
     */
    private void setWayPoints(Guard guard, int x, int y) {
        int row = mLevelData.tiles.size();
        int col = mLevelData.tiles.get(0).length();
        if (y + 2 < row) {
            int idx;
            String path = mLevelData.tiles.get(y + 2);
            int left = 0, right = 0;
            for (int i = 1; i <= MAX_WAY_POINT_IN_EITHER_DIRECTION; i++) {
                idx = x - i;
                if (idx > 0 && AbsGameObject.isTile(path.charAt(idx))) {
                    left++;
                } else {
                    break;
                }
            }

            for (int i = 1; i <= MAX_WAY_POINT_IN_EITHER_DIRECTION; i++) {
                idx = x + i;
                if (idx < col && AbsGameObject.isTile(path.charAt(idx))) {
                    right++;
                } else {
                    break;
                }
            }
            guard.setWayPoints(x - left, right + x);
        }
    }


    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public Bitmap getBitmap(int index) {
        return mBitmaps[index];
    }

    public int getBitmapIndex(char blockType) {
        return GameObjectType.get(blockType).ordinal();
    }

    public int getBitmapIndex(GameObjectType type) {

        return type.ordinal();
    }


    public int getIntValue(char ch) {
        return Integer.parseInt(String.valueOf(ch));
    }

    public List<AbsGameObject> getGameObjects() {
        return mGameObjects;
    }

    public Bitmap[] getBitmaps() {
        return mBitmaps;
    }

    public int getPlayerIndex() {
        return mPlayerIndex;
    }

    public float getGravity() {
        return mGravity;
    }

    public void switchPlayingStatus() {
        isPlaying = !isPlaying;
        if (isPlaying) {
            mGravity = 6;
        } else {
            mGravity = 0;
        }
    }

    private void loadBackgrounds(Context context,
                                 int pixelsPerMetre, int screenWidth) {

        backgroundList = new ArrayList<>();
        //load the background data into the Background objects and
        // place them in our GameObject arraylist
        for (BackgroundData bgData : mLevelData.backgroundDataList) {
            backgroundList.add(new Background(context,
                    pixelsPerMetre, screenWidth, bgData));
        }
    }

    public void removeGameObject(int x, int y) {
        String path = mLevelData.tiles.get(y);
        String newPath = path.substring(0, x) + GameObjectType.SPACE.getCharacter()
                + path.substring(x + 1);
        mLevelData.tiles.remove(y);
        mLevelData.tiles.add(y, newPath);
    }

    public Player getPlayer() {
        return mPlayer;
    }

    public List<Background> getBackgroundList() {
        return backgroundList;
    }
}


