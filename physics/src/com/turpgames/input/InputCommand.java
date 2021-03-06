package com.turpgames.input;

abstract class InputCommand {
	abstract boolean execute(IInputListener listener);

	final static TouchCommand touchDown = new TouchCommand.TouchDownCommand();
	final static TouchCommand touchUp = new TouchCommand.TouchUpCommand();
	final static TouchCommand touchDragged = new TouchCommand.TouchDraggedCommand();
	final static KeyCommand keyUp = new KeyCommand.KeyUpCommand();
	final static KeyCommand keyDown = new KeyCommand.KeyDownCommand();
	final static TapCommand tap = new TapCommand();
	final static LongPressCommand longPress = new LongPressCommand();
	
	abstract static class KeyCommand extends InputCommand {
		private KeyCommand() {
			
		}
		
		int keyCode;
		
		private final static class KeyUpCommand extends KeyCommand {
			private KeyUpCommand() {
				
			}
			
			@Override
			boolean execute(IInputListener listener) {
				return listener.keyUp(keyCode);
			}
		}
		
		private final static class KeyDownCommand extends KeyCommand {
			private KeyDownCommand() {
				
			}
			@Override
			boolean execute(IInputListener listener) {
				return listener.keyDown(keyCode);
			}
		}
	}
	
	final static class LongPressCommand extends InputCommand {
		float x;
		float y;
		
		private LongPressCommand() {
			
		}
		
		@Override
		boolean execute(IInputListener listener) {
			return listener.longPress(x, y);
		}
	}
	
	final static class TapCommand extends InputCommand {
		float x;
		float y;
		int count;
		
		private TapCommand() {
			
		}
		
		@Override
		boolean execute(IInputListener listener) {
			return listener.tap(x, y, count);
		}
	}
	
	abstract static class TouchCommand extends InputCommand {
		private TouchCommand() {
			
		}
		
		float x;
		float y;
		int pointer;

		private final static class TouchDownCommand extends TouchCommand {
			private TouchDownCommand() {
				
			}
			
			@Override
			boolean execute(IInputListener listener) {
				return listener.touchDown(x, y, pointer);
			}
		}

		private final static class TouchUpCommand extends TouchCommand {
			private TouchUpCommand() {
				
			}
			
			@Override
			boolean execute(IInputListener listener) {
				return listener.touchUp(x, y, pointer);
			}
		}

		private final static class TouchDraggedCommand extends TouchCommand {
			private TouchDraggedCommand() {
				
			}
			
			@Override
			boolean execute(IInputListener listener) {
				return listener.touchDragged(x, y, pointer);
			}
		}
	}
}
