package com.turpgames.input;

public class InputListener implements IInputListener {
	@Override
	public boolean touchDown(float x, float y, int pointer) {
		return false;
	}

	@Override
	public boolean touchUp(float x, float y, int pointer) {
		return false;
	}

	@Override
	public boolean touchDragged(float x, float y, int pointer) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
}
