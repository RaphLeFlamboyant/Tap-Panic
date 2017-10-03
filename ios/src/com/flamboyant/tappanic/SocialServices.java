package com.flamboyant.tappanic;

import com.flamboyant.common.multiplatform.social.ISocialServices;

import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.uikit.UIAlertAction;
import org.robovm.apple.uikit.UIAlertActionStyle;
import org.robovm.apple.uikit.UIAlertController;
import org.robovm.apple.uikit.UIAlertControllerStyle;
import org.robovm.apple.uikit.UIAlertView;
import org.robovm.apple.uikit.UITableViewController;
import org.robovm.apple.uikit.UIViewController;

import java.util.Arrays;

/**
 * Created by Reborn Portable on 14/04/2016.
 */
public class SocialServices implements ISocialServices {
    @Override
    public void shareToSocial() {

    }

    @Override
    public void shareScore(int hits, int points) {

    }

    @Override
    public void shareScore(int hits, int points, float boss) {

    }

    @Override
    public void shareScore(float boss) {

    }

    @Override
    public void shareGameOver(boolean bossMode) {

    }

    private void loginAction(String toPublish) {
        if (FacebookHandler.getInstance().isLoggedIn()) {
            FacebookHandler.getInstance().logOut();

        } else {
            FacebookHandler.getInstance().logIn(Arrays.asList("email", "user_friends"),
                    new FacebookHandler.LoginListener() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(String message) {
                            FacebookHandler.getInstance().alertError("Error during login!", message);
                        }

                        @Override
                        public void onCancel() {
                        }
                    });
        }
    }

    private void publishFeedAction(String toPublish) {
        FacebookHandler.getInstance().publishFeed("Tap Panic", "Tap Panic Fb iOS",
                toPublish, "", "",
                new FacebookHandler.RequestListener() {
                    @Override
                    public void onSuccess(NSObject result) {
                        UIAlertController alert = new UIAlertController("Oops!", "This feature isn't available right now", UIAlertControllerStyle.Alert);
                        alert.addAction(new UIAlertAction("OK", UIAlertActionStyle.Default, null));

                        self.presentViewController(alert, animated: true){};
                        //UIAlertView alert = new UIAlertView("Success!", "Your message has been posted!", null, "OK");
                        //alert.show();
                    }

                    @Override
                    public void onError(String message) {
                        FacebookHandler.getInstance().alertError("Error during feed!", message);
                    }

                    @Override
                    public void onCancel() {}
                });
    }
}
