package com.flamboyant.tappanic.world.phases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.SymbolCapsuleHelper;
import com.flamboyant.tappanic.tools.TrapEventHelper;
import com.flamboyant.tappanic.world.TapPanicWorld;
import com.flamboyant.tappanic.world.Symbol;
import com.flamboyant.tappanic.world.SymbolCapsule;
import com.flamboyant.tappanic.world.SymbolsHandler;
import com.flamboyant.tappanic.world.helpers.GameplayHelper;
import com.flamboyant.tappanic.world.traps.RotateTrapEvent;
import com.flamboyant.tappanic.world.traps.SlideKeyTrapEvent;
import com.flamboyant.tappanic.world.traps.SwapTrapEvent;
import com.flamboyant.tappanic.world.traps.TransformKeyTrapEvent;
import com.flamboyant.tappanic.world.traps.TrapEvent;
import com.flamboyant.tappanic.world.traps.WrongKeyTrapEvent;
import com.flamboyant.tappanic.world.traps.hugeTraps.HugeTrapEvent;
import com.flamboyant.tappanic.world.traps.hugeTraps.IceBlockTrapEvent;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by Reborn Portable on 19/10/2015.
 */
public class NormalPhase extends GameplayPhaseModule {
    private SymbolsHandler symbolHandler;
    private Array<Vector2> keyboardPositionBuffer = new Array<Vector2>();
    //private Array<SymbolCapsule> keyboard;
    private Array<SymbolCapsule> shownSymbols;
    //private Symbol shownSymbol;

    private Random rnd;

    private float generalDifficulty;
    private int scoreTurn = GameContants.START_SCORE_TURN;
    private int scorePoints;
    private float bossTimeMS = 0;
    private int lastTurnScore;
    private float timeLap;
    private boolean gameOver = false;
    private boolean veryHardMode = false;
    private boolean threadGuard = false;
    private boolean doingBoss = false;
    private int timeToReactReference;
    private int timeToReact;
    private int targetGirlX;
    private SymbolCapsule destroyedCap;

    private Array<TrapEvent> trapEvents;
    private Array<HugeTrapEvent> hugeTrapEvents;
    private HugeTrapEvent lastHugeTrapEvent;

    //private Vector2 debugPointerPosition = new Vector2();

    public NormalPhase(TapPanicWorld world, int phaseID, int difficulty) {
        super(world, phaseID);
        shownSymbols = new Array<SymbolCapsule>();

        generalDifficulty = difficulty;
        symbolHandler = new SymbolsHandler();
        rnd = TapPanicGame.rnd;
        timeToReactReference = GameContants.TIME_TO_REACT_MS_MAX;
        timeToReact = timeToReactReference + 6000;

        initGame();
    }

    @Override
    public void reset()
    {
        shownSymbols.clear();
        symbolHandler.reset();

        timeToReactReference = GameContants.TIME_TO_REACT_MS_MAX;
        timeToReact = timeToReactReference + 6000;

        scoreTurn = GameContants.START_SCORE_TURN;
        scorePoints = 0;
        lastTurnScore = 0;
        timeLap = 0;
        bossTimeMS = 0;
        gameOver = false;
        threadGuard = false;
        veryHardMode = false;
        doingBoss = false;
        lastHugeTrapEvent = null;
        initSymbols();
        Symbol s = symbolHandler.getSymbolKeyboard().get(rnd.nextInt(symbolHandler.getSymbolKeyboard().size)).getSymbol();
        addNewShownSymbol(s, 0);
        targetGirlX = (int) shownSymbols.get(0).getPosition().x;

        for (int i = 0; i < trapEvents.size; i++)
            trapEvents.get(i).reset();
        for (int i = 0; i < hugeTrapEvents.size; i++)
            hugeTrapEvents.get(i).reset();

        destroyedCap = null;
    }

    private Array<SymbolCapsule> keyboard()
    {
        return symbolHandler.getSymbolKeyboard();
    }

    private void initGame()
    {
        doingBoss = false;
        scoreTurn = GameContants.START_SCORE_TURN;
        scorePoints = 0;
        lastTurnScore = 0;
        timeLap = 0;
        bossTimeMS = 0;
        gameOver = false;
        threadGuard = false;
        lastHugeTrapEvent = null;
        initSymbols();
        Symbol s = symbolHandler.getSymbolKeyboard().get(rnd.nextInt(symbolHandler.getSymbolKeyboard().size)).getSymbol();
        addNewShownSymbol(s, 0);
        targetGirlX = (int) shownSymbols.get(0).getPosition().x;
        initTrapEvents();
    }

