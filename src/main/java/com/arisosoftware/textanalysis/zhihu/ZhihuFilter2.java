/**
 * 
 */
package com.arisosoftware.textanalysis.zhihu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

import com.ariso.fsm2.FSM;
import com.ariso.fsm2.FSMNodes;
import com.ariso.fsm2.PoemVO;

/**
 * @author airsoft
 *
 */
public class ZhihuFilter2 extends FSM<String> {

	//关注问题写回答 , 1 

	public enum State {
		Dump(9),	DumpSp(109),
		
		QTitle(1), 	QTSp(101), 
		QBoby(2), 	QBSp(102), 
		QUser(3), 	QUSp(103),
		Answer(4), 	AnswerSp(104),
		AUser(5), 	AUserSp(105),
		Comment(6),	CommentSp(106), 	
		
		;

		private final Integer value;

		private State(int value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	};
 
	public History history = new History();
	public Question currentVO = new Question();

	/**
	 * 
	 */
	public ZhihuFilter2() {
		this.AddHandel(State.Dump.value, new FSMNodes<String>() {
			@Override
			public Integer Run(String input, FSM<String> fsm) {
				ZhihuFilter2 fff = (ZhihuFilter2) fsm;
				if (input.equals("")) {
					return State.DumpSp.value;
				} else {
					
				}
				history.addHistory(input, State.Dump.value);
				return State.Dump.value;
			}
		});
		
		

		this.AddHandel(State.HeaderSp.value, new FSMNodes<String>() {
			@Override
			public Integer Run(String input, FSM<String> fsm) {
				ZhihuFilter2 fff = (ZhihuFilter2) fsm;
				if (input == ':') {
					return State.HeaderSp.value;
				} else {
					currentVO.Author.append(input);
					return State.Author.value;
				}
			}
		});

		this.AddHandel(State.Author.value, new FSMNodes<String>() {
			@Override
			public Integer Run(String input, FSM<String> fsm) {
				ZhihuFilter2 fff = (ZhihuFilter2) fsm;
				if (input == ':') {
					return State.AuthorSp.value;
				} else {
					fff.currentVO.Author.append(input);
					return State.Author.value;
				}
			}
		});

		this.AddHandel(State.AuthorSp.value, new FSMNodes<String>() {
			@Override
			public Integer Run(String input, FSM<String> fsm) {
				ZhihuFilter2 fff = (ZhihuFilter2) fsm;
				if (input == ':') {
					return State.AuthorSp.value;
				} else {
					fff.currentVO.Poem.append(input);
					return State.Poem.value;
				}
			}
		});

		this.AddHandel(State.Poem.value, new FSMNodes<String>() {
			@Override
			public Integer Run(String input, FSM<String> fsm) {
				ZhihuFilter2 fff = (ZhihuFilter2) fsm;
				if (input == '\n') {

					if (fff.currentVO.valid()) {
						fff.Result.add(fff.currentVO);
						// fff.currentVO.Print();
					}
					fff.currentVO = new PoemVO();
					return State.Header.value;
				} else {
					// filter

					switch (input) {
//						case '{':;
//						case '/':;
//						case '}':;
					case ']':
						;
					case '？':
						;
					case '[':
						break;
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

	public void Log(String msg) {
		System.out.print(msg);
	}

	public void OnRun(String word) {
		super.Run(word);
		// super.CurrentNode.Run(word, this);
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		ZhihuFilter2 fsm = new ZhihuFilter2();

//	
//		String bigtxt = testTxt;
//		
//		for(int i=0;i<bigtxt.length();i++)
//		{
//			String cc =(String)bigtxt.charAt(i) ; 
//			fsm.OnRun(cc );
//		}
//		 
		String filePath = "/tmp/ZZ/dataset/poetryTang/poetryTang.txt";
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		int lineNo = 0;

		String line;
		while ((line = reader.readLine()) != null) {
			lineNo++;

			for (int i = 0; i < line.length(); i++) {
				String cc = (String) line.charAt(i);
				fsm.OnRun(cc);

			}
			fsm.OnRun('\n');
			fsm.reset();
		}
		// out put

		BufferedWriter writer1 = new BufferedWriter(new FileWriter("/tmp/Tan1.txt"));
		BufferedWriter writer2 = new BufferedWriter(new FileWriter("/tmp/Tan2.txt"));
		for (int i = 0; i < fsm.Result.size(); i++) {
			PoemVO vo = fsm.Result.get(i);

			if ((vo.Author.indexOf("庞蕴") >= 0) || (vo.Author.indexOf("德诚") >= 0) || (vo.Author.indexOf("罗衮") >= 0)
					|| (vo.Author.indexOf("智闲") >= 0)

					|| (vo.Author.indexOf("罗虬") >= 0) || (vo.Author.indexOf("杜光庭") >= 0)
					|| (vo.Author.indexOf("孙宗闵") >= 0) || (vo.Author.indexOf("元阳子") >= 0)

					|| (vo.Author.indexOf("白云仙人") >= 0) || (vo.Author.indexOf("朱君绪") >= 0)
					|| (vo.Author.indexOf("柳冲用") >= 0) || (vo.Author.indexOf("真人") >= 0)
					|| (vo.Author.indexOf("真君") >= 0) || (vo.Author.indexOf("蕴中") >= 0)
					|| (vo.Author.indexOf("道人") >= 0) || (vo.Author.indexOf("慧寂") >= 0)
					|| (vo.Author.indexOf("禅师") >= 0) || (vo.Author.indexOf("吕岩") >= 0)
					|| (vo.Author.indexOf("居遁") >= 0) || (vo.Author.indexOf("傅翕") >= 0)
					|| (vo.Author.indexOf("宝志") >= 0) || (vo.Author.indexOf("菩提达摩") >= 0)
					|| (vo.Author.indexOf("傅翕") >= 0) || (vo.Author.indexOf("契此") >= 0)
					|| (vo.Author.indexOf("徐成") >= 0) || (vo.Author.indexOf("文偃") >= 0)
					|| (vo.Author.indexOf("道世") >= 0) || (vo.Author.indexOf("王梵志") >= 0)
					|| (vo.Author.indexOf("道镜　善导") >= 0) || (vo.Author.indexOf("玄觉") >= 0))
				continue;

			if ((vo.Header.indexOf("四言") >= 0) || (vo.Header.indexOf("道藏") >= 0) || (vo.Header.indexOf("兵要望江南") >= 0))
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
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}

			return stringBuilder.toString();
		} finally {
			reader.close();
		}
	}

}
