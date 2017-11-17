package com.gxiv.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.bullet.FireBall;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.Constants;

public class Mario extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD, SHOOTING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private float fireTime;
    private Animation<TextureRegion> marioRun;
    private TextureRegion marioStand;
    private Animation<TextureRegion> gxivShooting;
    private TextureRegion marioJump;
    private TextureRegion gxivShoot;
    private TextureRegion marioDead;
    private TextureRegion bigMarioStand;
    private TextureRegion bigMarioJump;
    private Animation<TextureRegion> bigMarioRun;
    private Animation<TextureRegion> growMario;
    private PlayScreen screen;

    private float stateTimer;
    private boolean runningRight;
    private boolean marioIsBig;
    private boolean runGrowAnimation;
    private boolean timeToDefineBigMario;
    private boolean timeToReDefineMario;
    private boolean marioIsDead;
    private Array<FireBall> fireballs;

    public Mario(PlayScreen screen){
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        this.screen = screen;

        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        //run

        marioStand = new TextureRegion(screen.getAtlas().findRegion("GXIV"), 193, 1, 22, 32);
        //stand

        frames.add(new TextureRegion(screen.getAtlas().findRegion("GXIV"), 73, 1, 22, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("GXIV"), 97, 1, 22, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("GXIV"), 121, 1, 22, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("GXIV"), 145, 1, 22, 32));
        marioRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

//        for(int i = 1;i < 4;i++)
//            frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), i * 16, 0, 16, 32));
//        bigMarioRun = new Animation<TextureRegion>(0.1f, frames);
//        frames.clear();

//        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0, 16, 32));
//        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
//        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0, 16, 32));
//        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
//        growMario = new Animation<TextureRegion>(0.2f, frames);

        marioJump = new TextureRegion(screen.getAtlas().findRegion("GXIV"), 169, 1, 22, 32);
//        bigMarioJump = new TextureRegion(screen.getAtlas().findRegion("big_mario"), 80, 0, 16, 32);

//        marioStand = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 0, 0, 16, 16);
//        bigMarioStand = new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32);

        marioDead = new TextureRegion(screen.getAtlas().findRegion("GXIV"), 96, 0, 16, 16);
        defineMario();
        fireballs = new Array<FireBall>();
        setBounds(0, 0, 22 / Constants.PPM, 32 / Constants.PPM);
        setRegion(marioStand);

    }

    public void update(float dt){
        if(marioIsBig)
            setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2 - 6 / Constants.PPM);
        else
            setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
