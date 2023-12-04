package p1Solution;

public class UndoManager1 {

	public static final int DEFAULT_BUFFER_SIZE = 20;

	private final UndoStack undoStack;
	private final RedoStack redoStack;

	public UndoManager1() {
		this(DEFAULT_BUFFER_SIZE);
	}

	public UndoManager1(int newUndoStackSize) {
		undoStack = new UndoStack(newUndoStackSize);
		redoStack = new RedoStack(newUndoStackSize);
	}

	public void pushUndo(UndoAction undoActivity) {
		undoStack.push(undoActivity);
	}

	public void pushRedo(UndoAction redoActivity) {
		redoStack.push(redoActivity);
	}

	public boolean isUndoable() {
		return undoStack.isUndoable();
	}

	public boolean isRedoable() {
		return redoStack.isRedoable();
	}

	public UndoAction popUndo() {
		return undoStack.pop();
	}

	public UndoAction popRedo() {
		return redoStack.pop();
	}

	public void clearUndos() {
		undoStack.clear();
	}

	public void clearRedos() {
		redoStack.clear();
	}

	public int getUndoSize() {
		return undoStack.getSize();
	}

	public int getRedoSize() {
		return redoStack.getSize();
	}
}
