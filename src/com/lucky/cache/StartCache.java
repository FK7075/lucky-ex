package com.lucky.cache;

import java.util.List;

import com.lucky.sqldao.AutoPackage;
import com.lucky.sqldao.ClassUtils;
import com.lucky.sqldao.PojoManage;
import com.lucky.sqldao.SqlInfo;
import com.lucky.sqldao.SqlOperation;
import com.lucky.utils.LuckyManager;
import com.lucky.utils.LuckyUtils;

@SuppressWarnings("all")
public class StartCache {
	private List<?> list = null;
	private SqlOperation sqlOperation =LuckyManager.getSqlOperation();
	private boolean isOk = false;
	private LuckyCache lucy = LuckyCache.getLuckyCache();
	private AutoPackage autopackage = new AutoPackage();
	private CreateSql createSql = new CreateSql();
	private ClassUtils classUtils = new ClassUtils();

	/**
	 * 启用缓存机制的ID查询
	 * @param c
	 * 包装类的Class
	 * @param id
	 * @return
	 */
	public Object getOneCache(Class c, Object id) {
		Object object = null;
		String id_ = PojoManage.getIdString(c);
		String sql = "SELECT * FROM " + PojoManage.getTable(c) + " WHERE " + id_ + "=?";
		if (lucy.get(createSql.getSqlString(sql, id)) == null) {
			list = autopackage.getTable(c, sql, id);
			lucy.add(createSql.getSqlString(sql, id), list);
		} else {
			list = lucy.get(createSql.getSqlString(sql, id));
		}
		if (!(list == null || list.size() == 0))
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
	public List<?> getListCache(Class c, String sql, Object... obj) {
		if (lucy.get(createSql.getSqlString(sql, obj)) == null) {
			list = autopackage.getTable(c, sql, obj);
			lucy.add(createSql.getSqlString(sql, obj), list);
		} else {
			list = lucy.get(createSql.getSqlString(sql, obj));
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
		if (lucy.get(createSql.getSqlString(f)) == null) {
			list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
			lucy.add(createSql.getSqlString(f), list);
		} else {
			list = lucy.get(createSql.getSqlString(f));
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
		if (lucy.get(createSql.getSqlString(f)) == null) {
			list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
			lucy.add(createSql.getSqlString(f), list);
		} else {
			list = lucy.get(createSql.getSqlString(f));
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
		if (lucy.get(createSql.getSqlString(f)) == null) {
			list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
			lucy.add(createSql.getSqlString(f), list);
		} else {
			list = lucy.get(createSql.getSqlString(f));
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
	public <T> List<?> getFuzzyListCache(Class c, String property, String info) {
		info = "%" + info + "%";
		SqlInfo f = classUtils.getSqlInfo(c, property, info);
		if (lucy.get(createSql.getSqlString(f)) == null) {
			list = autopackage.getTable(c, f.getSql(), f.getObj());
			lucy.add(createSql.getSqlString(f), list);
		} else {
			list = lucy.get(createSql.getSqlString(f));
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
	public boolean deleteCache(Class c, Object id) {
		String id_ = PojoManage.getIdString(c);
		String sql = "DELETE FROM " +  PojoManage.getTable(c) + " WHERE " + id_ + "=?";
		isOk = sqlOperation.setSql(sql, id);
		lucy.evenChange();
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
		lucy.evenChange();
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
		lucy.evenChange();
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
		lucy.evenChange();
		return isOk;
	}
	
	/**
	 * 启用缓存机制的添加数据
	 * @param t
	 * 包含添加信息的包装类的对象
	 * @return
	 */
	public <T> boolean saveCache(T t) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "insert");
		sqlOperation.setSql(f.getSql(), f.getObj());
		lucy.evenChange();
		LuckyUtils.pojoSetId(t);
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
		lucy.evenChange();
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
		lucy.evenChange();
		return true;
	}


	/**
	 * ID查询
	 * @param c
	 * 包装类的Class
	 * @param id
	 * @return
	 */
	public Object getOne(Class c, Object id) {
		Object object = null;
		String id_ =PojoManage.getIdString(c);
		String sql = "SELECT * FROM " + PojoManage.getTable(c)+ " WHERE " + id_ + "=?";
			list = autopackage.getTable(c, sql, id);
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
	public List<?> getList(Class c, String sql, Object... obj) {
		list = autopackage.getTable(c, sql, obj);
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
		list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
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
		list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
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
		list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
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
	public <T> List<?> getFuzzyList(Class c, String property, String info) {
		info = "%" + info + "%";
		SqlInfo f = classUtils.getSqlInfo(c, property, info);
		list = autopackage.getTable(c, f.getSql(), f.getObj());
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
	public boolean delete(Class c, Object id) {
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
	public <T> boolean save(T t) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "insert");
		sqlOperation.setSql(f.getSql(), f.getObj());
		LuckyUtils.pojoSetId(t);
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
	public Object getObject(Class c, String sql, Object... obj) {
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
	public Object getObjectCache(Class c, String sql, Object... obj) {
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
		lucy.evenChange();
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
		lucy.evenChange();
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
		lucy.evenChange();
		return isOk;
	}
	/**
	 * 批量添加(缓存模式)
	 * @param obj
	 * 封装着添加信息的对象数组
	 * @return
	 */
	public boolean saveBatchByArrayCache(Object...obj) {
		for(Object o:obj)
			saveCache(o);
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
	public boolean saveBatchByArray(Object... obj) {
		for(Object o:obj) {
			save(o);
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
	public boolean deleteBatchById(Class clzz,Object...ids) {
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
	public boolean deleteBatchByIdCache(Class clzz,Object...ids) {
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
		lucy.evenChange();
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
		lucy.empty();
	}
}
