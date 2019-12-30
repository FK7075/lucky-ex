package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lucky.jacklamb.enums.Location;

public class PerformMethod {
	
	private Class<?> targetClass;//��ʵ����
	
	private MethodRun methodRun;
	
	private List<Method> targetMethods;//��ʵ��������Ҫ��ǿ�����Ƕ���ķ�������
	
	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	public MethodRun getMethodRun() {
		return methodRun;
	}

	public void setMethodRun(MethodRun methodRun) {
		this.methodRun = methodRun;
	}

	public List<Method> getTargetMethods() {
		return targetMethods;
	}

	public void setTargetMethods(List<Method> targetMethods) {
		this.targetMethods = targetMethods;
	}

	public void addTargetMethodName(Method...targetMethods) {
		this.targetMethods=new ArrayList<>();
		Stream.of(targetMethods).forEach(this.targetMethods::add);
	}
	
	/**
	 * �õ���ʵ����ĳ��������������ǿ
	 * @param performMethods
	 * @param targetMethod
	 * @return
	 */
	public static List<MethodRun> getMethodRuns(List<PerformMethod> performMethods,Method targetMethod) {
		List<MethodRun> methodRuns=new ArrayList<>();
		for(PerformMethod pmm:performMethods) {
			if(pmm.getTargetMethods().contains(targetMethod))
				methodRuns.add(pmm.getMethodRun());
		}
		return methodRuns;
	} 
	
	public static List<MethodRun> getBeforeMethodRuns(List<PerformMethod> performMethods,Method targetMethod){
		return getMethodRuns(performMethods,targetMethod).stream().filter(a->a.getLocation()==Location.BEFORE).collect(Collectors.toList());
		
	}
	
	public static List<MethodRun> getAfterMethodRuns(List<PerformMethod> performMethods,Method targetMethod){
		return getMethodRuns(performMethods,targetMethod).stream().filter(a->a.getLocation()==Location.AFTER).collect(Collectors.toList());
	}

}
