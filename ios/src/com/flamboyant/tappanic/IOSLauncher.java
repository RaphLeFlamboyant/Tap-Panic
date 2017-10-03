package com.flamboyant.tappanic;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSErrorException;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.pods.google.GGLContext;
import org.robovm.pods.google.games.GPGManager;
import org.robovm.pods.google.signin.GIDSignIn;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.flamboyant.common.multiplatform.ads.EmptyInterstitialHandler;
import com.flamboyant.common.multiplatform.play.EmptyPlayService;
import com.flamboyant.common.multiplatform.social.EmptySocialServices;

public class IOSLauncher extends IOSApplication.Delegate {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();

        InterstitialHandler tHandler = new InterstitialHandler();
        tHandler.loadAds();

        return new IOSApplication(new TapPanicGame(tHandler, new PlayServices(), new EmptySocialServices()), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    @Override
    public boolean didFinishLaunching(UIApplication application, UIApplicationLaunchOptions launchOptions) {
        super.didFinishLaunching(application, launchOptions);
        try {
            GGLContext.getSharedInstance().configure();
            GIDSignIn.getSharedInstance().setAllowsSignInWithWebView(true);
            GPGManager.getSharedInstance().signIn("151503205284-v6kdjg8tefgnl2vkot57f33upsd9dc7t.apps.googleusercontent.com", true);
        } catch (NSErrorException e) {
        }
        return true;
    }
}