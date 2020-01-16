package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * ��ǰִ�з��������������
 * @author fk-7075
 *
 */
public class TargetMethodSignature {
	
	/**
	 * ��ǰ��ʵ��Ĵ������
	 */
	private Object aspectObject;
	
	/**
	 * ��ǰ��ʵ���Ӧ��Class
	 */
	private Class<?> targetClass;
	
	/**
	 * ��ǰ��ʵ������Method
	 */
	private Method currMethod;
	
	/**
	 * ��ǰ��ʵ�����Ĳ����б�
	 */
	private Object[] params;
	
	/**
	 * �ɵ�ǰ��ʵ�����Ĳ����б�ֵ�����Ӧ��λ����ɵ�Map<index,value>
	 */
	private Map<Integer,Object> indexMap;
	
	/**
	 * �ɵ�ǰ��ʵ�����Ĳ����б�ֵ�����Ӧ��������ɵ�Map<paramName,value>
	 */
	private Map<String,Object> nameMap;
	
	/**
	 * �����б�� Parameter[]
	 */
	private Parameter[] parameters;
	
	
	
	/**
	 * �õ���ǰ��ʵ���Ӧ��Class
	 * @return
	 */
	public Class<?> getTargetClass() {
		return targetClass;
	}

	/**
	 * �õ���ǰ��ʵ��Ĵ������
	 * @return
	 */
	public Object getAspectObject() {
		return aspectObject;
	}

	/**
	 *�õ���ǰ��ʵ������Method
	 * @return
	 */
	public Method getCurrMethod() {
		return currMethod;
	}

	/**
	 * �õ���ǰ��ʵ�����Ĳ����б�
	 * @return
	 */
	public Object[] getParams() {
		return params;
	}

	/**
	 * �õ��ɵ�ǰ��ʵ�����Ĳ����б�ֵ�����Ӧ��λ����ɵ�Map<index,value>
	 * @return
	 */
	public Map<Integer, Object> getIndexMap() {
		return indexMap;
	}

	/**
	 * �õ��ɵ�ǰ��ʵ�����Ĳ����б�ֵ�����Ӧ��������ɵ�Map<paramName,value>
	 * JDK8�����ϰ汾����ʹ�ã��ұ��뿪��-parameters���ܵõ���ȷ�Ĳ����������򽫵õ���arg0��arg1...��
	 * @return
	 */
	public Map<String, Object> getNameMap() {
		return nameMap;
	}

	/**
	 * �õ������б�� Parameter[]
	 * @return
	 */
	public Parameter[] getParameters() {
		return parameters;
	}

	public TargetMethodSignature(Object aspectObject,Method currMethod,Object[] params) {
		this.aspectObject=aspectObject;
		this.targetClass=aspectObject.getClass().getSuperclass();
		this.currMethod=currMethod;
		this.params=params;
		indexMap=new HashMap<>();
		for(int i=0;i<params.length;i++) {
			indexMap.put(i+1, params[i]);
		}
		parameters = currMethod.getParameters();
		nameMap=new HashMap<>();
		for(int i=0;i<parameters.length;i++) {
			nameMap.put(parameters[i].getName(), params[i]);
		}
	}
	
	public boolean containsIndex(int index) {
		return indexMap.containsKey(index);
	}
	
	public Object getParamByIndex(int index){
		if(!containsIndex(index))
			return null;
		return indexMap.get(index);
	}
	
	public boolean containsParamName(String paramName) {
		return nameMap.containsKey(paramName);
	}
	
	public Object getParamByName(String paramName) {
		if(!containsParamName(paramName))
			return null;
		return nameMap.get(paramName);
	}
	


}
