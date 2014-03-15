package com.turpgames.physics;

public class UserData {
	private boolean isCollidable;
	private String name;

	public UserData() {
		this(true);
	}

	public UserData(boolean isCollidable) {
		this(isCollidable, "");
	}

	public UserData(String name) {
		this(true, name);
	}

	public UserData(boolean isCollidable, String name) {
		this.isCollidable = isCollidable;
		this.name = name;
	}

	public boolean isCollidable() {
		return isCollidable;
	}

	public void setCollidable(boolean isCollidable) {
		this.isCollidable = isCollidable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