//        if(timeToDefineBigMario)
//            defineBigMario();
//        if(timeToReDefineMario)
//            redefineMario();
        for(FireBall  ball : fireballs) {
            ball.update(dt);
            if(ball.isDestroyed())
                fireballs.removeValue(ball, true);
        }
        Gdx.app.log("asd",""+fireTime);
    }

    public boolean isBig(){
        return marioIsBig;
    }
    public boolean isDead(){
        return marioIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case DEAD:
                fireTime = 0;
                region = marioDead;
                break;
            case GROWING:
                fireTime += dt;
                region = growMario.getKeyFrame(stateTimer);
                if(growMario.isAnimationFinished(stateTimer))
                    runGrowAnimation = false;
                break;
            case JUMPING:
                fireTime += 0.05;
                region = marioJump;
                break;
            case RUNNING:
                fireTime += 0.05;
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case SHOOTING:
                region = marioStand;
                break;
            case FALLING:
                fireTime += 0.05;
                region = marioJump;
                break;
            case STANDING:
            default:
                fireTime += 0.05;
                region = marioStand;
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
        else if(runGrowAnimation)
            return State.GROWING;
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

    public void grow(){
        runGrowAnimation = true;
        marioIsBig = true;
        timeToDefineBigMario = true;
        setBounds(getX(), getY(), getWidth(), getHeight() * 2);
        AssetsManager.manager.get("audio/sounds/powerup.wav", Sound.class).play();
    }

    public void hit(){
        if(marioIsBig){
            marioIsBig = false;
            timeToReDefineMario = true;
            setBounds(getX(), getY(), getWidth(), getHeight() / 2);
            AssetsManager.manager.get("audio/sounds/powerdown.wav", Sound.class).play();
        }
        else {
            AssetsManager.manager.get("audio/music/mario_music.ogg", Music.class).stop();
            AssetsManager.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
            marioIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = Constants.NOTHING_BIT;
            for(Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public float getFireTime(){
        return fireTime;
    }

    public void setFireTime(float ft){
        fireTime = ft;
    }
//    public void redefineMario(){
//        Vector2 position = b2body.getPosition();
//        world.destroyBody(b2body);
//
//        BodyDef bdef = new BodyDef();
//        bdef.position.set(position);
//        bdef.type = BodyDef.BodyType.DynamicBody;
//        b2body = world.createBody(bdef);
//
//        FixtureDef fdef = new FixtureDef();
//        CircleShape shape = new CircleShape();
//        shape.setRadius(6 / Constants.PPM);
//        fdef.filter.categoryBits = Constants.MARIO_BIT;
//        fdef.filter.maskBits = Constants.GROUND_BIT |
//                Constants.COIN_BIT |
//                Constants.BRICK_BIT |
//                Constants.ENEMY_BIT |
//                Constants.OBJECT_BIT |
//                Constants.ENEMY_HEAD_BIT |
//                Constants.ITEM_BIT;
//
//        fdef.shape = shape;
//        b2body.createFixture(fdef).setUserData(this);
//
//        EdgeShape head = new EdgeShape();
//        head.set(new Vector2(-2 / Constants.PPM, 6 / Constants.PPM), new Vector2(2 / Constants.PPM, 6 / Constants.PPM));
//        fdef.filter.categoryBits = Constants.MARIO_HEAD_BIT;
//        fdef.shape = head;
//        fdef.isSensor = true;
//
//        b2body.createFixture(fdef).setUserData(this);
//        timeToReDefineMario = false;
//    }
//
//    public void defineBigMario(){
//        Vector2 currentPosition = b2body.getPosition();
//        world.destroyBody(b2body);
//
//        BodyDef bdef = new BodyDef();
//        bdef.position.set(currentPosition.add(0, 10 / Constants.PPM));
//        bdef.type = BodyDef.BodyType.DynamicBody;
//        b2body = world.createBody(bdef);
//
//        FixtureDef fdef = new FixtureDef();
//        CircleShape shape = new CircleShape();
//        shape.setRadius(6 / Constants.PPM);
//        fdef.filter.categoryBits = Constants.MARIO_BIT;
//        fdef.filter.maskBits = Constants.GROUND_BIT |
//                Constants.COIN_BIT |
//                Constants.BRICK_BIT |
//                Constants.ENEMY_BIT |
//                Constants.OBJECT_BIT |
//                Constants.ENEMY_HEAD_BIT |
//                Constants.ITEM_BIT;
//
//        fdef.shape = shape;
//        b2body.createFixture(fdef).setUserData(this);
//        shape.setPosition(new Vector2(0, -14 / Constants.PPM));
//        b2body.createFixture(fdef).setUserData(this);
//
//        EdgeShape head = new EdgeShape();
//        head.set(new Vector2(-2 / Constants.PPM, 6 / Constants.PPM), new Vector2(2 / Constants.PPM, 6 / Constants.PPM));
//        fdef.filter.categoryBits = Constants.MARIO_HEAD_BIT;
//        fdef.shape = head;
//        fdef.isSensor = true;
//
//        b2body.createFixture(fdef).setUserData(this);
//        timeToDefineBigMario = false;
//    }

    public void defineMario(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(450/Constants.PPM, 32/Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Constants.PPM);
        fdef.filter.categoryBits = Constants.MARIO_BIT;
        fdef.filter.maskBits = Constants.GROUND_BIT |
                Constants.COIN_BIT |
                Constants.BRICK_BIT |
                Constants.ENEMY_BIT |
                Constants.OBJECT_BIT |
                Constants.ENEMY_HEAD_BIT |
                Constants.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0, -7.8f / Constants.PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / Constants.PPM, 6 / Constants.PPM), new Vector2(2 / Constants.PPM, 6 / Constants.PPM));
        fdef.filter.categoryBits = Constants.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void fire(){
        fireballs.add(new FireBall(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
        AssetsManager.manager.get("audio/sounds/gun.wav", Sound.class).play();
        Gdx.app.log("fire", ""+fireballs);


    }

    public void draw(Batch batch){
        super.draw(batch);
        for(FireBall ball : fireballs){
                ball.draw(batch);

        }
    }
}
