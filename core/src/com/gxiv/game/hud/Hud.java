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
    private static int score;
    private static int hp;
    private static int amr;
    private static Label scoreLabel;
    private static Label hpBar;

    public Hud(SpriteBatch sb){

        int worldTimer = 300;
        timeCount = 0;
        score = 0;
        hp = 10;
        amr = 5;
        /*Stage Setup*/
        Viewport viewport = new FillViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        /*Elements Preparation*/
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        hpBar = new Label(String.format("%02d/%d", hp, amr), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label hpLabel = new Label("HP/AMR", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label marioLabel = new Label("GXIV", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(hpLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(marioLabel).expandX().padTop(10);
        table.row();
        table.add(hpBar).expandX();
        table.add(levelLabel).expandX();
        table.add(scoreLabel).expandX();

        /*Element Enter the stage*/
        stage.addActor(table);
    }

    public void update(float dt){
        timeCount += dt;
//        if(timeCount >= 1){
//            worldTimer--;
//            hpBar.setText(String.format("%03d", worldTimer));
//            timeCount = 0;
//        }

    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));

    }

    public static void updateHP(int value){
        if(amr == 0 && hp > 0)
            hp -= value;
        else if (amr > 0)
            amr -= value;
        hpBar.setText(String.format("%02d/%d", hp, amr));
    }

    public static int getHP(){
        return hp;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
