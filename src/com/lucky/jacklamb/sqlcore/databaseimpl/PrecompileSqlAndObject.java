package com.lucky.jacklamb.sqlcore.databaseimpl;

import java.util.ArrayList;
import java.util.List;

public class PrecompileSqlAndObject {
	
	private String precompileSql;
	
	private List<Object> objects;

	public String getPrecompileSql() {
		return precompileSql;
	}

	public void setPrecompileSql(String precompileSql) {
		this.precompileSql = precompileSql;
	}

	public List<Object> getObjects() {
		return objects;
	}

	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}
	
	public void addObjects(Object object) {
		if(objects==null)
			objects=new ArrayList<>();
		objects.add(object);
	}
	
	public void addAllObjects(List<Object> objects) {
		if(objects==null)
			objects=new ArrayList<>();
		this.objects.addAll(objects);
	}

}
