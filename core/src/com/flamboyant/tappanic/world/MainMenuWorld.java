package com.flamboyant.tappanic.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Reborn Portable on 02/12/2015.
 */
public class MainMenuWorld {

    private int turnRecord = 0;
    private int scoreRecord = 0;

    public MainMenuWorld()
    {
        FileHandle save = Gdx.files.local("tappanic.sav");

        if (save.exists())
        {
            String data = save.readString();

            turnRecord = Integer.parseInt(data.split(";")[0]);
            scoreRecord = Integer.parseInt(data.split(";")[1]);
        }
    }

    public int getTurnRecord() {
        return turnRecord;
    }

    public int getScoreRecord() {
        return scoreRecord;
    }
}
