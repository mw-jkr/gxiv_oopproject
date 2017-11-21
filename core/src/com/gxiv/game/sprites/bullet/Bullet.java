package com.gxiv.game.sprites.bullet;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.gxiv.game.screen.PlayScreen;

public abstract class Bullet extends Sprite {
    public Body b2body;
    protected PlayScreen screen;
    protected World world;
    protected float stateTime;
    protected boolean destroyed;
    protected boolean setToDestroy;
    protected boolean fireRight;

    public Bullet(PlayScreen screen, float x, float y, boolean fireRight) {
        this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();
        defineBullet();
    }

    public abstract void defineBullet();

    public abstract void update(float dt);

    public void setToDestroy() {
        setToDestroy = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
