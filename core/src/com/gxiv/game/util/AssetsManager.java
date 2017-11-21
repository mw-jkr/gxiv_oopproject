package com.gxiv.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.gxiv.game.Gxiv;
import com.gxiv.game.screen.MainMenuScreen;
import com.gxiv.game.screen.PlayScreen;

public class AssetsManager {

    public static final AssetManager manager = new AssetManager();


    /* --- Utilities assets --- */

    public static Image logo;
    public static Image groupLogo;
    public static TextureRegionDrawable exitButtonUp;
    public static TextureRegionDrawable exitButtonDown;
    public static Texture white;
    public static Image topLayer;

    /* ------------------------ */

    /* --- Sound assets --- */

    public static Music mainMenuBgm;
    public static Sound gunSound;
    public static Sound clickSound;
    public static Sound flashSound;
    public static Sound startSound;
    public static Sound receiveItem;
    public static Sound itemDrop;
    public static Sound explodeSound;
    public static Sound laser;

    /* -------------------- */


    /* --- MainMenuScreen Assets --- */

    public static Image backgroundMenu;
    public static Image flashEffect;

    public static TextureRegionDrawable startButtonUp;
    public static TextureRegionDrawable startButtonDown;
    public static ImageButton startButton;

    public static TextureRegionDrawable tutorialButtonUp;
    public static TextureRegionDrawable tutorialButtonDown;
    public static ImageButton tutorialButton;

    public static TextureRegionDrawable creditButtonUp;
    public static TextureRegionDrawable creditButtonDown;
    public static ImageButton creditButton;

    public static TextureRegionDrawable exitGameButtonUp;
    public static TextureRegionDrawable exitGameButtonDown;
    public static ImageButton exitGameButton;

    /* ----------------------------- */

    /* --- TutorialPane Assets --- */

    public static Image tutorialPane;
    public static ImageButton exitTutorialButton;

    /* --------------------------- */

    /* --- CreditPane assets --- */

    public static Image creditPane;
    public static ImageButton exitCreditButton;

    /* ------------------------- */

    /* --- PauseScreen assets --- */

    public static Image pauseBackground;
    public static Image pauseMessage;
    public static TextureRegionDrawable soundOnButtonUp;
    public static TextureRegionDrawable soundOnButtonDown;
    public static ImageButton soundOnButton;
    public static TextureRegionDrawable soundOffButtonUp;
    public static TextureRegionDrawable soundOffButtonDown;
    public static ImageButton soundOffButton;
    public static TextureRegionDrawable bgmOnButtonUp;
    public static TextureRegionDrawable bgmOnButtonDown;
    public static ImageButton bgmOnButton;
    public static TextureRegionDrawable bgmOffButtonUp;
    public static TextureRegionDrawable bgmOffButtonDown;
    public static ImageButton bgmOffButton;
    public static TextureRegionDrawable resumeButtonUp;
    public static TextureRegionDrawable resumeButtonDown;
    public static ImageButton resumeButton;
    public static TextureRegionDrawable tutorialButtonPauseUp;
    public static TextureRegionDrawable tutorialButtonPauseDown;
    public static ImageButton tutorialButtonPause;
    public static TextureRegionDrawable creditButtonPauseUp;
    public static TextureRegionDrawable creditButtonPauseDown;
    public static ImageButton creditButtonPause;
    public static TextureRegionDrawable backButtonUp;
    public static TextureRegionDrawable backButtonDown;
    public static ImageButton backButton;

    // Decision pane
    public static Image decisionPane;
    public static TextureRegionDrawable yesButtonUp;
    public static TextureRegionDrawable yesButtonDown;
    public static ImageButton yesButton;
    public static TextureRegionDrawable noButtonUp;
    public static TextureRegionDrawable noButtonDown;
    public static ImageButton noButton;

    /* -------------------------- */

    /* --- Bullet assets --- */

    public static TextureAtlas explode;

    /* --------------------- */

    /* --- TEST ASSETS --- */

    private static String nameMap = "map1.tmx";

    /* ------------------- */

