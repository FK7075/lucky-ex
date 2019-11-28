package com.lucky.jacklamb.sqlcore.databaseimpl;

import java.util.List;

import com.lucky.jacklamb.query.QueryBuilder;
import com.lucky.jacklamb.sqlcore.abstractionlayer.SqlCore;

public class DB2Core extends SqlCore{

	public DB2Core(String dbname) {
		super(dbname);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createJavaBean() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createJavaBean(String srcPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createJavaBean(String... tables) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createJavaBean(String srcPath, String... tables) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> List<T> getFuzzyList(Class<T> c, String property, String info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getPagList(T t, int index, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getSortList(T t, String property, int r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> query(QueryBuilder queryBuilder, Class<T> resultClass, String... expression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> boolean insert(T t, boolean... addId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertBatchByArray(boolean addId, Object... obj) {
		// TODO Auto-generated method stub
		return false;
	}


}
