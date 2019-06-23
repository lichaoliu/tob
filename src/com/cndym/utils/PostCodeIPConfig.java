package com.cndym.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.cndym.utils.Utils;

public class PostCodeIPConfig {
	private static Logger logger = Logger.getLogger(PostCodeIPConfig.class);
	public static final String CONFIG_FILE = "postCodeIPConfig.properties";
	private static Map<String, String> map = new HashMap<String, String>();

	static {
		forInstance();
	}

	public static void forInstance() {
		try {
            Properties properties = new Properties();
			InputStream inputStream = new FileInputStream(Utils.getClassPath() + CONFIG_FILE);
			properties.load(inputStream);
			Enumeration<Object> all = properties.keys();
			while (all.hasMoreElements()) {
				String name = (String) all.nextElement();
				String value = (String) properties.get(name);
				map.put(name, value);
			}
		} catch (Exception e) {
			logger.error("读取文件postCodeIPConfig.properties出错,msg="+e.getMessage());
		}
	}

	public static String getValue(String name) {
		if (map.containsKey(name)) {
			return map.get(name);
		}
		return null;
	}

	public static void main(String args[]) {
		System.out.println("115.290.195.160="+PostCodeIPConfig.getValue("115.290.195.160"));
	}
}
