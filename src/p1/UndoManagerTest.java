package p1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.FigureEnumeration;
import CH.ifa.draw.util.Undoable;

public class UndoManagerTest {

	@Test
	public void testDefaultConstructor() {
		UndoManager undoManager = new UndoManager();
		assertNotNull(undoManager);
		assertEquals(0, undoManager.getUndoSize());
		assertEquals(0, undoManager.getRedoSize());
		assertFalse(undoManager.isUndoable());
		assertFalse(undoManager.isRedoable());
	}

	@Test
	public void testConstructorWithBufferSize() {
		int bufferSize = 10;
		UndoManager undoManager = new UndoManager(bufferSize);
		assertNotNull(undoManager);
		assertEquals(0, undoManager.getUndoSize());
		assertEquals(0, undoManager.getRedoSize());
		assertFalse(undoManager.isUndoable());
		assertFalse(undoManager.isRedoable());
	}

	@Test
	public void testMaxBufferSizeReached() {
		int bufferSize = 20;
		UndoManager undoManager = new UndoManager(bufferSize);

		// Push undo activities until the buffer is full
		for (int i = 0; i < bufferSize; i++) {
			undoManager.pushUndo(new TestUndoable(true, true));
		}

		// Push one more undo activity, causing the oldest one to be removed
		undoManager.pushUndo(new TestUndoable(true, true));

		assertEquals(bufferSize, undoManager.getUndoSize());
		assertFalse(undoManager.isRedoable());
	}

	@Test
	public void testPushUndo() {
		UndoManager undoManager = new UndoManager();
		// Case 1: Push Undoable Activity
		TestUndoable undoable1 = new TestUndoable(true, true);
		undoManager.pushUndo(undoable1);

		assertEquals(1, undoManager.getUndoSize());
		assertTrue(undoManager.isUndoable());
		assertFalse(undoManager.isRedoable());

		// Case 2: Push Not Undoable Activity
		TestUndoable undoable2 = new TestUndoable(false, false);
		undoManager.pushUndo(undoable2);

		assertEquals(0, undoManager.getUndoSize()); // Stack is cleared
		assertFalse(undoManager.isUndoable());
		assertFalse(undoManager.isRedoable());

		// Case 3: Exceed Buffer Size
		int bufferSize = 2; // Set a small buffer size for testing
		undoManager = new UndoManager(bufferSize);

		TestUndoable undoable3 = new TestUndoable(true, true);
		TestUndoable undoable4 = new TestUndoable(true, true);
		TestUndoable undoable5 = new TestUndoable(true, true);

		undoManager.pushUndo(undoable3);
		undoManager.pushUndo(undoable4);
		undoManager.pushUndo(undoable5); // Exceed buffer size

		assertEquals(bufferSize, undoManager.getUndoSize()); // Oldest activity removed
		assertTrue(undoManager.isUndoable());
		assertFalse(undoManager.isRedoable());
	}

	@Test
	public void testPushRedo() {
		UndoManager undoManager = new UndoManager();
		TestUndoable redoable = new TestUndoable(true, true);

		undoManager.pushRedo(redoable);

		assertEquals(1, undoManager.getRedoSize());
		assertFalse(undoManager.isUndoable());
		assertTrue(undoManager.isRedoable());
	}

	@Test
	public void testIsUndoable() {
		UndoManager undoManager = new UndoManager();
		assertFalse(undoManager.isUndoable());

		TestUndoable undoable = new TestUndoable(true, true);
		undoManager.pushUndo(undoable);

		assertTrue(undoManager.isUndoable());
	}

	@Test
	public void testIsRedoable() {
		UndoManager undoManager = new UndoManager();
		assertFalse(undoManager.isRedoable());

		TestUndoable redoable = new TestUndoable(true, true);
		undoManager.pushRedo(redoable);

		assertTrue(undoManager.isRedoable());
	}

	@Test
	public void testClearEmptyUndoStack() {
		UndoManager undoManager = new UndoManager();

		// Clearing an empty undo stack should not throw an exception
		undoManager.clearUndos();

		assertEquals(0, undoManager.getUndoSize());
		assertFalse(undoManager.isUndoable());
	}

	@Test
	public void testClearEmptyRedoStack() {
		UndoManager undoManager = new UndoManager();

		// Clearing an empty redo stack should not throw an exception
		undoManager.clearRedos();

		assertEquals(0, undoManager.getRedoSize());
		assertFalse(undoManager.isRedoable());
	}

