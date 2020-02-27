package com.lucky.jacklamb.file.ini;

import java.util.Map;

public class AppConfig {
	
	public static Map<String,String> getAppParamMap() {
		return IniFilePars.getIniFilePars().getSectionMap("App");
	}
	
	public static String getAppParam(String key) {
		return getAppParamMap().get(key);
	}
	
	public static Map<String,String> getSectionMap(String section) {
		return IniFilePars.getIniFilePars().getSectionMap(section);
	}
	
	public static String getValue(String section,String key) {
		return IniFilePars.getIniFilePars().getSectionMap(section).get(key);
	}

}
