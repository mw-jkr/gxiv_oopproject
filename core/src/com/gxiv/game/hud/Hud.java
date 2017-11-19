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
import com.gxiv.game.sprites.Player;
import com.gxiv.game.util.Constants;

public class Hud implements Disposable {

    public Stage stage;
    private float timeCount;
    private static Label scoreLabel;
    private static Label hpBar;
    private static Label timeBar;
    private static int worldTimer;
    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;

        /*Stage Setup*/
        Viewport viewport = new FillViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        /*Elements Preparation*/
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        hpBar = new Label(String.format("%02d/%02d", Constants.hp, Constants.amr), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", Constants.score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label hpLabel = new Label("HP/ARMOR", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label levelLabel = new Label(String.format("%d", Constants.map), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
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

        /*Element Enter the stage*/
        stage.addActor(table);
    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            Constants.worldTimer--;
            timeBar.setText(String.format("%03d", Constants.worldTimer));
            timeCount = 0;
        }

    }

    public static void addScore(int value){
        Constants.score += value;
        scoreLabel.setText(String.format("%06d", Constants.score));

    }


    public static void updateHP(int value){
        if(Constants.amr == 0 && Constants.hp > 0)
            Constants.hp -= value;
        else if (Constants.amr > 0)
            Constants.amr -= value;
        hpBar.setText(String.format("%02d/%02d", Constants.hp, Constants.amr));
    }

    public static int getTime(){
        return Constants.worldTimer;
    }


    public static int getHP(){
        return Constants.hp;
    }

    public static void setMap(int value){
        Constants.map += value;
    }



    @Override
    public void dispose() {
        stage.dispose();
    }
}
