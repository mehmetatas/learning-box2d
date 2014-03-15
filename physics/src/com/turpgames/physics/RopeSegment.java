package com.turpgames.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.turpgames.physics.box2d.builders.Box2DBuilders;

public class RopeSegment {
	public final Body body;

	public RopeSegment(GameWorld gameWorld, float centerX, float y, float width, float height) {
		body = Box2DBuilders.Body.newDynamicBodyBuilder()
				.setCenter(centerX, y)
				.build(gameWorld.getWorld(),
						Box2DBuilders.Fixture.newFixtureBuilder()
								.setDensity(0.01f)
								.setElasticity(0.01f)
								.setFriction(0.01f)
								.setShape(Box2DBuilders.Shape.buildBox(width, height)));
	}
}