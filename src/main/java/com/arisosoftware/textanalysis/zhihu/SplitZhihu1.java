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
		
	
		String filePath = "/tmp/a2";
		if (args.length>1)
		{
			filePath = args[0];
		}
		int StateId = 0;

		History history = new History();
		Question question = new Question();

		Chapter c = new Chapter();
		Comment comment = new Comment();
		Scanner s = new Scanner(new File(filePath));
		String LastStateLine = null;
		int lineNo=0;
		while (s.hasNextLine()) {
			lineNo++;
			String line = s.nextLine().trim();
			if (line != null) {

				history.addHistory(line);

				if (lineNo==706)
				{
					System.out.println(line);
				}
					
				
				
				switch (StateId) {
				case 0:

					if (line.endsWith("​写回答")) // exit
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
						String xl = history.FindFirstSameSubseqString(15);
						question.chapter.add(c);
						c = new Chapter();
						c.User = xl;
						log(xl);
						StateId = 3;
						LastStateLine = line;
					}

					if (line.equals("​切换为时间排序")) {
						StateId = 4;
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

							sb = StringUtils.replace(sb, "", "");

							sb = StringUtils.replaceEach(
									sb, new String[] { "真诚赞赏，手留余香", "还没有人赞赏，快来当第一个赞赏的人吧！", "添加评论", "赞赏", "​分享",
											"​展开阅读全文", "​收藏", "​收起评论", },
									new String[] { "", "", "", "", "", "", "", "", });

							c.Body = sb;

							StateId = 2;
						//	log(sb);
							LastStateLine = line;
						

							String xl = history.GetLastMatchInRange(new String[] { "发布于", "编辑于", }, 11, '^');
							LastStateLine = xl;
							
						}

//System.exit(1);
					}

					break;

				case 4: {
					String line1 = history.GetLast(2);
					String line2 = history.GetLast(1);
					if (line1.matches("[0-9]+ 条评论") && line2.startsWith("​切换为")) {
						comment = new Comment();
						comment.User = line;
						LastStateLine = line;
						StateId = 5;
						c.comments.add(comment);
					}

					if (line.matches("[0-9]*下一页")
							|| line.equals("写下你的评论...")
							) {
						LastStateLine = line;
						StateId = 2;
					}
					break;
				}

				case 5: {
					LastStateLine = line;
					StateId = 6;
					break;
				}
				case 6: {
					if (line.equals("​ 举报")) {
						String xl = history.GetLastUntilMatch(LastStateLine,2).toString();

						comment.Body = xl;
						 
						StateId = 7;
					}
					
					
					break;
				}
				
				case 7: {
					if (line.matches("[0-9]*下一页")
							|| line.equals("写下你的评论...")
							) {
						LastStateLine = line;
						StateId = 2;
						break;
					}
					
					if (line.matches(".* [0-9]* 条回复$"))
					{
						break;
					}
					else
					{
						comment = new Comment();
						comment.User = line;
						LastStateLine = line;
						StateId = 5;	c.comments.add(comment);
						break;
					}
				 
				}
				
				
				}
			}
		}

		//finally process
		if (c.User!=null)
		{
			if (comment.User!=null)
			{
				c.comments.add(comment);
			}
			question.chapter.add(c);
		}
		
		
		FileOutputStream fos = new FileOutputStream(filePath + "X.md");

		try (Writer w = new OutputStreamWriter(fos, "UTF-8")) {

			w.write(question.Title);
			w.write("\n");
			for (int i = 0; i < question.chapter.size()  ; i++) {
				Chapter cc = question.chapter.get(i);
				if (cc.User == null)
					continue;
				log(i+" user="+cc.User);
				w.write("\n### " + cc.User);
				w.write("\n");
				String body = cc.Body;
				body = body.replaceAll("\n\n", "\n");
				body = body.replaceAll("\n\n", "\n");
				body = body.replaceAll("\n\n", "\n");

				body = body.replaceAll("^已赞同 [0-9]*", "");
				body = body.replaceAll("赞同 [0-9]*", "");
				body = body.replaceAll("展开阅读全文", "");

				body = body.replaceAll("[0-9]* 条评论", "");
				w.write(body);
				log(i+","+c.User+"-----------" );
				for(int ic = 0; ic<cc.comments.size();ic++)
				{
					Comment cm = cc.comments.get(ic);
					if (cm.User==null)
						continue;
					w.write("\t" + 
						StringUtils.rightPad(cm.User, 15,"　")+":\t"+cm.Body );
				 
				}

			}
			w.close();
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
