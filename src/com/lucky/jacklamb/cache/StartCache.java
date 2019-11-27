package com.lucky.jacklamb.cache;

import java.util.List;

import com.lucky.jacklamb.query.QFilter;
import com.lucky.jacklamb.sqlcore.AutoPackage;
import com.lucky.jacklamb.sqlcore.ClassUtils;
import com.lucky.jacklamb.sqlcore.PojoManage;
import com.lucky.jacklamb.sqlcore.SqlInfo;
import com.lucky.jacklamb.sqlcore.SqlOperation;
import com.lucky.jacklamb.utils.LuckyManager;
import com.lucky.jacklamb.utils.LuckyUtils;

public class StartCache {
	protected List<?> list;
	protected SqlOperation sqlOperation;
	protected boolean isOk;
	protected LuckyCache lucy;
	protected AutoPackage autopackage;
	protected CreateSql createSql;
	protected ClassUtils classUtils;
	protected String dbname;
	
	public  StartCache(String dbname) {
		this.dbname=dbname;
		list = null;
		isOk = false;
		createSql = new CreateSql();
		classUtils = new ClassUtils();
		autopackage = new AutoPackage(dbname);
		sqlOperation =LuckyManager.getSqlOperation(dbname);
		lucy = LuckyCache.getLuckyCache();
	}

	/**
	 * 启用缓存机制的ID查询
	 * @param c
	 * 包装类的Class
	 * @param id
	 * @return
	 */
	public Object getOneCache(Class<?> c, Object id) {
		QFilter qf=new QFilter(c);
		Object object = null;
		String id_ = PojoManage.getIdString(c);
		String sql = "SELECT * FROM " + PojoManage.getTable(c) + " WHERE " + id_ + "=?";
		sql=sql.replaceFirst("\\*", qf.lines());
		if (!lucy.contains(dbname,createSql.getSqlString(sql, id))) {
			list = autopackage.autoPackageToList(c, sql, id);
			lucy.add(dbname,createSql.getSqlString(sql, id), list);
		} else {
			list = lucy.get(dbname,createSql.getSqlString(sql, id));
		}
		if (!(list == null || list.isEmpty()))
			object = list.get(0);
		return object;
	}
	

	/**
	 * 启用缓存的预编译Sql查询
	 * 
	 * @param c
	 *            包装类的Class
	 * @param sql
	 *            预编译的sql语句
	 * @param obj
	 * @return
	 */
	public List<?> getListCache(Class<?> c, String sql, Object... obj) {
		if (lucy.get(dbname,createSql.getSqlString(sql, obj)) == null) {
			list = autopackage.autoPackageToList(c, sql, obj);
			lucy.add(dbname,createSql.getSqlString(sql, obj), list);
		} else {
			list = lucy.get(dbname,createSql.getSqlString(sql, obj));
		}
		return list;
	}

