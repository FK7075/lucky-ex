package com.lucky.aop.defaultexpand;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.lucky.annotation.Cacheable;
import com.lucky.ioc.ApplicationBeans;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * ª∫¥Ê¿©’π¿‡
 * @author DELL
 *
 */
public class CacheExpand {
	
	public void cacheAgent() {
		ApplicationBeans beans=ApplicationBeans.createApplicationBeans();
		for(Entry<String,Object> entry:beans.getServiceBeans().entrySet()) {
			Object agent=getCacheExpandObject(beans,entry.getValue().getClass());
			entry.setValue(agent);
		}
	}
	
	public Object  getCacheExpandObject(ApplicationBeans beans,Class<?> superClass) {
		Enhancer enh=new Enhancer();
		enh.setSuperclass(superClass);
		enh.setCallback(getInterceptor(beans));
		return enh.create();
		
	}
	
	@SuppressWarnings("unchecked")
	private MethodInterceptor getInterceptor(ApplicationBeans beans) {
		MethodInterceptor interceptor=(object,method,params,methodProxy)->{
			if(method.isAnnotationPresent(Cacheable.class)) {
				Cacheable cache=method.getAnnotation(Cacheable.class);
				String mapId=cache.value();
//				=Integer.parseInt(cache.key());
				int keyExpression=0;
				Map<String,Object> cacheMap;
				Map<String,Object> componentMap=beans.getComponentBeans();
				String paramKey = params[keyExpression].toString();
				if(componentMap.containsKey(mapId)) {
					cacheMap=(Map<String, Object>) beans.getComponentBean(mapId);
					if(cacheMap.containsKey(paramKey))
						return cacheMap.get(paramKey);
					else {
						Object returnobj=methodProxy.invokeSuper(object, params);
						cacheMap.put(paramKey, returnobj);
						return cacheMap.get(paramKey);
					}
				}else {
					Object returnobj=methodProxy.invokeSuper(object, params);
					cacheMap=new HashMap<>();
					cacheMap.put(paramKey, returnobj);
					beans.addComponentBean(mapId, cacheMap);
					return returnobj;
				}
			}
			return methodProxy.invokeSuper(object, params);
		};
		return interceptor;
	}

}
