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
public class PoemFSM2 {

	static String testTxt = "白发又经秋.端居海上洲.无机因事发.有涕为时流.新酒乘凉压.残碁隔夜收.公车无路入.同拜老闲侯.\n"
			+ "寺在五峰阴.穿缘一径寻.云藏古殿暗.石护小房深.宿鸟连僧定.寒猿应客吟.上方应见海.月出试登临.\n"
			+ "路岐无乐处.时节倍思家.彩索飏轻吹.黄鹂啼落花.连干驰宝马.历禄鬬香车.行客胜回首.看看春日斜.\n" + "去年花下把金卮.曾赋杨花数句诗.回首朱门闭荒草.如今愁到牡丹时.\n"
			+ "东阁无人事渺茫.老僧持钵过丹阳.十年栖止如何报.好与南谯剩炷香.\n" + "柳弱风长在.云轻雨易休.不劳芳草色.更惹夕阳愁.万里独归去.五陵无与游.\n"
			+ "始怜春草细霏霏.不觉秋来绿渐稀.惆怅撷芳人散尽.满园烟露蝶高飞.\n" + "天下有水亦有山.富春山水非人寰.长川不是春来绿.千峰倒影落其间.";

	public ArrayList<PoemVO> Result = new ArrayList<PoemVO>();

	public PoemFSM2() {

	}

	public void Log(String msg) {
		System.out.print(msg);
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		PoemFSM2 fsm = new PoemFSM2();
//	 	
//	
//		String bigtxt = testTxt;
//		
//		for(int i=0;i<bigtxt.length();i++)
//		{
//			Character cc =(Character)bigtxt.charAt(i) ; 
//			fsm.OnRun(cc );
//		}
//		System.exit(1);
//		 /////////////////////////////////
		String filePath = "/tmp/ZZ/dataset/poetryTang/Tan1.txt";
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		int lineNo = 0;
		BufferedWriter writer1 = new BufferedWriter(new FileWriter("/tmp/ZZ/dataset/poetryTang/five1.txt"));
		String line;
		while ((line = reader.readLine()) != null) {
			lineNo++;

			String[] statement = fSplit(line, '.');
			boolean isFive = true;

			if (line.indexOf("□")>=0)
				continue;
			
			for (int i = 0; i < statement.length; i++) {
				if (statement[i].length() != 5 && statement[i].length() > 2) {
					isFive = false;
					break;
				} else {
				}
			}
			
			
			int xcount =0;
			for (int i = 0; i < statement.length && isFive; i++) {
				if (statement[i].length() == 5 ) {
					writer1.write(statement[i]);
					xcount ++;
					if ((xcount) % 4 == 0 && i != 0) {
						writer1.newLine();
						xcount =0;
					} else {
						writer1.write(".");
					}
				}
			}
			if (xcount<4 && xcount>0)
			{
				writer1.newLine();
			}
			

		}
		// out put

		writer1.close();

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

	private static final String[] fSplit(String src, char delim) {
		ArrayList<String> output = new ArrayList<String>();
		int index = 0;
		int lindex = 0;
		while ((index = src.indexOf(delim, lindex)) != -1) {
			output.add(src.substring(lindex, index));
			lindex = index + 1;
		}
		output.add(src.substring(lindex));
		return output.toArray(new String[output.size()]);
	}

	private static final String[] fSplit(String src, String delim) {
		ArrayList<String> output = new ArrayList<String>();
		int index = 0;
		int lindex = 0;
		while ((index = src.indexOf(delim, lindex)) != -1) {
			output.add(src.substring(lindex, index));
			lindex = index + delim.length();
		}
		output.add(src.substring(lindex));
		return output.toArray(new String[output.size()]);
	}

}
