package com.ariso.fsm2;

import java.util.HashMap;

public class FSM<T> {
	
	public HashMap<Integer,FSMNodes<T>> handelmap  = new HashMap<Integer,FSMNodes<T>>();
	
//	public void AddHandel(Integer code, FSMNodes<T> node)
//	{
//		handelmap.put(code, node);
//		if (CurrentNode==null)
//		{
//			CurrentNode = node;
//			CurrentCode = code;
//		}
//		
//		if (InitCode==0)
//		{
//			InitCode = code;
//		}
//	}
	
	public void AddHandel( FSMNodes<T> node)
	{
		Integer code = node.NodeId();
		
		handelmap.put(code, node);
		if (CurrentNode==null)
		{
			CurrentNode = node;
			CurrentCode = code;
		}

		if (this.InitCode==0)
		{
			this.InitCode = code;
		}
	}
	
	public Integer CurrentCode = 0;
	public String CurrentStatus ;
	public FSMNodes<T> CurrentNode = null;
	
	Integer InitCode =0;
	
	public void Run(T inputStr)
	{
		//	System.out.println("Node#:"+ CurrentCode +" Input"+inputStr.toString());
		
		Integer nextNode = CurrentNode.Run(inputStr, this);

		if (handelmap.containsKey(nextNode))
		{		
			CurrentCode = nextNode;
			CurrentNode = handelmap.get(nextNode);			
		}
		else
		{			
			System.err.print("Rtn:"+ nextNode +" no exist, system quit");
		}	
	}
	
	public void reset()
	{
		this.CurrentCode = this.InitCode;
	}
}
