package p1Solution;

import java.util.ArrayList;
import java.util.List;

public class RedoStack {
	private final List<UndoAction> actions;
	private final int maxStackSize;

	public RedoStack(int maxStackSize) {
		this.maxStackSize = maxStackSize;
		actions = new ArrayList<>();
	}

	public void push(UndoAction action) {
		if (action.isRedoable()) {
			ensureMaxStackSize();
			if (!isActionAlreadyLast(action)) {
				actions.add(action);
			}
		} else {
			clear();
		}
	}

	public boolean isRedoable() {
		return !actions.isEmpty() && actions.get(actions.size() - 1).isRedoable();
	}

	public UndoAction pop() {
		if (!actions.isEmpty()) {
			return actions.remove(actions.size() - 1);
		}
		return null;
	}

	public void clear() {
		actions.clear();
	}

	public int getSize() {
		return actions.size();
	}

	private void ensureMaxStackSize() {
		if (actions.size() >= maxStackSize) {
			removeOldestAction();
		}
	}

	private void removeOldestAction() {
		actions.remove(0);
	}

	private boolean isActionAlreadyLast(UndoAction action) {
		return !actions.isEmpty() && actions.get(actions.size() - 1) == action;
	}
}
