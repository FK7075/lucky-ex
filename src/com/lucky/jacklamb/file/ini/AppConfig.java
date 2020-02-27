package com.lucky.jacklamb.file.ini;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lucky.jacklamb.tcconversion.typechange.JavaConversion;
import com.lucky.jacklamb.utils.ArrayCast;

public class AppConfig {
	
	private static IniFilePars iniFilePars;
	
	static {
		iniFilePars=IniFilePars.getIniFilePars();
	}
	
	/**
	 * �õ�App���µ�����key-valueֵ��ɵ�Map
	 * @return
	 */
	public static Map<String,String> getAppParamMap() {
		return iniFilePars.getSectionMap("App");
	}
	
	/**
	 *  �õ�App���µ�ĳһ��key��Ӧ��valueֵ
	 * @param key key��
	 * @return
	 */
	public static String getAppParam(String key) {
		return getAppParamMap().get(key);
	}
	
	/**
	 * �õ�App���µ�ĳһ��key��Ӧ��valueֵ(ָ������)
	 * @param key key��
	 * @param clazz ����Class
	 * @return
	 */
	public static <T> T getAppParam(String key,Class<T> clazz) {
		return getValue("App",key,clazz);
	}
	
	/**
	 * �õ�App���µ�ĳһ��key��Ӧ��valueֵ(String[]����)
	 * @param key key��
	 * @param separator �ָ���
	 * @return
	 */
	public static String[] getAppStringArray(String key,String separator) {
		return getStringArray("App",key,separator);
	}
	
	
	/**
	 * �õ�App���µ�ĳһ��key��Ӧ��valueֵ(String[]����)
	 * @param key key��
	 * @param separator �ָ���
	 * @return
	 */
	public static String[] getAppStringArray(String key) {
		return getAppStringArray(key,",");
	}
	
	public static <T> T[] getAppArray(String key,Class<T> clazz) {
		return getArray("App",key,clazz);
	}
	
	public static <T> T[] getAppArray(String key,Class<T> clazz,String separator) {
		return getArray("App",key,clazz,separator);
	}
	
	/**
	 * �õ�ĳ����ָ�����µ����е�key-valueֵ��ɵ�Map
	 * @param section �ڵ�����
	 * @return
	 */
	public static Map<String,String> getSectionMap(String section) {
		return iniFilePars.getSectionMap(section);
	}
	
	/**
	 * �õ�ĳ��ָ������ָ��key��valueֵ
	 * @param section
	 * @param key
	 * @return
	 */
	public static String getValue(String section,String key) {
		return iniFilePars.getSectionMap(section).get(key);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getValue(String section,String key,Class<T> clazz) {
		return (T) JavaConversion.strToBasic(getValue(section,key), clazz);
	}
	
	/**
	 * �õ�һ��String[]��ʽ��value
	 * @param section ������
	 * @param key key����
	 * @param separator �ָ���
	 * @return
	 */
	public static String[] getStringArray(String section,String key,String separator) {
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
	public static String[] getStringArray(String section,String key) {
		return getStringArray(section,key,",");
	}
	
	/**
	 * �õ�һ��ָ������������ʽ��value
	 * @param section ������
	 * @param key key����
	 * @param changTypeClass ��������Class
	 * @param separator �ָ���
	 * @return
	 */
	public static <T> T[] getArray(String section,String key,Class<T> changTypeClass,String separator) {
		return ArrayCast.strArrayChange(getStringArray(section,key,separator), changTypeClass);
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
	public static <T extends Collection<M>,M> T getCollection(String section,String key,Class<T> collectionClass,Class<M> genericClass,String separator) {
		M[] objArr=getArray(section,key,genericClass,separator);
		if(collectionClass.isAssignableFrom(List.class)) {
			return (T) Arrays.asList(objArr);
		}else if(collectionClass.isAssignableFrom(Set.class)){
			Set<M> hashSet = new HashSet<>(Arrays.asList(objArr));
			return (T) hashSet;
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
	public static <T extends Collection<M>,M> T getAppCollection(String key,Class<T> collectionClass,Class<M> genericClass,String separator) {
		return getCollection("App",key,collectionClass,genericClass,separator);
	}
	
	/**
	 * �õ�App����ָ�����ͼ�����ʽ��value
	 * @param key key��
	 * @param collectionClass ��������
	 * @param genericClass ��������
	 * @return
	 */
	public static <T extends Collection<M>,M> T getAppCollection(String key,Class<T> collectionClass,Class<M> genericClass) {
		return getCollection("App",key,collectionClass,genericClass);
	}
	
	/**
	 * �õ�App����String���ͼ�����ʽ��value
	 * @param key key��
	 * @param collectionClass ��������
	 * @return
	 */
	public static <T extends Collection<String>> T getAppCollection(String key,Class<T> collectionClass) {
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
	public static <T extends Collection<M>,M> T getCollection(String section,String key,Class<T> collectionClass,Class<M> genericClass) {
		return getCollection(section,key,collectionClass,genericClass,",");
	}
	
	/**
	 * �õ�ָ������String���ͼ�����ʽ��value
	 * @param section ����
	 * @param key key��
	 * @param collectionClass ��������
	 * @return
	 */
	public static <T extends Collection<String>> T getCollection(String section,String key,Class<T> collectionClass) {
		return getCollection(section,key,collectionClass,String.class,",");
	}
	
	/**
	 * �õ�һ��ָ������������ʽ��value
	 * @param section ������
	 * @param key key����
	 * @param changTypeClass ��������Class
	 * @return
	 */
	public static <T> T[] getArray(String section,String key,Class<T> changTypeClass) {
		return getArray(section,key,changTypeClass,",");
	}
	
	/**
	 * ��ĳ�����µ�������Ϣ��װΪһ���ض��Ķ���
	 * @param clazz �����Class
	 * @return
	 */
	public static <T> T getObject(Class<T> clzz) {
		return getObject(clzz,clzz.getSimpleName());
	}
	
	
	/**
	 * ��ĳ�����µ�������Ϣ��װΪһ���ض��Ķ���
	 * @param clazz �����Class
	 * @param section ������
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObject(Class<T> clazz,String section) {
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
					if(field.getType().isArray()) {
						field.set(object, getArray(section,sectionMap.get(fieldName),field.getType()));
					}else if(field.getType().isAssignableFrom(List.class)) {
						field.set(object, getCollection(section,fieldName,List.class,ArrayCast.getClassFieldGenericType(field)[0]));
					}else if(field.getType().isAssignableFrom(Set.class)) {
						field.set(object, getCollection(section,fieldName,Set.class,ArrayCast.getClassFieldGenericType(field)[0]));
					}else if(field.getType().getClassLoader()==null) {
						field.set(object, JavaConversion.strToBasic(sectionMap.get(fieldName), field.getType()));
					}else {
						field.set(object, Class.forName(sectionMap.get(fieldName)).newInstance());
					}
				}
			}
			return (T) object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
