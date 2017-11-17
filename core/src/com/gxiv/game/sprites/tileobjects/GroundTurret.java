package com.gxiv.game.sprites.tileobjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

import com.gxiv.game.hud.Hud;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.Player;
import com.gxiv.game.sprites.bullet.Revolver;
import com.gxiv.game.util.AssetsManager;
import com.gxiv.game.util.Constants;

public class GroundTurret extends InteractiveTileObject{
    float turret = 0;
    public GroundTurret(PlayScreen screen, RectangleMapObject objects){
        super(screen, objects);
        fixture.setUserData(this);
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
            AssetsManager.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
        }
    }

}
