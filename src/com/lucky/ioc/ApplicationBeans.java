package com.lucky.ioc;

import com.lucky.exception.NotFindBean;

public class ApplicationBeans {
	
	private IOCVessel iocVessel;
	
	/**
	 * ����ID�õ�service�����е�bean
	 * @param beanId
	 * @return
	 */
	public Object getServiceBean(String beanId) {
		if(!iocVessel.getServiceIOC().getServiceIDS().contains(beanId))
			throw new NotFindBean("service����(ioc)���Ҳ���IDΪ--"+beanId+"--��bean......");
		return iocVessel.getServiceIOC().getServiceMap().get(beanId);
	}
	
	public Object getControllerBean(String beanId) {
		if(!iocVessel.getControllerIOC().getControllerIDS().contains(beanId))
			throw new NotFindBean("controller����(ioc)���Ҳ���IDΪ--"+beanId+"--��bean......");
		return iocVessel.getControllerIOC().getControllerMap().get(beanId);
	}
	
	public Object getRepositoryBean(String beanId) {
		if(!iocVessel.getRepositoryIOC().getRepositoryIDS().contains(beanId))
			throw new NotFindBean("repository����(ioc)���Ҳ���IDΪ--"+beanId+"--��bean......");
		return iocVessel.getRepositoryIOC().getRepositoryMap().get(beanId);
	}
	
	public Object getMapper(String mapperId) {
		if(!iocVessel.getRepositoryIOC().getMapperIDS().contains(mapperId))
			throw new NotFindBean("mapper����(ioc)���Ҳ���IDΪ--"+mapperId+"--��bean......");
		return iocVessel.getRepositoryIOC().getMapperMap().get(mapperId);
	}
	
	

}
