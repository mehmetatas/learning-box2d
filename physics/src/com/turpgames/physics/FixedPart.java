package com.turpgames.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.turpgames.physics.box2d.builders.Box2DBuilders;

public class FixedPart {
	public final Body body;
	
	public FixedPart(GameWorld gameWorld) {
		body = Box2DBuilders.Body.newStaticBodyBuilder()
				.setCenter(0.75f, 2.5f)
				.build(gameWorld.getWorld(),
					Box2DBuilders.Fixture.newFixtureBuilder()
								.setShape(Box2DBuilders.Shape.buildBox(0.1f, 0.1f)));
	}
}