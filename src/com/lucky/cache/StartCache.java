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
	 * ���û�����Ƶ�ID��ѯ
	 * @param c
	 * ��װ���Class
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
	 * ���û����Ԥ����Sql��ѯ
	 * 
	 * @param c
	 *            ��װ���Class
	 * @param sql
	 *            Ԥ�����sql���
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
	 * ���û���Ķ����ѯ
	 * @param c ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param t ����
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
	 * ���û���ķ�ҳ��ѯ
	 * @param t
	 * ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param index
	 * ��һ�������ڱ��е�λ��
	 * @param size
	 * ÿҳ�ļ�¼��
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
	 * ���û���������ѯ
	 * @param t
	 *  ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param property
	 *  ����ؼ���
	 * @param r
	 * ����ʽ��0-���� 1-����
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
	 * ���û���ļ�ģ����ѯ
	 * @param c
	 * ��װ���Class
	 * @param property
	 * Ҫ��ѯ���ֶ�
	 * @param info
	 * ��ѯ�ؼ���
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
	 * ���û�����Ƶ�idɾ��(ע�ⷽʽ)
	 * @param c
	 * ��������
	 * @param id
	 * idֵ
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
	 * ���û�����Ƶ�Ԥ�������ɾ��
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
	 * ���û�����Ƶ�Ԥ��������޸�
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
	 * ���û�����Ƶ�Ԥ������䱣��
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
	 * ���û�����Ƶ��������
	 * @param t
	 * ���������Ϣ�İ�װ��Ķ���
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
	 * ���û�����Ƶ�ɾ������
	 * @param t
	 * ����ɾ����Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean deleteCache(T t) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "delete");
		sqlOperation.setSql(f.getSql(), f.getObj());
		lucy.evenChange();
		return true;
	}
	
	/**
	 * ���û�����Ƶ��޸�����
	 * @param t
	 * �����޸���Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean updateCache(T t) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "update");
		sqlOperation.setSql(f.getSql(), f.getObj());
		lucy.evenChange();
		return true;
	}


	/**
	 * ID��ѯ
	 * @param c
	 * ��װ���Class
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
	 * Ԥ����Sql��ѯ
	 * @param c
	 * ��װ���Class
	 * @param sql
	 * Ԥ�����sql���
	 * @param obj
	 * @return
	 */
	public List<?> getList(Class c, String sql, Object... obj) {
		list = autopackage.getTable(c, sql, obj);
		return list;
	}

	/**
	 * �����ѯ
	 * @param c ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param t ����
	 * @return
	 */
	public <T> List<?> getList(T t) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "select");
		list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
		return list;
	}

	/**
	 * ��ҳ��ѯ
	 * @param t
	 * ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param index
	 * ��һ�������ڱ��е�λ��
	 * @param size
	 *  ÿҳ�ļ�¼��
	 * @return
	 */
	public <T> List<?> getPagList(T t, int index, int size) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), index, size);
		list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
		return list;
	}

	/**
	 * �����ѯ
	 * @param t
	 * ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param property
	 * ����ؼ���
	 * @param r
	 * ����ʽ��0-���� 1-����
	 * @return
	 */
	public <T> List<?> getSortList(T t, String property, int r) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), property, r);
		list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
		return list;
	}

	/**
	 * ��ģ����ѯ
	 * @param c
	 * ��װ���Class
	 * @param property
	 * Ҫ��ѯ���ֶ�
	 * @param info
	 * ��ѯ�ؼ���
	 * @return
	 */
	public <T> List<?> getFuzzyList(Class c, String property, String info) {
		info = "%" + info + "%";
		SqlInfo f = classUtils.getSqlInfo(c, property, info);
		list = autopackage.getTable(c, f.getSql(), f.getObj());
		return list;
	}
	
	/**
	 * idɾ��(ע�ⷽʽ)
	 * @param c
	 * ��������
	 * @param id
	 * idֵ
	 * @return
	 */
	public boolean delete(Class c, Object id) {
		String id_ = PojoManage.getIdString(c);
		String sql = "DELETE FROM " + PojoManage.getTable(c) + " WHERE " + id_ + "=?";
		isOk = sqlOperation.setSql(sql, id);
		return isOk;
	}
	
	/**
	 * Ԥ�������ɾ��
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean delete(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		return isOk;
	}
	
	/**
	 * Ԥ��������޸�
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean update(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		return isOk;
	}
	
	/**
	 * Ԥ������䱣��
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean save(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		return isOk;
	}
	
	/**
	 * �������
	 * @param t
	 * ���������Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean save(T t) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "insert");
		sqlOperation.setSql(f.getSql(), f.getObj());
		LuckyUtils.pojoSetId(t);
		return true;
	}
	
	/**
	 * ɾ������
	 * @param t
	 * ����ɾ����Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean delete(T t) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "delete");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return true;
	}
	
	/**
	 * �޸�����
	 * @param t
	 *  �����޸���Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean update(T t) {
		SqlInfo f = classUtils.getSqlInfo(PojoManage.createClassInfo(t), "update");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return true;
	}
	
	/**
	 * ����ʽ��õ�������
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
	 * Ԥ���뷽ʽ��õ�������
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
	 * ����ʽ��õ������ݣ����棩
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
	 * Ԥ����sql��ʽ��õ������ݣ����棩
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
	 * ������Ӳ���(����ģʽ)
	 * @param sql
	 * ģ��Ԥ����SQL���
	 * @param obj
	 * ���ռλ����һ�������������ɵĶ�ά����
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean saveBatchCache(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		lucy.evenChange();
		return isOk;
	}
	/**
	 * ����ɾ������(����ģʽ)
	 * @param sql
	 * ģ��Ԥ����SQL���
	 * @param obj
	 * ���ռλ����һ�������������ɵĶ�ά����
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean deleteBatchCache(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		lucy.evenChange();
		return isOk;
	}
	/**
	 * �������²���(����ģʽ)
	 * @param sql
	 * ģ��Ԥ����SQL���
	 * @param obj
	 * ���ռλ����һ�������������ɵĶ�ά����
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean updateBatchCache(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		lucy.evenChange();
		return isOk;
	}
	/**
	 * �������(����ģʽ)
	 * @param obj
	 * ��װ�������Ϣ�Ķ�������
	 * @return
	 */
	public boolean saveBatchByArrayCache(Object...obj) {
		for(Object o:obj)
			saveCache(o);
		return true;
	}
	/**
	 * ����ɾ��(����ģʽ)
	 * @param obj
	 * ��װ��ɾ����Ϣ�Ķ�������
	 * @return
	 */
	public boolean deleteBatchByArrayCache(Object...obj) {
		for(Object o:obj)
			deleteCache(o);
		return true;
	}
	/**
	 * ��������(����ģʽ)
	 * @param obj
	 * ��װ�Ÿ�����Ϣ�Ķ�������
	 * @return
	 */
	public boolean updateBatchByArrayCache(Object...obj) {
		for(Object o:obj)
			updateCache(o);
		return true;
	}
	/**
	 * ������Ӳ���
	 * @param sql
	 * ģ��Ԥ����SQL���
	 * @param obj
	 * ���ռλ����һ�������������ɵĶ�ά����
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean saveBatch(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		return isOk;
	}
	/**
	 * ����ɾ������
	 * @param sql
	 * ģ��Ԥ����SQL���
	 * @param obj
	 * ���ռλ����һ�������������ɵĶ�ά����
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean deleteBatch(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		return isOk;
	}
	/**
	 * �������²���
	 * @param sql
	 * ģ��Ԥ����SQL���
	 * @param obj
	 * ���ռλ����һ�������������ɵĶ�ά����
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean updateBatch(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		return isOk;
	}
	/**
	 * ��������
	 * @param obj
	 * ��װ�ű�����Ϣ�Ķ�������
	 * @return
	 */
	public boolean saveBatchByArray(Object... obj) {
		for(Object o:obj) {
			save(o);
		}
		return true;
	}
	/**
	 * ��������
	 * @param obj
	 * ��װ�Ÿ�����Ϣ�Ķ�������
	 * @return
	 */
	public boolean updateBatchByArray(Object... obj) {
		for(Object o:obj) {
			update(o);
		}
		return true;
	}
	/**
	 * ����ɾ��
	 * @param obj
	 * ��װ��ɾ����Ϣ�Ķ�������
	 * @return
	 */
	public boolean deleteBatchByArray(Object... obj) {
		for(Object o:obj)
			delete(o);
		return true;
	}
	/**
	 * ����IDɾ��
	 * @param clzz
	 * Ҫ�������Ӧ���Class
	 * @param ids
	 * Ҫɾ����id����ɵļ���
	 * @return
	 */
	public boolean deleteBatchById(Class clzz,Object...ids) {
		for(Object i:ids)
			delete(clzz, i);
		return true;
	}
	/**
	 * ����IDɾ��(����ģʽ)
	 * @param clzz
	 * Ҫ�������Ӧ���Class
	 * @param ids
	 * Ҫɾ����id����ɵļ���
	 * @return
	 */
	public boolean deleteBatchByIdCache(Class clzz,Object...ids) {
		for(Object i:ids)
			deleteCache(clzz, i);
		return true;
	}
	/**
	 * �������
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean saveBatchByList(List<T> list) {
		BatchInsert bbi=new BatchInsert(list);
		isOk = sqlOperation.setSqlBatch(bbi.getInsertSql(), bbi.getInsertObject());
		return true;
	}
	/**
	 * ����ɾ��
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean deleteBatchByList(List<T> list) {
		for (T t : list) {
			delete(t);
		}
		return true;
	}
	/**
	 * ��������
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean updateBatchByList(List<T> list) {
		for (T t : list) {
			update(t);
		}
		return true;
	}
	/**
	 * �������(����ģʽ)
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean saveBatchByListCache(List<T> list){
		BatchInsert bbi=new BatchInsert(list);
		isOk = sqlOperation.setSqlBatch(bbi.getInsertSql(), bbi.getInsertObject());
		lucy.evenChange();
		return true;
	}
	/**
	 * ����ɾ��(����ģʽ)
	 * @param list Ҫ�����Ķ�������ɵļ���	
	 * @return false or true
	 */
	public <T> boolean deleteBatchByListCache(List<T> list) {
		for (T t : list) {
			deleteCache(t);
		}
		return true;
	}
	/**
	 * ��������(����ģʽ)
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean updateBatchByListCache(List<T> list) {
		for (T t : list) {
			updateCache(t);
		}
		return true;
	}

	/**
	 * ��ջ���
	 */
	public void clear() {
		lucy.empty();
	}
}
