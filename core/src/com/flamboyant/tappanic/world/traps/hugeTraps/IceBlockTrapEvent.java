package com.flamboyant.tappanic.world.traps.hugeTraps;

import com.flamboyant.tappanic.world.TapPanicWorld;
import com.flamboyant.tappanic.world.phases.NormalPhase;

/**
 * Created by Reborn Portable on 11/01/2016.
 */
public class IceBlockTrapEvent extends HugeTrapEvent {
    public IceBlockTrapEvent(float gameDifficulty, NormalPhase np, TapPanicWorld world) {
        super(25, 20, 80, gameDifficulty, np, world);
    }

    @Override
    public void activateTrap() {
        world.getPhase("IceBlock").activate();
    }

    @Override
    public boolean validateTimeOut() {
        return false;
    }
}
