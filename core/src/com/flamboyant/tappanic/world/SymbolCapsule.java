package com.flamboyant.tappanic.world;

import com.badlogic.gdx.math.Vector2;
import com.flamboyant.common.MovableObject;

/**
 * Created by Reborn Portable on 04/01/2016.
 */
public class SymbolCapsule extends MovableObject {
    private Symbol symbol;

    public SymbolCapsule(Symbol s, int x, int y, int w, int h)
    {
        symbol = s;
        position = new Vector2(x, y);
        size = new Vector2(w, h);
        speed = new Vector2(0, 0);
        sizeSpeed = new Vector2(0, 0);
        rotation = 0;
        rotationSpeed = 0;
    }


    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}
