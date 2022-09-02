package com.ariso.fsm2;

public interface FSMNodes<T> {
	public String State();
	
	public String Run(T input, FSM<T> fsm);

}


 