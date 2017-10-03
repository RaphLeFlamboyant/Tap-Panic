package com.flamboyant.tappanic.render.phases;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.render.AnimationPositionObject;

import com.flamboyant.tappanic.render.TapPanicRenderer;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.assetpack.AnimationPack;
import com.flamboyant.tappanic.tools.assetpack.AssetPack;
import com.flamboyant.tappanic.tools.assetpack.TextureRegionPack;
import com.flamboyant.tappanic.world.SymbolCapsule;
import com.flamboyant.tappanic.world.phases.GameOverPhase;
import com.flamboyant.tappanic.world.phases.GameplayPhaseModule;

/**
 * Created by Reborn Portable on 02/12/2015.
 */
public class GameOverPhaseRenderer extends PhaseRenderer {
    private GameOverPhase phase;
    private TapPanicRenderer owner;
    private AnimationPositionObject girl;
    private boolean previousActiveState;
    private TapPanicGame game;

    private int pannelFrontID = 1;
    private int pannelFrontX = GameContants.BACKGROUND_INIT_POSITION_X;
    private final int frontPannelSpeed = 100;

    public GameOverPhaseRenderer(GameplayPhaseModule gamePhase, TapPanicGame g, TapPanicRenderer r)
    {
        super(gamePhase);

        game = g;
        owner = r;
        assert gamePhase.getClass().equals(GameOverPhase.class);

        phase = ((GameOverPhase) gamePhase);
    }


    public void completeAssetPacksForLoading(AssetPack texturePack, AssetPack soundPack, AssetPack musicPack)
    {
        for (int i = 1; i < 6; i++)
        {
            texturePack.add("background (" + i + ")", "Pictures/Background/background (" + i + ").png");
        }

        texturePack.add("gameBackground", "Pictures/Background/gameBackground.png");
        texturePack.add("gameOverIcons", "Pictures/GameOver/gameOverIcons.png");
        texturePack.add("gameOverCross", "Pictures/GameOver/gameOverCross.png");
        texturePack.add("bossScore", "Pictures/Interface/bossScore.png");
        texturePack.add("hitsScore", "Pictures/Interface/hitsScore.png");
        texturePack.add("pointsScore", "Pictures/Interface/pointsScore.png");
        texturePack.add("loose", "Pictures/loose.png");

        texturePack.add("loose", "Animations/loose.png");
        texturePack.add("idle", "Animations/idle.png");

        musicPack.add("gameOver", "Musics/gameOver.mp3");
        musicPack.add("bossVictory", "Musics/bossVictory.mp3");
    }


    public void completePacksForPostLoading(TextureRegionPack trPack, AnimationPack animPack){
        for (int i = 1; i < 6; i++)
        {
            trPack.add("background (" + i + ")", "background (" + i + ")", 0, 100, 1800, 1280, true);
        }

        trPack.add("gameBackground", "gameBackground", 0, 100, 800, 1280);
        trPack.add("gameOverIcons", "gameOverIcons", 0, 0, 800, 1200);
        trPack.add("gameOverCross", "gameOverCross", 0, 0, 106, 91);
        trPack.add("bossScore", "bossScore", 0, 0, 100, 100);
        trPack.add("hitsScore", "hitsScore", 0, 0, 100, 100);
        trPack.add("pointsScore", "pointsScore", 0, 0, 100, 100);

        animPack.add("loose", "loose", 200, 275, Animation.PlayMode.NORMAL);
        animPack.add("idle", "idle", 200, 275, Animation.PlayMode.LOOP);

        girl = new AnimationPositionObject("loose");
        girl.setSize(GameContants.SCREEN_HEIGTH / 4 * 200 / 275, GameContants.SCREEN_HEIGTH / 4);
        girl.setPosition(GameContants.SCREEN_WIDTH / 6 - girl.getSize().x / 2, GameContants.SCREEN_HEIGTH * 3 / 4);

        game.assetManager.loadFontFromGenerator("Fonts/TitanOne-Regular.ttf", "gameOverHitsFont", (int) (40 * GameContants.RATIO_FONT_SIZE), Color.valueOf("ff4444"));
        game.assetManager.loadFontFromGenerator("Fonts/TitanOne-Regular.ttf", "gameOverScoreFont", (int) (40 * GameContants.RATIO_FONT_SIZE), Color.valueOf("e4a82d"));
        game.assetManager.loadFontFromGenerator("Fonts/TitanOne-Regular.ttf", "gameOverBossFont", (int) (40 * GameContants.RATIO_FONT_SIZE), Color.valueOf("16729b"));
        game.assetManager.loadFontFromGenerator("Fonts/TitanOne-Regular.ttf", "gameOverHitsFontBig", (int) (60 * GameContants.RATIO_FONT_SIZE), Color.valueOf("ff4444"));
        game.assetManager.loadFontFromGenerator("Fonts/TitanOne-Regular.ttf", "gameOverScoreFontBig", (int) (60 * GameContants.RATIO_FONT_SIZE), Color.valueOf("e4a82d"));
        game.assetManager.loadFontFromGenerator("Fonts/TitanOne-Regular.ttf", "gameOverBossFontBig", (int) (60 * GameContants.RATIO_FONT_SIZE), Color.valueOf("16729b"));

        Music msc = game.assetManager.getMusic("gameOver");
        msc.setLooping(false);
        msc.setVolume(0.7f);

        msc = game.assetManager.getMusic("bossVictory");
        msc.setLooping(false);
        msc.setVolume(0.7f);
    }

