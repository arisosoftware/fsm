package com.arisosoftware.fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StatNode {

	private final String _curState;
	private HashMap _transitionMap;
	private HashMap _transitions;
	private String _configFileName;

	private AbsAction stateEntry;
	private AbsAction stateExit;

	
	public StatNode(String state, HashMap map) {
		this._curState = state;
		this._transitionMap = map;
		updateNewTransitionMap();
	}

	/**
	 * Method to allow addition of Messages along with their own corresponding
	 * Action
	 * 
	 * @param message
	 * @param action
	 */
	public void addMessages(String message, Object action) {
		this._transitionMap.put(message, action);
		updateNewTransitionMap();
	}

	/**
	 * Method to update new Transition Map<br/>
	 * 
	 * @return private void updateNewTransitionMap() {
	 */
	private void updateNewTransitionMap() {
		if (_transitionMap != null) {
			if (_transitions == null)
				_transitions = new HashMap<String, Transition>();
			Iterator iter = _transitionMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry next = (Map.Entry) iter.next();
				String[] val = ((String) next.getValue()).split(":", 2);
				this._transitions.put(next.getKey(), new Transition(val[0], val[1]));
			}
		}
	}

	/**
	 * Method to allow addition of Messages along with their own corresponding
	 * Action
	 * 
	 * @param message message for which action is being assigned
	 * @param act     action method which needs to be assigned
	 */
	public void addMessageAction(String message, AbsAction act) {
		if (_transitions != null) {
			if (_transitions.containsKey(message)) {
				((Transition) _transitions.get(message)).updateAction(act);
			}
		}
	}

	/**
	 * Method to set the Action method used to specify the exit method for<br/>
	 * this state<br/>
	 * 
	 * @param act
	 */
	public void setBeforeTransition(AbsAction act) {
		this.stateEntry = act;
	}

	/**
	 * Method to set the Action method used to specify the exit method for<br/>
	 * this state<br/>
	 * 
	 * @param act
	 */
	public void setAfterTransition(AbsAction act) {
		this.stateExit = act;
	}

	/**
	 * Method to return the entire Transition Map<br/>
	 * 
	 * @return
	 */
	public HashMap getTransitionMap() {
		return this._transitionMap;
	}

	/**
	 * Method to return State-Name of the FSM State
	 * 
	 * @return
	 */
	public String getCurrentState() {
		return this._curState;
	}

	/**
	 * Method to return new entire Transition Map<br/>
	 * 
	 * @return
	 */
	public Map getNewTransitionMap() {
		return this._transitions;
	}

	public AbsAction getBeforeTransition() {
		return stateEntry;
	}

	public AbsAction getAfterTransition() {
		return stateExit;
	}
}
