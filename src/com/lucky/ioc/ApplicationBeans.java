package com.lucky.ioc;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lucky.annotation.Controller;
import com.lucky.annotation.RequestMapping;
import com.lucky.exception.NotFindBeanException;
import com.lucky.ioc.config.WebConfig;
import com.lucky.servlet.Model;
import com.lucky.utils.LuckyUtils;

public class ApplicationBeans {
	
	public static IOCContainers iocContainers;
	
	private static ApplicationBeans applicationBean;
	
	static {
		LuckyUtils.welcome();
		iocContainers=new IOCContainers();
		iocContainers.init();
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
	public Map<String,String> allMapping(){
		return iocContainers.getControllerIOC().getMapping();
	}
	
	/**
	 * 根据ID得到一个具体的方法映射
	 * @param id
	 * @return
	 */
	public ControllerAndMethod getHanderMethod(String id) {
		return iocContainers.getControllerIOC().getControllerAndMethod(id);
	}
	
	/**
	 * 得到所有的方法映射
	 * @return
	 */
	public Map<String,ControllerAndMethod> getHanderMethods() {
		return iocContainers.getControllerIOC().getHanderMap();
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
		else if(iocContainers.getAgentIOC().containId(beanId))
			return iocContainers.getAgentIOC().getAgentBean(beanId);
		else
			throw new NotFindBeanException("在IOC容器中找不到ID为--"+beanId+"--的Bean...");
	}
	
	public ControllerAndMethod getCurrControllerAndMethod(String resturl) {
		String mapping = getKey(resturl);
		if(mapping==null)
			return null;
		ControllerAndMethod come = getHanderMethod(mapping);
		come.setUrl(mapping);
		Method method = come.getMethod();
		RequestMapping rm = method.getAnnotation(RequestMapping.class);
		String rmvalue = rm.value();
		if (rmvalue.contains("//")) {
			String restParamStr = resturl.replaceAll(mapping + "/", "");
			String[] restVs = restParamStr.split("/");
			int start = rmvalue.indexOf("//");
			rmvalue = rmvalue.substring(start + 2, rmvalue.length());
			String[] restKs = rmvalue.split("/");
			if(restVs.length!=restKs.length)
				return null;
			for (int i = 0; i < restKs.length; i++) {
				String currKey=restKs[i];
				if( currKey.startsWith("#")) {//以#开头，表示Rest参数名
					String ck=currKey.substring(1, currKey.length());
					come.restPut(ck, restVs[i]);
				}else if(currKey.startsWith("*")&&!currKey.endsWith("*")) {//以*开头，表示以*后面的字符结尾即可
					String ck=currKey.substring(1, currKey.length());
					if(!restVs[i].endsWith(ck))
						return null;
				}else if(!currKey.startsWith("*")&&currKey.endsWith("*")) {//以*结尾，表示以*前面的字符开头即可
					String ck=currKey.substring(0, currKey.length()-1);
					if(!restVs[i].startsWith(ck))
						return null;
				}else if(currKey.startsWith("*")&&currKey.endsWith("*")) {//以*开头以*结尾,表示存在中间的字符即可
					String ck=currKey.substring(1, currKey.length()-1);
					if(!restVs[i].contains(ck))
						return null;
				}else if("?".equals(currKey)) {
					//只有?,表示匹配任意一个非空字符
				}else {//没有特殊字符表示参数全匹配
					if(!restVs[i].equals(currKey))
						return null;
				}
			}
		}
		Controller cont=come.getController().getClass().getAnnotation(Controller.class);
		List<String> globalprefixAndSuffix=WebConfig.getWebConfig().getHanderPrefixAndSuffix();
 		come.setPrefix(globalprefixAndSuffix.get(0));
		come.setSuffix(globalprefixAndSuffix.get(1));
		if(!"".equals(cont.prefix()))
			come.setPrefix(cont.prefix());
		if(!"".equals(cont.suffix()))
			come.setSuffix(cont.suffix());
		return come;
	}
	
	public void autowReqAdnResp(Object object,Model model) {
		try {
			iocContainers.autowReqAdnResp(object, model);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 过滤掉url中的参数项（rest风格参数）
	 * @param url
	 * @return
	 */
	private String getKey(String url) {
		if (iocContainers.getControllerIOC().containHander(url))
			return url;
		if (url.lastIndexOf('/') == 0)
			return null;
		int end = url.lastIndexOf('/');
		url = url.substring(0, end);
		return getKey(url);
	}
	public void printBeans() {
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Controller组件:"+getControllerBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Service组件:"+getServiceBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Repository组件:"+getRepositoryBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Mapper组件:"+getMapperBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Component组件:"+getComponentBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Agent组件:"+getAgentBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->URL-ControllerMethod映射关系:"+allMapping());
	}
	

}
