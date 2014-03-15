package com.turpgames.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;
import com.turpgames.physics.box2d.builders.Box2DBuilders;

public class RopeSegment {
	public final Body body;

	public RopeSegment(GameWorld gameWorld, float centerX, float y, Shape shape) {
		body = Box2DBuilders.Body.dynamicBodyBuilder()
				.setCenter(centerX, y)
				.build(gameWorld.getWorld());
		
		Fixture fixture = Box2DBuilders.Fixture.fixtureBuilder()
				.setDensity(0.1f)
				.setElasticity(0.01f)
				.setFriction(0.9f)
				.setShape(shape)
				.build(body);
		
		fixture.setUserData(new UserData(false));
	}
}
