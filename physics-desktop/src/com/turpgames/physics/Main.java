package com.turpgames.physics;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "physics";
		cfg.useGL20 = false;
		cfg.width = 360;
		cfg.height = 640;

		new LwjglApplication(new PhysicsGame(), cfg);
	}
}
