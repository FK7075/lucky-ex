package com.lucky.jacklamb.sqldao;

import java.sql.SQLException;
import java.util.List;

import com.lucky.jacklamb.cache.StartCache;
import com.lucky.jacklamb.mapper.LuckyMapperProxy;
import com.lucky.jacklamb.query.ObjectToJoinSql;
import com.lucky.jacklamb.query.QueryBuilder;
import com.lucky.jacklamb.table.CreateTable;
import com.lucky.jacklamb.table.TableToJava;
import com.lucky.jacklamb.utils.LuckyManager;
import com.lucky.jacklamb.utils.ReadProperties;

/**
 * Lucky的用户使用类(操作数据库)
 * 
 * @author fk-7075
 *
 */
@SuppressWarnings("unchecked")
public class SqlControl implements SqlCore {

	private SqlOperation sqlOperation;
	private StartCache start;
	private boolean cache;
	private TableToJava tableToJava;
	private String dbname;

	private SqlControl(String dbname) {
		this.dbname=dbname;
		tableToJava=new TableToJava(dbname);
		cache = ReadProperties.getDataSource(dbname).isCache();
		sqlOperation = LuckyManager.getSqlOperation(dbname);
		start = new StartCache(dbname);
	}
	
	private SqlControl() {
		new SqlControl("defaultDB");
	}
	
	public static SqlControl getSqlControl(String...dbname) {
		if(dbname!=null&&dbname.length!=0)
			return new SqlControl(dbname[0]);
		return new SqlControl();
	}
	
	

	/**
	 * 数据库表逆向生成JavaBean
	 */
	public void createJavaBean() {
		tableToJava.generateJavaSrc();
	}

	/**
	 * 数据库表逆向生成JavaBean
	 * 
	 * @param srcPath
	 *            src目录的绝对路径
	 */
	public void createJavaBean(String srcPath) {
		tableToJava.generateJavaSrc(srcPath);
	}

	/**
	 * 数据库表逆向生成JavaBean
	 * 
	 * @param tables
	 *            表名
	 */
	public void createJavaBean(String... tables) {
		tableToJava.b_generateJavaSrc(tables);
	}
	
	/**
	 * 数据库表逆向生成JavaBean
	 * 
	 * @param srcPath
	 *            src目录的绝对路径
	 * @param tables
	 *            表名
	 */
	public void createJavaBean(String srcPath, String... tables) {
		tableToJava.a_generateJavaSrc(srcPath, tables);
	}

	/**
	 * 根据实体类创建数据库表
	 * @param name DataSourceName
	 */
	public void createTable() {
		CreateTable ct = new CreateTable(dbname);
		ct.creatTable();
	}

	/**
	 * ID查询
	 * 
	 * @param c
	 *            包装类的Class
	 * @param id
	 * @return
	 */
	public <T> T getOne(Class<T> c, Object id) {
		if (!cache) {
			return c.cast(start.getOne(c, id));
		} else
			return c.cast(start.getOneCache(c, id));
	}

	/**
	 * id删除(注解方式)
	 * 
	 * @param c
	 *            所操作类
	 * @param id
	 *            id值
	 * @return
	 */
	public boolean delete(Class<?> c, Object id) {
		if (!cache)
			return start.delete(c, id);
		else
			return start.deleteCache(c, id);
	}

	/**
	 * 
	 * @param c
	 *            包装类的Class
	 * @param sql
	 *            预编译的sql语句
	 * @param obj
	 * @return
	 */
	public <T> List<T> getList(Class<T> c, String sql, Object... obj) {
		if (!cache)
			return (List<T>) start.getList(c, sql, obj);
		else
			return (List<T>) start.getListCache(c, sql, obj);
	}

