package com.flamboyant.tappanic.world.SmartSymbols;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Reborn Portable on 15/02/2016.
 */
public class PathPoint {
    protected Vector2 position = new Vector2();
    protected float speed;
    protected Vector2 size = new Vector2();
    protected float sizeSpeed;
    protected float rotation;
    protected float rotationSpeed;
    protected float validationDistance;

    public PathPoint()
    {

    }

    public PathPoint(float x, float y, float width, float heigth, float speed, float sizeSpeed, float rotation, float rotationSpeed, float validationDistance)
    {
        position.x = x;
        position.y = y;
        size.x = width;
        size.y = heigth;
        this.speed = speed;
        this.sizeSpeed = sizeSpeed;
        this.rotation = rotation;
        this.rotationSpeed = rotationSpeed;
        this.validationDistance = validationDistance;
    }

    public void setPosition (float x, float y)
    {
        position.set(x, y);
    }

    public void setSpeed (float s)
    {
        speed = s;
    }

    public void setSize (float w, float h)
    {
        size.set(w, h);
    }

    public void setSizeSpeed (float s)
    {
        sizeSpeed = s;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public float getSpeed()
    {
        return speed;
    }

    public Vector2 getSize()
    {
        return size;
    }

    public float getSizeSpeed() {
        return sizeSpeed;
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

    public float getValidationDistance() {
        return validationDistance;
    }

    public void setValidationDistance(float validationDistance) {
        this.validationDistance = validationDistance;
    }
}
