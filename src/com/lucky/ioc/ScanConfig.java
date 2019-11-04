package com.lucky.ioc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * IOC组件的默认位置后缀配置
 * @author DELL
 *
 */
public class ScanConfig {
	
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
	
	

	public ScanConfig() {
		controllerPackSuffix=new ArrayList<>();
		servicePackSuffix=new ArrayList<>();
		repositoryPackSuffix=new ArrayList<>();
		agentPackSuffix=new ArrayList<>();
		componentPackSuffix=new ArrayList<>();
		pojoPackSuffix=new ArrayList<>();
	}

	public List<String> getControllerPackSuffix() {
		return controllerPackSuffix;
	}

	/**
	 * 添加一个装载Controller组件的包后缀
	 * @param suffix
	 */
	public void addControllerPackSuffix(String...suffix) {
		controllerPackSuffix.addAll(Arrays.asList(suffix));
	}
	/**
	 * 清空原有的配置后，添加一个装载Controller组件的包后缀
	 * @param suffix
	 */
	public void emptyAddControllerPackSuffix(String...suffix) {
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
	 * @param suffix
	 */
	public void addServicePackSuffix(String...suffix) {
		servicePackSuffix.addAll(Arrays.asList(suffix));
	}
	
	/**
	 * 清空原有的配置后，添加一个装载Service组件的包后缀
	 * @param suffix
	 */
	public void emptyAddServicePackSuffix(String...suffix) {
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
	 * @return
	 */
	public void addRepositoryPackSuffix(String...suffix) {
		repositoryPackSuffix.addAll(Arrays.asList(suffix));
	}
	
	/**
	 * 清空原有的配置后，添加一个装载Repository组件的包后缀
	 * @param suffix
	 */
	public void emptyAddRepositoryPackSuffix(String...suffix) {
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
	 * @param suffix
	 */
	public void addAgentPackSuffix(String...suffix) {
		agentPackSuffix.addAll(Arrays.asList(suffix));
	}
	
	/**
	 * 清空原有的配置后，添加一个装载Agent组件的包后缀
	 * @param suffix
	 */
	public void emptyAddAgentPackSuffix(String...suffix) {
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
	 * @param suffix
	 */
	public void addComponentPackSuffix(String...suffix) {
		componentPackSuffix.addAll(Arrays.asList(suffix));
	}
	
	/**
	 * 清空原有的配置后，添加一个装载普通组件的包后缀
	 * @param suffix
	 */
	public void emptyAddComponentPackSuffix(String...suffix) {
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
	 * @param suffix
	 */
	public void addPojoPackSuffix(String...suffix) {
		pojoPackSuffix.addAll(Arrays.asList(suffix));
	}
	
	/**
	 * 清空原有的配置后，添加一个装载pojo实体组件的包后缀
	 * @param suffix
	 */
	public void emptyAddPojoPackSuffix(String...suffix) {
		pojoPackSuffix.clear();
		pojoPackSuffix.addAll(Arrays.asList(suffix));
	}

	private static ScanConfig defaultScanConfig() {
		ScanConfig scanfig=new ScanConfig();
		scanfig.addControllerPackSuffix("controller");
		scanfig.addServicePackSuffix("service");
		scanfig.addRepositoryPackSuffix("dao","repository");
		scanfig.addComponentPackSuffix("component");
		scanfig.addAgentPackSuffix("agent");
		scanfig.addPojoPackSuffix("pojo","entity");
		return scanfig;
	}
	
	/**
	 * 得到有关包扫描的配置信息
	 * @return
	 */
	public static ScanConfig getScanConfig() {
		ScanConfig defaultScanConfig = defaultScanConfig();
		PackageScan ps=PackageScan.getPackageScan();
		List<String> cfgClass = ps.loadComponent("appconfig").stream().map(entry->entry.substring(0, entry.length()-6)).collect(Collectors.toList());
		for(String clzz:cfgClass) {
			try {
				Class<?> cfg=Class.forName(clzz);
				if(ScanPackConfig.class.isAssignableFrom(cfg)) {
					ScanPackConfig cfConfig=(ScanPackConfig) cfg.newInstance();
					cfConfig.scanPackConfig(defaultScanConfig);
					break;
				}else {
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
		return defaultScanConfig;
	}
	

}
