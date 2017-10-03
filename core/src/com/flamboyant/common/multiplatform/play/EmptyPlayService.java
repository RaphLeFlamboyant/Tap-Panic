package com.flamboyant.common.multiplatform.play;

/**
 * Created by Reborn Portable on 24/02/2016.
 */
public class EmptyPlayService implements IPlayServices {
    @Override
    public void signIn() {

    }

    @Override
    public void signOut() {

    }

    @Override
    public void rateGame() {

    }

    @Override
    public void unlockAchievement(String achievementName) {

    }

    @Override
    public void submitScoreHits(int highScore) {

    }

    @Override
    public void submitScorePoints(int highScore) {

    }

    @Override
    public void submitScoreBossTime(int timeMS) {

    }

    @Override
    public void showAchievement() {

    }

    @Override
    public void showScore() {

    }

    @Override
    public boolean isSignedIn() {
        return false;
    }
}
