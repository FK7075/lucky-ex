package com.lucky.ioc;

import com.lucky.exception.NotFindBeanException;

public class ApplicationBeans {
	
	private IOCContainers iocContainers;
	
	private static ApplicationBeans applicationBean;
	
	private ApplicationBeans() {
		iocContainers=new IOCContainers();
	}
	
	public static ApplicationBeans createApplicationBeans() {
		if(applicationBean==null)
			applicationBean=new ApplicationBeans();
		return applicationBean;
	}
	
	/**
	 * ����ID�õ�service�����е�bean
	 * @param beanId
	 * @return
	 */
	public Object getServiceBean(String beanId) {
		if(!iocContainers.getServiceIOC().getServiceIDS().contains(beanId))
			throw new NotFindBeanException("service����(ioc)���Ҳ���IDΪ--"+beanId+"--��bean......");
		return iocContainers.getServiceIOC().getServiceMap().get(beanId);
	}
	
	public Object getControllerBean(String beanId) {
		if(!iocContainers.getControllerIOC().getControllerIDS().contains(beanId))
			throw new NotFindBeanException("controller����(ioc)���Ҳ���IDΪ--"+beanId+"--��bean......");
		return iocContainers.getControllerIOC().getControllerMap().get(beanId);
	}
	
	public Object getRepositoryBean(String beanId) {
		if(!iocContainers.getRepositoryIOC().getRepositoryIDS().contains(beanId))
			throw new NotFindBeanException("repository����(ioc)���Ҳ���IDΪ--"+beanId+"--��bean......");
		return iocContainers.getRepositoryIOC().getRepositoryMap().get(beanId);
	}
	
	public Object getMapper(String mapperId) {
		if(!iocContainers.getRepositoryIOC().getMapperIDS().contains(mapperId))
			throw new NotFindBeanException("mapper����(ioc)���Ҳ���IDΪ--"+mapperId+"--��bean......");
		return iocContainers.getRepositoryIOC().getMapperMap().get(mapperId);
	}
	
	

}
