package com.flamboyant.tappanic.render.phases;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.HighLevelAssetManager;
import com.flamboyant.tappanic.tools.assetpack.AnimationPack;
import com.flamboyant.tappanic.tools.assetpack.AssetPack;
import com.flamboyant.tappanic.tools.assetpack.TextureRegionPack;
import com.flamboyant.tappanic.world.phases.GameStarterPhase;
import com.flamboyant.tappanic.world.phases.GameplayPhaseModule;

/**
 * Created by Reborn Portable on 19/10/2015.
 */
public class GameStarterPhaseRenderer extends PhaseRenderer {
    private GameStarterPhase phase;
    private TapPanicGame game;
    private OrthographicCamera camera;

    public GameStarterPhaseRenderer(GameplayPhaseModule gamePhase, TapPanicGame g, OrthographicCamera cam)
    {
        super(gamePhase);
        game = g;
        camera = cam;
        assert gamePhase.getClass().equals(GameStarterPhase.class);

        phase = ((GameStarterPhase) gamePhase);
    }

    public void completeAssetPacksForLoading(AssetPack texturePack, AssetPack soundPack, AssetPack musicPack)
    {
        texturePack.add("howtoplay", "Pictures/Starter/howtoplay.png");
        texturePack.add("startplay", "Pictures/Starter/startplay.png");
    }


    public void completePacksForPostLoading(TextureRegionPack trPack, AnimationPack animPack){
        trPack.add("howtoplay", "howtoplay", 0, 0, 800, 1280);
        trPack.add("startplay", "startplay", 0, 0, 800, 1280);
    }

    @Override
    public void reset()
    {
        super.reset();
    }

    @Override
    public void draw(SpriteBatch batcher, float delta, ShapeRenderer shapeRenderer) {
        if (!phase.isActive())
            return;

        if (phase.getStep() == phase.STEP_HOWTOPLAY)
            batcher.draw(game.assetManager.getTextureRegion("howtoplay"), camera.position.x - GameContants.SCREEN_WIDTH / 2, camera.position.y - GameContants.SCREEN_HEIGTH / 2, GameContants.SCREEN_WIDTH, GameContants.SCREEN_HEIGTH);
        else if (phase.getStep() == phase.STEP_TAPTOPLAY)
            batcher.draw(game.assetManager.getTextureRegion("startplay"), camera.position.x - GameContants.SCREEN_WIDTH / 2, camera.position.y - GameContants.SCREEN_HEIGTH / 2, GameContants.SCREEN_WIDTH, GameContants.SCREEN_HEIGTH);
    }
}
