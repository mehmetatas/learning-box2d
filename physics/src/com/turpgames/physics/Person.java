package com.turpgames.physics;

import com.badlogic.gdx.math.Vector2;
import com.turpgames.input.GdxInputManager;
import com.turpgames.input.InputListener;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.turpgames.physics.box2d.builders.Box2DBuilders;

public class Person { 
	private final Body person;

	public Person(GameWorld gameWorld) {
		person = Box2DBuilders.Body.dynamicBodyBuilder()
				.setCenter(gameWorld.getWidth() / 2, 0.1f)
				.build(gameWorld.getWorld());
		
		CircleShape head = Box2DBuilders.Shape.circleBuilder()
				.setPosition(0f, 1.65f)
				.setRadius(0.1f)
				.build();

		PolygonShape neck = Box2DBuilders.Shape.buildBox(0.05f, 0.05f, 0, 1.525f, 0f);
		PolygonShape body = Box2DBuilders.Shape.buildBox(0.4f, 0.6f, 0, 1.20f, 0f);
		PolygonShape leftLeg = Box2DBuilders.Shape.buildBox(0.15f, 0.8f, -0.125f, 0.5f, 0f);
		PolygonShape leftFoot = Box2DBuilders.Shape.buildBox(0.2f, 0.1f, -0.15f, 0.05f, 0f);
		PolygonShape rightLeg = Box2DBuilders.Shape.buildBox(0.15f, 0.8f, 0.125f, 0.5f, 0f);
		PolygonShape rightFoot = Box2DBuilders.Shape.buildBox(0.2f, 0.1f, 0.15f, 0.05f, 0f);
		PolygonShape leftArm = Box2DBuilders.Shape.buildBox(0.1f, 0.6f, -0.25f, 1.20f, 0f);
		PolygonShape rightArm = Box2DBuilders.Shape.buildBox(0.1f, 0.6f, 0.25f, 1.20f, 0f);

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(head)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData("head");

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(neck)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData("neck");

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(body)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData("body");

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(leftLeg)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData("leftLeg");

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(leftLeg)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData("leftLeg");

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(rightLeg)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData("rightLeg");

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(rightFoot)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData("rightFoot");

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(leftFoot)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData("leftFoot");

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(leftArm)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData("leftArm");

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(rightArm)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData("rightArm");

		head.dispose();
		neck.dispose();
		body.dispose();
		leftLeg.dispose();
		leftFoot.dispose();
		rightLeg.dispose();
		rightFoot.dispose();
		leftArm.dispose();
		rightArm.dispose();

		GdxInputManager.instance.registerListener(inputListener);
	}

	private void onTouchDownInside(float x, float y, Fixture fixture) {
		Vector2 fixtureCenter = getCenter(fixture);
		float cx = person.getPosition().x + fixtureCenter.x; // MathUtils.cos(man.getAngle()) * (fixtureCenter.x - man.getPosition().x);
		float cy = person.getPosition().y + fixtureCenter.y; // MathUtils.sin(man.getAngle()) * (fixtureCenter.y - man.getPosition().y);
		
		float dx = x - cx;
		float dy = y - cy;
//		System.out.println(String.format("%s: %f %f", fixture.getUserData(), dx, dy));
//		System.out.println(String.format("center: %f %f", fixtureCenter.x, fixtureCenter.y));
//		System.out.println(String.format("person: %f %f", person.getPosition().x, person.getPosition().y));
		person.applyLinearImpulse(dx * 5, dy * 5, cx, cy, true);
	}

	private boolean onTouchDown(float x, float y) {
		x = GameWorld.scale * x;
		y = GameWorld.scale * y;
		for (Fixture fixture : person.getFixtureList()) {
			if (fixture.testPoint(x, y)) {
				onTouchDownInside(x, y, fixture);
				return true;
			}
		}
		return false;
	}

	private final static Vector2 tmp = new Vector2();

	private Vector2 getCenter(Fixture fixture) {
		Shape shape = fixture.getShape();

		if (shape.getType() == Type.Circle) {
			CircleShape circle = (CircleShape) shape;
			return circle.getPosition();
		} else if (shape.getType() == Type.Polygon) {
			PolygonShape polygon = (PolygonShape) shape;

			tmp.set(0, 0);

			int vertexCount = polygon.getVertexCount();
			final Vector2 vertice = new Vector2();
			for (int i = 0; i < vertexCount; i++) {
				polygon.getVertex(i, vertice);
				tmp.x += vertice.x;
				tmp.y += vertice.y;
			}

			tmp.x /= (float) vertexCount;
			tmp.y /= (float) vertexCount;

			return tmp;
		}

		throw new UnsupportedOperationException("Unsupported shape type " + shape.getType());
	}

	private final InputListener inputListener = new InputListener() {
		@Override
		public boolean touchDown(float x, float y, int pointer) {
			return onTouchDown(x, y);
		}
	};
}
