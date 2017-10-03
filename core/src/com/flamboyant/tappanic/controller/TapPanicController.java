package com.flamboyant.tappanic.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.render.TapPanicRenderer;
import com.flamboyant.tappanic.render.phases.BossPhaseRenderer;
import com.flamboyant.tappanic.screens.MainScreen;
import com.flamboyant.tappanic.screens.TapPanicScreen;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.HighLevelAssetManager;
import com.flamboyant.tappanic.world.TapPanicWorld;
import com.flamboyant.tappanic.world.SymbolCapsule;
import com.flamboyant.tappanic.world.phases.GameOverPhase;
import com.flamboyant.tappanic.world.phases.IceBlockPhase;
import com.flamboyant.tappanic.world.phases.NormalPhase;

/**
 * Created by Reborn Portable on 14/10/2015.
 */
public class TapPanicController extends Controller {
    private TapPanicWorld world;
    private TapPanicRenderer renderer;
    private TapPanicGame game;
    private TapPanicScreen owner;

    private Stage stage;
    private Group gameOverGrp;
    private Skin skin;
    private int noAdTurnCounter;
    private float timeScreenChanged = 0;

    private boolean updateDone = false;
    private boolean callingGame = false;
    private boolean isStillSameGame = false;
    private boolean bossMode;

    public TapPanicController(TapPanicScreen screen, TapPanicWorld w, TapPanicRenderer r, TapPanicGame g, Camera cam)
    {
        world = w;
        renderer = r;
        game = g;
        owner = screen;

        stage = new Stage(new FitViewport(GameContants.SCREEN_WIDTH, GameContants.SCREEN_HEIGTH, cam));
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        gameOverGrp = new Group();

        initGameOver();

        stage.addActor(gameOverGrp);
    }

    public void reset()
    {
        gameOverGrp.setVisible(false);
        isStillSameGame = false;
    }

