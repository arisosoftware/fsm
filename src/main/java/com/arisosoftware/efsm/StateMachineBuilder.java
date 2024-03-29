package com.arisosoftware.efsm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author arisosoftware@gmail.com License : MIT
 *
 * @param <State>     The state of the entity
 * @param <EventType> The event type to be handled
 */
public class StateMachineBuilder<State extends Enum<State>, EventType extends Enum<EventType>> {

	private final Map<State, StateNode<State, EventType>> nodes;
	private final StateNode<State, EventType> root;

	/**
	 * @param initialState the initial state of the state machine
	 */
	public StateMachineBuilder(State initialState) {
		nodes = new HashMap<>();
		root = new StateNode<>(initialState);
		nodes.put(initialState, root);
	}

	/**
	 * Use this method to construct the state machine after completing the
	 * declaration of state machine topology and listeners.
	 *
	 * @return the final state machine
	 */
	public StateMachine<State, EventType> build() {
		return new StateMachine<>(root);
	}

	/**
	 * Add a transition to the state machine from "startState" to "endState" in
	 * response to events of type "eventType"
	 *
	 * @param startState the starting state of the transition
	 * @param eventType  the event type that triggered the transition
	 * @param endState   the end state of the transition
	 */
	public StateMachineBuilder<State, EventType> addTransition(State startState, EventType eventType, State endState) {
		
		StateNode<State, EventType> startNode = nodes.get(startState);

		if (startNode == null) {
			startNode = new StateNode<>(startState);
			nodes.put(startState, startNode);
		}

		StateNode<State, EventType> endNode = nodes.get(endState);

		if (endNode == null) {
			endNode = new StateNode<>(endState);
			nodes.put(endState, endNode);
		}

		startNode.addStateEdge(eventType, endNode);

		return this;
	}

	/**
	 * Add a runnable to the state machine which will only be executed when the
	 * state machine enters the specified state.
	 *
	 * @param state   The state for which we are listening to onEnter events
	 * @param onEnter The runnable to call when the state is entered
	 */
	public StateMachineBuilder<State, EventType> onEnter(State state, Runnable onEnter) {
		StateNode<State, EventType> node = nodes.get(state);

		if (node == null) {
			node = new StateNode<>(state);
			nodes.put(state, node);
		}

		node.addOnEnterListener(onEnter);

		return this;
	}

	/**
	 * Add a runnable to the state machine which will only be executed when the
	 * state machine exits the specified state.
	 *
	 * @param state  The state for which we are listening to onExit events
	 * @param onExit The runnable to call when the state is exited
	 */
	public StateMachineBuilder<State, EventType> onExit(State state, Runnable onExit) {
		StateNode<State, EventType> node = nodes.get(state);

		if (node == null) {
			node = new StateNode<>(state);
			nodes.put(state, node);
		}

		node.addOnExitListener(onExit);

		return this;
	}
}
