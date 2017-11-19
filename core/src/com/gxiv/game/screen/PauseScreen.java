package com.gxiv.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.Constants;

public class PauseScreen implements Screen{

    Stage stage;

    @Override
    public void show() {

        stage = new Stage();

        AssetsManager.pauseBackground.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        AssetsManager.pauseBackground.setPosition(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, Align.center);
        AssetsManager.pauseBackground.setOrigin(Align.center);

        AssetsManager.pauseMessage.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.pauseMessage.setPosition(790, 220);

        AssetsManager.resumeButton.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.resumeButton.setPosition(790, 150);

        AssetsManager.backButton.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.backButton.setPosition(790, 80);

        AssetsManager.resumeButton.addListener(new ClickListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                AssetsManager.playSound(AssetsManager.clickSound);
                return true;
            }
        });

        AssetsManager.backButton.addListener(new ClickListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                // dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                AssetsManager.playSound(AssetsManager.clickSound);
                return true;
            }
        });

        stage.addActor(AssetsManager.pauseBackground);
        stage.addActor(AssetsManager.pauseMessage);
        stage.addActor(AssetsManager.resumeButton);
        stage.addActor(AssetsManager.backButton);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
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
    }

}
