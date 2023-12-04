package p1Solution;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class UndoManager1Test {
	private UndoManager1 undoManager;

	@Before
	public void setUp() {
		undoManager = new UndoManager1(3); // Create an UndoManager1 with a stack size of 3
	}

	@Test
	public void testPushUndoAndPopUndo() {
		UndoAction action1 = new UndoAction(true, false);
		UndoAction action2 = new UndoAction(true, false);
		UndoAction action3 = new UndoAction(true, false);

		undoManager.pushUndo(action1);
		undoManager.pushUndo(action2);
		undoManager.pushUndo(action3);

		assertEquals(3, undoManager.getUndoSize());
		assertEquals(action3, undoManager.popUndo());
		assertEquals(action2, undoManager.popUndo());
		assertEquals(action1, undoManager.popUndo());
		assertEquals(0, undoManager.getUndoSize());
	}

	@Test
	public void testPushRedoAndPopRedo() {
		UndoAction action1 = new UndoAction(false, true);
		UndoAction action2 = new UndoAction(false, true);
		UndoAction action3 = new UndoAction(false, true);

		undoManager.pushRedo(action1);
		undoManager.pushRedo(action2);
		undoManager.pushRedo(action3);

		assertEquals(3, undoManager.getRedoSize());
		assertEquals(action3, undoManager.popRedo());
		assertEquals(action2, undoManager.popRedo());
		assertEquals(action1, undoManager.popRedo());
		assertEquals(0, undoManager.getRedoSize());
	}

	@Test
	public void testIsUndoable() {
		assertFalse(undoManager.isUndoable());

		UndoAction undoableAction = new UndoAction(true, false);
		undoManager.pushUndo(undoableAction);
		assertTrue(undoManager.isUndoable());

		UndoAction nonUndoableAction = new UndoAction(false, false);
		undoManager.pushUndo(nonUndoableAction);
		assertFalse(undoManager.isUndoable());
	}

	@Test
	public void testIsRedoable() {
		assertFalse(undoManager.isRedoable());

		UndoAction redoableAction = new UndoAction(false, true);
		undoManager.pushRedo(redoableAction);
		assertTrue(undoManager.isRedoable());

		UndoAction nonRedoableAction = new UndoAction(false, false);
		undoManager.pushRedo(nonRedoableAction);
		assertFalse(undoManager.isRedoable());
	}

	@Test
	public void testClearUndos() {
		UndoAction action1 = new UndoAction(true, false);
		UndoAction action2 = new UndoAction(true, false);

		undoManager.pushUndo(action1);
		undoManager.pushUndo(action2);

		assertEquals(2, undoManager.getUndoSize()); // Check that there are two actions in the undo stack before
													// clearing

		undoManager.clearUndos(); // Clear the undo stack

		assertEquals(0, undoManager.getUndoSize()); // Check that the undo stack is now empty
	}

	@Test
	public void testClearRedos() {
		UndoAction action1 = new UndoAction(false, true);
		UndoAction action2 = new UndoAction(false, true);
		UndoAction action3 = new UndoAction(false, true);

		undoManager.pushRedo(action1);
		undoManager.pushRedo(action2);
		undoManager.pushRedo(action3);

		undoManager.clearRedos();

		assertEquals(0, undoManager.getRedoSize());
	}

	@Test
	public void testPushUndoAndPopUndoWithEmptyStack() {
		assertNull(undoManager.popUndo());
	}

	@Test
	public void testPushRedoAndPopRedoWithEmptyStack() {
		assertNull(undoManager.popRedo());
	}

	@Test
	public void testPushUndoAndPopUndoWithNonUndoableAction() {
		UndoAction nonUndoableAction = new UndoAction(false, false);
		undoManager.pushUndo(nonUndoableAction);

		assertFalse(undoManager.isUndoable());
		assertNull(undoManager.popUndo());
	}

	@Test
	public void testPushRedoAndPopRedoWithNonRedoableAction() {
		UndoAction nonRedoableAction = new UndoAction(false, false);
		undoManager.pushRedo(nonRedoableAction);

		assertFalse(undoManager.isRedoable());
		assertNull(undoManager.popRedo());
	}

	@Test
	public void testPushUndoAndPopUndoWithMaxStackSize() {
		UndoAction action1 = new UndoAction(true, false);
		UndoAction action2 = new UndoAction(true, false);
		UndoAction action3 = new UndoAction(true, false);
		UndoAction action4 = new UndoAction(true, false);

		undoManager.pushUndo(action1);
		undoManager.pushUndo(action2);
		undoManager.pushUndo(action3);
		undoManager.pushUndo(action4); // This should remove the oldest action (action1)

		assertEquals(3, undoManager.getUndoSize());
		assertEquals(action4, undoManager.popUndo());
		assertEquals(action3, undoManager.popUndo());
		assertEquals(action2, undoManager.popUndo());
		assertEquals(0, undoManager.getUndoSize());
	}

}
