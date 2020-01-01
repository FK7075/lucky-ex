package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.annotation.aop.Expand;

public class PointRunFactory {
	
	public static List<PointRun> createPointRuns(Class<?> agentClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		List<PointRun> pointRuns=new ArrayList<>();
		Constructor<?> constructor = agentClass.getConstructor();
		constructor.setAccessible(true);
		Object agent=constructor.newInstance();
		Method[] agentMethods=agentClass.getDeclaredMethods();
		for(Method method:agentMethods) {
			if(method.isAnnotationPresent(Expand.class))
				pointRuns.add(new PointRun(agent,method));
		}
		return pointRuns;
	}

}
