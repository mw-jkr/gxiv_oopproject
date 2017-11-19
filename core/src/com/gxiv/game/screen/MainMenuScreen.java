package com.gxiv.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.gxiv.game.Gxiv;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.Constants;
import com.gxiv.game.util.MusicManager;
import com.gxiv.game.util.StateManager;

public class MainMenuScreen implements Screen {

    public static Stage stage;
    private MusicManager music;

    public MainMenuScreen() {
    }

    @Override
    public void show() {

        /*Stage Setup*/
        stage = new Stage();
        music = new MusicManager();

        /*Assets Preparation*/
        AssetsManager.backgroundMenu.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        AssetsManager.backgroundMenu.setPosition(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, Align.center);
        AssetsManager.backgroundMenu.setOrigin(Align.center);

        AssetsManager.logo.setSize(Constants.LOGO_WIDTH, Constants.LOGO_HEIGHT);
        AssetsManager.logo.setPosition(650, 350);

        AssetsManager.startButton.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.startButton.setPosition(790, 290);

        AssetsManager.tutorialButton.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.tutorialButton.setPosition(790, 220);

        AssetsManager.creditButton.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.creditButton.setPosition(790, 150);

        AssetsManager.exitGameButton.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.exitGameButton.setPosition(790, 80);

        /* Create black screen top layer */
        stage.addActor(AssetsManager.backgroundMenu);
        stage.addActor(AssetsManager.topLayer);

        /* [Action] Remove top layer when fading complete */
        Action removeTopLayer = new Action(){
            @Override
            public boolean act(float delta){
                AssetsManager.topLayer.remove();
                return true;
            }
        };

        
        /* [Action] flash Screen then add buttons */
        Action addComponents = new Action(){
            @Override
            public boolean act(float delta) {
                music.setMusic(Constants.MAIN_MENU_BGM);
                music.playMusic();
                stage.addActor(AssetsManager.flashEffect);
                AssetsManager.playSound(AssetsManager.flashSound);
                stage.addActor(AssetsManager.flashEffect);
                AssetsManager.flashEffect.addAction(Actions.fadeOut(2));
                AssetsManager.flashEffect.setColor(1,1,1,1);
                stage.addActor(AssetsManager.logo);
                stage.addActor(AssetsManager.startButton);
                stage.addActor(AssetsManager.tutorialButton);
                stage.addActor(AssetsManager.creditButton);
                stage.addActor(AssetsManager.exitGameButton);
                return true;
            }
        };

        AssetsManager.topLayer.addAction(Actions.sequence(
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

        if (StateManager.isExitfromMenu) {
            StateManager.isExitfromMenu = false;
            Gxiv gxiv = ((Gxiv)Gdx.app.getApplicationListener());
            gxiv.setScreen(new PlayScreen(gxiv));
        }

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
