package com.flamboyant.tappanic.render.scripted;

import com.badlogic.gdx.Gdx;
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
import com.flamboyant.tappanic.render.MusicLoop;
import com.flamboyant.tappanic.render.phases.NormalPhaseRenderer;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.HighLevelAssetManager;
import com.flamboyant.tappanic.world.phases.BossPhase;
import com.flamboyant.tappanic.world.phases.NormalPhase;

/**
 * Created by Reborn Portable on 17/02/2016.
 */
public class BossOpeningRenderer extends ScriptedSceneRenderer {
    private BossPhase phase;
    private OrthographicCamera camera;
    private HighLevelAssetManager assetManager;
    private float timeLap = 0;
    private int step = 0;
    private float cameraYorigin;
    private float cameraXorigin;
    private boolean bossMode;
    private MusicLoop bossMusic;

    public BossOpeningRenderer(BossPhase b, HighLevelAssetManager assetManager, OrthographicCamera camera, MusicLoop bossMusic)
    {
        this.bossMusic = bossMusic;
        phase = b;
        this.camera = camera;
        this.assetManager = assetManager;
        cameraXorigin = camera.position.x;
        cameraYorigin = camera.position.y;
    }

    public void reset()
    {
        timeLap = 0;
        step = 0;
    }

    private void playSound(String soundName)
    {
        if (TapPanicGame.soundEnabled)
            assetManager.getSound(soundName).play();
    }

    private void phaseFade(SpriteBatch batcher) {
        //Gdx.app.log("Opening", "phase fade at time " + timeLap);
        if (timeLap < 1) {

            if (bossMode)
                assetManager.getMusic("gameMusic").stop();
            else {
                batcher.setColor(1, 1, 1, 1f - timeLap); // 1s fade out

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

                if (TapPanicGame.soundEnabled){
                    assetManager.getMusic("gameMusic").setVolume((1f - timeLap) * 0.6f);
                    assetManager.getMusic("gameMusic").play();
                }

                batcher.setColor(1, 1, 1, 1);
            }

            if (TapPanicGame.soundEnabled) {
                bossMusic.setVolume(timeLap * 0.4f);
                bossMusic.play();
            }
        }
        else {
            if (TapPanicGame.soundEnabled)
                assetManager.getMusic("gameMusic").pause();

            step++;
        }
    }

    private void phaseCameraMoves(SpriteBatch batcher, float delta)
    {
        //Gdx.app.log("Opening", "phase camera moves at time " + timeLap);
        //Gdx.app.log("Opening", "phase camera pos = " + camera.position.x);
        if (timeLap < 5) { //1 + 4
            camera.position.y = cameraYorigin - GameContants.SCREEN_HEIGTH / 3f * (timeLap - 1f) / 4f;

            if (timeLap >= 2 && timeLap < 2.5)
            {
                if (timeLap - delta < 2)
                    playSound("bossFoots");

                camera.position.x = cameraXorigin + MathUtils.cos(720 * 2 * (timeLap - 2)) * GameContants.SCREEN_WIDTH / 20;
            }
            else if (timeLap >= 3.5 && timeLap < 4)
            {
                if (timeLap - delta < 3.5)
                    playSound("bossFoots");

                camera.position.x = cameraXorigin + MathUtils.cos(720 * 2 * (timeLap - 3.5f)) * GameContants.SCREEN_WIDTH / 20;
            }
            else
                camera.position.x = cameraXorigin;
        }
        else
        {
            camera.position.y = cameraYorigin - GameContants.SCREEN_HEIGTH / 3f;
            step++;
        }
    }

    private void phaseBossFall(SpriteBatch batcher, float delta) {
        //Gdx.app.log("Opening", "phase boss fall at time " + timeLap);
        if (timeLap < 8) { //1 + 4 + 3
            float posY = GameContants.BOSS_POS_Y;
            if (timeLap < 7)
            {
                float dist = GameContants.SCREEN_HEIGTH / 3f + GameContants.SCREEN_HEIGTH;
                float realDist = dist * (7f - timeLap) / 2f;
                posY -= realDist;

                if (realDist <= GameContants.SCREEN_HEIGTH / 3f)
                    camera.position.y = cameraYorigin - realDist;

            }
            else
            {
                if (timeLap - delta < 7)
                    playSound("bossFall");

                float degrees = 360 * 5;
                float shakeDist = GameContants.SCREEN_HEIGTH / 40f * (8f - timeLap);
                camera.position.y = cameraYorigin - shakeDist * MathUtils.cos(degrees * (8f - timeLap));
            }

            Animation boss = assetManager.getAnimation("boss");
            batcher.draw(boss.getKeyFrame(timeLap), GameContants.BOSS_POS_X, posY, GameContants.BOSS_SIZE_X / 2, GameContants.BOSS_SIZE_Y / 2,
                    GameContants.BOSS_SIZE_X, GameContants.BOSS_SIZE_Y, 1, 1, 0);


        }
        else
        {
            camera.position.y = cameraYorigin;
            Animation boss = assetManager.getAnimation("boss");
            batcher.draw(boss.getKeyFrame(timeLap), GameContants.BOSS_POS_X, GameContants.BOSS_POS_Y, GameContants.BOSS_SIZE_X / 2, GameContants.BOSS_SIZE_Y / 2,
                    GameContants.BOSS_SIZE_X, GameContants.BOSS_SIZE_Y, 1, 1, 0);

            playSound("bossShoot");

            //playSound("bossTentacles");
            step++;
        }


    }

