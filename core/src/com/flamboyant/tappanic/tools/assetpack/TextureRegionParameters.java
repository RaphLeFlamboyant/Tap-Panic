package com.flamboyant.tappanic.tools.assetpack;

/**
 * Created by Reborn Portable on 10/02/2016.
 */
public class TextureRegionParameters {
    private String textureName;
    private String textureRegionName;
    private int x, y, width, heigth;
    private boolean flipY;

    public TextureRegionParameters (String textureName, String textureRegionName, int x, int y, int width, int heigth, boolean flipY)
    {
        this.textureName = textureName;
        this.textureRegionName = textureRegionName;
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
        this.flipY = flipY;
    }

    public String getTextureName() {
        return textureName;
    }

    public String getTextureRegionName() {
        return textureRegionName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeigth() {
        return heigth;
    }

    public boolean isFlipY() {
        return flipY;
    }
}
