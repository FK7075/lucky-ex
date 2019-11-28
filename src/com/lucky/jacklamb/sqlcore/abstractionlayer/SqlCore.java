package com.lucky.jacklamb.sqlcore.abstractionlayer;

import java.util.List;

import com.lucky.jacklamb.mapper.LuckyMapperProxy;
import com.lucky.jacklamb.sqlcore.DataSource;
import com.lucky.jacklamb.sqlcore.ReadProperties;
import com.lucky.jacklamb.sqlcore.databaseimpl.GeneralObjectCoreImpl;
import com.lucky.jacklamb.sqlcore.databaseimpl.StatementCoreImpl;

/**
 * �����й�ϵ�����ݿ�����ĳ��󣬱�������ۺ϶�StatementCore�ӿں�GeneralObjectCore�ӿڵ�����ʵ�֣�
 * ��UniqueSqlCore�ӿڵķ�������������ȥʵ��
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
	 * ID��ѯ
	 * @param pojoClass
	 * ��װ���Class
	 * @param id
	 * @return
	 */
	protected <T> T getOne(Class<T> pojoClass, Object id) {
		return generalObjectCore.getOne(pojoClass, id);
	}
	
	/**
	 * ����ʽ��õ�������
	 * @param pojo
	 * @return
	 */
	protected <T> T getObject(T pojo) {
		return generalObjectCore.getObject(pojo);
	}
	
	/**
	 * �����ѯ
	 * @param pojo
	 * ����
	 * @return
	 */
	protected <T> List<T> getList(T pojo){
		return generalObjectCore.getList(pojo);
	}
	
	/**
	 * ͳ������
	 * @param pojo
	 * @return
	 */
	protected <T> int count(T pojo) {
		return generalObjectCore.count(pojo);
	}
	
	
	/**
	 * ɾ������
	 * @param pojo
	 * ����ɾ����Ϣ�İ�װ��Ķ���
	 * @return
	 */
	protected <T> boolean delete(T pojo) {
		return generalObjectCore.delete(pojo);
	}
	
	/**
	 * �޸�����
	 * @param pojo
	 * �����޸���Ϣ�İ�װ��Ķ���
	 * @return
	 */
	protected <T> boolean update(T pojo) {
		return generalObjectCore.update(pojo);
	}
	
	/**
	 * ����ɾ��-����ģʽ
	 * @param pojos
	 * ����ɾ����Ϣ�Ķ�������
	 * @return
	 */
	protected boolean deleteBatchByArray(Object...pojos) {
		return generalObjectCore.deleteBatchByArray(pojos);
	}
	
	
	/**
	 * ��������-����ģʽ
	 * @param pojos
	 * ����������Ϣ�Ķ�������
	 * @return
	 */
	protected boolean updateBatchByArray(Object...pojos) {
		return generalObjectCore.updateBatchByArray(pojos);
	}
	
	/**
	 * ����ɾ��-����ģʽ
	 * @param pojolist Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	protected <T> boolean deleteBatchByList(List<T> pojolist) {
		return generalObjectCore.deleteBatchByList(pojolist);
	}
	
	/**
	 * ��������-����ģʽ
	 * @param pojolist Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	protected <T> boolean insertBatchByList(List<T> pojolist) {
		return generalObjectCore.insertBatchByList(pojolist);
	}
	
	/**
	 * ��������-����ģʽ
	 * @param pojolist Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	protected <T> boolean updateBatchByList(List<T> pojolist) {
		return generalObjectCore.updateBatchByList(pojolist);
	}
	
	/**
	 * SQL��ѯ
	 * @param pojoClass
	 * ��װ���Class
	 * @param sql
	 * Ԥ�����sql���
	 * @param obj
	 * @return
	 */
	protected <T> List<T> getList(Class<T> pojoClass, String sql, Object... obj){
		return statementCore.getList(pojoClass, sql, obj);
	}
	
	/**
	 * Ԥ����SQL��ʽ��õ�һ����
	 * @param pojoClass
	 * @param sql
	 * @param obj
	 * @return
	 */
	protected <T> T getObject(Class<T> pojoClass,String sql,Object...obj) {
		return statementCore.getObject(pojoClass, sql, obj);
	}
	
	/**
	 * Ԥ����SQL�ǲ�ѯ����
	 * @param sql
	 * @param obj
	 * @return
	 */
	protected boolean update(String sql,Object...obj) {
		return statementCore.update(sql, obj);
	}
	
	/**
	 * idɾ��
	 * @param pojoClass
	 * ��������
	 * @param id
	 * idֵ
	 * @return
	 */
	protected boolean delete(Class<?> pojoClass,Object id) {
		return generalObjectCore.delete(pojoClass, id);
	}
	
	/**
	 * ����IDɾ��
	 * @param pojoClass Ҫ�������Ӧ���Class
	 * @param ids Ҫɾ����id����ɵļ���
	 * @return
	 */
	protected boolean deleteBatchByID(Class<?> pojoClass,Object...ids) {
		return generalObjectCore.deleteBatchByID(pojoClass, ids);
	}
	
	
	/**
	 * ����SQL�ǲ�ѯ����
	 * @param sql
	 * ģ��Ԥ����SQL���
	 * @param obj
	 * ���ռλ����һ�������������ɵĶ�ά����
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	protected boolean updateBatch(String sql,Object[][] obj) {
		return statementCore.updateBatch(sql, obj);
	}
	
	/**
	 * �õ���ǰSqlCore�����Ӧ������Դ��dbname
	 * @return
	 */
	protected String getDbName() {
		return dataSource.getName();
	}
	
	/**
	 * Mapper�ӿ�ʽ����,���ظýӿڵĴ������
	 * @param clazz Mapper�ӿڵ�Class
	 * @return Mapper�ӿڵĴ������
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
