package com.gxiv.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class AssetsManager {

    public static Music music;
    public static Sound clickSound;

    public static Texture dropImage;
    public static Texture player;
    public static Sound dropSound;
    public static Music rainMusic;

    public static Image backgroundMenu;
    public static Image logo;
    public static Image startMenu;
    public static Image tutorialMenu;
    public static Image exitMenu;
    public static Image activeStart;
    public static Image activeTutorial;
    public static Image activeExit;
    public static Image tutorialScreen;

    public static void load () {

        logo = new Image(new Texture(Constants.LOGO));
        backgroundMenu = new Image(new Texture(Constants.MAIN_MENU_BACKGROUND));
        startMenu = new Image(new Texture(Constants.MAIN_MENU_START));
        tutorialMenu = new Image(new Texture(Constants.MAIN_MENU_TUTORIAL));
        exitMenu = new Image(new Texture(Constants.MAIN_MENU_EXIT));

        activeStart = new Image(new Texture(Constants.MAIN_MENU_START_ACTIVE));
        activeTutorial = new Image(new Texture(Constants.MAIN_MENU_TUTORIAL_ACTIVE));
        activeExit = new Image(new Texture(Constants.MAIN_MENU_EXIT_ACTIVE));

        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        player = new Texture(Gdx.files.internal("player.png"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        clickSound = Gdx.audio.newSound(Gdx.files.internal(Constants.CLICK_SOUND));

        tutorialScreen = new Image(new Texture(Constants.TUTORIAL_SCREEN));

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
