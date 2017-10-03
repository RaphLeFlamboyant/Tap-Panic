package com.flamboyant.tappanic.world.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.world.SymbolCapsule;
import com.flamboyant.tappanic.world.phases.NormalPhase;
import com.flamboyant.tappanic.world.Symbol;
import com.flamboyant.tappanic.world.SymbolsHandler;

import java.util.Random;

/**
 * Created by Reborn Portable on 10/11/2015.
 */
public class WrongKeyTrapEvent extends TrapEvent {

    public WrongKeyTrapEvent(float gameDifficulty, NormalPhase np) {
        super(10, 20, 40, gameDifficulty, np);//50, 1, 2, gameDifficulty, np);//
    }

    @Override
    public void activateTrap() {
        Random rnd = phase.getRandom();

        if (phase.getScoreTurn() < GameContants.TURN_ENABLE_HARDE_MODE)
            putRandomToShown();
        else if (phase.getScoreTurn() > GameContants.TURN_MAX_DIFFICULTY)
        {
            putFamilyToShown();
        }
        else
        {
            int diceRoll = rnd.nextInt(GameContants.TURN_MAX_DIFFICULTY - GameContants.TURN_ENABLE_HARDE_MODE) + GameContants.TURN_ENABLE_HARDE_MODE;
            if (diceRoll <= phase.getScoreTurn())
                putFamilyToShown();
            else
                putRandomToShown();
        }


        SymbolCapsule cap = phase.getShownSymbols().get(0);
        int realPosCap = (int) (cap.getPosition().x + cap.getSize().x / 2);
        int realPosRnd = rnd.nextInt(GameContants.SCREEN_WIDTH - GameContants.SYMBOL_MAX_SIZE - (int) (GameContants.GIRL_SIZE_X * 2.5)) + GameContants.GIRL_SIZE_X / 2;

        phase.setTargetGirlX(realPosRnd < (realPosCap - GameContants.GIRL_SIZE_X) ? realPosRnd : realPosRnd + GameContants.SYMBOL_MAX_SIZE + GameContants.GIRL_SIZE_X);
    }

    @Override
    public void activateVeryHardTrap() {
        activateTrap();
    }

    private void putRandomToShown()
    {
        SymbolsHandler sh = phase.getSymbolHandler();
        Random rnd = phase.getRandom();

        phase.getShownSymbols().get(0).setSymbol(sh.getNotInKeyboard(rnd.nextInt(sh.getSymbolsCount() - sh.getSymbolKeyboard().size)));
    }

    private void putFamilyToShown()
    {
        Gdx.app.log("Wrong Trap", "Hard mode activated at turn " + phase.getScoreTurn());
        SymbolsHandler sh = phase.getSymbolHandler();
        Random rnd = phase.getRandom();
        Symbol member = sh.getSymbolKeyboard().get(rnd.nextInt(sh.getSymbolKeyboard().size)).getSymbol();
        Symbol toPut;

        Array<Symbol> family = sh.getFamily(member);

        do {
            toPut = family.get(rnd.nextInt(family.size));
        } while (!sh.getSymbolsNotInKeyboard().contains(toPut, true) && (toPut != member));

        phase.getShownSymbols().get(0).setSymbol(toPut);
    }
}
