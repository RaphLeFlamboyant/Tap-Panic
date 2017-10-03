package com.flamboyant.tappanic.render.phases;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.flamboyant.common.TextureRegionMovableObject;
import com.flamboyant.common.helpers.MovableObjectHelper;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.render.AnimationPositionObject;

import com.flamboyant.tappanic.render.scripted.SkyRenderer;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.assetpack.AnimationPack;
import com.flamboyant.tappanic.tools.assetpack.AssetPack;
import com.flamboyant.tappanic.tools.assetpack.TextureRegionPack;
import com.flamboyant.tappanic.world.SymbolCapsule;
import com.flamboyant.tappanic.world.phases.GameplayPhaseModule;
import com.flamboyant.tappanic.world.phases.NormalPhase;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by Reborn Portable on 19/10/2015.
 */
public class NormalPhaseRenderer extends PhaseRenderer {
    private NormalPhase phase;
    private boolean lastActiveState;
    private boolean lastPauseState;
    private TapPanicGame game;
    private SkyRenderer skyRenderer;

    private AnimationPositionObject destroyedSymbolAnimation;
    private TextureRegionMovableObject commentObject;
    private SymbolCapsule destroyedSymbol = null;

    private int lastTurnReachUpdate = -1;
    private float timeLastTurn = 0;

    private ObjectMap<String, AnimationPositionObject> animationObjects = new ObjectMap<String, AnimationPositionObject>();

    public NormalPhaseRenderer(GameplayPhaseModule gamePhase, TapPanicGame g, SkyRenderer sky){
        super(gamePhase);
        game = g;
        skyRenderer = sky;
        assert gamePhase.getClass().equals(NormalPhase.class);

        phase = ((NormalPhase) gamePhase);
    }


    public void completeAssetPacksForLoading(AssetPack texturePack, AssetPack soundPack, AssetPack musicPack)
    {
        for (int i = 1; i <= GameContants.SYMBOL_COUNT; i++)
        {
            texturePack.add("" + i, "Pictures/Symbols/" + i + ".png");
        }

        texturePack.add("ok", "Pictures/Comments/ok.png");
        texturePack.add("great", "Pictures/Comments/great.png");
        texturePack.add("awesome", "Pictures/Comments/awesome.png");
        texturePack.add("progressBar", "Pictures/Interface/progressBar.png");
        texturePack.add("progressCursor", "Pictures/Interface/progressCursor.png");
        texturePack.add("hitsScore", "Pictures/Interface/hitsScore.png");
        texturePack.add("pointsScore", "Pictures/Interface/pointsScore.png");

        if (game.applicationDataDebug.getHeapLevel() == 3) {
            texturePack.add("poulet", "Animations/poulet.png");
            texturePack.add("slip", "Animations/slip.png");
            texturePack.add("banane", "Animations/banane.png");
        }

        texturePack.add("panic3", "Animations/panic3.png");
        texturePack.add("explosion", "Animations/explosion.png");

        //texturePack.add("pointer", "Pictures/Debug/pointer.png");
        //texturePack.add("pointerBlue", "Pictures/Debug/pointerBlue.png");

        musicPack.add("gameMusic", "Musics/gameMusic.mp3");

        soundPack.add("gamePause", "Sounds/Effects/gamePause.mp3");
        soundPack.add("poulet", "Sounds/Effects/poulet.mp3");
        soundPack.add("slip", "Sounds/Effects/slip.mp3");
        soundPack.add("banane", "Sounds/Effects/banane.mp3");
    }


