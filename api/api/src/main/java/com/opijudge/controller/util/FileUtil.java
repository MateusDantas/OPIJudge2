package com.opijudge.controller.util;

import com.sun.jersey.core.header.FormDataContentDisposition;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {


	public static File convertToFile(InputStream inputStream, FormDataContentDisposition fileDetail, String totalPath) {
		
		String totPath = totalPath;
		
		saveToFile(inputStream, totPath);
		
		File file = new File(totPath);
		return file;
	}
	
	private static boolean saveToFile(InputStream inputStream, String fileLocation) {
		
		try {
			OutputStream out = null;
			int read = 0;
			byte[] bytes = new byte[1024];
			
			File file = new File(fileLocation);
			file.mkdirs();
			
			out = new FileOutputStream(file);
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static String getFileExtension(String fileName) {
		
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		
		return "";
	}
	
	public static String getFileExtension(File file) {
		
		String fileName = file.getName();
		return getFileExtension(fileName);
	}
}
