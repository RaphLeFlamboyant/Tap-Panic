package com.flamboyant.tappanic.screens;

import com.badlogic.gdx.Screen;

/**
 * Created by Reborn Portable on 12/02/2016.
 */
public abstract class BatonPassScreen implements Screen {

    public abstract void loadAssets();

    public abstract boolean update();

    public abstract void setAsGameScreen();

    public abstract boolean isCallingScreen();
}
