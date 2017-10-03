package com.flamboyant.tappanic.render;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectMap;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.screens.WaitingScreen;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.HighLevelAssetManager;
import com.flamboyant.tappanic.tools.assetpack.AnimationPack;
import com.flamboyant.tappanic.tools.assetpack.AssetPack;
import com.flamboyant.tappanic.tools.assetpack.TextureRegionPack;
import com.flamboyant.tappanic.world.MainMenuWorld;

/**
 * Created by Reborn Portable on 12/02/2016.
 */
public class WaitingScreenRenderer {
    private float timelap = 0;
    private TapPanicGame game;

    public WaitingScreenRenderer(TapPanicGame g) {
        game = g;
    }

    public void loadAssets()
    {
        AssetPack texturePack = new AssetPack();
        texturePack.add("gameBackground", "Pictures/Background/gameBackground.png");

        texturePack.add("idle", "Animations/idle.png");
        texturePack.add("loading", "Animations/loading.png");
        game.assetManager.loadPack(texturePack, null, null);
    }

    public void loadingDone()
    {
        TextureRegionPack trPack = new TextureRegionPack();

        trPack.add("gameBackground", "gameBackground", 0, 0, 800, 1280, true);

        AnimationPack animPack = new AnimationPack();
        animPack.add("loading", "loading", 175, 300, Animation.PlayMode.LOOP);
        animPack.add("idle", "idle", 200, 275, Animation.PlayMode.LOOP);

        game.assetManager.postLoadingAssetsGeneration(trPack, animPack);
        timelap = 0;
    }

    public void render(SpriteBatch batcher, float delta)
    {
        batcher.draw(game.assetManager.getTextureRegion("gameBackground"), 0, 0, GameContants.SCREEN_WIDTH, GameContants.SCREEN_HEIGTH);
        timelap += delta / 2;

        Animation ldg = game.assetManager.getAnimation("loading");
        int width = GameContants.SCREEN_WIDTH / 8;
        int heigth = width * 300 / 175;
        batcher.draw(ldg.getKeyFrame(timelap), GameContants.SCREEN_WIDTH / 2 - width / 2, GameContants.SCREEN_HEIGTH * 2 / 3, width, heigth);

        Animation girl = game.assetManager.getAnimation("idle");
        width = GameContants.GIRL_SIZE_X;
        heigth = GameContants.GIRL_SIZE_Y;
        batcher.draw(girl.getKeyFrame(timelap), GameContants.SCREEN_WIDTH / 2 - width / 2, GameContants.SCREEN_HEIGTH * 2f / 3f - heigth * 1.1f, width, heigth);

    }
}
