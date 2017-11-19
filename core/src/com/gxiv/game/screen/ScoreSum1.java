package com.gxiv.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gxiv.game.Gxiv;
import com.gxiv.game.hud.Hud;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.Constants;

public class ScoreSum1 implements Screen {

    private Stage stage;
    private Viewport viewport;
    private Game game;
    public ScoreSum1(Game game) {
        this.game = game;
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT);
        stage = new Stage(viewport, ((Gxiv) game).batch);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("Summary Score", font);
        Label playAgainLabel = new Label("Click to Play Again", font);
        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10);
        stage.addActor(table);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            Hud.setMap(1);
            AssetsManager.setManager(String.format("MAP%d.tmx",Constants.MAP));
            game.setScreen(new PlayScreen((Gxiv) game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    }
}

