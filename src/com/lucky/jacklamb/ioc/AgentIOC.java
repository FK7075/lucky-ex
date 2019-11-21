package com.lucky.jacklamb.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.exception.NotAddIOCComponent;
import com.lucky.jacklamb.exception.NotFindBeanException;

/**
 * ������󼯺�
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
			throw new NotFindBeanException("��Controller(ioc)�������Ҳ���IDΪ--"+id+"--��Bean...");
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
			throw new NotAddIOCComponent("Agent(ioc)�������Ѵ���IDΪ--"+id+"--��������޷��ظ����......");
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
