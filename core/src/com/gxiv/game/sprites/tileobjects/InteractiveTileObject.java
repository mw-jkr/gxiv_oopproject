package com.gxiv.game.sprites.tileobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.Player;
import com.gxiv.game.sprites.bullet.Revolver;
import com.gxiv.game.util.Constants;

public abstract class InteractiveTileObject extends Sprite{
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    public Body body;
    protected Rectangle bounds;
    protected Fixture fixture;
    protected PlayScreen screen;
    protected RectangleMapObject objects;

    public InteractiveTileObject(PlayScreen screen, RectangleMapObject objects){
        this.objects = objects;
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = objects.getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Constants.PPM, (bounds.getY() + bounds.getHeight() / 2) / Constants.PPM);
        body = world.createBody(bdef);
        shape.setAsBox((bounds.getWidth() /2) / Constants.PPM, (bounds.getHeight() /2) / Constants.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public abstract void hitOnBullet(Revolver bullet);
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell1(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(3);
        return layer.getCell((int)((body.getPosition().x * Constants.PPM) / 16), (int)((body.getPosition().y * Constants.PPM) / 16));
    }

    public TiledMapTileLayer.Cell getCell2(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(3);
        return layer.getCell((int)((body.getPosition().x * Constants.PPM) / 16)-1, (int)((body.getPosition().y * Constants.PPM) / 16));
    }

    public TiledMapTileLayer.Cell getCell3(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(3);
        return layer.getCell((int)((body.getPosition().x * Constants.PPM) / 16)-1, (int)((body.getPosition().y * Constants.PPM) / 16)-1);
    }

    public TiledMapTileLayer.Cell getCell4(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(3);
        return layer.getCell((int)((body.getPosition().x * Constants.PPM) / 16), (int)((body.getPosition().y * Constants.PPM) / 16)-1);
    }
}
