package com.flamboyant.tappanic.world.phases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.flamboyant.tappanic.world.TapPanicWorld;
import com.flamboyant.tappanic.world.SymbolCapsule;

/**
 * Created by Reborn Portable on 02/12/2015.
 */
public class GameOverPhase extends GameplayPhaseModule {
    private int finalTurnCount;
    private int finalScore;
    private int bossTimeMS;
    private SymbolCapsule defeatSymbol;
    private SymbolCapsule clickedSymbol;
    private boolean bossMode;
    private boolean newTurnRecord;
    private boolean newScoreRecord;

    public GameOverPhase(TapPanicWorld world, int phaseID) {
        super(world, phaseID);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void reset()
    {
        super.reset();

        bossTimeMS = 0;
    }

    @Override
    public void activate(){
        super.activate();

        bossMode = world.isBossMode();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
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

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public int getFinalTurnCount() {
        return finalTurnCount;
    }

    public void setFinalTurnCount(int finalTurnCount) {
        this.finalTurnCount = finalTurnCount;
    }

    public SymbolCapsule getDefeatSymbol() {
        return defeatSymbol;
    }

    public void setDefeatSymbol(SymbolCapsule defeatSymbol) {
        this.defeatSymbol = defeatSymbol;
    }

    public SymbolCapsule getClickedSymbol() {
        return clickedSymbol;
    }

    public void setClickedSymbol(SymbolCapsule clickedSymbol) {
        this.clickedSymbol = clickedSymbol;
    }

    public int getBossTimeMS() {
        return bossTimeMS;
    }

    public void setBossTimeMS(int bossTimeMS) {
        this.bossTimeMS = bossTimeMS;
    }

    public boolean isBossMode() {
        return bossMode;
    }
}
