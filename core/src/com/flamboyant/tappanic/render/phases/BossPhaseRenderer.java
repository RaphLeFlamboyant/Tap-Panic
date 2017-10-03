package com.flamboyant.tappanic.render.phases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;
import com.flamboyant.common.TextureRegionMovableObject;
import com.flamboyant.common.helpers.MovableObjectHelper;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.render.AnimationPositionObject;
import com.flamboyant.common.BreathingTextureObject;
import com.flamboyant.tappanic.render.MusicLoop;
import com.flamboyant.tappanic.render.TapPanicRenderer;
import com.flamboyant.tappanic.render.scripted.BossClosingRenderer;
import com.flamboyant.tappanic.render.scripted.BossOpeningRenderer;
import com.flamboyant.tappanic.render.scripted.SkyRenderer;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.SymbolCapsuleHelper;
import com.flamboyant.tappanic.tools.assetpack.AnimationPack;
import com.flamboyant.tappanic.tools.assetpack.AssetPack;
import com.flamboyant.tappanic.tools.assetpack.TextureRegionPack;
import com.flamboyant.tappanic.world.SmartSymbols.SmartSymbolCapsule;
import com.flamboyant.tappanic.world.SymbolCapsule;
import com.flamboyant.tappanic.world.phases.BossPhase;
import com.flamboyant.tappanic.world.phases.GameplayPhaseModule;

import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Reborn Portable on 15/02/2016.
 */
public class BossPhaseRenderer extends PhaseRenderer {
    private BossPhase phase;
    private TapPanicRenderer gameRenderer;
    private SkyRenderer skyRenderer;
    private OrthographicCamera camera;

    private boolean lastActiveState;
    private boolean lastPauseState;
    private boolean closingStarted;
    private TapPanicGame game;

    private Array<AnimationPositionObject> destroyedSymbolAnimations = new Array<AnimationPositionObject>();
    private Array<AnimationPositionObject> autodestroyedSymbolAnimations = new Array<AnimationPositionObject>();
    private Array<TextureRegionMovableObject> commentObjects = new Array<TextureRegionMovableObject>();
    private Array<Float> commentObjectsTimers = new Array<Float>();

    private BossOpeningRenderer opening;
    private BossClosingRenderer closing;

    private Array<BreathingTextureObject> leftTentacles = new Array<BreathingTextureObject>();
    private Array<BreathingTextureObject> rightTentacles = new Array<BreathingTextureObject>();

    private int lastTurnReachUpdate = -1;
    private float timeLap = 0;

    private AnimationPositionObject girlAnimation;
    private MusicLoop bossMusicLoop;

    private boolean transitionFinished = false;
    private boolean bossHit = false;
    private int bossHitFrame = 0;

    public BossPhaseRenderer(GameplayPhaseModule gamePhase, TapPanicRenderer r, TapPanicGame g, SkyRenderer sky, OrthographicCamera camera) {
        super(gamePhase);
        game = g;
        skyRenderer = sky;
        gameRenderer = r;
        this.camera = camera;
        assert gamePhase.getClass().equals(BossPhase.class);

        phase = ((BossPhase) gamePhase);

        bossMusicLoop = new MusicLoop("bossMusic", game.assetManager);

        opening = new BossOpeningRenderer(phase, g.assetManager, camera, bossMusicLoop);
        closing = new BossClosingRenderer(phase, g.assetManager, camera, bossMusicLoop);


        for (int i = 0; i < 2; i++)
        {
            BreathingTextureObject tentacle = new BreathingTextureObject(0.5f * i);
            tentacle.setTextureName("tentacle" + ((2 * i) % 3 + 1));
            tentacle.setPosition(GameContants.LEFT_TENTACLE_OFF_POS_X, GameContants.TENTACLE_Y_BASE + GameContants.SCREEN_HEIGTH / 4f * i);
            tentacle.setSize(GameContants.TENTACLE_WIDTH_BASE, GameContants.TENTACLE_HEIGTH_BASE);
            tentacle.setRotation(-90);
            leftTentacles.add(tentacle);

            tentacle = new BreathingTextureObject(0.25f + 0.5f * i);
            tentacle.setTextureName("tentacle" + ((2 * i + 1) % 3 + 1));
            tentacle.setPosition(GameContants.RIGHT_TENTACLE_OFF_POS_X, GameContants.TENTACLE_Y_BASE + GameContants.SCREEN_HEIGTH / 8f * (2f * i + 1f));
            tentacle.setSize(GameContants.TENTACLE_WIDTH_BASE, GameContants.TENTACLE_HEIGTH_BASE);
            tentacle.setRotation(90);
            rightTentacles.add(tentacle);
        }

        phase.setLeftTentacles(leftTentacles);
        phase.setRightTentacles(rightTentacles);
    }


