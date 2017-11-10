package com.gxiv.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.gxiv.game.Gxiv;
import com.gxiv.game.util.AssetsManager;


public class TutorialScreen implements Screen {

    Stage stage;

    public TutorialScreen() {
    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        AssetsManager.tutorialScreen.setSize(1920, 1080);
        AssetsManager.tutorialScreen.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
        AssetsManager.tutorialScreen.setOrigin(Align.center);

        stage.addActor(AssetsManager.tutorialScreen);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}