    private void initSymbols()
    {
        Array<Symbol> symbolList = new Array<Symbol>();
        for (int i = 1; i <= GameContants.SYMBOL_COUNT; i++)
        {
            symbolList.add(new Symbol("" + i));
        }

        symbolHandler.initSymbolList(symbolList);

        int firstQuarter = ((GameContants.SYMBOL_COUNT / 4) / 10) * 10;
        int secondQuarter = ((GameContants.SYMBOL_COUNT * 2 / 4) / 10) * 10;
        int thirdQuarter = ((GameContants.SYMBOL_COUNT * 3 / 4) / 10) * 10;

        symbolHandler.insertNewKey(rnd.nextInt(firstQuarter), GameContants.KEY_ZONE_X + GameContants.KEY_START_MARGIN,
                GameContants.KEY_ZONE_Y + GameContants.KEY_START_MARGIN, GameContants.KEY_SIZE, GameContants.KEY_SIZE);
        symbolHandler.insertNewKey(rnd.nextInt(secondQuarter - firstQuarter) + firstQuarter - 1, GameContants.KEY_ZONE_X + GameContants.KEY_START_MARGIN + 2 * GameContants.KEY_CELL_SIZE,
                GameContants.KEY_ZONE_Y + GameContants.KEY_START_MARGIN, GameContants.KEY_SIZE, GameContants.KEY_SIZE);
        symbolHandler.insertNewKey(rnd.nextInt(thirdQuarter - secondQuarter) + secondQuarter - 2, GameContants.KEY_ZONE_X + GameContants.KEY_START_MARGIN,
                GameContants.KEY_ZONE_Y + GameContants.KEY_START_MARGIN + 2 * GameContants.KEY_CELL_SIZE, GameContants.KEY_SIZE, GameContants.KEY_SIZE);
        symbolHandler.insertNewKey(rnd.nextInt(GameContants.SYMBOL_COUNT - thirdQuarter) + thirdQuarter - 3, GameContants.KEY_ZONE_X + GameContants.KEY_START_MARGIN + 2 * GameContants.KEY_CELL_SIZE,
                GameContants.KEY_ZONE_Y + GameContants.KEY_START_MARGIN + 2 * GameContants.KEY_CELL_SIZE, GameContants.KEY_SIZE, GameContants.KEY_SIZE);
    }

    private void initTrapEvents()
    {
        trapEvents = new Array<TrapEvent>();
        trapEvents.add(new SlideKeyTrapEvent(generalDifficulty, this));
        trapEvents.add(new RotateTrapEvent(generalDifficulty, this));
        trapEvents.add(new WrongKeyTrapEvent(generalDifficulty, this));
        trapEvents.add(new SwapTrapEvent(generalDifficulty, this));
        trapEvents.add(new TransformKeyTrapEvent(generalDifficulty, this));

        //traps avec gros effet visuel
        hugeTrapEvents = new Array<HugeTrapEvent>();
        hugeTrapEvents.add(new IceBlockTrapEvent(generalDifficulty, this, world));
    }

    public void initializeSubPhaseInWorld()
    {

    }

    private void prepareForBoss()
    {
        keyboardPositionBuffer.clear();

        for (int i = 0; i < symbolHandler.getSymbolKeyboard().size; i++) {
            keyboardPositionBuffer.add(new Vector2(symbolHandler.getSymbolKeyboard().get(i).getPosition()));
            //symbolHandler.getSymbolKeyboard().get(i).setTargetPos(GameContants.SCREEN_WIDTH * 2, 0);
        }

        shownSymbols.clear();

        doingBoss = true;
        destroyedCap = null;

        //BossPhase bossPhase = (BossPhase) world.getPhase("Boss");
        world.activatePhase("Boss");
        this.deactivate();
    }

    public void activate()
    {
        super.activate();
    }

