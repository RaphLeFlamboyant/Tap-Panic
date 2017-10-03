package com.flamboyant.tappanic.world.phases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.flamboyant.common.Activator;
import com.flamboyant.common.IActivable;
import com.flamboyant.tappanic.world.TapPanicWorld;
import com.flamboyant.tappanic.world.SymbolCapsule;

/**
 * Created by Reborn Portable on 22/10/2015.
 */
public abstract class GameplayPhaseModule implements IActivable {
    protected TapPanicWorld world;
    protected int phaseID;
    protected boolean isActive = false;
    protected boolean isPaused = false;
    protected Activator activator = new Activator();

    protected GameplayPhaseModule(TapPanicWorld world, int phaseID)
    {
        this.world = world;
        this.phaseID = phaseID;
    }

    public void reset()
    {
        isActive = false;
        isPaused = false;
    }

    public void activate()
    {
        isActive = true;
        activator.activate();
    }

    public void deactivate()
    {
        isActive = false;
        activator.deactivate();
    }

    public void pause()
    {
        isPaused = true;
    }

    public void unpause()
    {
        isPaused = false;
    }

    public void addNotifyActivation(IActivable listener)
    {
        activator.addNewSon(listener);
    }

    public abstract void update(float delta);

    public void initializeSubPhaseInWorld()
    {}

    public boolean isActive() {
        return isActive;
    }
    public boolean isPaused() {
        return isPaused;
    }


    public abstract boolean touchDown(int screenX, int screenY, int pointer, int button);

    public abstract boolean touchUp(int screenX, int screenY, int pointer, int button);

    public abstract boolean touchDragged(int screenX, int screenY, int pointer);

    public abstract boolean mouseMoved(int screenX, int screenY);


}