    @Override
    public void reset()
    {
        super.reset();

        previousActiveState = false;
        girl.reset();
    }

    private void updatePreviousStateAndEffects()
    {
        if (TapPanicGame.soundEnabled) {
            if (previousActiveState == false && phase.isActive())
                game.assetManager.getMusic("gameOver").play();
            else if (previousActiveState == true && !phase.isActive())
                game.assetManager.getMusic("gameOver").stop();
        }

        previousActiveState = phase.isActive();
    }

    @Override
    public void activate()
    {
        super.activate();

        if (TapPanicGame.soundEnabled && (phase.getBossTimeMS() == 0 || !owner.isBossMode())) {
            game.assetManager.getMusic("gameOver").play();
            girl.setAnimationName("loose");
        }
        else if (owner.isBossMode() && phase.getBossTimeMS() > 0)
        {
            game.assetManager.getMusic("bossVictory").play();
            girl.setAnimationName("idle");
        }
    }

    @Override
    public void deactivate()
    {
        super.deactivate();

        game.assetManager.getMusic("gameOver").stop();
    }

    @Override
    public void draw(SpriteBatch batcher, float delta, ShapeRenderer shapeRenderer) {
        //updatePreviousStateAndEffects();

        if (!phase.isActive())
            return;

        batcher.draw(game.assetManager.getTextureRegion("gameBackground"), 0, 0, GameContants.SCREEN_WIDTH, GameContants.SCREEN_HEIGTH);

        pannelFrontX -= delta * frontPannelSpeed;
        if (pannelFrontX < -GameContants.BACKGROUND_PANNEL_WIDTH)
        {
            pannelFrontX += GameContants.BACKGROUND_PANNEL_WIDTH;
            pannelFrontID = (pannelFrontID) % 5 + 1;
        }

        batcher.draw(game.assetManager.getTextureRegion("background (" + pannelFrontID + ")"), pannelFrontX, GameContants.BACKGROUND_POSITION_Y, GameContants.BACKGROUND_PANNEL_WIDTH, GameContants.BACKGROUND_HEIGTH);
        batcher.draw(game.assetManager.getTextureRegion("background (" + (pannelFrontID % 5 + 1) + ")"), pannelFrontX + GameContants.BACKGROUND_PANNEL_WIDTH, GameContants.BACKGROUND_POSITION_Y, GameContants.BACKGROUND_PANNEL_WIDTH, GameContants.BACKGROUND_HEIGTH);

        if (phase.getDefeatSymbol() != null) {
            SymbolCapsule capsule = phase.getDefeatSymbol();
            batcher.draw(game.assetManager.getTextureRegion(capsule.getSymbol().getName()),
                    girl.getPosition().x + girl.getSize().x / 2, girl.getPosition().y + girl.getSize().y / 5,
                    girl.getSize().x / 6, girl.getSize().x / 6,
                    girl.getSize().x / 3, girl.getSize().x / 3, 1, 1, 45);

            capsule = phase.getClickedSymbol();
            if (capsule != null) {
                batcher.draw(game.assetManager.getTextureRegion(capsule.getSymbol().getName()),
                        GameContants.SCREEN_WIDTH * 8 / 12, GameContants.SCREEN_HEIGTH * 4 / 5,
                        GameContants.KEY_SIZE / 2, GameContants.KEY_SIZE / 2,
                        GameContants.KEY_SIZE, GameContants.KEY_SIZE, 1, 1, 0);
            }
        }

        girl.draw(batcher, delta, false, game.assetManager);

        if (phase.getFinalTurnCount() != 0 && phase.getBossTimeMS() == 0) {
            BitmapFont fntHits = game.assetManager.getFont("gameOverHitsFontBig");
            BitmapFont fntScore = game.assetManager.getFont("gameOverScoreFontBig");

            float picSize = GameContants.SCREEN_WIDTH / 9;
            batcher.draw(game.assetManager.getTextureRegion("hitsScore"), GameContants.SCREEN_WIDTH / 10, GameContants.SCREEN_HEIGTH / 16f * 0.45f, picSize, picSize);
            batcher.draw(game.assetManager.getTextureRegion("pointsScore"), GameContants.SCREEN_WIDTH / 10, GameContants.SCREEN_HEIGTH / 16f * 1.95f, picSize, picSize);

            fntHits.draw(batcher, phase.getFinalTurnCount() + "", GameContants.SCREEN_WIDTH / 10 + picSize * 1.5f, GameContants.SCREEN_HEIGTH / 16f * 0.5f);
            fntScore.draw(batcher, phase.getFinalScore() + "", GameContants.SCREEN_WIDTH / 10 + picSize * 1.5f, GameContants.SCREEN_HEIGTH / 16f * 2f);
        }
        else if (phase.getFinalTurnCount() != 0 && phase.getBossTimeMS() != 0)
        {
            BitmapFont fntBoss = game.assetManager.getFont("gameOverBossFont");
            BitmapFont fntHits = game.assetManager.getFont("gameOverHitsFont");
            BitmapFont fntScore = game.assetManager.getFont("gameOverScoreFont");

            float picSize = GameContants.SCREEN_WIDTH / 16;
            batcher.draw(game.assetManager.getTextureRegion("hitsScore"), GameContants.SCREEN_WIDTH / 10, GameContants.SCREEN_HEIGTH / 16f * 0.5f, picSize, picSize);
            batcher.draw(game.assetManager.getTextureRegion("pointsScore"), GameContants.SCREEN_WIDTH / 10, GameContants.SCREEN_HEIGTH / 16f * 1.5f, picSize, picSize);
            batcher.draw(game.assetManager.getTextureRegion("bossScore"), GameContants.SCREEN_WIDTH / 10, GameContants.SCREEN_HEIGTH / 16f * 2.5f, picSize, picSize);

            fntHits.draw(batcher, phase.getFinalTurnCount() + "", GameContants.SCREEN_WIDTH / 10 + picSize * 1.5f, GameContants.SCREEN_HEIGTH / 16f * 0.5f);
            fntScore.draw(batcher, phase.getFinalScore() + "", GameContants.SCREEN_WIDTH / 10 + picSize * 1.5f, GameContants.SCREEN_HEIGTH / 16f * 1.5f);
            fntBoss.draw(batcher,  "" + msToStringMnSec(phase.getBossTimeMS()), GameContants.SCREEN_WIDTH / 10 + picSize * 1.5f, GameContants.SCREEN_HEIGTH / 16f * 2.5f);
        }
        else if (phase.getBossTimeMS() != 0)
        {
            BitmapFont fntBoss = game.assetManager.getFont("gameOverBossFontBig");

            float picSize = GameContants.SCREEN_WIDTH / 6;
            batcher.draw(game.assetManager.getTextureRegion("bossScore"), GameContants.SCREEN_WIDTH / 10, GameContants.SCREEN_HEIGTH / 16f * 1.05f, picSize, picSize);

            fntBoss.draw(batcher, "" + msToStringMnSec(phase.getBossTimeMS()), GameContants.SCREEN_WIDTH / 10 + picSize * 1.5f, GameContants.SCREEN_HEIGTH / 16f * 1.2f);
        }

        float width = GameContants.SCREEN_WIDTH;
        float heigth = width * 1200f / 800f;
        float posY = (GameContants.SCREEN_HEIGTH - heigth) / 2f;
        batcher.draw(game.assetManager.getTextureRegion("gameOverIcons"), 0, posY, width, heigth);
    }

    private String msToStringMnSec(float timeMS)
    {
        String res = "";
        if (timeMS / 60000 < 10)
            res += '0';
        res += (int) (timeMS / 60000);

        res +="'";
        if (timeMS / 1000 % 60 < 10)
            res += '0';
        res += (int) (timeMS / 1000 % 60);
        res += '"';

        return res;
    }

    @Override
    public void stopMusics()
    {
        game.assetManager.getMusic("gameOver").stop();
    }
}