    private void phaseTentacleComing(SpriteBatch batcher, float delta) {
        //Gdx.app.log("Opening", "phase tentacle coming at time " + timeLap);
        if (timeLap < 10) {//1 + 4 + 3 + 2
            Animation boss = assetManager.getAnimation("boss");
            batcher.draw(boss.getKeyFrame(timeLap), GameContants.BOSS_POS_X, GameContants.BOSS_POS_Y, GameContants.BOSS_SIZE_X / 2, GameContants.BOSS_SIZE_Y / 2,
                    GameContants.BOSS_SIZE_X, GameContants.BOSS_SIZE_Y, 1, 1, 0);

            Array<BreathingTextureObject> lefts = phase.getLeftTentacles();
            Array<BreathingTextureObject> rights = phase.getRightTentacles();

            for (int i = 0; i < lefts.size; i++)
            {
                float posY = lefts.get(i).getPosition().y;
                float posX = GameContants.LEFT_TENTACLE_INIT + (GameContants.LEFT_TENTACLE_OFF_POS_X - GameContants.LEFT_TENTACLE_INIT) * (2f - (10f - timeLap)) / 2f;
                //float posX = GameContants.LEFT_TENTACLE_OFF_POS_X + (GameContants.LEFT_TENTACLE_ON_POS_X - GameContants.LEFT_TENTACLE_OFF_POS_X) * (2f - (10f - timeLap)) / 2f;
                lefts.get(i).setPosition(posX, posY);

                lefts.get(i).draw(batcher, timeLap, assetManager);

                posY = rights.get(i).getPosition().y;
                posX = GameContants.RIGHT_TENTACLE_INIT + (GameContants.RIGHT_TENTACLE_OFF_POS_X - (GameContants.RIGHT_TENTACLE_INIT)) * (2f - (10f - timeLap)) / 2f;
                //posX = GameContants.RIGHT_TENTACLE_OFF_POS_X + (GameContants.RIGHT_TENTACLE_ON_POS_X - (GameContants.RIGHT_TENTACLE_OFF_POS_X)) * (2f - (10f - timeLap)) / 2f;
                rights.get(i).setPosition(posX, posY);

                rights.get(i).draw(batcher, timeLap, assetManager);
            }
        }
        else {
            Animation boss = assetManager.getAnimation("boss");
            batcher.draw(boss.getKeyFrame(timeLap), GameContants.BOSS_POS_X, GameContants.BOSS_POS_Y, GameContants.BOSS_SIZE_X / 2, GameContants.BOSS_SIZE_Y / 2,
                    GameContants.BOSS_SIZE_X, GameContants.BOSS_SIZE_Y, 1, 1, 0);

            Array<BreathingTextureObject> lefts = phase.getLeftTentacles();
            Array<BreathingTextureObject> rights = phase.getRightTentacles();
            for (int i = 0; i < lefts.size; i++)
            {
                lefts.get(i).draw(batcher, timeLap, assetManager);
                rights.get(i).draw(batcher, timeLap, assetManager);
            }

            step++;
        }
    }

    private void phaseFinal(SpriteBatch batcher) {
        //Gdx.app.log("Opening", "phase final at time " + timeLap);
        Animation boss = assetManager.getAnimation("boss");
        batcher.draw(boss.getKeyFrame(timeLap), GameContants.BOSS_POS_X, GameContants.BOSS_POS_Y, GameContants.BOSS_SIZE_X / 2, GameContants.BOSS_SIZE_Y / 2,
                GameContants.BOSS_SIZE_X, GameContants.BOSS_SIZE_Y, 1, 1, 0);

        Array<BreathingTextureObject> lefts = phase.getLeftTentacles();
        Array<BreathingTextureObject> rights = phase.getRightTentacles();

        for (int i = 0; i < lefts.size; i++)
        {
            float posY = lefts.get(i).getPosition().y;
            float posX = GameContants.LEFT_TENTACLE_OFF_POS_X;
            lefts.get(i).setPosition(posX, posY);

            lefts.get(i).draw(batcher, timeLap, assetManager);

            posY = rights.get(i).getPosition().y;
            posX = GameContants.RIGHT_TENTACLE_OFF_POS_X;
            rights.get(i).setPosition(posX, posY);

            rights.get(i).draw(batcher, timeLap, assetManager);
        }

        bossMusic.setVolume(0.6f);

        step++;
    }

        @Override
    public boolean draw(SpriteBatch batcher, float delta, ShapeRenderer shapeRenderer) {
        timeLap += delta;

        if (step == 4)
            phaseFinal(batcher);
        if (step == 3)
            phaseTentacleComing(batcher, delta);
        if (step == 2)
            phaseBossFall(batcher, delta);
        if (step == 1)
            phaseCameraMoves(batcher, delta);
        if (step == 0)
            phaseFade(batcher);
        if (step >= 5)
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
