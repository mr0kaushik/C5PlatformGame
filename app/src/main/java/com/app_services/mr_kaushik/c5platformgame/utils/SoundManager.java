package com.app_services.mr_kaushik.c5platformgame.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;

public class SoundManager {

    public enum SoundType {
        SHOOT, JUMP, TELEPORT, COIN_PICKUP, GUN_UPGRADE,
        PLAYER_BURN, RICOCHET, HIT_GUARD, EXPLODE, EXTRA_LIFE
    }

    private SoundPool soundPool;
    int shoot = -1;
    int jump = -1;
    int teleport = -1;
    int coin_pickup = -1;
    int gun_upgrade = -1;
    int player_burn = -1;
    int ricochet = -1;
    int hit_guard = -1;
    int explode = -1;
    int extra_life = -1;

    public void loadSound(Context context) {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            //Create objects of the 2 required classes
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;
            //create our fx
            descriptor = assetManager.openFd("shoot.ogg");
            shoot = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("jump.ogg");
            jump = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("teleport.ogg");
            teleport = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("coin_pickup.ogg");
            coin_pickup = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("gun_upgrade.ogg");
            gun_upgrade = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("player_burn.ogg");
            player_burn = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("ricochet.ogg");
            ricochet = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("hit_guard.ogg");
            hit_guard = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("explode.ogg");
            explode = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("extra_life.ogg");
            extra_life = soundPool.load(descriptor, 0);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", "failed to load sound files");
        }
    }

    public void playSound(SoundType type) {
        switch (type) {
            case SHOOT:
                soundPool.play(shoot, 1, 1, 0, 0, 1);
                break;
            case JUMP:
                soundPool.play(jump, 1, 1, 0, 0, 1);
                break;
            case TELEPORT:
                soundPool.play(teleport, 1, 1, 0, 0, 1);
                break;
            case COIN_PICKUP:
                soundPool.play(coin_pickup, 1, 1, 0, 0, 1);
                break;
            case GUN_UPGRADE:
                soundPool.play(gun_upgrade, 1, 1, 0, 0, 1);
                break;
            case PLAYER_BURN:
                soundPool.play(player_burn, 1, 1, 0, 0, 1);
                break;
            case RICOCHET:
                soundPool.play(ricochet, 1, 1, 0, 0, 1);
                break;
            case HIT_GUARD:
                soundPool.play(hit_guard, 1, 1, 0, 0, 1);
                break;
            case EXPLODE:
                soundPool.play(explode, 1, 1, 0, 0, 1);
                break;
            case EXTRA_LIFE:
                soundPool.play(extra_life, 1, 1, 0, 0, 1);
                break;
        }
    }
}