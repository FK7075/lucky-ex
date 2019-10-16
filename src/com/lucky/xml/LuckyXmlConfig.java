package com.lucky.xml;

import java.util.List;

import com.lucky.utils.ProperConfig;

public class LuckyXmlConfig {
	
	private List<LuckyXml> beans;
	private ProperConfig proper;
	private MvcXmlModel mvcxml;
	
	private static LuckyXmlConfig cfg;
	
	
	private LuckyXmlConfig() {
		
		this.beans=LuckyDomXml.getLuckyBeans();
		this.mvcxml=LuckyDomXml.getLuckyMappings();
		this.proper=LuckyDomXml.getProperties();
	}
	
	public static LuckyXmlConfig loadLuckyXmlConfig() {
		if(cfg==null)
			return new LuckyXmlConfig();
		else
			return cfg;
	}

	
	public MvcXmlModel getMvcxml() {
		return mvcxml;
	}

	public List<LuckyXml> getBeans() {
		return beans;
	}

	public ProperConfig getProper() {
		return proper;
	}


}
