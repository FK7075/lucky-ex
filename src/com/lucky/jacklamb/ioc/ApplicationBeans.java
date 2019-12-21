package com.lucky.jacklamb.ioc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lucky.jacklamb.exception.NotFindBeanException;
import com.lucky.jacklamb.sqlcore.c3p0.DataSource;
import com.lucky.jacklamb.utils.Jacklabm;
import com.lucky.jacklamb.utils.LuckyUtils;

public class ApplicationBeans {
	
	public static IOCContainers iocContainers;
	
	private static ApplicationBeans applicationBean;
	
	static {
		Jacklabm.welcome();
		iocContainers=new IOCContainers();
		iocContainers.init();
	}
	
	public static ApplicationBeans createApplicationBeans() {
		if(applicationBean==null) {
			applicationBean=new ApplicationBeans();
			applicationBean.printBeans();
		}
		return applicationBean;
	}
	
	/**
	 * �õ����м򻯵ķ���ӳ���ϵ
	 * @return
	 */
	public Map<String,String> allMapping(){
		return iocContainers.getControllerIOC().getMapping();
	}
	
	/**
	 * ����ID�õ�һ������ķ���ӳ��
	 * @param id
	 * @return
	 */
	public ControllerAndMethod getHanderMethod(String id) {
		return iocContainers.getControllerIOC().getControllerAndMethod(id);
	}
	
	/**
	 * �õ����еķ���ӳ��
	 * @return
	 */
	public Map<String,ControllerAndMethod> getHanderMethods() {
		return iocContainers.getControllerIOC().getHanderMap();
	}
	
	/**
	 * ����ID�õ�Agent���
	 * @param id
	 * @return
	 */
	public Object getAgentBean(String id) {
		return iocContainers.getAgentIOC().getAgentBean(id);
	}
	
	/**
	 * �õ�����Agent���
	 * @return
	 */
	public Map<String,Object> getAgentBeans(){
		return iocContainers.getAgentIOC().getAgentMap();
	}
	
	/**
	 * ����ID�õ�Service�����е�bean
	 * @param beanId
	 * @return
	 */
	public Object getServiceBean(String beanId) {
		return iocContainers.getServiceIOC().getServiceBean(beanId);
	}
	
	/**
	 * �õ�����Service���
	 * @return
	 */
	public Map<String,Object> getServiceBeans(){
		return iocContainers.getServiceIOC().getServiceMap();
	}
	
	/**
	 * ����ID�õ�һ��Controller���
	 * @param beanId
	 * @return
	 */
	public Object getControllerBean(String beanId) {
		return iocContainers.getControllerIOC().getControllerBean(beanId);
	}
	
	/**
	 * �õ����е�Controller���
	 * @return
	 */
	public Map<String,Object> getControllerBeans(){
		return iocContainers.getControllerIOC().getControllerMap();
	}
	
	/**
	 * �õ����е�Repository���
	 * @return
	 */
	public Map<String,Object> getRepositoryBeans(){
		return iocContainers.getRepositoryIOC().getRepositoryMap();
	}
	
	/**
	 * ����ID�õ�һ��Mapper��Repositroy���
	 * @param mapperId ���ID
	 * @return
	 */
	public Object getMapperBean(String mapperId) {
		return iocContainers.getRepositoryIOC().getMaRepBean(mapperId);
	}
	
	/**
	 * �õ����е�Mapper���
	 * @return
	 */
	public Map<String,Object> getMapperBeans(){
		return iocContainers.getRepositoryIOC().getMapperMap();
	}
	
	/**
	 * ����ID�õ�һ��Component���
	 * @param componentId ���ID
	 * @return
	 */
	public Object getComponentBean(String componentId) {
		return iocContainers.getAppIOC().getComponentBean(componentId);
	}
	
	/**
	 * �õ�����Component���
	 * @return
	 */
	public Map<String,Object> getComponentBeans() {
		return iocContainers.getAppIOC().getAppMap();
	}
	
	/**
	 * �������͵õ�һ��IOC���
	 * @param clzz �������
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
			throw new NotFindBeanException("��IOC�������Ҳ�������Ϊ--"+clzz+"--��Bean...");
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
	 * �õ����������е�DataSource����
	 * @return
	 */
	public List<DataSource> getDataSources() {
		List<DataSource> list=new ArrayList<>();
		for(Entry<String,Object> entry:getComponentBeans().entrySet()) {
			Object obj=entry.getValue();
			Class<?> mapClass=obj.getClass();
			if(DataSource.class.isAssignableFrom(mapClass))
				list.add((DataSource)entry.getValue());
		}
		return list;
	}
	
	/**
	 * �ж�IOC�������Ƿ��и�ID��Ӧ�����
	 * @param beanId
	 * @return
	 */
	public boolean contains(String beanId) {
		return iocContainers.getControllerIOC().containId(beanId)||iocContainers.getServiceIOC().containId(beanId)
				||iocContainers.getRepositoryIOC().containId(beanId)||iocContainers.getAppIOC().containId(beanId)
				||iocContainers.getAgentIOC().containId(beanId);
	}
	
	/**
	 * ��Component�����м���һ�����
	 * @param Id
	 * @param component
	 */
	public void addComponentBean(String Id,Object component) {
		iocContainers.addComponent(Id, component);
	}
	
	/**
	 * �ж�Component�������Ƿ���ڸ�Id�����
	 * @param componentId
	 * @return
	 */
	public boolean containsComponent(String componentId) {
		return iocContainers.getAppIOC().containId(componentId);
	}
	
	/**
	 * ����ID�õ�һ��IOC���
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
			throw new NotFindBeanException("��IOC�������Ҳ���IDΪ--"+beanId+"--��Bean...");
	}
	
	/**
	 * ��ӡ�����������Ϣ
	 */
	public void printBeans() {
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Controller���:"+getControllerBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Service���:"+getServiceBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Repository���:"+getRepositoryBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Mapper���:"+getMapperBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Component���:"+getComponentBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Agent���:"+getAgentBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->URL-ControllerMethodӳ���ϵ:"+allMapping());
	}
	

}
