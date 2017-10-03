package com.flamboyant.tappanic.tools;

import com.badlogic.gdx.utils.Array;
import com.flamboyant.tappanic.world.traps.TrapEvent;
import com.flamboyant.tappanic.world.traps.hugeTraps.HugeTrapEvent;

import java.util.Random;

/**
 * Created by Reborn Portable on 05/01/2016.
 */
public class TrapEventHelper {
    public static int sumHugeTrapEventWeigths(Array<HugeTrapEvent> list)
    {
        int res = 0;

        for (int i = 0; i < list.size; i++)
            res += list.get(i).getNextWeigth();

        return res;
    }

    /**
     * @return null if no huge trapEvent or the corresponding HugeTrapEvent either
     */
    public static HugeTrapEvent choseTrapsAndLaunch(Array<TrapEvent> trapEvents, Array<HugeTrapEvent> hugeTrapEvents, Random rnd, int probaHuge, boolean veryHardMode)
    {
        int diceRoll = rnd.nextInt(100);

        if (diceRoll >= probaHuge) {
            for (int i = 0; i < trapEvents.size; i++) {
                diceRoll = rnd.nextInt(100);
                if (diceRoll < trapEvents.get(i).getNextProba()) {
                    if (!veryHardMode)
                        trapEvents.get(i).activateTrap();
                    else
                        trapEvents.get(i).activateVeryHardTrap();
                }
            }

            return null;
        }


        int maxDice = sumHugeTrapEventWeigths(hugeTrapEvents);

        if (maxDice <= 0 )
            return null;

        diceRoll = rnd.nextInt(maxDice);

        for (int i = 0; i < hugeTrapEvents.size; i++){
            diceRoll -= hugeTrapEvents.get(i).getLastWeigth();
            if (diceRoll < 0)
            {
                hugeTrapEvents.get(i).activateTrap();
                return hugeTrapEvents.get(i);
            }
        }

        return null;
    }
}
