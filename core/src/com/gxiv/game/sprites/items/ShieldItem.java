package com.gxiv.game.sprites.items;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.gxiv.game.screen.PlayScreen;
import com.gxiv.game.sprites.Player;
import com.gxiv.game.util.Constants;

public class ShieldItem extends Item {
    private TextureAtlas shield;

    public ShieldItem(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        shield = new TextureAtlas("sheild.pack");
        setRegion(shield.findRegion("sheild"), 0, 0, 198, 223);
        setBounds(0, 0, 16 / Constants.PPM, 18 / Constants.PPM);
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
        fdef.filter.categoryBits = Constants.ARMOR_BIT;
        fdef.filter.maskBits = Constants.PLAYER_BIT |
                Constants.OBJECT_BIT |
                Constants.GROUND_BIT |
                Constants.BOSS_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void use(Player player) {
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }
}
