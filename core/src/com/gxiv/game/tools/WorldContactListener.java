package com.gxiv.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.gxiv.game.hud.Hud;
import com.gxiv.game.sprites.Player;
import com.gxiv.game.sprites.bullet.CTurretBullet;
import com.gxiv.game.sprites.bullet.GTurretBullet;
import com.gxiv.game.sprites.bullet.Revolver;
import com.gxiv.game.sprites.enemies.Enemy;
import com.gxiv.game.sprites.enemies.Army;
import com.gxiv.game.sprites.items.HeartItem;
import com.gxiv.game.sprites.items.ShieldItem;
import com.gxiv.game.sprites.tileobjects.InteractiveTileObject;
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
                    ((HeartItem) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                else{
                    if(Constants.HP < 10){
                        Hud.addHP(-1);
                    }
                    ((HeartItem) fixA.getUserData()).use((Player) fixB.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.ARMOR_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT){
                    if(Constants.ARMOR < 10){
                        Hud.updateAMR(-1);
                    }
                    ((ShieldItem) fixB.getUserData()).use((Player) fixA.getUserData());
                }
                else{
                    if(Constants.ARMOR < 10){
                        Hud.updateAMR(-1);
                    }
                    ((ShieldItem) fixA.getUserData()).use((Player) fixB.getUserData());
                }
                break;
            case Constants.PLAYER_BULLET_BIT | Constants.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Constants.PLAYER_BULLET_BIT){
                    ((Revolver)fixA.getUserData()).setToDestroy();
                    Revolver.delay = 0.5f;
                    Gdx.app.log("Bullet", "Destroy");
                }
                else {
                    ((Revolver) fixB.getUserData()).setToDestroy();
                    Revolver.delay = 0.5f;
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
                    ((Revolver) fixA.getUserData()).setToDestroy();
                    Constants.eN += 1;
                    Revolver.delay = 0;
                    ((Army) fixB.getUserData()).hitOnBullet();
                }
                else{
                    ((Revolver) fixB.getUserData()).setToDestroy();
                    Constants.eN += 1;
                    Revolver.delay = 0;
                    ((Army) fixA.getUserData()).hitOnBullet();
                }
                break;
            case Constants.PLAYER_BULLET_BIT | Constants.GROUND_TURRET_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BULLET_BIT){
                    ((Revolver) fixA.getUserData()).setToDestroy();
                    Revolver.delay = 0.5f;
                    ((InteractiveTileObject) fixB.getUserData()).hitOnBullet((Revolver) fixA.getUserData());
                }
                else{
                    ((Revolver) fixB.getUserData()).setToDestroy();
                    Revolver.delay = 0.5f;
                    ((InteractiveTileObject) fixA.getUserData()).hitOnBullet((Revolver) fixB.getUserData());
                }
                break;
            case Constants.PLAYER_BULLET_BIT | Constants.CEIL_TURRET_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BULLET_BIT){
                    ((Revolver) fixA.getUserData()).setToDestroy();
                    Revolver.delay = 0.5f;
                    ((InteractiveTileObject) fixB.getUserData()).hitOnBullet((Revolver) fixA.getUserData());
                }
                else{
                    ((Revolver) fixB.getUserData()).setToDestroy();
                    Revolver.delay = 0.5f;
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
                    Gdx.app.log("CBullet","Hit");
                }
                else{
                    ((GTurretBullet) fixB.getUserData()).setToDestroy();
                    Gdx.app.log("CBullet","Hit");
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
                    Gdx.app.log("CBullet","Hit");
                }
                else{
                    ((CTurretBullet) fixB.getUserData()).setToDestroy();
                    Gdx.app.log("CBullet","Hit");
                }
                break;
            case Constants.CEIL_BULLET_BIT | Constants.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == Constants.CEIL_BULLET_BIT){
                    ((CTurretBullet) fixA.getUserData()).setToDestroy();
                    Gdx.app.log("CBullet","Hit");
                }
                else{
                    ((CTurretBullet) fixB.getUserData()).setToDestroy();
                    Gdx.app.log("CBullet","Hit");
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
