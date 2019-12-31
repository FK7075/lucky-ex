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
	
	private List<PerformMethod> performMethods;//关于某一个类的所有增强的执行方法
	
	private List<PointRun> pointRuns;//关于某一个类的所有增强的执行节点
	
	/**
	 * 回调函数构造器，得到一个真实对象的的所有执行方法(MethodRun)和环绕执行节点集合(PointRun)，
	 * 根据实际情况为真实对象的每一个需要被增强的方法产生一个特定的回调策略
	 * @param performMethods 方法执行链
	 * @param pointRuns 环绕执行节点集合(可变参形式传入)
	 */
	public LuckyMethodInterceptor(List<PerformMethod> performMethods,PointRun...pointRuns) {
		this.performMethods = performMethods;
		this.pointRuns=new ArrayList<>();
		Stream.of(pointRuns).forEach(this.pointRuns::add);
	}
	
	/**
	 * 回调函数构造器，得到一个真实对象的的所有执行方法(MethodRun)和环绕执行链(PointRun)，
	 * 根据实际情况为真实对象的每一个需要被增强的方法产生一个特定的回调策略
	 * @param performMethods 方法执行链
	 * @param pointRuns 环绕执行节点集合(集合参形式传入)
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
		//得到所有的前置增强执行方法
		List<MethodRun> beforeMethodRuns = PerformMethod.getBeforeMethodRuns(performMethods, method);
		
		//得到所有的后置增强执行方法
		List<MethodRun> afterMethodRuns = PerformMethod.getAfterMethodRuns(performMethods, method);
		
		//将所有的前置增强和后置增强转化为一个环绕增强节点
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
		
		//得到所有自定义的以及Lucky自带的环绕增强节点
		pointRuns.stream().filter(a->a.getMethods()!=null&&a.getMethods().contains(method)).forEach((a)->{Point p=a.getPoint();p.method=method;p.params=params;points.add(p);});
		
		point.method=method;
		point.params=params;
		points.add(point);
		
		//将所的环绕增强节点组成一个执行链
		Chain chain=new Chain(points,target,params,methodProxy);
		Object resule;
		
		//执行增强策略
		resule= chain.proceed();
		chain.setIndex(-1);
		return resule;
	}


}
