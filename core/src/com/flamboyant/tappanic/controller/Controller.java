package com.flamboyant.tappanic.controller;

import com.badlogic.gdx.InputProcessor;

/**
 * Created by Reborn Portable on 14/10/2015.
 */
public abstract class Controller implements InputProcessor {
    public abstract void update(float delta);
}
