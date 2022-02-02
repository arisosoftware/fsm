package com.arisosoftware.fsm.show;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.arisosoftware.fsm.*;
import com.arisosoftware.textanalysis.n1.Chapter;

public class ZhihutxtFilter {

	enum State {
		SKIP, RECORD 
	};

	enum EventType {
		ENDOFSKIP,
		STARTSKIP,
		READLINE,
	};

	public static void main(String[] args) throws Exception {

		StateMachine<State, EventType> stateMachine
		=	new StateMachineBuilder<State, EventType>(State.SKIP)

			.addTransition(State.SKIP, EventType.ENDOFSKIP, State.RECORD)
			.addTransition(State.SKIP, EventType.READLINE, State.SKIP)
			.addTransition(State.RECORD, EventType.READLINE, State.RECORD)
			.addTransition(State.RECORD, EventType.ENDOFSKIP, State.SKIP)
				
			.onEnter(State.RECORD, new Runnable() {
					@Override
					public void run() {
						System.out.println("..");
					}} )
			
			.onRun(State.RECORD, new Runnable() {
				@Override
				public void run() {
					System.out.println("..");
				}} )
				.build();
		
		
		
		String filePath = "/tmp/a1.txt";
	   
		Scanner s = new Scanner(new File(filePath));
		while(s.hasNextLine())
		{
			String line = s.nextLine();
			if (line!=null)
			{
				stateMachine.HandleString(line);
			}
		}
		s.close();
		System.out.println("##### for JDK 8+");
		
		 
	};

}
