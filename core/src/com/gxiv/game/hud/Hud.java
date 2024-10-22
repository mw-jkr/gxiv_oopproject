package com.gxiv.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gxiv.game.util.Constants;

public class Hud implements Disposable {

    private static Label scoreLabel;
    private static Label hpBar;
    private static Label timeBar;
    private static int worldTimer;
    public Stage stage;
    private float timeCount;

    public Hud(SpriteBatch sb) {

        worldTimer = 300;
        timeCount = 0;

        /* --- Stage Setup --- */
        Viewport viewport = new FillViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        /* --- Elements Preparation --- */
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        hpBar = new Label(String.format("%02d/%02d", Constants.HP, Constants.ARMOR), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", Constants.SCORE), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label hpLabel = new Label("HP/SHIELD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label levelLabel = new Label(String.format("%d", Constants.MAP), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label gxivLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeBar = new Label(String.format("%03d", Constants.worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(hpLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(gxivLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(hpBar).expandX();
        table.add(levelLabel).expandX();
        table.add(scoreLabel).expandX();
        table.add(timeBar).expandX();

        /* --- Element Enter the stage --- */
        stage.addActor(table);

    }

    public static void addScore(int value) {
        Constants.SCORE += value;
        scoreLabel.setText(String.format("%06d", Constants.SCORE));
    }

    public static void updateHP(int value) {
        if (Constants.ARMOR == 0 && Constants.HP > 0)
            Constants.HP -= value;
        else if (Constants.ARMOR > 0)
            Constants.ARMOR -= value;
        hpBar.setText(String.format("%02d/%02d", Constants.HP, Constants.ARMOR));
    }

    public static void updateAMR(int value) {
        Constants.ARMOR -= value;
        hpBar.setText(String.format("%02d/%02d", Constants.HP, Constants.ARMOR));
    }

    public static void addHP(int value) {
        Constants.HP -= value;
        hpBar.setText(String.format("%02d/%02d", Constants.HP, Constants.ARMOR));
    }

    public static int getTime() {
        return Constants.worldTimer;
    }

    public static int getHP() {
        return Constants.HP;
    }

    public static void setMap(int value) {
        Constants.MAP += value;
    }

    public void update(float dt) {
        timeCount += dt;
        if (timeCount >= 1) {
            Constants.worldTimer--;
            timeBar.setText(String.format("%03d", Constants.worldTimer));
            timeCount = 0;
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
