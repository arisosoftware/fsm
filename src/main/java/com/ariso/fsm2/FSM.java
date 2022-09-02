package com.ariso.fsm2;

import java.util.HashMap;

public class FSM<T> {

	public HashMap<String, FSMNodes<T>> handelmap = new HashMap<String, FSMNodes<T>>();

	public String CurrentStatus;
	public FSMNodes<T> CurrentNode = null;

	String InitCode;

	public void AddHandel(FSMNodes<T> node) {
		String code = node.State();

		handelmap.put(code, node);
		if (CurrentNode == null) {
			CurrentNode = node;
			CurrentStatus = code;
		}

		if (this.InitCode == null) {
			this.InitCode = code;
		}
	}

	String previousStatus;

	public void Run(T inputStr) {
		if (previousStatus == null) {
			previousStatus = CurrentStatus;
		//	System.out.println("Status:" + previousStatus);
		}



		String nextNode = CurrentNode.Run(inputStr, this);

		if (handelmap.containsKey(nextNode)) {
			CurrentStatus = nextNode;
			CurrentNode = handelmap.get(nextNode);
		} else {
			System.err.print("Rtn:" + nextNode + " no exist, system quit");
		}

//		if (!previousStatus.equals(CurrentStatus)) {
//			System.out.println("Change from " + previousStatus + " --> " + CurrentStatus);
//			previousStatus = CurrentStatus;
//		}

		System.out.println( CurrentStatus+">>" + inputStr.toString());
		
	}

	public void reset() {
		this.CurrentStatus = this.InitCode;
	}
}
