package com.arisosoftware.fsm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;
 


/**
 * 状态机有四个核心概念，这是所有状态机的基础

State ，状态。一个状态机至少要包含两个状态。

Event ，事件。事件就是执行某个操作的触发条件或者口令。“借书”就是一个事件。

Action ，动作。事件发生以后要执行动作。例如事件是“借书”，动作是“借”。
		编程的时候，一个 Action 一般就对应一个函数。 动作是在给定时刻要进行的活动的描述。有多种类型的动作：
			进入动作（entry action）：在进入状态时进行
			退出动作：在退出状态时进行
			输入动作：依赖于当前状态和输入条件进行
			转移动作：在进行特定转移时进行

Transition ，状态变化。也就是从一个状态变化为另一个状态。

对状态机输入一个事件，状态机会根据当前状态和触发的事件唯一确定一个状态迁移。

 * @author airsoft
 *
 */
 
