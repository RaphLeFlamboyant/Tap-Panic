package com.flamboyant.tappanic.world.phases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.flamboyant.common.BreathingTextureObject;
import com.flamboyant.common.MovableObject;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.PathGenerator;
import com.flamboyant.tappanic.world.SmartSymbols.SmartSymbolCapsule;
import com.flamboyant.tappanic.world.Symbol;
import com.flamboyant.tappanic.world.SymbolCapsule;
import com.flamboyant.tappanic.world.SymbolsHandler;
import com.flamboyant.tappanic.world.TapPanicWorld;
import com.flamboyant.tappanic.world.helpers.GameplayHelper;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by Reborn Portable on 15/02/2016.
 */
public class BossPhase extends GameplayPhaseModule {
    private NormalPhase znph;

    private Array<SmartSymbolCapsule> keyboard = new Array<SmartSymbolCapsule>();
    private Array<SmartSymbolCapsule> shownSymbols = new Array<SmartSymbolCapsule>();

    private Array<BreathingTextureObject> leftTentacles;
    private Array<BreathingTextureObject> rightTentacles;
    private Array<Boolean> leftOnScreen = new Array<Boolean>();
    private Array<Boolean> rightOnScreen = new Array<Boolean>();
    private int bossHP = GameContants.BOSS_MAX_HP;

    private float timeLap = 0;
    private float bossTimeSeconds = 0;

    private int scoreTurn;
    private int scorePoints;

    int diceTotal = 0;
    boolean keyboardWillChange = false;
    float timeLapChanging = 0;

    private boolean gameOver = false;
    private boolean animating = false;


    private Array<SymbolCapsule> destroyedCapsules = new Array<SymbolCapsule>();
    private Array<SymbolCapsule> autodestroyedCapsules = new Array<SymbolCapsule>();

    public BossPhase(TapPanicWorld world, int phaseID)
    {
        super(world, phaseID);
    }

    private NormalPhase normalPhase()
    {
        if (znph == null)
            znph = (NormalPhase) world.getPhase("Normal");

        return znph;
    }

    public void update(float delta)
    {
        if (gameOver || isPaused || animating)
            return;

        //Update des deux listes //Position, scale & angle
        int iterate = 0;
        for (Array<SmartSymbolCapsule> capList = shownSymbols;  2 != iterate++; capList = keyboard) {
            for (Iterator<SmartSymbolCapsule> i = capList.iterator(); i.hasNext(); ) {
                SmartSymbolCapsule cap = i.next();

                cap.update(delta);
            }
        }

        SmartSymbolCapsule cap = GameplayHelper.reachedSymbolGirl(shownSymbols);
        if (cap != null)
        {
            SymbolCapsule fail = hasFailedOn();
            if (fail == null)
            {
                shownSymbols.removeIndex(shownSymbols.indexOf(cap, true));
            }
            else
            {
                normalPhase().launchGameOver(null, fail);
                this.deactivate();
            }
        }

        bossBehavior(delta);

        timeLap += delta;
        bossTimeSeconds += delta;
    }

