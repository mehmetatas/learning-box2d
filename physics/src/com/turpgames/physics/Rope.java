package com.turpgames.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.turpgames.physics.box2d.builders.Box2DBuilders;
import com.turpgames.physics.box2d.builders.RevoluteJointBuilder;
import com.turpgames.physics.box2d.builders.RopeJointBuilder;

public class Rope {
	private Body headSegmentBody;
	private Body tailSegmentBody;
	private Body headBody;
	private Body tailBody;

	private RevoluteJoint headJoint;
	private RevoluteJoint tailJoint;
	private RopeJoint mainRopeJoint;

	private final float ropeLength;
	private final int segmentCount;
	private final float segmentWidth;
	private final float segmentHeight;
	private final RopeSegment[] segments;
	private final RopeJoint[] ropeJoints;
	private final RevoluteJoint[] revoluteJoints;

	private final GameWorld gameWorld;

	private Rope(Builder builder, GameWorld gameWorld) {
		this.ropeLength = builder.ropeLength;
		this.segmentCount = builder.segmentCount;
		this.segmentWidth = builder.segmentWidth;
		this.gameWorld = gameWorld;
		this.segmentHeight = ropeLength / segmentCount;

		this.ropeJoints = new RopeJoint[segmentCount - 1];
		this.revoluteJoints = new RevoluteJoint[segmentCount - 1];

		this.segments = initSegments();

		headSegmentBody = segments[0].body;
		tailSegmentBody = segments[segmentCount - 1].body;
	}

	private RopeSegment[] initSegments() {
		RevoluteJointBuilder revoluteJointBuilder = Box2DBuilders.Joint.revoluteJointBuilder()
				.setLocalAnchorA(0, -segmentHeight / 2f)
				.setLocalAnchorB(0, segmentHeight / 2f);

		Shape segmentShape = Box2DBuilders.Shape.buildBox(segmentWidth, segmentHeight);

		RopeSegment[] segments = new RopeSegment[segmentCount];
		for (int i = 0; i < segments.length; i++) {
			segments[i] = new RopeSegment(gameWorld, segmentShape);
			segments[i].body.setSleepingAllowed(true);
			segments[i].body.setAwake(false);
			if (i > 0) {
				revoluteJoints[i - 1] = revoluteJointBuilder
						.setBodyA(segments[i - 1].body)
						.setBodyB(segments[i].body)
						.build(gameWorld.getWorld());
			}
		}

		segmentShape.dispose();

		return segments;
	}

	public void tieHead(Body headBody) {
		if (this.headBody != null)
			throw new UnsupportedOperationException("Rope already has a head body tied!");

		this.headBody = headBody;

		buildRopeJoints();
		buildHeadJoint();
		buildMainRopeJoint();
	}

	public void untieHead() {
		if (this.headBody == null)
			return;

		destroyRopeJoints();
		destroyHeadJoint();
		destroyMainRopeJoint();

		this.headBody = null;
	}

	public void tieTail(Body tailBody) {
		if (this.tailBody != null)
			throw new UnsupportedOperationException("Rope already has a tail body tied!");

		this.tailBody = tailBody;

		buildTailJoint();
		buildMainRopeJoint();
	}

	public void untieTail() {
		if (this.tailBody == null)
			return;

		destroyTailJoint();
		destroyMainRopeJoint();

		this.tailBody = null;
	}

	private void buildRopeJoints() {
		RopeJointBuilder ropeJointBuilder = Box2DBuilders.Joint.ropeJointBuilder();
		for (int i = 1; i < segments.length; i++) {
			float segmentCenterDistanceToHeadBodyCenterY = (i + 0.5f) * segmentHeight;
			ropeJoints[i - 1] = ropeJointBuilder
					.setBodyA(headBody)
					.setBodyB(segments[i].body)
					.setMaxLength(segmentCenterDistanceToHeadBodyCenterY)
					.build(gameWorld.getWorld());
		}
	}

	private void destroyRopeJoints() {
		for (int i = 0; i < ropeJoints.length; i++) {
			gameWorld.getWorld().destroyJoint(ropeJoints[i]);
			ropeJoints[i] = null;
		}
	}

	private void buildHeadJoint() {
		headJoint = Box2DBuilders.Joint.revoluteJointBuilder()
				.setBodyA(headBody)
				.setBodyB(headSegmentBody)
				.setLocalAnchorB(0, segmentHeight / 2f)
				.build(gameWorld.getWorld());
	}

	private void destroyHeadJoint() {
		gameWorld.getWorld().destroyJoint(headJoint);
		headJoint = null;
	}

	private void buildTailJoint() {
		tailJoint = Box2DBuilders.Joint.revoluteJointBuilder()
				.setBodyA(tailBody)
				.setBodyB(tailSegmentBody)
				.setLocalAnchorB(0, -segmentHeight / 2f)
				.build(gameWorld.getWorld());
	}

	private void destroyTailJoint() {
		gameWorld.getWorld().destroyJoint(tailJoint);
		tailJoint = null;
	}

	private void buildMainRopeJoint() {
		if (tailBody == null || headBody == null)
			return;

		mainRopeJoint = Box2DBuilders.Joint.ropeJointBuilder()
				.setBodyA(headBody)
				.setBodyB(tailBody)
				.setMaxLength(ropeLength)
				.setCollideConnected(true)
				.build(gameWorld.getWorld());
	}

	private void destroyMainRopeJoint() {
		if (mainRopeJoint == null)
			return;

		gameWorld.getWorld().destroyJoint(mainRopeJoint);
		mainRopeJoint = null;
	}

	private final static Builder builder = new Builder();

	public static Builder builder() {
		builder.reset();
		return builder;
	}

	public static class Builder {
		private float ropeLength;
		private int segmentCount;
		private float segmentWidth;

		private Builder() {

		}

		private void reset() {
			ropeLength = 1f;
			segmentCount = 4;
			segmentWidth = 0.01f;
		}

		public Builder setRopeLength(float ropeLength) {
			this.ropeLength = ropeLength;
			return this;
		}

		public Builder setSegmentCount(int segmentCount) {
			this.segmentCount = segmentCount;
			return this;
		}

		public Builder setSegmentWidth(float segmentWidth) {
			this.segmentWidth = segmentWidth;
			return this;
		}

		public Rope build(GameWorld gameWorld) {
			return new Rope(this, gameWorld);
		}
	}
}
