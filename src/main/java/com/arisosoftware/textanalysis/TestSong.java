package com.arisosoftware.textanalysis;
 
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
 
public class TestSong {

	public static void main(String[] args) {
		
	//	List<String> words = Arrays.asList("Hello alan i am here where are you and what are you doing hello you there".split(" "));

//		Map<String, Long> collect = words.stream()
//		        .map(String::toLowerCase) // convert to lower case
//		        .collect( // group and count by name
//		                Collectors.groupingBy(Function.identity(), Collectors.counting()));
//
//		collect.keySet().stream()
//		        .sorted( // order by count descending, then by name
//		                Comparator
//		                        .comparing(collect::get)
//		                        .reversed()
//		                        .thenComparing(Collator.getInstance()))
//		        .map(k -> k + " (" + collect.get(k) + ")") // map to name and count string
//		        .limit(7) // only first 7 entries
//		        .forEach(System.out::println); // output
//		
		
		//Map<String, Integer> map = new HashMap<>();
		 List<String> words = toStringArray(data);
		
		//map.compute(data, (key, oldValue) -> ((oldValue == null) ? 1 : oldValue+1));
		
		 
 
	    words.stream()
	         .map(String::toLowerCase)
	         .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
	         .entrySet().stream()
	         .sorted(
	        		 Map.Entry.<String, Long> 
	        		 comparingByValue(Comparator.reverseOrder())
	        		 .thenComparing(Map.Entry.comparingByKey()))
	         .limit(12)
	         .forEach(System.out::println); // output
 
//		                 .map(Map.Entry::getKey)
//		                 .collect(toList());
 
	}

