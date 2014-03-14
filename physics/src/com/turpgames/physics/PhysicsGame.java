package com.turpgames.physics;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.turpgames.input.GdxInputManager;
import com.turpgames.physics.box2d.builders.Box2DBuilders;
import com.turpgames.physics.box2d.builders.RevoluteJointBuilder;
import com.turpgames.physics.box2d.builders.RopeJointBuilder;

public class PhysicsGame implements ApplicationListener {
	private GameWorld gameWorld;

	@Override
	public void create() {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		gameWorld = GameWorld.newBuilder().gravity(0, -10f).viewport(GameWorld.scale * width, GameWorld.scale * height).build();

		FixedPart fixedPart = new FixedPart(gameWorld);
		Ball ball = new Ball(gameWorld, 0.75f, 0.75f, 0.05f);

		new RopeSegment(gameWorld, 0.75f, 0.75f, 0.1f, 0.2f);

		final float ropeLength = 1.5f;
		final float segmentLength = 0.1f;
		final int ropeSegmentCount = (int) (ropeLength / segmentLength);
		final float y = 2.5f - 0.05f;

		RevoluteJointBuilder revoluteJointBuilder = Box2DBuilders.Joint.newRevoluteJointBuilder()
				.setLocalAnchorA(0, -segmentLength / 2f)
				.setLocalAnchorB(0, segmentLength / 2f);

		RopeJointBuilder ropeJointBuilder = Box2DBuilders.Joint.newRopeJointBuilder()
				.setCollideConnected(true);

		final RopeSegment[] segments = new RopeSegment[ropeSegmentCount];
		for (int i = 0; i < segments.length; i++) {
			segments[i] = new RopeSegment(gameWorld, 0.75f, y - (i + 0.5f) * segmentLength, 0.01f, segmentLength);
			segments[i].body.setSleepingAllowed(true);
			segments[i].body.setAwake(false);
			if (i > 0) {
				revoluteJointBuilder
						.setBodyA(segments[i - 1].body)
						.setBodyB(segments[i].body)
						.build(gameWorld.getWorld());
			}

			ropeJointBuilder
					.setBodyA(fixedPart.body)
					.setBodyB(segments[i].body)
					.setMaxLength((i + 1) * segmentLength)
					.build(gameWorld.getWorld());
		}

		Box2DBuilders.Joint.newRopeJointBuilder()
				.setBodyA(ball.body)
				.setBodyB(fixedPart.body)
				.setMaxLength(ropeLength + 0.05f)
				.setCollideConnected(true)
				.build(gameWorld.getWorld());

		Box2DBuilders.Joint.newRevoluteJointBuilder()
				.setBodyA(ball.body)
				.setBodyB(segments[segments.length - 1].body)
				.setLocalAnchorB(0, -segmentLength / 2f)
				.build(gameWorld.getWorld());

		Box2DBuilders.Joint.newRevoluteJointBuilder()
				.setBodyA(fixedPart.body)
				.setBodyB(segments[0].body)
				.setLocalAnchorB(0, segmentLength / 2f)
				.build(gameWorld.getWorld());

		gameWorld.getWorld().setContactFilter(new ContactFilter() {
			@Override
			public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
				for (RopeSegment segment : segments) {
					if (segment.body == fixtureA.getBody() ||
						segment.body == fixtureB.getBody())
						return false;
				}
				return true;
			}
		});

		Room.newBuilder()
				.setPosition(0, 0)
				.setSize(gameWorld.getWidth(), gameWorld.getHeight())
				.setWallThickness(0.1f)
				.build(gameWorld);

		GdxInputManager.instance.activate();

		GdxInputManager.instance.registerListener(new MouseJointInputListener(gameWorld, fixedPart.body));
	}

	@Override
	public void render() {
		gameWorld.update();
		gameWorld.render();
	}

	@Override
	public void dispose() {
		gameWorld.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}