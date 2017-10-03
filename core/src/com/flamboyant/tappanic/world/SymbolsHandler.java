package com.flamboyant.tappanic.world;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.flamboyant.tappanic.tools.GameContants;

/**
 * Created by Reborn Portable on 14/10/2015.
 */
public class SymbolsHandler {
    private Array<SymbolCapsule> symbolKeyboard;
    private Array<Symbol> symbolsNotInKeyboard;
    private Array<Symbol> symbolList;
    private Array<Array<Symbol>> families;
    private ObjectMap<Symbol, Array<Symbol>> sonsToFamily;

    public SymbolsHandler() {
        this.symbolKeyboard = new Array<SymbolCapsule>();
        this.symbolsNotInKeyboard = new Array<Symbol>();
        this.symbolList = new Array<Symbol>();
        families = new Array<Array<Symbol>>();
        sonsToFamily = new ObjectMap<Symbol, Array<Symbol>>();
    }

    public void reset()
    {
        symbolKeyboard.clear();
        symbolsNotInKeyboard.clear();
        symbolList.clear();

        families.clear();
        sonsToFamily.clear();
    }

    public int getSymbolsCount()
    {
        return symbolList.size;
    }

    /// Returns the symbol replaced
    public Symbol changeKey(int keyPos, int newSymbolPos)
    {
        Symbol res = symbolKeyboard.get(keyPos).getSymbol();

        symbolKeyboard.get(keyPos).setSymbol(symbolsNotInKeyboard.get(newSymbolPos));
        symbolsNotInKeyboard.set(newSymbolPos, res);

        return res;
    }

    public void swapKey(int keyPos1, int keyPos2)
    {
        Symbol tmp = symbolKeyboard.get(keyPos1).getSymbol();

        symbolKeyboard.get(keyPos1).setSymbol(symbolKeyboard.get(keyPos2).getSymbol());
        symbolKeyboard.get(keyPos2).setSymbol(tmp);
    }

    public SymbolCapsule insertNewKey(int indexNotInKeyboard, int posX, int posY, int width, int heigth)
    {
        SymbolCapsule res = new SymbolCapsule(symbolsNotInKeyboard.get(indexNotInKeyboard), posX, posY, width, heigth);
        symbolKeyboard.add(res);
        symbolsNotInKeyboard.removeIndex(indexNotInKeyboard);

        return res;
    }

    public void removeKey(int indexKeyboard)
    {
        symbolsNotInKeyboard.add(symbolKeyboard.get(indexKeyboard).getSymbol());
        symbolKeyboard.removeIndex(indexKeyboard);
    }

    public Symbol getNotInKeyboard(int index)
    {
        return symbolsNotInKeyboard.get(index);
    }

    public Array<SymbolCapsule> getSymbolKeyboard() {
        return symbolKeyboard;
    }

    public Array<Symbol> getSymbolsNotInKeyboard() {
        return symbolsNotInKeyboard;
    }

    public Array<Symbol> getSymbolList() {
        return symbolList;
    }

    public Array<Symbol> getFamily(Symbol member)
    {
        return sonsToFamily.get(member);
    }

    public Array<Symbol> getFamily(int familyID)
    {
        return sonsToFamily.values().toArray().get(familyID);
    }

    public void initSymbolList(Array<Symbol> symbolList) {
        this.symbolList = new Array<Symbol>(symbolList);
        this.symbolsNotInKeyboard = new Array<Symbol>(symbolList);

        for (int i = 0; i < GameContants.FAMILY_COUNT; i++)
        {
            Array<Symbol> familyList = new Array<Symbol>();

            for (int familyIndex = 0; familyIndex < GameContants.FAMILY_SIZE; familyIndex++)
            {
                familyList.add(symbolList.get(i * GameContants.FAMILY_SIZE + familyIndex));
                sonsToFamily.put(familyList.get(familyIndex), familyList);
            }

            families.add(familyList);
        }
    }
}
