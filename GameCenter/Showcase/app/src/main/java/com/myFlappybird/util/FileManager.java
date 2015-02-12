package com.myFlappybird.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class FileManager {
	
	private File sdPath;
	private File dirPath;
	private File file;
	
	public String fileReader() {
		String score = null;
		try {
			FileInputStream fin = new FileInputStream(file);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			score = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return score;
	}
	
	public void fileWriter(String msg) {
		String score = null;
		try {
			FileOutputStream fout = new FileOutputStream(file);
			byte[] buffer = msg.getBytes();
			fout.write(buffer);
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initFile() {
		String dirpath = getSdCardPath() + File.separator + "FlyBird";
		dirPath = new File(dirpath);
		if(!dirPath.exists()) {
			dirPath.mkdirs();
		}
		
		String filePath = dirpath + File.separator + "score.txt";
		file = new File(filePath);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getSdCardPath(){ 
		sdPath = Environment.getExternalStorageDirectory();
		return sdPath.toString(); 
	}
	
	public boolean isSdCardAvalible() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在 
	}
}