    public void completePacksForPostLoading(TextureRegionPack trPack, AnimationPack animPack){
        game.assetManager.loadFontFromGenerator("Fonts/TitanOne-Regular.ttf", "gameHitsFont", (int) (25 * GameContants.RATIO_FONT_SIZE), Color.valueOf("ff4444"));
        game.assetManager.loadFontFromGenerator("Fonts/TitanOne-Regular.ttf", "gameScoreFont", (int) (25 * GameContants.RATIO_FONT_SIZE), Color.valueOf("e4a82d"));

        for (int i = 1; i <= GameContants.SYMBOL_COUNT; i++)
        {
            trPack.add("" + i, "" + i, 0, 0, 200, 200, true);
        }

        trPack.add("ok", "commentOk");
        trPack.add("great", "commentGreat");
        trPack.add("awesome", "commentAwesome");
        trPack.add("progressBar", "progressBar");
        trPack.add("hitsScore", "hitsScore");
        trPack.add("pointsScore", "pointsScore");

        //trPack.add("pointer", "pointer");
        //trPack.add("pointerBlue", "pointerBlue");

        if (game.applicationDataDebug.getHeapLevel() == 3) {
            animPack.add("poulet", "poulet", 105, 111, Animation.PlayMode.LOOP);
            animPack.add("slip", "slip", 100, 103, Animation.PlayMode.LOOP);
            animPack.add("banane", "banane", 107, 110, Animation.PlayMode.LOOP);
        }

        animPack.add("panic3", "panic3", 200, 275, Animation.PlayMode.LOOP);
        animPack.add("explosion", "explosion", 200, 200, Animation.PlayMode.NORMAL);

        Music msc = game.assetManager.getMusic("gameMusic");
        msc.setVolume(0.6f);
        msc.setLooping(true);

        destroyedSymbolAnimation = new AnimationPositionObject("");

        animationObjects.put("itemFly", new AnimationPositionObject(""));
        AnimationPositionObject girl = new AnimationPositionObject("panic3");
        girl.setPosition(GameContants.SCREEN_WIDTH / 2, GameContants.GIRL_POSITION_Y);
        girl.setTargetPos(GameContants.SCREEN_WIDTH / 2, GameContants.GIRL_POSITION_Y);
        girl.setSize(GameContants.GIRL_SIZE_X, GameContants.GIRL_SIZE_Y);
        animationObjects.put("girl", girl);

        commentObject = new TextureRegionMovableObject();
        commentObject.setRotation(-90);
        commentObject.setTextureName("commentOk");
    }

    @Override
    public void reset()
    {
        lastActiveState = false;
        lastPauseState = false;

        destroyedSymbol = null;
        lastTurnReachUpdate = -1;
        timeLastTurn = 0;

        AnimationPositionObject girl = animationObjects.get("girl");
        girl.setPosition(GameContants.SCREEN_WIDTH / 2, GameContants.GIRL_POSITION_Y);
        girl.setTargetPos(GameContants.SCREEN_WIDTH / 2, GameContants.GIRL_POSITION_Y);
        girl.setSize(GameContants.GIRL_SIZE_X, GameContants.GIRL_SIZE_Y);
        girl.setSpeed(0, 0);
        girl.setAcceleration(0, 0);
        girl.setAnimationName("panic3");

        AnimationPositionObject itemFlyAPO = animationObjects.get("itemFly");
        itemFlyAPO.setAnimationName("");
        itemFlyAPO.setPosition(0, 0);
        itemFlyAPO.setSpeed(0, 0);
        itemFlyAPO.setAcceleration(0, 0);

        game.assetManager.getMusic("gameMusic").setVolume(0.6f);
        game.assetManager.getMusic("gameMusic").setPosition(0);
    }

    private void playSound(String soundName)
    {
        if (TapPanicGame.soundEnabled)
            game.assetManager.getSound(soundName).play();
    }

    private void playMusic(String musicName)
    {
        if (TapPanicGame.soundEnabled)
            game.assetManager.getMusic(musicName).play();
    }

    private void updateStatesAndEventEffects()
    {
        if (lastPauseState == false && phase.isPaused() && phase.isActive()) {
            game.assetManager.getMusic("gameMusic").pause();
            playSound("gamePause");
        }
        else if (lastPauseState == true && !phase.isPaused() && phase.isActive())
            playMusic("gameMusic");

        if (lastActiveState == false && phase.isActive()) {
            playMusic("gameMusic");
        }
        else if (lastActiveState == true && !phase.isActive() && !phase.isDoingBoss())
            game.assetManager.getMusic("gameMusic").pause();

        lastActiveState = phase.isActive();
        lastPauseState = phase.isPaused();
    }

