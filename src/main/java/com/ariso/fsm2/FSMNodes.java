package com.ariso.fsm2;

public interface FSMNodes<T> {
	public Integer NodeId();
	
	public Integer Run(T input, FSM<T> fsm);

}


 