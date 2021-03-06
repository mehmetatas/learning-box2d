package com.turpgames.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.turpgames.input.InputListener;
import com.turpgames.physics.box2d.builders.Box2DBuilders;
import com.turpgames.physics.box2d.builders.MouseJointBuilder;

public class MouseJointInputListener extends InputListener {
	private final GameWorld gameWorld;

	private Body hitBody;
	private MouseJoint mouseJoint;
	private final MouseJointBuilder mouseJointBuilder;
	private final Vector2 target = new Vector2();
	private final Vector3 testPoint = new Vector3();
	private final QueryCallback callback = new QueryCallback() {
		@Override
		public boolean reportFixture(Fixture fixture) {
			// if the hit point is inside the fixture of the body
			// we report it
			if (fixture.testPoint(testPoint.x, testPoint.y)) {
				hitBody = fixture.getBody();
				return false;
			} else
				return true;
		}
	};

	public MouseJointInputListener(GameWorld gameWorld, Body dummyBodyA) {
		this.gameWorld = gameWorld;
		this.mouseJointBuilder = Box2DBuilders.Joint.mouseJointBuilder()
				.setCollideConnected(true)
				.setBodyA(dummyBodyA);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer) {
		// translate the mouse coordinates to world coordinates
		gameWorld.unproject(testPoint.set(x, y, 0));
		// ask the world which bodies are within the given
		// bounding box around the mouse pointer
		hitBody = null;
		gameWorld.getWorld().QueryAABB(callback, testPoint.x - 0.001f, testPoint.y - 0.001f, testPoint.x + 0.001f, testPoint.y + 0.001f);

		// ignore non-dynamic bodies
		if (hitBody == null || hitBody.getType() != BodyType.DynamicBody)
			return false;

		// if we hit something we create a new mouse joint
		// and attach it to the hit body.
		if (hitBody != null) {
			mouseJoint = mouseJointBuilder.setBodyB(hitBody)
					.setTarget(testPoint.x, testPoint.y)
					.setMaxForce(1000.0f * hitBody.getMass())
					.build(gameWorld.getWorld());

			hitBody.setAwake(true);
		}

		return false;
	}

	/** another temporary vector **/

	@Override
	public boolean touchDragged(float x, float y, int pointer) {
		// if a mouse joint exists we simply update
		// the target of the joint based on the new
		// mouse coordinates
		if (mouseJoint != null) {
			gameWorld.unproject(testPoint.set(x, y, 0));
			mouseJoint.setTarget(target.set(testPoint.x, testPoint.y));
		}
		return false;
	}

	@Override
	public boolean touchUp(float x, float y, int pointer) {
		// if a mouse joint exists we simply destroy it
		if (mouseJoint != null) {
			gameWorld.getWorld().destroyJoint(mouseJoint);
			mouseJoint = null;
		}
		return false;
	}
}
