package com.lucky.jacklamb.sqlcore.databaseimpl;

import java.util.List;

import com.lucky.jacklamb.cache.CreateSql;
import com.lucky.jacklamb.cache.LuckyCache;
import com.lucky.jacklamb.sqlcore.AutoPackage;
import com.lucky.jacklamb.sqlcore.DataSource;
import com.lucky.jacklamb.sqlcore.abstractionlayer.StatementCore;

@SuppressWarnings("unchecked")
public final class StatementCoreImpl implements StatementCore {
	
	private String dbname;
	
	private boolean isCache;
	
	private LuckyCache cache;
	
	private CreateSql createSql;
	
	protected AutoPackage autopackage;
	
	
	public StatementCoreImpl(DataSource dataSource) {
		this.createSql= new CreateSql();
		this.cache=LuckyCache.getLuckyCache();
		this.dbname=dataSource.getName();
		this.isCache=dataSource.isCache();
		this.autopackage=new AutoPackage(dbname);
	}
	
	
	@Override
	public <T> List<T> getList(Class<T> c, String sql, Object... obj) {
		List<?> result=null;
		if(isCache) {
			if (!cache.contains(dbname,createSql.getSqlString(sql, obj))) {
				result = autopackage.autoPackageToList(c, sql, obj);
				cache.add(dbname,createSql.getSqlString(sql, obj), result);
			} else {
				result = cache.get(dbname,createSql.getSqlString(sql, obj));
			}
			return (List<T>) result;
		}
		return (List<T>) autopackage.autoPackageToList(c, sql, obj);
	}

	@Override
	public <T> T getObject(Class<T> c, String sql, Object... obj) {
		List<T> list = getList(c,sql,obj);
		if(list==null||!list.isEmpty())
			return list.get(0);
		return null;
	}

	@Override
	public boolean update(String sql, Object... obj) {
		if(isCache)
			cache.empty(dbname);
		return autopackage.update(sql, obj);
	}

	@Override
	public boolean updateBatch(String sql, Object[][] obj) {
		if(isCache)
			cache.empty(dbname);
		return autopackage.updateBatch(sql, obj);
	}

}
