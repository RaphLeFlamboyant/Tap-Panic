package com.flamboyant.tappanic.tools.assetpack;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Reborn Portable on 10/02/2016.
 */
public class AnimationPack {
    private Array<AnimationParameters> parametersList = new Array<AnimationParameters>();

    public void add(String textureName, String animName, int width, int heigth, Animation.PlayMode playMode)
    {
        parametersList.add(new AnimationParameters(textureName, animName, width, heigth, playMode));
    }

    public Array<AnimationParameters> getParametersList() {
        return parametersList;
    }
}
