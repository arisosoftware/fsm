package com.arisosoftware.afsm;

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
	private final List<StateAction> onEnterListeners;
	private final List<StateAction> onExitListeners;
	private final List<StateAction> onRunningListeners;

	 
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

	public void onEnter(Object data) {
		onExitListeners.forEach(action -> {
			action.run(data);
		});
	}

	public void onExit(Object data) {
		onEnterListeners.forEach(action -> {
			action.run(data);
		});
	}

	
	public void onRunning(Object data) {
		onRunningListeners.forEach(action -> {
			action.run(data);
		});
	}
 

	public void addStateEdge(EventType eventType, StateNode<State, EventType> destination) {
		stateEdge.put(eventType, destination);
	}
	
	public void addOnonRunningListener(StateAction onRunningListener) {
		onEnterListeners.add(onRunningListener);
	}

	

	public void addOnEnterListener(StateAction onEnterListener) {
		onRunningListeners.add(onEnterListener);
	}

	public void addOnExitListener(StateAction onExitListener) {
		onExitListeners.add(onExitListener);
	}
}