    public static void load() {

        /* --- Load Utilities assets --- */

        logo = new Image(new Texture(Constants.LOGO));
        groupLogo = new Image(new Texture(Constants.GROUP_LOGO));
        clickSound = Gdx.audio.newSound(Gdx.files.internal(Constants.CLICK_SOUND));
        exitButtonDown = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal(Constants.EXIT_BUTTON_ACTIVE))));
        exitButtonUp = new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal(Constants.EXIT_BUTTON))));
        topLayer = new Image(new TextureRegion(white = Gxiv.getTexture()));
        topLayer.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        topLayer.setColor(Color.BLACK);

        /* ----------------------------- */

        /* --- Load sound assets --- */

        flashSound = Gdx.audio.newSound(Gdx.files.internal(Constants.MAIN_MENU_FLASH_SOUND));
        startSound = Gdx.audio.newSound(Gdx.files.internal(Constants.MAIN_MENU_START_SOUND));
        mainMenuBgm = Gdx.audio.newMusic(Gdx.files.internal(Constants.MAIN_MENU_BGM));
        gunSound = Gdx.audio.newSound(Gdx.files.internal(Constants.GUN_SOUND));
        receiveItem = Gdx.audio.newSound(Gdx.files.internal(Constants.RECEIVE_ITEM));
        itemDrop = Gdx.audio.newSound(Gdx.files.internal(Constants.ITEM_DROP));
        explodeSound = Gdx.audio.newSound(Gdx.files.internal(Constants.EXPLODE));
        laser = Gdx.audio.newSound(Gdx.files.internal(Constants.LASER));

        /* ------------------------- */

        /* --- Load Tutorial assets --- */

        tutorialPane = new Image(new Texture(Constants.TUTORIAL_PANE));
        exitTutorialButton = new ImageButton(exitButtonUp, exitButtonDown);

        // Add button listener
        exitTutorialButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                tutorialPane.remove();
                exitTutorialButton.remove();
            }
        });

        /* ---------------------------- */

        /* --- Load Credit assets --- */

        creditPane = new Image(new Texture(Constants.CREDITS_PANE));
        exitCreditButton = new ImageButton(exitButtonUp, exitButtonDown);

        // Add button listener
        exitCreditButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                creditPane.remove();
                exitCreditButton.remove();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }
        });

        /* -------------------------- */

        /* --- Load MainMenuScreen Assets --- */

        backgroundMenu = new Image(new Texture(Constants.MAIN_MENU_BACKGROUND));
        flashEffect = new Image(new Texture(Constants.MAIN_MENU_FLASH_EFFECT));


        /* ---------------------------------- */

        /* --- Start button setup --- */

        startButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_START))));
        startButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_START_ACTIVE))));
        startButton = new ImageButton(startButtonUp, startButtonDown);
        // Start button listener
        startButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MainMenuScreen.stage.addActor(topLayer);
                topLayer.addAction(Actions.sequence(Actions.color(Color.BLACK, 2), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        flashEffect.remove();
                        logo.remove();
                        startButton.remove();
                        tutorialButton.remove();
                        creditButton.remove();
                        exitGameButton.remove();
                        backgroundMenu.remove();
                        StateManager.isExitfromMenu = true;
                    }
                })));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(startSound);
                return true;
            }
        });

        /* -------------------------- */

        /* --- Tutorial button setup --- */

        tutorialButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_TUTORIAL))));
        tutorialButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_TUTORIAL_ACTIVE))));
        tutorialButton = new ImageButton(tutorialButtonUp, tutorialButtonDown);

        // Tutorial Button Listener
        tutorialButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                tutorialPane.setSize(
                        Constants.MAIN_MENU_PANE_WIDTH,
                        Constants.MAIN_MENU_PANE_HEIGHT
                );

                tutorialPane.setPosition(
                        Constants.SCREEN_WIDTH / 2,
                        Constants.SCREEN_HEIGHT / 2, Align.center
                );

                tutorialPane.setOrigin(Align.center);
                MainMenuScreen.stage.addActor(tutorialPane);

                exitTutorialButton.setSize(
                        Constants.EXIT_BUTTON_WIDTH,
                        Constants.EXIT_BUTTON_HEIGHT
                );

                exitTutorialButton.setPosition(265, 565);
                MainMenuScreen.stage.addActor(exitTutorialButton);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }
        });

        /* ----------------------------- */

        /* --- Credit Button Setup --- */

        creditButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_CREDITS))));
        creditButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_CREDITS_ACTIVE))));
        creditButton = new ImageButton(creditButtonUp, creditButtonDown);

        // Credit Button Listener
        creditButton.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                creditPane.setSize(
                        Constants.MAIN_MENU_PANE_WIDTH,
                        Constants.MAIN_MENU_PANE_HEIGHT
                );

                creditPane.setPosition(
                        Constants.SCREEN_WIDTH / 2,
                        Constants.SCREEN_HEIGHT / 2, Align.center
                );

                creditPane.setOrigin(Align.center);
                MainMenuScreen.stage.addActor(creditPane);

                exitCreditButton.setSize(Constants.EXIT_BUTTON_WIDTH, Constants.EXIT_BUTTON_HEIGHT);
                exitCreditButton.setPosition(265, 565);
                MainMenuScreen.stage.addActor(exitCreditButton);
            }

        });

        /* --------------------------- */

        /* --- Exit button setup --- */

        exitGameButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_EXIT))));
        exitGameButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_EXIT_ACTIVE))));
        exitGameButton = new ImageButton(exitGameButtonUp, exitGameButtonDown);

        // Exit game button listener
        exitGameButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }
        });

        /* ------------------------- */

        /* --- Setup pause screen --- */

        pauseBackground = new Image(new Texture(Constants.PAUSE_BACKGROUND));
        pauseMessage = new Image(new Texture(Constants.PAUSE_MESSAGE));

        // Resume button on pause screen
        resumeButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.RESUME_BUTTON))));
        resumeButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.RESUME_BUTTON_ACTIVE))));
        resumeButton = new ImageButton(resumeButtonUp, resumeButtonDown);

        // Tutorial button on pause screen
        tutorialButtonPauseUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_TUTORIAL))));
        tutorialButtonPauseDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_TUTORIAL_ACTIVE))));
        tutorialButtonPause = new ImageButton(tutorialButtonPauseUp, tutorialButtonPauseDown);

        // Credits button on pause screen
        creditButtonPauseUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_CREDITS))));
        creditButtonPauseDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_CREDITS_ACTIVE))));
        creditButtonPause = new ImageButton(creditButtonPauseUp, creditButtonPauseDown);

        // Back to main menu button on pause screen
        backButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.BACK_BUTTON))));
        backButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.BACK_BUTTON_ACTIVE))));
        backButton = new ImageButton(backButtonUp, backButtonDown);

        // Decision when user wants to go back to main menu
        decisionPane = new Image(new Texture(Constants.DECISION_PANE));
        yesButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.YES_BUTTON))));
        yesButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.YES_BUTTON_ACTIVE))));
        yesButton = new ImageButton(yesButtonUp, yesButtonDown);
        noButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.NO_BUTTON))));
        noButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.NO_BUTTON_ACTIVE))));
        noButton = new ImageButton(noButtonUp, noButtonDown);

        // Audio manager
        soundOnButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.SOUND_ON))));
        soundOnButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.SOUND_ON_ACTIVE))));
        soundOnButton = new ImageButton(soundOnButtonUp, soundOnButtonDown);
        soundOffButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.SOUND_OFF))));
        soundOffButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.SOUND_OFF_ACTIVE))));
        soundOffButton = new ImageButton(soundOffButtonUp, soundOffButtonDown);
        bgmOnButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.BGM_ON))));
        bgmOnButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.BGM_ON_ACTIVE))));
        bgmOnButton = new ImageButton(bgmOnButtonUp, bgmOnButtonDown);
        bgmOffButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.BGM_OFF))));
        bgmOffButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.BGM_OFF_ACTIVE))));
        bgmOffButton = new ImageButton(bgmOffButtonUp, bgmOffButtonDown);

        // Add listener

        resumeButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PlayScreen.isPaused = true;
                PlayScreen.pauseHelper = true;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }
        });

        tutorialButtonPause.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                tutorialPane.setSize(
                        Constants.MAIN_MENU_PANE_WIDTH,
                        Constants.MAIN_MENU_PANE_HEIGHT
                );

                tutorialPane.setPosition(
                        Constants.SCREEN_WIDTH / 2,
                        Constants.SCREEN_HEIGHT / 2, Align.center
                );

                tutorialPane.setOrigin(Align.center);
                PlayScreen.stage.addActor(tutorialPane);

                exitTutorialButton.setSize(
                        Constants.EXIT_BUTTON_WIDTH,
                        Constants.EXIT_BUTTON_HEIGHT
                );

                exitTutorialButton.setPosition(265, 565);
                PlayScreen.stage.addActor(exitTutorialButton);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }
        });

        creditButtonPause.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                creditPane.setSize(
                        Constants.MAIN_MENU_PANE_WIDTH,
                        Constants.MAIN_MENU_PANE_HEIGHT
                );

                creditPane.setPosition(
                        Constants.SCREEN_WIDTH / 2,
                        Constants.SCREEN_HEIGHT / 2, Align.center
                );

                creditPane.setOrigin(Align.center);
                PlayScreen.stage.addActor(creditPane);

                exitCreditButton.setSize(Constants.EXIT_BUTTON_WIDTH, Constants.EXIT_BUTTON_HEIGHT);
                exitCreditButton.setPosition(265, 565);
                PlayScreen.stage.addActor(exitCreditButton);
            }

        });

        backButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PlayScreen.stage.addActor(decisionPane);
                PlayScreen.stage.addActor(yesButton);
                PlayScreen.stage.addActor(noButton);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }
        });

        yesButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                StateManager.isBacktoMenu = true;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }
        });

        noButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                decisionPane.remove();
                yesButton.remove();
                noButton.remove();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }
        });

        soundOnButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.soundOn = false;
                soundOnButton.remove();
                PlayScreen.stage.addActor(soundOffButton);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }
        });

        soundOffButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.soundOn = true;
                soundOffButton.remove();
                PlayScreen.stage.addActor(soundOnButton);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }
        });

        bgmOnButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.bgmOn = false;
                PlayScreen.music.muteBgm();
                bgmOnButton.remove();
                PlayScreen.stage.addActor(bgmOffButton);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }
        });

        bgmOffButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.bgmOn = true;
                PlayScreen.music.muteBgm();
                bgmOffButton.remove();
                PlayScreen.stage.addActor(bgmOnButton);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.playSound(clickSound);
                return true;
            }
        });

        /* -------------------------- */

    }

    public static String getNameMap() {
        return nameMap;
    }

    public static void setManager(String nameMap) {
        AssetsManager.nameMap = nameMap;
    }

}
