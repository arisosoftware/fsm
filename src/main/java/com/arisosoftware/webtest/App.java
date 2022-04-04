package com.arisosoftware.webtest;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
 

public class App {
 

	public static String FullSizeImage = "FullSize";
	public static String SampleImage = "Sample";

	public static void main(String[] args) {

	 
	 
	}

	public static String decode(String url) {
		String prevURL = "";
		String decodeURL = url;
		while (!prevURL.equals(decodeURL)) {
			prevURL = decodeURL;
			decodeURL = Jsoup.parse(decodeURL).text();

		}
		return decodeURL;
	}



}
