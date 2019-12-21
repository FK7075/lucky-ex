package com.lucky.jacklamb.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * pojo对象转json字符串
 * 
 * @author fk7075
 *
 */
public class LSON {

	private String jsonStr;

	public String getJsonStr() {
		return jsonStr;
	}

	/**
	 * 将pojo转化为Json字符
	 * 
	 * @param jsonObject
	 */
	@SuppressWarnings("unchecked")
	public LSON(Object jsonObject) {
		if(jsonObject==null)
			jsonStr="null";
		else {
			Class<?> clzz = jsonObject.getClass();
			if (Collection.class.isAssignableFrom(clzz)) {
				try {
					jsonStr = collectionToJsonStr((Collection<?>) jsonObject);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else if (clzz.isArray()) {
				try {
					jsonStr = arrayToJsonStr((Object[])jsonObject);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}else if(Map.class.isAssignableFrom(clzz)){
				jsonStr=mapToJsonStr((Map<Object,Object>)jsonObject);
			} else if (clzz.getClassLoader() != null) {
				try {
					jsonStr = objectToJsonStr(jsonObject);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}else {
				jsonStr="\""+jsonObject.toString()+"\"";
			}
		}

	}
	
	private String mapToJsonStr(Map<Object,Object> map) {
		if(map==null||map.isEmpty()) {
			return "{}";
		}
		StringBuilder str=new StringBuilder("{");
		for(Entry<Object,Object> entry:map.entrySet()) {
			str.append(new LSON(entry.getKey()).getJsonStr()).append(":").append(new LSON(entry.getValue()).getJsonStr()).append(",");
		}
		if(str.toString().endsWith(","))
			return str.substring(0, str.length()-1)+"}";
		return "{}";
	}

	private String arrayToJsonStr(Object[] objects) throws IllegalArgumentException, IllegalAccessException {
		if (objects == null || objects.length == 0) {
			return "[]";
		}
		StringBuilder arrayJsonStr = new StringBuilder("[");
		List<String> field_json_copy = new ArrayList<>();
		List<String> field_json = new ArrayList<>();
		for (Object objStr : objects) {
			field_json_copy.add(objectToJsonStr(objStr));
		}
		field_json_copy.stream().filter(a->a!=null).forEach(field_json::add);
		for (int i = 0; i < field_json.size(); i++) {
			arrayJsonStr.append(field_json.get(i)).append(",");
		}
		if(arrayJsonStr.toString().endsWith(","))
			return arrayJsonStr.substring(0, arrayJsonStr.length()-1)+"]";
		return "[]";
	}

	/**
	 * 将List集合形式的Pojo转化为Json格式
	 * @param list pojo格式的数据
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private <T> String collectionToJsonStr(Collection<T> list) throws IllegalArgumentException, IllegalAccessException {
		if (list.isEmpty() || list == null) {
			return "[]";
		}
		StringBuilder listJsonStr =new StringBuilder("[");
		List<String> field_json_copy = new ArrayList<>();
		List<String> field_json = new ArrayList<>();
		for (T objStr : list) {
			field_json_copy.add(objectToJsonStr(objStr));
		}
		field_json_copy.stream().filter(a->a!=null).forEach(field_json::add);
		for (int i = 0; i < field_json.size(); i++) {
			listJsonStr.append(field_json.get(i)).append(",");
		}
		if(listJsonStr.toString().endsWith(","))
			return listJsonStr.substring(0, listJsonStr.length()-1)+"]";
		return "[]";
	}

	/**
	 * 将pojo对象转化为JSON格式的数据
	 * @param object  pojo对象
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private String objectToJsonStr(Object object) throws IllegalArgumentException, IllegalAccessException {
		if (object == null) {
			return "{}";
		}
		if (object.getClass().getClassLoader() != null) {
			StringBuilder objJsonStr =new StringBuilder("{");
			List<String> field_json_copy = new ArrayList<>();
			List<String> field_json = new ArrayList<>();
			Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields) {
				field_json_copy.add(fieldToJsonStr(object, field));
			}
			field_json_copy.stream().filter(a->a!=null).forEach(field_json::add);
			for (int i = 0; i < field_json.size(); i++) {
				objJsonStr.append(field_json.get(i)).append(",");
			}
			if(objJsonStr.toString().endsWith(","))
				return objJsonStr.substring(0, objJsonStr.length()-1)+"}";
			return "{}";
		} else {
			return "\"" + object + "\"";
		}

	}

	/**
	 * 将不为null的属性转变为json格式
	 * @param field_Obj  目标对象
	 * @param field 目标属性
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private String fieldToJsonStr(Object field_Obj, Field field)
			throws IllegalArgumentException, IllegalAccessException {
		
		StringBuilder fieldJsonStr = new StringBuilder();
		field.setAccessible(true);
		Object obj = field.get(field_Obj);
		if (obj != null) {
			fieldJsonStr.append(new LSON(field.getName()).getJsonStr()).append(":").append(new LSON(obj).getJsonStr());
			return fieldJsonStr.toString();
		}
		return null;
	}
}
