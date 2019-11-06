//package com.lucky.xml;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.lucky.ioc.PackageScan;
//import com.lucky.utils.ProperConfig;
//
//public class LuckyXmlConfig {
//	
//	private List<LuckyXml> beans;
//	private ProperConfig proper;
//	private MvcXmlModel mvcxml;
//	
//	private static LuckyXmlConfig cfg;
//	
//	
//	private LuckyXmlConfig() {
//		this.beans=LuckyDomXml.getLuckyBeans();
//		this.mvcxml=LuckyDomXml.getLuckyMappings();
//		this.proper=LuckyDomXml.getProperties();
//		if(proper.getClaurl().isEmpty()) {
//			List<String> classpaths=new ArrayList<>();	
//			PackageScan.getPackageScan().loadComponent(classpaths,"pojo","entity");
//			classpaths.stream().forEach((entry)->{entry=entry.substring(0,entry.length()-6);proper.getClaurl().add(entry);});
//		}
//		
//	}
//	
//	public static LuckyXmlConfig loadLuckyXmlConfig() {
//		if(cfg==null)
//			return new LuckyXmlConfig();
//		else
//			return cfg;
//	}
//
//	
//	public MvcXmlModel getMvcxml() {
//		return mvcxml;
//	}
//
//	public List<LuckyXml> getBeans() {
//		return beans;
//	}
//
//	public ProperConfig getProper() {
//		return proper;
//	}
//
//
//}
