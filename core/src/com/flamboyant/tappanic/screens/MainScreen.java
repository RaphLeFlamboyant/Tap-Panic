package com.flamboyant.tappanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.controller.MainMenuController;
import com.flamboyant.tappanic.render.MainMenuRenderer;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.world.MainMenuWorld;

/**
 * Created by Reborn Portable on 14/10/2015.
 */
public class MainScreen extends BatonPassScreen {
    private TapPanicGame game;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private MainMenuWorld world;
    private MainMenuRenderer renderer;
    private MainMenuController controller;

    public MainScreen(final TapPanicGame gam) {
        game = gam;

        world = new MainMenuWorld();
        renderer = new MainMenuRenderer(world, game);
        camera = new OrthographicCamera();
        camera.setToOrtho(true, GameContants.SCREEN_WIDTH, GameContants.SCREEN_HEIGTH);
        controller = new MainMenuController(world, renderer, game, camera);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    public void loadAssets()
    {
        Gdx.app.log("MainScreen", "Load assets");

        renderer.loadAssets();
    }

    public boolean update()
    {
        return renderer.updateLoading();
    }

    public void setAsGameScreen()
    {
        Gdx.app.log("MainScreen", "Set as screen");
        game.setScreen(this);
        Gdx.input.setInputProcessor(controller);
        controller.setAsGameScreen();
    }

    @Override
    public void show() {
        Gdx.app.log("MainScreen", "Show");
        if (!game.gameScreen.isCallingScreen() && !game.introScreen.isCallingGame() && !game.waitingScreen.isCallingScreen()) {
            game.waitingScreen.setAsGameScreen(this, false);
            game.contextReload();
        }
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
        controller.getStage().act(delta);
        controller.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        //controller.pause();
    }

    @Override
    public void resume() {
        //controller.resume();
    }

    @Override
    public void hide() {
        Gdx.app.log("MainScreen", "Hide");
        if (!controller.isCallingGame())
            game.contextLoss();
    }

    public boolean isCallingScreen()
    {
        return controller.isCallingGame();
    }

    @Override
    public void dispose() {

    }

    /*public TapPanicWorld getWorld()
    {
        return world;
    }*/
}
