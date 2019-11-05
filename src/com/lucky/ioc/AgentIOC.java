package com.lucky.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.exception.NotFindBeanException;

/**
 * 代理对象集合
 * @author DELL
 *
 */
public class AgentIOC {
	
	private Map<String,Object> agentMap;
	
	private List<String> agentIDS;
	
	public boolean containId(String id) {
		if(agentMap==null)
			return false;
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
		if(agentMap==null)
			agentMap=new HashMap<>();
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
		if(agentIDS==null)
			agentIDS=new ArrayList<>();
		agentIDS.add(id);
	}
	
	

}
