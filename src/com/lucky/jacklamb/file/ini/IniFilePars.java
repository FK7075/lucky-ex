package com.lucky.jacklamb.file.ini;

import static com.lucky.jacklamb.sqlcore.c3p0.IniKey.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

import com.lucky.jacklamb.enums.Scan;
import com.lucky.jacklamb.ioc.config.ScanConfig;
import com.lucky.jacklamb.ioc.config.ServerConfig;
import com.lucky.jacklamb.ioc.config.WebConfig;

public class IniFilePars {
	
	private static IniFilePars iniFilePars;
	
	private Map<String,Map<String,String>> iniMap;
	
	private String currSection;
	
	private String currLine;
	
	private String iniName;
	
	private InputStream iniInputStream;
	
	private IniFilePars() {
		iniMap=new HashMap<>();
		iniInputStream=IniFilePars.class.getClassLoader().getResourceAsStream("appconfig.ini");
		iniName="appconfig.ini";
		try {
			if(iniMap.isEmpty())
				pars();
		}catch(ArrayIndexOutOfBoundsException e) {
			throw new RuntimeException(iniName+"配置文件内容格式不正确",e);
		}
	}
	
	private IniFilePars(String iniFilePath) {
		iniMap=new HashMap<>();
		iniInputStream=IniFilePars.class.getClassLoader().getResourceAsStream(iniFilePath);
		iniName=iniFilePath.substring(iniFilePath.lastIndexOf("/"));
		try {
			if(iniMap.isEmpty())
				pars();
		}catch(ArrayIndexOutOfBoundsException e) {
			throw new RuntimeException(iniName+"配置文件内容格式不正确",e);
		}
	}
	
	public static IniFilePars getIniFilePars() {
		if(iniFilePars==null)
			iniFilePars=new IniFilePars();
		return iniFilePars;
	}
	
	public boolean iniExist() {
		return iniInputStream!=null;
	}
	
