package com.gxiv.game.tools;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.enemies.Goomba;
import com.gxiv.game.sprites.tileobjects.CeilTurret;
import com.gxiv.game.sprites.tileobjects.GroundTurret;
import com.gxiv.game.util.Constants;

public class B2WorldCreator {
    private Array<Goomba> arr;
    private Array<GroundTurret> groundTurretArray;
    public B2WorldCreator(PlayScreen screen) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        /* create ground fixed body */
        for (RectangleMapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rect = object.getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(
                    (rect.getX() + rect.getWidth() / 2) / Constants.PPM,
                    (rect.getY() + rect.getHeight() / 2) / Constants.PPM
            );
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Constants.PPM, (rect.getHeight() / 2) / Constants.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);

        }

        /* create wall fixed body */
        for (RectangleMapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rect = object.getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(
                    (rect.getX() + rect.getWidth() / 2) / Constants.PPM,
                    (rect.getY() + rect.getHeight() / 2) / Constants.PPM
            );

            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Constants.PPM, (rect.getHeight() / 2) / Constants.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Constants.OBJECT_BIT;
            body.createFixture(fdef);
        }
        //create brick fixed body
        groundTurretArray = new Array<GroundTurret>();
        for (RectangleMapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            groundTurretArray.add(new GroundTurret(screen, object));

        }
        //create coin fixed body
        for (RectangleMapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            new CeilTurret(screen, object);
        }

//      Turret
        arr = new Array<Goomba>();
        for (RectangleMapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            arr.add(new Goomba(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }
//
//    public Array<GroundTurret> getTurret() {
//        return turretArray;
//    }

    }
    public Array<Goomba> getArr(){
        return arr;
    }
    public Array<GroundTurret> getGroundTurretArray(){
        return groundTurretArray;
    }
}
