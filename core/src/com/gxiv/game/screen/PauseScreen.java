package com.gxiv.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gxiv.game.Gxiv;

public class PauseScreen implements Screen {

    private Stage stage;
    private Texture white;

    @Override
    public void show() {

        stage = new Stage();

        final Image pauseOverlay = new Image(new TextureRegion(white= Gxiv.getTexture()));
        pauseOverlay.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        pauseOverlay.setColor(Color.WHITE);
        stage.addActor(pauseOverlay);

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        white.dispose();
    }
}
