package com.arisosoftware.webtest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URLDecoder;

import org.apache.commons.codec.digest.DigestUtils;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;

public class ImageDownloadUtil {

	static final int proxyPort = 1080; // your proxy port
	static final String proxyHost = "127.0.0.1";

	public static OkHttpClient GetHttpClient() {

		InetSocketAddress proxyAddr = new InetSocketAddress(proxyHost, proxyPort);
		Proxy proxy = new Proxy(Proxy.Type.SOCKS, proxyAddr);
 

		OkHttpClient client = new OkHttpClient.Builder().proxy(proxy).build();
		return client;
	}

	static long ImageId = 1000000;

//	public String downLoadImage(String url, String savePath) {
//
//		try {
//			ImageId++;
//			String digest = DigestUtils.md5Hex(url);
//
//			String fileName = String.format("T%d.%s", ImageId, digest);
//			fileName = URLDecoder.decode(fileName, "UTF-8");
//			String picType = url.substring(url.lastIndexOf(".") + 1);
//
//			if (picType.contains("/") || picType.length() >= 5) {
//				picType = "png";
//			}
//			return downLoadImage(url, savePath, fileName, picType);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}

//	 public void CalcMd5void main(String[] args) {
//	        try {
//	            // Define the data file path and create an InputStream object.
//	            String data = System.getProperty("user.dir") + "/target/classes/data.txt" ;
//	            File file = new File(data);
//	            InputStream is = new FileInputStream(file);
//
//	            // Calculates the MD5 digest of the given InputStream object.
//	            // It will generate a 32 characters hex string.
//	            String digest = DigestUtils.md5Hex(is);
//	            System.out.println("Digest = " + digest);
//	            System.out.println("Length = " + digest.length());
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	    }
// }
//
//	private String downLoadImage(String url, String savePath, String fileName, String picType) {
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		if (url == null || url.equals(""))
//			return null;
//
//		File parentDir = new File(savePath);
//
//		if (!parentDir.exists()) {
//			parentDir.mkdirs();
//		}
//
//		String fullPath = savePath + File.separator + fileName + "." + picType;
//		File file = new File(fullPath);
//		if (file.exists() && file.length() > 1000) {
//			return fullPath;
//		}
//
//		InputStream in = null;
//		try {
//			HttpGet httpget = new HttpGet(url);
//			HttpResponse response = httpClient.execute(httpget);
//			HttpEntity entity = response.getEntity();
//			in = entity.getContent();
//
//			FileOutputStream fout = new FileOutputStream(file);
//			int l = -1;
//			byte[] tmp = new byte[1024];
//			while ((l = in.read(tmp)) != -1) {
//				fout.write(tmp, 0, l);
//			}
//			fout.flush();
//			fout.close();
//			return fullPath;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (in != null)
//					in.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}

}