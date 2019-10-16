package com.lucky.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * lucky.properties���ݵİ�װ��
 * @author fk-7075
 *
 */
public class ProperConfig {
	private String driver;//jdbc.driver
	private String url;//jdbc.url
	private String username;//jdbc.username
	private String password;//jdbc.password
	private boolean log;//����̨��Ϣ
	private boolean cache;//��ѯ����
	private Integer fieldlength;//����ʱ�ֶεĳ���
	private String packages;//���򹤳̴�����������ڵİ�
	private String srcPath;//���򹤳̴�����ʱ��Ҫ֪����src�ļ��еľ���·��
	private Integer poolmin;//���ӳ���С��������
	private Integer poolmax;//���ӳ������������
	private String xmlpath;
	private List<String> scans=new ArrayList<>();;//���ɨ��
	private List<String> scans_mapper=new ArrayList<>();//mapper�ӿ�ɨ��
	private List<String> claurl=new ArrayList<>();//�Զ����������
	
	
	
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