    public void backFromBoss()
    {
        if (world.isBossMode()) {
            launchGameOver(null, null);
            return;
        }

        doingBoss = false;

        SymbolCapsuleHelper.changeTargetAndUpdateSpeed(symbolHandler.getSymbolKeyboard().get(0), GameContants.KEY_ZONE_X + GameContants.KEY_START_MARGIN, GameContants.KEY_ZONE_Y + GameContants.KEY_START_MARGIN);

        int keyToChange = 0;
        int keyToTake = rnd.nextInt(symbolHandler.getSymbolsNotInKeyboard().size);

        symbolHandler.changeKey(keyToChange, keyToTake);

        SymbolCapsuleHelper.changeTargetAndUpdateSpeed(symbolHandler.getSymbolKeyboard().get(1), GameContants.KEY_ZONE_X + GameContants.KEY_START_MARGIN + 2 * GameContants.KEY_CELL_SIZE, GameContants.KEY_ZONE_Y + GameContants.KEY_START_MARGIN);

        keyToChange = 1;
        keyToTake = rnd.nextInt(symbolHandler.getSymbolsNotInKeyboard().size);

        symbolHandler.changeKey(keyToChange, keyToTake);

        SymbolCapsuleHelper.changeTargetAndUpdateSpeed(symbolHandler.getSymbolKeyboard().get(2), GameContants.KEY_ZONE_X + GameContants.KEY_START_MARGIN, GameContants.KEY_ZONE_Y + GameContants.KEY_START_MARGIN + 2 * GameContants.KEY_CELL_SIZE);

        keyToChange = 2;
        keyToTake = rnd.nextInt(symbolHandler.getSymbolsNotInKeyboard().size);

        symbolHandler.changeKey(keyToChange, keyToTake);

        SymbolCapsuleHelper.changeTargetAndUpdateSpeed(symbolHandler.getSymbolKeyboard().get(3), GameContants.KEY_ZONE_X + GameContants.KEY_START_MARGIN + 2 * GameContants.KEY_CELL_SIZE, GameContants.KEY_ZONE_Y + GameContants.KEY_START_MARGIN + 2 * GameContants.KEY_CELL_SIZE);

        keyToChange = 3;
        keyToTake = rnd.nextInt(symbolHandler.getSymbolsNotInKeyboard().size);

        symbolHandler.changeKey(keyToChange, keyToTake);

        int firstQuarter = ((symbolHandler.getSymbolsNotInKeyboard().size / 4) / 10) * 10;
        int secondQuarter = ((symbolHandler.getSymbolsNotInKeyboard().size * 2 / 4) / 10) * 10;
        int thirdQuarter = ((symbolHandler.getSymbolsNotInKeyboard().size * 3 / 4) / 10) * 10;

        symbolHandler.changeKey(0, rnd.nextInt(firstQuarter));
        symbolHandler.changeKey(1, rnd.nextInt(secondQuarter));
        symbolHandler.changeKey(2, rnd.nextInt(thirdQuarter));
        symbolHandler.changeKey(3, rnd.nextInt(symbolHandler.getSymbolsNotInKeyboard().size - thirdQuarter));
        scoreTurn++;
        veryHardMode = true;

        replaceShownSymbol(keyboard().get(rnd.nextInt(symbolHandler.getSymbolKeyboard().size)).getSymbol(), 0);
        setTargetGirlX((int) (shownSymbols.get(0).getPosition().x + shownSymbols.get(0).getSize().x / 2));
        timeToReactReference = GameContants.TIME_TO_REACT_MS_MIN;
        timeToReact = timeToReactReference;
    }

    private void generateNextTurn()
    {
        if (world.getPhase("Boss").isActive())
            return;

        threadGuard = true;
        scoreTurn++;

        if (scoreTurn % GameContants.TURN_BETWEEN_BOSS == 0 && scoreTurn / GameContants.TURN_BETWEEN_BOSS > 0)
        {
            prepareForBoss();

            threadGuard = false;
            return;
        }

        lastTurnScore = shownSymbols.get(0).getPosition().y < GameContants.AWESOME_Y_LIMIT ? 45 : shownSymbols.get(0).getPosition().y < GameContants.YEAH_Y_LIMIT ? 30 : 15;
        scorePoints += lastTurnScore;

        timeToReactReference = timeToReactReference <= GameContants.TIME_TO_REACT_MS_MIN ? timeToReactReference : timeToReactReference - 5;
        timeToReact = timeToReactReference;

        if (scoreTurn <= 6) {
            timeToReact += 1000 * (6 - scoreTurn);
        }

        replaceShownSymbol(keyboard().get(rnd.nextInt(symbolHandler.getSymbolKeyboard().size)).getSymbol(), 0);
        setTargetGirlX((int) (shownSymbols.get(0).getPosition().x + shownSymbols.get(0).getSize().x / 2));
        //Gdx.app.log("Normal Phase", "Symbol show pos x = " + shownSymbols.get(0).getPosition().x + "symbol size = " + shownSymbols.get(0).getSize().x);
        lastHugeTrapEvent = TrapEventHelper.choseTrapsAndLaunch(trapEvents, hugeTrapEvents, rnd, (int) (5 * (scoreTurn < 150 ? (float) scoreTurn / 150f : 2)), veryHardMode);

        timeLap = 0;
        threadGuard = false;
    }

