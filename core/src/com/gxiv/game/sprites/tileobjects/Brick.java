package com.gxiv.game.sprites.tileobjects;

import com.badlogic.gdx.maps.objects.RectangleMapObject;

import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.Player;
import com.gxiv.game.util.Constants;

public class Brick extends com.gxiv.game.sprites.tileobjects.InteractiveTileObject{
    public Brick(PlayScreen screen, RectangleMapObject objects){
        super(screen, objects);
        fixture.setUserData(this);
        setCategoryFilter(Constants.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Player player) {
//        if(player.isBig()) {
//            setCategoryFilter(Constants.DESTROY_BIT);
//            getCell().setTile(null);
//            Hud.addScore(200);
//            AssetsManager.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
//        }
//        else
//            AssetsManager.manager.get("audio/sounds/bump.wav", Sound.class).play();
    }
}
