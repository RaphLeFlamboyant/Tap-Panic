package com.flamboyant.tappanic.render.phases;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.flamboyant.common.IActivable;
import com.flamboyant.tappanic.tools.assetpack.AnimationPack;
import com.flamboyant.tappanic.tools.assetpack.AssetPack;
import com.flamboyant.tappanic.tools.assetpack.TextureRegionPack;
import com.flamboyant.tappanic.world.phases.GameplayPhaseModule;

/**
 * Created by Reborn Portable on 23/11/2015.
 */
public abstract class PhaseRenderer implements IActivable {
    private GameplayPhaseModule gamePhase;

    public PhaseRenderer(GameplayPhaseModule gamePhase)
    {
        this.gamePhase = gamePhase;
        gamePhase.addNotifyActivation(this);
    }

    public void reset()
    {

    }

    public abstract void completeAssetPacksForLoading(AssetPack texturePack, AssetPack soundPack, AssetPack musicPack);
    public abstract void completePacksForPostLoading(TextureRegionPack trPack, AnimationPack animPack);

    public abstract void draw(SpriteBatch batcher, float delta, ShapeRenderer shapeRenderer);

    public void activate(){}
    public void deactivate(){}
    public void stopMusics() {}
}
