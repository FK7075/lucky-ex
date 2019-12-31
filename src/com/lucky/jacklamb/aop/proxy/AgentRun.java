package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Method;

import com.lucky.jacklamb.enums.Location;

public class AgentRun {
	
	private Method method;
	
	private Point point;
	
	private Location lication;
	
	private String[] idScope;
	
	private String[] pathScope;
	
	public AgentRun(Method method, Location lication, String[] idScope, String[] pathScope) {
		this.method = method;
		this.lication = lication;
		this.idScope = idScope;
		this.pathScope = pathScope;
	}
	

	public AgentRun(Point point, String[] idScope, String[] pathScope) {
		this.point = point;
		this.idScope = idScope;
		this.pathScope = pathScope;
	}



	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Location getLication() {
		return lication;
	}

	public void setLication(Location lication) {
		this.lication = lication;
	}

	public String[] getIdScope() {
		return idScope;
	}

	public void setIdScope(String[] idScope) {
		this.idScope = idScope;
	}

	public String[] getPathScope() {
		return pathScope;
	}

	public void setPathScope(String[] pathScope) {
		this.pathScope = pathScope;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
	
	
	
}
