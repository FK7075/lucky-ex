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
	 * 连接操作的连接方式
	 */
	private JOIN join=JOIN.INNER_JOIN;
	
	/**
	 * 对象以及别名集合
	 */
	private List<ObjectAlias> objAndAlias;
	
	/**
	 * 需要返回的列
	 */
	private String result="";
	
	/**
	 * 分页信息
	 */
	private String limit="";
	
	/**
	 * 排序信息
	 */
	private String sort="";
	

	/**
	 * 返回查询对象的数组
	 * @return
	 */
	public Object[] getObjectArray() {
		List<Object> list=new ArrayList<>();
		objAndAlias.stream().forEach(oa->list.add(oa.getEntity()));
		return list.toArray();
	}
	
	/**
	 * 返回Class与Class对应别名所组成的Map
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
	 * 设置要查询的实体类信息以及别名
	 * @param obj 封装查询信息的实体类对象
	 * @param alias 别名
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
	 * 得到查询指定需要返回的列
	 * @return
	 */
	public String getResult() {
		if("".equals(result))
			return result;
		return result.substring(0,result.length()-1);
	}

	/**
	 * 设置查询指定返回列
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
