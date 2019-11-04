package com.lucky.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentIOC {
	
	private Map<String,Object> agentMap;
	
	private List<String> agentIDS;

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
