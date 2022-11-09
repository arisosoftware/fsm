/**
 * 
 */
package com.ariso.fsm2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

class BookMarkVO {
	public int ID;
	public int level;
	public String url;
	// public String cache;
	public String name;
	public BookMarkVO parent;
	public int items;
	public boolean TobeDelete;
	// public StringBuffer buffer = new StringBuffer();
}

/**
 * @author airsoft
 *
 */
public class BookmarkFSM extends FSM<String> {

	public String State_Header = "Header";
	public String State_Links = "Links";
	public String State_Bookmark = "Bookmark";
	public int SeqId = 1;
	public ArrayList<BookMarkVO> Result = new ArrayList<BookMarkVO>();

	public HashSet<String> hashSet = new HashSet<String>();
	public BookMarkVO vo = new BookMarkVO();
	public BookMarkVO folder = null;

	public BookmarkFSM() {

		this.AddHandel(new FSMNodes<String>() {

			@Override
			public String State() {
				return State_Header;
			}

			@Override
			public String Run(String inputLine, FSM<String> fsm) {

				BookmarkFSM fff = (BookmarkFSM) fsm;
				fff.SeqId++;

				int level = inputLine.indexOf("<DT>");
				String trimLine = inputLine.trim();
				if (level >= 0) {
					level = level / 4;
					fff.vo.level = level;
					fff.vo.ID = fff.SeqId;
					if (trimLine.startsWith("<DT><H3")) {
						int a1 = trimLine.indexOf('>', 8);
						int a2 = trimLine.indexOf('<', a1 + 1);
						fff.vo.name = trimLine.substring(a1, a2);
						fff.Result.add(fff.vo);
						fff.vo = new BookMarkVO();
					}

					if (trimLine.startsWith("<DT><A")) {
						try {
							int a1 = trimLine.indexOf("=\"", 8);
							int a2 = trimLine.indexOf("\" A", a1 + 2);
							int a3 = trimLine.indexOf("\">", a2 + 1);
							int a4 = trimLine.indexOf("</A>", a3 + 1);
							String url = trimLine.substring(a1 + 2, a2);

							if (fff.hashSet.contains(url))
								return State_Header;
							fff.hashSet.add(url);

							fff.vo.url = url;
							fff.vo.name = trimLine.substring(a3 + 2, a4);
							fff.Result.add(fff.vo);
							fff.vo = new BookMarkVO();
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}

				return State_Header;
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

		BookmarkFSM fsm = new BookmarkFSM();

		String filePath = "/tmp/bookmarks_11_8_22.html";
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		int lineNo = 0;

		String line;
		while ((line = reader.readLine()) != null) {
			lineNo++;
			fsm.OnRun(line);
		}
		// out put

		// fsm.ExportBookMarkVoArraylist(fsm.Result, "Step1.txt");
		ArrayList<BookMarkVO> newResult = null;
		int stepNo = 100;
		do {
			stepNo++;
			fsm.ExportBookMarkVoArraylist(fsm.Result, "/tmp/Step" + stepNo + ".txt");
			newResult = fsm.ScanEmptyAndRemoveFolder(fsm.Result);
			if (newResult != null) {
				fsm.Result = newResult;
			}
		} while (newResult != null);
		fsm.ExportBookMarkVoArraylist(fsm.Result, "/tmp/Step" + stepNo + ".txt");
	}

	void ExportBookMarkVoArraylist(ArrayList<BookMarkVO> Result, String FileName) {
		try {
			BufferedWriter writer1 = new BufferedWriter(new FileWriter(FileName));

			for (int i = 0; i < Result.size(); i++) {
				BookMarkVO vo = Result.get(i);
				writer1.write(String.format("%d,%d,%s,%s\n", vo.level, vo.ID, vo.name, vo.url));
			}
			writer1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/// return null means no need to change anything.
	ArrayList<BookMarkVO> ScanEmptyAndRemoveFolder(ArrayList<BookMarkVO> Result) {
		boolean containsEmptyFolder = false;

		Stack<BookMarkVO> stack = new Stack<BookMarkVO>();
		ArrayList<BookMarkVO> newResult = new ArrayList<BookMarkVO>();

		BookMarkVO root = new BookMarkVO();
		root.level = 0;
		stack.push(root);
		stack.push(Result.get(0));
		for (int i = 1; i < Result.size(); i++) {
			BookMarkVO vo = Result.get(i);
			BookMarkVO curFolder = stack.lastElement();

			if (vo.level > curFolder.level) {
				curFolder.items++;
				vo.parent = curFolder;
			} else if (vo.level <= curFolder.level) {
				do {
					if (curFolder.parent == null) {
						System.out.print("xx");
					}

					curFolder = stack.pop();

					// || curFolder.parent == null

				} while (curFolder.level > vo.level);
				if (curFolder.level > vo.level) {
					curFolder.items++;
					vo.parent = curFolder;

				}
			}

			if (vo.url == null) {
				stack.push(vo);
			}

		}
		//
		for (int i = 0; i < Result.size(); i++) {
			BookMarkVO vo = Result.get(i);
			if (vo.items == 0 && vo.url == null) {
				containsEmptyFolder = true;
			} else {
				newResult.add(vo);
			}
		}

		if (containsEmptyFolder) {
			return newResult;
		} else {
			return null;
		}
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
