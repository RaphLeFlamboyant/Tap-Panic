package com.flamboyant.tappanic.render.scripted;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.flamboyant.common.ScriptedSceneRenderer;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.HighLevelAssetManager;
import com.flamboyant.tappanic.tools.assetpack.AnimationPack;
import com.flamboyant.tappanic.tools.assetpack.AssetPack;
import com.flamboyant.tappanic.tools.assetpack.TextureRegionPack;

/**
 * Created by Reborn Portable on 26/02/2016.
 */
public class SkyRenderer extends ScriptedSceneRenderer {
    private HighLevelAssetManager assetsManager;
    private TapPanicGame game;
    private float timeLap = 0;
    private int pannelFrontID = 1;
    private int pannelFrontX = GameContants.BACKGROUND_INIT_POSITION_X;

    private int pannelBackID = 1;
    private int pannelBackX = GameContants.BACKGROUND_INIT_POSITION_X;

    private final int frontPannelSpeed = 100;
    private final int backPannelSpeed = 65;
    private int maxI;
    private int perfLvl;

    public SkyRenderer(HighLevelAssetManager assetsManager, TapPanicGame game)
    {
        perfLvl = game.applicationDataDebug.getHeapLevel();
        maxI = perfLvl == 3 ? 20 : 5;

        this.game = game;
        this.assetsManager = assetsManager;
    }

    public void reset(){
        timeLap = 0;
    }

    public void completeAssetPacksForLoading(AssetPack texturePack, AssetPack soundPack, AssetPack musicPack) {

        texturePack.add("gameBackground", "Pictures/Background/gameBackground.png");
        texturePack.add("complementBackground", "Pictures/Background/complementBackground.png");

        perfLvl = game.applicationDataDebug.getHeapLevel();

        for (int i = 1; i <= maxI; i++)
        {
            texturePack.add("background (" + i + ")", "Pictures/Background/background (" + i + ").png");
        }

        if (perfLvl == 3) {
            for (int i = 1; i <= 10; i++) {
                texturePack.add("cloud (" + i + ")", "Pictures/Background/cloud (" + i + ").png");
            }
        }
    }

    public void completePacksForPostLoading(TextureRegionPack trPack, AnimationPack animPack){
        trPack.add("gameBackground", "gameBackground");
        trPack.add("complementBackground", "complementBackground");

        perfLvl = game.applicationDataDebug.getHeapLevel();

        for (int i = 1; i <= maxI; i++)
        {
            trPack.add("background (" + i + ")", "background (" + i + ")");
        }

        if (perfLvl == 3) {
            for (int i = 1; i <= 10; i++) {
                trPack.add("cloud (" + i + ")", "cloud (" + i + ")");
            }
        }
    }

    @Override
    public boolean draw(SpriteBatch batcher, float delta, ShapeRenderer shapeRenderer) {
        pannelFrontX -= delta * frontPannelSpeed;
        if (pannelFrontX < -GameContants.BACKGROUND_PANNEL_WIDTH)
        {
            pannelFrontX += GameContants.BACKGROUND_PANNEL_WIDTH;
            pannelFrontID = (pannelFrontID) % maxI + 1; //parce qu'on va de 1 à 20, pas de 0 à 19
        }

        if (perfLvl == 3) {
            pannelBackX -= delta * backPannelSpeed;
            if (pannelBackX < -GameContants.BACKGROUND_PANNEL_WIDTH)
            {
                pannelBackX += GameContants.BACKGROUND_PANNEL_WIDTH;
                pannelBackID = (pannelBackID) % 10 + 1; //parce qu'on va de 1 à 20, pas de 0 à 19
            }
        }

        batcher.draw(assetsManager.getTextureRegion("complementBackground"), GameContants.BACKGROUND_INIT_POSITION_X, GameContants.BACKGROUND_POSITION_Y - GameContants.BACKGROUND_HEIGTH * 562 / 1380,
                GameContants.BACKGROUND_WIDTH, GameContants.BACKGROUND_HEIGTH * 562 / 1380);
        batcher.draw(assetsManager.getTextureRegion("gameBackground"), GameContants.BACKGROUND_INIT_POSITION_X, GameContants.BACKGROUND_POSITION_Y, GameContants.BACKGROUND_WIDTH, GameContants.BACKGROUND_HEIGTH);

        if (perfLvl == 3) {
            batcher.draw(assetsManager.getTextureRegion("cloud (" + pannelBackID + ")"), pannelBackX, GameContants.BACKGROUND_POSITION_Y, GameContants.BACKGROUND_PANNEL_WIDTH, GameContants.BACKGROUND_HEIGTH);
            batcher.draw(assetsManager.getTextureRegion("cloud (" + (pannelBackID % 10 + 1) + ")"), pannelBackX + GameContants.BACKGROUND_PANNEL_WIDTH, GameContants.BACKGROUND_POSITION_Y, GameContants.BACKGROUND_PANNEL_WIDTH, GameContants.BACKGROUND_HEIGTH);
        }

        batcher.draw(assetsManager.getTextureRegion("background (" + pannelFrontID +")"), pannelFrontX, GameContants.BACKGROUND_POSITION_Y, GameContants.BACKGROUND_PANNEL_WIDTH, GameContants.BACKGROUND_HEIGTH);
        batcher.draw(assetsManager.getTextureRegion("background (" + (pannelFrontID % maxI + 1) +")"), pannelFrontX + GameContants.BACKGROUND_PANNEL_WIDTH, GameContants.BACKGROUND_POSITION_Y, GameContants.BACKGROUND_PANNEL_WIDTH, GameContants.BACKGROUND_HEIGTH);

        return false;
    }
}
