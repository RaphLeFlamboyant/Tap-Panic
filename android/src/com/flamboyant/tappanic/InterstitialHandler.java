package com.flamboyant.tappanic;

import android.os.Handler;
import android.os.Message;

import com.flamboyant.common.multiplatform.ads.IInterstitialHandler;
import com.flamboyant.tappanic.tools.GameContants;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Reborn Portable on 27/01/2016.
 */
public class InterstitialHandler implements IInterstitialHandler {
    private InterstitialAd interAd;
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case GameContants.SHOW_INTERSTITIAL_MSG:
                {
                    if (interAd != null && interAd.isLoaded())
                        interAd.show();

                    break;
                }
            }
        }
    };

    public InterstitialHandler(InterstitialAd ad)
    {
        interAd = ad;
    }

    @Override
    public void launchInterstitial() {
        handler.sendEmptyMessage(GameContants.SHOW_INTERSTITIAL_MSG);
    }
}
