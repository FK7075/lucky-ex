package com.lucky.jacklamb.sqlcore.abstractionlayer.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.exception.NoDataSourceException;


/**
 * Lucky��ѯ���洦����(DL)
 * 
 * @author fk-7075
 *
 */
public class LuckyCache {
	private static Map<String,Map<String, List<?>>> cacheMap;

	private LuckyCache() {
		if(cacheMap==null)
			cacheMap = new HashMap<String,Map<String, List<?>>>();
	}

	public static LuckyCache getLuckyCache() {
		return new LuckyCache();
	}
	
	public boolean containsDbname(String dbname) {
		return cacheMap.containsKey(dbname);
	}
	
	public Map<String,List<?>> getMapByDbName(String dbname){
		if(containsDbname(dbname))
			return cacheMap.get(dbname);
		throw new NoDataSourceException("�Ҳ�������Ϊ"+dbname+"������Դ��");
	}
	
	/**
	 * �жϻ������Ƿ���ڸ�sql
	 * @param sql
	 * @return
	 */
	public boolean contains(String dbname,String sql) {
		return getMapByDbName(dbname).containsKey(sql);
	}

	/**
	 * ��ӵ�����
	 * @param key
	 * @param value
	 */
	public void add(String dbname,String key, List<?> value) {
		if (containsDbname(dbname)) {
			if(!cacheMap.get(dbname).containsKey(key)) {
				cacheMap.get(dbname).put(key, value);
			}
		}else {
			Map<String,List<?>> dbMap=new HashMap<>();
			dbMap.put(key, value);
			cacheMap.put(dbname, dbMap);
		}
	}

	/**
	 * �ӻ�����ֵ
	 * @param key
	 * @return
	 */
	public List<?> get(String dbname,String key) {
		if (contains(dbname,key)) {
			return cacheMap.get(dbname).get(key);
		} else {
			return null;
		}
	}
	
	/**
	 * �ǲ�ѯ����ʱɾ�������ж�Ӧ������ 
	 * @param name
	 */
	public void evenChange(String dbname) {
		empty(dbname);
	}
	
	/**
	 * ��ջ���
	 */
	public void empty(String dbname) {
		if(containsDbname(dbname))
			cacheMap.get(dbname).clear();
	}
}
