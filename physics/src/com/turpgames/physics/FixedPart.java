package com.turpgames.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.Shape;
import com.turpgames.input.GdxInputManager;
import com.turpgames.input.InputListener;
import com.turpgames.physics.box2d.builders.Box2DBuilders;

public class FixedPart {
	public final Body body;
	public final Fixture fixture;

	private final Vector3 v = new Vector3();

	public FixedPart(final GameWorld gameWorld) {
		body = Box2DBuilders.Body.staticBodyBuilder()
				.setCenter(0.75f, 2.5f)
				.build(gameWorld.getWorld());

		Shape shape = Box2DBuilders.Shape.buildBox(0.1f, 0.1f);

		fixture = Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(shape)
				.build(body);

		shape.dispose();

		GdxInputManager.instance.registerListener(new InputListener() {
			@Override
			public boolean touchDown(float x, float y, int pointer) {
				gameWorld.unproject(v.set(x, y, 0));
				gameWorld.getWorld().QueryAABB(new QueryCallback() {
					@Override
					public boolean reportFixture(Fixture f) {
						if (f == fixture) {
							PhysicsGame.rope.untieHead();
							return true;
						}
						return false;
					}
				}, v.x, v.y, v.x, v.y);
				return super.touchDown(x, y, pointer);
			}
		});
	}
}
