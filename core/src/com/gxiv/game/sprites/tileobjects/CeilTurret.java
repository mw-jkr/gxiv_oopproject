package com.gxiv.game.sprites.tileobjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gxiv.game.hud.Hud;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.bullet.CTurretBullet;
import com.gxiv.game.sprites.bullet.GTurretBullet;
import com.gxiv.game.sprites.bullet.Revolver;
import com.gxiv.game.sprites.items.HeartItem;
import com.gxiv.game.sprites.items.ItemDef;
import com.gxiv.game.sprites.items.ShieldItem;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.AudioManager;
import com.gxiv.game.util.Constants;

public class CeilTurret extends InteractiveTileObject{
    float turret = 0;
    private Array<CTurretBullet> bullets;
    private float fireTime;
    private boolean setToDestroy;
    public CeilTurret(PlayScreen screen, RectangleMapObject objects){
        super(screen, objects);
        fixture.setUserData(this);
        bullets = new Array<CTurretBullet>();
        setToDestroy = false;
        fireTime = 0;
        setCategoryFilter(Constants.GROUND_TURRET_BIT);
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
            Constants.cT += 1;
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
        for(CTurretBullet bullet: bullets) {
            bullet.update(dt);
            if(bullet.isDestroyed())
                bullets.removeValue(bullet, true);
        }
    }

    public void fire(){
        bullets.add(new CTurretBullet(screen, body.getPosition().x, body.getPosition().y));
        AudioManager.playSound(AssetsManager.laser);
    }

    public void draw(Batch batch){
        for(CTurretBullet bullet : bullets){
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
