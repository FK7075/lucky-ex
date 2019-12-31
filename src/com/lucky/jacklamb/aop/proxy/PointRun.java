package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Method;
import java.util.List;

public class PointRun {
	
	private Point point;
	
	private List<Method> methods;//执行该节点方法的真实方法

	/**
	 * MethodRun构造器，用于构造一个可以直接运行的环绕增强节点
	 * @param point 自定义的环绕增强
	 * @param methods 环绕增强作用的真实方法
	 */
	public PointRun(Point point, List<Method> methods) {
		this.point = point;
		this.methods = methods;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}
	
	
}
