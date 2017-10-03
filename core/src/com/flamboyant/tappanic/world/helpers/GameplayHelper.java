package com.flamboyant.tappanic.world.helpers;

import com.badlogic.gdx.utils.Array;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.PathGenerator;
import com.flamboyant.tappanic.world.SmartSymbols.SmartSymbolCapsule;
import com.flamboyant.tappanic.world.Symbol;
import com.flamboyant.tappanic.world.SymbolCapsule;
import com.flamboyant.tappanic.world.SymbolsHandler;

import java.util.Random;

/**
 * Created by Reborn Portable on 11/01/2016.
 */
public class GameplayHelper {
    public static boolean timedOut(Array<SymbolCapsule> shownSymbols)
    {
        for (int i = 0; i < shownSymbols.size; i++)
        {
            if (shownSymbols.get(i).getPosition().y + shownSymbols.get(i).getSize().y >= GameContants.GIRL_Y_LIMIT)
                return true;
        }

        return false;
    }

    public static SmartSymbolCapsule reachedSymbolGirl(Array<SmartSymbolCapsule> shownSymbols)
    {
        for (int i = 0; i < shownSymbols.size; i++)
        {
            if (shownSymbols.get(i).getPosition().y + shownSymbols.get(i).getSize().y >= GameContants.GIRL_Y_LIMIT)
                return shownSymbols.get(i);
        }

        return null;
    }

    public static void launchFourFlowers(Array<SmartSymbolCapsule> keyboard, Array<SmartSymbolCapsule> shownSymbols, SymbolsHandler sh, Random rnd)
    {
        Array<Symbol> chosens = new Array<Symbol>();

        for (int i = 0; i < 4; i++) {
            int dice = rnd.nextInt(100);
            if (dice > 85)
                chosens.add(keyboard.get(rnd.nextInt(keyboard.size)).getSymbol());
            else if (dice > 65) {
                Array<Symbol> family = sh.getFamily(keyboard.get(0).getSymbol());
                chosens.add(family.get(rnd.nextInt(family.size)));
            }
            else {
                chosens.add(sh.getSymbolList().get(rnd.nextInt(sh.getSymbolList().size)));
            }
        }

        SmartSymbolCapsule newCap1 = new SmartSymbolCapsule(chosens.get(0), GameContants.SCREEN_WIDTH / 2 - (GameContants.SYMBOL_MIN_SIZE / 2), 0, GameContants.SYMBOL_MIN_SIZE, GameContants.SYMBOL_MIN_SIZE, 0.99f, false);
        SmartSymbolCapsule newCap2 = new SmartSymbolCapsule(chosens.get(1), GameContants.SCREEN_WIDTH / 2 - (GameContants.SYMBOL_MIN_SIZE / 2), 0, GameContants.SYMBOL_MIN_SIZE, GameContants.SYMBOL_MIN_SIZE, 0.99f, false);
        SmartSymbolCapsule newCap3 = new SmartSymbolCapsule(chosens.get(2), GameContants.SCREEN_WIDTH / 2 - (GameContants.SYMBOL_MIN_SIZE / 2), 0, GameContants.SYMBOL_MIN_SIZE, GameContants.SYMBOL_MIN_SIZE, 0.99f, false);
        SmartSymbolCapsule newCap4 = new SmartSymbolCapsule(chosens.get(3), GameContants.SCREEN_WIDTH / 2 - (GameContants.SYMBOL_MIN_SIZE / 2), 0, GameContants.SYMBOL_MIN_SIZE, GameContants.SYMBOL_MIN_SIZE, 0.99f, false);

        PathGenerator.generateFourFlowersPath(newCap1.getPath(), newCap2.getPath(), newCap3.getPath(), newCap4.getPath(), newCap1.getSize().x, rnd);

        shownSymbols.add(newCap1);
        shownSymbols.add(newCap2);
        shownSymbols.add(newCap3);
        shownSymbols.add(newCap4);
    }

    private static boolean keyboardContains(Array<SmartSymbolCapsule> keyboard, Symbol symbol)
    {
        for (int i = 0; i < keyboard.size; i++){
            if (keyboard.get(i).getSymbol() == symbol)
                return true;
        }

        return false;
    }