	private void pars() {
		if(iniInputStream!=null) {
			InputStreamReader isr = new InputStreamReader(iniInputStream);
			BufferedReader read = new BufferedReader(isr);
			Map<String,String> kvMap=new HashMap<>();
			try {
				while((currLine = read.readLine()) != null) {
					if(currLine.contains(";")) {
						currLine=currLine.substring(0,currLine.indexOf(";"));
					}
					if(currLine.contains("#")) {
						currLine=currLine.substring(0,currLine.indexOf("#"));
					}
					if(currLine.startsWith(";")||currLine.startsWith("#")) {
						continue;
					}else if(currLine.startsWith("[")&&currLine.endsWith("]")) {
						currSection=currLine.substring(1,currLine.length()-1);
						if(iniMap.containsKey(currSection))
							throw new RuntimeException(iniName+"配置文件内容格式不正确,存在两个相同的Section:["+currSection+"]");
						iniMap.put(currSection,new HashMap<>());
						continue;
					}else if(currLine.contains("=")){
						currLine=currLine.replaceFirst("=", "%Lucky%FK@7075&XFL");
						String[] KV = currLine.split("%Lucky%FK@7075&XFL");
						if(iniMap.containsKey(currSection)) {
							kvMap=iniMap.get(currSection);
							kvMap.put(KV[0], KV[1]);
						}else {
							kvMap.put(KV[0], KV[1]);
							iniMap.put(currSection, kvMap);
						}
						continue;
					}else {
						continue;
					}
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				read.close();
				isr.close();
				iniInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean isHasSection(String section) {
		return iniMap.containsKey(section);
	}
	
	public boolean isHasKey(String section,String key) {
		if(isHasSection(section)) {
			return iniMap.get(section).containsKey(key);
		}else {
			return false;
		}
	}
	
	public Map<String,String> getSectionMap(String section){
		if(iniMap.containsKey(section))
			return iniMap.get(section);
		return null;
	}
	
	public String getValue(String section,String key) {
		Map<String, String> sectionMap = getSectionMap(section);
		if(sectionMap!=null) {
			if(sectionMap.containsKey(key))
				return sectionMap.get(key);
			return null;
		}
		return null;
	}
	
	public void modifyAllocation(ScanConfig scan,WebConfig web,ServerConfig server) {
		if(iniMap.isEmpty())
			return;
		Map<String, String> sectionMap;
		if(this.isHasSection(SECTION_SUFFIX_SCAN)) {
			sectionMap = this.getSectionMap(SECTION_SUFFIX_SCAN);
			scan.setScanMode(Scan.SUFFIX_SCAN);
			setScanConfig(scan,sectionMap);
		}
		if(this.isHasSection(SECTION_TOMCAT)) {
			sectionMap = this.getSectionMap(SECTION_TOMCAT);
			setTomcat(server,sectionMap);
		}
		if(this.isHasSection(SECTION_SERVLET)) {
			sectionMap = this.getSectionMap(SECTION_SERVLET);
			addServlet(server,sectionMap);
		}
		if(this.isHasSection(SECTION_FILTER)) {
			sectionMap = this.getSectionMap(SECTION_FILTER);
			addFilter(server,sectionMap);
		}
		if(this.isHasSection(SECTION_WEB)) {
			sectionMap = this.getSectionMap(SECTION_WEB);
			webSetting(web,sectionMap);
		}
		if(this.isHasSection(SECTION_HANDERPREFIXANDSUFFIX)) {
			sectionMap = this.getSectionMap(SECTION_HANDERPREFIXANDSUFFIX);
			addPrefixAndSuffix(web,sectionMap);
		}
		if(this.isHasSection(SECTION_STATICHANDER)) {
			sectionMap = this.getSectionMap(SECTION_STATICHANDER);
			addStaticHander(web,sectionMap);
		}
		if(this.isHasSection(SECTION_SPECIFIRESOURCESIPRESTRICT)) {
			sectionMap = this.getSectionMap(SECTION_SPECIFIRESOURCESIPRESTRICT);
			addspecifiResourcesIpRestrict(web,sectionMap);
		}
		
	}
	
	private void addspecifiResourcesIpRestrict(WebConfig web,Map<String, String> sectionMap) {
		Map<String,List<String>> resAndIpsMap=new HashMap<>();
		for(Entry<String,String> e:sectionMap.entrySet()) {
			resAndIpsMap.put(e.getKey(), Arrays.asList(e.getValue().trim().split(",")));
		}
		web.setSpecifiResourcesIpRestrict(resAndIpsMap);
	}
	
	private void addStaticHander(WebConfig web,Map<String, String> sectionMap) {
		for(Entry<String,String> e:sectionMap.entrySet()) {
			web.addStaticHander(e.getKey(), e.getValue());
		}
	}
	
	private void addPrefixAndSuffix(WebConfig web,Map<String, String> sectionMap) {
		String p="",s="";
		if(sectionMap.containsKey("prefix"))
			p=sectionMap.get("prefix");
		if(sectionMap.containsKey("suffix"))
			s=sectionMap.get("suffix");
		web.setHanderPrefixAndSuffix(p, s);
	}
	
	private void webSetting(WebConfig web,Map<String, String> sectionMap) {
		if(sectionMap.containsKey("encoding")) {
			web.setEncoding(sectionMap.get("encoding"));
		}
		if(sectionMap.containsKey("openStaticResourceManage")) {
			web.openStaticResourceManage(Boolean.parseBoolean(sectionMap.get("openStaticResourceManage")));
		}
		if(sectionMap.containsKey("postChangeMethod")) {
			web.postChangeMethod(Boolean.parseBoolean(sectionMap.get("postChangeMethod")));
		}
		if(sectionMap.containsKey("globalResourcesIpRestrict")) {
			web.addGlobalResourcesIpRestrict(sectionMap.get("globalResourcesIpRestrict").trim().split(","));
		}
		if(sectionMap.containsKey("staticResourcesIpRestrict")) {
			web.addStaticResourcesIpRestrict(sectionMap.get("staticResourcesIpRestrict").trim().split(","));
		}
	}
	
	private void addFilter(ServerConfig server,Map<String, String> sectionMap) {
		Set<String> filterNames = sectionMap.keySet();
		for(String filterName:filterNames) {
			if(!this.isHasKey(SECTION_FILTER_MAPPING, filterName))
				throw new RuntimeException("appconfig.ini配置文件中有Filter没有配置请求映射：[Filter]->"+filterName+"="+sectionMap.get(filterName));
			try {
				server.addFilter((Filter)Class.forName(sectionMap.get(filterName)).newInstance(), this.getValue(SECTION_FILTER_MAPPING, filterName).split(","));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("appconfig.ini配置文件有Filter配置错误，无法创建实例！[Filter]->"+filterName+"="+sectionMap.get(filterName),e);
			}
		}
	}
	
	private void addServlet(ServerConfig server,Map<String, String> sectionMap) {
		Set<String> servletNames = sectionMap.keySet();
		for(String servletName:servletNames) {
			if(!this.isHasKey(SECTION_SERVLET_MAPPING, servletName))
				throw new RuntimeException("appconfig.ini配置文件中有Servlet没有配置请求映射：[Servlet]->"+servletName+"="+sectionMap.get(servletName));
			try {
				server.addServlet((HttpServlet)Class.forName(sectionMap.get(servletName)).newInstance(), this.getValue(SECTION_SERVLET_MAPPING, servletName).split(","));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("appconfig.ini配置文件有Servlet配置错误，无法创建实例！[Servlet]->"+servletName+"="+sectionMap.get(servletName),e);
			}
		}
	}
	
	private void setTomcat(ServerConfig server,Map<String, String> sectionMap) {
		if(sectionMap.containsKey("port")) {
			server.setPort(Integer.parseInt(sectionMap.get("port")));
		}
		if(sectionMap.containsKey("docBase")) {
			String docStr=sectionMap.get("docBase");
			if(docStr.contains(":")) {
				server.setDocBase(docStr);
			}else {
				server.setDocBaseRelativeProject(docStr);
			}
		}
		if(sectionMap.containsKey("contextPath")) {
			server.setContextPath(sectionMap.get("contextPath"));
		}
		if(sectionMap.containsKey("webapp")) {
			server.setWebapp(sectionMap.get("webapp"));
		}
	}
	
	private void setScanConfig(ScanConfig scan,Map<String, String> sectionMap) {
		String suffixStr;
		if(sectionMap.containsKey("controller")) {
			suffixStr=sectionMap.get("controller");
			if(suffixStr.startsWith("reset:")) {
				scan.emptyAddControllerPackSuffix(suffixStr.substring(6).trim().split(","));
			}else {
				scan.addControllerPackSuffix(suffixStr.trim().split(","));
			}
		}
		if(sectionMap.containsKey("service")) {
			suffixStr=sectionMap.get("service");
			if(suffixStr.startsWith("reset:")) {
				scan.emptyAddServicePackSuffix(suffixStr.substring(6).trim().split(","));
			}else {
				scan.addServicePackSuffix(suffixStr.trim().split(","));
			}
		}
		if(sectionMap.containsKey("repository")) {
			suffixStr=sectionMap.get("repository");
			if(suffixStr.startsWith("reset:")) {
				scan.emptyAddRepositoryPackSuffix(suffixStr.substring(6).trim().split(","));
			}else {
				scan.addRepositoryPackSuffix(suffixStr.trim().split(","));
			}
		}
		if(sectionMap.containsKey("aspect")) {
			suffixStr=sectionMap.get("aspect");
			if(suffixStr.startsWith("reset:")) {
				scan.emptyAddAspectPackSuffix(suffixStr.substring(6).trim().split(","));
			}else {
				scan.addAspectPackSuffix(suffixStr.trim().split(","));
			}
		}
		if(sectionMap.containsKey("component")) {
			suffixStr=sectionMap.get("component");
			if(suffixStr.startsWith("reset:")) {
				scan.emptyAddComponentPackSuffix(suffixStr.substring(6).trim().split(","));
			}else {
				scan.addComponentPackSuffix(suffixStr.trim().split(","));
			}
		}
		if(sectionMap.containsKey("pojo")) {
			suffixStr=sectionMap.get("pojo");
			if(suffixStr.startsWith("reset:")) {
				scan.emptyAddPojoPackSuffix(suffixStr.substring(6).trim().split(","));
			}else {
				scan.addPojoPackSuffix(suffixStr.trim().split(","));
			}
		}
	}
}

