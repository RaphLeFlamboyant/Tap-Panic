package com.flamboyant.tappanic.world.traps;

import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.world.SymbolsHandler;
import com.flamboyant.tappanic.world.phases.NormalPhase;

/**
 * Created by Reborn Portable on 05/01/2016.
 */
public class RotateTrapEvent extends TrapEvent {
    public RotateTrapEvent(float gameDifficulty, NormalPhase np) {
        super(80, 1, 0, gameDifficulty, np);
    }

    @Override
    public void activateTrap() {
        phase.getShownSymbols().get(0).setRotationSpeed(phase.getRandom().nextInt(GameContants.SYMBOL_MAX_ROTATE_SPEED * 2) - GameContants.SYMBOL_MAX_ROTATE_SPEED);
    }

    @Override
    public void activateVeryHardTrap() {
        activateTrap();
    }
}
