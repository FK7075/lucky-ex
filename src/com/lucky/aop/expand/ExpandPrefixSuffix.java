package com.lucky.aop.expand;

/**
 * 前置增强执行与后置增强执行组成的执行对
 * @author DELL
 *
 */
public class ExpandPrefixSuffix {
	
	private Perform prefixExpand;
	
	private Perform suffixExpand;

	public Perform getPrefixExpand() {
		return prefixExpand;
	}

	public void setPrefixExpand(Perform prefixExpand) {
		this.prefixExpand = prefixExpand;
	}

	public Perform getSuffixExpand() {
		return suffixExpand;
	}

	public void setSuffixExpand(Perform suffixExpand) {
		this.suffixExpand = suffixExpand;
	}
	
	

}
