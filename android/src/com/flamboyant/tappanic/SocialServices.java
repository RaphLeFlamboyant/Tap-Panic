package com.flamboyant.tappanic;

import android.content.Intent;

import com.flamboyant.common.multiplatform.social.ISocialServices;

import java.util.Locale;

/**
 * Created by Reborn Portable on 16/03/2016.
 */
public class SocialServices implements ISocialServices {
    private AndroidLauncher owner;
    private String language;

    public SocialServices(AndroidLauncher owner)
    {
        this.owner = owner;
        language  = Locale.getDefault().getDisplayLanguage();
    }

    private void startIntent(String message)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        owner.startActivity(sendIntent);
    }

    @Override
    public void shareToSocial() {
        if (language.equals(Locale.FRANCE.getDisplayLanguage()))
            startIntent("Pas de panique, essayez ce jeu ! http://bit.ly/TapPanic-play");
        else
            startIntent("Don't panic and try this game ! http://bit.ly/TapPanic-play");
    }

    @Override
    public void shareScore(int hits, int points) {
        String message;

        if (language.equals(Locale.FRANCE.getDisplayLanguage())) {
            message = hits < 50 ? "Aaah ! C'est la panique." : "Je maitrise le jeu !";
            message += " Essayez de faire plus que " + hits + " coups et " + points + " points sur Tap Panic ! http://bit.ly/TapPanic-play";
        }
        else
        {
            message = hits < 50 ? "Aaah ! I panicked." : "I master the game !";
            message +=  " Try to do better than " + hits + " hits and " + points + " points on Tap Panic ! http://bit.ly/TapPanic-play";
        }

        startIntent(message);
    }

    @Override
    public void shareScore(int hits, int points, float boss) {
        String message;

        if (language.equals(Locale.FRANCE.getDisplayLanguage())) {
            message = "J'ai tué le boss de Tap Panic en " + (int) (boss / 1000 / 60) + "minutes " + (int) (boss / 1000 % 60) + " ! " + hits + " coups et " + points + " points, faites mieux pour voir ! http://bit.ly/TapPanic-play";
        }
        else
        {
            message = "I killed the Tap Panic's boss in " + (int) (boss / 1000 / 60) + "minutes and " + (int) (boss / 1000 % 60) + " seconds ! " + hits + " hits et " + points + " points, try to do better ! http://bit.ly/TapPanic-play";
        }

        startIntent(message);
    }

    @Override
    public void shareScore(float boss) {
        String message;

        if (language.equals(Locale.FRANCE.getDisplayLanguage())) {
            message = "J'ai tué le boss de Tap Panic en " + (int) (boss / 1000 / 60) + " minute " + (int) (boss / 1000 % 60) + " ! Même pas dit que vous réussissiez à le battre ;) http://bit.ly/TapPanic-play";
        }
        else
        {
            message = "I killed the Tap Panic's boss in " + (int) (boss / 1000 / 60) + " minute and " + (int) (boss / 1000 % 60) + " seconds ! I'm sure you can't beat it ;) http://bit.ly/TapPanic-play";
        }

        startIntent(message);
    }

    @Override
    public void shareGameOver(boolean bossMode) {
        if (bossMode) {
            if (language.equals(Locale.FRANCE.getDisplayLanguage()))
                startIntent("J'ai trop paniqué devant le boss de Tap Panic, essayez-le ! http://bit.ly/TapPanic-play");
            else
                startIntent("I panicked against the Tap Panic's boss, try it ! http://bit.ly/TapPanic-play");
        }
        else {
            if (language.equals(Locale.FRANCE.getDisplayLanguage()))
                startIntent("C'est la panique, je ne m'en sors pas sur Tap Panic ! Êtes-vous à la hauteur ? http://bit.ly/TapPanic-play");
            else
                startIntent("Help, i can't handle Tap Panic game ! Are you strong enough for it ? http://bit.ly/TapPanic-play");
        }
    }
}