    private void updateItemFly(SpriteBatch batcher, float delta)
    {
        AnimationPositionObject itemFlyAPO = animationObjects.get("itemFly");

        if (!itemFlyAPO.getAnimationName().equals(""))
        {
            itemFlyAPO.update(delta);

            Vector2 itemFlyPosition = itemFlyAPO.getPosition();
            if (itemFlyPosition.x > GameContants.SCREEN_WIDTH || itemFlyPosition.y < -120 || itemFlyPosition.y > GameContants.SCREEN_HEIGTH) {
                itemFlyAPO.setAnimationName("");
                return;
            }

            itemFlyAPO.draw(batcher, delta, false, game.assetManager);
        }
        else
        {
            if (phase.getRandom().nextInt(1000) < 2)
            {
                itemFlyAPO.setSize(GameContants.ITEMFLY_SIZE, GameContants.ITEMFLY_SIZE);
                int rnd = phase.getRandom().nextInt(3);
                if (rnd == 0) {
                    itemFlyAPO.setAnimationName("poulet");
                    playSound("poulet");
                }
                else if (rnd == 1) {
                    itemFlyAPO.setAnimationName("banane");
                    playSound("banane");
                }
                else {
                    itemFlyAPO.setAnimationName("slip");
                    playSound("slip");
                }

                itemFlyAPO.setPosition(-120, phase.getRandom().nextInt(GameContants.SCREEN_HEIGTH - (GameContants.SCREEN_HEIGTH / 2)) + GameContants.SCREEN_HEIGTH / 4);
                float angle = (float) ((phase.getRandom().nextInt(100) - 90) + 40) / 180f * MathUtils.PI;
                itemFlyAPO.setSpeed(MathUtils.cos(angle) * GameContants.SCREEN_WIDTH / 2, MathUtils.sin(angle) * GameContants.SCREEN_WIDTH / 2);

                itemFlyAPO.draw(batcher, delta, true, game.assetManager);
            }
        }
    }

    private void updateGirl(SpriteBatch batcher, float delta)
    {
        SymbolCapsule sc = phase.getShownSymbols().get(0);
        float panicDelta = delta;
        AnimationPositionObject girl = animationObjects.get("girl");
        Random rnd = phase.getRandom();
        int speed = (int) (0.75 * GameContants.SCREEN_WIDTH);

        if (MovableObjectHelper.reachesTarget(girl, girl.getSpeed().x * delta, girl.getSpeed().y * delta))
        {
            int targetX = rnd.nextInt(GameContants.SCREEN_WIDTH - (int) girl.getSize().x - GameContants.SCREEN_WIDTH / 4);
            targetX = targetX > girl.getPosition().x - GameContants.SCREEN_WIDTH / 8 ? targetX + GameContants.SCREEN_WIDTH / 4 : targetX;

            girl.setTargetPos(targetX, girl.getPosition().y);
            girl.setSpeed(targetX < girl.getPosition().x ? -speed : speed, 0);
        }

        float realPosSymbol = sc.getPosition().x + sc.getSize().x / 2;
        float realPosGirl = girl.getPosition().x + girl.getSize().x / 2;
        float finalTargetGirlX = phase.getTargetGirlX();
        float timeToLose = (GameContants.GIRL_Y_LIMIT - (sc.getPosition().y + sc.getSize().y)) / sc.getSpeed().y;
        float timeToReachX = (realPosGirl - finalTargetGirlX);
        timeToReachX = (timeToReachX < 0 ? -1 : 1) * timeToReachX / speed;

        if (timeToReachX > (timeToLose - 0.05f) && timeToReachX < (timeToLose + 0.05f))
        {
            girl.setTargetPos(finalTargetGirlX - girl.getSize().x / 2, girl.getPosition().y);
            girl.setSpeed((finalTargetGirlX - girl.getSize().x / 2) < girl.getPosition().x ? -speed : speed, 0);
        }

        /*if (MovableObjectHelper.reachesTarget(girl, girl.getSpeed().x * delta, girl.getSpeed().y * delta)) {
            girl.setSpeed(0, 0);
        }*/

        /*batcher.draw(game.assetManager.getTextureRegion("pointer"),
                (int) girl.getTargetPosition().x - 15 + girl.getSize().x / 2, (int) girl.getTargetPosition().y - 15, 30, 30);
        batcher.draw(game.assetManager.getTextureRegion("pointerBlue"),
                (int) phase.getTargetGirlX() - 15, (int) girl.getTargetPosition().y - 15, 30, 30);
        /*batcher.draw(AssetsLoader.loadTextureRegion("Pictures/Debug/pointer.png", "pointer", 0, 0, 30, 30),
                (int) sc.getTargetPosition().x - 15 + sc.getSize().x / 2, (int) sc.getTargetPosition().y - 15, 30, 30);
        */

        if ((girl.getPosition().x < 0 && girl.getSpeed().x < 0) || (girl.getPosition().x > GameContants.SCREEN_WIDTH - girl.getSize().x && girl.getSpeed().x > 0))
            girl.getSpeed().x = -girl.getSpeed().x;

        girl.update(delta);
        girl.draw(batcher, panicDelta, false, game.assetManager);
    }

