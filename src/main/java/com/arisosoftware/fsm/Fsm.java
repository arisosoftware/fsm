package com.arisosoftware.fsm;

import java.util.ArrayList;

public class Fsm {

	/*
	 * Any FSM requires three things: * States * Messages * Actions
	 */
	private StatNode _fsm;
	private transient AbsAction _action;
	private transient Object _sharedData;
 
	
	
	public Object OnEvent(Event event) {
		
		
		
		
		
		Object _r;
		_r = this._fsm.getCurrentState().getNewTransitionMap().get(recvdMsgId);
		if (null != _r) {
			String[] _t = new String[2];
			_t[0] = ((FSMTransitionInfo) _r).getActionName();
			_t[1] = ((FSMTransitionInfo) _r).getNextState();
			boolean status = true;
			for (Object _f : this._fsm.getAllStates()) {
				if (((StatNode) _f).getCurrentState().equals((String) _t[1])) {
					/*
					 * Check if the action specific to each message exists If not, then in this case
					 * call the generic action function
					 */
					IAction _a = ((StatNode) _f).getBeforeTransition();
					if (_a != null) {
						_a.stateTransition(((StatNode) _f).getCurrentState(), this._sharedData);
					}

					AbsAction act = ((FSMTransitionInfo) _r).getAction();
					if (act != null) {
						/* If customized action is declared, call an entry function */
						act.entry(this._fsm.getCurrentState().getCurrentState(), (String) _t[0], (String) _t[1],
								this._sharedData);
						status = act.action(this._fsm.getCurrentState().getCurrentState(), (String) _t[0],
								(String) _t[1], this._sharedData);
					} else if (null != this._action) {
						status = this._action.action(this._fsm.getCurrentState().getCurrentState(), (String) _t[0],
								(String) _t[1], this._sharedData);
					}

					if (status) {
						this._fsm.setCurrentState((StatNode) _f);

						if (act != null) {
							act.afterTransition(this._fsm.getCurrentState().getCurrentState(), (String) _t[0],
									(String) _t[1], this._sharedData);
						} else if (null != this._action) {
							this._action.afterTransition(this._fsm.getCurrentState().getCurrentState(), (String) _t[0],
									(String) _t[1], this._sharedData);
						}
					}

					if (act != null) {
						/* Exit function called irrespective of transition status */
						act.exit(this._fsm.getCurrentState().getCurrentState(), (String) _t[0], (String) _t[1],
								this._sharedData);
					}

					IAction _b = ((StatNode) _f).getAfterTransition();
					if (_b != null) {
						_b.stateTransition(((StatNode) _f).getCurrentState(), this._sharedData);
					}

					break;
				}
			}
		}
		return _r;
	}

 
	public String getCurrentState() {
		return this._fsm.getCurrentState().getCurrentState();
	}

 
	public void setShareData(Object data) {
		this._sharedData = data;
	}
 
	public void setAction(ArrayList<String> states, String message, AbsAction act) {
		_fsm.setAction(states, message, act);
	}

 
	public void setAction(String state, String message, AbsAction act) {
		setAction(new ArrayList(Arrays.asList(state)), message, act);
	}

 
	public void setAction(String message, AbsAction act) {
		_fsm.setAction(message, act);
	}

	public void setStatesBeforeTransition(String state, IAction act) {
		_fsm.setStateBeforeTransition(state, act);
	}

	public void setStatesBeforeTransition(ArrayList<String> states, IAction act) {
		_fsm.setStateBeforeTransition(states, act);
	}

	public void setStatesBeforeTransition(IAction act) {
		ArrayList<String> l = null;
		_fsm.setStateBeforeTransition(l, act);
	}

	public void setStatesAfterTransition(String state, IAction act) {
		_fsm.setStateAfterTransition(state, act);
	}

	public void setStatesAfterTransition(ArrayList<String> states, IAction act) {
		_fsm.setStateAfterTransition(states, act);
	}

	public void setStatesAfterTransition(IAction act) {
		ArrayList<String> l = null;
		_fsm.setStateAfterTransition(l, act);
	}

	 
	public List getAllStates() {
		return _fsm.getAllStates();
	}
 
	public void setDefaultAbsAction(AbsAction act) {
		_action = act;
	}
}
