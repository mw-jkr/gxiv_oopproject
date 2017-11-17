package com.gxiv.game.sprites.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.util.Constants;

public abstract class Bullet extends Sprite{
    protected PlayScreen screen;
    protected World world;
    protected float stateTime;
    protected boolean destroyed;
    protected boolean setToDestroy;
    protected boolean fireRight;


    public Body b2body;

    public Bullet(PlayScreen screen, float x, float y, boolean fireRight){
        this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();
        defineBullet();
    }

    public abstract void defineBullet();

    public abstract void update(float dt);

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }
}
