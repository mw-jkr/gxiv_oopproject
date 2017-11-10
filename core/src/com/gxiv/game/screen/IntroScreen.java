package com.gxiv.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.gxiv.game.Gxiv;
import com.gxiv.game.util.AssetsManager;

public class IntroScreen implements Screen{

    Stage stage;
    Texture white;

    @Override
    public void show() {

        stage = new Stage();

        AssetsManager.logo.setSize(1948/2,1394/2);
        AssetsManager.logo.setPosition(stage.getWidth()/2, stage.getHeight()/1.75f, Align.center);
        stage.addActor(AssetsManager.logo);

        final Image topLayer = new Image(new TextureRegion(white= Gxiv.getTexture()));
        topLayer.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        topLayer.setColor(Color.BLACK);
        stage.addActor(topLayer);

        stage.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {

                topLayer.addAction(Actions.sequence(Actions.color(Color.BLACK,2),Actions.run(new Runnable() {
                    @Override
                    public void run() {

                        Gxiv gxiv = ((Gxiv)Gdx.app.getApplicationListener());

                        gxiv.setScreen(gxiv.MainMenuScreen);
                    }
                })));

                super.clicked(event, x, y);
            }
        });

        topLayer.addAction(Actions.fadeOut(2));
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
        white.dispose();
    }
}