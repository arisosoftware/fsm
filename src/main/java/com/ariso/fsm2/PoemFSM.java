/**
 * 
 */
package com.ariso.fsm2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author airsoft
 *
 */
public class PoemFSM extends FSM<Character> {


	
	static String testTxt = "田园作::孟浩然::弊庐隔尘喧，惟先养恬素。卜邻近三径，植果盈千树。粤余任推迁，三十犹未遇。书剑时将晚，丘园日已暮。晨兴自多怀，昼坐常寡悟。冲天羡鸿鹄，争食羞鸡骛。望断金马门，劳歌采樵路。乡曲无知己，朝端乏亲故。谁能为扬雄，一荐甘泉赋。\n" + 
			"采樵作::孟浩然::采樵入深山，山深树重叠。桥崩卧槎拥，路险垂藤接。日落伴将稀，山风拂萝衣。长歌负轻策，平野望烟归。\n" + 
			"自浔阳泛舟经明海::孟浩然::大江分九流，淼淼成水乡。舟子乘利涉，往来至浔阳。因之泛五湖，流浪经三湘。观涛壮枚发，吊屈痛沈湘。魏阙心恒在，金门诏不忘。遥怜上林雁，冰泮也回翔。\n" + 
			"早发渔浦潭::孟浩然::东旭早光芒，渚禽已惊聒。卧闻渔浦口，桡声暗相拨。日出气象分，始知江湖阔。美人常晏起，照影弄流沫。饮水畏惊猿，祭鱼时见獭。舟行自无闷，况值晴景豁。\n" + 
			"经七里滩::孟浩然::予奉垂堂诫，千金非所轻。为多山水乐，频作泛舟行。五岳追向子，三湘吊屈平。湖经洞庭阔，江入新安清。复闻严陵濑，乃在兹湍路。叠障数百里，沿洄非一趣。彩翠相氛氲，别流乱奔注。钓矶平可坐，苔磴滑难步。猿饮石下潭，鸟还日边树。观奇恨来晚，倚櫂惜将暮。挥手弄潺湲，从兹洗尘虑。\n" + 
			"岁暮海上作::孟浩然::仲尼既云殁，余亦浮于海。昏见斗柄回，方知岁星改。虚舟任所适，垂钓非有待。为问乘槎人，沧洲复谁在。\n" + 
			"南归阻雪::孟浩然::我行滞宛许，日夕望京豫。旷野莽茫茫，乡山在何处。孤烟村际起，归雁天边去。积雪复平皋，饥鹰捉寒兔。少年弄文墨，属意在章句。十上耻还家，裴回守归路。\n" + 
			"听郑五愔弹琴::孟浩然::阮籍推名饮，清风满竹林。半酣下衫袖，拂拭龙唇琴。一杯弹一曲，不觉夕阳沈。予意在山水，闻之谐夙心。\n" + 
			"同张明府清镜叹::孟浩然::妾有盘龙镜，清光常昼发。自从生尘埃，有若雾中月。愁来试取照，坐叹生白发。寄语边塞人，如何久离别。\n" + 
			"庭橘::孟浩然::明发览群物，万木何阴森。凝霜渐渐水，庭橘似悬金。女伴争攀摘，摘窥碍叶深。并生怜共蒂，相示感同心。骨刺红罗被，香黏翠羽簪。擎来玉盘里，全胜在幽林。\n" + 
			"早梅::孟浩然::园中有早梅，年例犯寒开。少妇曾攀折，将归插镜台。犹言看不足，更欲剪刀裁。\n" + 
			"清明即事::孟浩然::帝里重清明，人心自愁思。车声上路合，柳色东城翠。花落草齐生，莺飞蝶双戏。空堂坐相忆，酌茗聊代醉。\n" + 
			"和卢明府送郑十三还京兼寄之什::孟浩然::昔时风景登临地，今日衣冠送别筵。醉坐自倾彭泽酒，思归长望白云天。洞庭一叶惊秋早，濩落空嗟滞江岛。寄语朝廷当世人，何时重见长安道。\n" + 
			"高阳池送朱二::孟浩然::当昔襄阳雄盛时，山公常醉习家池。池边钓女日相随，妆成照影竞来窥。澄波澹澹芙蓉发，绿岸毵毵杨柳垂。一朝物变人亦非，四面荒凉人径稀。意气豪华何处在，空余草露湿罗衣。此地朝来饯行者，翻向此中牧征马。征马分飞日渐斜，见此空为人所嗟。殷勤为访桃源路，予亦归来松子家。\n" + 
			"鹦鹉洲送王九之江左::孟浩然::昔登江上黄鹤楼，遥爱江中鹦鹉洲。洲势逶迤遶碧流，鸳鸯鸂鶒满滩头。滩头日落沙碛长，金沙熠熠动飙光。舟人牵锦缆，浣女结罗裳。月明全见芦花白，风起遥闻杜若香，君行采采莫相忘。";
	 

	
	public enum State {
		Header(1), HeaderSp(2), Author(3),AuthorSp(4), Poem(5) ;
		
