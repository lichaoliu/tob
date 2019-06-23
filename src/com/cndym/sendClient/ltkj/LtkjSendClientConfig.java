package com.cndym.sendClient.ltkj;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.cndym.utils.Utils;

public class LtkjSendClientConfig {
	
	public static final String CONFIG_FILE = "ltkjSendClientConfig.properties";
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
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static String getValue(String name) {
		if (map.containsKey(name)) {
			return map.get(name);
		}
		return null;
	}

	public static void main(String args[]) {
		System.out.println("POST_CODE="+LtkjSendClientConfig.getValue("POST_CODE"));
	}
}