    private void updateDestroyedItemAndComments(SpriteBatch batcher, float delta)
    {
        if (destroyedSymbol != phase.getDestroyedCap())
        {
            destroyedSymbol = phase.getDestroyedCap();
            playSound("tic");

            if (destroyedSymbol == null)
                return;

            destroyedSymbolAnimation.setPosition(destroyedSymbol.getPosition().x, destroyedSymbol.getPosition().y);
            destroyedSymbolAnimation.setSize(destroyedSymbol.getSize().x, destroyedSymbol.getSize().y);
            destroyedSymbolAnimation.setTargetPos(destroyedSymbol.getPosition().x, destroyedSymbol.getPosition().y);
            destroyedSymbolAnimation.setTargetSize(destroyedSymbol.getSize().x, destroyedSymbol.getSize().y);
            destroyedSymbolAnimation.setAnimationName("explosion");

            destroyedSymbolAnimation.draw(batcher, delta, true, game.assetManager);

            float score = phase.getLastTurnScore();
            float sizeX, sizeY;
            commentObject.setTextureName(score == 45 ? "commentAwesome" : score == 30 ? "commentGreat" : "commentOk");
            sizeX = destroyedSymbol.getSize().x * 2f;
            sizeY = sizeX / 284f * 52f;
            commentObject.setSize(sizeX, sizeY);

            float posX = destroyedSymbol.getPosition().x + destroyedSymbol.getSize().x / 2 - sizeX / 2;
            float posY = destroyedSymbol.getPosition().y + destroyedSymbol.getSize().y / 2 - sizeY / 2;
            commentObject.setPosition(posX, posY);
            float rotation = TapPanicGame.rnd.nextInt(9000) / 100f - 45f;
            commentObject.setRotation(rotation);

            /*
            int score = phase.getLastTurnScore();
            int sizeX, sizeY;
            commentObject.setTextureName(score == 45 ? "commentAwesome" : score == 30 ? "commentGreat" : "commentOk");
            sizeX = (int) (GameContants.KEY_CELL_SIZE * 1.5);
            sizeY = (int) (sizeX / 284f * 52f);
            commentObject.setSize(sizeX, sizeY);

            int posY = GameContants.GAME_OVER_HEIGTH / 4 + (int) (((45 - score) / 15) * GameContants.KEY_CELL_SIZE * 1.3f);
            int posX = GameContants.SCREEN_WIDTH - sizeX / 2 - sizeY / 2 - GameContants.SCREEN_WIDTH / 60;
            commentObject.setPosition(posX, posY);*/
        }
        else if (destroyedSymbol != null)
        {
            destroyedSymbolAnimation.draw(batcher, delta, false, game.assetManager);

            if (timeLastTurn < 0.5 && lastTurnReachUpdate > 0)
            {
                float scale = timeLastTurn > 0.1 ? 1f : timeLastTurn / 0.1f;
                batcher.draw(game.assetManager.getTextureRegion(commentObject.getTextureName()), commentObject.getPosition().x, commentObject.getPosition().y,
                        commentObject.getSize().x / 2, commentObject.getSize().y / 2, commentObject.getSize().x, commentObject.getSize().y, scale, scale, commentObject.getRotation());
            }
        }

        /*batcher.draw(AssetsLoader.loadTextureRegion("Pictures/Debug/pointer.png", "pointer", 0, 0, 30, 30),
                (int) destroyedSymbolAnimation.getPosition().x - 15, (int) destroyedSymbolAnimation.getPosition().y - 15, 30, 30);/**/
    }

