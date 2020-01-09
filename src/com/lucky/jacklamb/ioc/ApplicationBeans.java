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
	 * 得到所有简化的方法映射关系
	 * @return
	 */
	public Set<String> allMapping(){
		return iocContainers.getControllerIOC().getMappingSet();
	}
	
	/**
	 * 根据ID得到一个具体的方法映射
	 * @param id
	 * @return
	 */
	public ControllerAndMethod getHanderMethod(URLAndRequestMethod uRLAndRequestMethod) {
		return iocContainers.getControllerIOC().getControllerAndMethod(uRLAndRequestMethod);
	}
	
	/**
	 * 得到所有的方法映射
	 * @return
	 */
	public ControllerAndMethodMap getHanderMethods() {
		return iocContainers.getControllerIOC().getHanderMap();
	}
	
	/**
	 * 根据ID得到Aspect组件
	 * @param id
	 * @return
	 */
	public Object getAspectBean(String id) {
		return iocContainers.getAspectIOC().getAspectBean(id);
	}
	
	/**
	 * 得到所有Aspect组件
	 * @return
	 */
	public Map<String,PointRun> getAspectBeans(){
		return iocContainers.getAspectIOC().getAspectMap();
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
	
	private Map<String,String> getMapperTypes(){
		return iocContainers.getRepositoryIOC().getMapperTtypeMap();
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
	 * 得到容器中所有的DataSource对象
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
	 * 判断IOC容器中是否含有该ID对应的组件
	 * @param beanId
	 * @return
	 */
	public boolean contains(String beanId) {
		return iocContainers.getControllerIOC().containId(beanId)||iocContainers.getServiceIOC().containId(beanId)
				||iocContainers.getRepositoryIOC().containId(beanId)||iocContainers.getAppIOC().containId(beanId)
				||iocContainers.getAspectIOC().containId(beanId);
	}
	
	/**
	 * 向Component容器中加入一个组件
	 * @param Id
	 * @param component
	 */
	public void addComponentBean(String Id,Object component) {
		iocContainers.addComponent(Id, component);
	}
	
	/**
	 * 判断Component容器中是否存在该Id的组件
	 * @param componentId
	 * @return
	 */
	public boolean containsComponent(String componentId) {
		return iocContainers.getAppIOC().containId(componentId);
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
		else if(iocContainers.getAspectIOC().containId(beanId))
			return iocContainers.getAspectIOC().getAspectBean(beanId);
		else
			throw new NotFindBeanException("在IOC容器中找不到ID为--"+beanId+"--的Bean...");
	}
	
	/**
	 * 打印容器中组件信息
	 */
	public void printBeans() {
		System.err.println();
		System.err.println(LuckyUtils.showtime()+"[SCAN-STRAT]->Lucky组件扫描开始......");
		System.err.println(LuckyUtils.showtime()+"[AOP-ASPECT-SCAN-OK] ASPECT==>"+getAspectBeans());
		System.err.println(LuckyUtils.showtime()+"[AOP-SCAN-END]->Aspect组件注册完成，开始执行IOC扫描以及动态代理......");
		System.err.println(LuckyUtils.showtime()+"[IOC-CONTROLLER-SCAN-OK] CONTROLLER==>"+getControllerBeans());
		System.err.println(LuckyUtils.showtime()+"[IOC-SERVICE-SCAN-OK]  SERVICE==>"+getServiceBeans());
		System.err.println(LuckyUtils.showtime()+"[IOC-REPOSITORY-SCAN-OK]  REPOSITORY==>"+getRepositoryBeans());
		System.err.println(LuckyUtils.showtime()+"[IOC-MAPPER-SCAN-OK]  MAPPER==>"+getMapperTypes());
		System.err.println(LuckyUtils.showtime()+"[IOC-COMPONENT-SCAN-OK] COMPONENT==>"+getComponentBeans());
		System.err.println(LuckyUtils.showtime()+"[IOC-SCAN-END]->IOC组件扫描结束!动态代理结束......");
		System.err.println(LuckyUtils.showtime()+"[URL-MAPPING-PARSING-START] 开始解析URL映射......");
		System.err.println(LuckyUtils.showtime()+"[URL-CONTROLLERMETHOD-SCAN-OK]->URL映射解析完毕，解析结果如下：");
		Set<String> allMapping = allMapping();
			System.err.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		for(String mapping:allMapping) {
			System.err.println(mapping);
			System.err.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		}
		System.err.println(LuckyUtils.showtime()+"[INITIALIZE-SUCCESSFUL]->IOC容器初始化成功启动成功! 动态代理执行完毕! 依赖注入完毕！ URL解析完毕......");
	}
	

}
