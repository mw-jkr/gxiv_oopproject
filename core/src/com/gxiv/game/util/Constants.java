package com.gxiv.game.util;

public class Constants {

    /* --- Utilities --- */

    public static final String LOGO = "MainMenu/logo.png";
    public static final String GROUP_LOGO = "IntroScreen/intro.png";
    public static final String EXIT_BUTTON = "Utilities/exit.png";
    public static final String EXIT_BUTTON_ACTIVE = "Utilities/exit_active.png";

    /* ----------------- */

    /* --- Main menu --- */

    public static final String MAIN_MENU_BACKGROUND = "MainMenu/main_menu_bg.jpg";
    public static final String MAIN_MENU_START = "MainMenu/start.png";
    public static final String MAIN_MENU_TUTORIAL = "MainMenu/tutorial.png";
    public static final String MAIN_MENU_CREDITS = "MainMenu/credits.png";
    public static final String MAIN_MENU_EXIT = "MainMenu/quit.png";
    public static final String MAIN_MENU_START_ACTIVE = "MainMenu/start_active.png";
    public static final String MAIN_MENU_TUTORIAL_ACTIVE = "MainMenu/tutorial_active.png";
    public static final String MAIN_MENU_CREDITS_ACTIVE = "MainMenu/credits_active.png";
    public static final String MAIN_MENU_EXIT_ACTIVE = "MainMenu/quit_active.png";
    public static final String MAIN_MENU_FLASH_EFFECT = "MainMenu/flash.jpg";
    public static final String MAIN_MENU_BGM = "MainMenu/main_menu_bgm.mp3";
    public static final String MAIN_MENU_START_SOUND = "MainMenu/start_sfx.mp3";
    public static final String MAIN_MENU_FLASH_SOUND = "MainMenu/thunder_sfx.wav";

    /* ----------------- */

    /* --- Sound --- */

    public static final String CLICK_SOUND = "MainMenu/button_sfx.wav";
    public static final String STAGE_1_BGM = "stage_1_bgm.mp3";

    /* ------------- */

    /* --- Tutorial & Credits pane --- */

    public static final String TUTORIAL_PANE = "Tutorial/tutorial_pane.png";
    public static final String CREDITS_PANE = "Credits/credits_pane.png";

    /* ------------------------------- */

    /* --- Height & Width of assets on main menu --- */

    public static final float SCREEN_WIDTH = 1280;
    public static final float SCREEN_HEIGHT = 720;
    public static final float LOGO_WIDTH = 1948/4;
    public static final float LOGO_HEIGHT = 1394/4;
    public static final float MENU_BUTTON_WIDTH = 479/2;
    public static final float MENU_BUTTON_HEIGHT = 171/2;
    public static final float MAIN_MENU_PANE_WIDTH = 3508 / 4;
    public static final float MAIN_MENU_PANE_HEIGHT = 2480 / 4;
    public static final float EXIT_BUTTON_WIDTH = 533/10;
    public static final float EXIT_BUTTON_HEIGHT = 536/10;

     /* ---------------------- */

    /* --- Game system Constants --- */

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 225;
    public static final float PPM = 100;
    public static final short NOTHING_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short DESTROY_BIT = 16;
    public static final short OBJECT_BIT = 32;
    public static final short ENEMY_BIT = 64;
    public static final short ENEMY_HEAD_BIT = 128;
    public static final short ITEM_BIT = 256;
    public static final short BOSS_BIT = 512;
    public static final short PLAYER_BULLET_BIT = 1024;
    public static final short GROUND_BULLET_BIT = 2048;
    public static final short CEIL_BULLET_BIT = 4096;
    public static final short CEIL_TURRET_BIT = 8192;
    public static final short GROUND_TURRET_BIT = 16384;
    public static final short NEXT_MAP_BIT = 16385;

    /* ----------------------------- */

    /* --- HUD --- */

    public static int HP = 10;
    public static int ARMOR = 10;
    public static int SCORE = 0;
    public static int MAP = 1;

    /* ----------- */

    /* --- PauseScreen --- */

    public static final String PAUSE_BACKGROUND = "PauseScreen/pause_screen.png";
    public static final String PAUSE_MESSAGE = "PauseScreen/pause_msg.png";
    public static final String RESUME_BUTTON = "PauseScreen/resume.png";
    public static final String RESUME_BUTTON_ACTIVE = "PauseScreen/resume_active.png";
    public static final String BACK_BUTTON = "PauseScreen/back.png";
    public static final String BACK_BUTTON_ACTIVE = "PauseScreen/back_active.png";

    /* ------------------- */

}
