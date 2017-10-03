package com.flamboyant.tappanic.world.traps.hugeTraps;

import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.world.TapPanicWorld;
import com.flamboyant.tappanic.world.Symbol;
import com.flamboyant.tappanic.world.SymbolCapsule;
import com.flamboyant.tappanic.world.phases.NormalPhase;

import java.util.Iterator;

/**
 * Huge trap event are traps that mechanics make it impossible to happen at the same time than an other trap
 */
public abstract class HugeTrapEvent {
    protected float resistance;
    protected int activationTurn;
    protected float generalDifficulty;
    protected NormalPhase phase;
    protected TapPanicWorld world;
    protected int finalWeigth;
    protected float last = 0;

    public HugeTrapEvent(int weigth, int resistance, int activationTurn, float gameDifficulty, NormalPhase np, TapPanicWorld world) {
        this.phase = np;
        generalDifficulty = gameDifficulty;
        this.resistance = resistance;
        this.activationTurn = activationTurn;
        this.world = world;
        finalWeigth = weigth;
    }

    public void reset()
    {
        last = 0;
    }

    public float getNextWeigth() {
        if (phase.getScoreTurn() >= activationTurn) {
            if (last == 0) {
                last = finalWeigth * 0.1f;
            } else
                last = last + (finalWeigth - last) * (1 - resistance / (GameContants.MAX_TRAP_WEIGTH * generalDifficulty));

            return last;
        } else
            return 0;
    }

    public float getLastWeigth()
    {
        return last;
    }

    public abstract void activateTrap();
    public boolean validateSelection(Symbol symbol)
    {
        for(Iterator<SymbolCapsule> i = phase.getShownSymbols().iterator(); i.hasNext(); ) {
            SymbolCapsule item = i.next();
            if (item.getSymbol().equals(symbol))
                return true;
        }

        return false;
    }

    public abstract boolean validateTimeOut();

}