    public static void launchThreeMoking(Array<SmartSymbolCapsule> keyboard, Array<SmartSymbolCapsule> shownSymbols, SymbolsHandler sh, Random rnd)
    {
        Array<Symbol> chosens = new Array<Symbol>();
        Array<Integer> indexes = new Array<Integer>();

        for (int i = 0; i < 3; i++)
        {
            indexes.add(i);
        }

        for (int i = 0; i < 3; i++) {
            int rndIndex = rnd.nextInt(indexes.size);

            if (indexes.get(rndIndex) == 0)
                chosens.add(keyboard.get(rnd.nextInt(keyboard.size)).getSymbol());
            else if (indexes.get(rndIndex) == 1) {
                Array<Symbol> family = sh.getFamily(keyboard.get(0).getSymbol());
                int roll = rnd.nextInt(family.size);
                Symbol chosen;
                do {
                    chosen = family.get(roll);
                    roll = (roll + 1) % family.size;
                } while (keyboardContains(keyboard, chosen));
                chosens.add(chosen);
            }
            else {
                int roll = rnd.nextInt(sh.getSymbolList().size);
                Symbol chosen;
                do {
                    chosen = sh.getSymbolList().get(roll);
                    roll = (roll + 1) % sh.getSymbolList().size;
                } while (keyboardContains(keyboard, chosen));
                chosens.add(chosen);
            }

            indexes.removeIndex(rndIndex);
        }

        SmartSymbolCapsule newCap1 = new SmartSymbolCapsule(chosens.get(0), GameContants.SCREEN_WIDTH / 2 - (GameContants.SYMBOL_MIN_SIZE / 2), 0, GameContants.SYMBOL_MIN_SIZE, GameContants.SYMBOL_MIN_SIZE, 0f, false);
        SmartSymbolCapsule newCap2 = new SmartSymbolCapsule(chosens.get(1), GameContants.SCREEN_WIDTH / 2 - (GameContants.SYMBOL_MIN_SIZE / 2), 0, GameContants.SYMBOL_MIN_SIZE, GameContants.SYMBOL_MIN_SIZE, 0f, false);
        SmartSymbolCapsule newCap3 = new SmartSymbolCapsule(chosens.get(2), GameContants.SCREEN_WIDTH / 2 - (GameContants.SYMBOL_MIN_SIZE / 2), 0, GameContants.SYMBOL_MIN_SIZE, GameContants.SYMBOL_MIN_SIZE, 0f, false);

        PathGenerator.generateThreeLinearMokingPath(newCap1.getPath(), newCap2.getPath(), newCap3.getPath(), newCap1.getSize().x, rnd);

        shownSymbols.add(newCap1);
        shownSymbols.add(newCap2);
        shownSymbols.add(newCap3);
    }

    public static void launchTwoStars(Array<SmartSymbolCapsule> keyboard, Array<SmartSymbolCapsule> shownSymbols, SymbolsHandler sh, Random rnd)
    {
        Array<Symbol> chosens = new Array<Symbol>();

        for (int i = 0; i < 2; i++) {
            int dice = rnd.nextInt(100);
            if (dice > 50)
                chosens.add(keyboard.get(rnd.nextInt(keyboard.size)).getSymbol());
            else {
                Array<Symbol> family = sh.getFamily(keyboard.get(0).getSymbol());
                chosens.add(family.get(rnd.nextInt(family.size)));
            }
        }

        SmartSymbolCapsule newCap1 = new SmartSymbolCapsule(chosens.get(0), GameContants.SCREEN_WIDTH / 2 - (GameContants.SYMBOL_MIN_SIZE / 2), 0, GameContants.SYMBOL_MIN_SIZE, GameContants.SYMBOL_MIN_SIZE, 0.9f, false);
        SmartSymbolCapsule newCap2 = new SmartSymbolCapsule(chosens.get(1), GameContants.SCREEN_WIDTH / 2 - (GameContants.SYMBOL_MIN_SIZE / 2), 0, GameContants.SYMBOL_MIN_SIZE, GameContants.SYMBOL_MIN_SIZE, 0.9f, false);

        PathGenerator.generateTwoStarsPath(newCap1.getPath(), newCap2.getPath(), newCap1.getSize().x, rnd);

        shownSymbols.add(newCap1);
        shownSymbols.add(newCap2);
    }

