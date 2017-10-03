package com.flamboyant.tappanic.tools;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.flamboyant.common.multiplatform.play.IPlayServices;

/**
 * Created by Reborn Portable on 25/02/2016.
 */
public class AchievementManager implements IPlayServices {
    private IPlayServices playService;
    private Array<String> achievementUnlocked = new Array<String>();

    private int lastHitsScore = 0;
    private int lastPointsScore = 0;
    private int lastTimeScore = 0;

    public AchievementManager(IPlayServices service)
    {
        playService = service;
    }

    @Override
    public void signIn() {
        playService.signIn();
    }

    @Override
    public void signOut() {
        playService.signOut();
    }

    @Override
    public void rateGame() {
        playService.rateGame();
    }

    @Override
    public void unlockAchievement(String achievementName) {
        if (!achievementUnlocked.contains(achievementName, false))
        {
            playService.unlockAchievement(achievementName);
            achievementUnlocked.add(achievementName);
        }

    }

    @Override
    public void submitScoreHits(int highScore) {
        if (highScore > lastHitsScore) {
            playService.submitScoreHits(highScore);
            lastHitsScore = highScore;
        }
    }

    @Override
    public void submitScorePoints(int highScore) {
        if (highScore > lastPointsScore) {
            playService.submitScorePoints(highScore);
            lastPointsScore = highScore;
        }
    }

    @Override
    public void submitScoreBossTime(int timeMS) {
        if (timeMS < lastTimeScore || lastPointsScore == 0)
        {
            playService.submitScoreBossTime(timeMS);
            lastTimeScore = timeMS;
        }
    }

    @Override
    public void showAchievement() {
        playService.showAchievement();
    }

    @Override
    public void showScore() {
        playService.showScore();
    }

    @Override
    public boolean isSignedIn() {
        return playService.isSignedIn();
    }
}