    public void update(float delta)
    {
        if (threadGuard || gameOver || isPaused)
            return;

        timeLap += delta;

        //Update des deux listes //Position, scale & angle
        int iterate = 0;
        for (Array<SymbolCapsule> capList = shownSymbols;  2 != iterate++; capList = keyboard()) {
            for (Iterator<SymbolCapsule> i = capList.iterator(); i.hasNext(); ) {
                SymbolCapsule cap = i.next();
                Vector2 pos = cap.getPosition();

                float deltaX = cap.getSpeed().x * delta;
                float deltaY = cap.getSpeed().y * delta;

                //TODO : régler le bug de la touche qui se fait la malle (elle existe encore)
                if (cap.getTargetPosition().x != -1 && SymbolCapsuleHelper.reachesTarget(cap, deltaX, deltaY)) {
                    cap.setPosition(cap.getTargetPosition().x, cap.getTargetPosition().y);
                    cap.setTargetPos(-1, -1);
                    cap.setSpeed(0, 0);
                } else {
                    cap.setPosition(pos.x + deltaX, pos.y + deltaY);
                }

                Vector2 size = cap.getSize();

                float deltasX = cap.getSizeSpeed().x * delta;
                float deltasY = cap.getSizeSpeed().y * delta;

                if (cap.getTargetSize().x != -1 && SymbolCapsuleHelper.reachesTargetSize(cap, deltasX, deltasY)) {
                    cap.setSize(cap.getTargetSize().x, cap.getTargetSize().y);
                    cap.setTargetSize(-1, -1);
                    cap.setSizeSpeed(0, 0);
                } else {
                    cap.setSize(size.x + deltasX,size.y + deltasY);
                }

                cap.setRotation(cap.getRotation() + cap.getRotationSpeed() * delta);
            }
        }

        if (GameplayHelper.timedOut(shownSymbols))
        {
            if (!hasFailed(null))
            {
                destroyedCap = null;

                generateNextTurn();
            }
            else
            {
                launchGameOver(null);
            }
        }
    }

    public void launchGameOver(SymbolCapsule clickedSymbol)
    {
        launchGameOver(clickedSymbol, shownSymbols.get(0));
    }

    public void launchGameOver(SymbolCapsule clickedSymbol, SymbolCapsule shownSymbol)
    {
        gameOver = true;
        GameOverPhase goPhase = (GameOverPhase) world.getPhase("GameOver");
        goPhase.setFinalTurnCount(scoreTurn);
        goPhase.setFinalScore(scorePoints);
        goPhase.setDefeatSymbol(shownSymbol);
        goPhase.setClickedSymbol(clickedSymbol);
        goPhase.setBossTimeMS((int) bossTimeMS);
        world.activatePhase("GameOver");
        this.deactivate();
    }

    public void eventTouchKey(int keyIndex)
    {
        if (threadGuard)
            return;

        if (!hasFailed(keyboard().get(keyIndex).getSymbol()))
        {
            destroyedCap = shownSymbols.get(0);

            generateNextTurn();
        }
        else
        {
            launchGameOver(keyboard().get(keyIndex));
        }
    }

    /**
     *
     * @param touchedSymbol null if it's timeout
     * @return
     */
    private boolean hasFailed(Symbol touchedSymbol)
    {//TODO : corriger le bug, à première vue le hasFailed est appelé après que le tour soit appliqué (?)

        if (shownSymbols.size == 0)
            return false;

        if (touchedSymbol != null) {
            for (Iterator<SymbolCapsule> i = shownSymbols.iterator(); i.hasNext(); ) {
                SymbolCapsule item = i.next();
                if (item.getSymbol().equals(touchedSymbol))
                    return false;
            }

            return true;
        }

        for (Iterator<SymbolCapsule> i = shownSymbols.iterator(); i.hasNext(); ) {
            SymbolCapsule item = i.next();
            for (Iterator<SymbolCapsule> j = keyboard().iterator(); j.hasNext(); ) {
                if (item.getSymbol().equals(j.next().getSymbol())) {
                    return true;
                }
            }
        }

        return false;
    }

