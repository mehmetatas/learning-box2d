package com.turpgames.physics;

public class UserData {
	private boolean isCollidable;

	public UserData() {
		this(true);
	}

	public UserData(boolean isCollidable) {
		this.isCollidable = isCollidable;
	}

	public boolean isCollidable() {
		return isCollidable;
	}

	public void setCollidable(boolean isCollidable) {
		this.isCollidable = isCollidable;
	}
}
