package com.flamboyant.tappanic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.flamboyant.common.multiplatform.play.IPlayServices;
import com.flamboyant.tappanic.tools.GameContants;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements IPlayServices {
	private AdView adView;
	private InterstitialAd iterAdView;

	private GameHelper gameHelper;
	private final static int requestCode = 1;

	private void initGameHelper()
	{
		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInFailed(){ }

			@Override
			public void onSignInSucceeded(){ }
		};

		gameHelper.setup(gameHelperListener);
	}

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new TapPanicGame(), config);

		// Create the layout
		RelativeLayout layout = new RelativeLayout(this);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		initGameHelper();

		// Create and setup the AdMob view
		iterAdView = new InterstitialAd(this);
		iterAdView.setAdUnitId("ca-app-pub-3139748549474862/1647231630");//ca-app-pub-3940256099942544/103317371");//A gauche Debug à droite release

		iterAdView.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				requestNewInterstitial();
			}
		});

		requestNewInterstitial();

		/*adView = new AdView(this); // Put in your secret key here
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
		AdRequest.Builder builder = new AdRequest.Builder();
		AdRequest request = builder.build();
		adView.loadAd(request);*/

		// Add the libgdx view
		TapPanicGame game = new TapPanicGame(new InterstitialHandler(iterAdView), this, new SocialServices(this));
		game.applicationDataDebug.setMaxHeap(Runtime.getRuntime().maxMemory());
		View gameView = initializeForView(game, config);
		//layout.addView(gameView);

		// Add the AdMob view
		/*RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);*/

		//layout.addView(iterAdView, adParams);

		setContentView(gameView);
	}

	private void requestNewInterstitial() {
		AdRequest adRequest = new AdRequest.Builder().build();//addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID").build();

		iterAdView.loadAd(adRequest);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void signIn()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.signOut();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame()
	{
		if (!isSignedIn())
			return;

		String str = "market://details?id=" + getPackageName();
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void unlockAchievement(String achievementName) {
		if (!isSignedIn())
			return;

		String achievement = "";
		switch (achievementName)
		{
			case GameContants.ACHIEVEMENT_FIRST_STEP:
				achievement = getString(R.string.achievement_first_steps);
				break;
			case GameContants.ACHIEVEMENT_HALF:
				achievement = getString(R.string.achievement_you_made_the_half_);
				break;
			case GameContants.ACHIEVEMENT_ICE:
				achievement = getString(R.string.achievement_let_it_go_);
				break;
			case GameContants.ACHIEVEMENT_BOSS_ENCOUNTER:
				achievement = getString(R.string.achievement_it_looks_like_a_manga_but_i_dont_remind_which_one____);
				break;
			case GameContants.ACHIEVEMENT_BOSS_DEFEAT:
				achievement = getString(R.string.achievement_we_did_it_);
				break;
			default:
				break;
		}

		Games.Achievements.unlock(gameHelper.getApiClient(), achievement);
	}

	@Override
	public void submitScoreHits(int highScore) {
		if (isSignedIn() == true)
		{

			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					getString(R.string.leaderboard_tap_panic_hits), highScore);
		}
	}

	@Override
	public void submitScorePoints(int highScore) {
		if (isSignedIn() == true)
		{
			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					getString(R.string.leaderboard_tap_panic_points), highScore);
		}
	}

	@Override
	public void submitScoreBossTime(int timeMS) {
		if (isSignedIn() == true)
		{
			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					getString(R.string.leaderboard_tap_panic_boss_paul_le_poulpe_time), timeMS);
		}
	}

	@Override
	public void showAchievement() {
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public void showScore() {
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(gameHelper.getApiClient()), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public boolean isSignedIn()
	{
		return gameHelper.isSignedIn();
	}

}
