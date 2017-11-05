package com.gxiv.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.gxiv.game.Gxiv;
import com.gxiv.game.Util.AssetsManager;

public class MainMenuScreen extends ScreenAdapter{

    private final Gxiv game;
    private SpriteBatch batch;
    OrthographicCamera guiCam;
    Rectangle playBounds;
    Rectangle tutorialBounds;
    Rectangle exitBounds;
    Vector3 touchPoint;

    public MainMenuScreen(final Gxiv game) {
        this.game = game;
        batch = new SpriteBatch();

        guiCam = new OrthographicCamera(1920, 1080);
        guiCam.position.set(1920 / 2, 1080 / 2, 0);

        playBounds = new Rectangle(1200, 400, 479, 171);
        tutorialBounds = new Rectangle(1200, 250, 479, 171);
        exitBounds = new Rectangle(1200, 100, 479, 171);

        touchPoint = new Vector3();
        AssetsManager.loadMusic();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }

    public void update () {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (playBounds.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("PLAY");
                AssetsManager.playSound(AssetsManager.clickSound);
                game.setScreen(new GameScreen(game));
                return;
            }
            if (tutorialBounds.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("TUTORIAL");
                AssetsManager.playSound(AssetsManager.clickSound);
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

    public void draw () {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        batch.setProjectionMatrix(guiCam.combined);

        batch.disableBlending();
        batch.begin();
        batch.draw(AssetsManager.backgroundTexture, 0, 0, 1920, 1080);
        batch.end();

        batch.enableBlending();
        batch.begin();
        batch.draw(AssetsManager.startButton, 1200, 400, 479, 171);
        batch.draw(AssetsManager.tutorialButton, 1200, 250, 479, 171);
        batch.draw(AssetsManager.exitButton, 1200, 100, 479, 171);
        batch.end();
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

    }
}


