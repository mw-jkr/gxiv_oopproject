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

public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;
    private int worldTimer;
    private float timeCount;
    private static int score;

    private Label hpBar;
    private static Label scoreLabel;
    private Label hpLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label marioLabel;
    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FillViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        hpBar = new Label(String.format("10/5", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        hpLabel = new Label("HP/AMR", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("GXIV", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(hpLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(marioLabel).expandX().padTop(10);
        table.row();
        table.add(hpBar).expandX();
        table.add(levelLabel).expandX();
        table.add(scoreLabel).expandX();

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

    @Override
    public void dispose() {
        stage.dispose();
    }
}
