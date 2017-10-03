package com.flamboyant.tappanic.controller;

import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.render.WaitingScreenRenderer;
import com.flamboyant.tappanic.screens.BatonPassScreen;

/**
 * Created by Reborn Portable on 12/02/2016.
 */
public class WaitingScreenController extends Controller {
    private WaitingScreenRenderer renderer;
    private TapPanicGame game;

    private boolean updateDone = false;
    private boolean callingScreen = false;

    private BatonPassScreen screen;

    public WaitingScreenController(WaitingScreenRenderer r, TapPanicGame g)
    {
        renderer = r;
        game = g;
    }

    public void setAsGameScreen(BatonPassScreen screen, boolean isAlreadyLoading)
    {
        this.screen = screen;
        updateDone = false;

        if (!isAlreadyLoading)
            screen.loadAssets();
    }

    @Override
    public void update(float delta) {
        if (!updateDone && screen.update()) {
            callingScreen = true;
            screen.setAsGameScreen();
            updateDone = true;
            callingScreen = false;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isCallingScreen() {
        return callingScreen;
    }
}