    private void playSound(String soundName)
    {
        if (TapPanicGame.soundEnabled)
            game.assetManager.getSound(soundName).play();
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
        texturePack.add("tentacle1", "Pictures/Boss/tentacle1.png");
        texturePack.add("tentacle2", "Pictures/Boss/tentacle2.png");
        texturePack.add("tentacle3", "Pictures/Boss/tentacle3.png");

        IntBuffer intBuffer = BufferUtils.newIntBuffer(16);
        Gdx.gl20.glGetIntegerv(GL20.GL_MAX_TEXTURE_SIZE, intBuffer);

        if (intBuffer.get(0) < 2048)
            texturePack.add("boss", "Animations/bossSmall.png");
        else
            texturePack.add("boss", "Animations/boss.png");

        texturePack.add("poulet", "Animations/poulet.png");
        texturePack.add("panic1", "Animations/panic1.png");
        texturePack.add("panic2", "Animations/panic2.png");
        texturePack.add("panic3", "Animations/panic3.png");
        texturePack.add("move", "Animations/move.png");
        texturePack.add("explosion", "Animations/explosion.png");

        soundPack.add("gamePause", "Sounds/Effects/gamePause.mp3");
        soundPack.add("poulet", "Sounds/Effects/poulet.mp3");
        soundPack.add("bossFoots", "Sounds/Effects/bossFoots.mp3");
        soundPack.add("bossFall", "Sounds/Effects/bossFall.mp3");
        soundPack.add("bossShoot", "Sounds/Effects/bossShoot.mp3");
        soundPack.add("bossHit", "Sounds/Effects/bossHit.mp3");
        soundPack.add("bossDiing", "Sounds/Effects/bossDiing.mp3");
        soundPack.add("explosion", "Sounds/Effects/explosion.mp3");
        soundPack.add("tic", "Sounds/Effects/tic.mp3");
        //soundPack.add("bossTentacles", "Sounds/Effects/bossTentacles.mp3");

        bossMusicLoop.completeAssetPacksForLoading("Musics/", musicPack);
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
        trPack.add("progressCursor", "progressCursor");
        trPack.add("tentacle1", "tentacle1");
        trPack.add("tentacle2", "tentacle2");
        trPack.add("tentacle3", "tentacle3");

        animPack.add("boss", "boss", 486, 322, Animation.PlayMode.LOOP);
        animPack.add("poulet", "poulet", 105, 111, Animation.PlayMode.LOOP);
        animPack.add("panic1", "panic1", 200, 275, Animation.PlayMode.LOOP);
        animPack.add("panic2", "panic2", 200, 275, Animation.PlayMode.LOOP);
        animPack.add("panic3", "panic3", 200, 275, Animation.PlayMode.LOOP);
        animPack.add("tentacle1", "tentacle1", 651, 921, Animation.PlayMode.LOOP);
        animPack.add("tentacle2", "tentacle2", 651, 921, Animation.PlayMode.LOOP);
        animPack.add("tentacle3", "tentacle3", 651, 921, Animation.PlayMode.LOOP);
        animPack.add("move", "move", 200, 275, Animation.PlayMode.LOOP);
        animPack.add("loose", "loose", 275, 275, Animation.PlayMode.LOOP);
        animPack.add("explosion", "explosion", 200, 200, Animation.PlayMode.NORMAL);

        bossMusicLoop.afterloading();
    }

