package com.flamboyant.tappanic.controller;

import com.badlogic.gdx.graphics.Camera;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.render.IntroScreenRenderer;
import com.flamboyant.tappanic.screens.IntroScreen;

/**
 * Created by Reborn Portable on 08/02/2016.
 */
public class IntroScreenController extends Controller {
    private IntroScreen screen;
    private IntroScreenRenderer renderer;
    private TapPanicGame game;
    private Camera camera;

    private boolean updateDone = false;
    private boolean callingGame = false;

    public IntroScreenController(IntroScreen s, IntroScreenRenderer r, TapPanicGame g, Camera cam)
    {
        screen = s;
        renderer = r;
        game = g;
        camera = cam;
    }

    public void launchMainScreenLoading()
    {
        game.mainScreen.loadAssets();
    }

    @Override
    public void update(float delta) {
        if (!updateDone && game.mainScreen.update())// game.assetManager.update())
            updateDone = true;

        if(renderer.isFinished() && updateDone)
        {
            callingGame = true;
            game.mainScreen.setAsGameScreen();
            callingGame = false;
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
        renderer.screenTapped();

        if(renderer.isFinished() && updateDone)
        {
            callingGame = true;
            game.mainScreen.setAsGameScreen();
            callingGame = false;
        }

        return true;
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

    public boolean isCallingGame() {
        return callingGame;
    }
}
