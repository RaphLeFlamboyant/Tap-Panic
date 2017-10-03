package com.flamboyant.tappanic.render.scripted;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.flamboyant.common.BreathingTextureObject;
import com.flamboyant.common.MovableObject;
import com.flamboyant.common.ScriptedSceneRenderer;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.render.AnimationPositionObject;
import com.flamboyant.tappanic.render.MusicLoop;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.HighLevelAssetManager;
import com.flamboyant.tappanic.world.phases.BossPhase;

/**
 * Created by Reborn Portable on 19/02/2016.
 */
public class BossClosingRenderer extends ScriptedSceneRenderer {
    private BossPhase phase;
    private OrthographicCamera camera;
    private HighLevelAssetManager assetManager;
    private AnimationPositionObject explosion;
    private float timeLap = 0;
    private float ownerTimelap;
    private int step = 0;
    private float bossPosY = 0;
    private float cameraXorigin;
    private boolean bossMode;
    private MusicLoop bossMusic;

    public BossClosingRenderer(BossPhase b, HighLevelAssetManager assetManager, OrthographicCamera camera, MusicLoop bossMusic)
    {
        phase = b;
        this.bossMusic = bossMusic;
        this.camera = camera;
        this.assetManager = assetManager;

        explosion = new AnimationPositionObject("explosion");

        explosion.setSize(GameContants.KEY_CELL_SIZE, GameContants.KEY_CELL_SIZE);
        explosion.setTargetSize(GameContants.KEY_CELL_SIZE, GameContants.KEY_CELL_SIZE);
        explosion.setAnimationName("explosion");

        cameraXorigin = camera.position.x;
    }

    public void setOwnerTimelap(float otimeLap)
    {
        ownerTimelap = otimeLap;
    }

    public void reset() {
        timeLap = 0;
        step = 0;
        bossPosY = GameContants.BOSS_POS_Y;
    }

    private void playSound(String soundName)
    {
        if (TapPanicGame.soundEnabled)
            assetManager.getSound(soundName).play();
    }

    private void phaseBossLeaving(SpriteBatch batcher, float delta) {
        if (timeLap < 6) {
            if (timeLap - delta <= 0)
                playSound("bossDiing");

            if (timeLap >= 0.5f && timeLap < 5.5f)
            {
                camera.position.x = cameraXorigin + MathUtils.cos(720 * 2 * (timeLap - 0.5f)) * GameContants.SCREEN_WIDTH / 40;
            }
            else
                camera.position.x = cameraXorigin;

            bossPosY = GameContants.BOSS_POS_Y - (GameContants.BOSS_SIZE_Y + GameContants.BOSS_POS_Y) * timeLap / 6f;

            Animation boss = assetManager.getAnimation("boss");
            batcher.draw(boss.getKeyFrame(timeLap), GameContants.BOSS_POS_X, bossPosY, GameContants.BOSS_SIZE_X / 2, GameContants.BOSS_SIZE_Y / 2,
                    GameContants.BOSS_SIZE_X, GameContants.BOSS_SIZE_Y, 1, 1, 0);

            Array<BreathingTextureObject> lefts = phase.getLeftTentacles();
            Array<BreathingTextureObject> rights = phase.getRightTentacles();

            for (int i = 0; i < lefts.size; i++)
            {
                float posY = lefts.get(i).getPosition().y;
                float posX = GameContants.LEFT_TENTACLE_ON_POS_X - (GameContants.LEFT_TENTACLE_ON_POS_X - GameContants.LEFT_TENTACLE_OFF_POS_X) * timeLap / 5f;
                lefts.get(i).setPosition(posX, posY);

                lefts.get(i).draw(batcher, timeLap, assetManager);

                posY = rights.get(i).getPosition().y;
                posX = GameContants.RIGHT_TENTACLE_ON_POS_X - (GameContants.RIGHT_TENTACLE_ON_POS_X - GameContants.RIGHT_TENTACLE_OFF_POS_X) * (timeLap) / 5f;
                rights.get(i).setPosition(posX, posY);

                rights.get(i).draw(batcher, timeLap, assetManager);
            }

            if (timeLap % 1.0f < (timeLap - delta) % 1.0f)
            {
                float yMax = bossPosY + GameContants.BOSS_SIZE_Y;
                explosion.setPosition(TapPanicGame.rnd.nextInt((int) (GameContants.SCREEN_WIDTH - explosion.getSize().x)), TapPanicGame.rnd.nextInt((int) yMax));
                explosion.draw(batcher, delta, true, assetManager);

                playSound("explosion");
            }
            else
                explosion.draw(batcher, delta, false, assetManager);

        } else {
            step++;
        }

    }

