package com.lucky.jacklamb.ioc.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lucky.jacklamb.enums.Logo;

/**
 * IOC组件的默认位置后缀配置
 * 
 * @author DELL
 *
 */
public class ScanConfig {

	private static ScanConfig scanfig;

	/**
	 * Controller组件所在包的后缀
	 */
	private List<String> controllerPackSuffix;

	/**
	 * Service组件所在包的后缀
	 */
	private List<String> servicePackSuffix;

	/**
	 * Repository组件所在包的后缀
	 */
	private List<String> repositoryPackSuffix;

	/**
	 * Agent组件所在包的后缀
	 */
	private List<String> agentPackSuffix;

	/**
	 * 普通组件所在包的后缀
	 */
	private List<String> componentPackSuffix;

	/**
	 * 实体类所在包的后缀
	 */
	private List<String> pojoPackSuffix;

	/**
	 * 设置Lucky运行时的Logo
	 */
	private Logo logo;

	/**
	 * 设置自定义的Logo
	 */
	private String customLogo;

	/**
	 * dataSource的默认读取路径(class:db.properties)
	 */
	private String defaultDB;
	
	public static ScanConfig getScanfig() {
		return scanfig;
	}
	public static void setScanfig(ScanConfig scanfig) {
		ScanConfig.scanfig = scanfig;
	}

	private ScanConfig() {
		controllerPackSuffix = new ArrayList<>();
		servicePackSuffix = new ArrayList<>();
		repositoryPackSuffix = new ArrayList<>();
		agentPackSuffix = new ArrayList<>();
		componentPackSuffix = new ArrayList<>();
		pojoPackSuffix = new ArrayList<>();
	}

	public String getDefaultDB() {
		return defaultDB;
	}

	/**
	 * 重置dataSource的默认读取路径(默认class:db.properties)
	 * 
	 * @param defaultDB
	 */
	public void setDefaultDB(String defaultDB) {
		this.defaultDB = defaultDB;
	}

	public String getCustomLogo() {
		return customLogo;
	}

	/**
	 * 设置一个自定义Logo
	 * 
	 * @param customLogo
	 */
	public void setCustomLogo(String customLogo) {
		this.customLogo = customLogo;
	}

	public Logo getLogo() {
		return logo;
	}

	/**
	 * 在Lucky中选择一个Logo
	 * 
	 * @param logo
	 */
	public void setLogo(Logo logo) {
		this.logo = logo;
	}

	public List<String> getControllerPackSuffix() {
		return controllerPackSuffix;
	}

	/**
	 * 添加一个装载Controller组件的包后缀
	 * 
	 * @param suffix
	 */
	public void addControllerPackSuffix(String... suffix) {
		controllerPackSuffix.addAll(Arrays.asList(suffix));
	}

	/**
	 * 清空原有的配置后，添加一个装载Controller组件的包后缀
	 * 
	 * @param suffix
	 */
	public void emptyAddControllerPackSuffix(String... suffix) {
		controllerPackSuffix.clear();
		controllerPackSuffix.addAll(Arrays.asList(suffix));
	}

	public void setControllerPackSuffix(List<String> controllerPackSuffix) {
		this.controllerPackSuffix = controllerPackSuffix;
	}

	public List<String> getServicePackSuffix() {
		return servicePackSuffix;
	}

	/**
	 * 添加一个装载Controller组件的包后缀
	 * 
	 * @param suffix
	 */
	public void addServicePackSuffix(String... suffix) {
		servicePackSuffix.addAll(Arrays.asList(suffix));
	}

	/**
	 * 清空原有的配置后，添加一个装载Service组件的包后缀
	 * 
	 * @param suffix
	 */
	public void emptyAddServicePackSuffix(String... suffix) {
		servicePackSuffix.clear();
		servicePackSuffix.addAll(Arrays.asList(suffix));
	}

