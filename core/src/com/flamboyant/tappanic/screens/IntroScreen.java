package com.flamboyant.tappanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.controller.IntroScreenController;
import com.flamboyant.tappanic.render.IntroScreenRenderer;
import com.flamboyant.tappanic.tools.GameContants;

/**
 * Created by Reborn Portable on 08/02/2016.
 */
public class IntroScreen implements Screen {
    private TapPanicGame game;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private IntroScreenRenderer renderer;
    private IntroScreenController controller;
    private boolean firstLoadDone = false;

    public IntroScreen(final TapPanicGame gam) {
        game = gam;

        renderer = new IntroScreenRenderer(game);

        loadAssets();
        game.assetManager.finishLoading();
        renderer.loadingDone();
        game.applicationDataDebug.loadFonts();

        controller = new IntroScreenController(this, renderer, game, camera);
        controller.launchMainScreenLoading();
        camera = new OrthographicCamera();
        camera.setToOrtho(true, GameContants.SCREEN_WIDTH, GameContants.SCREEN_HEIGTH);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    private void loadAssets()
    {
        Gdx.app.log("IntroScreen", "Load Assets");
        renderer.loadAssets();
    }

    @Override
    public void show() {
        Gdx.app.log("IntroScreen", "Show");
        if (firstLoadDone)
        {
            loadAssets();
            game.assetManager.finishLoading();
            renderer.loadingDone();
            game.applicationDataDebug.loadFonts();

            controller.launchMainScreenLoading();
            game.contextReload();
        }

        Gdx.input.setInputProcessor(controller);
        firstLoadDone = true;
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
        Gdx.app.log("IntroScreen", "Hide");
        if (!controller.isCallingGame())
            game.contextLoss();
    }

    @Override
    public void dispose() {

    }

    public boolean isCallingGame()
    {
        return controller.isCallingGame();
    }
}
