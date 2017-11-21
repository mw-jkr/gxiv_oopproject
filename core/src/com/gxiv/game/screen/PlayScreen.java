package com.gxiv.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gxiv.game.Gxiv;
import com.gxiv.game.hud.Hud;
import com.gxiv.game.hud.Pause;
import com.gxiv.game.sprites.Player;
import com.gxiv.game.sprites.enemies.Army;
import com.gxiv.game.sprites.enemies.Boss;
import com.gxiv.game.sprites.items.HeartItem;
import com.gxiv.game.sprites.items.Item;
import com.gxiv.game.sprites.items.ItemDef;
import com.gxiv.game.sprites.items.ShieldItem;
import com.gxiv.game.sprites.tileobjects.CeilTurret;
import com.gxiv.game.sprites.tileobjects.GroundTurret;
import com.gxiv.game.tools.B2WorldCreator;
import com.gxiv.game.tools.WorldContactListener;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.AudioManager;
import com.gxiv.game.util.Constants;
import com.gxiv.game.util.StateManager;

import java.util.concurrent.LinkedBlockingDeque;

public class PlayScreen implements Screen {

    /* --- PlayScreen Setup --- */
    public static Stage stage;
    public static AudioManager music;
    /* --- Pause State Logic --- */
    public static boolean isPaused = false;
    public static boolean pauseHelper = false;
    private static Gxiv game;
    private Hud hud;
    private World world;
    private Player player;
    private TiledMap map;
    private Array<Item> items;
    private Viewport gamePort;
    private TextureAtlas atlas;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera gamecam;
    private OrthogonalTiledMapRenderer renderer;
    private LinkedBlockingDeque<ItemDef> itemsToSpawn;
    private B2WorldCreator creator;
    private float timer;
    private int count;
    private int change;

    public PlayScreen(Gxiv game) {

        PlayScreen.game = game;
        stage = new Stage();
        hud = new Hud(game.batch);
        gamecam = new OrthographicCamera();
        music = new AudioManager();
        atlas = new TextureAtlas("GXIV.pack");
        gamePort = new FitViewport(
                Constants.V_WIDTH / Constants.PPM,
                Constants.V_HEIGHT / Constants.PPM, gamecam
        );

        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load(AssetsManager.getNameMap());
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.PPM);

        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        Constants.shot = 0;
        Constants.bshot = 0;
        player = new Player(this);
        timer = 0;
        count = 0;
        change = 0;
        world.setContactListener(new WorldContactListener());
        Constants.worldTimer = 300;
        music.stopMusic();
        music.setMusic(Constants.STAGE_1_BGM);
        music.playMusic();
        timer = 3;
        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingDeque<ItemDef>();

        AssetsManager.pauseBackground.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        AssetsManager.pauseBackground.setPosition(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, Align.center);
        AssetsManager.pauseBackground.setOrigin(Align.center);

        AssetsManager.pauseMessage.setSize(479, 171);
        AssetsManager.pauseMessage.setPosition(Constants.SCREEN_WIDTH / 2, 550, Align.center);
        AssetsManager.pauseMessage.setOrigin(Align.center);

        AssetsManager.resumeButton.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.resumeButton.setPosition(Constants.SCREEN_WIDTH / 2, 400, Align.center);
        AssetsManager.resumeButton.setOrigin(Align.center);

        AssetsManager.tutorialButtonPause.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.tutorialButtonPause.setPosition(Constants.SCREEN_WIDTH / 2, 320, Align.center);
        AssetsManager.tutorialButtonPause.setOrigin(Align.center);

        AssetsManager.creditButtonPause.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.creditButtonPause.setPosition(Constants.SCREEN_WIDTH / 2, 240, Align.center);
        AssetsManager.creditButtonPause.setOrigin(Align.center);

        AssetsManager.backButton.setSize(3508 / 6, 806 / 6);
        AssetsManager.backButton.setPosition(Constants.SCREEN_WIDTH / 2, 160, Align.center);
        AssetsManager.backButton.setOrigin(Align.center);

        AssetsManager.decisionPane.setSize(3508 / 3, 1240 / 3);
        AssetsManager.decisionPane.setPosition(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, Align.center);
        AssetsManager.decisionPane.setOrigin(Align.center);

