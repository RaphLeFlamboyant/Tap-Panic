package com.flamboyant.tappanic.world.SmartSymbols;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.flamboyant.tappanic.world.Symbol;
import com.flamboyant.tappanic.world.SymbolCapsule;

/**
 * Created by Reborn Portable on 15/02/2016.
 */
public class SmartSymbolCapsule {
    private SymbolCapsule capsule;

    private int stepPath = 0;
    private Vector2 direction = new Vector2();

    private Array<PathPoint> path = new Array<PathPoint>();
    private float smoothFactor;
    private boolean loop;

    public SmartSymbolCapsule(Symbol cap, int x, int y, int w, int h, float smoothFactor, boolean loopMove)
    {
        capsule = new SymbolCapsule(cap, x, y, w, h);
        this.smoothFactor = smoothFactor;
        loop = loopMove;
    }

    public SmartSymbolCapsule(SymbolCapsule cap, float smoothFactor, boolean loopMove)
    {
        capsule = cap;
        this.smoothFactor = smoothFactor;
        loop = loopMove;
    }

    public void update (float delta)
    {
        if (stepPath == path.size)
            return;

        float smoothDelta = 1 - smoothFactor;

        capsule.setPosition(capsule.getSpeed().x * delta + capsule.getPosition().x, capsule.getSpeed().y * delta + capsule.getPosition().y);
        capsule.setSize(capsule.getSizeSpeed().x * delta + capsule.getSize().x, capsule.getSizeSpeed().y * delta + capsule.getSize().y);
        capsule.setRotation(capsule.getRotationSpeed() * delta + capsule.getRotation());

        PathPoint pt = path.get(stepPath);
        float x = pt.getPosition().x - capsule.getPosition().x;
        float y = pt.getPosition().y - capsule.getPosition().y;

        if (Math.sqrt(x*x + y*y) < pt.getValidationDistance()) {
            stepPath++;

            if (stepPath == path.size && loop)
                stepPath = 0;
            else if (stepPath == path.size)
                return;
            pt = path.get(stepPath);
        }

        float vToTargetX;
        float vToTargetY;
        float distance;

        if (!capsule.getPosition().equals(pt.getPosition())) {
            vToTargetX = pt.getPosition().x - capsule.getPosition().x;
            vToTargetY = pt.getPosition().y - capsule.getPosition().y;
            distance = (float) Math.sqrt(vToTargetX * vToTargetX + vToTargetY * vToTargetY);
            vToTargetX = vToTargetX / distance * pt.getSpeed();
            vToTargetY = vToTargetY / distance * pt.getSpeed();

            Vector2 currentSpeed = capsule.getSpeed();
            capsule.setSpeed(currentSpeed.x + (vToTargetX - currentSpeed.x) * smoothDelta, currentSpeed.y + (vToTargetY - currentSpeed.y) * smoothDelta);
        }

        if (!capsule.getSize().equals(pt.getSize())) {
            vToTargetX = pt.getSize().x - capsule.getSize().x;
            vToTargetY = pt.getSize().y - capsule.getSize().y;
            distance = (float) Math.sqrt(vToTargetX * vToTargetX + vToTargetY * vToTargetY);
            vToTargetX = vToTargetX / distance * pt.getSizeSpeed();
            vToTargetY = vToTargetY / distance * pt.getSizeSpeed();

            Vector2 currentSpeed = capsule.getSizeSpeed();
            capsule.setSizeSpeed(currentSpeed.x + (vToTargetX - currentSpeed.x) * smoothDelta, currentSpeed.y + (vToTargetY - currentSpeed.y) * smoothDelta);
        }

        if (pt.getRotation() == -1) {
            vToTargetX = pt.getRotationSpeed();
            float rSpeed = capsule.getRotationSpeed();
            capsule.setRotationSpeed(rSpeed + (vToTargetX - rSpeed) * smoothDelta);
        }
        else {
            vToTargetX = pt.rotation - capsule.getRotation();
            float rSpeed = capsule.getRotationSpeed();
            capsule.setRotationSpeed(rSpeed + (vToTargetX - rSpeed) * smoothDelta);
        }
    }

    public Vector2 getPosition()
    {
        return capsule.getPosition();
    }

    public Vector2 getSpeed()
    {
        return capsule.getSpeed();
    }

    public Vector2 getSize()
    {
        return capsule.getSize();
    }

    public Vector2 getTargetPosition() {
        return capsule.getTargetPosition();
    }

    public Vector2 getTargetSize() {
        return capsule.getTargetSize();
    }

    public Vector2 getSizeSpeed() {
        return capsule.getSizeSpeed();
    }

    public float getRotation() {
        return capsule.getRotation();
    }

    public float getRotationSpeed() {
        return capsule.getRotationSpeed();
    }

    public Array<PathPoint> getPath() {
        return path;
    }

    public Symbol getSymbol()
    {
        return capsule.getSymbol();
    }

    public void setSymbol(Symbol symbol)
    {
        capsule.setSymbol(symbol);
    }

    public SymbolCapsule getCapsule() {
        return capsule;
    }
}
