package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Method;
import java.util.List;

public class PointRun {
	
	private Point point;
	
	private List<Method> methods;//ִ�иýڵ㷽������ʵ����

	/**
	 * MethodRun�����������ڹ���һ������ֱ�����еĻ�����ǿ�ڵ�
	 * @param point �Զ���Ļ�����ǿ
	 * @param methods ������ǿ���õ���ʵ����
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
