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
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Chapter {
	public String Title;
	public StringBuilder Text;

	public Chapter() {
		Text = new StringBuilder();
	}
}

public class SplitTxtNovel {

	static Pattern p1 = Pattern.compile("<![^>]*>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	public static void main(String[] args) throws Exception {
		String filePath = "/tmp/novel1.txt";
		ArrayList<Chapter> article = new ArrayList<Chapter>();
		Chapter row = new Chapter();
		;

		Scanner s = new Scanner(new File(filePath));
		while (s.hasNextLine()) {
			String line = s.nextLine();
			if (line != null) {

				line = line.replace("UU看书", "");
				line = line.replace("www.uｕkansｈu.ｃom", "");
				// line = line.replace("www.uｕkansｈu.cｏm", "");

				line = p1.matcher(line).replaceAll("");
				/*
				 * if (line.trim().length()<1) continue;
				 */
				if (line.indexOf("小说网") > 0)
					continue;

				if (line.startsWith("===第")) {
					if (row == null) {
						row = new Chapter();
					} else {
						article.add(row);
						row = new Chapter();
					}
					row.Title = line;
				} else {
					row.Text.append(line).append("\n");
				}
			}

		}
		article.add(row);
		s.close();
		System.out.println("##### for JDK 8+");

		FileOutputStream fos = new FileOutputStream(filePath + "X");

		try (Writer w = new OutputStreamWriter(fos, "UTF-8")) {

			for (int i = article.size() - 1; i >= 0; i--) {
				Chapter ch = article.get(i);
				if (ch.Title != null)
					w.write(ch.Title);
				if (ch.Text != null)
					w.write(ch.Text.toString());
			}
		}
	}

	/*
	 * 
	 * try (Stream<String> stream = Files.lines(Paths.get(filePath),
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
