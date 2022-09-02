package com.ariso.fsm2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Utf8toPinyin {

	public static void main(String[] args) {

		//pinyin-utf8.dat
	}

	public HashMap<String, String> pinyinLib = new HashMap<String, String>();

	private String loadPinyinLib() throws IOException {
		
		FileResourcesUtils frs = new FileResourcesUtils();
		return "todo:";
//		
//
//        FileResourcesUtils app = new FileResourcesUtils();
//
//        //String fileName = "database.properties";
//        String fileName = "pinyin-utf8.dat";
//
//        System.out.println("getResourceAsStream : " + fileName);
//        InputStream is = app.getFileFromResourceAsStream(fileName);
//        printInputStream(is);
//
//        System.out.println("\ngetResource : " + fileName);
//        File file = app.getFileFromResource(fileName);
//        printFile(file);
//        
//        
//        
//		BufferedReader reader = new BufferedReader(new FileReader(file));
//		String line = null;
//		StringBuilder stringBuilder = new StringBuilder();
//		String ls = System.getProperty("line.separator");
//
//		try {
//			while ((line = reader.readLine()) != null) {
//				stringBuilder.append(line);
//				stringBuilder.append(ls);
//			}
//
//			return stringBuilder.toString();
//		} finally {
//			reader.close();
//		}
	}
	
}
