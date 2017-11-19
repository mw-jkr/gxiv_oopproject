package com.gxiv.game.sprites.enemies;

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
import com.gxiv.game.util.Constants;


public class Army extends Enemy{
    public enum State {WALKING, DEAD}
    private State currentState;
    private State previousState;
    private Boolean runningLeft;
    private float stateTime;
    private static String nameAtlas;
    private TextureAtlas enemyAtlas;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private TextureAtlas explode;
    private Animation<TextureRegion> exploding;
    private float delay = 0.8f;
    public Army(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        enemyAtlas = new TextureAtlas(Army.getNameAtlas());
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(enemyAtlas.findRegion("1"), 1, 1, Constants.x1, Constants.y1));
        frames.add(new TextureRegion(enemyAtlas.findRegion("2"), 1, 1, Constants.x2, Constants.y2));
        frames.add(new TextureRegion(enemyAtlas.findRegion("3"), 1, 1, Constants.x3, Constants.y3));
        frames.add(new TextureRegion(enemyAtlas.findRegion("4"), 1, 1, Constants.x4, Constants.y4));
        walkAnimation = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
        explode = new TextureAtlas("explode2.pack");
        for(int i=1;i<=8;i++)
            frames.add(new TextureRegion(explode.findRegion("explosion"), i*48, 1, 48, 48));
        exploding = new Animation<TextureRegion>(0.1f, frames);
        runningLeft = true;
        stateTime = 0;
        setToDestroy = false;
        destroyed = false;
        velocity = new Vector2(-0.4f, 0);
        setBounds(getX(), getY(), 32 / Constants.PPM, 32 / Constants.PPM);
    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
//            delay -= dt;
//            if (delay < 0){
                world.destroyBody(b2body);
                destroyed = true;
                Hud.addScore(100);
            setRegion(getFrame(dt));
//            }
        }
        else if(!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 3.5f);
            setRegion(getFrame(dt));
            b2body.setLinearVelocity(velocity);
        }

    }

    private TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case DEAD:
                region = exploding.getKeyFrame(stateTime, true);
                break;
            default:
                region = walkAnimation.getKeyFrame(stateTime, true);
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !runningLeft) && region.isFlipX()){
            region.flip(true, false);
            runningLeft = false;
        }

        else if((b2body.getLinearVelocity().x > 0 || runningLeft) && !region.isFlipX()){
            region.flip(true, false);
            runningLeft = true;
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
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Constants.PPM);
        fdef.filter.categoryBits = Constants.ENEMY_BIT;
        fdef.filter.maskBits = Constants.GROUND_BIT |
                Constants.END_GAME_BIT |
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
        fdef.filter.categoryBits = Constants.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if(!destroyed || stateTime <1){
            super.draw(batch);
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
}
