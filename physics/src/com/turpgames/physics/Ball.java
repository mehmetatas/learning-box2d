package com.turpgames.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.turpgames.physics.box2d.builders.Box2DBuilders;

public class Ball {
	public final Body body;

	public Ball(GameWorld gameWorld, float x, float y, float r) {
		Shape shape = Box2DBuilders.Shape.circleBuilder().setRadius(r).build();
		
		body = Box2DBuilders.Body.dynamicBodyBuilder()
				.setCenter(x, y)
				.build(gameWorld.getWorld(),
						Box2DBuilders.Fixture.fixtureBuilder()
								.setElasticity(0.8f)
								.setDensity(200.2f)
								.setFriction(0.4f)
								.setShape(shape));

		shape.dispose();
	}
}