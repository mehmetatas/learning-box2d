package com.turpgames.physics;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;

public class CollideContactFilter implements ContactFilter {
	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		UserData dataA = (UserData) fixtureA.getUserData();
		UserData dataB = (UserData) fixtureB.getUserData();
		return ((dataA == null || dataA.isCollidable()) && (dataB == null || dataB.isCollidable()));
	}
}
