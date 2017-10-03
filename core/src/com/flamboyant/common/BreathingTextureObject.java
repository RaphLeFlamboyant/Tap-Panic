package com.flamboyant.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.flamboyant.tappanic.tools.HighLevelAssetManager;

/**
 * Created by Reborn Portable on 20/02/2016.
 */
public class BreathingTextureObject extends TextureRegionMovableObject {
    private float selfDelta;
    private float scaleFactor = 0.01f;

    public BreathingTextureObject(float selfDelta)
    {
        this.selfDelta = selfDelta;
    }

    public void draw(SpriteBatch batcher, float timeLap, HighLevelAssetManager assetManager)
    {
        TextureRegion tr = assetManager.getTextureRegion(textureName);
        float ratio = 1f + scaleFactor * MathUtils.cos((timeLap + selfDelta) * 2 * MathUtils.PI);
        batcher.draw(tr, position.x, position.y, size.x / 2, size.y / 2,
                size.x, size.y, ratio, ratio, rotation);

    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }
}
