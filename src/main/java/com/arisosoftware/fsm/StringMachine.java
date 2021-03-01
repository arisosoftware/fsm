package com.arisosoftware.fsm;

import java.util.concurrent.atomic.AtomicReference;

import unquietcode.tools.esm.StateMachine;
import unquietcode.tools.esm.StringStateMachine;

public class StringMachine {
	public void testStringState() {
	   AtomicReference<String> cur = new AtomicReference<String>(null);

		StateMachine<String> sm = new StringStateMachine();

		sm.addTransition(null, "hello");
		sm.addTransition("hello", "world", (from, to) -> cur.set(to));
		sm.addTransition("world", "goodbye ");
		sm.addTransition("GOODBYE", null);

		sm.transition("hello");
 

		sm.transition("world");
	 

		sm.transition("goodbye");
	}
}
