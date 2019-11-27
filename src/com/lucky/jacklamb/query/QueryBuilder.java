package com.lucky.jacklamb.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.enums.JOIN;
import com.lucky.jacklamb.enums.Sort;
import com.lucky.jacklamb.sqlcore.PojoManage;

public class QueryBuilder {
	
	/**
	 * ���Ӳ��������ӷ�ʽ
	 */
	private JOIN join=JOIN.INNER_JOIN;
	
	/**
	 * �����Լ���������
	 */
	private List<ObjectAlias> objAndAlias;
	
	/**
	 * ��Ҫ���ص���
	 */
	private String result="";
	
	/**
	 * ��ҳ��Ϣ
	 */
	private String limit="";
	
	/**
	 * ������Ϣ
	 */
	private String sort="";
	

	/**
	 * ���ز�ѯ���������
	 * @return
	 */
	public Object[] getObjectArray() {
		List<Object> list=new ArrayList<>();
		objAndAlias.stream().forEach(oa->list.add(oa.getEntity()));
		return list.toArray();
	}
	
	/**
	 * ����Class��Class��Ӧ��������ɵ�Map
	 * @return
	 */
	public Map<Class<?>,String> getClassAliasMap(){
		Map<Class<?>,String> map=new HashMap<>();
		objAndAlias.stream().forEach(oa->map.put(oa.getEntity().getClass(), oa.getAlias()));
		return map;
	}

	public List<ObjectAlias> getObjAndAlias() {
		return objAndAlias;
	}

	public void setObjAndAlias(List<ObjectAlias> objAndAlias) {
		this.objAndAlias = objAndAlias;
	}
	
	/**
	 * ����Ҫ��ѯ��ʵ������Ϣ�Լ�����
	 * @param obj ��װ��ѯ��Ϣ��ʵ�������
	 * @param alias ����
	 * @return
	 */
	public QueryBuilder addObject(Object obj,String...alias) {
		if(objAndAlias==null)
			objAndAlias=new ArrayList<>();
		ObjectAlias oa=new ObjectAlias();
		oa.setEntity(obj);
		if(alias.length!=0&&!"".equals(alias[0]))
			oa.setAlias(alias[0]);
		else
			oa.setAlias(PojoManage.getTable(obj.getClass()));
		objAndAlias.add(oa);
		return this;
	}

	/**
	 * �õ���ѯָ����Ҫ���ص���
	 * @return
	 */
	public String getResult() {
		if("".equals(result))
			return result;
		return result.substring(0,result.length()-1);
	}

	/**
	 * ���ò�ѯָ��������
	 * @param result
	 * @return
	 */
	public QueryBuilder addResult(String column) {
		this.result+= column+",";
		return this;
	}

	public JOIN getJoin() {
		return join;
	}

	public void setJoin(JOIN join) {
		this.join = join;
	}

	
	public String getSort() {
		if("".equals(sort))
			return "";
		return sort.substring(0,sort.length()-1);
	}

	public QueryBuilder addSort(String field,Sort sortenum) {
		if("".equals(this.sort))
			this.sort+=" ORDER BY ";
		this.sort+=field+" "+sortenum.getSort()+",";
		return this;
	}

	public String getLimit() {
		return limit;
	}

	public void limit(int page,int rows) {
		this.limit +=" LIMIT "+((page-1)*rows)+","+rows;
	}




}
