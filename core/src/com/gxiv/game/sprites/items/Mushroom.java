package com.gxiv.game.sprites.items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.Mario;
import com.gxiv.game.util.Constants;

public class Mushroom extends Item {
    public Mushroom(PlayScreen screen, float x, float y){
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("mushroom"), 0, 0, 16, 16);
        velocity = new Vector2(0.7f, 0);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Constants.PPM);
        fdef.filter.categoryBits = Constants.ITEM_BIT;
        fdef.filter.maskBits = Constants.MARIO_BIT |
                Constants.OBJECT_BIT |
                Constants.GROUND_BIT |
                Constants.COIN_BIT |
                Constants.BRICK_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void use(Mario mario) {
        destroy();
        mario.grow();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        velocity.y = b2body.getLinearVelocity().y;
        b2body.setLinearVelocity(velocity);
    }
}