    public static void launchNormalFall(Array<SmartSymbolCapsule> keyboard, Array<SmartSymbolCapsule> shownSymbols, SymbolsHandler sh, Random rnd)
    {
        Symbol chosen;

        if (rnd.nextInt(100) > 50)
            chosen = keyboard.get(rnd.nextInt(keyboard.size)).getSymbol();
        else
        {
            Array<Symbol> family = sh.getFamily(keyboard.get(0).getSymbol());
            chosen = family.get(rnd.nextInt(family.size));
        }

        SmartSymbolCapsule newCap = new SmartSymbolCapsule(chosen, GameContants.SCREEN_WIDTH / 2 - (GameContants.SYMBOL_MIN_SIZE / 2), 0, GameContants.SYMBOL_MIN_SIZE, GameContants.SYMBOL_MIN_SIZE, 0.00f, false);
        PathGenerator.generateLinearPath(newCap.getPath(), newCap.getPosition().x, newCap.getPosition().y, newCap.getPosition().x, GameContants.SCREEN_HEIGTH, newCap.getSize().x, 2.9f, rnd);

        shownSymbols.add(newCap);
    }

    public static void launchDoubleFall(Array<SmartSymbolCapsule> keyboard, Array<SmartSymbolCapsule> shownSymbols, SymbolsHandler sh, Random rnd)
    {
        Symbol chosen1;
        Symbol chosen2;
        boolean diceRoll = rnd.nextBoolean();
        Array<Symbol> family = sh.getFamily(keyboard.get(0).getSymbol());

        Symbol chosen;
        int roll = rnd.nextInt(family.size);
        do {
            chosen = family.get(roll);
            roll = (roll + 1) % family.size;
        } while (keyboardContains(keyboard, chosen));

        chosen1 = diceRoll ? keyboard.get(rnd.nextInt(keyboard.size)).getSymbol() : chosen;
        chosen2 = diceRoll ? chosen : keyboard.get(rnd.nextInt(keyboard.size)).getSymbol();

        SmartSymbolCapsule newCap = new SmartSymbolCapsule(chosen1, GameContants.SCREEN_WIDTH / 2 - (GameContants.SYMBOL_MIN_SIZE / 2), 0, GameContants.SYMBOL_MIN_SIZE, GameContants.SYMBOL_MIN_SIZE, 0.00f, false);
        PathGenerator.generateLinearPath(newCap.getPath(), newCap.getPosition().x, newCap.getPosition().y, newCap.getPosition().x + GameContants.SYMBOL_MIN_SIZE, GameContants.SCREEN_HEIGTH, newCap.getSize().x, 2.9f, rnd);

        shownSymbols.add(newCap);

        newCap = new SmartSymbolCapsule(chosen2, GameContants.SCREEN_WIDTH / 2 - (GameContants.SYMBOL_MIN_SIZE / 2), 0, GameContants.SYMBOL_MIN_SIZE, GameContants.SYMBOL_MIN_SIZE, 0.00f, false);
        PathGenerator.generateLinearPath(newCap.getPath(), newCap.getPosition().x, newCap.getPosition().y, newCap.getPosition().x - GameContants.SYMBOL_MIN_SIZE, GameContants.SCREEN_HEIGTH, newCap.getSize().x, 2.9f, rnd);

        shownSymbols.add(newCap);
    }

    public static void launchLeafFall(Array<SmartSymbolCapsule> keyboard, Array<SmartSymbolCapsule> shownSymbols, SymbolsHandler sh, Random rnd) {

        Symbol chosen;

        if (rnd.nextInt(100) > 50)
            chosen = keyboard.get(rnd.nextInt(keyboard.size)).getSymbol();
        else
        {
            Array<Symbol> family = sh.getFamily(keyboard.get(0).getSymbol());
            chosen = family.get(rnd.nextInt(family.size));
        }

        SmartSymbolCapsule newCap = new SmartSymbolCapsule(chosen, GameContants.SCREEN_WIDTH / 2 - (GameContants.SYMBOL_MIN_SIZE / 2), 0, GameContants.SYMBOL_MIN_SIZE, GameContants.SYMBOL_MIN_SIZE, 0.95f, false);
        PathGenerator.generateSinusoidalPath(newCap.getPath(), newCap.getSize().x, rnd);

        shownSymbols.add(newCap);
    }
}