	/**
	 * 预编译语句删除
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean delete(String sql, Object... obj) {
		if (!cache)
			return start.delete(sql, obj);
		else
			return start.deleteCache(sql, obj);
	}

	/**
	 * 预编译语句修改
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean update(String sql, Object... obj) {
		if (!cache)
			return start.update(sql, obj);
		else
			return start.updateCache(sql, obj);
	}

	/**
	 * 预编译语句保存
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean save(String sql, Object... obj) {
		if (!cache)
			return start.save(sql, obj);
		else
			return start.saveCache(sql, obj);
	}

	/**
	 * 添加数据
	 * 
	 * @param t
	 *            包含添加信息的包装类的对象
	 * @return
	 */
	public <T> boolean save(T t, boolean... addId) {
		if (!cache)
			return start.save(t, addId);
		else
			return start.saveCache(t, addId);
	}

	/**
	 * 删除数据
	 * 
	 * @param t
	 *            包含删除信息的包装类的对象
	 * @return
	 */
	public <T> boolean delete(T t) {
		if (!cache)
			return start.delete(t);
		else
			return start.deleteCache(t);
	}

	/**
	 * 修改数据
	 * 
	 * @param t
	 *            包含修改信息的包装类的对象
	 * @return
	 */
	public <T> boolean update(T t) {
		if (!cache)
			return start.update(t);
		else
			return start.updateCache(t);
	}

	/**
	 * 查询数据
	 * 
	 * @param c
	 *            包含查询信息的包装类的对象
	 * @param t
	 *            对象
	 * @return
	 */
	public <T> List<T> getList(T t) {
		if (!cache)
			return (List<T>) start.getList(t);
		else
			return (List<T>) start.getListCache(t);
	}

	/**
	 * 分页查询
	 * 
	 * @param t
	 *            包含查询信息的包装类的对象
	 * @param index
	 *            第一条数据在表中的位置
	 * @param size
	 *            每页的记录数
	 * @return
	 */
	public <T> List<T> getPagList(T t, int index, int size) {
		if (!cache)
			return (List<T>) start.getPagList(t, index, size);
		else
			return (List<T>) start.getPagListCache(t, index, size);
	}

	/**
	 * 排序查询
	 * 
	 * @param t
	 *            包含查询信息的包装类的对象
	 * @param property
	 *            排序关键字
	 * @param r
	 *            排序方式（0-升序 1-降序）
	 * @return
	 */
	public <T> List<T> getSortList(T t, String property, int r) {
		if (!cache)
			return (List<T>) start.getSortList(t, property, r);
		else
			return (List<T>) start.getSortListCache(t, property, r);
	}

	/**
	 * 简单模糊查询
	 * 
	 * @param c
	 *            包装类的Class
	 * @param property
	 *            要查询的字段
	 * @param info
	 *            查询关键字
	 * @return
	 */
	public <T> List<T> getFuzzyList(Class<T> c, String property, String info) {
		if (!cache)
			return (List<T>) start.getFuzzyList(c, property, info);
		else
			return (List<T>) start.getFuzzyListCache(c, property, info);
	}

	/**
	 * 对象方式获得单个对象
	 * 
	 * @param t
	 * @return
	 */
	public <T> T getObject(T t) {
		if (!cache)
			return (T) start.getObject(t);
		else
			return (T) start.getObjectCache(t);
	}

	/**
	 * 预编译sql方式获得单一对象
	 * 
	 * @param c
	 * @param sql
	 * @param obj
	 * @return
	 */
	public <T> T getObject(Class<T> c, String sql, Object... obj) {
		if (!cache)
			return (T) start.getObject(c, sql, obj);
		else
			return (T) start.getObjectCache(c, sql, obj);
	}

	/**
	 * 批量保存-数组模式
	 * 
	 * @param obj
	 *            包含保存信息的对象数组
	 * @return
	 */
	public boolean saveArrayBatch(boolean addId, Object... obj) {
		if (!cache)
			return start.saveBatchByArray(addId, obj);
		else
			return start.saveBatchByArrayCache(addId, obj);
	}

