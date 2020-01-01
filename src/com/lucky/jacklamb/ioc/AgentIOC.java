package com.lucky.jacklamb.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.annotation.aop.Agent;
import com.lucky.jacklamb.annotation.aop.Expand;
import com.lucky.jacklamb.aop.proxy.Point;
import com.lucky.jacklamb.aop.proxy.PointRun;
import com.lucky.jacklamb.exception.NotAddIOCComponent;
import com.lucky.jacklamb.exception.NotFindBeanException;
import com.lucky.jacklamb.utils.LuckyUtils;

/**
 * 代理对象集合
 * @author DELL
 *
 */
public class AgentIOC {
	
	private Map<String,PointRun> agentMap;
	
	private List<String> agentIDS;
	
	public AgentIOC() {
		agentMap=new HashMap<>();
		agentIDS=new ArrayList<>();
	}

	public boolean containId(String id) {
		return agentIDS.contains(id);
	}
	
	public PointRun getAgentBean(String id) {
		if(!containId(id))
			throw new NotFindBeanException("在Controller(ioc)容器中找不到ID为--"+id+"--的Bean...");
		return agentMap.get(id);
	}

	public Map<String, PointRun> getAgentMap() {
		return agentMap;
	}

	public void setAgentMap(Map<String, PointRun> agentMap) {
		this.agentMap = agentMap;
	}
	
	public void addAgentMap(String id,PointRun object) {
		if(containId(id))
			throw new NotAddIOCComponent("Agent(ioc)容器中已存在ID为--"+id+"--的组件，无法重复添加（您可能配置了同名的@Agent组件，这将会导致异常的发生！）......");
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
		Expand expand;
		PointRun pointRun;
		Constructor<?> constructor;
		for(String clzz:componentClass) {
			Class<?> agent=Class.forName(clzz);
			if(agent.isAnnotationPresent(Agent.class)) {
				String name;
				agann=agent.getAnnotation(Agent.class);
				if("".equals(agann.value())) {
					name=LuckyUtils.TableToClass1(agent.getSimpleName());
				}else {
					name=agann.value();
				}
				Method[] enhanceMethods=agent.getDeclaredMethods();
				for(Method method:enhanceMethods) {
					String agentid;
					if(method.isAnnotationPresent(Expand.class)) {
						expand=method.getAnnotation(Expand.class);
						if("".equals(expand.value())) {
							agentid=name+("."+LuckyUtils.TableToClass1(method.getName()));
						}else {
							agentid=name+("."+expand.value());
						}
						constructor = agent.getConstructor();
						constructor.setAccessible(true);
						pointRun=new PointRun(constructor.newInstance(),method);
						addAgentMap(agentid, pointRun);
					}else{
						continue;
					}
				}
			}else if(Point.class.isAssignableFrom(agent)) {
				String name;
				expand=agent.getAnnotation(Expand.class);
				name=LuckyUtils.TableToClass1(agent.getSimpleName())+".proceed";
				constructor = agent.getConstructor();
				constructor.setAccessible(true);
				pointRun=new PointRun((Point)constructor.newInstance());
				addAgentMap(name,pointRun);
			}
		}
		return this;
	}

}
