package com.flamboyant.tappanic.world.traps;

import com.badlogic.gdx.Gdx;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.world.phases.NormalPhase;
import com.flamboyant.tappanic.world.SymbolsHandler;

import java.util.Random;

/**
 * Created by Reborn Portable on 10/11/2015.
 */
public class SwapTrapEvent extends TrapEvent {
    //int probability, int resistance, int activationTurn
    public SwapTrapEvent(float gameDifficulty, NormalPhase np) {
        super(15, 15, 20, gameDifficulty, np);
    }

    @Override
    public void activateTrap() { // TODO : les faire slider à la place
        SymbolsHandler sh = phase.getSymbolHandler();
        Random rnd = phase.getRandom();

        int first = rnd.nextInt(sh.getSymbolKeyboard().size);
        int second;
        do
        {
            second = rnd.nextInt(sh.getSymbolKeyboard().size);
        } while (second != first);
        sh.swapKey(first, second);

        if (phase.getScoreTurn() > GameContants.TURN_MAX_DIFFICULTY || rnd.nextInt(GameContants.TURN_MAX_DIFFICULTY - GameContants.TURN_ENABLE_HARDE_MODE) + GameContants.TURN_ENABLE_HARDE_MODE <= phase.getScoreTurn())
        {
            Gdx.app.log("Swap Trap", "Hard mode activated at turn " + phase.getScoreTurn());
            int chosenSymbol = rnd.nextInt(2) == 0 ? first : second;
            phase.getShownSymbols().get(0).setSymbol(sh.getSymbolKeyboard().get(chosenSymbol).getSymbol());
        }
    }

    @Override
    public void activateVeryHardTrap() {
        SymbolsHandler sh = phase.getSymbolHandler();
        Random rnd = phase.getRandom();

        int first = rnd.nextInt(sh.getSymbolKeyboard().size);
        int second, third;
        do { second = rnd.nextInt(sh.getSymbolKeyboard().size); } while (second != first);
        do { third = rnd.nextInt(sh.getSymbolKeyboard().size); } while (second != third && first != third);
        sh.swapKey(first, second);
        sh.swapKey(first, third);
    }
}
