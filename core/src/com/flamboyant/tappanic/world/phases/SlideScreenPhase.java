package com.flamboyant.tappanic.world.phases;

import com.flamboyant.tappanic.world.TapPanicWorld;

/**
 * Created by Reborn Portable on 20/11/2015.
 */

//TODO : on fera ça un peu plus tard
public class SlideScreenPhase extends GameplayPhaseModule {
    private int directionX;
    private int directionY;
    private int distanceToSlide;

    protected SlideScreenPhase(TapPanicWorld world, int phaseID) {
        super(world, phaseID);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void activate()
    {
        super.activate();
        //TODO : init dirX, dirY et distancetoshow
    }


    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
}
