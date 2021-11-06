package com.arisosoftware.textanalysis.zhihu;

import org.apache.commons.io.FileUtils;

import unquietcode.tools.esm.StateMachine;
import unquietcode.tools.esm.StringStateMachine;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CleanupZhihuCopy {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		   AtomicReference<String> cur = new AtomicReference<String>(null);

			StateMachine<String> sm = new StringStateMachine();

			sm.addTransition(null, "hello");
			sm.addTransition("hello", "world", (from, to) -> cur.set(to));
			sm.addTransition("world", "goodbye ");
			sm.addTransition("GOODBYE", null);

			sm.setInitialState(null);
			sm.transition("hello");
	 
			System.out.println(sm.currentState());
			sm.transition("world");
		 
			System.out.println(sm.currentState());
			sm.transition("goodbye");
			System.out.println(sm.currentState());
			
				
//		
//		
//		File file = new File("/tmp/a1");
//
//		try {
//
//			List<String> lines = FileUtils.readLines(file);
//
//			for (int i = 0; i < 100; i++) {
//				System.out.println(lines.get(i));
//
//				String process = lines.get(i);
//
//			}
//
//			// lines.forEach(System.out::println);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}

}
