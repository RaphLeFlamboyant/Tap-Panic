package com.flamboyant.tappanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.controller.TapPanicController;
import com.flamboyant.tappanic.render.TapPanicRenderer;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.world.TapPanicWorld;

/**
 * Created by Reborn Portable on 14/10/2015.
 */
public class TapPanicScreen extends BatonPassScreen {
    private TapPanicGame game;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private TapPanicWorld world;
    private TapPanicRenderer renderer;
    private TapPanicController controller;

    private boolean showing = false;

    public TapPanicScreen(final TapPanicGame gam) {
        this(gam, true);
    }

    public TapPanicScreen(final TapPanicGame gam, boolean showHowToPlay)
    {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(true, GameContants.SCREEN_WIDTH, GameContants.SCREEN_HEIGTH);

        world = new TapPanicWorld(showHowToPlay);
        renderer = new TapPanicRenderer(world, game, camera);
        controller = new TapPanicController(this, world, renderer, game, camera);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    public void loadAssets()
    {
        Gdx.app.log("TapPanicScreen", "Load Assets");
        renderer.loadAssets();
    }

    public boolean update()
    {
        return renderer.updateLoading();
    }

    public void setAsGameScreen()
    {
        Gdx.app.log("TapPanicScreen", "Set as Screen");
        showing = true;
        game.setScreen(this);
        Gdx.input.setInputProcessor(controller);
        Gdx.input.setCatchBackKey(true);
        controller.setAsGameScreen();
        showing = false;
    }

    public void reset()
    {
        Gdx.app.log("TapPanicScreen", "reset");
        world.reset();
        renderer.reset();
        controller.reset();
    }

    @Override
    public void show() {
        Gdx.app.log("TapPanicScreen", "Show");
        if (!game.mainScreen.isCallingScreen() && !game.waitingScreen.isCallingScreen())
        {
            game.waitingScreen.setAsGameScreen(this, false);
            game.contextReload();
        }
    }

    @Override
    public void render(float delta) {
        world.update(delta);

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.end();

        game.batch.begin();
        renderer.render(game.batch, delta, shapeRenderer);
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
        controller.pause();
    }

    @Override
    public void resume() {
        Gdx.app.log("TapPanicScreen", "Resume");
        controller.resume();
    }

    @Override
    public void hide() {
        Gdx.app.log("TapPanicScreen", "Hide");
        if (!controller.isCallingGame())
            game.contextLoss();
    }

    @Override
    public void dispose() {

    }

    public void setBossMode(boolean isBossMode)
    {
        world.setBossMode(isBossMode);
        controller.setBossMode(isBossMode);
        renderer.setBossMode(isBossMode);
    }

    public boolean isCallingScreen()
    {
        return controller.isCallingGame();
    }

    public TapPanicWorld getWorld()
    {
        return world;
    }

    public boolean isShowing() {
        return showing;
    }
}
