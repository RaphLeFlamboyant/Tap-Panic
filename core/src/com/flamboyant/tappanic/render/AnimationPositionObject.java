package com.flamboyant.tappanic.render;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.flamboyant.common.MovableObject;
import com.flamboyant.tappanic.tools.HighLevelAssetManager;


/**
 * Created by Reborn Portable on 13/01/2016.
 */
public class AnimationPositionObject extends MovableObject implements IGraphicObject {
    private Vector2 acceleration = new Vector2();
    private Vector2 sizeAcceleration = new Vector2();

    private String animationName;
    private float timeAnim;
    private boolean animationFinished;

    public AnimationPositionObject(String animName){
        animationName = animName;
    }

    public void reset()
    {
        timeAnim = 0;
    }

    @Override
    public Vector2 getAcceleration() {
        return acceleration;
    }

    @Override
    public void setAcceleration(float x, float y) {
        acceleration.x = x;
        acceleration.y = y;
    }

    @Override
    public Vector2 getSizeAcceleration() {
        return sizeAcceleration;
    }

    @Override
    public void setSizeAcceleration(float x, float y) {
        sizeAcceleration.x = x;
        sizeAcceleration.y = y;
    }

    public String getAnimationName()
    {
        return animationName;
    }

    public void setAnimationName(String animName)
    {
        animationName = animName;
    }

    public boolean isAnimationFinished()
    {
        return animationFinished;
    }

    @Override
    public void update(float delta) {
        position.x = position.x + speed.x * delta;
        position.y = position.y + speed.y * delta;
        speed.x = speed.x + acceleration.x * delta;
        speed.y = speed.y + acceleration.y * delta;

        size.x = size.x + sizeSpeed.x * delta;
        size.y = size.y + sizeSpeed.y * delta;
        sizeSpeed.x = sizeSpeed.x + sizeAcceleration.x * delta;
        sizeSpeed.y = sizeSpeed.y + sizeAcceleration.y * delta;
    }

    public void draw(SpriteBatch batcher, float delta, boolean newCycle, HighLevelAssetManager assetManager){
        if (newCycle)
            timeAnim = 0;
        else
            timeAnim += delta;

        Animation animation = assetManager.getAnimation(animationName);
        animationFinished = animation.isAnimationFinished(timeAnim);
        batcher.draw(animation.getKeyFrame(timeAnim),
                position.x, position.y, size.x, size.y);
    }
}
