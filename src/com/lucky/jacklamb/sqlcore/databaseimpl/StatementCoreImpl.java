package com.lucky.jacklamb.sqlcore.databaseimpl;

import java.util.List;

import com.lucky.jacklamb.cache.CreateSql;
import com.lucky.jacklamb.cache.LuckyCache;
import com.lucky.jacklamb.sqlcore.AutoPackage;
import com.lucky.jacklamb.sqlcore.DataSource;
import com.lucky.jacklamb.sqlcore.abstractionlayer.StatementCore;

@SuppressWarnings("unchecked")
public final class StatementCoreImpl implements StatementCore {
	
	private DataSource dataSource;
	
	private LuckyCache cache;
	
	private CreateSql createSql;
	
	protected AutoPackage autopackage;
	
	
	public StatementCoreImpl(DataSource dataSource) {
		this.createSql= new CreateSql();
		this.cache=LuckyCache.getLuckyCache();
		this.dataSource=dataSource;
		this.autopackage=new AutoPackage(dataSource.getName());
	}
	
	
	@Override
	public <T> List<T> getList(Class<T> c, String sql, Object... obj) {
		List<?> result=null;
		if(dataSource.isCache()) {
			if (!cache.contains(dataSource.getName(),createSql.getSqlString(sql, obj))) {
				result = autopackage.autoPackageToList(c, sql, obj);
				cache.add(dataSource.getName(),createSql.getSqlString(sql, obj), result);
			} else {
				result = cache.get(dataSource.getName(),createSql.getSqlString(sql, obj));
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
		if(dataSource.isCache())
			cache.empty(dataSource.getName());
		return autopackage.update(sql, obj);
	}

	@Override
	public boolean updateBatch(String sql, Object[][] obj) {
		if(dataSource.isCache())
			cache.empty(dataSource.getName());
		return autopackage.updateBatch(sql, obj);
	}

}
