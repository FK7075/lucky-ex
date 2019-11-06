package com.lucky.sqldao;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lucky.annotation.Table;
import com.lucky.cache.StartCache;
import com.lucky.ioc.config.DataSource;
import com.lucky.mapper.LuckyMapperProxy;
import com.lucky.query.ObjectToJoinSql;
import com.lucky.query.QueryBuilder;
import com.lucky.table.CreateTable;
import com.lucky.table.TableToJava;
import com.lucky.utils.LuckyManager;

/**
 * Lucky���û�ʹ����(�������ݿ�)
 * 
 * @author fk-7075
 *
 */
@SuppressWarnings("all")
public class SqlControl implements SqlCore {
	
	private SqlOperation sqlOperation = LuckyManager.getSqlOperation();
	private StartCache start = new StartCache();
	private boolean cache = DataSource.getDataSource().isCache();

	
	public static SqlControl getSqlControl() {
		return new SqlControl();
	}
	
	public static SqlControl getSqlControlAddCJavaBean() {
		SqlControl sqlControl=new SqlControl();
		TableToJava.generateJavaSrc();
		return sqlControl;
	}
	
	public static SqlControl getSqlControlAddCJavaBean(String classPath) {
		return new SqlControl(classPath);
	}
	
	public static SqlControl getSqlControlAddCJavaBean(String classPath, String...tables) {
		return new SqlControl(classPath,tables);
	}
	
	public static SqlControl getSqlControlAddCJavaBean(String... tables) {
		return new SqlControl(tables);
	}

	public static SqlControl getSqlControl(boolean isCreateTable) {
		return new SqlControl(isCreateTable);
	}
	/**
	 * �޲ι���(�����𽨱�)
	 */
	private SqlControl() {
	};

	/**
	 * �Ƿ񽨱�
	 * @param table
	 */
	private SqlControl(boolean table) {
		if (table) {
			CreateTable ct = new CreateTable();
			ct.creatTable();
		}
	}

	/**
	 * �вι���(���򹤳�����JavaBean)
	 * @param srcPath srcĿ¼�ľ���·��
	 */
	private SqlControl(String srcPath) {
		TableToJava.generateJavaSrc(srcPath);
	}

	/**
	 * �вι���(���򹤳�����JavaBean)
	 * 
	 * @param tables
	 *            ����
	 */
	private SqlControl(String... tables) {
		TableToJava.b_generateJavaSrc(tables);
	}

	/**
	 * �вι���(���򹤳�����JavaBean)
	 * 
	 * @param srcPath
	 *            srcĿ¼�ľ���·��
	 * @param tables
	 *            ����
	 */
	private SqlControl(String srcPath, String... tables) {
		TableToJava.a_generateJavaSrc(srcPath, tables);
	}

	/**
	 * ID��ѯ
	 * 
	 * @param c
	 *            ��װ���Class
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
	 * idɾ��(ע�ⷽʽ)
	 * 
	 * @param c
	 *            ��������
	 * @param id
	 *            idֵ
	 * @return
	 */
	public boolean delete(Class c, Object id) {
		if (!cache)
			return start.delete(c, id);
		else
			return start.deleteCache(c, id);
	}

	/**
	 * 
	 * @param c
	 *            ��װ���Class
	 * @param sql
	 *            Ԥ�����sql���
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
	 * Ԥ�������ɾ��
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
	 * Ԥ��������޸�
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
	 * Ԥ������䱣��
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
	 * �������
	 * 
	 * @param t
	 *            ���������Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean save(T t,boolean...addId) {
		boolean isOk=false;
		if (!cache) 
			return start.save(t,addId);
		else 
			return start.saveCache(t,addId);
	}

	/**
	 * ɾ������
	 * 
	 * @param t
	 *            ����ɾ����Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean delete(T t) {
		if (!cache)
			return start.delete(t);
		else
			return start.deleteCache(t);
	}

	/**
	 * �޸�����
	 * 
	 * @param t
	 *            �����޸���Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean update(T t) {
		if (!cache)
			return start.update(t);
		else
			return start.updateCache(t);
	}

	/**
	 * ��ѯ����
	 * 
	 * @param c
	 *            ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param t
	 *            ����
	 * @return
	 */
	public <T> List<T> getList(T t) {
		if (!cache)
			return (List<T>) start.getList(t);
		else
			return (List<T>) start.getListCache(t);
	}

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param t
	 *            ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param index
	 *            ��һ�������ڱ��е�λ��
	 * @param size
	 *            ÿҳ�ļ�¼��
	 * @return
	 */
	public <T> List<T> getPagList(T t, int index, int size) {
		if (!cache)
			return (List<T>) start.getPagList(t, index, size);
		else
			return (List<T>) start.getPagListCache(t, index, size);
	}

	/**
	 * �����ѯ
	 * 
	 * @param t
	 *            ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param property
	 *            ����ؼ���
	 * @param r
	 *            ����ʽ��0-���� 1-����
	 * @return
	 */
	public <T> List<T> getSortList(T t, String property, int r) {
		if (!cache)
			return (List<T>) start.getSortList(t, property, r);
		else
			return (List<T>) start.getSortListCache(t, property, r);
	}