    @Override
    public void reset()
    {
        lastActiveState = false;
        lastPauseState = false;

        destroyedSymbolAnimations.clear();
        autodestroyedSymbolAnimations.clear();
        commentObjects.clear();
        commentObjectsTimers.clear();
        lastTurnReachUpdate = -1;
        timeLap = 0;
        transitionFinished = false;
        closingStarted = false;
        bossHit = false;
        bossHitFrame = 0;
        stopMusics();
    }

    @Override
    public void activate()
    {
        NormalPhaseRenderer normalRenderer =(NormalPhaseRenderer) gameRenderer.getRenderer("Normal");

        girlAnimation = normalRenderer.getGirl();
        destroyedSymbolAnimations.clear();
        autodestroyedSymbolAnimations.clear();
        commentObjects.clear();
        commentObjectsTimers.clear();
        transitionFinished = false;
        closingStarted = false;
        bossHit = false;
        bossHitFrame = 0;
    }

    private void updateStatesAndEventEffects()
    {
        if (TapPanicGame.soundEnabled) {
            if (lastPauseState == false && phase.isPaused() && phase.isActive()) {
                bossMusicLoop.pause();
                game.assetManager.getMusic("gameMusic").pause();
                game.assetManager.getSound("gamePause").play();
            } else if (lastPauseState == true && !phase.isPaused() && phase.isActive())
                bossMusicLoop.play();

            if (lastActiveState == false && phase.isActive())
                bossMusicLoop.play();
            else if (lastActiveState == true && !phase.isActive())
                bossMusicLoop.stop();
        }

        lastActiveState = phase.isActive();
        lastPauseState = phase.isPaused();
    }

    private void updateGirl(SpriteBatch batcher, float delta)
    {
        float panicDelta = delta;
        AnimationPositionObject girl = girlAnimation;
        Random rnd = TapPanicGame.rnd;
        int speed = (int) (0.75 * GameContants.SCREEN_WIDTH);

        if (MovableObjectHelper.reachesTarget(girl, girl.getSpeed().x * delta, girl.getSpeed().y * delta))
        {
            int targetX = rnd.nextInt(GameContants.SCREEN_WIDTH - (int) girl.getSize().x - GameContants.SCREEN_WIDTH / 4);
            targetX = targetX > girl.getPosition().x - GameContants.SCREEN_WIDTH / 8 ? targetX + GameContants.SCREEN_WIDTH / 4 : targetX;

            girl.setTargetPos(targetX, girl.getPosition().y);
            girl.setSpeed(targetX < girl.getPosition().x ? -speed : speed, 0);
        }

        girl.update(delta);
        girl.draw(batcher, panicDelta, false, game.assetManager);
    }

