package com.flamboyant.tappanic.world.phases;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.world.TapPanicWorld;
import com.flamboyant.tappanic.world.SymbolCapsule;

import java.util.Iterator;

/**
 * Created by Reborn Portable on 08/01/2016.
 */
public class IceBlockPhase extends GameplayPhaseModule {
    private ObjectMap<SymbolCapsule, Integer> iceResistances = new ObjectMap<SymbolCapsule, Integer>();
    private boolean isIceBroken = false;

    public IceBlockPhase(TapPanicWorld world, int phaseID) {
        super(world, phaseID);
    }

    public void reset()
    {
        super.reset();

        iceResistances.clear();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void activate()
    {
        Array<SymbolCapsule> keyboard =  world.getNormalPhase().getSymbolHandler().getSymbolKeyboard();

        iceResistances.clear();
        for(Iterator<SymbolCapsule> i = keyboard.iterator(); i.hasNext(); )
        {
            SymbolCapsule item = i.next();

            iceResistances.put(item, GameContants.ICE_RESISTANCE);
        }

        //TODO : créer un "extendTimeToReact"
        world.getNormalPhase().extendCurrentTurnTime(world.getNormalPhase().getTimeToReact());

        super.activate();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (world.getPhase("IceBlock").isActive()) {
            Array<SymbolCapsule> kb = world.getNormalPhase().getSymbolHandler().getSymbolKeyboard();

            for (int i = 0; i < kb.size; i++) {
                SymbolCapsule cap = kb.get(i);
                if (screenX > cap.getPosition().x && screenX < cap.getPosition().x + cap.getSize().x
                        && screenY > cap.getPosition().y && screenY < cap.getPosition().y + cap.getSize().y) {
                    int resistance = iceResistances.get(cap);

                    if (resistance == 0)
                    {
                        this.deactivate();
                        world.getNormalPhase().eventTouchKey(i);
                        isIceBroken = true;
                    }
                    else
                    {
                        iceResistances.put(cap, resistance - 1);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public ObjectMap<SymbolCapsule, Integer> getIceResistances() {
        return iceResistances;
    }

    public boolean isIceBroken()
    {
        return isIceBroken;
    }
}