	@Test
	public void testPushUndoAndRedoAlternately() {
		UndoManager undoManager = new UndoManager();
		TestUndoable undoable = new TestUndoable(true, true);

		// Push undo and redo activities alternately
		undoManager.pushUndo(undoable);
		undoManager.pushRedo(undoable);
		undoManager.pushUndo(undoable);

		assertEquals(2, undoManager.getUndoSize());
		assertEquals(1, undoManager.getRedoSize());
		assertTrue(undoManager.isUndoable());
		assertTrue(undoManager.isRedoable());
	}

	@Test
	public void testPushAfterClearingUndoStack() {
		UndoManager undoManager = new UndoManager();
		TestUndoable undoable = new TestUndoable(true, true);

		// Push an undo activity, clear undo stack, then push another undo activity
		undoManager.pushUndo(undoable);
		undoManager.clearUndos();
		undoManager.pushUndo(undoable);

		assertEquals(1, undoManager.getUndoSize());
		assertFalse(undoManager.isRedoable());
	}

	@Test
	public void testPushAfterClearingRedoStack() {
		UndoManager undoManager = new UndoManager();
		TestUndoable redoable = new TestUndoable(true, true);

		// Push a redo activity, clear redo stack, then push another redo activity
		undoManager.pushRedo(redoable);
		undoManager.clearRedos();
		undoManager.pushRedo(redoable);

		assertEquals(1, undoManager.getRedoSize());
		assertFalse(undoManager.isUndoable());
	}

	@Test
	public void testPopUndo() {
		UndoManager undoManager = new UndoManager();
		TestUndoable undoable = new TestUndoable(true, true);
		undoManager.pushUndo(undoable);

		Undoable poppedUndoable = undoManager.popUndo();

		assertEquals(undoable, poppedUndoable);
		assertEquals(0, undoManager.getUndoSize());
		assertFalse(undoManager.isUndoable());
	}

	@Test
	public void testPopRedo() {
		UndoManager undoManager = new UndoManager();
		TestUndoable redoable = new TestUndoable(true, true);
		undoManager.pushRedo(redoable);

		Undoable poppedRedoable = undoManager.popRedo();

		assertEquals(redoable, poppedRedoable);
		assertEquals(0, undoManager.getRedoSize());
		assertFalse(undoManager.isRedoable());
	}

	@Test
	public void testClearUndos() {
		UndoManager undoManager = new UndoManager();
		TestUndoable undoable = new TestUndoable(true, true);
		undoManager.pushUndo(undoable);

		undoManager.clearUndos();

		assertEquals(0, undoManager.getUndoSize());
		assertFalse(undoManager.isUndoable());
	}

	@Test
	public void testClearRedos() {
		UndoManager undoManager = new UndoManager();
		TestUndoable redoable = new TestUndoable(true, true);
		undoManager.pushRedo(redoable);

		undoManager.clearRedos();

		assertEquals(0, undoManager.getRedoSize());
		assertFalse(undoManager.isRedoable());
	}

	// Helper class for testing
	private static class TestUndoable implements Undoable {
		private boolean isUndoable;
		private boolean isRedoable;

		public TestUndoable(boolean isUndoable, boolean isRedoable) {
			this.isUndoable = isUndoable;
			this.isRedoable = isRedoable;
		}

		@Override
		public boolean isUndoable() {
			return isUndoable;
		}

		@Override
		public boolean isRedoable() {
			return isRedoable;
		}

		@Override
		public FigureEnumeration getAffectedFigures() {
			// TODO: Implement if needed for testing
			return null;
		}

		@Override
		public int getAffectedFiguresCount() {
			// TODO: Implement if needed for testing
			return 0;
		}

		@Override
		public DrawingView getDrawingView() {
			// TODO: Implement if needed for testing
			return null;
		}

		@Override
		public void release() {
			// TODO: Implement if needed for testing
		}

		@Override
		public void setAffectedFigures(FigureEnumeration arg0) {
			// TODO: Implement if needed for testing
		}

		@Override
		public void setRedoable(boolean arg0) {
			// TODO: Implement if needed for testing
		}

		@Override
		public void setUndoable(boolean arg0) {
			// TODO: Implement if needed for testing
		}

		@Override
		public boolean redo() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean undo() {
			// TODO Auto-generated method stub
			return false;
		}
	}
}