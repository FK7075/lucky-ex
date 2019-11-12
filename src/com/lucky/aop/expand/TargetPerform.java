package com.lucky.aop.expand;

import java.util.List;

/**
 * 单个方法的所有增强集成
 * @author DELL
 *
 */
public class TargetPerform {
	
	private Perform targetPerform;
	
	private List<ExpandPrefixSuffix> eps;
	
	

	public TargetPerform(Perform targetPerform) {
		this.targetPerform = targetPerform;
	}

	public Perform getTargetPerform() {
		return targetPerform;
	}

	public void setTargetPerform(Perform targetPerform) {
		this.targetPerform = targetPerform;
	}

	public List<ExpandPrefixSuffix> getEps() {
		return eps;
	}

	public void setEps(List<ExpandPrefixSuffix> eps) {
		this.eps = eps;
	}
	

}