    private void phaseFade(SpriteBatch batcher) {
        if (timeLap < 7) {// 6 + 1

            if (!bossMode) {
                batcher.setColor(1, 1, 1, timeLap - 6); // 1s fade out

                batcher.draw(assetManager.getTextureRegion("progressBar"), GameContants.SCREEN_WIDTH / 32, GameContants.SCREEN_WIDTH / 32, GameContants.BOSS_BAR_WIDTH, GameContants.BOSS_BAR_HEIGTH);
                int scoreTurn = phase.getScoreTurn();
                scoreTurn = scoreTurn == 0 ? 0 : scoreTurn - 1;
                batcher.draw(assetManager.getTextureRegion("progressCursor"), GameContants.SCREEN_WIDTH / 32 + ((GameContants.BOSS_BAR_WIDTH - GameContants.BOSS_BAR_HEIGTH) * (scoreTurn % GameContants.TURN_BETWEEN_BOSS / GameContants.TURN_BETWEEN_BOSS)),
                        GameContants.SCREEN_WIDTH / 32, GameContants.BOSS_BAR_HEIGTH, GameContants.BOSS_BAR_HEIGTH);

                float iconSize = 1.25f * 25 * GameContants.RATIO_FONT_SIZE;
                batcher.draw(assetManager.getTexture("hitsScore"), GameContants.SCREEN_WIDTH / 32, GameContants.SCREEN_WIDTH * 5 / 32 - (0.125f * 25 * GameContants.RATIO_FONT_SIZE), iconSize, iconSize);
                batcher.draw(assetManager.getTexture("pointsScore"), GameContants.SCREEN_WIDTH / 32, GameContants.SCREEN_WIDTH * 8 / 32 - (0.125f * 25 * GameContants.RATIO_FONT_SIZE), iconSize, iconSize);

                assetManager.getFont("gameHitsFont").draw(batcher, "" + phase.getScoreTurn(), GameContants.SCREEN_WIDTH / 32 + 1.50f * 25 * GameContants.RATIO_FONT_SIZE, GameContants.SCREEN_WIDTH * 5 / 32);
                assetManager.getFont("gameScoreFont").draw(batcher, "" + phase.getScorePoints(), GameContants.SCREEN_WIDTH / 32 + 1.50f * 25 * GameContants.RATIO_FONT_SIZE, GameContants.SCREEN_WIDTH * 8 / 32);

                if (TapPanicGame.soundEnabled) {
                    assetManager.getMusic("gameMusic").setVolume((timeLap - 6f) * 0.6f);
                    assetManager.getMusic("gameMusic").play();
                }

                batcher.setColor(1, 1, 1, 1);
            }

            if (TapPanicGame.soundEnabled) {
                bossMusic.setVolume((1f - (timeLap - 6f)) * 0.6f);
                bossMusic.play();
            }
        }
        else {
            if (TapPanicGame.soundEnabled) {
                if (!bossMode)
                    assetManager.getMusic("gameMusic").setVolume(0.6f);

                bossMusic.stop();
            }
            step++;
        }
    }


    @Override
    public boolean draw(SpriteBatch batcher, float delta, ShapeRenderer shapeRenderer) {
        timeLap += delta;
        ownerTimelap += delta;

        if (step == 1)
            phaseFade(batcher);
        if (step == 0)
            phaseBossLeaving(batcher, delta);

        if (step >= 2)
            return false;

        return true;
    }

    public float getTimeLap() {
        return timeLap;
    }


    public boolean isBossMode() {
        return bossMode;
    }

    public void setBossMode(boolean bossMode) {
        this.bossMode = bossMode;
    }
}