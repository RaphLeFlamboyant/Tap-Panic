package com.flamboyant.common;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Reborn Portable on 08/01/2016.
 */
public class Activator implements IActivable {
    private Array<IActivable> sons = new Array<IActivable>();

    public void addNewSon(IActivable son)
    {
        sons.add(son);
    }

    public boolean deleteSon(IActivable son)
    {
        int index = sons.indexOf(son, true);

        if (index == -1)
            return false;

        sons.removeIndex(index);

        return true;
    }

    @Override
    public void activate() {
        for (int i = 0; i < sons.size; i++)
            sons.get(i).activate();
    }

    @Override
    public void deactivate() {
        for (int i = 0; i < sons.size; i++)
            sons.get(i).deactivate();
    }
}
