package com.flamboyant.tappanic.world.traps.hugeTraps;

import com.flamboyant.tappanic.world.TapPanicWorld;
import com.flamboyant.tappanic.world.phases.NormalPhase;

/**
 * Created by Reborn Portable on 20/11/2015.
 */
public class SlideScreenTrapEvent extends HugeTrapEvent {


    public SlideScreenTrapEvent(int weigth, int resistance, int activationTurn, float gameDifficulty, NormalPhase np, TapPanicWorld world) {
        super(weigth, resistance, activationTurn, gameDifficulty, np, world);
    }

    //TODO : activer la slide phase et trouver quand/comment on en sort
    @Override
    public void activateTrap() {
        //this.
    }

    @Override
    public boolean validateTimeOut() {
        return false;
    }
}
