package com.arisosoftware.textanalysis.n1;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class SplitTxtNovel2 {

	static Pattern p1 = Pattern.compile("<![^>]*>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	public static void main(String[] args) throws Exception {
		String filePath = "/tmp/sdyx"; //射雕英雄
		
	 
		
		//这篇java的目的是把所有 :   说道: 的前3个字找出来,然后统计出最多的列表.
		
		ArrayList<String> nameList = new ArrayList<String>(); 
		
		Scanner s = new Scanner(new File(filePath));
		while (s.hasNextLine()) {
			String line = s.nextLine();
			if (line != null) {

				line = line.replace("”", ".");
				line = line.replace("。", ".");
				line = line.replace("，", ".");
				line = line.replace("：", ".");
				line = line.replace("？", ".");
				 line = line.replace("“", ".");
				 line = line.replace("；", ".");
				 line = line.replace("）", ".");
				 line = line.replace("‘", ".");
				 line = line.replace("！", "."); 
				 line = line.replace("（", ".");
				 
				 
				if (line.indexOf("道")>0)
				{
//					int fId = line.indexOf("说道：“");
//					
//					if (fId>5)
//					{
//						String name5w = line.substring(fId-5,fId);
//						nameList.add(name5w);
//					}
					
					for(int i=0; i+4<line.length(); i++) {
						String word = line.substring(i,i+4);
						nameList.add(word);
					}
				}
				
				}

		}
		s.close();
		System.out.println("##### for JDK 8+");

		
		 
		FileOutputStream fos = new FileOutputStream(filePath + "X");
		

		try (Writer w = new OutputStreamWriter(fos, "UTF-8")) {
			nameList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet()
					.stream()
					
					.sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
							.thenComparing(Map.Entry.comparingByKey()))
					.filter((k)-> {
				 		
						String key = k.getKey().toString();
						long val = k.getValue();
						
						if (val<22)
							return false;
						
						
						return true;
					})
					.forEach((k) -> {
						try {
							w.write(k.getKey().toString()+":"+k.getValue().toString()+"\n");
						} catch (IOException e) {
						}
					} ); // output
		} catch (IOException e) {
			e.printStackTrace();
		}

//	    
//	    
//		
//
//		try (Writer w = new OutputStreamWriter(fos, "UTF-8")) { 
//			
//			for (int i = article.size() - 1; i >= 0; i--) {
//				Chapter ch = article.get(i);
//				if (ch.Title != null)
//					w.write(ch.Title);
//				if (ch.Text != null)
//					w.write(ch.Text.toString());
//			}
//		}
	}

	/*
	 * 
	 * try (Stream<String> stream = Files.lines(Paths.get(filePath),
	 * StandardCharsets.UTF_8)) {
	 * 
	 * foreach(String : stream) {
	 * 
	 * } stream.forEach(s -> {
	 * 
	 * if (s.startsWith("===第")) { if (row == null) { article.add(row); row = new
	 * Chapter(); } row.Title = s; } else { row.Text.append(s).append("\n"); } });
	 * 
	 * } catch (IOException e) { e.printStackTrace(); }
	 * 
	 */

	public static List<String> toStringArray(String sourceString) {
		char[] charArrays = new char[sourceString.length()];
		charArrays = sourceString.toCharArray();
		List<String> stringArray = new ArrayList<String>(charArrays.length);
		for (int i = 0; i < charArrays.length; i++) {
			stringArray.add(("" + charArrays[i]).trim());
		}
		return stringArray;
	}

}