	/**
	 * 批量删除-数组模式
	 * 
	 * @param obj
	 *            包含删除信息的对象数组
	 * @return
	 */
	public boolean deleteArrayBatch(Object... obj) {
		if (!cache)
			return start.deleteBatchByArray(obj);
		else
			return start.deleteBatchByArrayCache(obj);
	}

	/**
	 * 批量更新-数组模式
	 * 
	 * @param obj
	 *            包含更新信息的对象数组
	 * @return
	 */
	public boolean updateArrayBatch(Object... obj) {
		if (!cache)
			return start.updateBatchByArray(obj);
		else
			return start.updateBatchByArrayCache(obj);
	}

	/**
	 * 批量保存操作
	 * 
	 * @param sql
	 *            模板预编译SQL语句
	 * @param obj
	 *            填充占位符的一组组对象数组组成的二维数组 [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean saveBatch(String sql, Object[][] obj) {
		if (!cache)
			return start.saveBatch(sql, obj);
		else
			return start.saveBatchCache(sql, obj);
	}

	/**
	 * 批量更删除操作
	 * 
	 * @param sql
	 *            模板预编译SQL语句
	 * @param obj
	 *            填充占位符的一组组对象数组组成的二维数组 [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean deleteBatch(String sql, Object[][] obj) {
		if (!cache)
			return start.deleteBatch(sql, obj);
		else
			return start.deleteBatchCache(sql, obj);
	}

	/**
	 * 批量更新操作
	 * 
	 * @param sql
	 *            模板预编译SQL语句
	 * @param obj
	 *            填充占位符的一组组对象数组组成的二维数组 [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean updateBatch(String sql, Object[][] obj) {
		if (!cache)
			return start.updateBatch(sql, obj);
		else
			return start.updateBatchCache(sql, obj);
	}

	/**
	 * 批量ID删除
	 * 
	 * @param clzz
	 *            要操作表对应类的Class
	 * @param ids
	 *            要删除的id所组成的集合
	 * @return
	 */
	public boolean deleteIDBatch(Class<?> clzz, Object... ids) {
		if (!cache)
			return start.deleteBatchById(clzz, ids);
		else
			return start.deleteBatchByIdCache(clzz, ids);
	}

	/**
	 * 批量保存-集合模式
	 * 
	 * @param list
	 *            要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean saveListBatch(List<T> list) {
		if (!cache)
			return start.saveBatchByList(list);
		else
			return start.saveBatchByListCache(list);
	}

	/**
	 * 批量删除-集合模式
	 * 
	 * @param list
	 *            要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean deleteListBatch(List<T> list) {
		if (!cache)
			return start.deleteBatchByList(list);
		else
			return start.deleteBatchByListCache(list);
	}

	/**
	 * 批量更新-集合模式
	 * 
	 * @param list
	 *            要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean updateListBatch(List<T> list) {
		if (!cache)
			return start.updateBatchByList(list);
		else
			return start.updateBatchByListCache(list);
	}

	/**
	 * 开启Lucky的事务管理
	 * 
	 * @return
	 */
	public Transaction openTransaction() {
		Transaction tx = new Transaction();
		tx.setConn(sqlOperation.getConn());
		try {
			tx.getConn().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tx;
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		sqlOperation.close();
	}

	public <T> T getMapper(Class<T> clazz) {
		LuckyMapperProxy mapperProxy = new LuckyMapperProxy(this);
		Object obj = null;
		try {
			obj = mapperProxy.getMapperProxyObject(clazz);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (T) obj;
	}

	public <T> List<T> query(QueryBuilder query, Class<T> resultClass, String... expression) {
		ObjectToJoinSql join = new ObjectToJoinSql(query);
		String sql = join.getJoinSql(expression);
		Object[] obj = join.getJoinObject();
		return getList(resultClass, sql, obj);
	}

	@Override
	public void clear() {
		start.clear();
	}

}
