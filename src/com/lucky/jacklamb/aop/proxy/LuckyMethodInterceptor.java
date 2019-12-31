package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.lucky.jacklamb.annotation.aop.Cacheable;
import com.lucky.jacklamb.aop.defaultexpand.CacheExpandPoint;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class LuckyMethodInterceptor implements MethodInterceptor {
	
	private List<PerformMethod> performMethods;//����ĳһ�����������ǿ��ִ�з���
	
	private List<PointRun> pointRuns;//����ĳһ�����������ǿ��ִ�нڵ�
	
	/**
	 * �ص��������������õ�һ����ʵ����ĵ�����ִ�з���(MethodRun)�ͻ���ִ�нڵ㼯��(PointRun)��
	 * ����ʵ�����Ϊ��ʵ�����ÿһ����Ҫ����ǿ�ķ�������һ���ض��Ļص�����
	 * @param performMethods ����ִ����
	 * @param pointRuns ����ִ�нڵ㼯��(�ɱ����ʽ����)
	 */
	public LuckyMethodInterceptor(List<PerformMethod> performMethods,PointRun...pointRuns) {
		this.performMethods = performMethods;
		this.pointRuns=new ArrayList<>();
		Stream.of(pointRuns).forEach(this.pointRuns::add);
	}
	
	/**
	 * �ص��������������õ�һ����ʵ����ĵ�����ִ�з���(MethodRun)�ͻ���ִ����(PointRun)��
	 * ����ʵ�����Ϊ��ʵ�����ÿһ����Ҫ����ǿ�ķ�������һ���ض��Ļص�����
	 * @param performMethods ����ִ����
	 * @param pointRuns ����ִ�нڵ㼯��(���ϲ���ʽ����)
	 */
	public LuckyMethodInterceptor(List<PerformMethod> performMethods,List<PointRun> pointRuns) {
		this.performMethods = performMethods;
		this.pointRuns=new ArrayList<>();
		this.pointRuns.addAll(pointRuns);
	}

	@Override
	public Object intercept(Object target, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
		List<Point> points=new ArrayList<>();
		if(method.isAnnotationPresent(Cacheable.class)) {
			Point cacheExpandPoint = new CacheExpandPoint();
			cacheExpandPoint.method=method;
			cacheExpandPoint.params=params;
			points.add(cacheExpandPoint);
		}
		//�õ����е�ǰ����ǿִ�з���
		List<MethodRun> beforeMethodRuns = PerformMethod.getBeforeMethodRuns(performMethods, method);
		
		//�õ����еĺ�����ǿִ�з���
		List<MethodRun> afterMethodRuns = PerformMethod.getAfterMethodRuns(performMethods, method);
		
		//�����е�ǰ����ǿ�ͺ�����ǿת��Ϊһ��������ǿ�ڵ�
		Point point=new Point() {

			@Override
			public Object proceed(Chain chain) {
				Object rest;
				beforeMethodRuns.stream().forEach(a->a.methodRun());
				rest=chain.proceed();
				afterMethodRuns.stream().forEach(a->a.methodRun());
				return rest;
			}
			
		};
		
		//�õ������Զ�����Լ�Lucky�Դ��Ļ�����ǿ�ڵ�
		pointRuns.stream().filter(a->a.getMethods()!=null&&a.getMethods().contains(method)).forEach((a)->{Point p=a.getPoint();p.method=method;p.params=params;points.add(p);});
		
		point.method=method;
		point.params=params;
		points.add(point);
		
		//�����Ļ�����ǿ�ڵ����һ��ִ����
		Chain chain=new Chain(points,target,params,methodProxy);
		Object resule;
		
		//ִ����ǿ����
		resule= chain.proceed();
		chain.setIndex(-1);
		return resule;
	}


}
