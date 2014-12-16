package com.appautocreate.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	public String antPath = "";
	public String loadPath = "";

	public Properties ant;// 控制app生成
	public Properties load;// 控制程序运行

	public static final int SAVEINANT = -1;
	public static final int SAVEINLOAD = -2;
	public static final int SAVEALL = -3;

	public void init(String antPath, String loadPath) {
		ant = new Properties();
		load = new Properties();
		this.antPath = antPath;
		this.loadPath = loadPath;
		try {
			ant.load(new FileInputStream(antPath));
			load.load(new FileInputStream(loadPath));
		} catch (Exception e) {
		}
	}
	/**
	 * put put后需要commit 才能保存到文件
	 * 
	 * @param key
	 * @param value
	 * @param savatype
	 */
	public void put(String key, String value, int savatype) {
		switch (savatype) {
		case SAVEINANT:
			ant.setProperty(key, value);
			break;
		case SAVEINLOAD:
			load.setProperty(key, value);
			break;
		case SAVEALL:
			ant.setProperty(key, value);
			load.setProperty(key, value);
			break;
		}
	};

	public void commit() {
		FileOutputStream antoutputStream = null;
		FileOutputStream loadoutputStream = null;
		try {
			antoutputStream = new FileOutputStream(antPath);
			loadoutputStream = new FileOutputStream(loadPath);
			load.store(loadoutputStream, "load-info");
			ant.store(antoutputStream, "ant-info");
			antoutputStream.close();
			loadoutputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
			try {
				antoutputStream.close();
				loadoutputStream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
