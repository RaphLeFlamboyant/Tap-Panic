package com.flamboyant.tappanic.world;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.TimeUtils;
import com.flamboyant.tappanic.world.phases.BossPhase;
import com.flamboyant.tappanic.world.phases.GameOverPhase;
import com.flamboyant.tappanic.world.phases.GameStarterPhase;
import com.flamboyant.tappanic.world.phases.GameplayPhaseModule;
import com.flamboyant.tappanic.world.phases.IceBlockPhase;
import com.flamboyant.tappanic.world.phases.NormalPhase;

import java.util.Iterator;

/**
 * Created by Reborn Portable on 14/10/2015.
 */
public class TapPanicWorld {
    private float lastTimerTick;
    private ObjectMap<String, GameplayPhaseModule> phases;
    private NormalPhase normalPhase;
    private GameStarterPhase starterPhase;
    private GameOverPhase overPhase;
    private BossPhase bossPhase;
    private boolean bossMode;

    public TapPanicWorld(boolean showHowToPlay)
    {
        lastTimerTick = TimeUtils.millis();
        phases = new ObjectMap<String, GameplayPhaseModule>();
        starterPhase = new GameStarterPhase(this, 0, showHowToPlay);
        phases.put("Starter", starterPhase);
        IceBlockPhase iceBlock = new IceBlockPhase(this, 3);
        phases.put("IceBlock", iceBlock);
        normalPhase = new NormalPhase(this, 1, 1);
        phases.put("Normal", normalPhase);
        overPhase = new GameOverPhase(this, 2);
        phases.put("GameOver", overPhase);
        bossPhase = new BossPhase(this, 4);
        phases.put("Boss", bossPhase);

        phases.get("Starter").activate();
    }

    public void reset()
    {
        synchronized (phases.values()) {
            for (Iterator<GameplayPhaseModule> i = phases.values(); i.hasNext(); ) {
                GameplayPhaseModule item = i.next();
                item.reset();
            }
        }

        phases.get("Starter").activate();
    }

    public void touchedKey(int keyNumber)
    {
        if (normalPhase.isActive())
            normalPhase.eventTouchKey(keyNumber);
    }

    // Chaque sous objet gameplay gère une phase du jeu : stater, fin, déroulement normal, décalage écran, etc.
    // Chaque sous objet gameplay a un sous objet render correspondant dans le world renderer qui affiche les éléments qui correspondent.
    // Chaque sous objet gameplay a un sous objet controller correspondant dans le world controller qui gère l'intéraction utilisateur
    public void update(float delta)
    {
        lastTimerTick = TimeUtils.millis();

        synchronized (phases.values()) {
            for (Iterator<GameplayPhaseModule> i = phases.values(); i.hasNext(); ) {
                GameplayPhaseModule item = i.next();
                if (item.isActive())
                    item.update(delta);
            }
        }
    }

    public void gamePause()
    {
        //TODO : spécifier la pause si les phases se compléxifient
        synchronized (phases.values()) {
            for (Iterator<GameplayPhaseModule> i = phases.values(); i.hasNext(); ) {
                i.next().pause();
            }
        }
    }

    public void unpauseAll()
    {
        synchronized (phases.values()) {
            for (Iterator<GameplayPhaseModule> i = phases.values(); i.hasNext(); ) {
                i.next().unpause();
            }
        }
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        boolean res = false;

        for(ObjectMap.Entries<String, GameplayPhaseModule> i = phases.iterator(); i.hasNext(); )
        {
            ObjectMap.Entry<String, GameplayPhaseModule> item =  i.next();

            if (item.value.isActive() && !item.value.isPaused())
                res = res || item.value.touchDown(screenX, screenY, pointer, button);
        }

        return res;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean res = false;

        for(ObjectMap.Entries<String, GameplayPhaseModule> i = phases.iterator(); i.hasNext(); )
        {
            ObjectMap.Entry<String, GameplayPhaseModule> item =  i.next();

            if (item.value.isActive() && !item.value.isPaused())
                res = res || item.value.touchUp(screenX, screenY, pointer, button);
        }

        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean res = false;

        for(ObjectMap.Entries<String, GameplayPhaseModule> i = phases.iterator(); i.hasNext(); )
        {
            ObjectMap.Entry<String, GameplayPhaseModule> item =  i.next();

            if (item.value.isActive() && !item.value.isPaused())
                res = res || item.value.touchDragged(screenX, screenY, pointer);
        }

        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        boolean res = false;

        for(ObjectMap.Entries<String, GameplayPhaseModule> i = phases.iterator(); i.hasNext(); )
        {
            ObjectMap.Entry<String, GameplayPhaseModule> item =  i.next();

            if (item.value.isActive() && !item.value.isPaused())
                res = res || item.value.mouseMoved(screenX, screenY);
        }

        return false;
    }


    public void activatePhase(String key)
    {
        phases.get(key).activate();
    }

    public GameplayPhaseModule getPhase(String phaseName)
    {
        return phases.get(phaseName);
    }

    public NormalPhase getNormalPhase () { return normalPhase; }

    public BossPhase getBossPhase () { return bossPhase; }

    public boolean isBossMode() {
        return bossMode;
    }

    public void setBossMode(boolean bossMode) {
        this.bossMode = bossMode;
    }
}
