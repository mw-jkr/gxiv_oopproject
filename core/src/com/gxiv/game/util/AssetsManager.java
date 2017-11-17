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
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.screen.MainMenuScreen;

public class AssetsManager {

    public static final AssetManager manager = new AssetManager();

    /* --- Utilities assets --- */
    public static Image logo;
    public static Image groupLogo;
    public static TextureRegionDrawable exitButtonUp;
    public static TextureRegionDrawable exitButtonDown;
    public static Music mainMenuBgm;
    public static Sound clickSound;
    public static Texture white;
    public static Image topLayer;

    /* --- MainMenuScreen Assets --- */
    public static Image backgroundMenu;
    public static Image flashEffect;
    public static Sound flashSound;
    public static Sound startSound;

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

    /* --- Tutorial Assets --- */
    public static Image tutorialPane;
    public static ImageButton exitTutorialButton;

    /* --- Credit assets --- */
    public static Image creditPane;
    public static ImageButton exitCreditButton;

    /* --- Bullet ---*/
    public static TextureAtlas explode;

    public static void load () {

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

        /* --- Load Tutorial assets --- */
        tutorialPane = new Image(new Texture(Constants.TUTORIAL_SCREEN));
        exitTutorialButton = new ImageButton(exitButtonUp, exitButtonDown);
        // Add button listener
        exitTutorialButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                playSound(clickSound);
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                tutorialPane.remove();
                exitTutorialButton.remove();
            }
        });
        /* ---------------------------- */

        /* --- Load Credit assets --- */
        creditPane = new Image(new Texture(Constants.CREDIT));
        exitCreditButton = new ImageButton(exitButtonUp, exitButtonDown);

        /* Add button listener */
        exitCreditButton.addListener(new ClickListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                creditPane.remove();
                exitCreditButton.remove();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                playSound(clickSound);
                return true;
            }
        });
        /* -------------------------- */

        /* --- Load MainMenuScreen Assets --- */
        backgroundMenu = new Image(new Texture(Constants.MAIN_MENU_BACKGROUND));
        flashEffect = new Image(new Texture(Constants.MAIN_MENU_FLASH_EFFECT));
        flashSound = Gdx.audio.newSound(Gdx.files.internal(Constants.MAIN_MENU_FLASH_SOUND));
        mainMenuBgm = Gdx.audio.newMusic(Gdx.files.internal(Constants.MAIN_MENU_BGM));
        startSound = Gdx.audio.newSound(Gdx.files.internal(Constants.MAIN_MENU_START_SOUND));

        /* Start button setup */
        startButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_START))));
        startButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_START_ACTIVE))));
        startButton = new ImageButton(startButtonUp, startButtonDown);

        /* Start button listener */
        startButton.addListener(new ClickListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                MainMenuScreen.stage.addActor(topLayer);
                topLayer.addAction(Actions.sequence(Actions.color(Color.BLACK,2),Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Gxiv gxiv = ((Gxiv)Gdx.app.getApplicationListener());
                        gxiv.setScreen(new PlayScreen(gxiv));
                    }
                })));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                playSound(startSound);
                return true;
            }
        });

        /* Tutorial button setup */
        tutorialButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_TUTORIAL))));
        tutorialButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_TUTORIAL_ACTIVE))));
        tutorialButton = new ImageButton(tutorialButtonUp, tutorialButtonDown);

        /* Tutorial Button Listener */
        tutorialButton.addListener(new ClickListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                AssetsManager.tutorialPane.setSize(
                        Constants.MAIN_MENU_PANE_WIDTH,
                        Constants.MAIN_MENU_PANE_HEIGHT
                );

                AssetsManager.tutorialPane.setPosition(
                        Constants.SCREEN_WIDTH / 2,
                        Constants.SCREEN_HEIGHT / 2, Align.center
                );

                AssetsManager.tutorialPane.setOrigin(Align.center);
                MainMenuScreen.stage.addActor(tutorialPane);

                AssetsManager.exitTutorialButton.setSize (
                        Constants.EXIT_BUTTON_WIDTH,
                        Constants.EXIT_BUTTON_HEIGHT
                );

                AssetsManager.exitTutorialButton.setPosition(325, 525);
                MainMenuScreen.stage.addActor(exitTutorialButton);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                playSound(clickSound);
                return true;
            }
        });

        /* Credit Button Setup */
        creditButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_CREDITS))));
        creditButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_CREDITS_ACTIVE))));
        creditButton = new ImageButton(creditButtonUp, creditButtonDown);

        /* Credit Button Listener */
        creditButton.addListener(new ClickListener(){

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                playSound(clickSound);
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                AssetsManager.creditPane.setSize(
                        Constants.MAIN_MENU_PANE_WIDTH,
                        Constants.MAIN_MENU_PANE_HEIGHT
                );

                AssetsManager.creditPane.setPosition(
                        Constants.SCREEN_WIDTH / 2,
                        Constants.SCREEN_HEIGHT / 2, Align.center
                );

                AssetsManager.creditPane.setOrigin(Align.center);
                MainMenuScreen.stage.addActor(creditPane);

                AssetsManager.exitCreditButton.setSize(Constants.EXIT_BUTTON_WIDTH, Constants.EXIT_BUTTON_HEIGHT);
                AssetsManager.exitCreditButton.setPosition(325, 525);
                MainMenuScreen.stage.addActor(exitCreditButton);
            }

        });

        /* Exit button setup */
        exitGameButtonUp = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_EXIT))));
        exitGameButtonDown = new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_EXIT_ACTIVE))));
        exitGameButton = new ImageButton(exitGameButtonUp, exitGameButtonDown);

        /* Exit game button listener */
        exitGameButton.addListener(new ClickListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                playSound(clickSound);
                return true;
            }
        });
        /* ---------------------------------- */

        /* Merge test */
        manager.load("audio/music/mario_music.ogg", Music.class);
        manager.load("audio/sounds/coin.wav", Sound.class);
        manager.load("audio/sounds/bump.wav", Sound.class);
        manager.load("audio/sounds/breakblock.wav", Sound.class);
        manager.load("audio/sounds/stomp.wav", Sound.class);
        manager.load("audio/sounds/powerup.wav", Sound.class);
        manager.load("audio/sounds/powerdown.wav", Sound.class);
        manager.load("audio/sounds/powerup_spawn.wav", Sound.class);
        manager.load("audio/sounds/mariodie.wav", Sound.class);
        manager.load("audio/sounds/gun.wav", Sound.class);
        manager.finishLoading();

    }

    public static void playSound (Sound sound) {
        sound.play(0.5f);
    }

}
