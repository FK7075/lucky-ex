package com.lucky.jacklamb.aop.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.annotation.aop.After;
import com.lucky.jacklamb.annotation.aop.Before;
import com.lucky.jacklamb.annotation.aop.Cacheable;
import com.lucky.jacklamb.aop.proxy.PointRun;
import com.lucky.jacklamb.aop.proxy.ProxyFactory;
import com.lucky.jacklamb.sqlcore.abstractionlayer.abstcore.SqlCore;
import com.lucky.jacklamb.sqlcore.c3p0.DataSource;

public class PointRunFactory {
	
	public static List<PointRun> createPointRuns(Class<?> agentClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		List<PointRun> pointRuns=new ArrayList<>();
		Constructor<?> constructor = agentClass.getConstructor();
		constructor.setAccessible(true);
		Object agent=constructor.newInstance();
		Method[] agentMethods=agentClass.getDeclaredMethods();
		for(Method method:agentMethods) {
			if(method.isAnnotationPresent(Before.class)||method.isAnnotationPresent(After.class))
				pointRuns.add(new PointRun(agent,method));
		}
		return pointRuns;
	}
	
	/**
	 * 执行代理
	 * @param agentMap 所有的Agent组件
	 * @param iocCode 当前组件的组件代码(Controller,Service,Repository,Component)
	 * @param beanid 当前组件的组件id
	 * @param bean 当前组件
	 */
	public static Object agent(Map<String,PointRun> agentMap,String iocCode, String beanid,Object bean) {
		List<PointRun> findPointbyBean = findPointbyBean(agentMap,iocCode,beanid,bean);
		if(!findPointbyBean.isEmpty()) {
			return ProxyFactory.createProxyFactory().getProxy(bean.getClass(), findPointbyBean);
		}else if(isCacheable(bean.getClass())) {
			return ProxyFactory.createProxyFactory().getProxy(bean.getClass(), findPointbyBean);
		}else{
			return bean;
		}
	}
	
	/**
	 * 判断当前组件是否有方法被@Cacheable注解标注
	 * @param beanClass
	 * @return
	 */
	private static boolean isCacheable(Class<?> beanClass) {
		Method[] declaredMethods = beanClass.getDeclaredMethods();
		for(Method method:declaredMethods) {
			if(method.isAnnotationPresent(Cacheable.class))
				return true;
		}
		return false;
	}

	/**
	 * 得到Agent组件中所有符合bean的组件
	 * @param agentMap
	 * @param iocCode 当前组件的组件代码(Controller,Service,Repository,Component)
	 * @param beanid 当前组件的组件id
	 * @param bean 当前组件
	 * @return
	 */
	public static List<PointRun> findPointbyBean(Map<String,PointRun> agentMap,String iocCode, String beanid,Object bean){
		List<PointRun> pointRuns=new  ArrayList<>();
		Collection<PointRun> values = agentMap.values();
		if(SqlCore.class.isAssignableFrom(bean.getClass())||DataSource.class.isAssignableFrom(bean.getClass()))
			return pointRuns;
		String mateClass;
		for(PointRun pointRun:values) {
			mateClass=pointRun.getMateClass();
			if(mateClass.startsWith("path:")) {
				if(standardPath(mateClass.substring(5),bean.getClass().getName())) 
					pointRuns.add(pointRun);
			}else if(mateClass.startsWith("id:")) {
				if(standardId(mateClass.substring(3),beanid))
					pointRuns.add(pointRun);
			}else if(mateClass.startsWith("ioc:")) {
				if(standardIocCode(mateClass.substring(4),iocCode))
					pointRuns.add(pointRun);
			}else {
				throw new RuntimeException("无法识别的mateClass,正确的mateClass必须以[path:,ioc:,id:]中的一个为前缀！错误位置："+pointRun.method+" ->@Expand(mateClass=>err)");
			}
		}
		return pointRuns;
	}
	
	private static boolean standardPath(String mateMethod,String beanName) {
		String[] cfgIocCode = mateMethod.split(",");
		for(String cfg:cfgIocCode) {
			if(cfg.endsWith(".*")) {
				if(beanName.contains(cfg.substring(0, cfg.length()-2)))
					return true;
			}else {
				if(cfg.equals(beanName))
					return true;
			}
		}
		return false;
		
	}
	
	private static boolean standardId(String mateMethod,String beanid) {
		String[] cfgIocCode = mateMethod.split(",");
		for(String cfg:cfgIocCode) {
			if(cfg.equals(beanid))
				return true;
		}
		return false;
		
	}
	
	private static boolean standardIocCode(String mateMethod,String iocCode) {
		String[] cfgIocCode = mateMethod.split(",");
		for(String cfg:cfgIocCode) {
			if(cfg.trim().equalsIgnoreCase(iocCode))
				return true;
		}
		return false;
	}

}
