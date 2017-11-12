package com.gxiv.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.gxiv.game.Gxiv;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.Constants;

public class MainMenuScreen implements Screen {

    Stage stage;
    Texture white;

    public MainMenuScreen() {
        AssetsManager.loadMusic();
    }

    @Override
    public void show() {

        stage = new Stage();

        AssetsManager.backgroundMenu.setSize(1920, 1080);
        AssetsManager.backgroundMenu.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
        AssetsManager.backgroundMenu.setOrigin(Align.center);

        AssetsManager.logo.setSize(1948/3, 1394/3);
        AssetsManager.logo.setPosition(1100, 450);

        AssetsManager.startButton.setSize(AssetsManager.startButton.getWidth()/2, AssetsManager.startButton.getHeight()/2);
        AssetsManager.startButton.setPosition(1325, 380);

        AssetsManager.tutorialButton.setSize(AssetsManager.tutorialButton.getWidth()/2, AssetsManager.tutorialButton.getHeight()/2);
        AssetsManager.tutorialButton.setPosition(1325, 300);

        AssetsManager.exitgameButton.setSize(AssetsManager.exitgameButton.getWidth()/2, AssetsManager.exitgameButton.getHeight()/2);
        AssetsManager.exitgameButton.setPosition(1325, 210);

        final Image topLayer = new Image(new TextureRegion(white = Gxiv.getTexture()));
        topLayer.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        topLayer.setColor(Color.BLACK);

        stage.addActor(AssetsManager.backgroundMenu);
        stage.addActor(topLayer);

        /* [Action] Remove top layer when fading complete */
        Action removeTopLayer = new Action(){
            @Override
            public boolean act(float delta){
                topLayer.remove();
                return true;
            }
        };
        
        /* [Action] flash Screen then add buttons */
        Action flashButton = new Action(){
            @Override
            public boolean act(float delta) {
                AssetsManager.playSound(AssetsManager.flashSound);
                stage.addActor(AssetsManager.flashEffect);
                AssetsManager.flashEffect.addAction(Actions.fadeOut(2));
                stage.addActor(AssetsManager.logo);
                stage.addActor(AssetsManager.startButton);
                stage.addActor(AssetsManager.tutorialButton);
                stage.addActor(AssetsManager.exitgameButton);
                return true;
            }
        };

        topLayer.addAction(Actions.sequence(
                Actions.fadeOut(1)
                , flashButton
                , removeTopLayer
        ));

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act();
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
