package com.lucky.join;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.enums.JoinWay;
import com.lucky.sqldao.PojoManage;

public class JoinQuery {
	
	private JoinWay join;
	
	private List<ObjectAlias> objAndAlias;
	
	private String result="";
	

	public Object[] getObjectArray() {
		List<Object> list=new ArrayList<>();
		objAndAlias.stream().forEach(oa->list.add(oa.getEntity()));
		return list.toArray();
	}
	
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
	
	public JoinQuery addObject(Object obj,String...alias) {
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

	public String getResult() {
		if("".equals(result))
			return result;
		return result.substring(0,result.length()-1);
	}

	public JoinQuery resultAppend(String result) {
		this.result+= result+",";
		return this;
	}

	public JoinWay getJoin() {
		return join;
	}

	public void setJoin(JoinWay join) {
		this.join = join;
	}




}
