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

public class Pause implements Disposable {

    public Stage stage;
    private Viewport viewport;
    private Label pause;
    public Pause(SpriteBatch sb){

        viewport = new FillViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        pause = new Label(String.format("%s", "Pause"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(pause).expandX().padTop(10);
        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