    private void initGameOver()
    {
        //TODO : changer l'écran quand boss mode fini
        gameOverGrp.setVisible(false);


        float width = GameContants.SCREEN_WIDTH;
        float heigth = width * 1200f / 800f;
        float iconsPosY = (GameContants.SCREEN_HEIGTH - heigth) / 2f;

        Button exitBtn = new Button(new Button.ButtonStyle(null, null, null));
        exitBtn.setSize(GameContants.SCREEN_WIDTH * 0.305f, GameContants.SCREEN_WIDTH * 0.305f);
        float posY = iconsPosY + heigth * 1.91f / 4f;
        exitBtn.setPosition(265f * width / 800f, posY);
        exitBtn.setRotation(-6);
        exitBtn.setTransform(true);

        exitBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (timeScreenChanged < 0.5)
                    return false;

                owner.reset();
                renderer.stopMusics();
                callingGame = true;

                if (updateDone) {
                    game.mainScreen.setAsGameScreen();
                } else {
                    game.waitingScreen.setAsGameScreen(game.mainScreen, true);
                }

                callingGame = false;

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                return;
            }
        });

        gameOverGrp.addActor(exitBtn);


        Button restartBtn = new Button(new Button.ButtonStyle(null, null, null));
        restartBtn.setSize(GameContants.SCREEN_WIDTH * 0.350f, GameContants.SCREEN_WIDTH * 0.35f);
        posY = iconsPosY + heigth * 1.25f / 4f;
        restartBtn.setPosition(71f * width / 800f, posY);
        restartBtn.setRotation(-13);
        restartBtn.setTransform(true);

        restartBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (timeScreenChanged < 0.5)
                    return false;

                renderer.stopMusics();
                //game.requestHandler.hideAds();
                owner.reset();

                //if (noAdTurnCounter == 0)
                game.interstitialHandler.launchInterstitial();

                // noAdTurnCounter = (noAdTurnCounter + 1) % 4;

                //game.setScreen(new TapPanicScreen(game, false));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                return;
            }
        });

        gameOverGrp.addActor(restartBtn);


        Button socialBtn = new Button(new Button.ButtonStyle(null, null, null));
        socialBtn.setSize(GameContants.SCREEN_WIDTH * 0.410f, GameContants.SCREEN_WIDTH * 0.410f);
        posY = iconsPosY + heigth * 0.89f / 4f;
        socialBtn.setPosition(402f * width / 800f, posY);
        socialBtn.setRotation(10);
        socialBtn.setTransform(true);

        socialBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameOverPhase phase = (GameOverPhase) world.getPhase("GameOver");

                if (phase.getFinalTurnCount() != 0 && phase.getBossTimeMS() == 0)
                    game.socialServices.shareScore(phase.getFinalTurnCount(), phase.getFinalScore());
                else if (phase.getFinalTurnCount() != 0 && phase.getBossTimeMS() != 0)
                    game.socialServices.shareScore(phase.getFinalTurnCount(), phase.getFinalScore(), phase.getBossTimeMS());
                else if (phase.getBossTimeMS() != 0)
                    game.socialServices.shareScore(phase.getBossTimeMS());
                else
                    game.socialServices.shareGameOver(phase.isBossMode());

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                return;
            }
        });

        gameOverGrp.addActor(socialBtn);

        //gameOverGrp.setDebug(true, true);
    }

    public void setAsGameScreen()
    {
        game.mainScreen.loadAssets();
        updateDone = false;
    }

    private void saveData()
    {
        int bestTurn = game.saveManager.getSaveData().scoreTurn;
        int bestScore = game.saveManager.getSaveData().scorePoints;
        int bestBossMS = game.saveManager.getSaveData().bossTimeMS;

        game.saveManager.getSaveData().scoreTurn = bestTurn < world.getNormalPhase().getScoreTurn() ? world.getNormalPhase().getScoreTurn() : bestTurn;
        game.saveManager.getSaveData().scorePoints = bestScore < world.getNormalPhase().getScorePoints() ? world.getNormalPhase().getScorePoints() : bestScore;
        int bossSec = (int) (world.getBossPhase().getBossTimeSeconds() * 1000);
        game.saveManager.getSaveData().bossTimeMS = bestBossMS < bossSec ? bossSec : bestBossMS;

        game.saveManager.writeSaveData();
    }

    private void validateAchievementsAndHighscores()
    {
        if (world.getNormalPhase().isActive()) {
            if (world.getNormalPhase().getScoreTurn() == 6)
                game.playServices.unlockAchievement(GameContants.ACHIEVEMENT_FIRST_STEP);
            if (world.getNormalPhase().getScoreTurn()== GameContants.TURN_BETWEEN_BOSS / 2)
                game.playServices.unlockAchievement(GameContants.ACHIEVEMENT_HALF);
        }

        if (world.getBossPhase().isActive()) {
            if (world.getBossPhase().getBossHP() <= 0) {
                game.playServices.unlockAchievement(GameContants.ACHIEVEMENT_BOSS_DEFEAT);
                game.playServices.submitScoreBossTime((int) (world.getBossPhase().getBossTimeSeconds() * 1000));
            }
            if (world.getBossPhase().getBossHP() == GameContants.BOSS_MAX_HP)
                game.playServices.unlockAchievement(GameContants.ACHIEVEMENT_BOSS_ENCOUNTER);
        }

        if (((IceBlockPhase) world.getPhase("IceBlock")).isIceBroken())
            game.playServices.unlockAchievement(GameContants.ACHIEVEMENT_ICE);

        GameOverPhase goPhase = (GameOverPhase) world.getPhase("GameOver");
        if (goPhase.isActive())
        {
            game.playServices.submitScoreHits(goPhase.getFinalTurnCount());
            game.playServices.submitScorePoints(goPhase.getFinalScore());

            if (!isStillSameGame)
            {
                saveData();
                isStillSameGame = true;
            }
        }
        else
            isStillSameGame = false;
    }

    public void update (float delta)
    {
        if (!updateDone && game.mainScreen.update())
            updateDone = true;

        if (world.getPhase("GameOver").isActive() && !gameOverGrp.isVisible())
        {
            gameOverGrp.setVisible(true);
            timeScreenChanged = 0;
        }
        else if (!world.getPhase("GameOver").isActive() && gameOverGrp.isVisible())
        {
            gameOverGrp.setVisible(false);
        }
        else
        {
            timeScreenChanged += delta;
        }

        validateAchievementsAndHighscores();

        BossPhaseRenderer bossR = (BossPhaseRenderer) renderer.getRenderer("Boss");
        if (world.getBossPhase().getBossHP() <= 0 && bossR.isTransitionFinished() && world.getBossPhase().isActive())
        {
            if (world.getBossPhase().isAnimating())
                bossR.setTransitionFinished(false);
            else {
                if (!bossMode)
                    world.getBossPhase().bossDeathFinished();
                else {
                    world.getNormalPhase().setBossTimeMS(world.getBossPhase().getBossTimeSeconds() * 1000);
                    world.getBossPhase().deactivate();
                    world.getNormalPhase().launchGameOver(null, null);
                }
            }
        }
    }

    public void pause()
    {
        if (!world.getPhase("GameOver").isActive() && !world.getPhase("Starter").isActive()) {
            world.gamePause();
        }
    }

    public void resume()
    {
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            pause();
            Gdx.input.setCatchBackKey(false);
        }

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
        //Gdx.app.log("Input", "coordinate : " + screenX + ";" + screenY);
        if (stage.touchDown(screenX, screenY, pointer, button))
            return true;

        return world.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (stage.touchUp(screenX, screenY, pointer, button))
            return true;

        return world.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (stage.touchDragged(screenX, screenY, pointer))
            return true;

        return world.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (stage.mouseMoved(screenX, screenY))
            return true;

        return world.mouseMoved(screenX, screenY);
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

    public boolean isBossMode() {
        return bossMode;
    }

    public void setBossMode(boolean bossMode) {
        this.bossMode = bossMode;
    }
}
