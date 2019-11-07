package com.lucky.aop.defaultexpand;

import java.util.HashMap;
import java.util.Map;

import com.lucky.ioc.ApplicationBeans;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * ª∫¥Ê¿©’π¿‡
 * @author DELL
 *
 */
public class CacheExpand {
	
	public Object  getCacheExpandObject(Class<?> superClass) {
		ApplicationBeans beans=ApplicationBeans.createApplicationBeans();
		Enhancer enh=new Enhancer();
		enh.setSuperclass(superClass);
		return enh.create();
		
	}
	
	@SuppressWarnings("unchecked")
	private MethodInterceptor getInterceptor(ApplicationBeans beans,String mapId,String keyExpression) {
		Map<String,Object> cacheMap;
		if(beans.containsComponent(mapId)) {
			cacheMap=(Map<String, Object>) beans.getComponentBean(mapId);
		}else {
			cacheMap=new HashMap<>();
			beans.addComponentBean(mapId, cacheMap);
		}
		String mapKey="";
		MethodInterceptor interceptor=(object,method,params,methodProxy)->{
			if(cacheMap.isEmpty())
				return methodProxy.invokeSuper(object, params);
			return cacheMap.get(mapKey);
		};
		return interceptor;
	}

}
