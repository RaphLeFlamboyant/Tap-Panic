package com.flamboyant.tappanic.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.flamboyant.common.helpers.MovableObjectHelper;
import com.flamboyant.tappanic.world.SymbolCapsule;
import com.flamboyant.tappanic.world.SymbolsHandler;
import com.flamboyant.tappanic.world.phases.NormalPhase;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by Reborn Portable on 05/01/2016.
 */
public class SymbolCapsuleHelper extends MovableObjectHelper {
    static public Vector2 findRandomFreeKeyPosition(NormalPhase phase)
    {
        SymbolsHandler sh = phase.getSymbolHandler();
        Random rnd = phase.getRandom();
        ObjectMap<SymbolCapsule, Integer> districts = new ObjectMap<SymbolCapsule, Integer>();
        districts.clear();
        Array<SymbolCapsule> keyboard = phase.getSymbolHandler().getSymbolKeyboard();

        for(Iterator<SymbolCapsule> i = keyboard.iterator(); i.hasNext();)
        {
            SymbolCapsule cap = i.next();
            Vector2 capPos = cap.getPosition();

            int district;
            if (capPos.x < GameContants.KEY_ZONE_X + GameContants.KEY_CELL_SIZE)
                district = 0;
            else if (capPos.x < GameContants.KEY_ZONE_X + GameContants.KEY_CELL_SIZE * 2)
                district = 1;
            else
                district = 2;

            if (capPos.y < GameContants.KEY_ZONE_Y + GameContants.KEY_CELL_SIZE)
                district += 0;
            else if (capPos.y < GameContants.KEY_ZONE_Y + GameContants.KEY_CELL_SIZE * 2)
                district += 3;
            else
                district += 6;

            districts.put(cap, district);
        }

        int chosenDistrict;
        do {
            chosenDistrict = rnd.nextInt(9);
        } while (districts.containsValue(chosenDistrict, false));

        int targetX;
        int targetY;

        if (chosenDistrict % 3 == 0)// TODO gagneen per envirant le tableau position et en utilisant les gameconst
            targetX = GameContants.KEY_ZONE_X;
        else if (chosenDistrict % 3 == 1)
            targetX = GameContants.KEY_START_MARGIN + GameContants.KEY_ZONE_X + GameContants.KEY_CELL_SIZE;
        else // TODO : ajouter un pos 4 pout la taille de la case 3
            targetX = GameContants.KEY_START_MARGIN + GameContants.KEY_ZONE_X + GameContants.KEY_CELL_SIZE * 2;

        if (chosenDistrict / 3 == 0)
            targetY = GameContants.KEY_START_MARGIN + GameContants.KEY_ZONE_Y;
        else if (chosenDistrict / 3 == 1)
            targetY = GameContants.KEY_START_MARGIN + GameContants.KEY_ZONE_Y + GameContants.KEY_CELL_SIZE;
        else // TODO : ajouter un pos 4 pout la taille de la case 3
            targetY = GameContants.KEY_START_MARGIN + GameContants.KEY_ZONE_Y + GameContants.KEY_CELL_SIZE * 2;

        return new Vector2(targetX, targetY);
    }

    static public Array<Vector2> getAllFreePosition(NormalPhase phase)
    {
        Array<Vector2> res = new Array<Vector2>();

        SymbolsHandler sh = phase.getSymbolHandler();
        Random rnd = phase.getRandom();
        ObjectMap<SymbolCapsule, Integer> districts = new ObjectMap<SymbolCapsule, Integer>();
        districts.clear();
        Array<SymbolCapsule> keyboard = phase.getSymbolHandler().getSymbolKeyboard();

        for(Iterator<SymbolCapsule> i = keyboard.iterator(); i.hasNext();)
        {
            SymbolCapsule cap = i.next();
            Vector2 capPos = cap.getPosition();

            int district;
            if (capPos.x < GameContants.KEY_ZONE_X + GameContants.KEY_CELL_SIZE)
                district = 0;
            else if (capPos.x < GameContants.KEY_ZONE_X + GameContants.KEY_CELL_SIZE * 2)
                district = 1;
            else
                district = 2;

            if (capPos.y < GameContants.KEY_ZONE_Y + GameContants.KEY_CELL_SIZE)
                district += 0;
            else if (capPos.y < GameContants.KEY_ZONE_Y + GameContants.KEY_CELL_SIZE * 2)
                district += 3;
            else
                district += 6;

            districts.put(cap, district);
        }

        for (int i = 0; i < 9; i++)
        {
            if (districts.containsValue(i, false))
                continue;

            int targetX;
            int targetY;

            if (i % 3 == 0)// TODO gagneen per envirant le tableau position et en utilisant les gameconst
                targetX = GameContants.KEY_ZONE_X;
            else if (i % 3 == 1)
                targetX = GameContants.KEY_START_MARGIN + GameContants.KEY_ZONE_X + GameContants.KEY_CELL_SIZE;
            else // TODO : ajouter un pos 4 pout la taille de la case 3
                targetX = GameContants.KEY_START_MARGIN + GameContants.KEY_ZONE_X + GameContants.KEY_CELL_SIZE * 2;

            if (i / 3 == 0)
                targetY = GameContants.KEY_START_MARGIN + GameContants.KEY_ZONE_Y;
            else if (i / 3 == 1)
                targetY = GameContants.KEY_START_MARGIN + GameContants.KEY_ZONE_Y + GameContants.KEY_CELL_SIZE;
            else // TODO : ajouter un pos 4 pout la taille de la case 3
                targetY = GameContants.KEY_START_MARGIN + GameContants.KEY_ZONE_Y + GameContants.KEY_CELL_SIZE * 2;

            res.add(new Vector2(targetX, targetY));
        }

        return res;
    }
}
