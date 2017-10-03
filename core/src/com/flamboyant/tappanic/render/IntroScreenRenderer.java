package com.flamboyant.tappanic.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.HighLevelAssetManager;
import com.flamboyant.tappanic.tools.assetpack.AssetPack;
import com.flamboyant.tappanic.tools.assetpack.TextureRegionPack;

/**
 * Created by Reborn Portable on 08/02/2016.
 */
public class IntroScreenRenderer {
    TapPanicGame game;
    private int step = 0;
    private float timeLap = 0;
    private Array<String> introPictures = new Array<String>();

    public IntroScreenRenderer(TapPanicGame g)
    {
        game = g;
    }

    public void loadAssets()
    {
        AssetPack texturePack = new AssetPack();

        texturePack.add("graoully", "Pictures/Intro/graoully.png");

        game.assetManager.loadPack(texturePack, null, null);
    }

    public void loadingDone()
    {
        TextureRegionPack pack = new TextureRegionPack();

        pack.add("graoully", "graoullyIntro", 0, 0, 800, 1280, true);
        introPictures.add("graoullyIntro");

        game.assetManager.postLoadingAssetsGeneration(pack, null);
    }

    public void render(SpriteBatch batcher, float delta) {
        timeLap += delta;

        if ((timeLap - delta) % 5 > timeLap % 5)
            step++;

        if (step < introPictures.size)
            batcher.draw(game.assetManager.getTextureRegion(introPictures.get(step)), 0, 0, GameContants.SCREEN_WIDTH, GameContants.SCREEN_HEIGTH);
    }

    public void screenTapped()
    {
        step++;
    }

    public boolean isFinished()
    {
        return step >= introPictures.size;
    }
}
