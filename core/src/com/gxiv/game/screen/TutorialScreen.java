package com.gxiv.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

        AssetsManager.exitButton.setSize(AssetsManager.exitButton.getWidth(), AssetsManager.exitButton.getHeight());
        AssetsManager.exitButton.setPosition(0, 0);

        AssetsManager.exitButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AssetsManager.playSound(AssetsManager.clickSound);
                Gxiv gxiv = ((Gxiv)Gdx.app.getApplicationListener());
                gxiv.setScreen(new MainMenuScreen());
                return true;
            }
        });

        stage.addActor(AssetsManager.tutorialScreen);
        stage.addActor(AssetsManager.exitButton);
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