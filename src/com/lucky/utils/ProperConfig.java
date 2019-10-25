package com.lucky.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * lucky.properties内容的包装类
 * @author fk-7075
 *
 */
public class ProperConfig {
	private String driver;//jdbc.driver
	private String url;//jdbc.url
	private String username;//jdbc.username
	private String password;//jdbc.password
	private boolean log=false;//控制台信息
	private boolean cache=false;//查询缓存
	private String packages;//逆向工程创建的类的所在的包
	private String srcPath;//逆向工程创建类时需要知道的src文件夹的绝对路径
	private Integer poolmin=10;//连接池最小链接数量
	private Integer poolmax=100;//连接池最大链接数量
	private String xmlpath;
	private List<String> scans=new ArrayList<>();//组件扫描
	private List<String> scans_mapper=new ArrayList<>();//mapper接口扫描
	private List<String> claurl=new ArrayList<>();//自动建表的配置
	
	
	/**
	 * mapper接口所在包
	 * @return
	 */
	public List<String> getScans_mapper() {
		return scans_mapper;
	}
	/**
	 * mapper接口所在包(缺省默认加载以mapper结尾的包内的mapper接口)
	 * @param scans_mapper
	 */
	public void setScans_mapper(List<String> scans_mapper) {
		this.scans_mapper = scans_mapper;
	}
	/**
	 * 自动建表机制-需要创建表对应类的包路径
	 * @return
	 */
	public List<String> getClaurl() {
		return claurl;
	}
	/**
	 * 自动建表机制-设置需要创建表对应类的包路径(缺省时使用建表机制会自动扫描以pojo和以entity结尾的包内的实体)
	 * @param claurl
	 */
	public void setClaurl(List<String> claurl) {
		this.claurl = claurl;
	}
	public String getXmlpath() {
		return xmlpath;
	}
	public void setXmlpath(String xmlpath) {
		this.xmlpath = xmlpath;
	}
	/**
	 * IOC组件所在的包
	 * @return
	 */
	public List<String> getScans() {
		return scans;
	}
	/**
	 * 设置IOC组件所在的包(缺省默认加载以controller、service、dao、repository结尾的包内的组件)
	 * @return
	 */
	public void setScans(List<String> scans) {
		this.scans = scans;
	}
	/**
	 * calsspath
	 * @return
	 */
	public String getSrcPath() {
		return srcPath;
	}
	/**
	 * 设置calsspath(逆向工程时会使用到)
	 * @param srcPath
	 */
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	/**
	 * 连接池的最小连接数
	 * @return
	 */
	public Integer getPoolmin() {
		return poolmin;
	}
	/**
	 * 设置连接池的最小连接数(缺省默认值：10)
	 * @param poolmin
	 */
	public void setPoolmin(Integer poolmin) {
		this.poolmin = poolmin;
	}
	/**
	 * 连接池的最大连接数
	 * @return
	 */
	public Integer getPoolmax() {
		return poolmax;
	}
	/**
	 * 设置连接池的最大连接数(缺省默认值：100)
	 * @param poolmax
	 */
	public void setPoolmax(Integer poolmax) {
		this.poolmax = poolmax;
	}
	/**
	 * 数据库驱动
	 * @return
	 */
	public String getDriver() {
		return driver;
	}
	/**
	 * 设置数据库驱动
	 * @param driver
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}
	/**
	 * 数据库的位置
	 * @return
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置数据库的位置
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 数据库的登录名
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置数据库的登录名
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 数据量密码
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置数据库登录密码
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 日志/Debug
	 * @return
	 */
	public boolean isLog() {
		return log;
	}
	/**
	 * 日志开关(默认false，不打印日志)
	 * @param log
	 */
	public void setLog(boolean log) {
		this.log = log;
	}
	/**
	 * 缓存
	 * @return
	 */
	public boolean isCache() {
		return cache;
	}
	/**
	 * 缓存开关(默认false，不使用)
	 * @param cache
	 */
	public void setCache(boolean cache) {
		this.cache = cache;
	}
	/**
	 * 逆向工程生成文件的位置
	 * @return
	 */
	public String getPackages() {
		return packages;
	}
	/**
	 * 设置逆向工程时生成文件的位置
	 * @param packages
	 */
	public void setPackages(String packages) {
		this.packages = packages;
	}
	
	public ProperConfig() {

	}

}
