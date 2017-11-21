package com.gxiv.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.AudioManager;
import com.gxiv.game.util.Constants;

public class EndGameScreen implements Screen{
    private Viewport viewport;
    private Stage stage;
    private AudioManager audio;
    private Game game;

    public EndGameScreen(Game game){
        this.game = game;
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT);
        stage = new Stage(viewport, ((Gxiv) game).batch);
        Constants.HP = 20;
        Constants.ARMOR = 20;
        Constants.SCORE = 0;
        Constants.gT = 0;
        Constants.cT = 0;
        Constants.eN = 0;
        Constants.boss = 0;
        Constants.MAP = 1;
        Constants.shot = 0;
        Constants.bshot = 0;
        Constants.fireTime = 0.4f;
        AssetsManager.setManager(String.format("map1.tmx"));
        Constants.STAGE_1_BGM = String.format("audio/music/map1.mp3");
        AssetsManager.setManager("map1.tmx");
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        audio = new AudioManager();
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("Mission Complete", font);
        Label playAgainLabel = new Label("back to the real world!", font);
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
            game.setScreen(new MainMenuScreen());
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
