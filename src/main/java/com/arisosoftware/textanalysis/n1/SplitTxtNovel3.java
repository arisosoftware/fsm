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
import java.util.TreeMap;
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

/*
 * the java 8 stream performance is not good. update to another one.
 */
public class SplitTxtNovel3 {
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

	static ArrayList<String> GetTop10(int TopTen, int minCount, List<String> datalist) {
		ArrayList<String> top10 = new ArrayList<String>();
		Map<String, Integer> wordcount = new HashMap<>();
		Map<String, Integer> sortWordcount = new TreeMap<>();
		
		ValueComparator bvc = new ValueComparator(wordcount);

		for (int i = 0; i < datalist.size(); i++) {
			String line = datalist.get(i);

			if (line.indexOf(" ") >= 0)
				continue;

			if (wordcount.containsKey(line)) {
				Integer x = wordcount.get(line);
				x = x + 1;
				wordcount.put(line, x);
			} else {
				Integer x = 1;
				wordcount.put(line, x);
			}
		}
		
		sortWordcount.putAll(wordcount);
	 
		System.out.println("Total wordcount: " + wordcount.size());

		datalist.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
				.sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
						.thenComparing(Map.Entry.comparingByKey()))
				.filter((k) -> {
					long val = k.getValue();
					if (val < minCount)
						return false;
					return true;
				}).limit(TopTen).forEach((k) -> {
					top10.add(k.getKey());
				}); // output

		return top10;
	}

	static ArrayList<String> SplitByLen(int minCount, String fulltext) {
		int fulltextlen = fulltext.length();
		int length = (int) ((fulltextlen / minCount) * 0.8);
		ArrayList<String> nameList = new ArrayList<String>(length);
		for (int j = 0; j + minCount < fulltextlen; j++) {
			String word = fulltext.substring(j, j + minCount);
			if (!word.contains(" "))
				nameList.add(word);
		}
		return nameList;
	}

	private static String readLineByLineJava8(String filePath) {
		StringBuilder contentBuilder = new StringBuilder();
		try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
			stream.forEach(line -> {

				line = line.replaceAll("[”|。|，|：|？|；|）|（|　|‘|！|  ]", " ");
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

		ArrayList<String> T = new ArrayList<String>();
		String fulltext = readLineByLineJava8(filePath);
		T.addAll(Iter(1, fulltext));
		StoreArrayToFile(T, "/tmp/T1");

	}

	static ArrayList<String> Iter(int Id, String fulltext) {

		ArrayList<String> T2 = SplitByLen(2, fulltext);
		ArrayList<String> T3 = SplitByLen(3, fulltext);

		ArrayList<String> TopWord2 = GetTop10(5, 100, T2);
		ArrayList<String> TopWord3 = GetTop10(5, 100, T3);

		for (int x1 = 0; x1 < T3.size(); x1++) {
			fulltext = fulltext.replace(T3.get(x1), "");
		}

		for (int x1 = 0; x1 < T2.size(); x1++) {
			fulltext = fulltext.replace(T2.get(x1), "");
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

class ValueComparator implements Comparator<String> {

	Map<String, Integer> base;

	public ValueComparator(Map<String, Integer> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with equals.
	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}
}