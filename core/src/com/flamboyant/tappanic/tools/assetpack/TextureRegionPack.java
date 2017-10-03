package com.flamboyant.tappanic.tools.assetpack;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Reborn Portable on 10/02/2016.
 */
public class TextureRegionPack {
    private Array<TextureRegionParameters> parametersList = new Array<TextureRegionParameters>();

    public void add(String textureName, String textureRegionName, int x, int y, int width, int heigth, boolean flipY)
    {
        parametersList.add(new TextureRegionParameters(textureName, textureRegionName, x, y, width, heigth, flipY));
    }


    public void add(String textureName, String textureRegionName, int x, int y, int width, int heigth)
    {
        add(textureName, textureRegionName, x, y, width, heigth, true);
    }



    public void add(String textureName, String textureRegionName) {
        add(textureName, textureRegionName, -1, -1, -1, -1, true);
    }


    public Array<TextureRegionParameters> getParametersList() {
        return parametersList;
    }
}
