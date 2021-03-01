package com.arisosoftware.fsm;

public interface IAction {
    /**
     * 
     * @param arg
     */
    public void stateTransition(String state, Object arg);
}