package com.flamboyant.tappanic.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.render.MainMenuRenderer;
import com.flamboyant.tappanic.screens.TapPanicScreen;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.HighLevelAssetManager;
import com.flamboyant.tappanic.world.MainMenuWorld;

/**
 * Created by Reborn Portable on 02/12/2015.
 */
public class MainMenuController extends Controller {
    private Stage stage;
    private Skin skin;
    private MainMenuRenderer renderer;
    private MainMenuWorld world;
    private TapPanicGame game;

    private boolean updateDone = false;
    private boolean callingGame = false;

    public MainMenuController(MainMenuWorld w, MainMenuRenderer r, TapPanicGame g, Camera cam) {
        world = w;
        renderer = r;
        game = g;

        stage = new Stage(new FitViewport(GameContants.SCREEN_WIDTH, GameContants.SCREEN_HEIGTH, cam));
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        Group grp = new Group();


        float width = GameContants.SCREEN_WIDTH;
        float heigth = width * 1200f / 800f;
        float iconsPosY = (GameContants.SCREEN_HEIGTH - heigth) / 2f;

        Button soundBtn = new Button(new Button.ButtonStyle(null, null, null));
        soundBtn.setSize(GameContants.SCREEN_WIDTH * 0.30f, GameContants.SCREEN_WIDTH * 0.30f);
        float posY = iconsPosY + heigth * 2.24f / 4f;
        soundBtn.setPosition(195f * width / 800f, posY); //133 x; 414 y
        soundBtn.setRotation(7);
        soundBtn.setTransform(true);

        soundBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                TapPanicGame.soundEnabled = !TapPanicGame.soundEnabled;

                if (TapPanicGame.soundEnabled)
                    game.assetManager.getMusic("menuMusic").play();
                else
                    game.assetManager.getMusic("menuMusic").stop();

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                return;
            }
        });

        grp.addActor(soundBtn);


        Button bossBtn = new Button(new Button.ButtonStyle(null, null, null));
        bossBtn.setSize(GameContants.SCREEN_WIDTH * 0.265f, GameContants.SCREEN_WIDTH * 0.265f);
        posY = iconsPosY + heigth * 2.58f / 4f;
        bossBtn.setPosition(408f * width / 800f, posY); //133 x; 414 y
        bossBtn.setRotation(-6);
        bossBtn.setTransform(true);

        bossBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (game.saveManager.getSaveData().scoreTurn < GameContants.TURN_BETWEEN_BOSS)
                    return true;

                game.assetManager.getMusic("menuMusic").stop();
                callingGame = true;

                game.gameScreen.setBossMode(true);
                if (updateDone) {
                    game.gameScreen.setAsGameScreen();
                } else {
                    game.waitingScreen.setAsGameScreen(game.gameScreen, true);
                }

                callingGame = false;

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                return;
            }
        });

        grp.addActor(bossBtn);


        Button leaderboardBtn = new Button(new Button.ButtonStyle(null, null, null));
        leaderboardBtn.setSize(GameContants.SCREEN_WIDTH * 0.31f, GameContants.SCREEN_WIDTH * 0.31f);
        posY = iconsPosY + heigth * 1.69f / 4f;
        leaderboardBtn.setPosition(440f * width / 800f, posY); //133 x; 414 y
        leaderboardBtn.setRotation(3.5f);
        leaderboardBtn.setTransform(true);

        leaderboardBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.playServices.showScore();

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                return;
            }
        });

        grp.addActor(leaderboardBtn);



        Button arcadeBtn = new Button(new Button.ButtonStyle(null, null, null));
        arcadeBtn.setSize(GameContants.SCREEN_WIDTH * 0.37f, GameContants.SCREEN_WIDTH * 0.37f);
        posY = iconsPosY + heigth * 1.45f / 4f;
        arcadeBtn.setPosition(110f * width / 800f, posY);
        arcadeBtn.setRotation(-8);
        arcadeBtn.setTransform(true);

        arcadeBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.assetManager.getMusic("menuMusic").stop();
                callingGame = true;

                game.gameScreen.setBossMode(false);
                if (updateDone) {
                    game.gameScreen.setAsGameScreen();
                } else {
                    game.waitingScreen.setAsGameScreen(game.gameScreen, true);
                }

                callingGame = false;

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                return;
            }
        });

        grp.addActor(arcadeBtn);


        Button socialBtn = new Button(new Button.ButtonStyle(null, null, null));
        socialBtn.setSize(GameContants.SCREEN_WIDTH / 8f, GameContants.SCREEN_WIDTH / 8f);
        socialBtn.setPosition(GameContants.SCREEN_WIDTH * 8.5f / 10f, GameContants.SCREEN_HEIGTH - GameContants.SCREEN_WIDTH * 1.5f / 10f);

        socialBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.socialServices.shareToSocial();

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                return;
            }
        });

        grp.addActor(socialBtn);
/*
        socialBreath.setSize(GameContants.SCREEN_WIDTH / 8f, GameContants.SCREEN_WIDTH / 8f);
        socialBreath.setPosition(GameContants.SCREEN_WIDTH * 8.5f / 10f, GameContants.SCREEN_HEIGTH - GameContants.SCREEN_WIDTH * 1.5f / 10f);
  */


        stage.addActor(grp);

        //grp.setDebug(true, true);
    }

    public void setAsGameScreen()
    {
        game.gameScreen.loadAssets();
        renderer.launchMusic();
        updateDone = false;
    }

    @Override
    public void update(float delta) {
        if (!updateDone && game.gameScreen.update())//game.assetManager.update())
            updateDone = true;
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        if (stage.touchDown(screenX, screenY, pointer, button))
            return true;

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (stage.touchUp(screenX, screenY, pointer, button))
            return true;

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (stage.touchDragged(screenX, screenY, pointer))
            return true;

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (stage.mouseMoved(screenX, screenY))
            return true;

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isCallingGame() {
        return callingGame;
    }
}
