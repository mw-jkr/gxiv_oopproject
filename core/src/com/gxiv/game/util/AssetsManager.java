package com.gxiv.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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

    /* ---Game logo--- */
    public static Image logo;

    /* ---[TEST] GameScreen Assets--- */
    public static Music music;
    public static Sound clickSound;
    public static Texture dropImage;
    public static Texture player;
    public static Sound dropSound;
    public static Music rainMusic;

    /* ---MainMenuScreen Assets--- */
    public static Image backgroundMenu;
    public static Image flashEffect;
    public static Sound flashSound;

    public static TextureRegionDrawable startButtonUp;
    public static TextureRegionDrawable startButtonDown;
    public static ImageButton startButton;

    public static TextureRegionDrawable tutorialbuttonUp;
    public static TextureRegionDrawable tutorialbuttonDown;
    public static ImageButton tutorialButton;

    public static TextureRegionDrawable exitgamebuttonUp;
    public static TextureRegionDrawable exitgamebuttonDown;
    public static ImageButton exitgameButton;

    /* ---TutorialScreen Assets--- */
    public static Image tutorialScreen;
    public static Image exitButton;

    public static void load () {

        /* ---Load logo--- */
        logo = new Image(new Texture(Constants.LOGO));

        /* ---Load [TEST] GameScreen Assets--- */
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        player = new Texture(Gdx.files.internal("player.png"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        clickSound = Gdx.audio.newSound(Gdx.files.internal(Constants.CLICK_SOUND));

        /* --Load TutorialScreen Assets--- */
        tutorialScreen = new Image(new Texture(Constants.TUTORIAL_SCREEN));
        exitButton = new Image(new Texture(Constants.EXIT_BUTTON));

        /* ---Load MainMenuScreen Assets--- */
        backgroundMenu = new Image(new Texture(Constants.MAIN_MENU_BACKGROUND));
        flashEffect = new Image(new Texture(Constants.MAIN_MENU_FLASH_EFFECT));
        flashSound = Gdx.audio.newSound(Gdx.files.internal(Constants.FLASH_SOUND));

        startButtonUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_START))));
        startButtonDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_START_ACTIVE))));
        startButton = new ImageButton(startButtonUp, startButtonDown);

        tutorialbuttonUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_TUTORIAL))));
        tutorialbuttonDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_TUTORIAL_ACTIVE))));
        tutorialButton = new ImageButton(tutorialbuttonUp, tutorialbuttonDown);

        exitgamebuttonUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_EXIT))));
        exitgamebuttonDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(Constants.MAIN_MENU_EXIT_ACTIVE))));
        exitgameButton = new ImageButton(exitgamebuttonUp, exitgamebuttonDown);

        /* ---Set listener on main menu button--- */

        // start button
        startButton.addListener(new ClickListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gxiv gxiv = ((Gxiv)Gdx.app.getApplicationListener());
                gxiv.setScreen(new PlayScreen(gxiv));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                playSound(clickSound);
                return true;
            }
        });

        // tutorial button
        tutorialButton.addListener(new ClickListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                tutorialScreen.setSize(1920/2, 1080/2);
                tutorialScreen.setPosition(MainMenuScreen.stage.getWidth() / 2, MainMenuScreen.stage.getHeight() / 2, Align.center);
                tutorialScreen.setOrigin(Align.center);

                exitButton.setSize(Constants.TUTORIAL_EXIT_WIDTH, Constants.TUTORIAL_EXIT_HEIGHT);
                exitButton.setPosition(MainMenuScreen.stage.getWidth() / 2, MainMenuScreen.stage.getHeight() / 2, Align.topLeft);
                exitButton.setOrigin(Align.topLeft);

                exitButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        playSound(clickSound);
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        tutorialScreen.remove();
                        exitButton.remove();
                    }
                });

                MainMenuScreen.stage.addActor(tutorialScreen);
                MainMenuScreen.stage.addActor(exitButton);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                playSound(clickSound);
                return true;
            }
        });

        // exit game button
        exitgameButton.addListener(new ClickListener(){
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
        manager.finishLoading();

    }

    public static void playSound (Sound sound) {
        sound.play(0.5f);
    }

    public static void loadMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal(Constants.GAME_MUSIC));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }
}
