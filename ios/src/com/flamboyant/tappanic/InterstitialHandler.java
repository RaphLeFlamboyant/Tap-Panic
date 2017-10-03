package com.flamboyant.tappanic;

import com.flamboyant.common.multiplatform.ads.IInterstitialHandler;

import org.robovm.apple.uikit.UIApplication;
import org.robovm.pods.google.mobileads.GADInterstitial;
import org.robovm.pods.google.mobileads.GADInterstitialDelegateAdapter;
import org.robovm.pods.google.mobileads.GADRequest;

import java.util.Arrays;


/**
 * Created by Reborn Portable on 12/04/2016.
 */
public class InterstitialHandler implements IInterstitialHandler {
    private GADInterstitial interstitial;

    private GADInterstitial createAndLoadInterstitial() {
        GADInterstitial interstitial = new GADInterstitial("ca-app-pub-3139748549474862/1605457232");
        interstitial.setDelegate(new GADInterstitialDelegateAdapter() {
            @Override
            public void didDismissScreen(GADInterstitial ad) {
                InterstitialHandler.this.interstitial = createAndLoadInterstitial();
            }
        });
        interstitial.loadRequest(createRequest());
        return interstitial;
    }

    private GADRequest createRequest() {
        GADRequest request = new GADRequest();
        request.setTestDevices(Arrays.asList(GADRequest.getSimulatorID()));
        return request;
    }

    public void loadAds() {
        interstitial = createAndLoadInterstitial();
    }

    @Override
    public void launchInterstitial() {
        if (interstitial.isReady()) {
            interstitial.present(UIApplication.getSharedApplication().getKeyWindow().getRootViewController());
        } else {
            //System.out.println("Interstitial not ready!");
        }
    }
}
