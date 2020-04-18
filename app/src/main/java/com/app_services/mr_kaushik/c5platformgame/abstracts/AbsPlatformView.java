package com.app_services.mr_kaushik.c5platformgame.abstracts;

import android.content.Context;
import android.view.SurfaceView;

public abstract class AbsPlatformView extends SurfaceView {

    public AbsPlatformView(Context context) {
        super(context);
    }

    protected abstract void update();
    protected abstract void draw();
    public abstract void resume();
    public abstract void pause();

}