	public void setServicePackSuffix(List<String> servicePackSuffix) {
		this.servicePackSuffix = servicePackSuffix;
	}

	public List<String> getRepositoryPackSuffix() {
		return repositoryPackSuffix;
	}

	/**
	 * 添加一个装载Repository组件的包后缀
	 * 
	 * @return
	 */
	public void addRepositoryPackSuffix(String... suffix) {
		repositoryPackSuffix.addAll(Arrays.asList(suffix));
	}

	/**
	 * 清空原有的配置后，添加一个装载Repository组件的包后缀
	 * 
	 * @param suffix
	 */
	public void emptyAddRepositoryPackSuffix(String... suffix) {
		repositoryPackSuffix.clear();
		repositoryPackSuffix.addAll(Arrays.asList(suffix));
	}

	public void setRepositoryPackSuffix(List<String> repositoryPackSuffix) {
		this.repositoryPackSuffix = repositoryPackSuffix;
	}

	public List<String> getAgentPackSuffix() {
		return agentPackSuffix;
	}

	/**
	 * 添加一个装载Agent组件的包后缀
	 * 
	 * @param suffix
	 */
	public void addAgentPackSuffix(String... suffix) {
		agentPackSuffix.addAll(Arrays.asList(suffix));
	}

	/**
	 * 清空原有的配置后，添加一个装载Agent组件的包后缀
	 * 
	 * @param suffix
	 */
	public void emptyAddAgentPackSuffix(String... suffix) {
		agentPackSuffix.clear();
		agentPackSuffix.addAll(Arrays.asList(suffix));
	}

	public void setAgentPackSuffix(List<String> agentPackSuffix) {
		this.agentPackSuffix = agentPackSuffix;
	}

	public List<String> getComponentPackSuffix() {
		return componentPackSuffix;
	}

	/**
	 * 添加一个装载普通组件的包后缀
	 * 
	 * @param suffix
	 */
	public void addComponentPackSuffix(String... suffix) {
		componentPackSuffix.addAll(Arrays.asList(suffix));
	}

	/**
	 * 清空原有的配置后，添加一个装载普通组件的包后缀
	 * 
	 * @param suffix
	 */
	public void emptyAddComponentPackSuffix(String... suffix) {
		componentPackSuffix.clear();
		componentPackSuffix.addAll(Arrays.asList(suffix));
	}

	public void setComponentPackSuffix(List<String> componentPackSuffix) {
		this.componentPackSuffix = componentPackSuffix;
	}

	public List<String> getPojoPackSuffix() {
		return pojoPackSuffix;
	}

	public void setPojoPackSuffix(List<String> pojoPackSuffix) {
		this.pojoPackSuffix = pojoPackSuffix;
	}

	/**
	 * 添加一个装载pojo实体组件的包后缀
	 * 
	 * @param suffix
	 */
	public void addPojoPackSuffix(String... suffix) {
		pojoPackSuffix.addAll(Arrays.asList(suffix));
	}

	/**
	 * 清空原有的配置后，添加一个装载pojo实体组件的包后缀
	 * 
	 * @param suffix
	 */
	public void emptyAddPojoPackSuffix(String... suffix) {
		pojoPackSuffix.clear();
		pojoPackSuffix.addAll(Arrays.asList(suffix));
	}
	
	public static ScanConfig defaultScanConfig() {
		if (scanfig == null) {
			scanfig = new ScanConfig();
			scanfig.addControllerPackSuffix("controller");
			scanfig.addServicePackSuffix("service");
			scanfig.addRepositoryPackSuffix("dao", "repository", "mapper");
			scanfig.addComponentPackSuffix("component", "bean");
			scanfig.addAgentPackSuffix("agent");
			scanfig.addPojoPackSuffix("pojo", "entity");
			scanfig.setLogo(Logo.MOUSELET);
			scanfig.setDefaultDB("db.properties");
		}
		return scanfig;
	}


}
