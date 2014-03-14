package com.turpgames.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.turpgames.input.GdxInputManager;
import com.turpgames.input.InputListener;
import com.turpgames.physics.box2d.builders.Box2DBuilders;

public class Ball {
	public final Body body;

	public Ball(GameWorld gameWorld, float x, float y, float r) {
		body = Box2DBuilders.Body.newDynamicBodyBuilder()
				.setCenter(x, y)
				.build(gameWorld.getWorld(),
						Box2DBuilders.Fixture.newFixtureBuilder()
								.setElasticity(0.8f)
								.setDensity(0.2f)
								.setFriction(0.4f)
								.setShape(Box2DBuilders.Shape.newCircleBuilder().setRadius(r).build()));
		body.getPosition().set(x, y);

		GdxInputManager.instance.registerListener(new InputListener() {
			@Override
			public boolean touchDown(float x, float y, int pointer) {
//				x = GameWorld.scale * x;
//				y = GameWorld.scale * y;
//				for (Fixture fixture : body.getFixtureList()) {
//					if (fixture.testPoint(x, y)) {
//						body.applyLinearImpulse(0, 5, 0, 0, true);
//						return true;
//					}
//				}
				return false;
			}
		});
	}
}