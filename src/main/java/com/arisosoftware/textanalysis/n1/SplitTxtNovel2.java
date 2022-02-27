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

	static void StoreArrayToFile(List<String> data, String filename) {

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filename);
			try (Writer w = new OutputStreamWriter(fos, "UTF-8")) {
				data.stream().forEach((k) -> {
					try {
						w.write(k + "\n");
					} catch (IOException e) {
					}
				}); // output
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	static void SaveListByGroupby(int minCount, String OutputFilepath, List<String> datalist) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(OutputFilepath);
			try (Writer w = new OutputStreamWriter(fos, "UTF-8")) {
				datalist.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet()
						.stream()

						.sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
								.thenComparing(Map.Entry.comparingByKey()))
						.filter((k) -> {
							long val = k.getValue();
							if (val < minCount)
								return false;

							return true;
						}).forEach((k) -> {
							try {
								w.write(k.getKey().toString() + ":" + k.getValue().toString() + "\n");
							} catch (IOException e) {
							}
						}); // output
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

	}
	
	
	static ArrayList<String> GetTop10(int TopTen,int minCount,  List<String> datalist) {
		 ArrayList<String> top10 = new ArrayList<String>();
		 
				datalist.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet()
						.stream()
						.sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
								.thenComparing(Map.Entry.comparingByKey()))
						.filter((k) -> {
							long val = k.getValue();
							if (val < minCount)
								return false;
							return true;
						}).limit(TopTen)
						.forEach((k) -> {
							top10.add(k.getKey());
						}); // output
					
				return top10;
	}

	

	static ArrayList<String> SplitByLen(int minCount,  String[] inputList) {
		ArrayList<String> nameList = new ArrayList<String>();
		for (int i = 0; i < inputList.length; i++) {
			String line = inputList[i];
			if (line.length() > minCount) {
				for (int j = 0; j + minCount < line.length(); j++) {
					String word = line.substring(j, j + minCount);
					nameList.add(word);
				}
			}
		}
		return nameList;
	}

	private static String readLineByLineJava8(String filePath) {
		StringBuilder contentBuilder = new StringBuilder();
		try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
			stream.forEach(line -> {
				line = line.replace("”", " ");
				line = line.replace("。", " ");
				line = line.replace("，", " ");
				line = line.replace("：", " ");
				line = line.replace("？", " ");
				line = line.replace("“", " ");
				line = line.replace("；", " ");
				line = line.replace("）", " ");
				line = line.replace("‘", " ");
				line = line.replace("！", " ");
				line = line.replace("（", " ");
				line = line.replace("　", " ");
				line = line.replace("  ", " ");
				line = line.replace("  ", " ");
				line = line.trim();
				if (line.length() > 1) {
					contentBuilder.append(line).append(" ");
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentBuilder.toString();
	}

	public static void main(String[] args) throws Exception {
		String filePath = "/tmp/sdyx"; // 射雕英雄

//这篇java的目的是把所有 :   说道: 的前3个字找出来,然后统计出最多的列表.

		ArrayList<String> nameList = new ArrayList<String>();
		String fulltext = readLineByLineJava8(filePath);
		String[] inputList = fulltext.split(" ");
		ArrayList<String> T2 = null;
		ArrayList<String> T3 = new ArrayList<String>();
		System.out.println("##### for JDK 8+" + nameList.size());
		ArrayList<String> T1 = null;
		
		for(int r = 0; r<100; r++)
		{
			T3.addAll( Iter(r, fulltext));
			System.out.println("##### Rank " + r + " WordList "+ T3.size());
		}
		
		StoreArrayToFile(T3, "/tmp/T3");
		
		
//		StoreArrayToFile(T1, "/tmp/T1");
//		SaveListByGroupby(3, "/tmp/T1A", T1);
//
//		T1 = SplitByLen(3,inputList);
//		StoreArrayToFile(T1, "/tmp/T2");
//		SaveListByGroupby(3, "/tmp/T2A", T1);
//
//		T1 = SplitByLen(2,inputList);
//		StoreArrayToFile(T1, "/tmp/T1");
//		T2 = GetTop10(2, T1);
//		T3.addAll(T2);
//		for (int x1 =0; x1<T2.size(); x1++)
//		{
//			fulltext = fulltext.replace(T2.get(x1),"");
//		}
//		
//		inputList = fulltext.split(" ");
//		T1 = SplitByLen(2,inputList);
//		StoreArrayToFile(T1, "/tmp/T2");
//		T2 = GetTop10(2, T1);
//		T3.addAll(T2);
//		for (int x1 =0; x1<T2.size(); x1++)
//		{
//			
//			fulltext = fulltext.replace(T2.get(x1),"");
//		}
//		
//		inputList = fulltext.split(" ");
//		T1 = SplitByLen(2,inputList);
//		StoreArrayToFile(T1, "/tmp/T3");
//		T2 = GetTop10(2, T1);
//		T3.addAll(T2);
//		for (int x1 =0; x1<T2.size(); x1++)
//		{
//			fulltext = fulltext.replace(T2.get(x1),"");
//		}
//		StoreArrayToFile(T3, "/tmp/T3X");
//		SaveListByGroupby(3, "/tmp/T3A", T1);
		
	}
	
	static ArrayList<String>  Iter(int Id, String fulltext)
	{
		String[] inputList = fulltext.split(" ");
		ArrayList<String> T2 = SplitByLen(2,inputList);
		ArrayList<String> T3 = SplitByLen(3,inputList);
		
		ArrayList<String>TopWord2 = GetTop10(5,100, T2);
		ArrayList<String>TopWord3 = GetTop10(5,100, T3);
		
		for (int x1 =0; x1<T3.size(); x1++)
		{
			fulltext = fulltext.replace(T3.get(x1),"");
		}
		
		for (int x1 =0; x1<T2.size(); x1++)
		{
			fulltext = fulltext.replace(T2.get(x1),"");
		}
		 System.gc();
		 TopWord2.addAll(TopWord3);
		 return TopWord2;
	}
	
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
