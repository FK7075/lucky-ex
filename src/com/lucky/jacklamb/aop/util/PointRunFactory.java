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

/**
 * �����������
 * @author fk-7075
 *
 */
public class PointRunFactory {
	
	public static List<PointRun> createPointRuns(Class<?> AspectClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		List<PointRun> pointRuns=new ArrayList<>();
		Constructor<?> constructor = AspectClass.getConstructor();
		constructor.setAccessible(true);
		Object Aspect=constructor.newInstance();
		Method[] AspectMethods=AspectClass.getDeclaredMethods();
		for(Method method:AspectMethods) {
			if(method.isAnnotationPresent(Before.class)||method.isAnnotationPresent(After.class))
				pointRuns.add(new PointRun(Aspect,method));
		}
		return pointRuns;
	}
	
	/**
	 * ִ�д���
	 * @param AspectMap ���е�Aspect���
	 * @param iocCode ��ǰ������������(Controller,Service,Repository,Component)
	 * @param beanid ��ǰ��������id
	 * @param beanClass ��ǰ���Class
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object Aspect(Map<String,PointRun> AspectMap,String iocCode, String beanid,Class<?> beanClass) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<PointRun> findPointbyBean = findPointbyBean(AspectMap,iocCode,beanid,beanClass);
		if(!findPointbyBean.isEmpty()) {
			return ProxyFactory.createProxyFactory().getProxy(beanClass, findPointbyBean);
		}else if(isCacheable(beanClass)) {
			return ProxyFactory.createProxyFactory().getProxy(beanClass, findPointbyBean);
		}else{
			Constructor<?> constructor = beanClass.getConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		}
	}
	
	/**
	 * �жϵ�ǰ����Ƿ��з�����@Cacheableע���ע
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
	 * �õ�Aspect��������з���bean�����
	 * @param AspectMap
	 * @param iocCode ��ǰ������������(Controller,Service,Repository,Component)
	 * @param beanid ��ǰ��������id
	 * @param beanClass ��ǰ���
	 * @return
	 */
	public static List<PointRun> findPointbyBean(Map<String,PointRun> AspectMap,String iocCode, String beanid,Class<?> beanClass){
		List<PointRun> pointRuns=new  ArrayList<>();
		Collection<PointRun> values = AspectMap.values();
		if(SqlCore.class.isAssignableFrom(beanClass))
			return pointRuns;
		String mateClass;
		for(PointRun pointRun:values) {
			mateClass=pointRun.getMateClass();
			if(mateClass.startsWith("path:")) {
				if(standardPath(mateClass.substring(5),beanClass.getName())) 
					pointRuns.add(pointRun);
			}else if(mateClass.startsWith("id:")) {
				if(standardId(mateClass.substring(3),beanid))
					pointRuns.add(pointRun);
			}else if(mateClass.startsWith("ioc:")) {
				if(standardIocCode(mateClass.substring(4),iocCode))
					pointRuns.add(pointRun);
			}else {
				throw new RuntimeException("�޷�ʶ�����������aspect,��ȷ��aspect������[path:,ioc:,id:]�е�һ��Ϊǰ׺������λ�ã�"+pointRun.method+" ->@Before/@After/@Around(aspect=>err)");
			}
		}
		return pointRuns;
	}
	
	/**
	 * ���鵱ǰ���Ƿ����path:����
	 * @param pointcut ��������
	 * @param beanName ��ǰ���ȫ·��
	 * @return
	 */
	private static boolean standardPath(String pointcut,String beanName) {
		String[] cfgIocCode = pointcut.split(",");
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
	
	/**
	 * ���鵱ǰ���Ƿ����id:����
	 * @param pointcut ��������
	 * @param beanid ��ǰ���beanID
	 * @return
	 */
	private static boolean standardId(String pointcut,String beanid) {
		String[] cfgIocCode = pointcut.split(",");
		for(String cfg:cfgIocCode) {
			if(cfg.equals(beanid))
				return true;
		}
		return false;
		
	}
	
	/**
	 * ���鵱ǰ���Ƿ����ioc:����
	 * @param pointcut ��������
	 * @param iocCode ��ǰ����������
	 * @return
	 */
	private static boolean standardIocCode(String pointcut,String iocCode) {
		String[] cfgIocCode = pointcut.split(",");
		for(String cfg:cfgIocCode) {
			if(cfg.trim().equalsIgnoreCase(iocCode))
				return true;
		}
		return false;
	}

}
