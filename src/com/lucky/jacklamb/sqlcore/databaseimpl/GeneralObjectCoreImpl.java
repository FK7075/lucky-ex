package com.lucky.jacklamb.sqlcore.databaseimpl;

import com.lucky.jacklamb.cache.BatchInsert;
import com.lucky.jacklamb.cache.CreateSql;
import com.lucky.jacklamb.cache.LuckyCache;
import com.lucky.jacklamb.sqlcore.AutoPackage;
import com.lucky.jacklamb.sqlcore.DataSource;
import com.lucky.jacklamb.sqlcore.abstractionlayer.GeneralObjectCore;
import com.lucky.jacklamb.sqlcore.sqlworkshop.GeneralSqlGenerator;

import java.util.List;

@SuppressWarnings("unchecked")
public final class GeneralObjectCoreImpl implements GeneralObjectCore {
	
	private LuckyCache cache;
	
	private CreateSql createSql;
	
	private AutoPackage autopackage;
	
	private GeneralSqlGenerator gcg;
	
	private String dbname;
	
	private boolean isCache;
	
	private List<?>  results;
	
	public GeneralObjectCoreImpl(DataSource dataSource) {
		gcg=new GeneralSqlGenerator();
		this.createSql= new CreateSql();
		this.cache=LuckyCache.getLuckyCache();
		this.dbname=dataSource.getName();
		this.isCache =dataSource.isCache();
		this.autopackage=new AutoPackage(dataSource.getName());
	}
	

	
	public <T> T getOne(Class<T> c, Object id) {
		String ysql = gcg.getOneSql(c);
		cache(c,ysql,id);
		if(results==null||results.isEmpty())
			return null;
		return (T) results.get(0);
	}

	public <T> T getObject(T t) {
		PrecompileSqlAndObject select = gcg.singleSelect(t);
		String ysql = select.getPrecompileSql();
		Object[] objects=select.getObjects().toArray();
		cache(t.getClass(),ysql,objects);
		if(results==null||results.isEmpty())
			return null;
		return (T) results.get(0);
	}

	public <T> List<T> getList(T t) {
		PrecompileSqlAndObject select = gcg.singleSelect(t);
		String ysql = select.getPrecompileSql();
		Object[] objects=select.getObjects().toArray();
		cache(t.getClass(),ysql,objects);
		return (List<T>) results;
	}

	public <T> int count(T t) {
		PrecompileSqlAndObject select = gcg.singleCount(t);
		String ysql = select.getPrecompileSql();
		Object[] objects=select.getObjects().toArray();
		cache(int.class,ysql,objects);
		if(results==null||results.isEmpty())
			return 0;
		return (int) results.get(0);
	}

	public <T> boolean delete(T t) {
		PrecompileSqlAndObject delete = gcg.singleDelete(t);
		if(isCache)
			cache.empty(dbname);
		return autopackage.update(delete.getPrecompileSql(), delete.getObjects().toArray());
	}

	public <T> boolean update(T t,String...conditions) {
		PrecompileSqlAndObject update = gcg.singleUpdate(t,conditions);
		if(isCache)
			cache.empty(dbname);
		return autopackage.update(update.getPrecompileSql(), update.getObjects().toArray());
	}

	public boolean deleteBatchByArray(Object... obj) {
		for(Object o:obj)
			delete(o);
		return true;
	}

	public boolean updateBatchByArray(Object... obj) {
		for(Object o:obj)
			update(o);
		return true;
	}

	public <T> boolean deleteBatchByList(List<T> list) {
		for(T o:list)
			delete(o);
		return true;
	}

	public <T> boolean insertBatchByList(List<T> list) {
		if(isCache)
			cache.empty(dbname);
		BatchInsert bbi=new BatchInsert(list);
		return autopackage.update(bbi.getInsertSql(), bbi.getInsertObject());
	}

	public <T> boolean updateBatchByList(List<T> list) {
		for(Object o:list)
			update(o);
		return true;
	}

	public boolean delete(Class<?> clazz, Object id) {
		String ysql = gcg.deleteOneSql(clazz);
		if(isCache)
			cache.empty(dbname);
		return autopackage.update(ysql, id);
	}

	public boolean deleteBatchByID(Class<?> clazz, Object... ids) {
		String ysql =gcg.deleteIn(clazz, ids);
		if(isCache)
			cache.empty(dbname);
		return autopackage.update(ysql, ids);
	}
	
	private void cache(Class<?> t,String ysql,Object...objects) {
		if(isCache) {
			String wsql=createSql.getSqlString(ysql, objects);
			if(cache.contains(dbname, wsql)) {
				results=cache.get(dbname, wsql);
			}else {
				results=autopackage.autoPackageToList(t, ysql, objects);
				cache.add(dbname, wsql, results);
			}
		}else {
			results=autopackage.autoPackageToList(t, ysql, objects);
		}
	}

}
