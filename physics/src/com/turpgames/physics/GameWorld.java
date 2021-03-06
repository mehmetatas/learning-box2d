package com.turpgames.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public final class GameWorld {
	public final static float scale = 1.5f / 360f;
	
	private final static float step = 1 / 60f;
	private final static int velocityIterations = 6;
	private final static int positionIterations = 2;

	private final SpriteBatch spriteBatch;
	private final World world;
	private final OrthographicCamera camera;
	private final Box2DDebugRenderer debugRenderer;

	private GameWorld(Builder builder) {
		this.world = builder.world;
		this.camera = builder.camera;
		this.spriteBatch = new SpriteBatch();
		this.debugRenderer = new Box2DDebugRenderer();
		
		debugRenderer.setDrawJoints(false);
		debugRenderer.setDrawContacts(false);
		
		camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f);
		camera.update();
	}
	
	public World getWorld() {
		return world;
	}
	
	public float getWidth() {
		return camera.viewportWidth;
	}
	
	public float getHeight() {
		return camera.viewportHeight;
	}

	public void update() {
		world.step(step, velocityIterations, positionIterations);
	}
	
	public void unproject(Vector3 v) {
		v.x *= scale;
		v.y *= scale;
	}

	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		debugRenderer.render(world, camera.combined);
		spriteBatch.end();
	}

	public void dispose() {
		spriteBatch.dispose();
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private World world;
		private OrthographicCamera camera;

		private Builder() {

		}

		public Builder gravity(float vx, float vy) {
			this.world = new World(new Vector2(vx, vy), true);
			return this;
		}

		public Builder viewport(float viewportWidth, float viewportHeight) {
			this.camera = new OrthographicCamera(viewportWidth, viewportHeight);
			return this;
		}

		public GameWorld build() {
			return new GameWorld(this);
		}
	}
}
