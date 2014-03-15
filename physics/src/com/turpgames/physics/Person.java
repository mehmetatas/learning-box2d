package com.turpgames.physics;

import com.badlogic.gdx.math.Vector2;
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
				.setUserData(new UserData("head"));

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(neck)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData(new UserData("neck"));

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(body)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData(new UserData("body"));

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(leftLeg)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData(new UserData("leftLeg"));

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(leftLeg)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData(new UserData("leftLeg"));

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(rightLeg)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData(new UserData("rightLeg"));

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(rightFoot)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData(new UserData("rightFoot"));

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(leftFoot)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData(new UserData("leftFoot"));

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(leftArm)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData(new UserData("leftArm"));

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(rightArm)
				.setDensity(0.9f)
				.setFriction(0.1f)
				.setElasticity(0.8f)
				.build(person)
				.setUserData(new UserData("rightArm"));

		head.dispose();
		neck.dispose();
		body.dispose();
		leftLeg.dispose();
		leftFoot.dispose();
		rightLeg.dispose();
		rightFoot.dispose();
		leftArm.dispose();
		rightArm.dispose();
	}

	private final static Vector2 tmp = new Vector2();

	protected Vector2 getCenter(Fixture fixture) {
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
}
