package com.arisosoftware.efsm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author arisosoftware@gmail.com License : MIT
 *
 * @param <State>
 * @param <EventType>
 */
public class StateNode<State extends Enum<State>, EventType extends Enum<EventType>> {

	
	
	
	private final Map<EventType, StateNode<State, EventType>> stateEdge;
	private final List<Runnable> onEnterListeners;
	private final List<Runnable> onExitListeners;
	private final List<Runnable> onRunningListeners;
	
	private final State state;

	StateNode(State state) {
		this.state = state;
		stateEdge = new HashMap<>();
		onEnterListeners = new LinkedList<>();
		onExitListeners = new LinkedList<>();
		onRunningListeners= new LinkedList<>();
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
	
	Object EventData =null;
	
	public void onRunning (Object data) {
		EventData = data;
		onRunningListeners.forEach(Runnable::run);
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
