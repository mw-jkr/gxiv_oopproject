package com.gxiv.game.screen;

import java.util.Iterator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.gxiv.game.Gxiv;
import com.gxiv.game.util.AssetsManager;

public class GameScreen implements Screen {

    final Gxiv game;

    Texture dropImage;
    Texture player;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;

    public GameScreen(final Gxiv game) {
        this.game = game;

        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        player = new Texture(Gdx.files.internal("player.png"));

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 0;
        bucket.width = 64;
        bucket.height = 64;

        raindrops = new Array<Rectangle>();
        spawnRaindrop();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        game.batch.draw(AssetsManager.player, bucket.x, bucket.y, bucket.width, bucket.height);
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(AssetsManager.dropImage, raindrop.x, raindrop.y);
        }
        game.batch.end();

        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            System.out.println("JUMPING !");
        }

        if (Gdx.input.isKeyPressed(Keys.LEFT))
            bucket.x -= 1000 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            bucket.x += 1000 * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0)
                iter.remove();
            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        player.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

}