        AssetsManager.yesButton.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.yesButton.setPosition(Constants.SCREEN_WIDTH / 2, 340, Align.center);
        AssetsManager.yesButton.setOrigin(Align.center);

        AssetsManager.noButton.setSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        AssetsManager.noButton.setPosition(Constants.SCREEN_WIDTH / 2, 260, Align.center);
        AssetsManager.noButton.setOrigin(Align.center);

        AssetsManager.soundOnButton.setSize(1200 / 6, 400 / 6);
        AssetsManager.soundOnButton.setPosition(950, 50, Align.center);
        AssetsManager.soundOnButton.setOrigin(Align.center);

        AssetsManager.soundOffButton.setSize(1200 / 6, 400 / 6);
        AssetsManager.soundOffButton.setPosition(950, 50, Align.center);
        AssetsManager.soundOffButton.setOrigin(Align.center);

        AssetsManager.bgmOnButton.setSize(1200 / 6, 400 / 6);
        AssetsManager.bgmOnButton.setPosition(1150, 50, Align.center);
        AssetsManager.bgmOnButton.setOrigin(Align.center);

        AssetsManager.bgmOffButton.setSize(1200 / 6, 400 / 5);
        AssetsManager.bgmOffButton.setPosition(1150, 50, Align.center);
        AssetsManager.bgmOffButton.setOrigin(Align.center);

    }

    public static Gxiv getGame() {
        return PlayScreen.game;
    }

    public void spawnItem(ItemDef idef) {
        itemsToSpawn.add(idef);
    }

    private void handleSpawningItems() {
        if (!itemsToSpawn.isEmpty()) {
            ItemDef idef = itemsToSpawn.poll();
            if (idef.type == HeartItem.class) {
                items.add(new HeartItem(this, idef.position.x, idef.position.y));
            }
            if (idef.type == ShieldItem.class) {
                items.add(new ShieldItem(this, idef.position.x, idef.position.y));
            }
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private void handleInput(float dt) {
        if (!player.isDead()) {

            /* --- Left UP Key -> Jump --- */
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.getState() != Player.State.JUMPING && player.getState() != Player.State.FALLING && !isPaused) {
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            }

            /* --- Right Key -> Move Right --- */
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2 && !isPaused)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);

            /* --- Left Key -> Move Left --- */
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2 && !isPaused)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

            /* --- Space Key -> Fire --- */
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isPaused && player.getFireTime() >= 1) {
                player.fire();
                Gdx.app.log("FireTime", "" + player.getFireTime());
                player.setFireTime(0);
            }
            /* --- Escape -> Pause --- */
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                pauseHelper = true;
                System.out.println(pauseHelper);
            }

        }
    }

    private void update(float dt) {
        handleInput(dt);
        handleSpawningItems();
        world.step(1 / 60f, 6, 2);
        player.update(dt);
        if (!isPaused) {
            for (Army enemy : creator.getArr()) {
                enemy.update(dt);
                if (!enemy.getDestroy() && enemy.getX() < player.getX() + 224 / Constants.PPM)
                    enemy.b2body.setActive(true);
            }
            for (Boss boss : creator.getBossArray()) {
                boss.update(dt);
                if (!boss.getDestroy() && boss.getX() < player.getX() + 224 / Constants.PPM)
                    boss.b2body.setActive(true);
            }
            for (Item item : items)
                item.update(dt);
            hud.update(dt);

            if (player.currentState != Player.State.DEAD) {
                gamecam.position.x = player.b2body.getPosition().x;
            }
            gamecam.update();
            renderer.setView(gamecam);
        } else {
            new Pause(game.batch);
        }
    }

    @Override
    public void render(float delta) {

        if (!isPaused) {
            update(delta);
        }

        handleInput(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
//        b2dr.render(world, gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Army enemy : creator.getArr())
            enemy.draw(game.batch);
        /* Boss Pattern Fire */
        for (Boss boss : creator.getBossArray()) {
            if (!boss.getDestroy() && boss.getFireTime() >= 1 && (boss.b2body.getPosition().x < player.getX() + 320 / Constants.PPM && !(boss.b2body.getPosition().x < player.getX() - 224 / Constants.PPM) && !isPaused)) {
                if (timer <= 30 || count == 25) {
                    timer += 1;
                    if (change % 6 == 0) {
                        boss.fire();
                    } else if (change % 6 == 1) {
                        boss.fire2();
                    } else if (change % 6 == 2) {
                        boss.fire3();
                    } else if (change % 6 == 3) {
                        boss.fire2();
                        boss.fire();
                    } else if (change % 6 == 4) {
                        boss.fire();
                        boss.fire3();
                    } else if (change % 6 == 5) {
                        boss.fire2();
                        boss.fire3();
                    }
                    count = 0;
                } else {
                    count += 1;
                }
                if (count == 20) {
                    change += 1;
                    timer = 0;
                }
                boss.setFireTime(0);
            } else {
                boss.addFireTime(0.3f);
            }
            if (!isPaused) {
                boss.update(delta);
            }
            boss.draw(game.batch);

        }

        // Ground Turret Shoot System
        for (GroundTurret turret : creator.getGroundTurretArray()) {
            if (!turret.getDestroy() && turret.getFireTime() >= 1 && (turret.body.getPosition().x < player.getX() + 224 / Constants.PPM && !(turret.body.getPosition().x < player.getX() - 224 / Constants.PPM) && !isPaused)) {
                turret.fire();
                turret.setFireTime(0);
            } else {
                turret.addFireTime(0.02f);
            }
            if (!isPaused) {
                turret.update(delta);
            }
            turret.draw(game.batch);
        }

        // Ceil Turret Shoot System
        for (CeilTurret turret : creator.getCeilTurretArray()) {
            if (!turret.getDestroy() && turret.getFireTime() >= 1 && (turret.body.getPosition().x < player.getX() + 224 / Constants.PPM && !(turret.body.getPosition().x < player.getX() - 224 / Constants.PPM))
                    && !isPaused) {
                turret.fire();
                turret.setFireTime(0);
            } else {
                turret.addFireTime(0.02f);
            }
            if (!isPaused) {
                turret.update(delta);
            }
            turret.draw(game.batch);
        }

        /* --- Item Draw --- */
        for (Item item : items) {
            item.draw(game.batch);
        }

        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (player.getNextMapTouch()) {
            game.setScreen(new Summary(game));
            dispose();
        }
        if (gameOver()) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        if (pauseHelper) {
            if (isPaused) {
                Gdx.app.log("State", "Playing");
                pauseHelper = false;
                AssetsManager.pauseBackground.remove();
                AssetsManager.pauseMessage.remove();
                AssetsManager.resumeButton.remove();
                AssetsManager.tutorialButtonPause.remove();
                AssetsManager.creditButtonPause.remove();
                AssetsManager.tutorialPane.remove();
                AssetsManager.exitTutorialButton.remove();
                AssetsManager.creditPane.remove();
                AssetsManager.exitCreditButton.remove();
                AssetsManager.backButton.remove();
                AssetsManager.decisionPane.remove();
                AssetsManager.yesButton.remove();
                AssetsManager.noButton.remove();
                AssetsManager.soundOnButton.remove();
                AssetsManager.soundOffButton.remove();
                AssetsManager.bgmOnButton.remove();
                AssetsManager.bgmOffButton.remove();
                isPaused = false;

            } else {
                Gdx.app.log("State", "Pause");
                pauseHelper = false;
                stage.addActor(AssetsManager.pauseBackground);
                stage.addActor(AssetsManager.pauseMessage);
                stage.addActor(AssetsManager.resumeButton);
                stage.addActor(AssetsManager.tutorialButtonPause);
                stage.addActor(AssetsManager.creditButtonPause);
                stage.addActor(AssetsManager.backButton);
                if (AudioManager.soundOn) {
                    stage.addActor(AssetsManager.soundOnButton);
                } else {
                    stage.addActor(AssetsManager.soundOffButton);
                }
                if (AudioManager.bgmOn) {
                    stage.addActor(AssetsManager.bgmOnButton);
                } else {
                    stage.addActor(AssetsManager.bgmOffButton);
                }
                isPaused = true;
            }
        }

        if (StateManager.isBacktoMenu) {
            StateManager.isBacktoMenu = false;
            PlayScreen.isPaused = false;
            PlayScreen.pauseHelper = false;
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
            music.stopMusic();
            game.setScreen(new MainMenuScreen());
            dispose();
        }

        stage.draw();
        stage.act();

    }

    private boolean gameOver() {
        return player.isDead() && player.getStateTimer() > 3;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
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
        stage.dispose();
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
