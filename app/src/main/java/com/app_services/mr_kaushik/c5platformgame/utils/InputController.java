package com.app_services.mr_kaushik.c5platformgame.utils;

import android.graphics.Rect;
import android.view.MotionEvent;

import com.app_services.mr_kaushik.c5platformgame.level_utils.LevelManager;

import java.util.ArrayList;

public class InputController {

    Rect left;
    Rect right;
    Rect jump;
    Rect pause;
    Rect shoot;

    public InputController(int screenWidth, int screenHeight) {
        int buttonWidth = screenWidth / 8;
        int buttonHeight = screenHeight / 7;
        int buttonPadding = screenWidth / 80;
        left = new Rect(buttonPadding,
                screenHeight - buttonHeight - buttonPadding,
                buttonWidth,
                screenHeight - buttonPadding);
        right = new Rect(buttonWidth + buttonPadding,
                screenHeight - buttonHeight - buttonPadding,
                buttonWidth + buttonPadding + buttonWidth,
                screenHeight - buttonPadding);
        jump = new Rect(screenWidth - buttonWidth - buttonPadding,
                screenHeight - buttonHeight - buttonPadding -
                        buttonHeight - buttonPadding,
                screenWidth - buttonPadding,
                screenHeight - buttonPadding - buttonHeight -
                        buttonPadding);
        shoot = new Rect(screenWidth - buttonWidth - buttonPadding,
                screenHeight - buttonHeight - buttonPadding,
                screenWidth - buttonPadding,
                screenHeight - buttonPadding);
        pause = new Rect(screenWidth - buttonPadding -
                buttonWidth,
                buttonPadding,
                screenWidth - buttonPadding,
                buttonPadding + buttonHeight);
    }

    public ArrayList<Rect> getButtons() {
        //create an array of buttons for the draw method
        return new ArrayList<Rect>() {
            {
                add(left);
                add(right);
                add(jump);
                add(shoot);
                add(pause);
            }
        };
    }

    public void handleInput(MotionEvent event, LevelManager levelManager, SoundManager soundManager, Viewport viewport) {

        int pointerCount = event.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {

            int x = (int) event.getX(i);
            int y = (int) event.getY(i);
            if (levelManager.isPlaying()) {

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (right.contains(x, y)) {
                            levelManager.getPlayer().setPressingRight(true);
                            levelManager.getPlayer().setPressingLeft(false);

                        } else if (left.contains(x, y)) {
                            levelManager.getPlayer().setPressingLeft(true);
                            levelManager.getPlayer().setPressingRight(false);
                        } else if (jump.contains(x, y)) {
                            levelManager.getPlayer().startJump(soundManager);

                        } else if (shoot.contains(x, y)) {
                            if (levelManager.getPlayer().pullTrigger()) {
                                soundManager.playSound(SoundManager.SoundType.SHOOT);
                            }
                        } else if (pause.contains(x, y)) {
                            levelManager.switchPlayingStatus();
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        if (right.contains(x, y)) {
                            levelManager.getPlayer().setPressingRight(false);
                        } else if (left.contains(x, y)) {
                            levelManager.getPlayer().setPressingLeft(false);
                        }
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        if (right.contains(x, y)) {
                            levelManager.getPlayer().setPressingRight(false);
                            //Log.w("rightP:", "up" );
                        } else if (left.contains(x, y)) {
                            levelManager.getPlayer().setPressingLeft(false);
                            //Log.w("leftP:", "up" );
                        } else if (shoot.contains(x, y)) {
                            //Handle shooting here
                        } else if (jump.contains(x, y)) {

                        }
                        break;
                    //Handle shooting here
                }
            } else {
                if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                    if (pause.contains(x, y)) {
                        levelManager.switchPlayingStatus();
                        //Log.w("pause:", "DOWN" );
                    }
                }
            }
        }
    }

}
