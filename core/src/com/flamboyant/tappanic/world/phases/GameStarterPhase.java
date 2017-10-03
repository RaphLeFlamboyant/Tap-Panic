package com.flamboyant.tappanic.world.phases;

import com.flamboyant.tappanic.world.TapPanicWorld;

/**
 * Created by Reborn Portable on 19/10/2015.
 */
public class GameStarterPhase extends GameplayPhaseModule {
    public static int STEP_HOWTOPLAY = 1;
    public static int STEP_TAPTOPLAY = 2;
    public static int STEP_START = 3;

    private int step = STEP_HOWTOPLAY;
    private boolean showHowToPlay;
    private boolean activateNormalPhase = true;

    public GameStarterPhase(TapPanicWorld world, int phaseID, boolean showHowToPlay) {
        super(world, phaseID);

        this.showHowToPlay = showHowToPlay;
    }

    @Override
    public void reset()
    {
        super.reset();

        activateNormalPhase = true;
        step = STEP_TAPTOPLAY;
    }

    public void update(float delta)
    {
    }

    public void relaunch()
    {
        step = showHowToPlay ? STEP_HOWTOPLAY : STEP_TAPTOPLAY;
    }

    public int getStep() {
        return step;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        if (step == STEP_HOWTOPLAY)
            step = STEP_TAPTOPLAY;
        else if (step == STEP_TAPTOPLAY) {
            isActive = false;

            if (activateNormalPhase)
            {
                if (world.isBossMode())
                    world.getBossPhase().activate();
                else
                    world.getPhase("Normal").activate();
                activateNormalPhase = false;
            }
            world.unpauseAll();
            step = STEP_START;
            activateNormalPhase = true;
        }

        return true;
    }

    @Override
    public void pause()
    {
        this.activate();
        activateNormalPhase = false;
        step = STEP_TAPTOPLAY;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean isShowHowToPlay() {
        return showHowToPlay;
    }

    public void setShowHowToPlay(boolean showHowToPlay) {
        this.showHowToPlay = showHowToPlay;
    }
}
