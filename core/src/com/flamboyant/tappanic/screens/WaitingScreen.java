package com.flamboyant.tappanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.controller.WaitingScreenController;
import com.flamboyant.tappanic.render.WaitingScreenRenderer;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.HighLevelAssetManager;

/**
 * Created by Reborn Portable on 12/02/2016.
 */
public class WaitingScreen implements Screen {
    private TapPanicGame game;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private WaitingScreenRenderer renderer;
    private WaitingScreenController controller;
    private boolean firstLoadDone = false;

    public WaitingScreen(final TapPanicGame gam) {
        game = gam;

        renderer = new WaitingScreenRenderer(game);

        controller = new WaitingScreenController(renderer, game);
        camera = new OrthographicCamera();
        camera.setToOrtho(true, GameContants.SCREEN_WIDTH, GameContants.SCREEN_HEIGTH);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    private void loadAssets()
    {
        Gdx.app.log("WaitingScreen", "Load Assets");
        firstLoadDone = true;
        renderer.loadAssets();
    }

    //For call from screens
    public void preloadWaitingScreen()
    {
        loadAssets();
        game.assetManager.finishLoading();
        renderer.loadingDone();
    }

    @Override
    public void show() {
        Gdx.app.log("WaitingScreen", "Show");
        if (!game.mainScreen.isCallingScreen() && !game.gameScreen.isCallingScreen()) {
            loadAssets();
            game.assetManager.finishLoading();
            renderer.loadingDone();
        }

        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.end();

        game.batch.begin();
        renderer.render(game.batch, delta);
        game.batch.end();

        controller.update(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.app.log("WaitingScreen", "Hide");
        if (!controller.isCallingScreen())
            game.contextLoss();
    }

    @Override
    public void dispose() {

    }

    public void setAsGameScreen(BatonPassScreen screen, boolean isAlreadyLoading)
    {
        game.setScreen(this);
        Gdx.input.setInputProcessor(controller);
        controller.setAsGameScreen(screen, isAlreadyLoading);
    }

    public boolean isCallingScreen()
    {
        return controller.isCallingScreen();
    }
}
