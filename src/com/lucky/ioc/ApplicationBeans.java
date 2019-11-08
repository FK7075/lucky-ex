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
				if( currKey.startsWith("#")) {//��#��ͷ����ʾRest������
					String ck=currKey.substring(1, currKey.length());
					come.restPut(ck, restVs[i]);
				}else if(currKey.startsWith("*")&&!currKey.endsWith("*")) {//��*��ͷ����ʾ��*������ַ���β����
					String ck=currKey.substring(1, currKey.length());
					if(!restVs[i].endsWith(ck))
						return null;
				}else if(!currKey.startsWith("*")&&currKey.endsWith("*")) {//��*��β����ʾ��*ǰ����ַ���ͷ����
					String ck=currKey.substring(0, currKey.length()-1);
					if(!restVs[i].startsWith(ck))
						return null;
				}else if(currKey.startsWith("*")&&currKey.endsWith("*")) {//��*��ͷ��*��β,��ʾ�����м���ַ�����
					String ck=currKey.substring(1, currKey.length()-1);
					if(!restVs[i].contains(ck))
						return null;
				}else if("?".equals(currKey)) {
					//ֻ��?,��ʾƥ������һ���ǿ��ַ�
				}else {//û�������ַ���ʾ����ȫƥ��
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
	 * ���˵�url�еĲ����rest��������
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
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Controller���:"+getControllerBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Service���:"+getServiceBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Repository���:"+getRepositoryBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Mapper���:"+getMapperBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Component���:"+getComponentBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->Agent���:"+getAgentBeans());
		System.out.println(LuckyUtils.showtime()+"[SCAN-OK]->URL-ControllerMethodӳ���ϵ:"+allMapping());
	}
	

}
