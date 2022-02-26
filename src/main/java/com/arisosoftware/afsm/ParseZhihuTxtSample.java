//package com.arisosoftware.afsm;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//import com.arisosoftware.afsm.*;
//
//import io.vertx.core.Verticle;
//import io.vertx.core.Vertx;
//import io.vertx.core.buffer.Buffer;
//import io.vertx.core.eventbus.EventBus;
//import io.vertx.core.parsetools.RecordParser;
//
//
//public class ParseZhihuTxtSample {
//
//	enum State {
//		SKIP, RECORD 
//	};
//
//	enum EventType {
//		ENDOFSKIP,
//		STARTSKIP,
//		READLINE,
//	};
//	
//	 
//
//	public static void main(String[] args) throws Exception {
//
//		
//	//	Vertx vertx = Vertx.vertx();
//		
////		final RecordParser parser = RecordParser.newDelimited("\n", h -> {
////			  System.out.println(h.toString());
////			});
////		parser.handle(Buffer.buffer("HELLO\nHOW ARE Y"));
////		parser.handle(Buffer.buffer("OU?\nI AM"));
////		parser.handle(Buffer.buffer("DOING OK"));
////		parser.handle(Buffer.buffer("\n"));
//		
////		
//		StateMachine<State, EventType> stateMachine
//		=	new StateMachineBuilder<State, EventType>(State.SKIP)
//			.addTransition(State.SKIP, EventType.ENDOFSKIP, State.RECORD)
//			.addTransition(State.SKIP, EventType.READLINE, State.SKIP)
//			.addTransition(State.RECORD, EventType.READLINE, State.RECORD)
//			.addTransition(State.RECORD, EventType.ENDOFSKIP, State.SKIP)
//			 .build();
// 
////	
//		String filePath = "/tmp/a1";
//		Scanner s = new Scanner(new File(filePath));
//		while(s.hasNextLine())
//		{
//			String line = s.nextLine();
//			if (line!=null)
//			{
//				stateMachine.OnRun(line);;
//			}
//		}
//		s.close();
//		System.out.println("##### for JDK 8+");
//		
//		 
//	};
//
//}
