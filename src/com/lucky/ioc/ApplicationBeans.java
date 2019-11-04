package com.lucky.ioc;

import com.lucky.exception.NotFindBean;

public class ApplicationBeans {
	
	private IOCVessel iocVessel;
	
	/**
	 * 根据ID得到service容器中的bean
	 * @param beanId
	 * @return
	 */
	public Object getServiceBean(String beanId) {
		if(!iocVessel.getServiceIOC().getServiceIDS().contains(beanId))
			throw new NotFindBean("service容器(ioc)中找不到ID为--"+beanId+"--的bean......");
		return iocVessel.getServiceIOC().getServiceMap().get(beanId);
	}
	
	public Object getControllerBean(String beanId) {
		if(!iocVessel.getControllerIOC().getControllerIDS().contains(beanId))
			throw new NotFindBean("controller容器(ioc)中找不到ID为--"+beanId+"--的bean......");
		return iocVessel.getControllerIOC().getControllerMap().get(beanId);
	}
	
	public Object getRepositoryBean(String beanId) {
		if(!iocVessel.getRepositoryIOC().getRepositoryIDS().contains(beanId))
			throw new NotFindBean("repository容器(ioc)中找不到ID为--"+beanId+"--的bean......");
		return iocVessel.getRepositoryIOC().getRepositoryMap().get(beanId);
	}
	
	public Object getMapper(String mapperId) {
		if(!iocVessel.getRepositoryIOC().getMapperIDS().contains(mapperId))
			throw new NotFindBean("mapper容器(ioc)中找不到ID为--"+mapperId+"--的bean......");
		return iocVessel.getRepositoryIOC().getMapperMap().get(mapperId);
	}
	
	

}
