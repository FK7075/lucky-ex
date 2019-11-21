package com.lucky.jacklamb.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.exception.NotAddIOCComponent;
import com.lucky.jacklamb.exception.NotFindBeanException;

/**
 * 代理对象集合
 * @author DELL
 *
 */
public class AgentIOC {
	
	private Map<String,Object> agentMap;
	
	private List<String> agentIDS;
	
	public AgentIOC() {
		agentMap=new HashMap<>();
		agentIDS=new ArrayList<>();
	}

	public boolean containId(String id) {
		return agentIDS.contains(id);
	}
	
	public Object getAgentBean(String id) {
		if(!containId(id))
			throw new NotFindBeanException("在Controller(ioc)容器中找不到ID为--"+id+"--的Bean...");
		return agentMap.get(id);
	}

	public Map<String, Object> getAgentMap() {
		return agentMap;
	}

	public void setAgentMap(Map<String, Object> agentMap) {
		this.agentMap = agentMap;
	}
	
	public void addAgentMap(String id,Object object) {
		if(containId(id))
			throw new NotAddIOCComponent("Agent(ioc)容器中已存在ID为--"+id+"--的组件，无法重复添加......");
		agentMap.put(id, object);
		addAgentID(id);
	}

	public List<String> getAgentIDS() {
		return agentIDS;
	}

	public void setAgentIDS(List<String> agentIDS) {
		this.agentIDS = agentIDS;
	}
	
	public void addAgentID(String id) {
		agentIDS.add(id);
	}
	
	

}