	public static   List<String> toStringArray(String sourceString) {
	    char[] charArrays = new char[sourceString.length()];
	    charArrays = sourceString.toCharArray();
	    List<String>  stringArray = new ArrayList<String> (charArrays.length);
	    for (int i = 0; i < charArrays.length; i++) {
	    	stringArray.add((""+charArrays[i]).trim());
	    }
	    return stringArray;
	}
	
	
	static String data="有一个美丽的小女孩\n" + 
			"她的名字叫作小薇\n" + 
			"她有双温柔的眼睛\n" + 
			"她悄悄偷走我的心\n" + 
			"小薇啊 你可知道我多爱你\n" + 
			"我要带你飞到天上去\n" + 
			"看那星星多么美丽\n" + 
			"摘下一颗亲手送给你\n" + 
			"有一个美丽的小女孩\n" + 
			"她的名字叫作小薇\n" + 
			"她有双温柔的眼睛\n" + 
			"她悄悄偷走我的心\n" + 
			"小薇啊 你可知道我多爱你\n" + 
			"我要带你飞到天上去\n" + 
			"看那星星多么美丽\n" + 
			"摘下一颗亲手送给你\n" + 
			"小薇啊 你可知道我多爱你\n" + 
			"我要带你飞到天上去\n" + 
			"看那星星多么美丽\n" + 
			"摘下一颗亲手送给你\n" + 
			"你说你爱了不该爱的人\n" + 
			"你的心中满是伤痕\n" + 
			"你说你犯了不该犯的错\n" + 
			"心中满是悔恨\n" + 
			"你说你尝尽了生活的苦\n" + 
			"找不到可以相信的人\n" + 
			"你说你感到万分沮丧\n" + 
			"甚至开始怀疑人生\n" + 
			"早知道伤心总是难免的\n" + 
			"你又何苦一往情深\n" + 
			"因为爱情总是难舍难分\n" + 
			"何必在意那一点点温存\n" + 
			"要知道伤心总是难免的\n" + 
			"在每一个梦醒时分\n" + 
			"有些事情你现在不必问\n" + 
			"有些人你永远不必等\n" + 
			"你说你爱了不该爱的人\n" + 
			"你的心中满是伤痕\n" + 
			"你说你犯了不该犯的错\n" + 
			"心中满是悔恨\n" + 
			"你说你尝尽了生活的苦\n" + 
			"找不到可以相信的人\n" + 
			"你说你感到万分沮丧\n" + 
			"甚至开始怀疑人生\n" + 
			"早知道伤心总是难免的\n" + 
			"你又何苦一往情深\n" + 
			"因为爱情总是难舍难分\n" + 
			"何必在意那一点点温存\n" + 
			"要知道伤心总是难免的\n" + 
			"在每一个梦醒时分\n" + 
			"有些事情你现在不必问\n" + 
			"有些人你永远不必等\n" + 
			"早知道伤心总是难免的\n" + 
			"你又何苦一往情深\n" + 
			"因为爱情总是难舍难分\n" + 
			"何必在意那一点点温存\n" + 
			"要知道伤心总是难免的\n" + 
			"在每一个梦醒时分\n" + 
			"有些事情你现在不必问\n" + 
			"有些人你永远不必等\n" + 
			" 怕不怕被拒绝 怕不怕被省略\n" + 
			"你怕不怕被沦落在宿命中妥协\n" + 
			"当真爱宣告从缺\n" + 
			"骄傲的玫瑰正一片一片枯萎\n" + 
			"尽管你抱歉 忏悔\n" + 
			"真心一旦坠跌 就不能飞\n" + 
			"别指望我谅解 别指望我体会\n" + 
			"爱不是点头就能挽回\n" + 
			"快乐或伤悲 没什么分别\n" + 
			"心碎到终点会迎刃而解\n" + 
			"别指望我谅解 别指望我体会\n" + 
			"爱不是注定要填你的缺\n" + 
			"太多的是非 来不及杜绝\n" + 
			"更不想依恋这残缺的美\n" + 
			"残缺的迂回\n" + 
			"怕不怕被拒绝 怕不怕被省略\n" + 
			"你怕不怕被沦落在宿命中妥协\n" + 
			"当真爱宣告从缺\n" + 
			"骄傲的玫瑰正一片一片枯萎\n" + 
			"尽管你抱歉 忏悔\n" + 
			"真心一旦坠跌 就不能飞\n" + 
			"别指望我谅解 别指望我体会\n" + 
			"爱不是点头就能挽回\n" + 
			"快乐或伤悲 没什么分别\n" + 
			"心碎到终点会迎刃而解\n" + 
			"别指望我谅解 别指望我体会\n" + 
			"爱不是注定要填你的缺\n" + 
			"太多的是非 来不及杜绝\n" + 
			"更不想依恋这残缺的美\n" + 
			"残缺的迂回\n" + 
			"别指望我谅解 别指望我体会\n" + 
			"爱不是注定要填你的缺\n" + 
			"太多的是非 来不及杜绝\n" + 
			"更不想依恋这残缺的美\n" + 
			"残缺的迂回\n" + 
			"他不爱我 牵手的时候太冷清 拥抱的时候不够靠近\n" + 
			"他不爱我 说话的时候不认真 沉默的时候又太用心\n" + 
			"我知道他不爱我 他的眼神 说出他的心\n" + 
			"我看透了他的心 还有别人逗留的背影\n" + 
			"他的回忆清除得不够干净\n" + 
			"我看到了他的心 演的全是他和她的电影\n" + 
			"他不爱我 尽管如此 他还是赢走了我的心\n" + 
			"我知道他不爱我 他的眼神 说出他的心\n" + 
			"我看透了他的心 还有别人逗留的背影\n" + 
			"他的回忆清除得不够干净\n" + 
			"我看到了他的心 演的全是他和她的电影\n" + 
			"他不爱我 尽管如此 他还是赢走了我的心\n" + 
			"我看透了他的心 还有别人逗留的背影\n" + 
			"他的回忆清除得不够干净\n" + 
			"我看到了他的心 演的全是他和她的电影\n" + 
			"他不爱我 尽管如此 他还是赢走了我的心\n" + 
			"你好喜欢看我眼睛\n" + 
			"你说是宇宙的缩影\n" + 
			"只要没有分离 天气晴 能看见星星\n" + 
			"我努力爱你宠你调整自己\n" + 
			"我是邻居还是伴侣\n" + 
			"时间带来残忍结局\n" + 
			"在爱情的隔壁 住友情 界线太锐利\n" + 
			"对不起就一刀切开所有亲密\n" + 
			"眼底星空 流星开始坠落\n" + 
			"每一滴眼泪说着你要好好走\n" + 
			"转过身跌入黑洞\n" + 
			"看着天长地久变两种漂泊\n" + 
			"男人流泪比流血加倍心痛\n" + 
			"眼底星空 流星跌落手中\n" + 
			"我紧紧握着抬头向上天祈求\n" + 
			"愿你先找到温柔\n" + 
			"有人包扎伤口也挡住寂寞\n" + 
			"谢谢你陪我陪爱听雨追风\n" + 
			"用三年去维系感情\n" + 
			"用三秒钟结束关系\n" + 
			"剩回忆能回去 能温习 能把你抱紧\n" + 
			"就算爱烧成灰烬扬起变乌云\n" + 
			"眼底星空 流星开始坠落\n" + 
			"每一滴眼泪说着你要好好走\n" + 
			"转过身跌入黑洞\n" + 
			"看着天长地久变两种漂泊\n" + 
			"男人流泪比流血加倍心痛\n" + 
			"眼底星空 流星跌落手中\n" + 
			"我紧紧握着抬头向上天祈求\n" + 
			"愿你先找到温柔\n" + 
			"有人包扎伤口也挡住寂寞\n" + 
			"谢谢你陪我陪爱听雨追风\n" + 
			"眼底星空 流星跌落手中\n" + 
			"我紧紧握着抬头向上天祈求\n" + 
			"愿你先找到温柔\n" + 
			"有人包扎伤口也挡住寂寞\n" + 
			"谢谢你陪我陪爱听雨追风\n" + 
			"谢谢他给你给爱另一个星空\n" + 
			"灯熄灭了月亮是寂寞的眼静静看着谁孤枕难眠\n" + 
			"远处传来那首熟悉的歌那些心声为何那样微弱\n" + 
			"很久不见你现在都还好吗？\n" + 
			"你曾说过你不愿一个人我们都活在这个城市里面\n" + 
			"却为何没有再见面却只和陌生人擦肩\n" + 
			"有没有那么一首歌会让你轻轻跟着和\n" + 
			"牵动我们共同过去记忆它不会沉默\n" + 
			"有没有那么一首歌会让你心里记着我\n" + 
			"让你欢喜也让你忧这么一个我\n" + 
			"最真的梦你现在还记得吗你如今也是一个有故事的人\n" + 
			"天空下着一样冷冷的雨落在同样的世界昨天已越来越遥远\n" + 
			"有没有那么一首歌会让你轻轻跟着和\n" + 
			"牵动我们共同过去记忆从未沉默过\n" + 
			"有没有那么一首歌会让你心里记着我\n" + 
			"让你欢喜也让你忧这么一个我\n" + 
			"有没有那么一首歌会让你轻轻跟着和\n" + 
			"随着我们生命起伏一起唱的主题歌\n" + 
			"有没有那么一首歌会让你突然想起我\n" + 
			"让你欢喜也让你忧这么一个我\n" + 
			"我现在唱的这首歌若是让你想起了我\n" + 
			"涌上来的若是寂寞我想知道为什么 Oh~~~\n" + 
			"有没有那么一首歌会让你突然想起我\n" + 
			"让你欢喜也让你忧这么一个我\n" + 
			"我现在唱的这首歌就代表我对你诉说\n" + 
			"就算日子匆匆过去我们曾一起走过\n" + 
			"我现在唱的这首歌就代表我对你诉说\n" + 
			"就算日子匆匆过去我们曾走过\n" + 
			"就算日子匆匆过去我们曾走过\n" + 
			"你我皆凡人 生在人世间\n" + 
			"终日奔波苦 一刻不得闲\n" + 
			"既然不是仙 难免有杂念\n" + 
			"道义放两旁 利字摆中间\n" + 
			"多少男子汉 一怒为红颜\n" + 
			"多少同林鸟 已成分飞燕\n" + 
			"人生何其短 何必苦苦恋\n" + 
			"爱人不见了 向谁去喊冤\n" + 
			"问你何时曾看见 这世界为了人们改变\n" + 
			"有了梦寐以求的容颜 是否就算是拥有春天\n" + 
			"你我皆凡人 生在人世间\n" + 
			"终日奔波苦 一刻不得闲\n" + 
			"你既然不是仙 难免有杂念\n" + 
			"道义放两旁 把利字摆中间\n" + 
			"多少男子汉 一怒为红颜\n" + 
			"多少同林鸟 已成了分飞燕\n" + 
			"人生何其短 何必苦苦恋\n" + 
			"爱人不见了 向谁去喊冤\n" + 
			"问你何时曾看见 这世界为了人们改变\n" + 
			"有了梦寐以求的容颜 是否就算是拥有春天\n" + 
			"别想你 忍不住我提醒自己\n" + 
			"伤了心 有些事也要过去\n" + 
			"心很痛 痛的不想再做我自己\n" + 
			"别回头 情已去缘已尽\n" + 
			"很想你 也不是因为失去你\n" + 
			"爱了你 用尽我全心全力\n" + 
			"一生情 只为这一次与你相遇\n" + 
			"情难了 难再续 难再醒\n" + 
			"人分飞 爱相随\n" + 
			"哪怕用一生去追\n" + 
			"我又怎么能追得回\n" + 
			"与你相慰 我为你痴 为你累\n" + 
			"风雨我都不后悔\n" + 
			"我又怎么有路可退\n" + 
			"曾经深情 你给了谁\n" + 
			"很想你 也不是因为失去你\n" + 
			"爱了你 用尽我全心全力\n" + 
			"一生情 只为这一次与你相遇\n" + 
			"情难了 难再续 难再醒\n" + 
			"人分飞 爱相随\n" + 
			"哪怕用一生去追\n" + 
			"我又怎么能追得回\n" + 
			"与你相慰 我为你痴 为你累\n" + 
			"风雨我都不后悔\n" + 
			"我又怎么有路可退\n" + 
			"我如何面对\n" + 
			"人分飞 爱相随\n" + 
			"哪怕用一生去追\n" + 
			"我又怎么能追得回\n" + 
			"与你相慰 我为你痴 为你累\n" + 
			"风雨我都不后悔\n" + 
			"我又怎么有路可退\n" + 
			"曾经深情 你给了谁\n" + 
			"夜已深 还有什么人 让你这样醒着数伤痕\n" + 
			"为何临睡前会想要留一盏灯 你若不肯说 我就不问\n" + 
			"只是你现在不得不承认 爱情有时候是一种沉沦\n" + 
			"让人失望的虽然是恋情本身 但是不要只是因为你是女人\n" + 
			"若爱得深会不能平衡 为情困 磨折了灵魂\n" + 
			"该爱就爱 该恨的就恨 要为自己保留几分\n" + 
			"女人独有的天真和温柔的天分 要留给真爱你的人\n" + 
			"不管未来多苦多难 有他陪你完成\n" + 
			"虽然爱是种责任 给要给得完整 有时爱 美在无法永恒\n" + 
			"爱有多销魂 就有多伤人 你若勇敢爱了 就要勇敢分\n" + 
			"夜已深 还有什么人 让你这样醒着数伤痕\n" + 
			"为何临睡前会想要留一盏灯 你若不肯说 我就不问\n" + 
			"若爱得深会不能平衡 为情困 磨折了灵魂\n" + 
			"该爱就爱 该恨的就恨 要为自己保留几分\n" + 
			"女人独有的天真和温柔的天分 要留给真爱你的人\n" + 
			"不管未来多苦多难 有他陪你完成\n" + 
			"虽然爱是种责任 给要给得完整 有时爱 美在无法永恒\n" + 
			"爱有多销魂 就有多伤人 你若勇敢爱了 就要勇敢分\n" + 
			"女人独有的天真和温柔的天分 要留给真爱你的人\n" + 
			"不管未来多苦多难 有他陪你完成\n" + 
			"虽然爱是种责任 给要给得完整 有时爱 美在无法永恒\n" + 
			"爱有多销魂 就有多伤人 你若勇敢爱了 就要勇敢分\n" + 
			"夜已深 还有什么人 让你这样醒着数伤痕\n" + 
			"为何临睡前会想要留一盏灯 你若不肯说 我就不问\n" + 
			"你总是心太软心太软\n" + 
			"独自一个人流泪到天亮\n" + 
			"你无怨无悔的爱着那个人\n" + 
			"我知道你根本没那么坚强\n" + 
			"你总是心太软心太软\n" + 
			"把所有问题都自己扛\n" + 
			"相爱总是简单相处太难\n" + 
			"不是你的就别再勉强\n" + 
			"夜深了你还不想睡\n" + 
			"你还在想着他吗\n" + 
			"你这样痴情到底累不累\n" + 
			"明知他不会回来安慰\n" + 
			"只不过想好好爱一个人\n" + 
			"可惜他无法给你满分\n" + 
			"多余的牺牲他不懂心疼\n" + 
			"你应该不会只想做个好人\n" + 
			"喔 算了吧\n" + 
			"就这样忘了吧该放就放\n" + 
			"再想也没有用\n" + 
			"傻傻等待他也不会回来\n" + 
			"你总该为自己想想未来\n" + 
			"你总是心太软心太软\n" + 
			"独自一个人流泪到天亮\n" + 
			"你无怨无悔的爱着那个人\n" + 
			"我知道你根本没那么坚强\n" + 
			"你总是心太软心太软\n" + 
			"把所有问题都自己扛\n" + 
			"相爱总是简单相处太难\n" + 
			"不是你的就别再勉强\n" + 
			"夜深了你还不想睡\n" + 
			"你还在想着他吗\n" + 
			"你这样痴情到底累不累\n" + 
			"明知他不会回来安慰\n" + 
			"只不过想好好爱一个人\n" + 
			"可惜他无法给你满分\n" + 
			"多余的牺牲他不懂心疼\n" + 
			"你应该不会只想做个好人\n" + 
			"喔 算了吧\n" + 
			"就这样忘了吧该放就放\n" + 
			"再想也没有用\n" + 
			"傻傻等待他也不会回来\n" + 
			"你总该为自己想想未来\n" + 
			"你总是心太软心太软\n" + 
			"独自一个人流泪到天亮\n" + 
			"你无怨无悔的爱着那个人\n" + 
			"我知道你根本没那么坚强\n" + 
			"你总是心太软心太软\n" + 
			"把所有问题都自己扛\n" + 
			"相爱总是简单相处太难\n" + 
			"不是你的就别再勉强\n" + 
			"不是你的就别再勉强\n" + 
			"不是你的就别再勉强\n" + 
			"不是你的就别再勉强";
}