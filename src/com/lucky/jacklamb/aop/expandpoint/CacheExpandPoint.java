package com.lucky.jacklamb.aop.expandpoint;

import java.util.HashMap;
import java.util.Map;

import com.lucky.jacklamb.annotation.aop.Cacheable;
import com.lucky.jacklamb.aop.proxy.Chain;
import com.lucky.jacklamb.aop.proxy.Point;
import com.lucky.jacklamb.expression.ExpressionEngine;
import com.lucky.jacklamb.ioc.ApplicationBeans;

public class CacheExpandPoint extends Point{
	
	private ApplicationBeans beans;
	
	@SuppressWarnings("unchecked")
	public Object cacheResult(Chain chain) {
//		String condition = cachAnn.condition();//���ʽ��������߻���,�������ʽ�������棬�������� 
		beans=ApplicationBeans.createApplicationBeans();
		Object result = null;
		Cacheable cachAnn=method.getAnnotation(Cacheable.class);
		String mapid = cachAnn.value();//�����еĻ����ids
		Map<String,Object> cacheMap = null;
		String key = cachAnn.key();//����ڻ����е�key
		key=ExpressionEngine.removeSymbol(key, params, "#[", "]");
		if(beans.containsComponent(mapid)) {
			cacheMap=(Map<String, Object>) beans.getComponentBean(mapid);
		}
		if(cacheMap==null) {//�����л������ڸû�������
			result=chain.proceed();
			cacheMap=new HashMap<>();
			cacheMap.put(key,result);
			beans.addComponentBean(mapid, cacheMap);
			return result;
			
		}else if(cacheMap!=null&&!cacheMap.containsKey(key)) {//�����д��ڸû�������,���ǻ��������в�����key
			result=chain.proceed();
			cacheMap.put(key, result);
			return result;
		}else {//�����д��ڸû�������,���ǻ��������д���key
			return cacheMap.get(key);
		}
		
	}
	
	@Override
	public Object proceed(Chain chain) {
		// ���ػ����е�ֵ
		return cacheResult(chain);
	}
	

}
