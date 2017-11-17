package com.gxiv.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.gxiv.game.sprites.Enemy;
import com.gxiv.game.sprites.bullet.Revolver;
import com.gxiv.game.sprites.Player;
import com.gxiv.game.sprites.tileobjects.InteractiveTileObject;
import com.gxiv.game.sprites.items.Item;
import com.gxiv.game.util.Constants;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cdef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cdef) {
            case Constants.MARIO_HEAD_BIT | Constants.BRICK_BIT:
            case Constants.MARIO_HEAD_BIT | Constants.COIN_BIT:
                if (fixA.getFilterData().categoryBits == Constants.MARIO_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Player) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Player) fixB.getUserData());
                break;
            case Constants.ENEMY_HEAD_BIT | Constants.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == Constants.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead();
                else
                    ((Enemy) fixB.getUserData()).hitOnHead();
                break;
            case Constants.ENEMY_BIT | Constants.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == Constants.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Constants.MARIO_BIT | Constants.ENEMY_BIT:
                Gdx.app.log("Player", "DIED");
                if (fixA.getFilterData().categoryBits == Constants.MARIO_BIT)
//                    ((Player) fixA.getUserData()).hit();
                    Gdx.app.log("Player", "Hit");
                else
//                    ((Player) fixB.getUserData()).hit();
                    Gdx.app.log("Player", "Hit");
                break;
//            case Constants.ENEMY_BIT | Constants.ENEMY_BIT:
//                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
//                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
//                break;
            case Constants.ITEM_BIT | Constants.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == Constants.ITEM_BIT)
                    ((Item) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Constants.ITEM_BIT | Constants.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == Constants.ITEM_BIT) {
                    if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
                        Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
                        Fixture object = head == fixA ? fixB : fixA;

                        if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                            ((Item) object.getUserData()).use(null);
                        }
                    } else
                        ((Item) fixA.getUserData()).use((Player) fixB.getUserData());
                } else if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
                    Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
                    Fixture object = head == fixA ? fixB : fixA;

                    if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
//                            ((Item) object.getUserData()).use(null);((Player) head.getUserData()).grow();
                    }
                } else
                    ((Item) fixB.getUserData()).use((Player) fixA.getUserData());

                break;
            case Constants.FIREBALL_BIT | Constants.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Constants.FIREBALL_BIT){
                    ((Revolver)fixA.getUserData()).setToDestroy();
                    Gdx.app.log("eiei", "Destroy");
                }
                else{
                    ((Revolver)fixB.getUserData()).setToDestroy();
                    Gdx.app.log("eiei", "Destroy");
                }
                break;
            case Constants.FIREBALL_BIT | Constants.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == Constants.FIREBALL_BIT)
                    ((Revolver)fixA.getUserData()).setToDestroy();
                else
                    ((Revolver)fixB.getUserData()).setToDestroy();
                break;
            case Constants.FIREBALL_BIT | Constants.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Constants.FIREBALL_BIT)
                    ((Revolver)fixA.getUserData()).setToDestroy();
                else
                    ((Revolver)fixB.getUserData()).setToDestroy();

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
