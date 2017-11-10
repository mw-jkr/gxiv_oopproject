package com.gxiv.game.screen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.gxiv.game.Gxiv;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.Constants;

public class MainMenuScreen extends ApplicationAdapter implements Screen {

    private SpriteBatch batch;
    OrthographicCamera guiCam;
    Rectangle playBounds;
    Rectangle tutorialBounds;
    Rectangle exitBounds;
    Vector3 touchPoint;

    Stage stage;
    Texture white;

    public MainMenuScreen() {
        batch = new SpriteBatch();

        guiCam = new OrthographicCamera(1920, 1080);
        guiCam.position.set(1920 / 2, 1080 / 2, 0);

        playBounds = new Rectangle(1200, 385, Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT);
        tutorialBounds = new Rectangle(1200, 290, Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT);
        exitBounds = new Rectangle(1200, 185, Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT);

        touchPoint = new Vector3();
        AssetsManager.loadMusic();
    }

    @Override
    public void show() {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        AssetsManager.backgroundMenu.setSize(1920, 1080);
        AssetsManager.backgroundMenu.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
        AssetsManager.backgroundMenu.setOrigin(Align.center);

        AssetsManager.logo.setSize(1948/3, 1394/3);
        AssetsManager.logo.setPosition(1050, 475);
        AssetsManager.logo.setOrigin(Align.center);

        AssetsManager.startMenu.setSize(Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT);
        AssetsManager.startMenu.setPosition(1200, 385);
        AssetsManager.startMenu.setOrigin(Align.center);

        AssetsManager.tutorialMenu.setSize(Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT);
        AssetsManager.tutorialMenu.setPosition(1200, 290);
        AssetsManager.tutorialMenu.setOrigin(Align.center);

        AssetsManager.exitMenu.setSize(Constants.MAIN_MENU_BUTTON_WIDTH, Constants.MAIN_MENU_BUTTON_HEIGHT);
        AssetsManager.exitMenu.setPosition(1200, 185);
        AssetsManager.exitMenu.setOrigin(Align.center);

        stage.addActor(AssetsManager.backgroundMenu);
        stage.addActor(AssetsManager.logo);
        stage.addActor(AssetsManager.startMenu);
        stage.addActor(AssetsManager.tutorialMenu);
        stage.addActor(AssetsManager.exitMenu);

        System.out.println(stage.getWidth());
        System.out.println(stage.getHeight());

        final Image topLayer = new Image(new TextureRegion(white = Gxiv.getTexture()));
        topLayer.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        topLayer.setColor(Color.BLACK);
        stage.addActor(topLayer);

        topLayer.addAction(Actions.fadeOut(2));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act();

        guiCam.update();
        batch.setProjectionMatrix(guiCam.combined);

        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (playBounds.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("PLAY");
                AssetsManager.playSound(AssetsManager.clickSound);
                Gxiv gxiv = ((Gxiv)Gdx.app.getApplicationListener());
                gxiv.setScreen(new GameScreen(gxiv));
                return;
            }
            if (tutorialBounds.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("TUTORIAL");
                AssetsManager.playSound(AssetsManager.clickSound);
                Gxiv gxiv = ((Gxiv)Gdx.app.getApplicationListener());
                gxiv.setScreen(new TutorialScreen());
                return;
            }
            if (exitBounds.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("EXIT");
                AssetsManager.playSound(AssetsManager.clickSound);
                Gdx.app.exit();
                return;
            }
        }
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


