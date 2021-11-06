package com.github.zevada.stateful;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StateNode<State extends Enum<State>, EventType extends Enum<EventType>> {
	private final Map<EventType, StateNode<State, EventType>> stateEdge;
	private final List<Runnable> onEnterListeners;
	private final List<Runnable> onExitListeners;
	private final State state;

	StateNode(State state) {
		this.state = state;
		stateEdge = new HashMap<>();
		onEnterListeners = new LinkedList<>();
		onExitListeners = new LinkedList<>();
	}

	public State getState() {
		return state;
	}

	public StateNode<State, EventType> getNeighbor(EventType eventType) {
		return stateEdge.get(eventType);
	}

	public void onEnter() {
		onEnterListeners.forEach(Runnable::run);
	}

	public void onExit() {
		onExitListeners.forEach(Runnable::run);
	}

	public void addStateEdge(EventType eventType, StateNode<State, EventType> destination) {
		stateEdge.put(eventType, destination);
	}

	public void addOnEnterListener(Runnable onEnterListener) {
		onEnterListeners.add(onEnterListener);
	}

	public void addOnExitListener(Runnable onExitListener) {
		onExitListeners.add(onExitListener);
	}
}
