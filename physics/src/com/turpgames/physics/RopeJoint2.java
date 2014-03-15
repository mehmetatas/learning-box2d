package com.turpgames.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.turpgames.physics.box2d.builders.Box2DBuilders;

public class RopeJoint2 {
	public static void build(World world, Body bodyA, Body bodyB, float x, float y, float length, int pieces) {
		float pieceLength = length / (float) pieces;

		Body[] pieceBodies = new Body[pieces + 1];
		pieceBodies[0] = bodyA;
		pieceBodies[pieces] = bodyB;

		for (int i = 1; i < pieces; i++) {
			pieceBodies[i] = Box2DBuilders.Body.dynamicBodyBuilder()
					.setCenter(x, y + i * pieceLength)
					.build(world, Box2DBuilders.Fixture.fixtureBuilder()
							.setDensity(0.1f)
							.setElasticity(1f)
							.setFriction(0.1f)
							.setShape(Box2DBuilders.Shape.circleBuilder().setRadius(0.001f).build()));

			Box2DBuilders.Joint.distanceJointBuilder()
					.setBodyA(pieceBodies[i - 1])
					.setBodyB(pieceBodies[i])
					.setLength(pieceLength)
					.build(world);
		}

		Box2DBuilders.Joint.distanceJointBuilder()
				.setBodyA(pieceBodies[pieces - 1])
				.setBodyB(bodyB)
				.setLength(pieceLength)
				.build(world);
	}
}
