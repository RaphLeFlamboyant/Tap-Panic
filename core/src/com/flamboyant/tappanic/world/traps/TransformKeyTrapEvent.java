package com.flamboyant.tappanic.world.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.world.Symbol;
import com.flamboyant.tappanic.world.phases.NormalPhase;
import com.flamboyant.tappanic.world.SymbolsHandler;

import java.util.Random;

/**
 * Created by Reborn Portable on 20/11/2015.
 */
public class TransformKeyTrapEvent extends TrapEvent  {
    public TransformKeyTrapEvent(float gameDifficulty, NormalPhase np) {
        super(10, 25, 30, gameDifficulty, np);
    }

    @Override
    public void activateTrap() {
        Random rnd = phase.getRandom();

        if (phase.getScoreTurn() < GameContants.TURN_ENABLE_HARDE_MODE)
            transformFromRandom();
        else if (phase.getScoreTurn() > GameContants.TURN_MAX_DIFFICULTY)
        {
            if (rnd.nextBoolean())
                transformFromFamily();
            else
                transformFromRandom();
        }
        else
        {
            int diceRoll = rnd.nextInt((GameContants.TURN_MAX_DIFFICULTY - GameContants.TURN_ENABLE_HARDE_MODE) / 2) + GameContants.TURN_ENABLE_HARDE_MODE;
            if (diceRoll <= phase.getScoreTurn())
                transformFromFamily();
            else
                transformFromRandom();
        }
    }

    @Override
    public void activateVeryHardTrap() {
        transformFromRandom();
        transformFromFamily();
    }

    private void transformFromRandom()
    {
        SymbolsHandler sh = phase.getSymbolHandler();
        Random rnd = phase.getRandom();

        int keyToChange = rnd.nextInt(sh.getSymbolKeyboard().size);
        int keyToTake = rnd.nextInt(sh.getSymbolsNotInKeyboard().size);

        sh.changeKey(keyToChange, keyToTake);
    }

    private void transformFromFamily()
    {
        Gdx.app.log("Transform Trap", "Hard mode activated at turn " + phase.getScoreTurn());
        SymbolsHandler sh = phase.getSymbolHandler();
        Random rnd = phase.getRandom();
        int memberIndex = rnd.nextInt(sh.getSymbolKeyboard().size);
        Symbol member = sh.getSymbolKeyboard().get(memberIndex).getSymbol();
        Symbol toPut;

        //take familly target
        Array<Symbol> family = sh.getFamily(member);

        do {
            toPut = family.get(rnd.nextInt(family.size));
        } while (!sh.getSymbolsNotInKeyboard().contains(toPut, true));// && (toPut != member));

        int toRemplace = rnd.nextInt(sh.getSymbolKeyboard().size - 1);
        toRemplace = toRemplace == memberIndex ? toRemplace + 1 : toRemplace;

        //Changing show to transformed key depending on max 75% proba
        int maxDif = (GameContants.TURN_MAX_DIFFICULTY - GameContants.TURN_ENABLE_HARDE_MODE);
        int ultratrapProba = phase.getScoreTurn() >= GameContants.TURN_MAX_DIFFICULTY ? 75 : 75 * ((maxDif - GameContants.TURN_MAX_DIFFICULTY + phase.getScoreTurn()) / maxDif);

        if (rnd.nextInt(75) <= ultratrapProba)
            phase.getShownSymbols().get(0).setSymbol(sh.getSymbolKeyboard().get(toRemplace).getSymbol());

        //replacing
        int indexToPut = sh.getSymbolsNotInKeyboard().indexOf(toPut, true);
        sh.changeKey(toRemplace, indexToPut);
    }
}
