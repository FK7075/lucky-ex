package com.lucky.jacklamb.file.ini;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.lucky.jacklamb.tcconversion.typechange.JavaConversion;
import com.lucky.jacklamb.utils.ArrayCast;
 
public class INIConfig {
	
	private  IniFilePars iniFilePars;
	
	private static Map<String,IniFilePars> iniMap;
	
	public INIConfig(){
		if(iniMap==null) {
			iniMap=new HashMap<>();
			iniFilePars=new IniFilePars();
			iniMap.put("appconfig.ini", iniFilePars);
		}else {
			iniFilePars=iniMap.get("appconfig.ini");
		}
	}
	
	public INIConfig(String path) {
		if(path.startsWith("/")) {
			path=path.substring(1);
		}
		if(iniMap==null) {
			iniMap=new HashMap<>();
			iniFilePars=new IniFilePars(path);
			iniMap.put(path, iniFilePars);
		}else if(iniMap.containsKey(path)){
			iniFilePars=iniMap.get(path);
		}else {
			iniFilePars=new IniFilePars(path);
			iniMap.put(path, iniFilePars);
		}
	}
	
	/**
	 * �õ������ļ������е�������Ϣ
	 * @return
	 */
	public Map<String,Map<String,String>> getIniMap(){
		return iniFilePars.getIniMap();
	}
	/**
	 * �õ�App���µ�����key-valueֵ��ɵ�Map
	 * @return
	 */
	public  Map<String,String> getAppParamMap() {
		return iniFilePars.getSectionMap("App");
	}
	
	/**
	 *  �õ�App���µ�ĳһ��key��Ӧ��valueֵ
	 * @param key key��
	 * @return
	 */
	public  String getAppParam(String key) {
		return getAppParamMap().get(key);
	}
	
	/**
	 * �õ�App���µ�ĳһ��key��Ӧ��valueֵ(ָ������)
	 * @param key key��
	 * @param clazz ����Class
	 * @return
	 */
	public  <T> T getAppParam(String key,Class<T> clazz) {
		return getValue("App",key,clazz);
	}
	
	/**
	 * �õ�App���µ�ĳһ��key��Ӧ��valueֵ(String[]����)
	 * @param key key��
	 * @param separator �ָ���
	 * @return
	 */
	public  String[] getAppStringArray(String key,String separator) {
		return getArray("App",key,separator);
	}
	
	
	/**
	 * �õ�App���µ�ĳһ��key��Ӧ��valueֵ(String[]����)
	 * @param key key��
	 * @param separator �ָ���
	 * @return
	 */
	public  String[] getAppStringArray(String key) {
		return getAppStringArray(key,",");
	}
	
	public  <T> T[] getAppArray(String key,Class<T> clazz) {
		return getArray("App",key,clazz);
	}
	
	public  <T> T[] getAppArray(String key,Class<T> clazz,String separator) {
		return getArray("App",key,clazz,separator);
	}
	
	/**
	 * �õ�ĳ����ָ�����µ����е�key-valueֵ��ɵ�Map
	 * @param section �ڵ�����
	 * @return
	 */
	public  Map<String,String> getSectionMap(String section) {
		return iniFilePars.getSectionMap(section);
	}
	
	/**
	 * �õ�ĳ��ָ������ָ��key��valueֵ
	 * @param section
	 * @param key
	 * @return
	 */
	public  String getValue(String section,String key) {
		return iniFilePars.getSectionMap(section).get(key);
	}
	
	/**
	 * �õ�һ���������͵�Value
	 * @param section ������
	 * @param key key��
	 * @param clazz ָ�����͵�Class
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  <T> T getValue(String section,String key,Class<T> clazz) {
		return (T) JavaConversion.strToBasic(getValue(section,key), clazz);
	}
	
	/**
	 * �õ�һ��String[]��ʽ��value
	 * @param section ������
	 * @param key key����
	 * @param separator �ָ���
	 * @return
	 */
	public  String[] getArray(String section,String key,String separator) {
		if(iniFilePars.isHasKey(section, key)) {
			return iniFilePars.getValue(section, key).split(separator);
		}
		return null;
	}
	
	/**
	 * �õ�һ��String[]��ʽ��value
	 * @param section ������
	 * @param key key����
	 * @return
	 */
	public  String[] getArray(String section,String key) {
		return getArray(section,key,",");
	}
	
	/**
	 * �õ�һ��ָ������������ʽ��value
	 * @param section ������
	 * @param key key����
	 * @param changTypeClass ��������Class
	 * @param separator �ָ���
	 * @return
	 */
	public  <T> T[] getArray(String section,String key,Class<T> changTypeClass,String separator) {
		return ArrayCast.strArrayChange(getArray(section,key,separator), changTypeClass);
	}
	
