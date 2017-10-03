package com.flamboyant.tappanic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.flamboyant.common.multiplatform.ads.IInterstitialHandler;
import com.flamboyant.common.multiplatform.play.IPlayServices;
import com.flamboyant.common.multiplatform.social.ISocialServices;
import com.flamboyant.tappanic.tools.ApplicationDataDebug;
import com.flamboyant.tappanic.screens.IntroScreen;
import com.flamboyant.tappanic.screens.MainScreen;
import com.flamboyant.tappanic.screens.TapPanicScreen;
import com.flamboyant.tappanic.screens.WaitingScreen;
import com.flamboyant.tappanic.tools.AchievementManager;
import com.flamboyant.tappanic.tools.HighLevelAssetManager;
import com.flamboyant.tappanic.tools.SaveManager;

import java.util.Random;

public class TapPanicGame extends Game {
	public static Random rnd;
	public static IPlayServices playServices;
	public static boolean soundEnabled = true;

	public SpriteBatch batch;
	public IntroScreen introScreen;
	public MainScreen mainScreen;
	public TapPanicScreen gameScreen;
	public WaitingScreen waitingScreen;
	public HighLevelAssetManager assetManager;
	public SaveManager saveManager;
	public ISocialServices socialServices;
	public ApplicationDataDebug applicationDataDebug;

	//public NumbersScreen introScreen;
	Texture img;
	public IInterstitialHandler interstitialHandler;
	public boolean isContextAvailable = true;

	public TapPanicGame(IInterstitialHandler interstitialHandler, IPlayServices playServices, ISocialServices socialServices)
	{
		super();

		this.socialServices = socialServices;
		this.playServices = new AchievementManager(playServices);
		this.interstitialHandler = interstitialHandler;
		this.applicationDataDebug = new ApplicationDataDebug(this);
	}

	@Override
	public void create () {
		rnd = new Random(TimeUtils.millis());

		assetManager = new HighLevelAssetManager();

		batch = new SpriteBatch();
		gameScreen = new TapPanicScreen(this);
		mainScreen = new MainScreen(this);
		introScreen = new IntroScreen(this);
		waitingScreen = new WaitingScreen(this);
		//introScreen = new NumbersScreen(this);
		waitingScreen.preloadWaitingScreen();
		saveManager = new SaveManager();
		saveManager.readSaveDate();

		this.setScreen(introScreen);
	}

	@Override
	public void render () {
		super.render();
	}

	public void dispose(){
		super.dispose();
	}

	public void contextLoss()
	{
		assetManager.freeMemory();
		batch.dispose();
		isContextAvailable = false;
	}

	public void contextReload()
	{
		batch = new SpriteBatch();
		isContextAvailable = true;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
