package com.gxiv.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gxiv.game.screen.IntroScreen;
import com.gxiv.game.screen.MainMenuScreen;
import com.gxiv.game.util.AssetsManager;

public class Gxiv extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public IntroScreen IntroScreen;
    public MainMenuScreen MainMenuScreen;

    public static Texture getTexture(){
        Pixmap pixmap;
        try {
            pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        }
        catch (GdxRuntimeException e) {
            pixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        }
        pixmap.setColor(Color.WHITE);
        pixmap.drawRectangle(0,0,1,1);
        return new Texture(pixmap);
    }

    @Override
    public void create() {
        AssetsManager.load();
        batch = new SpriteBatch();
        font = new BitmapFont();
        IntroScreen = new IntroScreen();
        MainMenuScreen = new MainMenuScreen();
        setScreen(IntroScreen);
    }

}
