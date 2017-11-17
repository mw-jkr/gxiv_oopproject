package com.gxiv.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.gxiv.game.Gxiv;
import com.gxiv.game.util.AssetsManager;

public class IntroScreen implements Screen{

    private Stage stage;

    @Override
    public void show() {

        /*Stage Setup*/
        stage = new Stage();

        /*Assets Preparation*/
        AssetsManager.groupLogo.setSize(1268/3,1584/3);
        AssetsManager.groupLogo.setPosition(stage.getWidth()/2, stage.getHeight()/1.75f, Align.center);

        /*Push Assets to the stage*/
        stage.addActor(AssetsManager.groupLogo);
        stage.addActor(AssetsManager.topLayer);

        /*Flash Effect Maker*/
        float delay = 0;
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                AssetsManager.topLayer.addAction(
                        Actions.sequence(
                                Actions.color(Color.BLACK,0),
                                Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        Gxiv gxiv = ((Gxiv)Gdx.app.getApplicationListener());
                                        gxiv.setScreen(gxiv.MainMenuScreen);
                                    }
                                })));
            }
        }, delay);

        AssetsManager.topLayer.addAction(Actions.fadeOut(0));
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}