package com.arisosoftware.textanalysis.zhihu;

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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

public class SplitZhihu1 {

	static void log(String line) {
		System.out.println(line);
	}

	static Pattern p1 = Pattern.compile("<![^>]*>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	static String Preprocess(String input) {
		return input;
	}

	public static void main(String[] args) throws Exception {
		String filePath = "/media/airsoft/lexar/tmp/a22";

		int StateId = 0;

		History history = new History();
		Question question = new Question();
		 
		Chapter c = new Chapter();
		Scanner s = new Scanner(new File(filePath));
		String LastStateLine = null;
		while (s.hasNextLine()) {
			String line = s.nextLine().trim();
			if (line != null) {

				history.addHistory(line);

				switch (StateId) {
				case 0:

					if (line.equals("关注问题​写回答")) // exit
					{

						String xl = history.GetLast(1);
						if (xl != null) {
							question.Title = xl;
							StateId = 1;
							log(xl);
							LastStateLine = xl;
						}

					}

					break;
				case 1:
					// on enter
					if (line.equals("关注者")) {
						String xl = history.GetLastUntilMatch(LastStateLine).toString();

						question.Body = xl;
						log(xl);
						StateId = 2;
					}

					break;

				case 2:
					// on enter
					if (line.endsWith("人赞同了该回答")) {
						String xl = history.GetDuplicateStringInRange(5);
						c.User = xl;
						log(xl);
						StateId = 3;
						LastStateLine = line;
					}

					break;
				case 3:
					// on enter
					/*
					 * ​分享 ​收藏 ​喜欢
					 */

					if (line.equals("​喜欢")) {

						String line1 = history.GetLast(1);
						String line2 = history.GetLast(2);

						if (line1.equals("​收藏") && line2.equals("​分享")) {
							String sb = history.GetLastUntilMatch(LastStateLine).toString();
							
							sb = StringUtils.replace(sb,"","");
									
							sb= StringUtils.replaceEach(sb, 
									new String[]{"真诚赞赏，手留余香",
											"还没有人赞赏，快来当第一个赞赏的人吧！",
											"添加评论",
											"赞赏",
											"​分享",
											"​\n\n",
											"​收藏"
											}, 
									new String[]{"","","","","","","",})  ;		
				 			 
							c.Body = sb;

							StateId = 2;
							log(sb);
							LastStateLine = line;
							question.chapter.add(c);
						
							String xl = history.GetLastMatchInRange(new String[] { "发布于", "编辑于", }, 11, '^');
							LastStateLine = xl;
							c = new Chapter();

						}

//System.exit(1);
					}

					break;
				}
			}
		}
		
	  
		FileOutputStream fos = new FileOutputStream(filePath + "X");

		try (Writer w = new OutputStreamWriter(fos, "UTF-8")) {
				
			w.write(question.Title);
			w.write("\n");
			for (int i = question.chapter.size() - 1; i >= 0; i--) {
				Chapter cc = question.chapter.get(i);
				 w.write("by "+cc.User);
				 w.write("\n");
				 w.write(cc.Body);
				 w.write("\n");
				 w.write("\n--------------------\n");
			}
		}

	}

	
	
	
	
	/*
	 * 
	 * try (Stream<String> stream = Fles.lines(Paths.get(filePath),
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
