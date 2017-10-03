package com.flamboyant.common;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Reborn Portable on 20/02/2016.
 */
public interface IMovableObject {
    void setPosition (float x, float y);
    void setSpeed (float dx, float dy);
    void setSize (float w, float h);
    void setSizeSpeed (float w, float h);
    Vector2 getPosition();
    Vector2 getSpeed();
    Vector2 getSize();
    Vector2 getTargetPosition();
    void setTargetPos(float x, float y);
    Vector2 getTargetSize();
    void setTargetSize(float tSizeX, float tSizeY);
    Vector2 getSizeSpeed();
    void setSizeSpeed(int dx, int dy);
    float getRotation();
    void setRotation(float rotation);
    float getRotationSpeed();
    void setRotationSpeed(float rotationSpeed);
}
