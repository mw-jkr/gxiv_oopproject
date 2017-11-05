package com.gxiv.game.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetsManager {
    public static Texture background;
    public static TextureRegion backgroundTexture;

    public static Texture startLoad;
    public static Texture tutorialLoad;
    public static Texture exitLoad;
    public static TextureRegion startButton;
    public static TextureRegion tutorialButton;
    public static TextureRegion exitButton;

    public static Texture startActiveLoad;
    public static Texture tutorialActiveLoad;
    public static Texture exitActiveLoad;
    public static TextureRegion startActiveButton;
    public static TextureRegion tutorialActiveButton;
    public static TextureRegion exitActiveButton;

    public static Music music;
    public static Sound clickSound;

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load () {
        background = loadTexture(Constants.MAIN_MENU_BACKGROUND);
        backgroundTexture = new TextureRegion(background, 0, 0, 1920, 1080);

        startLoad = loadTexture(Constants.MAIN_MENU_START);
        startButton = new TextureRegion(startLoad, 0, 0, 479, 171);

        tutorialLoad = loadTexture(Constants.MAIN_MENU_TUTORIAL);
        tutorialButton = new TextureRegion(tutorialLoad, 0, 0, 479, 171);

        exitLoad = loadTexture(Constants.MAIN_MENU_EXIT);
        exitButton = new TextureRegion(exitLoad, 0, 0, 479, 171);

        startActiveLoad = loadTexture(Constants.MAIN_MENU_START_ACTIVE);
        startActiveButton = new TextureRegion(startLoad, 0, 0, 479, 171);

        tutorialActiveLoad = loadTexture(Constants.MAIN_MENU_TUTORIAL_ACTIVE);
        tutorialActiveButton = new TextureRegion(tutorialLoad, 0, 0, 479, 171);

        exitActiveLoad = loadTexture(Constants.MAIN_MENU_EXIT_ACTIVE);
        exitActiveButton = new TextureRegion(exitLoad, 0, 0, 479, 171);

        clickSound = Gdx.audio.newSound(Gdx.files.internal(Constants.CLICK_SOUND));
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
