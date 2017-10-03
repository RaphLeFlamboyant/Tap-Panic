package com.flamboyant.common.multiplatform.play;

import java.util.Timer;

/**
 * Created by Reborn Portable on 24/02/2016.
 */
public interface IPlayServices {
    void signIn();
    void signOut();
    void rateGame();
    void unlockAchievement(String achievementName);
    void submitScoreHits(int highScore);
    void submitScorePoints(int highScore);
    void submitScoreBossTime(int timeMS);
    void showAchievement();
    void showScore();
    boolean isSignedIn();
}
