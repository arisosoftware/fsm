package com.ariso.fsm2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZhihuFSM3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//关注问题​写回答

	}

	static ArrayList<String> removeFilter = null;
	static ArrayList<String> removeSameFilter = null;
	static HashMap<Character, Character> map = null;
	static HashMap<String, String> filter = null;

	static HashMap<Pattern, String> pattern1 = null;

	
	public void init() {
		
		if (filter == null) {
			filter = new HashMap<String, String>();
			filter.put(".*www.zhihu.com/people.*", "");
		}
		

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

		// 有一样的就删
//		removeFilter = new ArrayList();
//		removeFilter.add("www.zhihu.com/people");
//		removeFilter.add("小时前 · IP 属地");
//		removeFilter.add("刚刚 · IP 属地");
//		removeFilter.add("人赞同了该回答");
//		removeFilter.add("pic3.zhimg.com/80");
//		removeFilter.add("​");
//		removeFilter.add("人读过​阅读");
//		removeFilter.add("​分享");
//		removeFilter.add("关系");
//		removeFilter.add("​关注者");
//		removeFilter.add("​关注问题​写回答");
//		removeFilter.add("​关注​发私信");
//		removeFilter.add("关于作者");
//		
//		//完全一样才删
//		removeSameFilter = new ArrayList();
//		removeSameFilter.add("​");

		Pattern pattern = Pattern.compile("w3schools", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher("Visit W3Schools!");
		boolean matchFound = matcher.find();
		if (matchFound) {
			System.out.println("Match found");
		} else {
			System.out.println("Match not found");
		}

	}

	private String removeScript(String content) {
		Pattern p = Pattern.compile("<script[^>]*>(.*?)</script>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		return p.matcher(content).replaceAll("");
	}

	public static String PreFilter(String body) {
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

	// remove utf hidden letter.remove nested duplicate line
	public void Filter1() {
		// if https://www.zhihu.com/people/ then removed

	}

	/*
	 * 1, skip till 点击打开Regular的主页 2, pick one , set to A 3, skip till A 4, pick
	 * until "关注者" 5, skip till 17 个回答 6, remove duplicate, till 61 人赞同了该回答 7,
	 * ​pick, until 分享, then goto 6 ​if line is this, remove it. ​添加评论 ​分享 ​收藏 ​喜欢
	 * 
	 * 
	 * ​赞同 9​ ​1 条评论 ​分享 ​收藏 ​喜欢 ​ 收起​
	 * 
	 * 
	 * 
	 * 关注问题​写回答 点击打开Regular的主页
	 * 
	 * if line is like this, remove it. 37 被浏览 64,454 关注问题​写回答 ​邀请回答 ​好问题 5 ​添加评论
	 * ​分享 ​修改问题 关注者 37 被浏览 64,454 关注问题​写回答 ​邀请回答 ​好问题 5 ​添加评论 ​分享 ​修改问题 ​ 17 个回答
	 * 默认排序
	 * 
	 * 
	 * 首页 学习 会员 发现 等你来答 对浪漫过敏的体验 ​ 提问 ​1 消息[首页] ​ 私信 ​ 创作中心 点击打开Regular的主页
	 * 
	 * 
	 */
}
