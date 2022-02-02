package com.arisosoftware.efsm;
 

public class StateMachineTest {

	enum State {
		INIT, RUNNING, PAUSED, COMPLETED, HEADER,
	};

	enum EventType {
		RUN, PAUSE, END
	};

	public static void main(String[] args) throws Exception {

		StateMachine<State, EventType> stateMachine = new StateMachineBuilder<State, EventType>(State.INIT)
				.addTransition(State.INIT, EventType.RUN, State.RUNNING)
				.addTransition(State.RUNNING, EventType.PAUSE, State.PAUSED)
				.addTransition(State.RUNNING, EventType.END, State.COMPLETED)
				.addTransition(State.PAUSED, EventType.RUN, State.RUNNING)
				.onEnter(State.RUNNING, new Runnable() {
					@Override
					public void run() {
					 
					}} )
				.build();
		
		
		stateMachine.updateStateOnEvent(EventType.RUN);

		stateMachine.updateStateOnEvent(EventType.PAUSE);

		stateMachine.updateStateOnEvent(EventType.RUN);

		stateMachine.updateStateOnEvent(EventType.END);
	};

}
