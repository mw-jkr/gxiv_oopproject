package com.gxiv.game.sprites.enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.gxiv.game.hud.Hud;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.bullet.*;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.Constants;

public class Boss extends Enemy{
    public enum State {WALKING, DEAD}
    private State currentState;
    private State previousState;
    private float stateTime;
    private TextureAtlas enemyAtlas;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private static String nameAtlas;
    private TextureRegion dead;
    private float delay = 0.8f;

    //Fire
    private Array<BossBullet1> bullets;
    private Array<BossBullet2> bullets2;
    private Array<BossBullet3> bullets3;
    private float fireTime;
    public Boss(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        enemyAtlas = new TextureAtlas(getNameAtlas());
        frames = new Array<TextureRegion>();
        bullets = new Array<BossBullet1>();
        bullets2 = new Array<BossBullet2>();
        bullets3 = new Array<BossBullet3>();
        frames.add(new TextureRegion(enemyAtlas.findRegion("1"), 1, 1, Constants.bx1, Constants.by1));
        frames.add(new TextureRegion(enemyAtlas.findRegion("2"), 1, 1, Constants.bx2, Constants.by2));
        frames.add(new TextureRegion(enemyAtlas.findRegion("3"), 1, 1, Constants.bx3, Constants.by3));
        walkAnimation = new Animation<TextureRegion>(0.1f, frames);
        dead = new TextureRegion(enemyAtlas.findRegion("4"), 1, 1, Constants.bx4, Constants.by4);
        frames.clear();
        stateTime = 0;
        setToDestroy = false;
        destroyed = false;

    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            b2body.setActive(false);
            setBounds(getX(), getY(), 32 / Constants.PPM, 20 / Constants.PPM);
            setRegion(getFrame(dt));
            delay -= dt;
            Constants.bshot = 0;
            if (delay < 0){
                world.destroyBody(b2body);
                destroyed = true;
                Hud.addScore(1000);
            }
        }
        else if(!destroyed) {
            setBounds(getX(), getY(), 46 / Constants.PPM, 32 / Constants.PPM);
            setPosition(b2body.getPosition().x - getWidth() / 2.5f, b2body.getPosition().y - getHeight() / 2.5f);
            setRegion(getFrame(dt));
        }
        fireTime += 0.0001;
        for(BossBullet1 bullet: bullets) {
            bullet.update(dt);
            if(bullet.isDestroyed())
                bullets.removeValue(bullet, true);
        }
        for(BossBullet2 bullet: bullets2) {
            bullet.update(dt);
            if(bullet.isDestroyed())
                bullets2.removeValue(bullet, true);
        }
        for(BossBullet3 bullet: bullets3) {
            bullet.update(dt);
            if(bullet.isDestroyed())
                bullets3.removeValue(bullet, true);
        }

    }

    private TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case DEAD:
                region = dead;
                break;
            default:
                region = walkAnimation.getKeyFrame(stateTime, true);
                break;
        }

        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){

        if(setToDestroy)
            return State.DEAD;
        else
            return State.WALKING;
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY() + 10 / Constants.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Constants.PPM);
        fdef.filter.categoryBits = Constants.BOSS_BIT;
        fdef.filter.maskBits = Constants.GROUND_BIT |
                Constants.BOSS_BIT |
                Constants.OBJECT_BIT |
                Constants.PLAYER_BIT |
                Constants.PLAYER_BULLET_BIT|
                Constants.NEXT_MAP_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 13).scl(1/ Constants.PPM);
        vertice[1] = new Vector2(5, 13).scl(1/ Constants.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1/ Constants.PPM);
        vertice[3] = new Vector2(3, 3).scl(1/ Constants.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = Constants.BOSS_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if(!destroyed || stateTime <1){
            super.draw(batch);
        }
        for(BossBullet1 bullet : bullets){
            bullet.draw(batch);
        }
        for(BossBullet2 bullet : bullets2){
            bullet.draw(batch);
        }
        for(BossBullet3 bullet : bullets3){
            bullet.draw(batch);
        }
    }

    public static void setNameAtlas(String name){
        nameAtlas = name;
    }
    public static String getNameAtlas(){
        return nameAtlas;
    }

    @Override
    public void hitOnBullet() {
        setToDestroy = true;
//        AssetsManager.manager.get("audio/sounds/stomp.wav", Sound.class).play();
    }

    public boolean getDestroy(){
        return setToDestroy;
    }


    public void fire(){
        bullets.add(new BossBullet1(screen, b2body.getPosition().x, b2body.getPosition().y));
        AssetsManager.manager.get("audio/sounds/laser.wav", Sound.class).play();
    }
    public void fire2(){
        bullets2.add(new BossBullet2(screen, b2body.getPosition().x, b2body.getPosition().y));
        AssetsManager.manager.get("audio/sounds/laser.wav", Sound.class).play();
    }
    public void fire3(){
        bullets3.add(new BossBullet3(screen, b2body.getPosition().x, b2body.getPosition().y));
        AssetsManager.manager.get("audio/sounds/laser.wav", Sound.class).play();
    }


    public void setFireTime(float fireTime) {
        this.fireTime = fireTime;
    }

    public float getFireTime() {
        return fireTime;
    }

    public void addFireTime(float fireTime) {
        this.fireTime += fireTime;
    }
}
