package com.turpgames.input;

public interface IInputListener {
	boolean touchDown(float x, float y, int pointer);

	boolean touchUp(float x, float y, int pointer);

	boolean touchDragged(float x, float y, int pointer);

	boolean tap(float x, float y, int count);

	boolean longPress(float x, float y);

	boolean keyDown(int keycode);

	boolean keyUp(int keycode);
}
