package com.flamboyant.tappanic.world.traps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.SymbolCapsuleHelper;
import com.flamboyant.tappanic.world.SymbolCapsule;
import com.flamboyant.tappanic.world.SymbolsHandler;
import com.flamboyant.tappanic.world.phases.NormalPhase;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by Reborn Portable on 05/01/2016.
 */
public class SlideKeyTrapEvent extends TrapEvent {

    public SlideKeyTrapEvent(float generalDif, NormalPhase np) {
        super(15, 25, 10, generalDif, np);
    }

    @Override
    public void activateTrap() {
        Array<SymbolCapsule> keyboard = phase.getSymbolHandler().getSymbolKeyboard();
        Random rnd = phase.getRandom();

        if (phase.getScoreTurn() < GameContants.TURN_ENABLE_HARDE_MODE)
        {
            activateEasyMode(keyboard);
        }
        else if (phase.getScoreTurn() > GameContants.TURN_MAX_DIFFICULTY)
        {
            activateHardMode(keyboard);
        }
        else
        {
            int diceRoll = rnd.nextInt(GameContants.TURN_MAX_DIFFICULTY - GameContants.TURN_ENABLE_HARDE_MODE) + GameContants.TURN_ENABLE_HARDE_MODE;
            if (diceRoll <= phase.getScoreTurn())
                activateHardMode(keyboard);
            else
                activateEasyMode(keyboard);
        }
    }

    @Override
    public void activateVeryHardTrap() {
        Array<SymbolCapsule> keyboard = phase.getSymbolHandler().getSymbolKeyboard();
        Random rnd = phase.getRandom();

        int turn = phase.getScoreTurn();
        Array<Integer> indexList = new Array<Integer>();
        Array<Vector2> freePlaces = SymbolCapsuleHelper.getAllFreePosition(phase);

        int nbToMove = 4;

        for (int i = 0; i < keyboard.size; i++)
            indexList.add(i);

        while (nbToMove > 0) {
            int rndIndexList = rnd.nextInt(indexList.size);
            int chosenIndex = indexList.get(rndIndexList);
            SymbolCapsule cap = keyboard.get(chosenIndex);
            indexList.removeIndex(rndIndexList);

            int rndFreePlaces = rnd.nextInt(freePlaces.size);
            Vector2 target = freePlaces.get(rndFreePlaces);
            freePlaces.removeIndex(rndFreePlaces);

            target.set(target.x + (rnd.nextInt(2 * GameContants.KEY_START_MARGIN)) - GameContants.KEY_START_MARGIN,
                    target.y + (rnd.nextInt(2 * GameContants.KEY_START_MARGIN)) - GameContants.KEY_START_MARGIN);

            SymbolCapsuleHelper.changeTargetAndUpdateSpeed(cap, target.x, target.y);

            nbToMove--;
        }
    }

    private void activateEasyMode(Array<SymbolCapsule> keyboard)
    {
        Random rnd = phase.getRandom();

        SymbolCapsule cap = keyboard.get(rnd.nextInt(keyboard.size));

        Vector2 target = SymbolCapsuleHelper.findRandomFreeKeyPosition(phase);
        target.set(target.x + (rnd.nextInt(2 * GameContants.KEY_START_MARGIN)) - GameContants.KEY_START_MARGIN,
                target.y + (rnd.nextInt(2 * GameContants.KEY_START_MARGIN)) - GameContants.KEY_START_MARGIN);

        SymbolCapsuleHelper.changeTargetAndUpdateSpeed(cap,  target.x,  target.y);
    }

    private void activateHardMode(Array<SymbolCapsule> keyboard)
    {
        Gdx.app.log("Slide Trap", "Hard mode activated at turn " + phase.getScoreTurn());
        Random rnd = phase.getRandom();
        int turn = phase.getScoreTurn();
        Array<Integer> indexList = new Array<Integer>();
        Array<Vector2> freePlaces = SymbolCapsuleHelper.getAllFreePosition(phase);

        int nbToMove = rnd.nextInt(turn / GameContants.TURN_ENABLE_HARDE_MODE) + 1;
        nbToMove = nbToMove > keyboard.size ? keyboard.size : nbToMove;

        for (int i = 0; i < keyboard.size; i++)
            indexList.add(i);

        while (nbToMove > 0)
        {
            int rndIndexList = rnd.nextInt(indexList.size);
            int chosenIndex = indexList.get(rndIndexList);
            SymbolCapsule cap = keyboard.get(chosenIndex);
            indexList.removeIndex(rndIndexList);

            int rndFreePlaces = rnd.nextInt(freePlaces.size);
            Vector2 target = freePlaces.get(rndFreePlaces);
            freePlaces.removeIndex(rndFreePlaces);

            target.set(target.x + (rnd.nextInt(2 * GameContants.KEY_START_MARGIN)) - GameContants.KEY_START_MARGIN,
                    target.y + (rnd.nextInt(2 * GameContants.KEY_START_MARGIN)) - GameContants.KEY_START_MARGIN);

            SymbolCapsuleHelper.changeTargetAndUpdateSpeed(cap,  target.x,  target.y);

            nbToMove--;
        }
    }
}
