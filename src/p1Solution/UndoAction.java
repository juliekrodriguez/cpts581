package p1Solution;

public class UndoAction {
	private final boolean undoable;
	private final boolean redoable;

	public UndoAction(boolean undoable, boolean redoable) {
		this.undoable = undoable;
		this.redoable = redoable;
	}

	public boolean isUndoable() {
		return undoable;
	}

	public boolean isRedoable() {
		return redoable;
	}
}