    @Override
    public void draw(SpriteBatch batcher, float delta, ShapeRenderer shapeRenderer) {
        updateStatesAndEventEffects();

        if (!phase.isActive() || phase.isPaused())
            return;

        timeLastTurn += delta;
        skyRenderer.draw(batcher, delta,shapeRenderer);
        updateDestroyedItemAndComments(batcher, delta);

        for(Iterator<SymbolCapsule> i = phase.getShownSymbols().iterator(); i.hasNext(); ) {
            SymbolCapsule capsule = i.next();

            batcher.draw(game.assetManager.getTextureRegion(capsule.getSymbol().getName()),
                    capsule.getPosition().x, capsule.getPosition().y, capsule.getSize().x / 2, capsule.getSize().y / 2, capsule.getSize().x, capsule.getSize().y, 1, 1, capsule.getRotation());
        }

        for(Iterator<SymbolCapsule> i = phase.getSymbolHandler().getSymbolKeyboard().iterator(); i.hasNext(); ) {
            SymbolCapsule capsule = i.next();

            batcher.draw(game.assetManager.getTextureRegion(capsule.getSymbol().getName()),
                    capsule.getPosition().x, capsule.getPosition().y, capsule.getSize().x / 2, capsule.getSize().y / 2, capsule.getSize().x, capsule.getSize().y, 1, 1, capsule.getRotation());
        }

        updateGirl(batcher, delta);

        if (game.applicationDataDebug.getHeapLevel() == 3) {
            updateItemFly(batcher, delta);
        }

        if (lastTurnReachUpdate != phase.getScoreTurn()) {
            timeLastTurn = 0;
            lastTurnReachUpdate = phase.getScoreTurn();
        }

        batcher.draw(game.assetManager.getTextureRegion("progressBar"), GameContants.SCREEN_WIDTH / 32, GameContants.SCREEN_WIDTH / 32, GameContants.BOSS_BAR_WIDTH, GameContants.BOSS_BAR_HEIGTH);
        int scoreTurn = phase.getScoreTurn();
        scoreTurn = scoreTurn == 0 ? 0 : scoreTurn - 1;
        batcher.draw(game.assetManager.getTextureRegion("progressCursor"), GameContants.SCREEN_WIDTH / 32 + ((GameContants.BOSS_BAR_WIDTH - GameContants.BOSS_BAR_HEIGTH) * (scoreTurn % GameContants.TURN_BETWEEN_BOSS / GameContants.TURN_BETWEEN_BOSS)),
                GameContants.SCREEN_WIDTH / 32, GameContants.BOSS_BAR_HEIGTH, GameContants.BOSS_BAR_HEIGTH);

        float iconSize = 1.25f * 25 * GameContants.RATIO_FONT_SIZE;
        batcher.draw(game.assetManager.getTexture("hitsScore"), GameContants.SCREEN_WIDTH / 32, GameContants.SCREEN_WIDTH * 5 / 32 - (0.125f * 25 * GameContants.RATIO_FONT_SIZE), iconSize, iconSize);
        batcher.draw(game.assetManager.getTexture("pointsScore"), GameContants.SCREEN_WIDTH / 32, GameContants.SCREEN_WIDTH * 8 / 32 - (0.125f * 25 * GameContants.RATIO_FONT_SIZE), iconSize, iconSize);

        game.assetManager.getFont("gameHitsFont").draw(batcher, "" + phase.getScoreTurn(), GameContants.SCREEN_WIDTH / 32 + 1.50f * 25 * GameContants.RATIO_FONT_SIZE, GameContants.SCREEN_WIDTH * 5 / 32);
        game.assetManager.getFont("gameScoreFont").draw(batcher, "" + phase.getScorePoints(), GameContants.SCREEN_WIDTH / 32 + 1.50f * 25 * GameContants.RATIO_FONT_SIZE, GameContants.SCREEN_WIDTH * 8 / 32);

        //Debug code block
        /*batcher.draw(AssetsLoader.loadTextureRegion("Pictures/Debug/pointer.png", "pointer", 0, 0, 30, 30),
                (int) phase.getDebugPointerPosition().x - 15, (int) phase.getDebugPointerPosition().y - 15, 30, 30);
        /*
        AssetsLoader.getFont("customFont").draw(batcher, "X : " + phase.getDebugPointerPosition().x, 300, 30);
        AssetsLoader.getFont("customFont").draw(batcher, "Y : " + phase.getDebugPointerPosition().y, 300, 130);*/
    }

    public AnimationPositionObject getGirl()
    {
        return this.animationObjects.get("girl");
    }
}