    @Override
    public void activate(){
        super.activate();

        animating = true;

        shownSymbols.clear();
        keyboard.clear();
        destroyedCapsules.clear();
        autodestroyedCapsules.clear();

        bossHP = GameContants.BOSS_MAX_HP;
        timeLap = 0;
        bossTimeSeconds = 0;
        diceTotal = 0;
        keyboardWillChange = false;
        timeLapChanging = 0;
        gameOver = false;

        scorePoints = normalPhase().getScorePoints();
        scoreTurn = normalPhase().getScoreTurn();

        Array<SymbolCapsule> normalKeyboard = normalPhase().getSymbolHandler().getSymbolKeyboard();
        float y = GameContants.KEY_ZONE_Y + 3f * GameContants.KEY_CELL_SIZE - 2.1f * GameContants.KEY_SIZE;
        for (int i = 0; i < normalKeyboard.size; i++)
        {
            SmartSymbolCapsule cap = new SmartSymbolCapsule(normalKeyboard.get(i), 0, false);

            float x = i % 2 == 0 ?  GameContants.KEY_SIZE / 8 : GameContants.SCREEN_WIDTH - GameContants.KEY_SIZE * 9 / 8;

            PathGenerator.generateLinearPath(cap.getPath(), cap.getPosition().x, cap.getPosition().y, x, y, cap.getSize().x, 0.4f, normalPhase().getRandom());

            keyboard.add(cap);
            y = i % 2 == 1 ? y + GameContants.KEY_SIZE * 1.1f : y;
        }

        rightOnScreen.clear();
        leftOnScreen.clear();
        for (int i = 0; i < leftTentacles.size; i++)
        {
            BreathingTextureObject tentacle = leftTentacles.get(i);

            tentacle.setPosition(GameContants.LEFT_TENTACLE_INIT, GameContants.TENTACLE_Y_BASE + GameContants.SCREEN_HEIGTH / 4f * i);
            //tentacle.setPosition(GameContants.LEFT_TENTACLE_OFF_POS_X, GameContants.TENTACLE_Y_BASE + GameContants.SCREEN_HEIGTH / 4f * i);
            rightOnScreen.add(false);

            tentacle = rightTentacles.get(i);
            tentacle.setPosition(GameContants.RIGHT_TENTACLE_INIT, GameContants.TENTACLE_Y_BASE + GameContants.SCREEN_HEIGTH / 8f * (2f * i + 1f));
            //tentacle.setPosition(GameContants.RIGHT_TENTACLE_OFF_POS_X, GameContants.TENTACLE_Y_BASE + GameContants.SCREEN_HEIGTH / 8f * (2f * i + 1f));
            leftOnScreen.add(false);
        }

        int famillyID = TapPanicGame.rnd.nextInt(6);
        Array<Symbol> family = normalPhase().getSymbolHandler().getFamily(famillyID);
        int lastChoice = -1;
        int maxChoice = 2;
        for (int i = 0; i < keyboard.size; i++)
        {
            int choice = TapPanicGame.rnd.nextInt(maxChoice - lastChoice) + 1  + lastChoice;
            keyboard.get(i).setSymbol(family.get(choice));
            maxChoice = maxChoice + 3 > 9 ? 9 : maxChoice + 3;
            lastChoice = choice;
        }
    }

    private void behaviorTentacles(float delta)
    {
        for (int i = 0; i < leftTentacles.size; i++)
        {
            BreathingTextureObject left = leftTentacles.get(i);
            if (leftOnScreen.get(i) && left.getPosition().x != GameContants.LEFT_TENTACLE_ON_POS_X) {
                float leftPlusDelta = left.getPosition().x + (GameContants.LEFT_TENTACLE_ON_POS_X - GameContants.LEFT_TENTACLE_OFF_POS_X) * delta;
                left.setPosition(leftPlusDelta > GameContants.LEFT_TENTACLE_ON_POS_X ? GameContants.LEFT_TENTACLE_ON_POS_X : leftPlusDelta, left.getPosition().y);
            }
            else if (!leftOnScreen.get(i) && left.getPosition().x != GameContants.LEFT_TENTACLE_OFF_POS_X) {
                float leftPlusDelta = left.getPosition().x - (GameContants.LEFT_TENTACLE_ON_POS_X - GameContants.LEFT_TENTACLE_OFF_POS_X) * delta;
                left.setPosition(leftPlusDelta < GameContants.LEFT_TENTACLE_OFF_POS_X ? GameContants.LEFT_TENTACLE_OFF_POS_X : leftPlusDelta, left.getPosition().y);
            }
            else
            {
                if (bossHP <= GameContants.BOSS_MAX_HP / 2)
                    leftOnScreen.set(i, false);
                else if ((timeLap - delta) % 1f > timeLap % 1f && TapPanicGame.rnd.nextInt(1000) < 100)
                    leftOnScreen.set(i, !leftOnScreen.get(i));
            }

            BreathingTextureObject right = rightTentacles.get(i);
            if (rightOnScreen.get(i) && right.getPosition().x != GameContants.RIGHT_TENTACLE_ON_POS_X) {
                float rightPlusDelta = right.getPosition().x + (GameContants.RIGHT_TENTACLE_ON_POS_X - GameContants.RIGHT_TENTACLE_OFF_POS_X) * delta;
                right.setPosition(rightPlusDelta < GameContants.RIGHT_TENTACLE_ON_POS_X ? GameContants.RIGHT_TENTACLE_ON_POS_X : rightPlusDelta, right.getPosition().y);
            }
            else if (!rightOnScreen.get(i) && right.getPosition().x != GameContants.RIGHT_TENTACLE_OFF_POS_X) {
                float rightPlusDelta = right.getPosition().x - (GameContants.RIGHT_TENTACLE_ON_POS_X - GameContants.RIGHT_TENTACLE_OFF_POS_X) * delta;
                right.setPosition(rightPlusDelta > GameContants.RIGHT_TENTACLE_OFF_POS_X ? GameContants.RIGHT_TENTACLE_OFF_POS_X : rightPlusDelta, right.getPosition().y);
            }
            else
            {
                if (bossHP <= GameContants.BOSS_MAX_HP / 2)
                    rightOnScreen.set(i, false);
                else if ((timeLap - delta) % 1f > timeLap % 1f && TapPanicGame.rnd.nextInt(1000) < 100)
                    rightOnScreen.set(i, !rightOnScreen.get(i));
            }
        }
    }