    public void addNewShownSymbol(Symbol s, int rotationSpeed)
    {
        int size = rnd.nextInt(GameContants.KEY_SIZE - GameContants.SYMBOL_MIN_SIZE) + GameContants.SYMBOL_MIN_SIZE;//GameContants.KEY_SIZE;
        int posX = rnd.nextInt(GameContants.SCREEN_WIDTH - GameContants.SYMBOL_MARGIN * 2 - size) + GameContants.SYMBOL_MARGIN;
        int posY = -size;

        SymbolCapsule sc = new SymbolCapsule(s, posX, posY, size, size);
        sc.setRotation(rnd.nextInt(360));
        if (rotationSpeed != 0)
        {
            sc.setRotationSpeed(rotationSpeed);
        }

        //Pour l'instant la rotation sera un TrapEvent
        //La speed est set ici pour le moment
        // Idem pour le temps de chutte, ici on le fixe mais il faudra le rendre aléatoire
        // Il faut un schéma pour la chute et pas se contenter d'une speed (ex : feuille morte)
        //TODO : améliorer ça

        int speed = (int) ((GameContants.GIRL_Y_LIMIT) / (timeToReact / 1000f));
        sc.setSpeed(0, speed);
        sc.setTargetPos(posX, GameContants.GIRL_Y_LIMIT - size);

        shownSymbols.add(sc);
    }

    public void extendCurrentTurnTime(int timeMS)
    {
        timeToReact += timeMS;

        for (int i = 0; i < shownSymbols.size; i++)
        {
            SymbolCapsule cap = shownSymbols.get(i);

            cap.setSpeed(0, (GameContants.GIRL_Y_LIMIT - (cap.getPosition().y + cap.getSize().y)) / ((timeToReact - timeLap) / 1000f));
        }
    }

    public void replaceShownSymbol(Symbol nxt, int rotationSpeed)
    {
        shownSymbols.clear();
        addNewShownSymbol(nxt, rotationSpeed);
    }

    public void deleteShowSymbol(Symbol toDel)
    {
        for (int i = 0; i < shownSymbols.size; i++)
        {
            if (shownSymbols.get(i).getSymbol().getName().equals(toDel.getName()))
            {
                shownSymbols.removeIndex(i);
                return;
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        if (world.getPhase("IceBlock").isActive())
            return false;

        //debugPointerPosition.x = screenX;
        //debugPointerPosition.y = screenY;

        Array<SymbolCapsule> kb = symbolHandler.getSymbolKeyboard();

        for (int i = 0; i < kb.size; i++) {
            SymbolCapsule cap = kb.get(i);
            if (screenX > cap.getPosition().x && screenX < cap.getPosition().x + cap.getSize().x
                    && screenY > cap.getPosition().y && screenY < cap.getPosition().y + cap.getSize().y) {
                eventTouchKey(i);
                return true;
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

    public Random getRandom() {
        return rnd;
    }

    public SymbolsHandler getSymbolHandler() {
        return symbolHandler;
    }

    public Array<SymbolCapsule> getShownSymbols() {
        return shownSymbols;
    }

    public int getScoreTurn() {
        return scoreTurn;
    }

    public void setScoreTurn(int score) {
        this.scoreTurn = score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public float getTimeLap() {
        return timeLap;
    }

    public int getTimeToReact() {
        return timeToReact;
    }

    public void setTimeToReact(int timeToReact) {
        this.timeToReact = timeToReact;
    }

    public int getLastTurnScore() {
        return lastTurnScore;
    }

    public int getScorePoints() {
        return scorePoints;
    }

    //public Vector2 getDebugPointerPosition() {  return debugPointerPosition;  }


    public boolean isVeryHardMode() {
        return veryHardMode;
    }

    public int getTargetGirlX() {
        return targetGirlX;
    }

    public void setTargetGirlX(int targetGirlX) {
        this.targetGirlX = targetGirlX;
    }

    public SymbolCapsule getDestroyedCap() {
        return destroyedCap;
    }

    public Array<Vector2> getKeyboardPositionBuffer() {
        return keyboardPositionBuffer;
    }

    public boolean isDoingBoss() {
        return doingBoss;
    }

    public float getBossTimeMS() {
        return bossTimeMS;
    }

    public void setBossTimeMS(float bossTimeMS) {
        this.bossTimeMS = bossTimeMS;
    }
}
