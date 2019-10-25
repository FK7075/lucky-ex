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
	private boolean log=false;//����̨��Ϣ
	private boolean cache=false;//��ѯ����
	private String packages;//���򹤳̴�����������ڵİ�
	private String srcPath;//���򹤳̴�����ʱ��Ҫ֪����src�ļ��еľ���·��
	private Integer poolmin=10;//���ӳ���С��������
	private Integer poolmax=100;//���ӳ������������
	private String xmlpath;
	private List<String> scans=new ArrayList<>();//���ɨ��
	private List<String> scans_mapper=new ArrayList<>();//mapper�ӿ�ɨ��
	private List<String> claurl=new ArrayList<>();//�Զ����������
	
	
	/**
	 * mapper�ӿ����ڰ�
	 * @return
	 */
	public List<String> getScans_mapper() {
		return scans_mapper;
	}
	/**
	 * mapper�ӿ����ڰ�(ȱʡĬ�ϼ�����mapper��β�İ��ڵ�mapper�ӿ�)
	 * @param scans_mapper
	 */
	public void setScans_mapper(List<String> scans_mapper) {
		this.scans_mapper = scans_mapper;
	}
	/**
	 * �Զ��������-��Ҫ�������Ӧ��İ�·��
	 * @return
	 */
	public List<String> getClaurl() {
		return claurl;
	}
	/**
	 * �Զ��������-������Ҫ�������Ӧ��İ�·��(ȱʡʱʹ�ý�����ƻ��Զ�ɨ����pojo����entity��β�İ��ڵ�ʵ��)
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
	 * IOC������ڵİ�
	 * @return
	 */
	public List<String> getScans() {
		return scans;
	}
	/**
	 * ����IOC������ڵİ�(ȱʡĬ�ϼ�����controller��service��dao��repository��β�İ��ڵ����)
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
	 * ����calsspath(���򹤳�ʱ��ʹ�õ�)
	 * @param srcPath
	 */
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	/**
	 * ���ӳص���С������
	 * @return
	 */
	public Integer getPoolmin() {
		return poolmin;
	}
	/**
	 * �������ӳص���С������(ȱʡĬ��ֵ��10)
	 * @param poolmin
	 */
	public void setPoolmin(Integer poolmin) {
		this.poolmin = poolmin;
	}
	/**
	 * ���ӳص����������
	 * @return
	 */
	public Integer getPoolmax() {
		return poolmax;
	}
	/**
	 * �������ӳص����������(ȱʡĬ��ֵ��100)
	 * @param poolmax
	 */
	public void setPoolmax(Integer poolmax) {
		this.poolmax = poolmax;
	}
	/**
	 * ���ݿ�����
	 * @return
	 */
	public String getDriver() {
		return driver;
	}
	/**
	 * �������ݿ�����
	 * @param driver
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}
	/**
	 * ���ݿ��λ��
	 * @return
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * �������ݿ��λ��
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * ���ݿ�ĵ�¼��
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * �������ݿ�ĵ�¼��
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * ����������
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * �������ݿ��¼����
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * ��־/Debug
	 * @return
	 */
	public boolean isLog() {
		return log;
	}
	/**
	 * ��־����(Ĭ��false������ӡ��־)
	 * @param log
	 */
	public void setLog(boolean log) {
		this.log = log;
	}
	/**
	 * ����
	 * @return
	 */
	public boolean isCache() {
		return cache;
	}
	/**
	 * ���濪��(Ĭ��false����ʹ��)
	 * @param cache
	 */
	public void setCache(boolean cache) {
		this.cache = cache;
	}
	/**
	 * ���򹤳������ļ���λ��
	 * @return
	 */
	public String getPackages() {
		return packages;
	}
	/**
	 * �������򹤳�ʱ�����ļ���λ��
	 * @param packages
	 */
	public void setPackages(String packages) {
		this.packages = packages;
	}
	
	public ProperConfig() {

	}

}
