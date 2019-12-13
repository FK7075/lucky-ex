package com.lucky.jacklamb.sqlcore.abstractionlayer.abstcore;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import com.lucky.jacklamb.enums.PrimaryType;
import com.lucky.jacklamb.mapper.LuckyMapperProxy;
import com.lucky.jacklamb.sqlcore.abstractionlayer.fixedcoreImpl.GeneralObjectCoreImpl;
import com.lucky.jacklamb.sqlcore.abstractionlayer.fixedcoreImpl.StatementCoreImpl;
import com.lucky.jacklamb.sqlcore.abstractionlayer.util.PojoManage;
import com.lucky.jacklamb.sqlcore.c3p0.DataSource;
import com.lucky.jacklamb.sqlcore.c3p0.ReadProperties;

/**
 * 对所有关系型数据库操作的抽象，本抽象类聚合对StatementCore接口和GeneralObjectCore接口的所有实现，
 * 对UniqueSqlCore接口的方法留给其子类去实现
 * @author fk-7075
 *
 */
public abstract class SqlCore implements UniqueSqlCore {
	
	protected String dbname;
	
	protected DataSource dataSource;

	protected StatementCore statementCore;
	
	protected GeneralObjectCore generalObjectCore;
	
	
	public SqlCore(String dbname) {
		this.dbname=dbname;
		this.dataSource=ReadProperties.getDataSource(dbname);
		this.statementCore=new StatementCoreImpl(dataSource);
		this.generalObjectCore=new GeneralObjectCoreImpl(this.statementCore);
	}
	
	/**
	 * ID查询
	 * @param pojoClass
	 * 包装类的Class
	 * @param id
	 * @return
	 */
	public <T> T getOne(Class<T> pojoClass, Object id) {
		return generalObjectCore.getOne(pojoClass, id);
	}
	
	/**
	 * 对象方式获得单个对象
	 * @param pojo
	 * @return
	 */
	public <T> T getObject(T pojo) {
		return generalObjectCore.getObject(pojo);
	}
	
	/**
	 * 对象查询
	 * @param pojo
	 * 对象
	 * @return
	 */
	public <T> List<T> getList(T pojo){
		return generalObjectCore.getList(pojo);
	}
	
	/**
	 * 统计总数
	 * @param pojo
	 * @return
	 */
	public <T> int count(T pojo) {
		return generalObjectCore.count(pojo);
	}
	
	
	/**
	 * 删除数据
	 * @param pojo
	 * 包含删除信息的包装类的对象
	 * @return
	 */
	public <T> boolean delete(T pojo) {
		return generalObjectCore.delete(pojo);
	}
	
	/**
	 * 跟新操作
	 * @param pojo 实体类对象
	 * @param conditions 作为更新条件的字段(支持多值，缺省默认使用Id字段作为更新条件)
	 * @return
	 */
	public <T> boolean updateByPojo(T pojo,String...conditions) {
		return generalObjectCore.update(pojo,conditions);
	}
	
	/**
	 * 批量删除-数组模式
	 * @param pojos
	 * 包含删除信息的对象数组
	 * @return
	 */
	public boolean deleteBatchByArray(Object...pojos) {
		return generalObjectCore.deleteBatchByArray(pojos);
	}
	
	
	/**
	 * 批量更新-数组模式
	 * @param pojos
	 * 包含更新信息的对象数组
	 * @return
	 */
	public boolean updateBatchByArray(Object...pojos) {
		return generalObjectCore.updateBatchByArray(pojos);
	}
	
	/**
	 * 批量删除-集合模式
	 * @param pojolist 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean deleteBatchByList(List<T> pojolist) {
		return generalObjectCore.deleteBatchByList(pojolist);
	}
	
	
	/**
	 * 批量更新-集合模式
	 * @param pojolist 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean updateBatchByList(List<T> pojolist) {
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
	public <T> List<T> getList(Class<T> pojoClass, String sql, Object... obj){
		return statementCore.getList(pojoClass, sql, obj);
	}
	
	/**
	 * 预编译SQL方式获得单一对象
	 * @param pojoClass
	 * @param sql
	 * @param obj
	 * @return
	 */
	public <T> T getObject(Class<T> pojoClass,String sql,Object...obj) {
		return statementCore.getObject(pojoClass, sql, obj);
	}
	
	/**
	 * 预编译SQL非查询操作
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean update(String sql,Object...obj) {
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
	public boolean delete(Class<?> pojoClass,Object id) {
		return generalObjectCore.delete(pojoClass, id);
	}
	
	/**
	 * 批量ID删除
	 * @param pojoClass 要操作表对应类的Class
	 * @param ids 要删除的id所组成的集合
	 * @return
	 */
	public boolean deleteBatchByID(Class<?> pojoClass,Object...ids) {
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
	public boolean updateBatch(String sql,Object[][] obj) {
		return statementCore.updateBatch(sql, obj);
	}
	
	/**
	 * 得到当前SqlCore对象对应的数据源的dbname
	 * @return
	 */
	public String getDbName() {
		return dataSource.getName();
	}
	
	/**
	 * 清空缓存
	 */
	public final void clear() {
		statementCore.clear();
		
	}

	/**
	 * Mapper接口式开发,返回该接口的代理对象
	 * @param clazz Mapper接口的Class
	 * @return Mapper接口的代理对象
	 */
	@SuppressWarnings("unchecked")
	public <T> T getMapper(Class<T> clazz) {
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

	@Override
	public <T> boolean insert(T t, boolean... addId) {
		generalObjectCore.insert(t);
		if(addId.length!=0&&addId[0]) {
			if(PojoManage.getIdType(t.getClass())==PrimaryType.AUTO_INT)
				setNextId(t);
			else if(PojoManage.getIdType(t.getClass())==PrimaryType.AUTO_UUID)
				setNextUUID(t);
		}
		return true;
	}

	@Override
	public boolean insertBatchByArray(boolean addId, Object... obj) {
		for(Object pojo:obj) {
			insert(pojo,addId);
		}
		return true;
	}
	
	public void setNextUUID(Object pojo) {
		Field idField=PojoManage.getIdField(pojo.getClass());
		idField.setAccessible(true);
		try {
			idField.set(pojo, UUID.randomUUID().toString().replaceAll("-", ""));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
