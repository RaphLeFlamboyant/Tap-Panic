package com.flamboyant.tappanic.world.traps;

import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.world.SymbolCapsule;
import com.flamboyant.tappanic.world.phases.NormalPhase;
import com.flamboyant.tappanic.world.Symbol;

import java.util.Iterator;

/**
 * Created by Reborn Portable on 10/11/2015.
 */

/*
* Normal
* WrongKey 5
* Swap 15
* Transform une key en une out du keyboard 20
* Screen slide 45
* Rotate 40
* Blind 35
* Ice Block 30
* Malus 10
 */

public abstract class TrapEvent {
    protected float resistance;
    protected int activationTurn;
    protected float generalDifficulty;
    protected NormalPhase phase;
    protected int finalProba;
    protected float last = 0;

    public TrapEvent(int probability, int resistance, int activationTurn, float gameDifficulty, NormalPhase np) {
        this.phase = np;
        generalDifficulty = gameDifficulty;
        this.resistance = resistance;
        this.activationTurn = activationTurn;
        finalProba = probability;
    }

    public void reset()
    {
        last = 0;
    }

    public float getNextProba() {
        if (phase.getScoreTurn() >= activationTurn) {
            if (last == 0) {
                last = finalProba * 0.1f;
            } else
                last = last + (finalProba - last) * (1 - (resistance / (GameContants.MAX_TRAP_WEIGTH * generalDifficulty)));

            return last;
        } else
            return 0;
    }

    public float getLastProba()
    {
        return last;
    }

    public abstract void activateTrap();
    public abstract void activateVeryHardTrap();
}

