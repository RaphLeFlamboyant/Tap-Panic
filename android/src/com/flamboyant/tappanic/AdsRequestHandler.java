package com.flamboyant.tappanic;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.flamboyant.common.multiplatform.ads.IAdsRequestHandler;
import com.flamboyant.tappanic.tools.GameContants;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Reborn Portable on 26/01/2016.
 */
public class AdsRequestHandler implements IAdsRequestHandler {
    private InterstitialAd iterAdView;
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case GameContants.SHOW_ADS_MSG:
                {
                    if (iterAdView.isLoaded())
                        iterAdView.show();
                    break;
                }
                case GameContants.HIDE_ADS_MSG:
                {
                    //iterAdView.setVisibility(View.GONE);
                    break;
                }
            }
        }
    };

    public AdsRequestHandler(InterstitialAd iterAdView)
    {
        this.iterAdView = iterAdView;
    }

    @Override
    public void showAds() {
        handler.sendEmptyMessage(GameContants.SHOW_ADS_MSG);
    }

    @Override
    public void hideAds() {
        handler.sendEmptyMessage(GameContants.HIDE_ADS_MSG);
    }
}
