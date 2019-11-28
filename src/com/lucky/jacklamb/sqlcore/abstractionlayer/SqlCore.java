package com.lucky.jacklamb.sqlcore.abstractionlayer;

import java.util.List;

import com.lucky.jacklamb.mapper.LuckyMapperProxy;
import com.lucky.jacklamb.sqlcore.DataSource;
import com.lucky.jacklamb.sqlcore.ReadProperties;
import com.lucky.jacklamb.sqlcore.databaseimpl.GeneralObjectCoreImpl;
import com.lucky.jacklamb.sqlcore.databaseimpl.StatementCoreImpl;

/**
 * 对所有关系型数据库操作的抽象，本抽象类聚合对StatementCore接口和GeneralObjectCore接口的所有实现，
 * 对UniqueSqlCore接口的方法留给其子类去实现
 * @author fk-7075
 *
 */
public abstract class SqlCore implements UniqueSqlCore {
	
	protected DataSource dataSource;
	
	protected StatementCore statementCore;
	
	protected GeneralObjectCore generalObjectCore;

	
	protected SqlCore(String dbname) {
		this.dataSource=ReadProperties.getDataSource(dbname);
		this.statementCore=new StatementCoreImpl(dataSource);
		this.generalObjectCore=new GeneralObjectCoreImpl(dataSource);
	}
	
	/**
	 * ID查询
	 * @param pojoClass
	 * 包装类的Class
	 * @param id
	 * @return
	 */
	protected <T> T getOne(Class<T> pojoClass, Object id) {
		return generalObjectCore.getOne(pojoClass, id);
	}
	
	/**
	 * 对象方式获得单个对象
	 * @param pojo
	 * @return
	 */
	protected <T> T getObject(T pojo) {
		return generalObjectCore.getObject(pojo);
	}
	
	/**
	 * 对象查询
	 * @param pojo
	 * 对象
	 * @return
	 */
	protected <T> List<T> getList(T pojo){
		return generalObjectCore.getList(pojo);
	}
	
	/**
	 * 统计总数
	 * @param pojo
	 * @return
	 */
	protected <T> int count(T pojo) {
		return generalObjectCore.count(pojo);
	}
	
	
	/**
	 * 删除数据
	 * @param pojo
	 * 包含删除信息的包装类的对象
	 * @return
	 */
	protected <T> boolean delete(T pojo) {
		return generalObjectCore.delete(pojo);
	}
	
	/**
	 * 修改数据
	 * @param pojo
	 * 包含修改信息的包装类的对象
	 * @return
	 */
	protected <T> boolean update(T pojo) {
		return generalObjectCore.update(pojo);
	}
	
	/**
	 * 批量删除-数组模式
	 * @param pojos
	 * 包含删除信息的对象数组
	 * @return
	 */
	protected boolean deleteBatchByArray(Object...pojos) {
		return generalObjectCore.deleteBatchByArray(pojos);
	}
	
	
	/**
	 * 批量更新-数组模式
	 * @param pojos
	 * 包含更新信息的对象数组
	 * @return
	 */
	protected boolean updateBatchByArray(Object...pojos) {
		return generalObjectCore.updateBatchByArray(pojos);
	}
	
	/**
	 * 批量删除-集合模式
	 * @param pojolist 要操作的对象所组成的集合
	 * @return false or true
	 */
	protected <T> boolean deleteBatchByList(List<T> pojolist) {
		return generalObjectCore.deleteBatchByList(pojolist);
	}
	
	/**
	 * 批量保存-集合模式
	 * @param pojolist 要操作的对象所组成的集合
	 * @return false or true
	 */
	protected <T> boolean insertBatchByList(List<T> pojolist) {
		return generalObjectCore.insertBatchByList(pojolist);
	}
	
	/**
	 * 批量更新-集合模式
	 * @param pojolist 要操作的对象所组成的集合
	 * @return false or true
	 */
	protected <T> boolean updateBatchByList(List<T> pojolist) {
		return generalObjectCore.updateBatchByList(pojolist);
	}
	
	/**
	 * SQL查询
	 * @param pojoClass
	 * 包装类的Class
	 * @param sql
	 * 预编译的sql语句
	 * @param obj
	 * @return
	 */
	protected <T> List<T> getList(Class<T> pojoClass, String sql, Object... obj){
		return statementCore.getList(pojoClass, sql, obj);
	}
	
	/**
	 * 预编译SQL方式获得单一对象
	 * @param pojoClass
	 * @param sql
	 * @param obj
	 * @return
	 */
	protected <T> T getObject(Class<T> pojoClass,String sql,Object...obj) {
		return statementCore.getObject(pojoClass, sql, obj);
	}
	
	/**
	 * 预编译SQL非查询操作
	 * @param sql
	 * @param obj
	 * @return
	 */
	protected boolean update(String sql,Object...obj) {
		return statementCore.update(sql, obj);
	}
	
	/**
	 * id删除
	 * @param pojoClass
	 * 所操作类
	 * @param id
	 * id值
	 * @return
	 */
	protected boolean delete(Class<?> pojoClass,Object id) {
		return generalObjectCore.delete(pojoClass, id);
	}
	
	/**
	 * 批量ID删除
	 * @param pojoClass 要操作表对应类的Class
	 * @param ids 要删除的id所组成的集合
	 * @return
	 */
	protected boolean deleteBatchByID(Class<?> pojoClass,Object...ids) {
		return generalObjectCore.deleteBatchByID(pojoClass, ids);
	}
	
	
	/**
	 * 批量SQL非查询操作
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	protected boolean updateBatch(String sql,Object[][] obj) {
		return statementCore.updateBatch(sql, obj);
	}
	
	/**
	 * 得到当前SqlCore对象对应的数据源的dbname
	 * @return
	 */
	protected String getDbName() {
		return dataSource.getName();
	}
	
	/**
	 * Mapper接口式开发,返回该接口的代理对象
	 * @param clazz Mapper接口的Class
	 * @return Mapper接口的代理对象
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getMapper(Class<T> clazz) {
		LuckyMapperProxy mapperProxy = new LuckyMapperProxy(this);
		Object obj = null;
		try {
			obj = mapperProxy.getMapperProxyObject(clazz);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return (T) obj;
	}

}