    private void behaviorKeyboard(float delta)
    {
        if (keyboardWillChange)
        {
            timeLapChanging += delta;

            if (timeLapChanging >= 1)
            {
                int famillyID = TapPanicGame.rnd.nextInt(6);
                Array<Symbol> family = normalPhase().getSymbolHandler().getFamily(famillyID);
                int lastChoice = -1;
                int maxChoice = 2;
                for (int i = 0; i < keyboard.size; i++) {
                    int choice = TapPanicGame.rnd.nextInt(maxChoice - lastChoice) + 1 + lastChoice;
                    keyboard.get(i).setSymbol(family.get(choice));
                    maxChoice = maxChoice + 3 > 9 ? 9 : maxChoice + 3;
                    lastChoice = choice;
                }

                keyboardWillChange = false;
                //Gdx.app.log("#######################################", "#############################################");
                for (int i = 0; i < keyboard.size; i++) {
                    for (int j = 0; j < shownSymbols.size; j++) {
                        SmartSymbolCapsule item = shownSymbols.get(j);

                        //Gdx.app.log("Key changing", "Keyboard : " + keyboard.get(i).getSymbol().getName() + " equals shown : " + shownSymbols.get(j).getSymbol().getName() + " ? -> " + item.getSymbol().equals(keyboard.get(i)));
                        if (item.getSymbol().equals(keyboard.get(i).getSymbol())) {
                            autodestroyedCapsules.add(shownSymbols.get(j).getCapsule());
                            //Gdx.app.log("Key changing", "Delete : " + shownSymbols.get(j).getSymbol().getName());
                            shownSymbols.removeIndex(j--);
                        }
                        //if (item.getSymbol().equals(keyboard.get(i)))
                        //    Gdx.app.log("Key changing", "Shown Symbol at y = " + item.getPosition().y + " <= " + (GameContants.GIRL_Y_LIMIT - item.getSize().y) * 4f / 5f);
                    }
                }
            }

            return;
        }

        if (timeLap % 0.8 < (timeLap - delta) % 0.8)
            diceTotal += TapPanicGame.rnd.nextInt(100);

        if (diceTotal > 1000) {
            keyboardWillChange = true;
            timeLapChanging = 0;
            diceTotal = 0;
        }
    }

    private void bossBehavior(float delta)
    {
        bossBehaviorSymbols(delta);
        behaviorTentacles(delta);
        behaviorKeyboard(delta);
        //Spells (?)
    }

    private void bossBehaviorSymbols(float delta)
    {
        float timeLapToLaunch = 1.5f;

        if (timeLap % timeLapToLaunch > (timeLap + delta) % timeLapToLaunch)
        {
            SymbolsHandler sh = normalPhase().getSymbolHandler();
            Random rnd = normalPhase().getRandom();
            int dice = rnd.nextInt(bossHP > GameContants.BOSS_MAX_HP / 2 ? 1000 : 2000);

            if (dice > 1700)
                GameplayHelper.launchTwoStars(keyboard, shownSymbols, sh, rnd);
            else if (dice > 1250)
                GameplayHelper.launchDoubleFall(keyboard, shownSymbols, sh, rnd);
            else if (dice > 1000)
                GameplayHelper.launchThreeMoking(keyboard, shownSymbols, sh, rnd);
            else if (dice > 600)
                GameplayHelper.launchNormalFall(keyboard, shownSymbols, sh, rnd);
            else if (dice > 200)
                GameplayHelper.launchLeafFall(keyboard, shownSymbols, sh, rnd);
            else if (dice > 000)
                GameplayHelper.launchFourFlowers(keyboard, shownSymbols, sh, rnd);
        }
    }

