package com.gxiv.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.*;
import com.gxiv.game.hud.Hud;
import com.gxiv.game.sprites.Player;
import com.gxiv.game.sprites.bullet.*;
import com.gxiv.game.sprites.enemies.Boss;
import com.gxiv.game.sprites.enemies.Enemy;
import com.gxiv.game.sprites.enemies.Army;
import com.gxiv.game.sprites.items.HeartItem;
import com.gxiv.game.sprites.items.ShieldItem;
import com.gxiv.game.sprites.tileobjects.InteractiveTileObject;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.AudioManager;
import com.gxiv.game.util.Constants;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cdef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cdef) {
            case Constants.ENEMY_BIT | Constants.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == Constants.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Constants.ENEMY_BIT | Constants.NEXT_MAP_BIT:
                if (fixA.getFilterData().categoryBits == Constants.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Constants.PLAYER_BIT | Constants.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT)
                    Hud.updateHP(1);
                else
                    Hud.updateHP(1);
                break;
            case Constants.PLAYER_BIT | Constants.HEART_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT){
                    if(Constants.HP < 10){
                        Hud.addHP(-1);
                    }
                    AudioManager.playSound(AssetsManager.receiveItem);
                    ((HeartItem) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                else{
                    if(Constants.HP < 10){
                        Hud.addHP(-1);
                    }
                    AudioManager.playSound(AssetsManager.receiveItem);
                    ((HeartItem) fixA.getUserData()).use((Player) fixB.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.ARMOR_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT){
                    if(Constants.ARMOR < 10){
                        Hud.updateAMR(-1);
                    }
                    AudioManager.playSound(AssetsManager.receiveItem);
                    ((ShieldItem) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                else{
                    if(Constants.ARMOR < 10){
                        Hud.updateAMR(-1);
                    }
                    AudioManager.playSound(AssetsManager.receiveItem);
                    ((ShieldItem) fixA.getUserData()).use((Player) fixB.getUserData());
                }
                break;
            case Constants.PLAYER_BULLET_BIT | Constants.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Constants.PLAYER_BULLET_BIT){
                    ((Revolver)fixA.getUserData()).setToDestroy();
                    Gdx.app.log("Bullet", "Destroy");
                }
                else {
                    ((Revolver) fixB.getUserData()).setToDestroy();
                    Gdx.app.log("Bullet", "Destroy");
                }
                break;
            case Constants.PLAYER_BULLET_BIT | Constants.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BULLET_BIT)
                    ((Revolver) fixA.getUserData()).setToDestroy();
                else
                    ((Revolver) fixB.getUserData()).setToDestroy();
                break;
            case Constants.PLAYER_BULLET_BIT | Constants.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BULLET_BIT){
                    Constants.shot += 1;
                    if(Constants.shot == Constants.limit){
                        ((Army) fixB.getUserData()).hitOnBullet();
                        Constants.eN += 1;
                    }
                    ((Revolver) fixA.getUserData()).setToDestroy();

                }
                else{
                    Constants.shot += 1;
                    if(Constants.shot == Constants.limit) {
                        ((Army) fixA.getUserData()).hitOnBullet();
                        Constants.eN += 1;
                    }
                    ((Revolver) fixB.getUserData()).setToDestroy();

                }
                break;
            case Constants.PLAYER_BULLET_BIT | Constants.BOSS_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BULLET_BIT){
                    Constants.bshot += 1;
                    if(Constants.bshot == Constants.blimit){
                        ((Boss) fixB.getUserData()).hitOnBullet();
                    }
                    ((Revolver) fixA.getUserData()).setToDestroy();

                }
                else{
                    Constants.bshot += 1;
                    if(Constants.bshot == Constants.blimit) {
                        ((Boss) fixA.getUserData()).hitOnBullet();
                    }
                    ((Revolver) fixB.getUserData()).setToDestroy();

                }
                break;
            case Constants.PLAYER_BULLET_BIT | Constants.GROUND_TURRET_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BULLET_BIT){
                    ((Revolver) fixA.getUserData()).setToDestroy();
                    ((InteractiveTileObject) fixB.getUserData()).hitOnBullet((Revolver) fixA.getUserData());
                }
                else{
                    ((Revolver) fixB.getUserData()).setToDestroy();
                    ((InteractiveTileObject) fixA.getUserData()).hitOnBullet((Revolver) fixB.getUserData());
                }
                break;
            case Constants.PLAYER_BULLET_BIT | Constants.CEIL_TURRET_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BULLET_BIT){
                    ((Revolver) fixA.getUserData()).setToDestroy();
                    ((InteractiveTileObject) fixB.getUserData()).hitOnBullet((Revolver) fixA.getUserData());
                }
                else{
                    ((Revolver) fixB.getUserData()).setToDestroy();
                    ((InteractiveTileObject) fixA.getUserData()).hitOnBullet((Revolver) fixB.getUserData());
                }
                break;
            case Constants.PLAYER_BULLET_BIT | Constants.GROUND_BULLET_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BULLET_BIT){
                    ((Revolver) fixA.getUserData()).setToDestroy();
                    ((GTurretBullet) fixB.getUserData()).setToDestroy();
                }
                else{
                    ((Revolver) fixB.getUserData()).setToDestroy();
                    ((GTurretBullet) fixA.getUserData()).setToDestroy();
                }
                break;
            case Constants.GROUND_BULLET_BIT | Constants.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == Constants.GROUND_BULLET_BIT){
                    ((GTurretBullet) fixA.getUserData()).setToDestroy();
                    Hud.updateHP(1);
                    Gdx.app.log("GBullet","Hit");
                }
                else{
                    Hud.updateHP(1);
                    ((GTurretBullet) fixB.getUserData()).setToDestroy();
                    Gdx.app.log("GBullet","Hit");
                }
                break;
            case Constants.GROUND_BULLET_BIT | Constants.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == Constants.GROUND_BULLET_BIT){
                    ((GTurretBullet) fixA.getUserData()).setToDestroy();
                }
                else{
                    ((GTurretBullet) fixB.getUserData()).setToDestroy();
                }
                break;
            case Constants.CEIL_BULLET_BIT | Constants.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == Constants.CEIL_BULLET_BIT){
                    ((CTurretBullet) fixA.getUserData()).setToDestroy();
                    Hud.updateHP(1);
                    Gdx.app.log("CBullet","Hit");
                }
                else{
                    ((CTurretBullet) fixB.getUserData()).setToDestroy();
                    Hud.updateHP(1);
                    Gdx.app.log("CBullet","Hit");
                }
                break;
            case Constants.CEIL_BULLET_BIT | Constants.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == Constants.CEIL_BULLET_BIT){
                    ((CTurretBullet) fixA.getUserData()).setToDestroy();
                }
                else{
                    ((CTurretBullet) fixB.getUserData()).setToDestroy();
                }
                break;
            case Constants.CEIL_BULLET_BIT | Constants.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == Constants.CEIL_BULLET_BIT){
                    ((CTurretBullet) fixA.getUserData()).setToDestroy();
                }
                else{
                    ((CTurretBullet) fixB.getUserData()).setToDestroy();
                }
                break;
            case Constants.PLAYER_BIT | Constants.NEXT_MAP_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT){
                    ((Player) fixA.getUserData()).setNextMapTouch(true);
                }
                else{
                    ((Player) fixB.getUserData()).setNextMapTouch(true);
                }
                break;
            case Constants.BOSS_BULLET_ONE | Constants.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == Constants.BOSS_BULLET_ONE){
                    ((BossBullet1) fixA.getUserData()).setToDestroy();
                    Hud.updateHP(1);
                    Gdx.app.log("CBullet","Hit");
                }
                else{
                    ((BossBullet1) fixB.getUserData()).setToDestroy();
                    Hud.updateHP(1);
                    Gdx.app.log("CBullet","Hit");
                }
                break;
            case Constants.BOSS_BULLET_TWO | Constants.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == Constants.BOSS_BULLET_TWO){
                    ((BossBullet2) fixA.getUserData()).setToDestroy();
                    Hud.updateHP(1);
                    Gdx.app.log("CBullet","Hit");
                }
                else{
                    ((BossBullet2) fixB.getUserData()).setToDestroy();
                    Hud.updateHP(1);
                    Gdx.app.log("CBullet","Hit");
                }
                break;
            case Constants.BOSS_BULLET_THREE| Constants.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == Constants.BOSS_BULLET_THREE){
                    ((BossBullet3) fixA.getUserData()).setToDestroy();
                    Hud.updateHP(1);
                    Gdx.app.log("CBullet","Hit");
                }
                else{
                    ((BossBullet3) fixB.getUserData()).setToDestroy();
                    Hud.updateHP(1);
                    Gdx.app.log("CBullet","Hit");
                }
                break;
            case Constants.BOSS_BULLET_ONE | Constants.PLAYER_BULLET_BIT:
                if (fixA.getFilterData().categoryBits == Constants.BOSS_BULLET_ONE){
                    ((BossBullet1) fixA.getUserData()).setToDestroy();
                    ((Revolver) fixB.getUserData()).setToDestroy();
                }
                else{
                    ((BossBullet1) fixB.getUserData()).setToDestroy();
                    ((Revolver) fixA.getUserData()).setToDestroy();
                }
                break;
            case Constants.BOSS_BULLET_TWO | Constants.PLAYER_BULLET_BIT:
                if (fixA.getFilterData().categoryBits == Constants.BOSS_BULLET_TWO){
                    ((BossBullet2) fixA.getUserData()).setToDestroy();
                    ((Revolver) fixB.getUserData()).setToDestroy();
                }
                else{
                    ((BossBullet2) fixB.getUserData()).setToDestroy();
                    ((Revolver) fixA.getUserData()).setToDestroy();
                }
                break;
            case Constants.BOSS_BULLET_THREE | Constants.PLAYER_BULLET_BIT:
                if (fixA.getFilterData().categoryBits == Constants.BOSS_BULLET_THREE){
                    ((BossBullet3) fixA.getUserData()).setToDestroy();
                    ((Revolver) fixB.getUserData()).setToDestroy();
                }
                else{
                    ((BossBullet3) fixB.getUserData()).setToDestroy();
                    ((Revolver) fixA.getUserData()).setToDestroy();
                }
                break;

        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
