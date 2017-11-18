package com.gxiv.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gxiv.game.hud.Hud;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.bullet.Revolver;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.Constants;

public class Player extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD, SHOOTING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private float fireTime;

    private Animation<TextureRegion> gxivRun;
    private TextureRegion gxivStand;
    private TextureRegion gxivJump;
    private TextureRegion gxivDead;
    private PlayScreen screen;

    private float stateTimer;
    private boolean runningRight;
    private boolean marioIsDead;
    private Array<Revolver> bullets;

    public Player(PlayScreen screen){

        this.world = screen.getWorld();
        this.screen = screen;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        /*Running Animation*/
        gxivStand = new TextureRegion(screen.getAtlas().findRegion("GXIV"), 193, 1, 22, 32);

        /*Stand Animation*/
        frames.add(new TextureRegion(screen.getAtlas().findRegion("GXIV"), 73, 1, 22, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("GXIV"), 97, 1, 22, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("GXIV"), 121, 1, 22, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("GXIV"), 145, 1, 22, 32));
        gxivRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        gxivJump = new TextureRegion(screen.getAtlas().findRegion("GXIV"), 169, 1, 22, 32);
        gxivDead = new TextureRegion(screen.getAtlas().findRegion("GXIV"), 96, 0, 16, 16);
        defineMario();
        bullets = new Array<Revolver>();
        setBounds(0, 0, 22 / Constants.PPM, 32 / Constants.PPM);
        setRegion(gxivStand);

    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
        if(Hud.getHP() == 0){
            marioIsDead = true;
        }
        for(Revolver bullet: bullets) {
            bullet.update(dt);
            if(bullet.isDestroyed())
                bullets.removeValue(bullet, true);
        }
    }

    public boolean isDead(){
        return marioIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    private TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case JUMPING:
                fireTime += 0.04;
                region = gxivJump;
                break;
            case RUNNING:
                fireTime += 0.04;
                region = gxivRun.getKeyFrame(stateTimer, true);
                break;
            case SHOOTING:
                region = gxivStand;
                break;
            case FALLING:
                fireTime += 0.04;
                region = gxivJump;
                break;
            case STANDING:
            default:
                fireTime += 0.04;
                region = gxivStand;
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){

        if(marioIsDead)
            return State.DEAD;
        else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return  State.JUMPING;
        else if(b2body.getLinearVelocity().y <0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            return State.SHOOTING;
        }
        else
            return State.STANDING;
    }


    public float getFireTime(){
        return fireTime;
    }

    public void setFireTime(float ft){
        fireTime = ft;
    }

    private void defineMario(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(450/Constants.PPM, 32/Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Constants.PPM);
        fdef.filter.categoryBits = Constants.PLAYER_BIT;
        fdef.filter.maskBits = Constants.GROUND_BIT |
                Constants.COIN_BIT |
                Constants.BRICK_BIT |
                Constants.ENEMY_BIT |
                Constants.OBJECT_BIT |
                Constants.ENEMY_HEAD_BIT |
                Constants.ITEM_BIT |
                Constants.GROUND_TURRET_BIT |
                Constants.CEIL_TURRET_BIT |
                Constants.ENEMY_BULLET_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        fdef.filter.categoryBits = Constants.PLAYER_BIT;
        fdef.filter.maskBits = Constants.GROUND_BIT |
                Constants.COIN_BIT |
                Constants.BRICK_BIT |
                Constants.ENEMY_BIT |
                Constants.OBJECT_BIT |
                Constants.ENEMY_HEAD_BIT |
                Constants.ITEM_BIT |
                Constants.GROUND_TURRET_BIT |
                Constants.CEIL_TURRET_BIT |
                Constants.ENEMY_BULLET_BIT;
        shape.setPosition(new Vector2(0, -7.8f / Constants.PPM));
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-8f, 15).scl(1/ Constants.PPM);
        vertice[1] = new Vector2(8f, 15).scl(1/ Constants.PPM);
        vertice[2] = new Vector2(-8f, -13.5f).scl(1/ Constants.PPM);
        vertice[3] = new Vector2(8f, -13.5f).scl(1/ Constants.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.filter.categoryBits = Constants.PLAYER_BIT;
        fdef.filter.maskBits = Constants.GROUND_BIT |
                Constants.COIN_BIT |
                Constants.BRICK_BIT |
                Constants.ENEMY_BIT |
                Constants.OBJECT_BIT |
                Constants.ENEMY_HEAD_BIT |
                Constants.ITEM_BIT |
                Constants.GROUND_TURRET_BIT |
                Constants.CEIL_TURRET_BIT |
                Constants.ENEMY_BULLET_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void fire(){
        bullets.add(new Revolver(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight));
        AssetsManager.manager.get("audio/sounds/gun.wav", Sound.class).play();
        Gdx.app.log("fire", ""+ bullets);
    }

    public void draw(Batch batch){
        super.draw(batch);
        for(Revolver bullet : bullets){
                bullet.draw(batch);
        }
    }
}
