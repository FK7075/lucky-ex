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
	private boolean log;//控制台信息
	private boolean cache;//查询缓存
	private Integer fieldlength;//建表时字段的长度
	private String packages;//逆向工程创建的类的所在的包
	private String srcPath;//逆向工程创建类时需要知道的src文件夹的绝对路径
	private Integer poolmin;//连接池最小链接数量
	private Integer poolmax;//连接池最大链接数量
	private String xmlpath;
	private List<String> scans=new ArrayList<>();;//组件扫描
	private List<String> scans_mapper=new ArrayList<>();//mapper接口扫描
	private List<String> claurl=new ArrayList<>();//自动建表的配置
	
	
	
	public List<String> getScans_mapper() {
		return scans_mapper;
	}
	public void setScans_mapper(List<String> scans_mapper) {
		this.scans_mapper = scans_mapper;
	}
	public List<String> getClaurl() {
		return claurl;
	}
	public void setClaurl(List<String> claurl) {
		this.claurl = claurl;
	}
	public String getXmlpath() {
		return xmlpath;
	}
	public void setXmlpath(String xmlpath) {
		this.xmlpath = xmlpath;
	}
	
	public List<String> getScans() {
		return scans;
	}
	public void setScans(List<String> scans) {
		this.scans = scans;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public Integer getPoolmin() {
		return poolmin;
	}
	public void setPoolmin(Integer poolmin) {
		this.poolmin = poolmin;
	}
	public Integer getPoolmax() {
		return poolmax;
	}
	public void setPoolmax(Integer poolmax) {
		this.poolmax = poolmax;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public Integer getFieldlength() {
		return fieldlength;
	}
	public void setFieldlength(Integer fieldlength) {
		this.fieldlength = fieldlength;
	}
	public String getPackages() {
		return packages;
	}
	public void setPackages(String packages) {
		this.packages = packages;
	}
	
	public ProperConfig() {

	}

}
