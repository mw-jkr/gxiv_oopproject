package com.gxiv.game.actors;

import com.badlogic.gdx.physics.box2d.World;
import com.gxiv.game.util.Constants;

public class Player extends DynamicGameObject{
    public Player(float x, float y) {
        super(x, y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
    }

    public void update (float delta) {

    }

}
