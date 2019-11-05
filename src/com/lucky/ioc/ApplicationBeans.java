package com.lucky.ioc;

import java.util.Map;
import java.util.Map.Entry;

import com.lucky.exception.NotFindBeanException;
import com.lucky.utils.LuckyUtils;

public class ApplicationBeans {
	
	public static IOCContainers iocContainers;
	
	private static ApplicationBeans applicationBean;
	
	static {
		iocContainers=new IOCContainers();
		iocContainers.init();
	}
	
	private ApplicationBeans() {
		LuckyUtils.welcome();
	}
	
	public static ApplicationBeans createApplicationBeans() {
		if(applicationBean==null) {
			applicationBean=new ApplicationBeans();
		}
		return applicationBean;
	}
	
	/**
	 * 根据ID得到Agent组件
	 * @param id
	 * @return
	 */
	public Object getAgentBean(String id) {
		return iocContainers.getAgentIOC().getAgentBean(id);
	}
	
	/**
	 * 得到所有Agent组件
	 * @return
	 */
	public Map<String,Object> getAgentBeans(){
		return iocContainers.getAgentIOC().getAgentMap();
	}
	
	/**
	 * 根据ID得到Service容器中的bean
	 * @param beanId
	 * @return
	 */
	public Object getServiceBean(String beanId) {
		return iocContainers.getServiceIOC().getServiceBean(beanId);
	}
	
	/**
	 * 得到所有Service组件
	 * @return
	 */
	public Map<String,Object> getServiceBeans(){
		return iocContainers.getServiceIOC().getServiceMap();
	}
	
	/**
	 * 根据ID得到一个Controller组件
	 * @param beanId
	 * @return
	 */
	public Object getControllerBean(String beanId) {
		return iocContainers.getControllerIOC().getControllerBean(beanId);
	}
	
	/**
	 * 得到所有的Controller组件
	 * @return
	 */
	public Map<String,Object> getControllerBeans(){
		return iocContainers.getControllerIOC().getControllerMap();
	}
	
	/**
	 * 得到所有的Repository组件
	 * @return
	 */
	public Map<String,Object> getRepositoryBeans(){
		return iocContainers.getRepositoryIOC().getRepositoryMap();
	}
	
	/**
	 * 根据ID得到一个Mapper或Repositroy组件
	 * @param mapperId 组件ID
	 * @return
	 */
	public Object getMapperBean(String mapperId) {
		return iocContainers.getRepositoryIOC().getMaRepBean(mapperId);
	}
	
	/**
	 * 得到所有的Mapper组件
	 * @return
	 */
	public Map<String,Object> getMapperBeans(){
		return iocContainers.getRepositoryIOC().getMapperMap();
	}
	
	/**
	 * 根据ID得到一个Component组件
	 * @param componentId 组件ID
	 * @return
	 */
	public Object getComponentBean(String componentId) {
		return iocContainers.getAppIOC().getComponentBean(componentId);
	}
	
	/**
	 * 得到所有Component组件
	 * @return
	 */
	public Map<String,Object> getComponentBeans() {
		return iocContainers.getAppIOC().getAppMap();
	}
	
	/**
	 * 根据类型得到一个IOC组件
	 * @param clzz 组件类型
	 * @return
	 */
	public Object getBean(Class<?> clzz) {
		Object controllerObj=getBeanByClass(iocContainers.getControllerIOC().getControllerMap(),clzz);
		Object serviceObj=getBeanByClass(iocContainers.getServiceIOC().getServiceMap(),clzz);
		Object repositoryObj=getBeanByClass(iocContainers.getRepositoryIOC().getRepositoryMap(),clzz);
		Object mapperObj=getBeanByClass(iocContainers.getRepositoryIOC().getMapperMap(),clzz);
		Object componentObj=getBeanByClass(iocContainers.getAppIOC().getAppMap(),clzz);
		if(controllerObj!=null)
			return controllerObj;
		else if(serviceObj!=null)
			return serviceObj;
		else if(repositoryObj!=null)
			return repositoryObj;
		else if(mapperObj!=null)
			return mapperObj;
		else if(componentObj!=null)
			return componentObj;
		else
			throw new NotFindBeanException("在IOC容器中找不到类型为--"+clzz+"--的Bean...");
	}
	
	private Object getBeanByClass(Map<String,Object> map,Class<?> clzz) {
		for(Entry<String,Object> entry:map.entrySet()) {
			Object obj=entry.getValue();
			Class<?> mapClass=obj.getClass();
			if(clzz.isAssignableFrom(mapClass))
				return obj;
		}
		return null;
	}
	
	/**
	 * 判断IOC容器中是否含有该ID对应的组件
	 * @param beanId
	 * @return
	 */
	public boolean contains(String beanId) {
		return iocContainers.getControllerIOC().containId(beanId)||iocContainers.getServiceIOC().containId(beanId)
				||iocContainers.getRepositoryIOC().containId(beanId)||iocContainers.getAppIOC().containId(beanId)
				||iocContainers.getAgentIOC().containId(beanId);
	}
	
	/**
	 * 根据ID得到一个IOC组件
	 * @param beanId
	 * @return
	 */
	public Object getBean(String beanId) {
		if(iocContainers.getControllerIOC().containId(beanId))
			return iocContainers.getControllerIOC().getControllerBean(beanId);
		else if(iocContainers.getServiceIOC().containId(beanId))
			return iocContainers.getServiceIOC().getServiceBean(beanId);
		else if(iocContainers.getRepositoryIOC().containId(beanId))
			return iocContainers.getRepositoryIOC().getMaRepBean(beanId);
		else if(iocContainers.getAppIOC().containId(beanId))
			return iocContainers.getAppIOC().getComponentBean(beanId);
		else if(iocContainers.getAgentIOC().containId(beanId))
			return iocContainers.getAgentIOC().getAgentBean(beanId);
		else
			throw new NotFindBeanException("在IOC容器中找不到ID为--"+beanId+"--的Bean...");
	}
	

}
