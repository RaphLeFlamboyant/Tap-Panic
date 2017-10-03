package com.flamboyant.tappanic.tools;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * Created by Reborn Portable on 22/10/2015.
 */
public class GameContants {
    public static boolean DEBUG_MODE = false;

    public static int ICE_RESISTANCE = 3;
    public static int TIME_TO_REACT_MS_MAX = 2000;
    public static int TIME_TO_REACT_MS_MIN = 1300;
    public static int TURN_ENABLE_HARDE_MODE = 80;
    public static int TURN_MAX_DIFFICULTY = 160;

    public static int DESKTOP_WIDTH = 525;
    public static int DESKTOP_HEIGTH = 800;
    public static int SCREEN_WIDTH = Gdx.app.getType() == Application.ApplicationType.Desktop ? DESKTOP_WIDTH : Gdx.graphics.getWidth();
    public static int SCREEN_HEIGTH = Gdx.app.getType() == Application.ApplicationType.Desktop ? DESKTOP_HEIGTH : Gdx.graphics.getHeight();

    public static int BACKGROUND_WIDTH = SCREEN_WIDTH * 1680 / 800;
    public static int BACKGROUND_HEIGTH = SCREEN_HEIGTH * 1380 / 1280;
    public static int BACKGROUND_PANNEL_WIDTH = SCREEN_HEIGTH * 1800 / 800;
    public static int BACKGROUND_INIT_POSITION_X = -(BACKGROUND_WIDTH - SCREEN_WIDTH) / 2;
    public static int BACKGROUND_POSITION_Y = -(BACKGROUND_HEIGTH - SCREEN_HEIGTH) / 2;

    public static float RATIO_X = SCREEN_WIDTH / 800f;
    public static float RATIO_Y = SCREEN_HEIGTH / 1280f;
    public static float RATIO_FONT_SIZE = SCREEN_HEIGTH / DESKTOP_HEIGTH;

    public static int SYMBOL_MARGIN = (int) (SCREEN_WIDTH * 0.1);
    public static int SYMBOL_MIN_SIZE = (int) (SCREEN_WIDTH * 0.1);
    public static int SYMBOL_MAX_SIZE = (int) (SCREEN_WIDTH * 0.2);
    public static int SYMBOL_MAX_ROTATE_SPEED = 720; //° per sec
    public static float SYMBOL_MIN_REACT_TIME = 0.5f; //temps min pour réagir

    public static int SIZE_PAUSE_X = (int) (SCREEN_WIDTH / 5.3 / 3);
    public static int SIZE_PAUSE_Y = (int) (SCREEN_HEIGTH / 8.53 / 3);

    public static int KEY_ZONE_X = (int)(GameContants.SCREEN_WIDTH / 8);
    public static int KEY_CELL_SIZE = (int) (GameContants.SCREEN_WIDTH / 4);
    public static int KEY_SIZE = (int) (KEY_CELL_SIZE * 0.8);
    public static int KEY_START_MARGIN = (int) ((KEY_CELL_SIZE - KEY_SIZE) / 2);
    public static int KEY_ZONE_Y = (int)((GameContants.SCREEN_HEIGTH - KEY_CELL_SIZE * 3) / 2);

    public static int ITEMFLY_SIZE = (int) (SCREEN_WIDTH * 0.1);
    public static int GIRL_SIZE_X = (int) (SCREEN_WIDTH / 3.5);
    public static int GIRL_SIZE_Y = GIRL_SIZE_X * 275 / 200;
    public static int GIRL_POSITION_Y = SCREEN_HEIGTH - GIRL_SIZE_Y * 9 / 10;
    public static int GIRL_MENU_SIZE_Y = SCREEN_HEIGTH / 2 - SCREEN_HEIGTH / 6;
    public static int GIRL_MENU_SIZE_X = GIRL_MENU_SIZE_Y * 200 / 275;

    public static int BOSS_SIZE_X = SCREEN_WIDTH;
    public static int BOSS_SIZE_Y = BOSS_SIZE_X * 322 / 486;
    public static int BOSS_POS_X = 0;
    public static int BOSS_POS_Y = - BOSS_SIZE_Y / 3;

    public static float TEANTACLE_X_DIV_Y_RATIO = 411f / 816f;
    public static float TEANTACLE_Y_DIV_X_RATIO = 816f / 411f;
    public static float TENTACLE_HEIGTH_BASE = SCREEN_WIDTH;
    public static float TENTACLE_WIDTH_BASE = TENTACLE_HEIGTH_BASE * TEANTACLE_X_DIV_Y_RATIO * 3f / 4f;
    public static float TENTACLE_Y_BASE = BOSS_SIZE_Y * 5f / 8f + BOSS_POS_Y + TENTACLE_WIDTH_BASE / 2f - TENTACLE_HEIGTH_BASE / 2f;

