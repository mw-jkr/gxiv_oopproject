package com.gxiv.game.sprites.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.gxiv.game.Gxiv;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.Mario;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.Constants;

 public class FireBall extends Sprite{
        public enum State {SHOOT, EXPLODE};
        public State currentState;
        public State previousState;
        private PlayScreen screen;
        private World world;
        private TextureAtlas fire;
        TextureRegion fireAnimation;
        private float stateTime;
        private Animation<TextureRegion> exploding;
        private Array<TextureRegion> frames;
        private boolean destroyed;
        private boolean setToDestroy;
        private boolean fireRight;
        private TextureAtlas explode;

        Body b2body;

        public FireBall(PlayScreen screen, float x, float y, boolean fireRight){
            this.fireRight = fireRight;
            this.screen = screen;
            this.world = screen.getWorld();
            fire = new TextureAtlas("bullet.txt");
            explode  = new TextureAtlas("explode.pack");
            frames = new Array<TextureRegion>();
            for(int i = 1;i <= 4;i++){
                frames.add(new TextureRegion(explode.findRegion("explosion"), i*20,1, 20, 16));
            }
            exploding = new Animation<TextureRegion>(0.1f, frames);
            frames.clear();
            fireAnimation = new TextureRegion(fire.findRegion("bullet"), 1, 1, 8, 6);
            setRegion(fireAnimation);
            setBounds(x, y, 8 / Constants.PPM, 6 / Constants.PPM);
            defineFireBall();
        }

        public void defineFireBall(){
            BodyDef bdef = new BodyDef();
            bdef.position.set(fireRight ? getX() + 20 /Constants.PPM : getX() - 20 /Constants.PPM, getY() - 2/Constants.PPM);
            bdef.type = BodyDef.BodyType.DynamicBody;
            b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();
            CircleShape shape = new CircleShape();
            shape.setRadius(3 / Constants.PPM);
            fdef.filter.categoryBits = Constants.FIREBALL_BIT;
            fdef.filter.maskBits = Constants.GROUND_BIT |
                    Constants.COIN_BIT |
                    Constants.BRICK_BIT |
                    Constants.ENEMY_BIT |
                    Constants.OBJECT_BIT |
                    Constants.ENEMY_HEAD_BIT;
            fdef.shape = shape;
            fdef.restitution = 1;
            fdef.friction = 0;
            b2body.createFixture(fdef).setUserData(this);
            shape.dispose();


        }

     public void update(float dt){
             stateTime += dt;
             b2body.setGravityScale(0);
             b2body.setLinearVelocity(new Vector2(fireRight ? 5 : -5,0));
             setRegion(getFrame(dt));
             setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
             if((stateTime > 0.9f || setToDestroy) && !destroyed) {
                  world.destroyBody(b2body);
                  destroyed = true;
             }
             if(b2body.getLinearVelocity().y > 2f)
                 b2body.setLinearVelocity(b2body.getLinearVelocity().x, 1f);
             if((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0))
                 setToDestroy();
     }

     public TextureRegion getFrame(float dt){
         currentState = getState();

         TextureRegion region;
         switch (currentState){
             case EXPLODE:
                 region = exploding.getKeyFrame(stateTime, false);
                 break;
             default:
                 region = fireAnimation;
                 break;
         }

         stateTime = currentState == previousState ? stateTime + dt : 0;
         previousState = currentState;
         return region;
     }

     public State getState(){
         if(setToDestroy){
             Gdx.app.log("Boom", "Boom");
             return State.EXPLODE;
         }
         else
             return State.SHOOT;
     }

        public void setToDestroy(){
            setToDestroy = true;
        }

        public boolean isDestroyed(){
            return destroyed;
        }
 }