	/**
	 * 启用缓存的对象查询
	 * @param c 包含查询信息的包装类的对象
	 * @param t 对象
	 * @return
	 */
	public <T> List<?> getListCache(T t) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "select");
		if (lucy.get(dbname,createSql.getSqlString(f)) == null) {
			list = autopackage.autoPackageToList(t.getClass(), f.getSql(), f.getObj());
			lucy.add(dbname,createSql.getSqlString(f), list);
		} else {
			list = lucy.get(dbname,createSql.getSqlString(f));
		}
		return list;
	}

	/**
	 * 启用缓存的分页查询
	 * @param t
	 * 包含查询信息的包装类的对象
	 * @param index
	 * 第一条数据在表中的位置
	 * @param size
	 * 每页的记录数
	 * @return
	 */
	public <T> List<?> getPagListCache(T t, int index, int size) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), index, size);
		if (lucy.get(dbname,createSql.getSqlString(f)) == null) {
			list = autopackage.autoPackageToList(t.getClass(), f.getSql(), f.getObj());
			lucy.add(dbname,createSql.getSqlString(f), list);
		} else {
			list = lucy.get(dbname,createSql.getSqlString(f));
		}
		return list;
	}

	/**
	 * 启用缓存的排序查询
	 * @param t
	 *  包含查询信息的包装类的对象
	 * @param property
	 *  排序关键字
	 * @param r
	 * 排序方式（0-升序 1-降序）
	 * @return
	 */
	public <T> List<?> getSortListCache(T t, String property, int r) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), property, r);
		if (lucy.get(dbname,createSql.getSqlString(f)) == null) {
			list = autopackage.autoPackageToList(t.getClass(), f.getSql(), f.getObj());
			lucy.add(dbname,createSql.getSqlString(f), list);
		} else {
			list = lucy.get(dbname,createSql.getSqlString(f));
		}
		return list;
	}

	/**
	 * 启用缓存的简单模糊查询
	 * @param c
	 * 包装类的Class
	 * @param property
	 * 要查询的字段
	 * @param info
	 * 查询关键字
	 * @return
	 */
	public <T> List<?> getFuzzyListCache(Class<?> c, String property, String info) {
		info = "%" + info + "%";
		SqlInfo f = classUtils.getSqlInfo(c, property, info);
		if (lucy.get(dbname,createSql.getSqlString(f)) == null) {
			list = autopackage.autoPackageToList(c, f.getSql(), f.getObj());
			lucy.add(dbname,createSql.getSqlString(f), list);
		} else {
			list = lucy.get(dbname,createSql.getSqlString(f));
		}
		return list;
	}
	
	
	/**
	 * 启用缓存机制的id删除(注解方式)
	 * @param c
	 * 所操作类
	 * @param id
	 * id值
	 * @return
	 */
	public boolean deleteCache(Class<?> c, Object id) {
		String id_ = PojoManage.getIdString(c);
		String sql = "DELETE FROM " +  PojoManage.getTable(c) + " WHERE " + id_ + "=?";
		isOk = sqlOperation.setSql(sql, id);
		lucy.evenChange(dbname);
		return isOk;
	}
	
	/**
	 * 启用缓存机制的预编译语句删除
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean deleteCache(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		lucy.evenChange(dbname);
		return isOk;
	}
	
	/**
	 * 启用缓存机制的预编译语句修改
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean updateCache(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		lucy.evenChange(dbname);
		return isOk;
	}
	
	/**
	 * 启用缓存机制的预编译语句保存
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean saveCache(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		lucy.evenChange(dbname);
		return isOk;
	}
	
	/**
	 * 启用缓存机制的添加数据
	 * @param t
	 * 包含添加信息的包装类的对象
	 * @return
	 */
	public <T> boolean saveCache(T t,boolean...addId) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "insert");
		sqlOperation.setSql(f.getSql(), f.getObj());
		lucy.evenChange(dbname);
		if(addId!=null&&addId.length!=0&&addId[0])
			LuckyUtils.pojoSetId(dbname,t);
		return true;
	}
	
	/**
	 * 启用缓存机制的删除数据
	 * @param t
	 * 包含删除信息的包装类的对象
	 * @return
	 */
	public <T> boolean deleteCache(T t) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "delete");
		sqlOperation.setSql(f.getSql(), f.getObj());
		lucy.evenChange(dbname);
		return true;
	}
	
	/**
	 * 启用缓存机制的修改数据
	 * @param t
	 * 包含修改信息的包装类的对象
	 * @return
	 */
	public <T> boolean updateCache(T t) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "update");
		sqlOperation.setSql(f.getSql(), f.getObj());
		lucy.evenChange(dbname);
		return true;
	}


	/**
	 * ID查询
	 * @param c
	 * 包装类的Class
	 * @param id
	 * @return
	 */
	public Object getOne(Class<?> c, Object id) {
		Object object = null;
		String id_ =PojoManage.getIdString(c);
		String sql = "SELECT * FROM " + PojoManage.getTable(c)+ " WHERE " + id_ + "=?";
			list = autopackage.autoPackageToList(c, sql, id);
		if (!(list == null || list.size() == 0))
			object = list.get(0);
		return object;
	}

	/**
	 * 预编译Sql查询
	 * @param c
	 * 包装类的Class
	 * @param sql
	 * 预编译的sql语句
	 * @param obj
	 * @return
	 */
	public List<?> getList(Class<?> c, String sql, Object... obj) {
		list = autopackage.autoPackageToList(c, sql, obj);
		return list;
	}

	/**
	 * 对象查询
	 * @param c 包含查询信息的包装类的对象
	 * @param t 对象
	 * @return
	 */
	public <T> List<?> getList(T t) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "select");
		list = autopackage.autoPackageToList(t.getClass(), f.getSql(), f.getObj());
		return list;
	}

	/**
	 * 分页查询
	 * @param t
	 * 包含查询信息的包装类的对象
	 * @param index
	 * 第一条数据在表中的位置
	 * @param size
	 *  每页的记录数
	 * @return
	 */
	public <T> List<?> getPagList(T t, int index, int size) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), index, size);
		list = autopackage.autoPackageToList(t.getClass(), f.getSql(), f.getObj());
		return list;
	}

	/**
	 * 排序查询
	 * @param t
	 * 包含查询信息的包装类的对象
	 * @param property
	 * 排序关键字
	 * @param r
	 * 排序方式（0-升序 1-降序）
	 * @return
	 */
	public <T> List<?> getSortList(T t, String property, int r) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), property, r);
		list = autopackage.autoPackageToList(t.getClass(), f.getSql(), f.getObj());
		return list;
	}

	/**
	 * 简单模糊查询
	 * @param c
	 * 包装类的Class
	 * @param property
	 * 要查询的字段
	 * @param info
	 * 查询关键字
	 * @return
	 */
	public <T> List<?> getFuzzyList(Class<?> c, String property, String info) {
		info = "%" + info + "%";
		SqlInfo f = classUtils.getSqlInfo(c, property, info);
		list = autopackage.autoPackageToList(c, f.getSql(), f.getObj());
		return list;
	}
	
	/**
	 * id删除(注解方式)
	 * @param c
	 * 所操作类
	 * @param id
	 * id值
	 * @return
	 */
	public boolean delete(Class<?> c, Object id) {
		String id_ = PojoManage.getIdString(c);
		String sql = "DELETE FROM " + PojoManage.getTable(c) + " WHERE " + id_ + "=?";
		isOk = sqlOperation.setSql(sql, id);
		return isOk;
	}
	
	/**
	 * 预编译语句删除
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean delete(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		return isOk;
	}
	
	/**
	 * 预编译语句修改
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean update(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		return isOk;
	}
	
	/**
	 * 预编译语句保存
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean save(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		return isOk;
	}
	
	/**
	 * 添加数据
	 * @param t
	 * 包含添加信息的包装类的对象
	 * @return
	 */
	public <T> boolean save(T t,boolean...addId) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "insert");
		sqlOperation.setSql(f.getSql(), f.getObj());
		if(addId!=null&&addId.length!=0&&addId[0])
			LuckyUtils.pojoSetId(dbname,t);
		return true;
	}
	
	/**
	 * 删除数据
	 * @param t
	 * 包含删除信息的包装类的对象
	 * @return
	 */
	public <T> boolean delete(T t) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "delete");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return true;
	}
	
	/**
	 * 修改数据
	 * @param t
	 *  包含修改信息的包装类的对象
	 * @return
	 */
	public <T> boolean update(T t) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "update");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return true;
	}
	
	/**
	 * 对象方式获得单条数据
	 * @param t
	 * @return
	 */
	public <T> Object getObject(T t) {
		List<?> lis=getList(t);
		if(lis.isEmpty())
			return null;
		else
			return lis.get(0);
	}
	
	/**
	 * 预编译方式获得单条数据
	 * @param c
	 * @param sql
	 * @param obj
	 * @return
	 */
	public Object getObject(Class<?> c, String sql, Object... obj) {
		List<?> list=getList(c,sql,obj);
		if(!list.isEmpty())
			return list.get(0);
		else
			return null;
	}
	
	/**
	 * 对象方式获得单条数据（缓存）
	 * @param t
	 * @return
	 */
	public <T> Object getObjectCache(T t) {
		List<?> lis=getListCache(t);
		if(!lis.isEmpty())
			return lis.get(0);
		else
			return null;
	}
	
	/**
	 * 预编译sql方式获得单条数据（缓存）
	 * @param c
	 * @param sql
	 * @param obj
	 * @return
	 */
	public Object getObjectCache(Class<?> c, String sql, Object... obj) {
		List<?> lis=getListCache(c,sql,obj);
		if(!lis.isEmpty())
			return lis.get(0);
		else
			return null;
	}
	/**
	 * 批量添加操作(缓存模式)
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean saveBatchCache(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		lucy.evenChange(dbname);
		return isOk;
	}
	/**
	 * 批量删除操作(缓存模式)
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean deleteBatchCache(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		lucy.evenChange(dbname);
		return isOk;
	}
	/**
	 * 批量更新操作(缓存模式)
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean updateBatchCache(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		lucy.evenChange(dbname);
		return isOk;
	}
	/**
	 * 批量添加(缓存模式)
	 * @param obj
	 * 封装着添加信息的对象数组
	 * @return
	 */
	public boolean saveBatchByArrayCache(boolean addId,Object...obj) {
		for(Object o:obj)
			saveCache(o,addId);
		return true;
	}
	/**
	 * 批量删除(缓存模式)
	 * @param obj
	 * 封装着删除信息的对象数组
	 * @return
	 */
	public boolean deleteBatchByArrayCache(Object...obj) {
		for(Object o:obj)
			deleteCache(o);
		return true;
	}
	/**
	 * 批量更新(缓存模式)
	 * @param obj
	 * 封装着更新信息的对象数组
	 * @return
	 */
	public boolean updateBatchByArrayCache(Object...obj) {
		for(Object o:obj)
			updateCache(o);
		return true;
	}
	/**
	 * 批量添加操作
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean saveBatch(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		return isOk;
	}
	/**
	 * 批量删除操作
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean deleteBatch(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		return isOk;
	}
	/**
	 * 批量更新操作
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean updateBatch(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		return isOk;
	}
	/**
	 * 批量保存
	 * @param obj
	 * 封装着保存信息的对象数组
	 * @return
	 */
	public boolean saveBatchByArray(boolean addid,Object... obj) {
		for(Object o:obj) {
			save(o,addid);
		}
		return true;
	}
	/**
	 * 批量更新
	 * @param obj
	 * 封装着更新信息的对象数组
	 * @return
	 */
	public boolean updateBatchByArray(Object... obj) {
		for(Object o:obj) {
			update(o);
		}
		return true;
	}
	/**
	 * 批量删除
	 * @param obj
	 * 封装着删除信息的对象数组
	 * @return
	 */
	public boolean deleteBatchByArray(Object... obj) {
		for(Object o:obj)
			delete(o);
		return true;
	}
	/**
	 * 批量ID删除
	 * @param clzz
	 * 要操作表对应类的Class
	 * @param ids
	 * 要删除的id所组成的集合
	 * @return
	 */
	public boolean deleteBatchById(Class<?> clzz,Object...ids) {
		for(Object i:ids)
			delete(clzz, i);
		return true;
	}
	/**
	 * 批量ID删除(缓存模式)
	 * @param clzz
	 * 要操作表对应类的Class
	 * @param ids
	 * 要删除的id所组成的集合
	 * @return
	 */
	public boolean deleteBatchByIdCache(Class<?> clzz,Object...ids) {
		for(Object i:ids)
			deleteCache(clzz, i);
		return true;
	}
	/**
	 * 批量添加
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean saveBatchByList(List<T> list) {
		BatchInsert bbi=new BatchInsert(list);
		isOk = sqlOperation.setSqlBatch(bbi.getInsertSql(), bbi.getInsertObject());
		return true;
	}
	/**
	 * 批量删除
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean deleteBatchByList(List<T> list) {
		for (T t : list) {
			delete(t);
		}
		return true;
	}
	/**
	 * 批量更新
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean updateBatchByList(List<T> list) {
		for (T t : list) {
			update(t);
		}
		return true;
	}
	/**
	 * 批量添加(缓存模式)
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean saveBatchByListCache(List<T> list){
		BatchInsert bbi=new BatchInsert(list);
		isOk = sqlOperation.setSqlBatch(bbi.getInsertSql(), bbi.getInsertObject());
		lucy.evenChange(dbname);
		return true;
	}
	/**
	 * 批量删除(缓存模式)
	 * @param list 要操作的对象所组成的集合	
	 * @return false or true
	 */
	public <T> boolean deleteBatchByListCache(List<T> list) {
		for (T t : list) {
			deleteCache(t);
		}
		return true;
	}
	/**
	 * 批量更新(缓存模式)
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean updateBatchByListCache(List<T> list) {
		for (T t : list) {
			updateCache(t);
		}
		return true;
	}

	/**
	 * 清空缓存
	 */
	public void clear() {
		lucy.empty(dbname);
	}
}
