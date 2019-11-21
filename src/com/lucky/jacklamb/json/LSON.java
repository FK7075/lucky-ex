package com.lucky.jacklamb.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * pojo����תjson�ַ���
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
	 * ��pojoת��ΪJson�ַ�
	 * 
	 * @param jsonObject
	 */
	public LSON(Object jsonObject) {
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
				jsonStr = arrayToJsonStr(jsonObject);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (clzz.getClassLoader() != null) {
			try {
				jsonStr = objectToJsonStr(jsonObject);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

	}

	private String arrayToJsonStr(Object... objects) throws IllegalArgumentException, IllegalAccessException {
		if (objects == null || objects.length == 0) {
			return "[]";
		}
		String arrayJsonStr = "[";
		List<String> field_json_copy = new ArrayList<>();
		List<String> field_json = new ArrayList<>();
		for (Object objStr : objects) {
			field_json_copy.add(objectToJsonStr(objStr));
		}
		field_json_copy.stream().filter(str -> !"".equals(str)).forEach(field_json::add);
		for (int i = 0; i < field_json.size(); i++) {
			if (i == field_json.size() - 1) {
				arrayJsonStr += field_json.get(i) + "]";
			} else {
				arrayJsonStr += field_json.get(i) + ",";
			}
		}
		if("[".equals(arrayJsonStr))
			arrayJsonStr="[]";
		return arrayJsonStr;
	}

	/**
	 * ��List������ʽ��Pojoת��ΪJson��ʽ
	 * @param list pojo��ʽ������
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private <T> String collectionToJsonStr(Collection<T> list) throws IllegalArgumentException, IllegalAccessException {
		if (list.isEmpty() || list == null) {
			return "[]";
		}
		String listJsonStr = "[";
		List<String> field_json_copy = new ArrayList<>();
		List<String> field_json = new ArrayList<>();
		for (T objStr : list) {
			field_json_copy.add(objectToJsonStr(objStr));
		}
		field_json_copy.stream().filter(str -> !"".equals(str)).forEach(field_json::add);
		for (int i = 0; i < field_json.size(); i++) {
			if (i == field_json.size() - 1) {
				listJsonStr += field_json.get(i) + "]";
			} else {
				listJsonStr += field_json.get(i) + ",";
			}
		}
		if("[".equals(listJsonStr))
			listJsonStr="[]";
		return listJsonStr;
	}

	/**
	 * ��pojo����ת��ΪJSON��ʽ������
	 * @param object  pojo����
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private String objectToJsonStr(Object object) throws IllegalArgumentException, IllegalAccessException {
		if (object == null) {
			return "{}";
		}
		if (object.getClass().getClassLoader() != null) {
			String objJsonStr = "{";
			List<String> field_json_copy = new ArrayList<>();
			List<String> field_json = new ArrayList<>();
			Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields) {
				field_json_copy.add(fieldToJsonStr(object, field));
			}
			field_json_copy.stream().filter(str -> !"".equals(str)).forEach(field_json::add);
			for (int i = 0; i < field_json.size(); i++) {
				if (i == field_json.size() - 1) {
					objJsonStr += field_json.get(i) + "}";
				} else {
					objJsonStr += field_json.get(i) + ",";
				}
			}
			if("{".equals(objJsonStr))
				objJsonStr="{}";
			return objJsonStr;
		} else {
			String objJsonStr = "\"" + object + "\"";
			return objJsonStr;
		}

	}

	/**
	 * ����Ϊnull������ת��Ϊjson��ʽ
	 * 
	 * @param field_Obj
	 *            Ŀ�����
	 * @param field
	 *            Ŀ������
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private String fieldToJsonStr(Object field_Obj, Field field)
			throws IllegalArgumentException, IllegalAccessException {
		String fieldJsonStr = "";
		field.setAccessible(true);
		Object obj = field.get(field_Obj);
		if (obj != null) {
			Class<?> clzz = field.getType();
			if (clzz.getClassLoader() == null && !clzz.isAssignableFrom(List.class) && !clzz.isArray()) {
				fieldJsonStr += "\"" + field.getName() + "\"" + ":\"" + obj + "\"";
			} else if (clzz.isArray()) {
				fieldJsonStr += "\"" + field.getName() + "\"" + ":" + arrayToJsonStr((Object[]) obj);
			} else if (Collection.class.isAssignableFrom(clzz)) {
				fieldJsonStr += "\"" + field.getName() + "\"" + ":" + collectionToJsonStr((Collection<?>) obj);
			} else if (clzz.getClassLoader() != null) {
				fieldJsonStr += "\"" + field.getName() + "\"" + ":" + objectToJsonStr(obj);
			}
		}
		return fieldJsonStr;
	}
}
