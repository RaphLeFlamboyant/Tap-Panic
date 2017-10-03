package com.flamboyant.tappanic.render;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectMap;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.assetpack.AnimationPack;
import com.flamboyant.tappanic.tools.assetpack.AssetPack;
import com.flamboyant.tappanic.tools.assetpack.TextureRegionPack;
import com.flamboyant.tappanic.world.MainMenuWorld;
import  com.flamboyant.common.BreathingTextureObject;

/**
 * Created by Reborn Portable on 02/12/2015.
 */
public class MainMenuRenderer {
    private ObjectMap<String, AnimationPositionObject> animationObjects = new ObjectMap<String, AnimationPositionObject>();
    private BreathingTextureObject logoBreath;
    private BreathingTextureObject socialBreath;
    private float timeLoop;
    private float timeLap = 0;
    private MainMenuWorld world;
    private TapPanicGame game;

    private int pannelFrontID = 1;
    private int pannelFrontX = GameContants.BACKGROUND_INIT_POSITION_X;
    private final int frontPannelSpeed = 100;

    public MainMenuRenderer (MainMenuWorld world, TapPanicGame g)
    {
        game = g;
        this.world = world;
    }

    public void loadAssets()
    {
        AssetPack texturePack = new AssetPack();
        texturePack.add("gameBackground", "Pictures/Background/gameBackground.png");
        for (int i = 1; i < 6; i++)
        {
            texturePack.add("background (" + i + ")", "Pictures/Background/background (" + i + ").png");
        }

        texturePack.add("social", "Pictures/MainMenu/social.png");
        texturePack.add("logotype", "Pictures/MainMenu/logotype.png");
        texturePack.add("soundOff", "Pictures/MainMenu/soundOff.png");
        texturePack.add("soundOn", "Pictures/MainMenu/soundOn.png");
        texturePack.add("bossButton", "Pictures/MainMenu/bossButton.png");
        texturePack.add("title", "Pictures/MainMenu/title.png");

        if (game.applicationDataDebug.getHeapLevel() == 3) {
            texturePack.add("panic1", "Animations/panic1.png");
            texturePack.add("panic2", "Animations/panic2.png");
            texturePack.add("panic3", "Animations/panic3.png");
        }

        texturePack.add("idle", "Animations/idle.png");

        AssetPack musicPack = new AssetPack();
        musicPack.add("menuMusic", "Musics/menuMusic.mp3");

        game.assetManager.loadPack(texturePack, null, musicPack);
    }

    public boolean updateLoading()
    {
        if (!game.assetManager.update())
            return false;

        TextureRegionPack trPack = new TextureRegionPack();

        trPack.add("gameBackground", "gameBackground", 0, 100, 800, 1280, true);

        for (int i = 1; i < 6; i++)
        {
            trPack.add("background (" + i + ")", "background (" + i + ")", 0, 100, 1800, 1280, true);
        }

        trPack.add("logotype", "logotype", 0, 0, 575, 313, true);
        trPack.add("social", "social", 0, 0, 120, 120, true);
        trPack.add("soundOff", "soundOff", 0, 0, 139, 115, true);
        trPack.add("soundOn", "soundOn", 0, 0, 139, 115, true);
        trPack.add("bossButton", "bossButton", 0, 0, 200, 200, true);
        trPack.add("title", "title", 0, 0, 800, 1200, true);

        AnimationPack animPack = new AnimationPack();
        if (game.applicationDataDebug.getHeapLevel() == 3) {
            animPack.add("panic1", "panic1", 200, 275, Animation.PlayMode.LOOP);
            animPack.add("panic2", "panic2", 200, 275, Animation.PlayMode.LOOP);
            animPack.add("panic3", "panic3", 200, 275, Animation.PlayMode.LOOP);
        }

        animPack.add("idle", "idle", 200, 275, Animation.PlayMode.LOOP);

        game.assetManager.postLoadingAssetsGeneration(trPack, animPack);

        AnimationPositionObject girl = new AnimationPositionObject("idle");
        girl.setSize(GameContants.GIRL_MENU_SIZE_X * 4f / 5f, GameContants.GIRL_MENU_SIZE_Y * 4f / 5f);
        girl.setPosition(GameContants.SCREEN_WIDTH / 4 - GameContants.GIRL_MENU_SIZE_X / 2, GameContants.SCREEN_HEIGTH * 7 / 8 - GameContants.GIRL_MENU_SIZE_Y * 4f / 10f);
        animationObjects.put("girl", girl);

        Music music = game.assetManager.getMusic("menuMusic");
        music.setLooping(false);
        music.setVolume(0.3f);

        logoBreath = new BreathingTextureObject(0);
        logoBreath.setScaleFactor(0.02f);
        logoBreath.setTextureName("logotype");
        logoBreath.setSize(0.71875f * GameContants.SCREEN_WIDTH, 0.24453125f * GameContants.SCREEN_HEIGTH);
        logoBreath.setPosition(GameContants.SCREEN_WIDTH / 2 - logoBreath.getSize().x / 2, 50f * GameContants.SCREEN_HEIGTH / 1280f);


        socialBreath = new BreathingTextureObject(0.3f);
        socialBreath.setScaleFactor(0.05f);
        socialBreath.setTextureName("social");
        socialBreath.setSize(GameContants.SCREEN_WIDTH / 8f, GameContants.SCREEN_WIDTH / 8f);
        socialBreath.setPosition(GameContants.SCREEN_WIDTH * 8.5f / 10f, GameContants.SCREEN_HEIGTH - GameContants.SCREEN_WIDTH * 1.5f / 10f);

        return true;
    }

