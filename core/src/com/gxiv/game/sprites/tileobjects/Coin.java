package com.gxiv.game.sprites.tileobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.gxiv.game.hud.Hud;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.Player;
import com.gxiv.game.sprites.items.ItemDef;
import com.gxiv.game.sprites.items.Mushroom;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.Constants;

public class Coin extends com.gxiv.game.sprites.tileobjects.InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;
    public Coin(PlayScreen screen, RectangleMapObject objects){
        super(screen, objects);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(Constants.COIN_BIT);
    }

    @Override
    public void onHeadHit(Player player) {
        Gdx.app.log("Coin", "Collision");
        if(getCell().getTile().getId() == BLANK_COIN)
            AssetsManager.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else{
            if(objects.getProperties().containsKey("mushroom")){
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), Mushroom.class));
                AssetsManager.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            }

            else
                AssetsManager.manager.get("audio/sounds/coin.wav", Sound.class).play();
        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(100);
    }
}