	/**
	 * �õ�һ��ָ�����ͼ�����ʽ��value
	 * @param section ����
	 * @param key key��
	 * @param collectionClass ��������
	 * @param genericClass ��������
	 * @param separator �ָ���
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  <T extends Collection<M>,M> T getCollection(String section,String key,Class<T> collectionClass,Class<M> genericClass,String separator) {
		M[] objArr=getArray(section,key,genericClass,separator);
		if(collectionClass.isAssignableFrom(List.class)) {
			return (T) Arrays.asList(objArr);
		}else if(collectionClass.isAssignableFrom(Set.class)){
			Set<M> set=new HashSet<>(Arrays.asList(objArr));
			return (T) set;
		}else {
			return null;
		}
	}
	
	/**
	 * �õ�App����ָ�����ͼ�����ʽ��value
	 * @param key key��
	 * @param collectionClass ��������
	 * @param genericClass ��������
	 * @param separator �ָ���
	 * @return
	 */
	public  <T extends Collection<M>,M> T getAppCollection(String key,Class<T> collectionClass,Class<M> genericClass,String separator) {
		return getCollection("App",key,collectionClass,genericClass,separator);
	}
	
	/**
	 * �õ�App����ָ�����ͼ�����ʽ��value
	 * @param key key��
	 * @param collectionClass ��������
	 * @param genericClass ��������
	 * @return
	 */
	public  <T extends Collection<M>,M> T getAppCollection(String key,Class<T> collectionClass,Class<M> genericClass) {
		return getCollection("App",key,collectionClass,genericClass);
	}
	
	/**
	 * �õ�App����String���ͼ�����ʽ��value
	 * @param key key��
	 * @param collectionClass ��������
	 * @return
	 */
	public  <T extends Collection<String>> T getAppCollection(String key,Class<T> collectionClass) {
		return getCollection("App",key,collectionClass,String.class);
	}
	
	/**
	 * �õ�ָ������ָ�����ͼ�����ʽ��value
	 * @param section ����
	 * @param key key��
	 * @param collectionClass ��������
	 * @param genericClass ��������
	 * @return
	 */
	public  <T extends Collection<M>,M> T getCollection(String section,String key,Class<T> collectionClass,Class<M> genericClass) {
		return getCollection(section,key,collectionClass,genericClass,",");
	}
	
	/**
	 * �õ�ָ������String���ͼ�����ʽ��value
	 * @param section ����
	 * @param key key��
	 * @param collectionClass ��������
	 * @return
	 */
	public  <T extends Collection<String>> T getCollection(String section,String key,Class<T> collectionClass) {
		return getCollection(section,key,collectionClass,String.class,",");
	}
	
	/**
	 * �õ�һ��ָ������������ʽ��value
	 * @param section ������
	 * @param key key����
	 * @param changTypeClass ��������Class
	 * @return
	 */
	public  <T> T[] getArray(String section,String key,Class<T> changTypeClass) {
		return getArray(section,key,changTypeClass,",");
	}
	
	/**
	 * ��ĳ�����µ�������Ϣ��װΪһ���ض��Ķ���
	 * @param clazz �����Class
	 * @return
	 */
	public  <T> T getObject(Class<T> clzz) {
		return getObject(clzz,clzz.getSimpleName());
	}
	
	
	/**
	 * ��ĳ�����µ�������Ϣ��װΪһ���ض��Ķ���
	 * @param clazz �����Class
	 * @param section ������
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  <T> T getObject(Class<T> clazz,String section) {
		if(!iniFilePars.isHasSection(section))
			return null;
		Object object=null;
		try {
			Map<String, String> sectionMap = iniFilePars.getSectionMap(section);
			Constructor<T> constructor = clazz.getConstructor();
			constructor.setAccessible(true);
			object=constructor.newInstance(); 
			Field[] fields=clazz.getDeclaredFields();
			String fieldName;
			for(Field field:fields) {
				fieldName=field.getName();
				if(sectionMap.containsKey(fieldName)) {
					field.setAccessible(true);
					String sectionValue = sectionMap.get(fieldName);
					if(field.getType().isArray()) {
						field.set(object, getArray(section,sectionValue,field.getType()));
					}else if(field.getType().isAssignableFrom(List.class)) {
						field.set(object, getCollection(section,fieldName,List.class,ArrayCast.getClassFieldGenericType(field)[0]));
					}else if(field.getType().isAssignableFrom(Set.class)) {
						field.set(object, getCollection(section,fieldName,Set.class,ArrayCast.getClassFieldGenericType(field)[0]));
					}else if(field.getType().getClassLoader()==null) {
						field.set(object, JavaConversion.strToBasic(sectionValue, field.getType()));
					}else if(sectionValue.startsWith("@S:")) {
						field.set(object,getObject(field.getType(),sectionValue.substring(3)));
					}else {
						field.set(object, Class.forName(sectionValue).newInstance());
					}
				}
			}
			return (T) object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * ��ӡ�����ļ��е�����������Ϣ
	 */
	public void printIniMap() {
		Map<String, Map<String, String>> iniMap = getIniMap();
		for(Entry<String,Map<String,String>> entry: iniMap.entrySet()) {
			System.out.println("["+entry.getKey()+"]");
			for(Entry<String,String> kv:entry.getValue().entrySet()) {
				System.out.println("\t"+kv.getKey()+"="+kv.getValue());
			}
		}
	}
	

}
