package com.lucky.jacklamb.sqlcore.c3p0;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.lucky.jacklamb.exception.NoDataSourceException;
import com.lucky.jacklamb.exception.NotFindBeanPropertyException;
import com.lucky.jacklamb.ioc.ApplicationBeans;
import com.lucky.jacklamb.ioc.config.ScanConfig;

public class ReadProperties {

	private static List<DataSource> allDataSource;
	
	private final static String defaultDB=ScanConfig.getScanConfig().getDefaultDB();
	
	public static List<DataSource> getAllDataSource() {
		allDataSources();
		return allDataSource;
	}

	public static List<DataSource> readList() {
		
		List<DataSource> dataList = new ArrayList<>();
		URL url = DataSource.class.getClassLoader().getResource(defaultDB);
		if (url == null)
			return null;
		DataSource db;
		db = read(defaultDB);
		dataList.add(db);
		for (String other : db.getOtherproperties())
			dataList.add(read(other));
		return dataList;
	}

	public static DataSource read(String propertiesPath) {
		DataSource dataSource = new DataSource();
		URL url = DataSource.class.getClassLoader().getResource(propertiesPath);
		if(url==null)
			throw new NoDataSourceException("\"找不到外部db配置文件classpath:\""+propertiesPath);
		Properties property = new Properties();
		InputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(url.getPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			property.load(bf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String name, driverClass, jdbcUrl, user, password, acquireIncrement, initialPoolSize, maxPoolSize, minPoolSize,
				maxidleTime, maxConnectionAge, maxStatements,checkoutTimeout, maxStatementsPerConnection, reversePckage, log, formatSqlLog, cache,
				srcpath, createTable, otherproperties;
		name = property.getProperty("name");
		driverClass = property.getProperty("driverClass");
		jdbcUrl = property.getProperty("jdbcUrl");
		user = property.getProperty("user");
		password = property.getProperty("password");
		acquireIncrement = property.getProperty("acquireIncrement");
		initialPoolSize = property.getProperty("initialPoolSize");
		maxPoolSize = property.getProperty("maxPoolSize");
		minPoolSize = property.getProperty("minPoolSize");
		maxidleTime = property.getProperty("maxidleTime");
		maxConnectionAge = property.getProperty("maxConnectionAge");
		checkoutTimeout= property.getProperty("checkoutTimeout");
		maxStatements = property.getProperty("maxStatements");
		maxStatementsPerConnection = property.getProperty("maxStatementsPerConnection");
		reversePckage = property.getProperty("reverse.pckage");
		log = property.getProperty("log");
		formatSqlLog=property.getProperty("formatSqlLog");
		cache = property.getProperty("cache");
		srcpath = property.getProperty("srcpath");
		createTable = property.getProperty("create.table");
		otherproperties = property.getProperty("otherproperties");
		if (defaultDB.equals(propertiesPath)) {
			name="defaultDB";
			if (driverClass == null || jdbcUrl == null || user == null || password == null || driverClass == ""
					|| jdbcUrl == "" || user == "" || password == "")
				throw new NotFindBeanPropertyException("在calsspath:"+defaultDB+"配置文件中找不到必须属性\"driverClass\",\"jdbcUrl\",\"user\",\"password\"");
		} else {
			DataSource defaultData = read(defaultDB);
			if(name==null||name=="")
				throw new NotFindBeanPropertyException("在calsspath:"+propertiesPath+"配置文件中找不到必须属性\"name\"");
			if (jdbcUrl == null||jdbcUrl=="")
				throw new NotFindBeanPropertyException("在calsspath:" + propertiesPath + "配置文件中找不到必须属性\"jdbcUrl\"");
			if (driverClass == null||driverClass=="")
				dataSource.setDriverClass(defaultData.getDriverClass());
			if (user == null||user=="")
				dataSource.setUser(defaultData.getUser());
			if (password == null||password=="")
				dataSource.setPassword(defaultData.getPassword());
			if (log == null || log == "")
				dataSource.setLog(defaultData.isLog());
			if (formatSqlLog == null || formatSqlLog == "")
				dataSource.setFormatSqlLog(defaultData.isFormatSqlLog());
			if(cache==null||cache=="")
				dataSource.setCache(defaultData.isCache());
			if(reversePckage==null||reversePckage=="")
				dataSource.setReversePack(defaultData.getReversePack());
			if(srcpath==null||srcpath=="")
				dataSource.setSrcPath(defaultData.getSrcPath());
			if(createTable==null||createTable=="")
				dataSource.setCaeateTable(defaultData.getCaeateTable());
		}
		dataSource.setName(name);
		dataSource.setDriverClass(driverClass);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		if (acquireIncrement != null && acquireIncrement != "")
			dataSource.setAcquireIncrement(Integer.parseInt(acquireIncrement));
		if (initialPoolSize != null && initialPoolSize != "")
			dataSource.setInitialPoolSize(Integer.parseInt(initialPoolSize));
		if (maxPoolSize != null && maxPoolSize != "")
			dataSource.setMaxPoolSize(Integer.parseInt(maxPoolSize));
		if (checkoutTimeout != null && checkoutTimeout != "")
			dataSource.setCheckoutTimeout(Integer.parseInt(checkoutTimeout));
		if (minPoolSize != null && minPoolSize != "")
			dataSource.setMinPoolSize(Integer.parseInt(minPoolSize));
		if (maxidleTime != null && maxidleTime != "")
			dataSource.setMaxidleTime(Integer.parseInt(maxidleTime));
		if (maxConnectionAge != null && maxConnectionAge != "")
			dataSource.setMaxConnectionAge(Integer.parseInt(maxConnectionAge));
		if (maxStatements != null && maxStatements != "")
			dataSource.setMaxStatements(Integer.parseInt(maxStatements));
		if (maxStatementsPerConnection != null && maxStatementsPerConnection != "")
			dataSource.setMaxStatementsPerConnection(Integer.parseInt(maxStatementsPerConnection));
		if (log != null && log != "")
			dataSource.setLog(Boolean.parseBoolean(log));
		if (formatSqlLog != null || formatSqlLog != "")
			dataSource.setFormatSqlLog(Boolean.parseBoolean(formatSqlLog));
		if (cache != null && cache != "")
			dataSource.setCache(Boolean.parseBoolean(cache));
		if (reversePckage != null && reversePckage != "")
			dataSource.setReversePack(reversePckage);
		if (srcpath != null && srcpath != "")
			dataSource.setSrcPath(srcpath);
		if (createTable != null && createTable != "") {
			dataSource.getCaeateTable().clear();
			String[] split = createTable.replaceAll(" ", "").split(",");
			for (String st : split)
				dataSource.getCaeateTable().add(property.getProperty(st));
		}
		if (otherproperties != null && otherproperties != "") {
			String[] split = otherproperties.replaceAll(" ", "").split(",");
			for (String pah : split) {
				String dbpath = property.getProperty(pah);
				if(dbpath==null)
					throw new NotFindBeanPropertyException("在calsspath:"+propertiesPath+"配置文件中找不到必须的外部db文件配置项:\""+pah+"\"！");
				dataSource.getOtherproperties().add(dbpath);
			}
		}
		return dataSource;
	}
	

	public static DataSource getDataSource(String name) {
		allDataSources();
		for (DataSource curr : allDataSource) {
			if (name.equals(curr.getName()))
				return curr;
		}
		throw new NoDataSourceException("在Ioc容器中找不到name=" + name + "的DataSource！");
	}

	public static void allDataSources() {
		if (allDataSource == null) {
			boolean haveDefaultDB=false;
			List<DataSource> iocDataSources = ApplicationBeans.createApplicationBeans().getDataSources();
			List<DataSource> dbDataSource = readList();
			if (dbDataSource != null)
				dbDataSource.stream().filter(f -> filter(iocDataSources, f.getName())).forEach(iocDataSources::add);
			for(DataSource data:iocDataSources) {
				if("defaultDB".equals(data.getName())) {
					haveDefaultDB=true;
					break;
				}
			}
			if(!haveDefaultDB&&iocDataSources.size()!=1)
				throw new NoDataSourceException("找不到默认的数据源，请检查是否配置了name属性为\"defaultDB\"的数据源");
			if(!haveDefaultDB&&iocDataSources.size()==1)
				iocDataSources.get(0).setName("defaultDB");
			allDataSource = iocDataSources;
		}
	}

	public static boolean filter(List<DataSource> list, String name) {
		for (DataSource data : list) {
			if (name.equals(data.getName()))
				return false;
		}
		return true;
	}

}
