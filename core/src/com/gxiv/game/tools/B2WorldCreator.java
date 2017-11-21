package com.gxiv.game.tools;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.enemies.Army;
import com.gxiv.game.sprites.enemies.Boss;
import com.gxiv.game.sprites.tileobjects.CeilTurret;
import com.gxiv.game.sprites.tileobjects.GroundTurret;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.Constants;

public class B2WorldCreator {
    private Array<Army> arr;
    private Array<Boss> bossArray;
    private Array<GroundTurret> groundTurretArray;
    private Array<CeilTurret> ceilTurretArray;

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
            fdef.filter.categoryBits = Constants.GROUND_BIT;
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
        //create Turret fixed body
        groundTurretArray = new Array<GroundTurret>();
        for (RectangleMapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            groundTurretArray.add(new GroundTurret(screen, object));

        }
        //create Turret fixed body
        ceilTurretArray = new Array<CeilTurret>();
        for (RectangleMapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            ceilTurretArray.add(new CeilTurret(screen, object));
        }

        //create Army fixed body
        arr = new Array<Army>();
        for (RectangleMapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            if (AssetsManager.getNameMap().equals("map1.tmx")) {
                Army.setNameAtlas("enemy1.pack");
                Constants.x1 = 70;
                Constants.y1 = 72;
                Constants.x2 = 74;
                Constants.y2 = 71;
                Constants.x3 = 70;
                Constants.y3 = 72;
                Constants.x4 = 68;
                Constants.y4 = 71;
                Constants.limit = 2;
                //boss
                Boss.setNameAtlas("boss1.pack");
                Constants.bx1 = 108;
                Constants.by1 = 76;
                Constants.bx2 = 109;
                Constants.by2 = 75;
                Constants.bx3 = 109;
                Constants.by3 = 76;
                Constants.bx4 = 76;
                Constants.by4 = 49;
                Constants.blimit = 10;

            }
            if (AssetsManager.getNameMap().equals("map2.tmx")) {
                Army.setNameAtlas("enemy2.pack");
                Constants.x1 = 56;
                Constants.y1 = 71;
                Constants.x2 = 60;
                Constants.y2 = 70;
                Constants.x3 = 56;
                Constants.y3 = 71;
                Constants.x4 = 56;
                Constants.y4 = 70;
                Constants.limit = 3;
                //boss
                Boss.setNameAtlas("boss2.pack");
                Constants.bx1 = 88;
                Constants.by1 = 75;
                Constants.bx2 = 90;
                Constants.by2 = 74;
                Constants.bx3 = 90;
                Constants.by3 = 75;
                Constants.bx4 = 74;
                Constants.by4 = 48;
                Constants.blimit = 15;
            }
            if (AssetsManager.getNameMap().equals("map3.tmx")) {
                Army.setNameAtlas("enemy3.pack");
                Constants.x1 = 67;
                Constants.y1 = 80;
                Constants.x2 = 76;
                Constants.y2 = 79;
                Constants.x3 = 69;
                Constants.y3 = 78;
                Constants.x4 = 62;
                Constants.y4 = 79;
                Constants.limit = 4;
                //boss
                Boss.setNameAtlas("boss3.pack");
                Constants.bx1 = 88;
                Constants.by1 = 77;
                Constants.bx2 = 88;
                Constants.by2 = 77;
                Constants.bx3 = 88;
                Constants.by3 = 77;
                Constants.bx4 = 74;
                Constants.by4 = 50;
                Constants.blimit = 20;
            }
            arr.add(new Army(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }
        //create next MAP
        for (RectangleMapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rect = object.getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(
                    (rect.getX() + rect.getWidth() / 2) / Constants.PPM,
                    (rect.getY() + rect.getHeight() / 2) / Constants.PPM
            );
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Constants.PPM, (rect.getHeight() / 2) / Constants.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Constants.NEXT_MAP_BIT;
            body.createFixture(fdef);

        }

        bossArray = new Array<Boss>();
        for (RectangleMapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            bossArray.add(new Boss(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }
//
//    public Array<GroundTurret> getTurret() {
//        return turretArray;
//    }

    }

    public Array<Army> getArr() {
        return arr;
    }

    public Array<GroundTurret> getGroundTurretArray() {
        return groundTurretArray;
    }

    public Array<Boss> getBossArray() {
        return bossArray;
    }

    public Array<CeilTurret> getCeilTurretArray() {
        return ceilTurretArray;
    }
}