	    private final Integer value;
	    private State(int value) {
	        this.value = value;
	    }
	    public Integer getValue() {
	        return value;
	    }
	};
	public ArrayList<PoemVO> Result = new ArrayList<PoemVO>();
	
	public PoemVO currentVO = new PoemVO();
	/**
	 * 
	 */
	public PoemFSM() {
		 this.AddHandel( State.Header.value, new FSMNodes<Character>() {
			@Override
			public Integer Run(Character input, FSM<Character> fsm) {
				PoemFSM fff = (PoemFSM)fsm;
				if (input == ':')
				{
					return State.HeaderSp.value;
				}
				else
				{
					fff.currentVO.Header.append(input);
				}
				
				return State.Header.value;
			}
		 });
		 
		 this.AddHandel( State.HeaderSp.value, new FSMNodes<Character>() {
				@Override
				public Integer Run(Character input, FSM<Character> fsm) {
					PoemFSM fff = (PoemFSM)fsm;
					if (input == ':')
					{
						return State.HeaderSp.value;
					}
					else
					{
						fff.currentVO.Author.append(input);
						return State.Author.value;	
					}
				}
			 }); 
		 
		 
		 this.AddHandel( State.Author.value, new FSMNodes<Character>() {
				@Override
				public Integer Run(Character input, FSM<Character> fsm) {
					PoemFSM fff = (PoemFSM)fsm;
					if (input == ':')
					{
						return State.AuthorSp.value;
					}
					else
					{
						fff.currentVO.Author.append(input);
						return State.Author.value;	
					}
				}
			 }); 
		 
		 this.AddHandel( State.AuthorSp.value, new FSMNodes<Character>() {
				@Override
				public Integer Run(Character input, FSM<Character> fsm) {
					PoemFSM fff = (PoemFSM)fsm;
					if (input == ':')
					{
						return State.AuthorSp.value;
					}
					else
					{
						fff.currentVO.Poem.append(input);
						return State.Poem.value;	
					}
				}
			 }); 
		
		 this.AddHandel( State.Poem.value, new FSMNodes<Character>() {
				@Override
				public Integer Run(Character input, FSM<Character> fsm) {
					PoemFSM fff = (PoemFSM)fsm;
					if (input == '\n')
					{
						
						if (fff.currentVO.valid())
						{
							fff.Result.add(fff.currentVO);
							//	fff.currentVO.Print();						
						}
						fff.currentVO = new PoemVO();
						return State.Header.value;
					}
					else
					{
						// filter
						
						switch(input)
						{
//						case '{':;
//						case '/':;
//						case '}':;
						case ']':;
						case '？':;
						case '[':break;
						case '。':
						case '，':							
							fff.currentVO.Poem.append('.');
							break;
						case '罒':
							break;
						default:
							fff.currentVO.Poem.append(input);
						}
						
					
						
						return State.Poem.value;	
					}
				}
			 }); 
		 
		 
	}
	
