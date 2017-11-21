package com.gxiv.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {

    public static Music backgroundMusic;
    public static boolean soundOn = true;
    public static boolean bgmOn = true;

    public static void playSound(Sound sound) {
        if (soundOn) {
            sound.play(0.5f);
        }
    }

    public void setMusic(String constants) {
        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(constants));
    }

    public void playMusic() {
        this.backgroundMusic.setLooping(true);
        this.backgroundMusic.setVolume(0.3f);
        this.backgroundMusic.play();
    }

    public void stopMusic() {
        this.backgroundMusic.stop();
    }

    public void muteBgm() {
        if (bgmOn) {
            this.backgroundMusic.setVolume(0.3f);
        } else {
            this.backgroundMusic.setVolume(0.0f);
        }
    }

}
