package com.flamboyant.tappanic.render;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Reborn Portable on 13/01/2016.
 */
public interface IGraphicObject {
    Vector2 getPosition();
    Vector2 getSpeed();
    Vector2 getAcceleration();

    void setPosition(float x, float y);
    void setSpeed(float x, float y);
    void setAcceleration(float x, float y);

    Vector2 getSize();
    Vector2 getSizeSpeed();
    Vector2 getSizeAcceleration();

    void setSize(float x, float y);
    void setSizeSpeed(float x, float y);
    void setSizeAcceleration(float x, float y);

    void update(float delta);
}
