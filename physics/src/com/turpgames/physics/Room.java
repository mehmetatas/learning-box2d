package com.turpgames.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.turpgames.physics.box2d.builders.Box2DBuilders;
import com.turpgames.physics.box2d.builders.FixtureBuilder;

public final class Room {
	private Room(GameWorld gameWorld, Builder builder) {
		createFloorAndCeiling(gameWorld, builder);
		createSideWalls(gameWorld, builder);
	}

	private void createFloorAndCeiling(GameWorld gameWorld, Builder builder) {
		Body floorBody = Box2DBuilders.Body.staticBodyBuilder()
				.setCenter(builder.width / 2, builder.wallThickness / 2)
				.build(gameWorld.getWorld());

		Body ceilingBody = Box2DBuilders.Body.staticBodyBuilder()
				.setCenter(builder.width / 2, builder.height - builder.wallThickness / 2)
				.build(gameWorld.getWorld());

		PolygonShape box = Box2DBuilders.Shape.buildBox(builder.width, builder.wallThickness, builder.position.x, builder.position.y, 0);

		FixtureBuilder fixtureBuilder = Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(box)
				.setDensity(0f);

		fixtureBuilder.build(floorBody);
		fixtureBuilder.build(ceilingBody);

		box.dispose();
	}

	private void createSideWalls(GameWorld gameWorld, Builder builder) {
		Body leftWallBody = Box2DBuilders.Body.staticBodyBuilder()
				.setCenter(builder.wallThickness / 2, builder.height / 2)
				.build(gameWorld.getWorld());

		Body rightWallBody = Box2DBuilders.Body.staticBodyBuilder()
				.setCenter(builder.width - builder.wallThickness / 2, builder.height / 2)
				.build(gameWorld.getWorld());

		PolygonShape box = Box2DBuilders.Shape.buildBox(builder.wallThickness, builder.height, builder.position.x, builder.position.y, 0);

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(box)
				.setDensity(0f)
				.build(leftWallBody);

		Box2DBuilders.Fixture.fixtureBuilder()
				.setShape(box)
				.setDensity(0f)
				.build(rightWallBody);

		box.dispose();
	}

	private final static Builder builder = new Builder();

	public static Builder newBuilder() {
		builder.reset();
		return builder;
	}

	public static class Builder {
		private float wallThickness;
		private float width;
		private float height;
		private final Vector2 position = new Vector2();

		private Builder() {
			reset();
		}

		private void reset() {
			wallThickness = 0.05f;
			width = 1f;
			height = 1f;
			position.set(0, 0);
		}

		public Builder setWallThickness(float wallThickness) {
			this.wallThickness = wallThickness;
			return this;
		}

		public Builder setPosition(float px, float py) {
			this.position.x = px;
			this.position.y = py;
			return this;
		}

		public Builder setSize(float width, float height) {
			this.width = width;
			this.height = height;
			return this;
		}

		public Room build(GameWorld gameWorld) {
			return new Room(gameWorld, this);
		}
	}
}
