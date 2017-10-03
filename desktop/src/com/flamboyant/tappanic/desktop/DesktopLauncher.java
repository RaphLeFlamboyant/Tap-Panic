package com.flamboyant.tappanic.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.flamboyant.common.multiplatform.ads.EmptyInterstitialHandler;
import com.flamboyant.common.multiplatform.play.EmptyPlayService;
import com.flamboyant.common.multiplatform.social.EmptySocialServices;
import com.flamboyant.tappanic.TapPanicGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tap Panic";
		config.width = 525;
		config.height = 800;
		new LwjglApplication(new TapPanicGame(new EmptyInterstitialHandler(), new EmptyPlayService(), new EmptySocialServices()), config);
	}
}