	public void Log(String msg)
	{
		System.out.print(msg);
	}
	

	public void OnRun(Character word)
	{
		super.Run(word);
	//	super.CurrentNode.Run(word, this);
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		 
		PoemFSM fsm = new PoemFSM();
		 
//	
//		String bigtxt = testTxt;
//		
//		for(int i=0;i<bigtxt.length();i++)
//		{
//			Character cc =(Character)bigtxt.charAt(i) ; 
//			fsm.OnRun(cc );
//		}
//		 
		String filePath = "/tmp/ZZ/dataset/poetryTang/poetryTang.txt";
		BufferedReader reader = new BufferedReader(new FileReader (filePath));
		int lineNo = 0;
	
		String line;
		while((line = reader.readLine()) != null) {
		   lineNo++;
		   
			
			for(int i=0;i<line.length();i++)
			{
				Character cc =(Character)line.charAt(i) ; 
				fsm.OnRun(cc );
			
			}	fsm.OnRun('\n' );
			fsm.reset();
        }
	  // out put
		
		
		
		BufferedWriter writer1 = new BufferedWriter(new FileWriter ("/tmp/Tan1.txt"));
		BufferedWriter writer2 = new BufferedWriter(new FileWriter ("/tmp/Tan2.txt"));
		for (int i=0;i<fsm.Result.size();i++)
		{
			PoemVO vo = fsm.Result.get(i);
			
			if ((vo.Author.indexOf("庞蕴")>=0)
				||(vo.Author.indexOf("德诚")>=0)
				||(vo.Author.indexOf("罗衮")>=0)
				||(vo.Author.indexOf("智闲")>=0)
				
				||(vo.Author.indexOf("罗虬")>=0)
				||(vo.Author.indexOf("杜光庭")>=0)
				||(vo.Author.indexOf("孙宗闵")>=0)
				||(vo.Author.indexOf("元阳子")>=0)
				
				||(vo.Author.indexOf("白云仙人")>=0)
				||(vo.Author.indexOf("朱君绪")>=0)
				||(vo.Author.indexOf("柳冲用")>=0)
				||(vo.Author.indexOf("真人")>=0)
				||(vo.Author.indexOf("真君")>=0)
				||(vo.Author.indexOf("蕴中")>=0)
				||(vo.Author.indexOf("道人")>=0)
				||(vo.Author.indexOf("慧寂")>=0)
				||(vo.Author.indexOf("禅师")>=0)
				||(vo.Author.indexOf("吕岩")>=0)
				||(vo.Author.indexOf("居遁")>=0)
				||(vo.Author.indexOf("傅翕")>=0)
				||(vo.Author.indexOf("宝志")>=0)
				||(vo.Author.indexOf("菩提达摩")>=0)
				||(vo.Author.indexOf("傅翕")>=0)
				||(vo.Author.indexOf("契此")>=0)
				||(vo.Author.indexOf("徐成")>=0)
				||(vo.Author.indexOf("文偃")>=0)
				||(vo.Author.indexOf("道世")>=0)
				||(vo.Author.indexOf("王梵志")>=0)
				||(vo.Author.indexOf("道镜　善导")>=0)
				||(vo.Author.indexOf("玄觉")>=0)
				)
				continue;
			

			if ((vo.Header.indexOf("四言")>=0)
				||(vo.Header.indexOf("道藏")>=0)
				||(vo.Header.indexOf("兵要望江南")>=0)
				)
				continue;
			
			
			
			
			
			 
			
			writer1.write(vo.Poem.toString());
			writer1.newLine();
			writer2.write(vo.ReversePoem());
			writer2.newLine();
		}
		writer1.close();
		writer2.close();
		
	}
	
	
	
	
	private String readFile(String file) throws IOException {
	    BufferedReader reader = new BufferedReader(new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        }

	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}
	
}
