package com.lucky.jacklamb.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Lucky查询缓存处理类(DL)
 * 
 * @author fk-7075
 *
 */
public class LuckyCache {
	private static LuckyCache luckycache = null;
	private static Map<String, List<?>> cacheMap = null;

	private LuckyCache() {
		if(cacheMap==null)
			cacheMap = new HashMap<String, List<?>>();
	}

	public static LuckyCache getLuckyCache() {
		if (luckycache == null) {
			luckycache = new LuckyCache();
		}
		return luckycache;
	}
	
	/**
	 * 判断缓存中是否存在该sql
	 * @param sql
	 * @return
	 */
	public boolean contains(String sql) {
		return cacheMap.containsKey(sql);
	}

	/**
	 * 添加到缓存
	 * @param key
	 * @param value
	 */
	public void add(String key, List<?> value) {
		if (!cacheMap.containsKey(key)) {
			cacheMap.put(key, value);
		}
	}

	/**
	 * 从缓存拿值
	 * @param key
	 * @return
	 */
	public List<?> get(String key) {
		if (cacheMap.containsKey(key)) {
			return cacheMap.get(key);
		} else {
			return null;
		}
	}
	
	/**
	 * 获取缓存中的所有key值
	 * @return
	 */
	public List<String> getkeys(){
		List<String> keys=new ArrayList<String>();
		Iterator<String> iter = cacheMap.keySet().iterator();
		while(iter.hasNext()) {
			keys.add(iter.next());
		}
		return keys;
	}
	/**
	 * 从sql语句中提取关键字
	 * @param sql
	 * @param info
	 * @return
	 */
	public String getName(String sql,String info) {
		String name="";
		int start;
		int end;
		if("delete".equalsIgnoreCase(info)) {
			start=sql.indexOf("DELETE FROM")+12;
			end=sql.indexOf("WHERE")-1;
			name=sql.substring(start, end);
		}
		if("insert".equalsIgnoreCase(info)) {
			start=sql.indexOf("INSERT INTO")+12;
			end=sql.indexOf("(")-1;
			name=sql.substring(start, end);
		}
		if("update".equalsIgnoreCase(info)) {
			start=sql.indexOf("UPDATE")+7;
			end=sql.indexOf("SET")-1;
			name=sql.substring(start, end);
		}
		return name;
	}
	/**
	 * 非查询操作时删除缓存中对应的内容 
	 * @param name
	 */
	public void evenChange() {
		empty();
//		List<String> keys=getkeys();
//		for (String str : keys) {
//			if(haveOrNot(str, name)) {
//				cacheMap.remove(str);
//			}
//		}
	}
	/**
	 * 判断sql中是否含有name
	 * @param sql
	 * @param name
	 * @return
	 */
	public boolean haveOrNot(String sql,String name) {
		if(sql.indexOf(name)==-1) 
			return false;
		else
			return true;
	}
	/**
	 * 清空缓存
	 */
	public void empty() {
		cacheMap.clear();
	}
}
