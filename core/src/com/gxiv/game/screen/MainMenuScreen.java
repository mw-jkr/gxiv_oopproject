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

    public static Stage stage;
    Texture white;

    public MainMenuScreen() {
    }

    @Override
    public void show() {

        stage = new Stage();

        AssetsManager.backgroundMenu.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        AssetsManager.backgroundMenu.setPosition(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, Align.center);
        AssetsManager.backgroundMenu.setOrigin(Align.center);

        AssetsManager.logo.setSize(Constants.LOGO_WIDTH, Constants.LOGO_HEIGHT);
        AssetsManager.logo.setPosition(700, 300);

        AssetsManager.startButton.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.startButton.setPosition(850, 230);

        AssetsManager.tutorialButton.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.tutorialButton.setPosition(850, 170);

        AssetsManager.creditButton.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.creditButton.setPosition(850, 110);

        AssetsManager.exitGameButton.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.exitGameButton.setPosition(850, 50);

        // Create black screen top layer
        final Image topLayer = new Image(new TextureRegion(white = Gxiv.getTexture()));
        topLayer.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        topLayer.setColor(Color.BLACK);

        stage.addActor(AssetsManager.backgroundMenu);
        stage.addActor(topLayer);

        // [Action] Remove top layer when fading complete
        Action removeTopLayer = new Action(){
            @Override
            public boolean act(float delta){
                topLayer.remove();
                return true;
            }
        };
        
        // [Action] flash Screen then add buttons
        Action addComponents = new Action(){
            @Override
            public boolean act(float delta) {
                AssetsManager.playMusic(AssetsManager.mainMenuBgm);
                AssetsManager.playSound(AssetsManager.flashSound);
                stage.addActor(AssetsManager.flashEffect);
                AssetsManager.flashEffect.addAction(Actions.fadeOut(2));
                stage.addActor(AssetsManager.logo);
                stage.addActor(AssetsManager.startButton);
                stage.addActor(AssetsManager.tutorialButton);
                stage.addActor(AssetsManager.creditButton);
                stage.addActor(AssetsManager.exitGameButton);
                return true;
            }
        };

        topLayer.addAction(Actions.sequence(
                Actions.fadeOut(2)
                , addComponents
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
