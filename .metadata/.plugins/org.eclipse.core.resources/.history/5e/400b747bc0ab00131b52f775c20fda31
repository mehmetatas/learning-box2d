package com.turpgames.input;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class GdxInputManager {
	public final static GdxInputManager instance = new GdxInputManager();

	private final List<IInputListener> listeners;
	private final List<IInputListener> listenersToBeRemoved;
	private final InputMultiplexer multiplexer;

	private GdxInputManager() {
		listeners = new ArrayList<IInputListener>();
		listenersToBeRemoved = new ArrayList<IInputListener>();

		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new GdxInputProcessor());
		multiplexer.addProcessor(new GestureDetector(new GdxGestureListener()));
	}

	public void activate() {
		Gdx.input.setInputProcessor(multiplexer);
	}

	public void deactivate() {
		Gdx.input.setInputProcessor(null);
	}

	public void registerListener(IInputListener listener) {
		listeners.add(listener);
	}

	public void unregisterListener(IInputListener listener) {
		listenersToBeRemoved.add(listener);
	}

	private boolean notifyListeners(InputCommand cmd) {
		boolean handled = false;
		
		for (IInputListener listener : listeners) {
			if (cmd.execute(listener)) {
				handled = true;
				break;
			}
		}
		
		for (IInputListener listener : listenersToBeRemoved) {
			listeners.remove(listener);
		}
		
		listenersToBeRemoved.clear();
		return handled;
	}

	private class GdxGestureListener implements GestureListener {
		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			return false;
		}

		@Override
		public boolean tap(float x, float y, int count, int button) {
			InputCommand.tap.x = x;
			InputCommand.tap.y = Gdx.graphics.getHeight() - y;
			InputCommand.tap.count = count;
			return notifyListeners(InputCommand.tap);
		}

		@Override
		public boolean longPress(float x, float y) {
			InputCommand.longPress.x = x;
			InputCommand.longPress.y = Gdx.graphics.getHeight() - y;
			return notifyListeners(InputCommand.longPress);
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			return false;
		}

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			return false;
		}

		@Override
		public boolean panStop(float x, float y, int pointer, int button) {
			return false;
		}

		@Override
		public boolean zoom(float initialDistance, float distance) {
			return false;
		}

		@Override
		public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
				Vector2 pointer1, Vector2 pointer2) {
			return false;
		}
	}

	private class GdxInputProcessor implements InputProcessor {
		@Override
		public boolean keyDown(int keycode) {
			InputCommand.keyDown.keyCode = keycode;
			return notifyListeners(InputCommand.keyDown);
		}

		@Override
		public boolean keyUp(int keycode) {
			InputCommand.keyUp.keyCode = keycode;
			return notifyListeners(InputCommand.keyUp);
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			InputCommand.touchDown.x = screenX;
			InputCommand.touchDown.y = Gdx.graphics.getHeight() - screenY;
			InputCommand.touchDown.pointer = pointer;
			return notifyListeners(InputCommand.touchDown);
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			InputCommand.touchUp.x = screenX;
			InputCommand.touchUp.y = Gdx.graphics.getHeight() - screenY;
			InputCommand.touchUp.pointer = pointer;
			return notifyListeners(InputCommand.touchUp);
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			InputCommand.touchDragged.x = screenX;
			InputCommand.touchDragged.y = Gdx.graphics.getHeight() - screenY;
			InputCommand.touchDragged.pointer = pointer;
			return notifyListeners(InputCommand.touchDragged);
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			return false;
		}
	}
}