    private void updateDestroyedItemAndComments(SpriteBatch batcher, float delta, Array<SymbolCapsule> destroyedCapFromPhase, Array<AnimationPositionObject> destroyedItemList,
                                                Array<Float> commentsTimer, Array<TextureRegionMovableObject> commentTexObjects)
    {
        Array<SymbolCapsule> caps = destroyedCapFromPhase;

        for (int i = 0; i < destroyedItemList.size; i++)
        {
            destroyedItemList.get(i).draw(batcher, delta, false, game.assetManager);

            if (commentTexObjects != null) {
                TextureRegionMovableObject commentObject = commentTexObjects.get(i);

                float timer = commentsTimer.get(i) + delta;
                commentsTimer.set(i, timer);

                float scale = timer > 0.1 ? 1f : timer / 0.1f;
                batcher.draw(game.assetManager.getTextureRegion(commentObject.getTextureName()), commentObject.getPosition().x, commentObject.getPosition().y,
                        commentObject.getSize().x / 2, commentObject.getSize().y / 2, commentObject.getSize().x, commentObject.getSize().y, scale, scale, commentObject.getRotation());
            }

            if (destroyedItemList.get(i).isAnimationFinished())
            {
                destroyedItemList.removeIndex(i);

                if (commentTexObjects != null) {
                    commentsTimer.removeIndex(i);
                    commentTexObjects.removeIndex(i);
                }

                caps.removeIndex(i--);
            }
        }

        for (int i = destroyedItemList.size; i < caps.size; i++)
        {
            SymbolCapsule destroyedSymbol = caps.get(i);
            AnimationPositionObject destroyedSymbolAnimation = new AnimationPositionObject("explosion");

            destroyedSymbolAnimation.setPosition(destroyedSymbol.getPosition().x, destroyedSymbol.getPosition().y);
            destroyedSymbolAnimation.setSize(destroyedSymbol.getSize().x, destroyedSymbol.getSize().y);
            destroyedSymbolAnimation.setTargetPos(destroyedSymbol.getPosition().x, destroyedSymbol.getPosition().y);
            destroyedSymbolAnimation.setTargetSize(destroyedSymbol.getSize().x, destroyedSymbol.getSize().y);
            destroyedSymbolAnimation.setAnimationName("explosion");

            destroyedSymbolAnimation.draw(batcher, delta, true, game.assetManager);

            if (commentTexObjects != null) {
                TextureRegionMovableObject commentObject = new TextureRegionMovableObject();
                float score = destroyedSymbol.getPosition().y < GameContants.AWESOME_Y_LIMIT ? 3 : destroyedSymbol.getPosition().y < GameContants.YEAH_Y_LIMIT ? 2 : 1;
                float sizeX, sizeY;
                commentObject.setTextureName(score == 3 ? "commentAwesome" : score == 2 ? "commentGreat" : "commentOk");
                sizeX = destroyedSymbol.getSize().x * 2f;
                sizeY = sizeX / 284f * 52f;
                commentObject.setSize(sizeX, sizeY);

                float posX = destroyedSymbol.getPosition().x + destroyedSymbol.getSize().x / 2 - sizeX / 2;
                float posY = destroyedSymbol.getPosition().y + destroyedSymbol.getSize().y / 2 - sizeY / 2;
                commentObject.setPosition(posX, posY);
                float rotation = TapPanicGame.rnd.nextInt(9000) / 100f - 45f;
                commentObject.setRotation(rotation);

                commentTexObjects.add(commentObject);
                commentsTimer.add(0f);

                playSound("bossHit");

                bossHit = true;
            }

            destroyedItemList.add(destroyedSymbolAnimation);
        }
    }

    private void updateTentacles(SpriteBatch batcher)
    {
        for (int i = 0; i < leftTentacles.size; i++)
        {
            BreathingTextureObject left = leftTentacles.get(i);
            left.draw(batcher, timeLap, game.assetManager);

            BreathingTextureObject right = rightTentacles.get(i);
            right.draw(batcher, timeLap, game.assetManager);
        }
    }