	/**
	 * ��ģ����ѯ
	 * 
	 * @param c
	 *            ��װ���Class
	 * @param property
	 *            Ҫ��ѯ���ֶ�
	 * @param info
	 *            ��ѯ�ؼ���
	 * @return
	 */
	public <T> List<T> getFuzzyList(Class<T> c, String property, String info) {
		if (!cache)
			return (List<T>) start.getFuzzyList(c, property, info);
		else
			return (List<T>) start.getFuzzyListCache(c, property, info);
	}

	/**
	 * ����ʽ��õ�������
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
	 * Ԥ����sql��ʽ��õ�һ����
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
	 * ��������-����ģʽ
	 * 
	 * @param obj
	 *            ����������Ϣ�Ķ�������
	 * @return
	 */
	public boolean saveArrayBatch(boolean addId,Object... obj) {
		if (!cache)
			return start.saveBatchByArray(addId,obj);
		else
			return start.saveBatchByArrayCache(addId,obj);
	}

	/**
	 * ����ɾ��-����ģʽ
	 * 
	 * @param obj
	 *            ����ɾ����Ϣ�Ķ�������
	 * @return
	 */
	public boolean deleteArrayBatch(Object... obj) {
		if (!cache)
			return start.deleteBatchByArray(obj);
		else
			return start.deleteBatchByArrayCache(obj);
	}

	/**
	 * ��������-����ģʽ
	 * 
	 * @param obj
	 *            ����������Ϣ�Ķ�������
	 * @return
	 */
	public boolean updateArrayBatch(Object... obj) {
		if (!cache)
			return start.updateBatchByArray(obj);
		else
			return start.updateBatchByArrayCache(obj);
	}

	/**
	 * �����������
	 * 
	 * @param sql
	 *            ģ��Ԥ����SQL���
	 * @param obj
	 *            ���ռλ����һ�������������ɵĶ�ά���� [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean saveBatch(String sql, Object[][] obj) {
		if (!cache)
			return start.saveBatch(sql, obj);
		else
			return start.saveBatchCache(sql, obj);
	}

	/**
	 * ������ɾ������
	 * 
	 * @param sql
	 *            ģ��Ԥ����SQL���
	 * @param obj
	 *            ���ռλ����һ�������������ɵĶ�ά���� [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean deleteBatch(String sql, Object[][] obj) {
		if (!cache)
			return start.deleteBatch(sql, obj);
		else
			return start.deleteBatchCache(sql, obj);
	}

	/**
	 * �������²���
	 * 
	 * @param sql
	 *            ģ��Ԥ����SQL���
	 * @param obj
	 *            ���ռλ����һ�������������ɵĶ�ά���� [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean updateBatch(String sql, Object[][] obj) {
		if (!cache)
			return start.updateBatch(sql, obj);
		else
			return start.updateBatchCache(sql, obj);
	}

	/**
	 * ����IDɾ��
	 * 
	 * @param clzz
	 *            Ҫ�������Ӧ���Class
	 * @param ids
	 *            Ҫɾ����id����ɵļ���
	 * @return
	 */
	public boolean deleteIDBatch(Class clzz, Object... ids) {
		if (!cache)
			return start.deleteBatchById(clzz, ids);
		else
			return start.deleteBatchByIdCache(clzz, ids);
	}

	/**
	 * ��������-����ģʽ
	 * 
	 * @param list
	 *            Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean saveListBatch(List<T> list) {
		if (!cache)
			return start.saveBatchByList(list);
		else
			return start.saveBatchByListCache(list);
	}

	/**
	 * ����ɾ��-����ģʽ
	 * 
	 * @param list
	 *            Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean deleteListBatch(List<T> list) {
		if (!cache)
			return start.deleteBatchByList(list);
		else
			return start.deleteBatchByListCache(list);
	}

	/**
	 * ��������-����ģʽ
	 * 
	 * @param list
	 *            Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean updateListBatch(List<T> list) {
		if (!cache)
			return start.updateBatchByList(list);
		else
			return start.updateBatchByListCache(list);
	}

	/**
	 * ����Lucky���������
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
	 * �ر���Դ
	 */
	public void close() {
		sqlOperation.close();
	}

	/**
	 * �������б�@Lucky��ǵ����Լ���
	 * 
	 * @param clzz
	 * @return
	 */
	private List<Field> lucky_F(Class<?> clzz) {
		List<Field> list = new ArrayList<>();
		Field[] fields = clzz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Table.class)) {
				list.add(field);
			}
		}
		return list;
	}


	public <T> T getMapper(Class<T> clazz) {
		LuckyMapperProxy mapperProxy = new LuckyMapperProxy(this);
		Object obj=null;
		try {
			obj= mapperProxy.getMapperProxyObject(clazz);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (T) obj;
	}

	public <T> List<T> query(QueryBuilder query,Class<T> resultClass,String...expression) {
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
