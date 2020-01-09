package com.lucky.jacklamb.ioc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.lucky.jacklamb.aop.proxy.PointRun;
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
		applicationBean.printBeans();
	}
	
	public static ApplicationBeans createApplicationBeans() {
		if(applicationBean==null) {
			applicationBean=new ApplicationBeans();
		}
		return applicationBean;
	}
	
	/**
	 * �õ����м򻯵ķ���ӳ���ϵ
	 * @return
	 */
	public Set<String> allMapping(){
		return iocContainers.getControllerIOC().getMappingSet();
	}
	
	/**
	 * ����ID�õ�һ������ķ���ӳ��
	 * @param id
	 * @return
	 */
	public ControllerAndMethod getHanderMethod(URLAndRequestMethod uRLAndRequestMethod) {
		return iocContainers.getControllerIOC().getControllerAndMethod(uRLAndRequestMethod);
	}
	
	/**
	 * �õ����еķ���ӳ��
	 * @return
	 */
	public ControllerAndMethodMap getHanderMethods() {
		return iocContainers.getControllerIOC().getHanderMap();
	}
	
	/**
	 * ����ID�õ�Aspect���
	 * @param id
	 * @return
	 */
	public Object getAspectBean(String id) {
		return iocContainers.getAspectIOC().getAspectBean(id);
	}
	
	/**
	 * �õ�����Aspect���
	 * @return
	 */
	public Map<String,PointRun> getAspectBeans(){
		return iocContainers.getAspectIOC().getAspectMap();
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
	
	private Map<String,String> getMapperTypes(){
		return iocContainers.getRepositoryIOC().getMapperTtypeMap();
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
				||iocContainers.getAspectIOC().containId(beanId);
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
		else if(iocContainers.getAspectIOC().containId(beanId))
			return iocContainers.getAspectIOC().getAspectBean(beanId);
		else
			throw new NotFindBeanException("��IOC�������Ҳ���IDΪ--"+beanId+"--��Bean...");
	}
	
	/**
	 * ��ӡ�����������Ϣ
	 */
	public void printBeans() {
		System.err.println();
		System.err.println(LuckyUtils.showtime()+"[SCAN-STRAT]->Lucky���ɨ�迪ʼ......");
		System.err.println(LuckyUtils.showtime()+"[AOP-ASPECT-SCAN-OK] ASPECT==>"+getAspectBeans());
		System.err.println(LuckyUtils.showtime()+"[AOP-SCAN-END]->Aspect���ע����ɣ���ʼִ��IOCɨ���Լ���̬����......");
		System.err.println(LuckyUtils.showtime()+"[IOC-CONTROLLER-SCAN-OK] CONTROLLER==>"+getControllerBeans());
		System.err.println(LuckyUtils.showtime()+"[IOC-SERVICE-SCAN-OK]  SERVICE==>"+getServiceBeans());
		System.err.println(LuckyUtils.showtime()+"[IOC-REPOSITORY-SCAN-OK]  REPOSITORY==>"+getRepositoryBeans());
		System.err.println(LuckyUtils.showtime()+"[IOC-MAPPER-SCAN-OK]  MAPPER==>"+getMapperTypes());
		System.err.println(LuckyUtils.showtime()+"[IOC-COMPONENT-SCAN-OK] COMPONENT==>"+getComponentBeans());
		System.err.println(LuckyUtils.showtime()+"[IOC-SCAN-END]->IOC���ɨ�����!��̬�������......");
		System.err.println(LuckyUtils.showtime()+"[URL-MAPPING-PARSING-START] ��ʼ����URLӳ��......");
		System.err.println(LuckyUtils.showtime()+"[URL-CONTROLLERMETHOD-SCAN-OK]->URLӳ�������ϣ�����������£�");
		Set<String> allMapping = allMapping();
			System.err.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		for(String mapping:allMapping) {
			System.err.println(mapping);
			System.err.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		}
		System.err.println(LuckyUtils.showtime()+"[INITIALIZE-SUCCESSFUL]->IOC������ʼ���ɹ������ɹ�! ��̬����ִ�����! ����ע����ϣ� URL�������......");
	}
	

}
