package com.turpgames.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.turpgames.physics.box2d.builders.Box2DBuilders;

public class Ball {
	public final Body body;

	public Ball(GameWorld gameWorld, float x, float y, float r) {
		body = Box2DBuilders.Body.dynamicBodyBuilder()
				.setCenter(x, y)
				.build(gameWorld.getWorld(),
						Box2DBuilders.Fixture.fixtureBuilder()
								.setElasticity(0.8f)
								.setDensity(0.2f)
								.setFriction(0.4f)
								.setShape(Box2DBuilders.Shape.circleBuilder().setRadius(r).build()));
		body.getPosition().set(x, y);
	}
}
