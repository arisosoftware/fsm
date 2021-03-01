package com.arisosoftware.fsm;

public class Transition {
 
	private String action;
	private String nextState;
	private transient AbsAction _a;

	public Transition(String message, String transState, AbsAction a) {
		action = message;
		nextState = transState;
		_a = a;
	}

	public Transition(String message, String transState) {
		this(message, transState, null);
	}

	public String getActionName() {
		return action;
	}

	public String getNextState() {
		return nextState;
	}

	public void updateAction(AbsAction act) {
		_a = act;
	}

	public AbsAction getAction() {
		return _a;
	}

}
