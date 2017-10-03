package com.flamboyant.tappanic.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flamboyant.tappanic.TapPanicGame;

/**
 * Created by Reborn Portable on 21/03/2016.
 */
public class ApplicationDataDebug {
    private TapPanicGame game;
    private Long maxMemory = null;

    public ApplicationDataDebug(TapPanicGame game){
        this.game = game;
    }

    public void loadFonts() {
        game.assetManager.loadFontFromGenerator("Fonts/TitanOne-Regular.ttf", "debugFont", (int) (15 * GameContants.RATIO_FONT_SIZE), Color.FIREBRICK);
    }

    public int getHeapLevel() {
        /*if (GameContants.DEBUG_MODE)
            return 1;*/
        if (this.maxMemory == null)
            return 3;

        if (this.maxMemory / (1024 * 1024) >= 100)
            return 3;
        else if (this.maxMemory / (1024 * 1024) > 32)
            return 2;

        return 1;
    }

    public void setMaxHeap(long maxMemory)
    {
        this.maxMemory = maxMemory;
    }

    public boolean draw(SpriteBatch batcher) {
        long jvheap = Gdx.app.getJavaHeap();
        long natheap = Gdx.app.getNativeHeap();

        game.assetManager.getFont("debugFont").draw(batcher, "JV heap : " + jvheap, GameContants.SCREEN_WIDTH / 32, GameContants.SCREEN_HEIGTH / 4);
        game.assetManager.getFont("debugFont").draw(batcher, "Nat heap : " + natheap, GameContants.SCREEN_WIDTH / 32, GameContants.SCREEN_HEIGTH / 4 + (int) (15 * GameContants.RATIO_FONT_SIZE) + 3);

        if (maxMemory != null) {
            game.assetManager.getFont("debugFont").draw(batcher, "Max heap : " + maxMemory, GameContants.SCREEN_WIDTH / 32, GameContants.SCREEN_HEIGTH / 4 + 2 * ((int) (15 * GameContants.RATIO_FONT_SIZE) + 3));
            game.assetManager.getFont("debugFont").draw(batcher, "Draw lvl : " + getHeapLevel(), GameContants.SCREEN_WIDTH / 32, GameContants.SCREEN_HEIGTH / 4 + 3 * ((int) (15 * GameContants.RATIO_FONT_SIZE) + 3));
        }

        return true;
    }
}
