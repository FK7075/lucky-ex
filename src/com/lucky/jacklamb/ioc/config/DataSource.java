package com.lucky.jacklamb.ioc.config;

import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.ioc.PackageScan;

public class DataSource {

	
	private String name;
	
	private String driverClass;

	private String jdbcUrl;

	private String user;

	private String password;

	private int acquireIncrement;

	private int initialPoolSize;
	
	private int maxPoolSize;

	private int minPoolSize;
	
	private int maxidleTime;

	private int maxConnectionAge;
	
	private int maxStatements;

	private int maxStatementsPerConnection;

	private boolean log;

	private boolean cache;

	private String reversePack;

	private String srcPath;

	private List<String> caeateTable;
	
	private List<String> otherproperties;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
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

	public int getAcquireIncrement() {
		return acquireIncrement;
	}

	public void setAcquireIncrement(int acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

	public int getInitialPoolSize() {
		return initialPoolSize;
	}

	public void setInitialPoolSize(int initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getMaxidleTime() {
		return maxidleTime;
	}

	public void setMaxidleTime(int maxidleTime) {
		this.maxidleTime = maxidleTime;
	}

	public int getMaxConnectionAge() {
		return maxConnectionAge;
	}

	public void setMaxConnectionAge(int maxConnectionAge) {
		this.maxConnectionAge = maxConnectionAge;
	}

	public int getMaxStatements() {
		return maxStatements;
	}

	public void setMaxStatements(int maxStatements) {
		this.maxStatements = maxStatements;
	}

	public int getMaxStatementsPerConnection() {
		return maxStatementsPerConnection;
	}

	public void setMaxStatementsPerConnection(int maxStatementsPerConnection) {
		this.maxStatementsPerConnection = maxStatementsPerConnection;
	}

	public boolean isLog() {
		return log;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public String getReversePack() {
		return reversePack;
	}

	public void setReversePack(String reversePack) {
		this.reversePack = reversePack;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public List<String> getCaeateTable() {
		return caeateTable;
	}

	public void setCaeateTable(List<String> caeateTable) {
		this.caeateTable = caeateTable;
	}

	public List<String> getOtherproperties() {
		return otherproperties;
	}

	public void setOtherproperties(List<String> otherproperties) {
		this.otherproperties = otherproperties;
	}

	public DataSource() {
		acquireIncrement=3;
		initialPoolSize=3;
		minPoolSize=1;
		maxPoolSize=15;
		maxidleTime=0;
		maxConnectionAge=0;
		maxStatements=0;
		maxStatementsPerConnection=0;
		List<String> suffixlist = new ArrayList<>();
		suffixlist.addAll(ScanConfig.getScanConfig().getPojoPackSuffix());
		caeateTable=PackageScan.getPackageScan().loadComponent(suffixlist);
		log=false;
		cache=false;
		otherproperties=new ArrayList<>();
	}

}
