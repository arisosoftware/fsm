package com.arisosoftware.fsm;

public abstract class AbsAction {

    
    public abstract boolean action(String curState, String message, String nextState, Object args);
    
    
    public void afterTransition(String curState, String message, String nextState, Object args) {
    	//Please Override it
    }
    
    public void entry(String curState, String message, String nextState, Object args) {
    	//Please Override it
    }

    public void exit(String curState, String message, String nextState, Object args) {
    	//Please Override it
    }
}
