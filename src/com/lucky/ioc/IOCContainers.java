package com.lucky.ioc;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import com.lucky.annotation.Autowired;
import com.lucky.exception.InjectionPropertiesException;

/**
 * ɨ���������ð��������е�IOC��������ص���Ӧ��IOC������
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
		inversionOfControl();
	}
	
	
	/**
	 * ���Ʒ�ת
	 */
	public void inversionOfControl() {
		initComponentIOC();
		initControllerIOC();
		initServiceIOC();
		initRepositoryIOC();
	}
	
	/**
	 * ����ע��
	 */
	public void dependencyInjection() {
		try {
			injection(controllerIOC.getControllerMap());
			injection(serviceIOC.getServiceMap());
			injection(repositoryIOC.getRepositoryMap());
			injection(appIOC.getAppMap());
		} catch (IllegalArgumentException e) {
			throw new InjectionPropertiesException("����ע���쳣��ע���������ԭ���Բ�ƥ��....");
		} catch (IllegalAccessException e) {
			throw new InjectionPropertiesException("����ע���쳣��û��Ȩ�޷��ʸ�����....");
		}
		
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
	 * �õ��йذ�ɨ���������Ϣ
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
	
	/**
	 * ���������ע��
	 * @param componentMap
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void injection(Map<String,Object> componentMap) throws IllegalArgumentException, IllegalAccessException {
		ApplicationBeans beans=ApplicationBeans.createApplicationBeans();
		for(Entry<String,Object> entry:componentMap.entrySet()) {
			Object component=entry.getValue();
			Class<?> componentClass=component.getClass();
			Field[] fields=componentClass.getDeclaredFields();
			for(Field field:fields) {
				if(field.isAnnotationPresent(Autowired.class)) {
					Autowired auto=field.getAnnotation(Autowired.class);
					field.setAccessible(true);
					if("".equals(auto.value())) {
						field.set(component, beans.getBean(field.getType()));
					}else {
						field.set(component, beans.getBean(auto.value()));
					}
				}
			}
		}
	}
}
