package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class LuckyMethodInterceptor implements MethodInterceptor {
	
	private List<PerformMethod> performMethods;//关于某一个类的所有增强的执行方法
	
	private List<PointRun> pointRuns;//关于某一个类的所有增强的执行节点
	
	public LuckyMethodInterceptor(List<PerformMethod> performMethods,PointRun...pointRuns) {
		this.performMethods = performMethods;
		this.pointRuns=new ArrayList<>();
		Stream.of(pointRuns).forEach(this.pointRuns::add);
	}

	@Override
	public Object intercept(Object target, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
		List<Point> points=new ArrayList<>();
		List<MethodRun> beforeMethodRuns = PerformMethod.getBeforeMethodRuns(performMethods, method);
		List<MethodRun> afterMethodRuns = PerformMethod.getAfterMethodRuns(performMethods, method);
		Point point=(c)->{
			Object rest;
			beforeMethodRuns.stream().forEach(a->a.methodRun());
			rest=c.proceed();
			afterMethodRuns.stream().forEach(a->a.methodRun());
			return rest;
		};
		points.add(point);
		pointRuns.stream().filter(a->a.getMethods()!=null&&a.getMethods().contains(method)).forEach(a->points.add(a.getPoint()));
		Chain chain=new Chain(points,target,params,methodProxy);
		Object resule;
		resule= chain.proceed();
		chain.setIndex(-1);
		return resule;
	}


}
