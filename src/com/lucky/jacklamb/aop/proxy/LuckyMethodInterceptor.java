package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.lucky.jacklamb.annotation.aop.Cacheable;
import com.lucky.jacklamb.aop.expandpoint.CacheExpandPoint;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class LuckyMethodInterceptor implements MethodInterceptor {
	
	
	private List<PointRun> pointRuns;//����ĳһ�����������ǿ��ִ�нڵ�
	
	/**
	 * �ص��������������õ�һ����ʵ����ĵ�����ִ�з���(MethodRun)�ͻ���ִ�нڵ㼯��(PointRun)��
	 * ����ʵ�����Ϊ��ʵ�����ÿһ����Ҫ����ǿ�ķ�������һ���ض��Ļص�����
	 * @param pointRuns ����ִ�нڵ㼯��(�ɱ����ʽ����)
	 */
	public LuckyMethodInterceptor(PointRun...pointRuns) {
		this.pointRuns=new ArrayList<>();
		Stream.of(pointRuns).forEach(this.pointRuns::add);
	}
	
	/**
	 * �ص��������������õ�һ����ʵ����ĵ�����ִ�з���(MethodRun)�ͻ���ִ����(PointRun)��
	 * ����ʵ�����Ϊ��ʵ�����ÿһ����Ҫ����ǿ�ķ�������һ���ض��Ļص�����
	 * @param pointRuns ����ִ�нڵ㼯��(���ϲ���ʽ����)
	 */
	public LuckyMethodInterceptor(List<PointRun> pointRuns) {
		this.pointRuns=new ArrayList<>();
		this.pointRuns.addAll(pointRuns);
	}

	@Override
	public Object intercept(Object target, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
		List<Point> points=new ArrayList<>();
		
		//��@Cacheableע���ע�ķ�������ִ�л������
		if(method.isAnnotationPresent(Cacheable.class)) {
			Point cacheExpandPoint = new CacheExpandPoint();
			cacheExpandPoint.method=method;
			cacheExpandPoint.params=params;
			points.add(cacheExpandPoint);
		}
		//�õ������Զ���ĵĻ�����ǿ�ڵ�
		pointRuns.stream().filter(a->a.standard(method)).forEach((a)->{Point p=a.getPoint();p.method=method;p.params=params;points.add(p);});
		
		//�����Ļ�����ǿ�ڵ����һ��ִ����
		Chain chain=new Chain(points,target,params,methodProxy);
		Object resule;
		
		//ִ����ǿ����
		resule= chain.proceed();
		chain.setIndex(-1);
		return resule;
	}


}
