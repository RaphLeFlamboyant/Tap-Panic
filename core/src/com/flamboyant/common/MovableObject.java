package com.flamboyant.common;

import com.badlogic.gdx.math.Vector2;
import com.flamboyant.tappanic.world.Symbol;

/**
 * Created by Reborn Portable on 28/01/2016.
 */
public class MovableObject implements IMovableObject {
    protected Vector2 position = new Vector2();
    protected Vector2 targetPos = new Vector2();
    protected Vector2 speed = new Vector2();
    protected Vector2 size = new Vector2();
    protected Vector2 sizeSpeed = new Vector2();
    protected Vector2 targetSize = new Vector2();
    protected float rotation;
    protected float rotationSpeed;

    public void setPosition (float x, float y)
    {
        position.set(x, y);
    }

    public void setSpeed (float dx, float dy)
    {
        speed.set(dx, dy);
    }

    public void setSize (float w, float h)
    {
        size.set(w, h);
    }

    public void setSizeSpeed (float w, float h)
    {
        sizeSpeed.set(w, h);
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public Vector2 getSpeed()
    {
        return speed;
    }

    public Vector2 getSize()
    {
        return size;
    }

    public Vector2 getTargetPosition() {
        return targetPos;
    }

    public void setTargetPos(float x, float y)
    {
        targetPos.set(x, y);
    }

    public Vector2 getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(float tSizeX, float tSizeY)
    {
        this.targetSize.x = tSizeX;
        this.targetSize.y = tSizeY;
    }

    public Vector2 getSizeSpeed() {
        return sizeSpeed;
    }

    public void setSizeSpeed(int dx, int dy)
    {
        sizeSpeed.set(dx, dy);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }
}
