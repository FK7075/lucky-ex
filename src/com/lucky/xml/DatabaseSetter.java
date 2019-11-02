package com.lucky.xml;

import java.util.List;

public class DatabaseSetter {
	
	private String ip;
	
	private String databaseType;
	
	private String port;
	
	private String databaseName;
	
	private String  encode;
	
	private String user;;
	
	private String password;;
	
	private int poolMin;
	
	private int poolMax;
	
	private boolean cache;
	
	private boolean debug;
	
	private String reversePackage;
	
	private String srcPath;
	
	private List<String> componentPackage;
	
	private List<String> mapperPackage;
	
	private List<String> entityFullPath;
	
	public String getDriver() {
		if("mysql".equalsIgnoreCase(databaseType))
			return "com.mysql.jdbc.Driver";
		return null;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
	
	public int getPoolMin() {
		return poolMin;
	}

	public void setPoolMin(int poolMin) {
		this.poolMin = poolMin;
	}

	public int getPoolMax() {
		return poolMax;
	}

	public void setPoolMax(int poolMax) {
		this.poolMax = poolMax;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getReversePackage() {
		return reversePackage;
	}

	public void setReversePackage(String reversePackage) {
		this.reversePackage = reversePackage;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public List<String> getComponentPackage() {
		return componentPackage;
	}

	public void setComponentPackage(List<String> componentPackage) {
		this.componentPackage = componentPackage;
	}

	public List<String> getMapperPackage() {
		return mapperPackage;
	}

	public void setMapperPackage(List<String> mapperPackage) {
		this.mapperPackage = mapperPackage;
	}

	public List<String> getEntityFullPath() {
		return entityFullPath;
	}

	public void setEntityFullPath(List<String> entityFullPath) {
		this.entityFullPath = entityFullPath;
	}

	public String getEncode() {
		return encode;
	}

	public String getUrl() {
		String url="";
		if("mysql".equalsIgnoreCase(databaseType)) 
			url+="jdbc:mysql://";
		url+=ip+":"+port+"/"+databaseName;
		if(encode!=null)
			url+="?useUnicode=true&characterEncoding="+encode;
		return url;
	}

}
