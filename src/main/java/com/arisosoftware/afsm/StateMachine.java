//package com.arisosoftware.afsm;
//
///**
// * 状态机有四个核心概念，这是所有状态机的基础: State, Transition, Event, Action.
// * 
// * State ，状态。在状态机里面是有一堆节点 StateNode
// * 
// * Transition ，状态变化。也就是从一个状态变化为另一个状态。
// * 
// * Event ，事件。事件就是执行某个操作的触发条件
// * 
// * Action ，动作。
// * 	事件发生以后要执行动作。例如事件是“借书”，动作是“借”。 编程的时候，一个 Action 一般就对应一个函数。
// * 	动作是在给定时刻要进行的活动的描述。有多种类型的动作： 
// * 			进入动作（entry action） 
// * 			退出动作：在退出状态时进行
// * 	输入动作：依赖于当前状态和输入条件进行 
// * 	转移动作：在进行特定转移时进行
// * 
// * StateMachine 状态机，内含一个当前状态，能够响应输入事件．
// * 
// * State Class
// *     StateId
// *      Map(EventId, NextStateId)
// * Event -Class
// *     Event Id
// *     Event Name
// * 
// * 
// * 
// * 对状态机输入一个事件，状态机会根据当前状态和触发的事件唯一确定一个状态迁移。并调用相应event handler runner
// * 
// * @param <State>     The state of the entity
// * @param <EventType> The event type to be handled
// * @author arisosoftware@gmail.com License : MIT
// */
//public class StateMachine<State extends Enum<State>, EventType extends Enum<EventType>> {
//
//	private StateNode<State, EventType> currentState;
//
//	StateMachine(StateNode<State, EventType> startPoint) {
//		this.currentState = startPoint;
//	}
//
//	
//	public void OnRun(Object data)
//	{
//		EventData = data;
//		currentState.onRunning(data);
//	}
//	
//	public Object EventData = null;
//	
//	
//	
//	/**
//	 * execute Event
//	 *　响应状态，如果需要
//	 * @param eventType The event type to be handled
//	 * @throws Exception
//	 */
//	public void OnEvent(Object data) throws Exception {
//
//		EventType eventType = null;
//		StateNode<State, EventType> nextState = currentState.getNeighbor(eventType);
//
//		if (nextState == null) {
//			String errormessage = String.format("?,?", currentState.getState(), eventType);
//			throw new Exception(errormessage);
//		}
//
//		if (nextState == currentState)
//		{
//			currentState.onRunning(EventData);
//		}
//		else
//		{
//			currentState.onExit();
//			currentState = nextState;
//			currentState.onEnter();
//		}
//	}
//
//	/**
//	 * @return The current state of the state machine
//	 */
//	public State getState() {
//		return currentState.getState();
//	}
//}
