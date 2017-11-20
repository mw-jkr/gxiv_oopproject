package com.gxiv.game.sprites.tileobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gxiv.game.hud.Hud;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.bullet.GTurretBullet;
import com.gxiv.game.sprites.bullet.Revolver;
import com.gxiv.game.sprites.items.ItemDef;
import com.gxiv.game.sprites.items.HeartItem;
import com.gxiv.game.sprites.items.ShieldItem;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.AudioManager;
import com.gxiv.game.util.Constants;

public class GroundTurret extends InteractiveTileObject{
    float turret = 0;
    private Array<GTurretBullet> bullets;
    private float fireTime;
    private float stateTime;
    private boolean setToDestroy;
    private TextureAtlas explode;
    private Animation<TextureRegion> exploding;
    private Array<TextureRegion> frames;
    public GroundTurret(PlayScreen screen, RectangleMapObject objects){
        super(screen, objects);
        fixture.setUserData(this);
        setToDestroy = false;
        fireTime = 0;
        stateTime = 0;
        setCategoryFilter(Constants.GROUND_TURRET_BIT);
        bullets = new Array<GTurretBullet>();
        frames = new Array<TextureRegion>();
        explode = new TextureAtlas("explode2.pack");
        for(int i=0;i<8;i++)
            frames.add(new TextureRegion(explode.findRegion("explosion"), i*48, 0, 48, 48));
        exploding = new Animation<TextureRegion>(0.1f, frames);

    }

    @Override
    public void hitOnBullet(Revolver bullet) {
        turret += 1;
        if(turret == 3){
            setCategoryFilter(Constants.DESTROY_BIT);
            getCell1().setTile(null);
            getCell2().setTile(null);
            getCell3().setTile(null);
            getCell4().setTile(null);
            Hud.addScore(100);
            Constants.gT += 1;
            setToDestroy = true;
            AudioManager.playSound(AssetsManager.explodeSound);
            if(objects.getProperties().containsKey("heart")){
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), HeartItem.class));
                AudioManager.playSound(AssetsManager.itemDrop);
            }
            if(objects.getProperties().containsKey("armor")){
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), ShieldItem.class));
                AudioManager.playSound(AssetsManager.itemDrop);
            }
        }

    }
    public void update(float dt){
        fireTime += 0.0001;
        stateTime += dt;
        for(GTurretBullet bullet: bullets) {
            bullet.update(dt);
            if(bullet.isDestroyed())
                bullets.removeValue(bullet, true);
        }
        if(setToDestroy){
            setPosition(getX(), getY());
            setBounds(0,0,32 / Constants.PPM, 32 / Constants.PPM);
            setRegion(exploding.getKeyFrame(stateTime, false));
        }
    }

    public void fire(){
        bullets.add(new GTurretBullet(screen, body.getPosition().x, body.getPosition().y));
        AudioManager.playSound(AssetsManager.laser);
    }

    public void draw(Batch batch){
        for(GTurretBullet bullet : bullets){
            bullet.draw(batch);
        }
    }

    public boolean getDestroy(){
        return setToDestroy;
    }

    public float getFireTime() {
        return fireTime;
    }

    public void setFireTime(float fireTime) {
        this.fireTime = fireTime;
    }

    public void addFireTime(float fireTime) {
        this.fireTime += fireTime;
    }
}
