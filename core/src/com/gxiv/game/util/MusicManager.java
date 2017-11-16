package com.gxiv.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {

    public static Music backgroundMusic;

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

}
