package com.arisosoftware.fsm;


/**
 * 状态机有四个核心概念，这是所有状态机的基础: State, Transition, Event, Action.
 * 
 * State ，状态。在状态机里面是有一堆节点 StateNode
 * 
 * Transition ，状态变化。也就是从一个状态变化为另一个状态。
 * 
 * Event ，事件。事件就是执行某个操作的触发条件
 * 
 * Action ，动作。
 * 	事件发生以后要执行动作。例如事件是“借书”，动作是“借”。 编程的时候，一个 Action 一般就对应一个函数。
 * 	动作是在给定时刻要进行的活动的描述。有多种类型的动作： 进入动作（entry action）：在进入状态时进行 退出动作：在退出状态时进行
 * 	输入动作：依赖于当前状态和输入条件进行 
 * 	转移动作：在进行特定转移时进行
 * 
 * StateMachine 状态机，内含一个当前状态，能够响应输入事件．
 * 
 * 
 * 对状态机输入一个事件，状态机会根据当前状态和触发的事件唯一确定一个状态迁移。
 * 
 * @param <State>     The state of the entity
 * @param <EventType> The event type to be handled
 * @author arisosoftware@gmail.com License : MIT
 */
public class StateEvent {

}
