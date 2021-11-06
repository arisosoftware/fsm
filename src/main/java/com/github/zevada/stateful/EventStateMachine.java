package com.github.zevada.stateful;

/**
 * A simple event based state machine.
 *
 * @param <State>     The state of the entity
 * @param <EventType> The event type to be handled
 */
public final class EventStateMachine<State extends Enum<State>, EventType extends Enum<EventType>> {
	private StateNode<State, EventType> root;

	EventStateMachine(StateNode<State, EventType> root) {
		this.root = root;
	}

	/**
	 * execute Event 
	 *
	 * @param eventType The event type to be handled
	 * @throws Exception
	 */
	public void onEvent(EventType eventType) throws Exception {

		StateNode<State, EventType> nextNode = root.getNeighbor(eventType);

		if (nextNode == null) {
			String errormessage = String.format("?,?", root.getState(), eventType);
			throw new Exception(errormessage);
		}

		root.onExit();
		root = nextNode;
		root.onEnter();
	}

	/**
	 * @return The current state of the state machine
	 */
	public State getState() {
		return root.getState();
	}
}