    public static float LEFT_TENTACLE_OFF_POS_X = GameContants.SCREEN_WIDTH / 10 - GameContants.TENTACLE_HEIGTH_BASE + TENTACLE_HEIGTH_BASE / 2f - TENTACLE_WIDTH_BASE / 2f;
    public static float RIGHT_TENTACLE_OFF_POS_X = -GameContants.SCREEN_WIDTH / 9 + GameContants.SCREEN_WIDTH + TENTACLE_HEIGTH_BASE / 2f - TENTACLE_WIDTH_BASE / 2f;
    public static float LEFT_TENTACLE_ON_POS_X = - TENTACLE_HEIGTH_BASE / 6 + TENTACLE_HEIGTH_BASE / 2f - TENTACLE_WIDTH_BASE / 2f;
    public static float RIGHT_TENTACLE_ON_POS_X = TENTACLE_HEIGTH_BASE / 6 + GameContants.SCREEN_WIDTH - TENTACLE_HEIGTH_BASE + TENTACLE_HEIGTH_BASE / 2f - TENTACLE_WIDTH_BASE / 2f;
    public static float LEFT_TENTACLE_INIT = GameContants.LEFT_TENTACLE_OFF_POS_X - GameContants.SCREEN_WIDTH / 10;
    public static float RIGHT_TENTACLE_INIT = GameContants.RIGHT_TENTACLE_OFF_POS_X + GameContants.SCREEN_WIDTH / 9;

    public static int START_SCORE_TURN = 0;//199;//

    /*public static int POS_KEY_X1 = (int) (GameContants.SCREEN_WIDTH / 2 - (SIZE_KEY_X * 1.5 + 75));
    public static int POS_KEY_X2 = (int) (GameContants.SCREEN_WIDTH / 2 - (SIZE_KEY_X * 0.5));
    public static int POS_KEY_X3 = (int) (GameContants.SCREEN_WIDTH / 2 + (SIZE_KEY_X * 0.5 + 75));

    public static int POS_KEY_Y3 = (int) (SCREEN_HEIGTH * 0.40);
    public static int POS_KEY_Y2 = (int) (SCREEN_HEIGTH * 0.5562);
    public static int POS_KEY_Y1 = (int) (SCREEN_HEIGTH * 0.71244);*/

    public static int COUNT_WIDTH = 75;//(int) (SCREEN_WIDTH * 0.15);
    public static int COUNT_HEIGTH = 75;//(int) (SCREEN_WIDTH * 0.15);

    public static int GAME_OVER_WIDTH = (int) (0.75 * SCREEN_WIDTH);
    public static int GAME_OVER_HEIGTH = (int) (0.75 * SCREEN_HEIGTH);
    public static int GAME_OVER_X = SCREEN_WIDTH / 2 - GAME_OVER_WIDTH / 2;
    public static int GAME_OVER_Y = SCREEN_HEIGTH / 2 - GAME_OVER_HEIGTH/ 2;
    public static int GO_TEXT_WIDTH = GAME_OVER_WIDTH / 2;
    public static int GO_TEXT_HEIGTH = SCREEN_HEIGTH / 20;
    public static int GO_TEXT_X = SCREEN_WIDTH / 2 - GO_TEXT_WIDTH / 2;
    public static int GO_TEXT_Y = GAME_OVER_Y + SCREEN_HEIGTH / 20;

    //The Y position when symbol hits the girl (game over)
    public static int GIRL_Y_LIMIT = SCREEN_HEIGTH * 5 / 6;
    public static int AWESOME_Y_LIMIT = GIRL_Y_LIMIT / 3;
    public static int YEAH_Y_LIMIT = GIRL_Y_LIMIT * 2 / 3;

    public static float TRANSITION_TIME_IN_SECOND = 0.1f;


    /*
            Liste des cas (pièges)
            - Normal, tout va bien
            - Affichage d'un symbole hors des 9
            - Swap de deux touches
            - Changement de symbole sur une touche
            - L'écran penche/glisse et il faut le remettre ne place
            - Le symbole tourne
            - Les images des touches disparassent, seule 3 sont sélectionnables
            - Un bloc de glace apparait sur chaque rouche, pour le casser il faut appuyer 3 fois
            - La case s'affiche en rouge, touche la touche correspondante enlève des points, il faut toucher une des autres.

            */

    public static int MAX_TRAP_WEIGTH = 50;

    public static final int FAMILY_COUNT = 6;
    public static final int FAMILY_SIZE = 10;
    public static final int SYMBOL_COUNT = FAMILY_COUNT * FAMILY_SIZE;

    public static final int SHOW_ADS_MSG = 1;
    public static final int HIDE_ADS_MSG = 0;
    public static final int SHOW_INTERSTITIAL_MSG = 1;

    public static int BOSS_BAR_WIDTH = SCREEN_WIDTH * 15 / 16; 
    public static int BOSS_BAR_HEIGTH = BOSS_BAR_WIDTH * 74 / 700;
    public static float TURN_BETWEEN_BOSS = 200;//10;//

    public static int BOSS_MAX_HP = 100;//12;//

    public static final String ACHIEVEMENT_FIRST_STEP = "achievement_first_steps";
    public static final String ACHIEVEMENT_HALF = "achievement_you_made_the_half_";
    public static final String ACHIEVEMENT_ICE = "achievement_let_it_go_";
    public static final String ACHIEVEMENT_BOSS_ENCOUNTER = "achievement_it_looks_like_a_manga_but_i_dont_remind_which_one_...";
    public static final String ACHIEVEMENT_BOSS_DEFEAT = "achievement_we_did_it_";

    public static final String LEADERBOARD_HITS = "leaderboard_tap_panic_hits";
    public static final String LEADERBOARD_POINTS = "leaderboard_tap_panic_points";
    public static final String LEADERBOARD_BOSS = "leaderboard_tap_panic_boss_paul_le_poulpe_time";
}