    public void launchMusic()
    {
        if (!TapPanicGame.soundEnabled)
            return;

        Music music = game.assetManager.getMusic("menuMusic");
        music.setVolume(0.3f);
        music.play();
    }

    public void render(SpriteBatch batcher, float delta)
    {
        timeLap += delta;

        batcher.draw(game.assetManager.getTextureRegion("gameBackground"), 0, 0, GameContants.SCREEN_WIDTH, GameContants.SCREEN_HEIGTH);

        pannelFrontX -= delta * frontPannelSpeed;
        if (pannelFrontX < -GameContants.BACKGROUND_PANNEL_WIDTH)
        {
            pannelFrontX += GameContants.BACKGROUND_PANNEL_WIDTH;
            pannelFrontID = (pannelFrontID) % 5 + 1;
        }

        batcher.draw(game.assetManager.getTextureRegion("background (" + pannelFrontID + ")"), pannelFrontX, GameContants.BACKGROUND_POSITION_Y, GameContants.BACKGROUND_PANNEL_WIDTH, GameContants.BACKGROUND_HEIGTH);
        batcher.draw(game.assetManager.getTextureRegion("background (" + (pannelFrontID % 5 + 1) + ")"), pannelFrontX + GameContants.BACKGROUND_PANNEL_WIDTH, GameContants.BACKGROUND_POSITION_Y, GameContants.BACKGROUND_PANNEL_WIDTH, GameContants.BACKGROUND_HEIGTH);

        logoBreath.draw(batcher, timeLap / 2f, game.assetManager);
        float width = GameContants.SCREEN_WIDTH;
        float heigth = width * 1200f / 800f;
        float posY = (GameContants.SCREEN_HEIGTH - heigth) / 2f;
        batcher.draw(game.assetManager.getTextureRegion("title"), 0, posY, width, heigth);

        AnimationPositionObject girl = animationObjects.get("girl");

        if (game.applicationDataDebug.getHeapLevel() == 3) {
            boolean reset = false;
            if (timeLoop < 2.3 && timeLoop + delta >= 2.3) {
                girl.setAnimationName("panic1");
                reset = true;
            } else if (timeLoop < 4.5 && timeLoop + delta >= 4.5) {
                girl.setAnimationName("panic2");
                reset = true;
            } else if (timeLoop < 6.8 && timeLoop + delta >= 6.8) {
                girl.setAnimationName("panic1");
                reset = true;
            } else if (timeLoop < 9 && timeLoop + delta >= 9) {
                girl.setAnimationName("idle");
                timeLoop = 0;
                reset = true;
            }
        }

        //fnt.draw(batcher, "Screen Heigth : " + GameContants.SCREEN_HEIGTH + " Ratio : " + GameContants.RATIO_FONT_SIZE, 20, GameContants.SCREEN_HEIGTH * 20 / 64);

        batcher.draw(game.assetManager.getTextureRegion(TapPanicGame.soundEnabled ? "soundOn" : "soundOff"),
                220f * width / 800f, posY + heigth * 2.42f / 4f, GameContants.SCREEN_WIDTH * 0.20f, GameContants.SCREEN_WIDTH * 0.20f);

        if (game.saveManager.getSaveData().scoreTurn >= GameContants.TURN_BETWEEN_BOSS) {
            batcher.draw(game.assetManager.getTextureRegion("bossButton"),
                    421f * width / 800f, posY + heigth * 2.56f / 4f, GameContants.SCREEN_WIDTH * 0.245f, GameContants.SCREEN_WIDTH * 0.245f);
        }

        socialBreath.draw(batcher, timeLap, game.assetManager);

        girl.draw(batcher, delta, false, game.assetManager);//reset);
        timeLoop += delta;


        if (GameContants.DEBUG_MODE)
            game.applicationDataDebug.draw(batcher);
    }
}
