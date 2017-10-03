package com.flamboyant.tappanic.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Reborn Portable on 13/10/2015.
 */
public class Symbol {
    private Texture symbolImg;
    private String name;

    public Symbol(String nm)
    {
        name = nm;
    }

    public Texture getSymbolImg() {
        return symbolImg;
    }

    public void setSymbolImg(Texture symbolImg) {
        this.symbolImg = symbolImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
