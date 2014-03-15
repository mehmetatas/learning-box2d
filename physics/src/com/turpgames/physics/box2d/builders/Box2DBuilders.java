package com.turpgames.physics.box2d.builders;

import com.badlogic.gdx.physics.box2d.PolygonShape;

public final class Box2DBuilders {
	private Box2DBuilders() {

	}

	public static class Body {
		public static BodyBuilder newStaticBodyBuilder() {
			return BodyBuilder.newStaticBody();
		}

		public static BodyBuilder newDynamicBodyBuilder() {
			return BodyBuilder.newDynamicBody();
		}

		public static BodyBuilder newKinematicBodyBuilder() {
			return BodyBuilder.newKinematicBody();
		}
	}

	public static class Shape {
		public static PolygonShapeBuilder newPolygonBuilder() {
			return PolygonShapeBuilder.newPolygon();
		}

		public static PolygonShape buildBox(float width, float height) {
			return PolygonShapeBuilder.buildBox(width, height);
		}

		public static PolygonShape buildBox(float width, float height, float x, float y) {
			return PolygonShapeBuilder.buildBox(width, height, x, y);
		}

		public static PolygonShape buildBox(float width, float height, float centerX, float centerY, float angle) {
			return PolygonShapeBuilder.buildBox(width, height, centerX, centerY, angle);
		}

		public static CircleShapeBuilder newCircleBuilder() {
			return CircleShapeBuilder.newCircle();
		}

		public static ChainShapeBuilder newChainBuilder() {
			return ChainShapeBuilder.newChain();
		}

		public static LoopedChainShapeBuilder newLoopedChainBuilder() {
			return LoopedChainShapeBuilder.newLoopedChain();
		}
	}

	public static class Fixture {
		public static FixtureBuilder newFixtureBuilder() {
			return FixtureBuilder.newFixture();
		}
	}
	
	public static class Joint {
		public static RopeJointBuilder newRopeJointBuilder() {
			return RopeJointBuilder.newRopeJoint();
		}
		
		public static MouseJointBuilder newMouseJointBuilder() {
			return MouseJointBuilder.newMouseJoint();
		}
		
		public static DistanceJointBuilder newDistanceJointBuilder() {
			return DistanceJointBuilder.newDistanceJoint();
		}
		
		public static RevoluteJointBuilder newRevoluteJointBuilder() {
			return RevoluteJointBuilder.newRevoluteJoint();
		}
	}
}