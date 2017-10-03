package com.flamboyant.common;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Reborn Portable on 17/02/2016.
 */
public abstract class ScriptedSceneRenderer {
    // Returns true if it's finished
    public abstract boolean draw(SpriteBatch batcher, float delta, ShapeRenderer shapeRenderer);
}
