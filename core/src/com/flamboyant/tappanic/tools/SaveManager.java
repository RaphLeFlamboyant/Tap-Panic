package com.flamboyant.tappanic.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Reborn Portable on 25/02/2016.
 */
public class SaveManager {
    private SaveData saveData = new SaveData();

    public void writeSaveData()
    {
        int bestTurn = 0;
        int bestScore = 0;
        int bestBoss = 0;

        FileHandle file = Gdx.files.local("tappanic.sav");
        if (file.exists())
        {
            String saves = file.readString();
            String[] split = saves.split(";");
            bestTurn = split.length >= 1 ? Integer.parseInt(split[0]) : 0;
            bestScore = split.length >= 2 ?  Integer.parseInt(split[1]) : 0;
            bestBoss = split.length >= 3 ? Integer.parseInt(split[2]) : 0;
        }

        bestTurn = bestTurn < saveData.scoreTurn ? saveData.scoreTurn : bestTurn;
        bestScore = bestScore < saveData.scorePoints ? saveData.scorePoints : bestScore;
        bestBoss = bestBoss > saveData.bossTimeMS && saveData.bossTimeMS != 0 ? saveData.bossTimeMS : bestBoss;

        file.writeString("" + bestTurn + ";" + bestScore + ";" + bestBoss, false);
    }

    public void readSaveDate()
    {
        FileHandle file = Gdx.files.local("tappanic.sav");
        if (file.exists())
        {
            String saves = file.readString();
            String[] split = saves.split(";");
            saveData.scoreTurn = split.length >= 1 ? Integer.parseInt(split[0]) : 0;
            saveData.scorePoints = split.length >= 2 ?  Integer.parseInt(split[1]) : 0;
            saveData.bossTimeMS = split.length >= 3 ? Integer.parseInt(split[2]) : 0;
        }
    }

    public SaveData getSaveData ()
    {
        return saveData;
    }

    public void setSaveData(SaveData data)
    {
        saveData = data;
    }

    public class SaveData {
        public int scoreTurn;
        public int scorePoints;
        public int bossTimeMS;
    }
}
