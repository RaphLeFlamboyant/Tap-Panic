package com.flamboyant.tappanic;

import com.flamboyant.common.multiplatform.play.IPlayServices;
import com.flamboyant.tappanic.tools.GameContants;

import org.robovm.apple.foundation.NSError;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.pods.google.games.GPGAchievement;
import org.robovm.pods.google.games.GPGAchievementUnlockCallback;
import org.robovm.pods.google.games.GPGLauncherController;
import org.robovm.pods.google.games.GPGManager;
import org.robovm.pods.google.games.GPGScore;
import org.robovm.pods.google.signin.GIDSignIn;
import org.robovm.pods.google.signin.GIDSignInUIDelegateAdapter;

/**
 * Created by Reborn Portable on 14/04/2016.
 */
public class PlayServices implements IPlayServices {
    public PlayServices()
    {

        GIDSignIn.getSharedInstance().setUiDelegate(new GIDSignInUIDelegateAdapter() {
            /*@Override
            public void willDispatch(GIDSignIn signIn, NSError error) {
                Foundation.log("willDispatch()");
            }*/

            @Override
            public void presentViewController(GIDSignIn signIn, UIViewController viewController) {
                UIApplication.getSharedApplication().getKeyWindow().getRootViewController().presentViewController(viewController, true, null);
            }

            @Override
            public void dismissViewController(GIDSignIn signIn, UIViewController viewController) {
                viewController.dismissViewController(true, null);
            }
        });
    }

    @Override
    public void signIn() {
        GPGManager.getSharedInstance().signIn("151503205284-v6kdjg8tefgnl2vkot57f33upsd9dc7t.apps.googleusercontent.com", false);
    }

    @Override
    public void signOut() {
        GPGManager.getSharedInstance().signOut();
    }

    @Override
    public void rateGame() {

    }

    @Override
    public void unlockAchievement(String achievementName) {
        if (!isSignedIn())
            return;

        String achievement = "";
        switch (achievementName)
        {
            case GameContants.ACHIEVEMENT_FIRST_STEP:
                achievement = "CgkIpOetsrQEEAIQBg";
                break;
            case GameContants.ACHIEVEMENT_HALF:
                achievement = "CgkIpOetsrQEEAIQBw";
                break;
            case GameContants.ACHIEVEMENT_ICE:
                achievement = "CgkIpOetsrQEEAIQBQ";
                break;
            case GameContants.ACHIEVEMENT_BOSS_ENCOUNTER:
                achievement = "CgkIpOetsrQEEAIQCA";
                break;
            case GameContants.ACHIEVEMENT_BOSS_DEFEAT:
                achievement = "CgkIpOetsrQEEAIQCQ";
                break;
            default:
                break;
        }

        final GPGAchievement unlockMe = new GPGAchievement(achievement);
        unlockMe.unlock(new GPGAchievementUnlockCallback() {
            @Override
            public void done(boolean newlyUnlocked, NSError error) {
                if (error != null) {
                    //Log.e("Received an error attempting to unlock an achievement %s: %s", unlockMe, error);
                }
            }
        });
    }

    @Override
    public void submitScoreHits(int highScore) {
        if (isSignedIn() == true) {
            final GPGScore submitMe = GPGScore.getScore("CgkIpOetsrQEEAIQAQ");
            submitMe.setValue(highScore);
        }
    }

    @Override
    public void submitScorePoints(int highScore) {
        if (isSignedIn() == true) {
            final GPGScore submitMe = GPGScore.getScore("CgkIpOetsrQEEAIQAA");
            submitMe.setValue(highScore);
        }
    }

    @Override
    public void submitScoreBossTime(int timeMS) {
        if (isSignedIn() == true) {
            final GPGScore submitMe = GPGScore.getScore("CgkIpOetsrQEEAIQAw");
            submitMe.setValue(timeMS);
        }
    }

    @Override
    public void showAchievement() {
        if (isSignedIn()) {
            GPGLauncherController.getSharedInstance().presentAchievementList();
        } else {
            signIn();
        }
    }

    @Override
    public void showScore() {
        if (isSignedIn()) {
            GPGLauncherController.getSharedInstance().presentLeaderboardList();
        } else {
            signIn();
        }
    }

    @Override
    public boolean isSignedIn() {
        return GPGManager.getSharedInstance().isSignedIn();
    }
}
