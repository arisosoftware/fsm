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
		// System.out.println(line);
	}

	static Pattern p1 = Pattern.compile("<![^>]*>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	static String Preprocess(String input) {
		return input;
	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < args.length; i++) {
			try {
				split(args[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void DebugStringInHEX(String input) {
		StringBuilder sb = new StringBuilder();
		char[] data = input.toCharArray();
		for (int i = 0; i < data.length; i++) {
			sb.append(String.format("%04x%s ", (int) data[i], data[i]));
		}
		System.out.println(sb.toString());
	}

	public static String MyReplaceAllEach(String text, String[] searchList, String[] replacementList) {

		// format the inputlist first
		for (int i = 0; i < searchList.length; i++) {
			String row = searchList[i];
			row = row.replace('\u200B', ' ').trim();
			searchList[i] = row;
		}
		return StringUtils.replaceEach(text, searchList, replacementList);
	}

	static HashMap<Character, Character> map = null;

	public static String PreFilter(String body) {
		if (map == null) {
			map = new HashMap<Character, Character>();
			map.put('\u200B', null);
			map.put('　', null);
			map.put('\t', null);
			map.put('[', '\'');
			map.put(']', '\'');
			map.put('“', '「');
			map.put('”', '」');
			map.put('【', '「');
			map.put('】', '」');
			map.put('‘', '「');
			map.put('’', '」');
		}

		body = body.trim();

		body = body.replaceAll("\n+", "\n");
		body = body.replaceAll("\n$", "");

		char[] inputlist = body.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < inputlist.length; i++) {
			char ic = inputlist[i];
			if (map.containsKey(ic)) {
				Character da = map.get(ic);
				if (da != null) {
					sb.append(da);
				}
			} else {
				sb.append(ic);
			}
		}
		return sb.toString();
	}

	public static void split(String filePath) throws Exception {

		if (filePath.endsWith("md"))
			return;

		int StateId = 0;

		History history = new History();
		Question question = new Question();

		ZAnswer curAnswer = new ZAnswer();
		Comment comment = new Comment();
		Scanner s = new Scanner(new File(filePath));

		int LastStateLineIdx = 0;
		int lastAnswerIdx = 0;
		question.chapter.add(curAnswer);

		int lineNo = 0;
		while (s.hasNextLine()) {

			String line = s.nextLine();
			line = PreFilter(line);

			if (line != null && line.length() > 0) {

				history.addHistory(line, StateId);
//history.LineNo == 13 ||
				if (history.LineNo == 47) {
					// System.out.println(line.equals(Flag32));

					// DebugStringInHEX(line);
					// DebugStringInHEX(Flag32);
					//
					// System.out.println();
				}

				if (line.equals("​ 举报") && StateId != 6) {
					String line1 = history.GetLast(1);
					String line2 = history.GetLast(2);

					// ​回复 回复
					if (line1.equals("​踩") && line2.equals("​回复")) {

						comment = new Comment();
						comment.User = history.GetIndex(LastStateLineIdx);
						LastStateLineIdx = LastStateLineIdx + 2;
						StateId = 6;
						curAnswer.comments.add(comment);
					}

				}

				switch (StateId) {
				case 0:

					if (line.equals("关注问题写回答")) // exit
					{
						String xl = history.GetLast(1);
						if (xl != null) {
							question.Title = xl;
							StateId = 1;
							log(xl);
							LastStateLineIdx = history.LineNo;
						}
					}

					break;
				case 1:
					// on enter
					if (line.equals("关注者")) {
						String xl = history.GetLastUntilLastPositionNum(LastStateLineIdx).toString();

						question.Body = xl;
						log(xl);
						StateId = 101;
					}

					break;

				case 101:
					if (line.endsWith(" 个回答")) {
						StateId = 2;
					}

					break;

				case 2:
					// on enter
					if (line.endsWith("人赞同了该回答")) {
						String xl = history.FindFirstSameSubseqString(15);
						if (xl == null) {
							int idx2 = history.LookupInRange("默认排序", 20);
							if (idx2 > 0) {
								xl = history.GetIndex(idx2 + 1);
							} else {
								xl = history.GetLast(2);
								if (xl.length() < 2) {
									xl = history.GetLast(3);
								}
							}
						} else if (xl.length() < 2) {
							xl = history.GetLast(2);
							if (xl.length() < 2) {
								xl = history.GetLast(3);
							}
						}

						curAnswer = new ZAnswer();
						question.chapter.add(curAnswer);
						curAnswer.User = xl;
						log(xl);
						System.out.println(" >@@> ");
						StateId = 3;
						LastStateLineIdx = history.LineNo;
					}

					if (line.equals("​​赞同​")) {
						StateId = 3;
					}
					
					
					if (line.equals("​切换为时间排序")) {
						StateId = 4;
					}

					// 175 line1.matches("[0-9]+ 条评论")
					if (line.matches("[0-9]+ 条评论")) {
						StateId = 41;
					}


					String P231 = "发布于".replace("\u200b", "");
					String P232 = "编辑于".replace("\u200b", "");
					
					String F233 = "喜欢".replace("\u200b", "");
					String F234 = "收藏".replace("\u200b", "");
					String F235 = "​分享".replace("\u200b", "");

//					if (true) {
//						if (line.equals(F233)) {
//							String line1 = history.GetLast(1);
//							String line2 = history.GetLast(2);
//								
//							if ((line1.equals(F234) && line2.equals(F235))) {
//								int range =  history.LineNo - LastStateLineIdx;
//								int idx1 = history.LookupInRange("默认排序", range);
//								int idx2=0,idx3=0;
//								if (idx1>0)
//								{
//									String xl = history.GetLast(idx1+1);
//									
//								}
//								
//								
//								if (idx1<0)
//								{
//									idx2 = history.LookupInRange("按时间排序", range);
//									if (idx2<0)
//									{
//										idx3 = history.LookupInRange("分享", range);
//									}
//								}
//								if (idx1>0)
//								{
//								
//								}
//								 
//								if (idx1<0)
//								{
//									
//								}
//								
//							 
//								
//							}
//						}
//								
//						if (line.startsWith(P231) || line.startsWith(P232))
//						{
//							
//							
//						}
 

					
					break;

				case 41: {

					String L1 = history.GetLast(1);
					String L2 = history.GetLast(2);
					String L3 = history.GetLast(3);

					if ((line.equals("​回复") || (line.equals("​热评")))) {
						comment = new Comment();

						comment.User = line;
						LastStateLineIdx = history.LineNo;
						StateId = 5;
						curAnswer.comments.add(comment);
					}
					if (L1.startsWith("IP 属地")) {
						if ((L3.matches("[0-9][0-9]-[0-9][0-9]")) || L3.matches("[0-9]+ 小时前")
								|| (L3.matches("[0-9]+ 分钟前"))) {
							comment = new Comment();
							int range = history.LineNo - LastStateLineIdx;
							int idx3 = 2;
							int idx2 = history.LookupInRange("回复", range);
							if (idx2 > 0) {
								String userName = history.GetIndex(idx2 + idx3);
								if (userName.matches("查看全部 [0-9]+ 条回复")) {
									idx3++;
									userName = history.GetIndex(idx2 + idx3);
								}

								comment.User = userName;
								comment.Body = history.GetLastUntilLastPositionNum(idx2 + idx3, 2).toString();
							} else {
								String userName = history.GetLast(5);
								comment.User = userName;
								comment.Body = history.GetLast(4);
							}

							LastStateLineIdx = history.LineNo;
							StateId = 41;
							curAnswer.comments.add(comment);

							System.out.println(" >> ");
						}

					}

					if (line.matches("[0-9]*下一页") || line.equals("写下你的评论...")) {
						LastStateLineIdx = history.LineNo;
						StateId = 2;
					}

					if (line.endsWith("人关注了作者") || line.endsWith("人赞同了该回答") || line.equals("更多回答")) {
						LastStateLineIdx = history.LineNo;
						StateId = 2;
					}

					
					break;
				}

				case 3:
					// on enter
					/*
					 * ​分享 ​收藏 ​喜欢
					 */

					String Flag30 = "喜欢".replace("\u200b", "");
					String Flag31 = "收藏".replace("\u200b", "");
					String Flag32 = "​分享".replace("\u200b", "");

					if (line.equals(Flag30)) {

						String line1 = history.GetLast(1);
						String line2 = history.GetLast(2);

						if ((line1.equals(Flag31) && line2.equals(Flag32))) {

							if (curAnswer.Body != null) {
								curAnswer = new ZAnswer();
								question.chapter.add(curAnswer);
								curAnswer.User = history.GetIndex(LastStateLineIdx);
								;
								LastStateLineIdx++;
							}

							lastAnswerIdx = history.LineNo;
							String sb = history.GetLastUntilLastPositionNum(LastStateLineIdx).toString();

							sb = MyReplaceAllEach(sb,
									new String[] { "真诚赞赏，手留余香", "还没有人赞赏，快来当第一个赞赏的人吧！", "添加评论", "赞赏", "​分享", "​展开阅读全文",
											"​收藏", "收起评论", "分享" },
									new String[] { "", "", "", "", "", "", "", "", "", });

							curAnswer.Body = sb;

							StateId = 2;
							// log(sb);
							LastStateLineIdx = history.LineNo;
							String P31 = "发布于".replace("\u200b", "");
							String P32 = "编辑于".replace("\u200b", "");

							int idx2 = history.LookupInRange(new String[] { P31, P32, }, 11, '^');
							if (idx2 > 0) {
								LastStateLineIdx = idx2;
							}
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
						LastStateLineIdx = history.LineNo;
						StateId = 5;
						curAnswer.comments.add(comment);
					}

					if (line.matches("[0-9]*下一页") || line.equals("写下你的评论...")) {
						LastStateLineIdx = history.LineNo;
						StateId = 2;
					}
					break;
				}

				case 5: {
					LastStateLineIdx = history.LineNo;
					StateId = 6;
					break;
				}
				case 6: {
					if (line.equals("​ 举报")) {
						String xl = history.GetLastUntilLastPositionNum(LastStateLineIdx, 2).toString();

						comment.Body = xl;

						StateId = 7;
					}
					if (line.equals("发布")) {
						String line1 = history.GetLast(2);
						String line2 = history.GetLast(1);
						if (line1.equals("") && line2.equals("")) {
							StateId = 3;
							LastStateLineIdx = lineNo + 1;
						}

					}

					//

					break;
				}

//				case 601:
//				{
//					if (line.equals("​喜欢")) {
//
//						String line1 = history.GetLast(1);
//						String line2 = history.GetLast(2);
//
//						if (line1.equals("​收藏") && line2.equals("​分享")) {
//							
//						}
//					}
//					break;
//				}

				case 7: {
					if (line.matches("[0-9]*下一页") || line.equals("写下你的评论...") || line.matches(".* [0-9]* 条回复$")) {
						LastStateLineIdx = history.LineNo;
						StateId = 2;
						break;
					} else {
						comment = new Comment();
						comment.User = line;
						LastStateLineIdx = history.LineNo;
						StateId = 5;
						curAnswer.comments.add(comment);
						break;
					}

				}

				}
			}
		}

		// finally process
//		if (curAnswer.User != null) {
//			if (comment.User != null) {
//				curAnswer.comments.add(comment);
//			}
//			question.chapter.add(curAnswer);
//		}
//
		FileOutputStream fos = new FileOutputStream(filePath + ".md");

		try (Writer w = new OutputStreamWriter(fos, "UTF-8")) {

			w.write(question.Title);
			w.write("\n");
			w.write(question.Body);
			w.write("\n");
			for (int i = 0; i < question.chapter.size(); i++) {
				ZAnswer cc = question.chapter.get(i);
				if (cc.User == null)
					continue;
				log(i + " user=" + cc.User);
				w.write("\n\n### " + cc.User);
				w.write("\n");
				String body = cc.Body;
				// body = body.replaceAll("\n\n", "\n");
//				body = body.replace("\u200B", "");
//				body = body.replaceAll("\n+", "\n");
//
//				body = body.replaceAll("[　\t]", "");
//				body = body.replace("[", "\"");
//				body = body.replace("]", "\"");
//				body = body.replace("　", "");
//				body = body.replace("\t", "");
//				body = body.replace("“", "「");
//				body = body.replace("”", "」");
//				body = body.replace("【", "「");
//				body = body.replace("】", "」");
//
//				body = body.replace("‘", "");
//				body = body.replace("’", "");
//				body = body.replaceAll("\n$", "");
//
//				body = body.replaceAll("[　\t]", "");

				body = body.replaceAll("^已赞同 [0-9]*", "");
				body = body.replaceAll("赞同 [0-9]*", "");
				body = body.replaceAll("展开阅读全文", "");

				body = body.replaceAll("[0-9]* 条评论", "");
				w.write(body);
				log(i + "," + curAnswer.User + "-----------");
				for (int ic = 0; ic < cc.comments.size(); ic++) {
					Comment cm = cc.comments.get(ic);
					if (cm.User == null)
						continue;
					String cmComment = cm.Body;
					if (cmComment != null) {
						cmComment = cmComment.replaceAll("\u200B", "");
						cmComment = cmComment.replaceAll("\n+", "\n");
						cmComment = cmComment.replaceAll("\n$", "");
						cmComment = cmComment.replaceAll("\n", "\n\t\t");
					}
					w.write("\n\t" + StringUtils.rightPad(cm.User, 5, "　") + ":\t" + cmComment);
					// w.write( "\n\t"+ cm.User + ":\t" + cmComment);
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
