package p1Solution;

import java.util.ArrayList;
import java.util.List;

public class UndoStack {
	private final List<UndoAction> actions;
	private final int maxStackSize;

	public UndoStack(int maxStackSize) {
		this.maxStackSize = maxStackSize;
		actions = new ArrayList<>();
	}

	public void push(UndoAction action) {
		if (action.isUndoable()) {
			ensureMaxStackSize();
			actions.add(action);
		} else {
			clearStack();
		}
	}

	public boolean isUndoable() {
		return !actions.isEmpty() && actions.get(actions.size() - 1).isUndoable();
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

	private void clearStack() {
		actions.clear();
	}
}
