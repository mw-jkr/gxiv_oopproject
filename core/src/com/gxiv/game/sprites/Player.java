package com.gxiv.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gxiv.game.hud.Hud;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.bullet.Revolver;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.AudioManager;
import com.gxiv.game.util.Constants;


public class Player extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD, SHOOTING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private float fireTime;
    private boolean nextMapTouch;
    private TextureAtlas dead;

    private Animation<TextureRegion> gxivRun;
    private TextureRegion gxivStand;
    private TextureRegion gxivJump;
    private TextureRegion gxivDead;
    private PlayScreen screen;

    private float stateTimer;
    private boolean runningRight;
    private boolean gxivIsDead;
    private Array<Revolver> bullets;

    public Player(PlayScreen screen){

        this.world = screen.getWorld();
        this.screen = screen;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        dead = new TextureAtlas("DeadGXIV.pack");
        Array<TextureRegion> frames = new Array<TextureRegion>();

        /*Running Animation*/
        gxivStand = new TextureRegion(screen.getAtlas().findRegion("5"), 1, 1, 53, 75);

        /*Stand Animation*/
        frames.add(new TextureRegion(screen.getAtlas().findRegion("1"), 1, 1, 55, 78));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("2"), 1, 1, 55, 78));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("3"), 1, 1, 53, 77));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("4"), 1, 1, 53, 77));
        gxivRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        gxivJump = new TextureRegion(screen.getAtlas().findRegion("6"), 1, 1, 53, 83);
        gxivDead = new TextureRegion(dead.findRegion("prone"), 1, 1, 84, 51 );
        defineGxiv();
        bullets = new Array<Revolver>();
        setRegion(gxivStand);

    }

    public void update(float dt){
        if(!gxivIsDead){
            setBounds(0, 0, 22 / Constants.PPM, 32 / Constants.PPM);
            setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/1.8f);
            setRegion(getFrame(dt));
        }
        if(gxivIsDead){
            AudioManager.backgroundMusic.stop();
            setBounds(0, 0, 38 / Constants.PPM, 22 / Constants.PPM);
            setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/1.3f);
            setRegion(getFrame(dt));
        }
        if(Hud.getHP() == 0 || Hud.getTime() == 0){
            gxivIsDead = true;
        }
        for(Revolver bullet: bullets) {
            bullet.update(dt);
            if(bullet.isDestroyed())
                bullets.removeValue(bullet, true);
        }
    }

    public boolean isDead(){
        return gxivIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    private TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case DEAD:
                region = gxivDead;
                break;
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

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        else if((b2body.getLinearVelocity().x > 0 || runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){

        if(gxivIsDead)
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

    public void setNextMapTouch(boolean nextMapTouch){
        this.nextMapTouch = nextMapTouch;
    }

    public boolean getNextMapTouch(){
        return nextMapTouch;
    }

    public float getFireTime(){
        return fireTime;
    }

    public void setFireTime(float ft){
        fireTime = ft;
    }

    private void defineGxiv(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(280/Constants.PPM, 50/Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Constants.PPM);
        fdef.filter.categoryBits = Constants.PLAYER_BIT;
        fdef.filter.maskBits = Constants.GROUND_BIT |
                Constants.END_GAME_BIT |
                Constants.BOSS_BIT |
                Constants.ENEMY_BIT |
                Constants.OBJECT_BIT |
                Constants.ENEMY_HEAD_BIT |
                Constants.HEART_BIT |
                Constants.GROUND_TURRET_BIT |
                Constants.CEIL_TURRET_BIT |
                Constants.GROUND_BULLET_BIT |
                Constants.CEIL_BULLET_BIT |
                Constants.NEXT_MAP_BIT |
                Constants.ARMOR_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        fdef.filter.categoryBits = Constants.PLAYER_BIT;
        fdef.filter.maskBits = Constants.GROUND_BIT |
                Constants.END_GAME_BIT |
                Constants.BOSS_BIT |
                Constants.ENEMY_BIT |
                Constants.OBJECT_BIT |
                Constants.ENEMY_HEAD_BIT |
                Constants.HEART_BIT |
                Constants.GROUND_TURRET_BIT |
                Constants.CEIL_TURRET_BIT |
                Constants.GROUND_BULLET_BIT |
                Constants.CEIL_BULLET_BIT |
                Constants.NEXT_MAP_BIT;

        shape.setPosition(new Vector2(0, -7.8f / Constants.PPM));
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-6f, 14).scl(1/ Constants.PPM);
        vertice[1] = new Vector2(6f, 14).scl(1/ Constants.PPM);
        vertice[2] = new Vector2(-3f, 3f).scl(1/ Constants.PPM);
        vertice[3] = new Vector2(3f, 3f).scl(1/ Constants.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.filter.categoryBits = Constants.PLAYER_BIT;
        fdef.filter.maskBits = Constants.GROUND_BIT |
                Constants.END_GAME_BIT |
                Constants.BOSS_BIT |
                Constants.ENEMY_BIT |
                Constants.OBJECT_BIT |
                Constants.ENEMY_HEAD_BIT |
                Constants.HEART_BIT |
                Constants.GROUND_TURRET_BIT |
                Constants.CEIL_TURRET_BIT |
                Constants.GROUND_BULLET_BIT |
                Constants.CEIL_BULLET_BIT |
                Constants.NEXT_MAP_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void fire(){
        bullets.add(new Revolver(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight));
        AudioManager.playSound(AssetsManager.gunSound);
        Gdx.app.log("fire", ""+ bullets);
    }

    public void draw(Batch batch){
        super.draw(batch);
        for(Revolver bullet : bullets){
                bullet.draw(batch);
        }
    }
}