    private void bossDeath()
    {
        shownSymbols.clear();
        animating = true;
    }

    public void bossDeathFinished()
    {
        normalPhase().backFromBoss();
        normalPhase().setBossTimeMS(bossTimeSeconds * 1000);

        if (!world.isBossMode())
            world.activatePhase("Normal");

        this.deactivate();
        bossHP = GameContants.BOSS_MAX_HP;
    }

    /**
     *
     * @param touchedSymbol null if it's timeout
     * @return
     */
    private boolean hasFailed(Symbol touchedSymbol) {
        if (shownSymbols.size == 0)
            return false;

        for (Iterator<SmartSymbolCapsule> i = shownSymbols.iterator(); i.hasNext(); ) {
            SmartSymbolCapsule item = i.next();
            if (item.getSymbol().equals(touchedSymbol))
                return false;
        }

        return true;
    }

    private SymbolCapsule hasFailedOn()
    {
        for (Iterator<SmartSymbolCapsule> i = shownSymbols.iterator(); i.hasNext(); ) {
            SmartSymbolCapsule item = i.next();
            for (Iterator<SmartSymbolCapsule> j = keyboard.iterator(); j.hasNext(); ) {
                if (item.getSymbol().equals(j.next().getSymbol()) && (item.getPosition().y + item.getSize().y) >= GameContants.GIRL_Y_LIMIT) {
                    return item.getCapsule();
                }
            }
        }

        return null;
    }

    public void eventTouchKey(int keyIndex)
    {
        if (!hasFailed(keyboard.get(keyIndex).getSymbol()))
        {
            SmartSymbolCapsule cap;

            for (int i = 0; i < shownSymbols.size; i++)
            {
                if (shownSymbols.get(i).getSymbol().equals(keyboard.get(keyIndex).getSymbol()))
                {
                    cap = shownSymbols.get(i);

                    int hpLost = cap.getPosition().y < GameContants.AWESOME_Y_LIMIT ? 3 : cap.getPosition().y < GameContants.YEAH_Y_LIMIT ? 2 : 1;
                    bossHP -= hpLost;
                    shownSymbols.removeIndex(i--);
                    destroyedCapsules.add(cap.getCapsule());
                }
            }

            if (bossHP <= 0)
            {
                bossDeath();
            }
        }
        else
        {
            normalPhase().launchGameOver(keyboard.get(keyIndex).getCapsule(), shownSymbols.get(0).getCapsule());
            this.deactivate();
        }
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Array<SmartSymbolCapsule> kb = keyboard;

        for (int i = 0; i < kb.size; i++) {
            SmartSymbolCapsule cap = kb.get(i);
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

    public boolean isAnimating() {
        return animating;
    }

    public void setAnimating(boolean animating) {
        this.animating = animating;
    }

    public Array<SmartSymbolCapsule> getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(Array<SmartSymbolCapsule> keyboard) {
        this.keyboard = keyboard;
    }

    public Array<SmartSymbolCapsule> getShownSymbols() {
        return shownSymbols;
    }

    public void setShownSymbols(Array<SmartSymbolCapsule> shownSymbols) {
        this.shownSymbols = shownSymbols;
    }

    public Array<BreathingTextureObject> getLeftTentacles() {
        return leftTentacles;
    }

    public void setLeftTentacles(Array<BreathingTextureObject> leftTentacles) {
        this.leftTentacles = leftTentacles;
    }

    public Array<BreathingTextureObject> getRightTentacles() {
        return rightTentacles;
    }

    public void setRightTentacles(Array<BreathingTextureObject> rightTentacles) {
        this.rightTentacles = rightTentacles;
    }

    public int getBossHP() {
        return bossHP;
    }

    public void setBossHP(int bossHP) {
        this.bossHP = bossHP;
    }

    public Array<SymbolCapsule> getDestroyedCapsules() {
        return destroyedCapsules;
    }

    public Array<SymbolCapsule> autogetDestroyedCapsules() {
        return autodestroyedCapsules;
    }

    public int getScoreTurn() {
        return scoreTurn;
    }

    public int getScorePoints() {
        return scorePoints;
    }

    public boolean isKeyboardWillChange() {
        return keyboardWillChange;
    }

    public float getTimeLapChanging() {
        return timeLapChanging;
    }

    public float getBossTimeSeconds() {
        return bossTimeSeconds;
    }
}