    @Override
    public void draw(SpriteBatch batcher, float delta, ShapeRenderer shapeRenderer) {
        updateStatesAndEventEffects();

        if (!phase.isActive() || phase.isPaused())
            return;

        if (!transitionFinished && phase.getBossHP() > 0)
            ;//slideKeys(delta);

        //bossMusicLoop.update();

        timeLap += delta * (phase.getBossHP() < GameContants.BOSS_MAX_HP / 2 ? 2 : 1 );

        skyRenderer.draw(batcher, delta,shapeRenderer);
        //Array<SymbolCapsule> destroyedCapFromPhase, Array<AnimationPositionObject> destroyedItemList,
        //Array<Float> commentsTimer, Array<TextureRegionMovableObject> commentTexObjects
        updateDestroyedItemAndComments(batcher, delta, phase.getDestroyedCapsules(), destroyedSymbolAnimations, commentObjectsTimers, commentObjects);
        updateDestroyedItemAndComments(batcher, delta, phase.autogetDestroyedCapsules(), autodestroyedSymbolAnimations, null, null);

        for(Iterator<SmartSymbolCapsule> i = phase.getShownSymbols().iterator(); i.hasNext(); ) {
            SmartSymbolCapsule capsule = i.next();

            batcher.draw(game.assetManager.getTextureRegion(capsule.getSymbol().getName()),
                    capsule.getPosition().x, capsule.getPosition().y, capsule.getSize().x / 2, capsule.getSize().y / 2, capsule.getSize().x, capsule.getSize().y, 1, 1, capsule.getRotation());
        }

        updateGirl(batcher, delta);

        if (!transitionFinished)
        {
            for (int i = 0; i < phase.getKeyboard().size; i++) {
                SmartSymbolCapsule cap = phase.getKeyboard().get(i);
                cap.update(delta);
            }

            if (phase.getBossHP() <= 0) {
                closing.setBossMode(gameRenderer.isBossMode());
                if (!closingStarted)
                {
                    closing.setOwnerTimelap(timeLap);
                }

                transitionFinished = !closing.draw(batcher, delta, shapeRenderer);
                if (transitionFinished)
                {
                    closing.reset();
                    phase.setAnimating(false);
                }
            }
            else {
                opening.setBossMode(gameRenderer.isBossMode());
                transitionFinished = !opening.draw(batcher, delta, shapeRenderer);
                if (transitionFinished)
                {
                    timeLap = opening.getTimeLap();
                    opening.reset();
                    phase.setAnimating(false);
                }
            }
        }
        else {
            float blueGreenRatio = 1;

            if (phase.getBossHP() < 0.75f * GameContants.BOSS_MAX_HP && phase.getBossHP() > 0.5f * GameContants.BOSS_MAX_HP) {
                float factor = (GameContants.BOSS_MAX_HP * 0.75f - phase.getBossHP()) / (GameContants.BOSS_MAX_HP / 2f);
                blueGreenRatio = 1 - factor;
            }
            else if (phase.getBossHP() <= 0.5f * GameContants.BOSS_MAX_HP)
                blueGreenRatio = 0.5f;

            if (bossHit)
            {
                batcher.setColor(bossHitFrame % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                if (++bossHitFrame == 3) {
                    bossHit = false;
                    bossHitFrame = 0;
                }
            }
            else
                batcher.setColor(1, blueGreenRatio, blueGreenRatio, 1);

            Animation boss = game.assetManager.getAnimation("boss");
            batcher.draw(boss.getKeyFrame(timeLap), GameContants.BOSS_POS_X, GameContants.BOSS_POS_Y, GameContants.BOSS_SIZE_X / 2, GameContants.BOSS_SIZE_Y / 2,
                    GameContants.BOSS_SIZE_X, GameContants.BOSS_SIZE_Y, 1, 1, 0);

            updateTentacles(batcher);

            batcher.setColor(1, 1, 1, 1);
        }

        if (!phase.isKeyboardWillChange() || ((phase.getTimeLapChanging() % 0.2f) / 0.1f) >= 1f) {
            for (Iterator<SmartSymbolCapsule> i = phase.getKeyboard().iterator(); i.hasNext(); ) {
                SmartSymbolCapsule capsule = i.next();


                batcher.draw(game.assetManager.getTextureRegion(capsule.getSymbol().getName()),
                        capsule.getPosition().x, capsule.getPosition().y, capsule.getSize().x / 2, capsule.getSize().y / 2, capsule.getSize().x, capsule.getSize().y, 1, 1, capsule.getRotation());
            }
        }
    }

    public boolean isTransitionFinished() {
        return transitionFinished;
    }

    public void setTransitionFinished(boolean transitionFinished) {
        this.transitionFinished = transitionFinished;
    }
}
