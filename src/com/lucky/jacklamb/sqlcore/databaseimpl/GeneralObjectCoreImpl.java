package com.lucky.jacklamb.sqlcore.databaseimpl;

import java.util.List;

import com.lucky.jacklamb.cache.CreateSql;
import com.lucky.jacklamb.cache.LuckyCache;
import com.lucky.jacklamb.sqlcore.AutoPackage;
import com.lucky.jacklamb.sqlcore.DataSource;
import com.lucky.jacklamb.sqlcore.abstractionlayer.GeneralObjectCore;

public final class GeneralObjectCoreImpl implements GeneralObjectCore {
	
	private DataSource dataSource;
	
	private LuckyCache cache;
	
	private CreateSql createSql;
	
	private AutoPackage autopackage;
	
	private GeneralSqlGenerator gcg;
	
	private String dbname;
	
	private boolean isCache;
	
	public GeneralObjectCoreImpl(DataSource dataSource) {
		gcg=new GeneralSqlGenerator();
		this.createSql= new CreateSql();
		this.cache=LuckyCache.getLuckyCache();
		this.dataSource=dataSource;
		this.dbname=dataSource.getName();
		this.isCache =dataSource.isCache();
		this.autopackage=new AutoPackage(dataSource.getName());
	}
	

	@Override
	public <T> T getOne(Class<T> c, Object id) {
		String ysql=gcg.getOneSql(c);
		String wsql=createSql.getSqlString(ysql, id);
		if(isCache) {
			if(cache.contains(dbname, wsql)) {
				 List<?> list = cache.get(dbname, wsql);
			}else {
				
			}
		}
		return null;
	}

	@Override
	public <T> T getObject(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getList(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> int count(T t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> boolean delete(T t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> boolean update(T t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteBatchByArray(Object... obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateBatchByArray(Object... obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> boolean deleteBatchByList(List<T> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> boolean insertBatchByList(List<T> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> boolean updateBatchByList(List<T> list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Class<?> clazz, Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteBatchByID(Class<?> clazz, Object... ids) {
		// TODO Auto-generated method stub
		return false;
	}

}
