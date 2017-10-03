package com.flamboyant.tappanic.tools.assetpack;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by Reborn Portable on 10/02/2016.
 */
public class AnimationParameters {
    private String textureName;
    private String animationName;
    private int width, heigth;
    private Animation.PlayMode playMode;

    public AnimationParameters (String textureName, String animName, int width, int heigth, Animation.PlayMode playMode)
    {
        this.textureName = textureName;
        this.animationName = animName;
        this.width = width;
        this.heigth = heigth;
        this.playMode = playMode;
    }

    public String getTextureName() {
        return textureName;
    }

    public String getAnimationName() {
        return animationName;
    }

    public int getWidth() {
        return width;
    }

    public int getHeigth() {
        return heigth;
    }

    public Animation.PlayMode getPlayMode() {
        return playMode;
    }
}
