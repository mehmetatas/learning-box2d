package com.turpgames.physics;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.turpgames.input.GdxInputManager;

public class PhysicsGame implements ApplicationListener {
	private GameWorld gameWorld;

	@Override
	public void create() {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		gameWorld = GameWorld.newBuilder().gravity(0, -10f).viewport(GameWorld.scale * width, GameWorld.scale * height).build();

		initSarkac();

		Room.newBuilder()
				.setPosition(0, 0)
				.setSize(gameWorld.getWidth(), gameWorld.getHeight())
				.setWallThickness(0.01f)
				.build(gameWorld);

		GdxInputManager.instance.activate();
	}

	private void initSarkac() {
		final float ropeLength = 1.0f;
		final float fixedPartCenterY = 2.5f;

		FixedPart fixedPart = new FixedPart(gameWorld);
		Ball ball = new Ball(gameWorld, 0.75f, fixedPartCenterY - ropeLength, 0.05f);

		Rope rope = Rope.builder().build(gameWorld);
		
		rope.tieHead(fixedPart.body);
		rope.tieTail(ball.body);
		
		gameWorld.getWorld().setContactFilter(new CollideContactFilter());

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
