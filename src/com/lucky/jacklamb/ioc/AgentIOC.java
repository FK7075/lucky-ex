package com.lucky.jacklamb.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.annotation.aop.After;
import com.lucky.jacklamb.annotation.aop.Agent;
import com.lucky.jacklamb.annotation.aop.Before;
import com.lucky.jacklamb.aop.proxy.AgentRun;
import com.lucky.jacklamb.aop.proxy.Chain;
import com.lucky.jacklamb.aop.proxy.Point;
import com.lucky.jacklamb.enums.Location;
import com.lucky.jacklamb.exception.NotAddIOCComponent;
import com.lucky.jacklamb.exception.NotFindBeanException;
import com.lucky.jacklamb.utils.LuckyUtils;

/**
 * ������󼯺�
 * @author DELL
 *
 */
public class AgentIOC {
	
	private Map<String,AgentRun> agentMap;
	
	private List<String> agentIDS;
	
	public AgentIOC() {
		agentMap=new HashMap<>();
		agentIDS=new ArrayList<>();
	}

	public boolean containId(String id) {
		return agentIDS.contains(id);
	}
	
	public AgentRun getAgentBean(String id) {
		if(!containId(id))
			throw new NotFindBeanException("��Controller(ioc)�������Ҳ���IDΪ--"+id+"--��Bean...");
		return agentMap.get(id);
	}

	public Map<String, AgentRun> getAgentMap() {
		return agentMap;
	}

	public void setAgentMap(Map<String, AgentRun> agentMap) {
		this.agentMap = agentMap;
	}
	
	public void addAgentMap(String id,AgentRun object) {
		if(containId(id))
			throw new NotAddIOCComponent("Agent(ioc)�������Ѵ���IDΪ--"+id+"--��������޷��ظ���ӣ�������������ͬ����@Agent������⽫�ᵼ���쳣�ķ�������......");
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
	
	public AgentIOC initAgentIOC(List<String> componentClass) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Agent agann;
		String name;
		AgentRun agentRun;
		Before before;
		After after;
		for(String clzz:componentClass) {
			Class<?> agent=Class.forName(clzz);
			if(agent.isAnnotationPresent(Agent.class)) {
				agann=agent.getAnnotation(Agent.class);
				if("".equals(agann.value())) {
					name=LuckyUtils.TableToClass1(agent.getSimpleName());
				}else {
					name=agann.value();
				}
				Method[] enhanceMethods=agent.getDeclaredMethods();
				for(Method method:enhanceMethods) {
					if(method.isAnnotationPresent(Before.class)) {
						before=method.getAnnotation(Before.class);
						if("".equals(before.value())) {
							name+=("."+LuckyUtils.TableToClass1(method.getName()));
						}else {
							name+=("."+before.value());
						}
						agentRun=new AgentRun(method,Location.BEFORE,before.idScope(),before.pathScope());
						addAgentMap(name, agentRun);
					}else if(method.isAnnotationPresent(After.class)) {
						after=method.getAnnotation(After.class);
						if("".equals(after.value())) {
							name+=("."+LuckyUtils.TableToClass1(method.getName()));
						}else {
							name+=("."+after.value());
						}
						agentRun=new AgentRun(method,Location.AFTER,after.idScope(),after.pathScope());
						addAgentMap(name, agentRun);
					}else{
						continue;
					}
				}
			}else if(Point.class.isAssignableFrom(agent)) {
				agann=agent.getDeclaredMethod("proceed", Chain.class).getAnnotation(Agent.class);
				name=LuckyUtils.TableToClass1(agent.getSimpleName())+".proceed";
				Constructor<?> constructor = agent.getConstructor();
				constructor.setAccessible(true);
				agentRun=new AgentRun((Point)constructor.newInstance(),agann.idScope(),agann.pathScope());
				addAgentMap(name,agentRun);
			}
		}
		return this;
	}

}
