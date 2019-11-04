package com.lucky.ioc;

/**
 * 扫描所有配置包，将所有的IOC组件都加载到相应的IOC容器中
 * @author DELL
 *
 */
public class IOCContainers {
	
	private AgentIOC agentIOC;
	
	private RepositoryIOC repositoryIOC;
	
	private ServiceIOC serviceIOC;
	
	private ComponentIOC appIOC;
	
	private ControllerIOC controllerIOC;
	
	private ScanConfig scanConfig;
	
	
	
	
	public IOCContainers() {
		scanConfigToComponentIOC();
		initControllerIOC();
		initServiceIOC();
		initRepositoryIOC();
		initComponentIOC();
	}

	public AgentIOC getAgentIOC() {
		return agentIOC;
	}

	public void setAgentIOC(AgentIOC agentIOC) {
		this.agentIOC = agentIOC;
	}

	public RepositoryIOC getRepositoryIOC() {
		return repositoryIOC;
	}

	public void setRepositoryIOC(RepositoryIOC repositoryIOC) {
		this.repositoryIOC = repositoryIOC;
	}

	public ServiceIOC getServiceIOC() {
		return serviceIOC;
	}

	public void setServiceIOC(ServiceIOC serviceIOC) {
		this.serviceIOC = serviceIOC;
	}

	public ComponentIOC getAppIOC() {
		return appIOC;
	}

	public void setAppIOC(ComponentIOC appIOC) {
		this.appIOC = appIOC;
	}

	public ControllerIOC getControllerIOC() {
		return controllerIOC;
	}

	public void setControllerIOC(ControllerIOC controllerIOC) {
		this.controllerIOC = controllerIOC;
	}

	/**
	 * 得到有关包扫描的配置信息
	 */
	public void scanConfigToComponentIOC() {
		scanConfig=ScanConfig.getScanConfig();
	}
	
	public void initComponentIOC() {
		appIOC=new ComponentIOC();
		appIOC.initComponentIOC(PackageScan.getPackageScan().loadComponent(scanConfig.getControllerPackSuffix()));
	}
	
	public void initControllerIOC() {
		controllerIOC=new ControllerIOC();
		controllerIOC.initControllerIOC(PackageScan.getPackageScan().loadComponent(scanConfig.getControllerPackSuffix()));
	}
	
	public void initServiceIOC() {
		serviceIOC=new ServiceIOC();
		serviceIOC.initServiceIOC(PackageScan.getPackageScan().loadComponent(scanConfig.getServicePackSuffix()));
	}
	public void initRepositoryIOC() {
		repositoryIOC=new RepositoryIOC();
		repositoryIOC.initRepositoryIOC(PackageScan.getPackageScan().loadComponent(scanConfig.getRepositoryPackSuffix()));
	}
}
