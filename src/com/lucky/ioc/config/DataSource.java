package com.lucky.ioc.config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.lucky.ioc.PackageScan;

public class DataSource {

	private static DataSource dataSource;
	private static boolean first;

	private String driver;

	private String url;

	private String username;

	private String password;

	private int poolmin;

	private int poolmax;

	private boolean log;

	private boolean cache;

	private String reversePack;

	private String srcPath;

	private List<String> caeateTable;

	static {
		first=true;
		dataSource = new DataSource();
		dataSource.setPoolmax(100);
		dataSource.setPoolmin(10);
		List<String> suffixlist = new ArrayList<>();
		suffixlist.addAll(ScanConfig.getScanConfig().getPojoPackSuffix());
		dataSource.setCaeateTable(PackageScan.getPackageScan().loadComponent(suffixlist));
		dataSource.setLog(false);
		dataSource.setCache(false);
	}

	public String getSrcPath() {
		return srcPath;
	}

	/**
	 * 逆向工程相关配置
	 * 
	 * @param srcPath
	 *            项目classpath的绝对路径(src)
	 */
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public List<String> getCaeateTable() {
		return caeateTable;
	}

	/**
	 * 自动建表机制相关配置
	 * 
	 * @param caeateTable
	 *            清除默认配置，添加需要建表机制建表的实体类的Class集合
	 */
	public void setCaeateTable(List<String> caeateTable) {
		caeateTable.clear();
		this.caeateTable = caeateTable;
	}

	/**
	 * 自动建表机制相关配置
	 * 
	 * @param tableClass
	 *            在默认配置的基础上添加需要建表机制建表的实体类的Class集合
	 */
	public void addCaeateTable(String... tableClass) {
		caeateTable.addAll(Arrays.asList(tableClass));
	}

	/**
	 * 自动建表机制相关配置
	 * 
	 * @param tableClass
	 *            清除默认配置，添加需要建表机制建表的实体类的Class集和
	 */
	public void emptyAddCaeateTable(String... tableClass) {
		caeateTable.clear();
		caeateTable.addAll(Arrays.asList(tableClass));
	}

	public String getReversePack() {
		return reversePack;
	}

	/**
	 * 逆向工程相关配置
	 * 
	 * @param reversePack
	 *            配置用于存放逆向工程机制生成的实体类的包 <br>
	 *            eg: com.lucky.pojo
	 */
	public void setReversePack(String reversePack) {
		this.reversePack = reversePack;
	}

	private DataSource() {
		caeateTable = new ArrayList<>();
	}

	public boolean isLog() {
		return log;
	}

	/**
	 * 是否打印日志
	 * 
	 * @param log
	 */
	public void setLog(boolean log) {
		this.log = log;
	}

	public boolean isCache() {
		return cache;
	}

	/**
	 * 是否启用缓存
	 * 
	 * @param cache
	 */
	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public String getDriver() {
		return driver;
	}

	/**
	 * 配置数据库连接驱动
	 * 
	 * @param driver
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	/**
	 * 配置数据库的访问路径
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	/**
	 * 用户名
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * 登录密码
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public int getPoolmin() {
		return poolmin;
	}

	/**
	 * 设置连接池的最小连接数量
	 * 
	 * @param poolmin
	 */
	public void setPoolmin(int poolmin) {
		this.poolmin = poolmin;
	}

	public int getPoolmax() {
		return poolmax;
	}

	/**
	 * 设置连接池的最大连接数量
	 * 
	 * @param poolmax
	 */
	public void setPoolmax(int poolmax) {
		this.poolmax = poolmax;
	}

	private static void defaultDataSource() {
		if(first) {
			URL url = DataSource.class.getClassLoader().getResource("db.properties");
			if(url!=null) {
				Properties property = new Properties();
				InputStream fileInputStream;
				try {
					fileInputStream = new FileInputStream(url.getPath());
					BufferedReader bf = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"));
					property.load(bf);
					String log,cache,min,max,ct;
					dataSource.setDriver(property.getProperty("jdbc.driver"));
					dataSource.setUrl(property.getProperty("jdbc.url"));
					dataSource.setUsername(property.getProperty("jdbc.username"));
					dataSource.setPassword(property.getProperty("jdbc.password"));
					dataSource.setReversePack(property.getProperty("reverse.package"));
					dataSource.setSrcPath(property.getProperty("srcpath"));
					log=property.getProperty("log");
					cache=property.getProperty("cache");
					min=property.getProperty("pool.min");
					max=property.getProperty("pool.max");
					dataSource.setLog(log!=null&&!"".equals(log)?Boolean.parseBoolean(log):false);
					dataSource.setCache(cache!=null&&!"".equals(cache)?Boolean.parseBoolean(cache):false);
					dataSource.setPoolmin(min!=null&&!"".equals(min)?Integer.parseInt(min):10);
					dataSource.setPoolmax(max!=null&&!"".equals(max)?Integer.parseInt(max):10);
					ct=property.getProperty("create.table");
					if(ct!=null&&!"".equals(ct)) {
						String[] split = ct.replaceAll(" ", "").split(",");
						for(String tabcla:split) {
							dataSource.addCaeateTable(property.getProperty(tabcla));
						}
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			first=false;
		}
	}

	public static DataSource getDataSource() {
		defaultDataSource();
		PackageScan ps = PackageScan.getPackageScan();
		List<String> cfgsuffix = new ArrayList<>();
		cfgsuffix.add("appconfig");
		List<String> cfgClass = ps.loadComponent(cfgsuffix);
		for (String clzz : cfgClass) {
			try {
				Class<?> cfg = Class.forName(clzz);
				if (ApplicationConfig.class.isAssignableFrom(cfg)) {
					ApplicationConfig cfConfig = (ApplicationConfig) cfg.newInstance();
					cfConfig.setDataSource(dataSource);
					break;
				} else {
					continue;
				}
			} catch (ClassNotFoundException e) {
				continue;
			} catch (InstantiationException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			}
		}
		return dataSource;
	}

}
