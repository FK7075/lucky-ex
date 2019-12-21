package com.lucky.jacklamb.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lucky.jacklamb.utils.LuckyUtils;

public class LXML {
	
	private String xmlStr;

	public String getXmlStr() {
		return xmlStr;
	}
	
	/**
	 * ��pojoת��ΪXML�ַ�
	 * 
	 * @param pojoObject
	 */
	@SuppressWarnings("unchecked")
	public LXML(Object pojo) {
		if(pojo==null)
			xmlStr="<null/>";
		else {
			Class<?> clzz = pojo.getClass();
			if (Collection.class.isAssignableFrom(clzz)) {
				try {
					xmlStr = collectionToxmlStr((Collection<?>) pojo);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else if (clzz.isArray()) {
				try {
					xmlStr = arrayToxmlStr((Object[])pojo);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}else if(Map.class.isAssignableFrom(clzz)){
				xmlStr=mapToxmlStr((Map<Object,Object>)pojo);
			} else if (clzz.getClassLoader() != null) {
				try {
					xmlStr = objectToxmlStr(pojo);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}else {
				xmlStr=pojo.toString();
			}
		}

	}
	
	private String mapToxmlStr(Map<Object,Object> map) {
		if(map==null||map.isEmpty()) {
			return "<map/>";
		}
		StringBuilder str=new StringBuilder("<map>");
		for(Entry<Object,Object> entry:map.entrySet()) {
			str.append("<").append(entry.getKey()).append(">").append(new LXML(entry.getValue()).getXmlStr()).append("</").append(entry.getKey()).append(">");
		}
		return str.toString()+"</map>";
	}

	private String arrayToxmlStr(Object[] objects) throws IllegalArgumentException, IllegalAccessException {
		if (objects == null || objects.length == 0) {
			return "<array/>";
		}
		StringBuilder arrayxmlStr =new StringBuilder("<array>");
		List<String> field_json_copy = new ArrayList<>();
		List<String> field_json = new ArrayList<>();
		for (Object objStr : objects) {
			field_json_copy.add(objectToxmlStr(objStr));
		}
		field_json_copy.stream().filter(str -> !"".equals(str)).forEach(field_json::add);
		for (int i = 0; i < field_json.size(); i++) {
			arrayxmlStr.append("<element>").append(field_json.get(i)).append("</element>");
		}
		return arrayxmlStr.toString()+"</array>";
	}

	/**
	 * ��List������ʽ��Pojoת��ΪJson��ʽ
	 * @param list pojo��ʽ������
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private <T> String collectionToxmlStr(Collection<T> list) throws IllegalArgumentException, IllegalAccessException {
		if (list.isEmpty() || list == null) {
			return "<collection/>";
		}
		StringBuilder listxmlStr=new StringBuilder("<collection>");
		List<String> field_json_copy = new ArrayList<>();
		List<String> field_json = new ArrayList<>();
		for (T objStr : list) {
			field_json_copy.add(objectToxmlStr(objStr));
		}
		field_json_copy.stream().filter(str -> !"".equals(str)).forEach(field_json::add);
		for (int i = 0; i < field_json.size(); i++) {
			listxmlStr.append("<element>").append(field_json.get(i)).append("</element>");
		}
		return listxmlStr.toString()+"</collection>";
	}

	/**
	 * ��pojo����ת��ΪJSON��ʽ������
	 * @param object  pojo����
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private String objectToxmlStr(Object object) throws IllegalArgumentException, IllegalAccessException {
		if (object == null) {
			return "";
		}
		if (object.getClass().getClassLoader() != null) {
			String className=LuckyUtils.TableToClass1(object.getClass().getSimpleName());
			StringBuilder objxmlStr =new StringBuilder("<").append(className).append(">");
			List<String> field_json_copy = new ArrayList<>();
			List<String> field_json = new ArrayList<>();
			Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields) {
				field_json_copy.add(fieldToxmlStr(object, field));
			}
			field_json_copy.stream().filter(str -> !"".equals(str)).forEach(field_json::add);
			for (int i = 0; i < field_json.size(); i++) {
				objxmlStr.append(field_json.get(i));
			}
			return objxmlStr.toString()+"</"+className+">";
		} else {
			return object.toString();
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
	private String fieldToxmlStr(Object field_Obj, Field field)
			throws IllegalArgumentException, IllegalAccessException {
		StringBuilder fieldxmlStr = new StringBuilder();
		field.setAccessible(true);
		Object obj = field.get(field_Obj);
		Class<?> fieldClass=field.getType();
		if (obj != null) {
			String fieldXmlStr=new LXML(obj).getXmlStr();
			if(fieldClass.isArray()&&!"<array/>".equals(fieldXmlStr)&&!"<array></array>".equals(fieldXmlStr)) {
				fieldXmlStr=fieldXmlStr.replaceAll("<array>", "<"+field.getName()+">").replaceAll("</array>", "</"+field.getName()+">");
				fieldxmlStr.append(fieldXmlStr);
				return fieldxmlStr.toString();
			}
			if(Collection.class.isAssignableFrom(fieldClass)&&!"<collection/>".equals(fieldXmlStr)&&!"<collection></collection>".equals(fieldXmlStr)) {
				fieldXmlStr=fieldXmlStr.replaceAll("<collection>", "<"+field.getName()+">").replaceAll("</collection>", "</"+field.getName()+">");
				fieldxmlStr.append(fieldXmlStr);
				return fieldxmlStr.toString();
			}
			if(Map.class.isAssignableFrom(fieldClass)&&!"<map/>".equals(fieldXmlStr)&&!"<map></map>".equals(fieldXmlStr)) {
				fieldXmlStr=fieldXmlStr.replaceAll("<map>", "<"+field.getName()+">").replaceAll("</map>", "</"+field.getName()+">");
				fieldxmlStr.append(fieldXmlStr);
				return fieldxmlStr.toString();
			}
			return "<"+field.getName()+">"+fieldXmlStr+"</"+field.getName()+">";
		}
		return fieldxmlStr.toString();
	}
	
	public static void main(String[] args) {
		TT object=new TT();
		object.setStr("String����");
		List<Double> list=new ArrayList<>();
		list.add(12.5);list.add(55.7);list.add(99.999);
		object.setList(list);
		Map<String,Integer> map=new HashMap<>();
		map.put("key1", 111);
		map.put("key2", 222);
		object.setMap(map);
		BB bb=new BB();
		bb.setBname("BNAME");
		String[] arr= {"OK","YES","HELLO"};
		bb.setArray(arr);
		object.setBb(bb);
		List<BB> list_bb=new ArrayList<>();
		list_bb.add(bb);list_bb.add(new BB("BB2"));
		object.setList_BB(list_bb);
		Map<String,BB> map_bb=new HashMap<>();
		map_bb.put("map1", bb);
		map_bb.put("map2", bb);
		map_bb.put("map3", new BB("MAPBB"));
		object.setMap_BB(map_bb);
		LXML l=new LXML(object);
		System.out.println(l.getXmlStr());
		
	}

}

class TT{
	
	private String str;
	private List<Double> list;
	private Map<String,BB> map_BB;
	private List<BB> list_BB;
	private Map<String,Integer> map;
	private BB bb;
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public List<Double> getList() {
		return list;
	}
	public void setList(List<Double> list) {
		this.list = list;
	}
	public Map<String, BB> getMap_BB() {
		return map_BB;
	}
	public void setMap_BB(Map<String, BB> map_BB) {
		this.map_BB = map_BB;
	}
	public List<BB> getList_BB() {
		return list_BB;
	}
	public void setList_BB(List<BB> list_BB) {
		this.list_BB = list_BB;
	}
	public Map<String, Integer> getMap() {
		return map;
	}
	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}
	public BB getBb() {
		return bb;
	}
	public void setBb(BB bb) {
		this.bb = bb;
	}
	
	

	
}

class BB{
	
	public BB() {
	}
	public BB(String bname) {
		this.bname = bname;
	}
	private String bname;
	private String[] array;
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String[] getArray() {
		return array;
	}
	public void setArray(String[] array) {
		this.array = array;
	}
	
	
}

