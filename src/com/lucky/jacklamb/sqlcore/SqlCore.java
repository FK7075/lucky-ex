package com.lucky.jacklamb.sqlcore;

import java.util.List;

import com.lucky.jacklamb.query.QueryBuilder;
import com.lucky.jacklamb.sqlcore.c3p0.Transaction;

public interface SqlCore {
	/**
	 * ��������
	 * @return
	 */
	public Transaction openTransaction();
	
	/**
	 * �õ���ǰSqlCore�����Ӧ������Դ��dbname
	 * @return
	 */
	public String getDbName();
	
	/**
	 * ���򹤳�����JavaBean,��Ҫ�������ļ�������classpath(src)�ľ���·�������ڰ���·��
	 */
	public void createJavaBean();
	
	/**
	 * ���򹤳�����JavaBean,��Ҫ�������ļ����������ڰ���·��
	 * @param srcPath classpath(src)�ľ���·��
	 */
	public void createJavaBean(String srcPath);
	
	/**
	 * ���򹤳�����JavaBean,��Ҫ�������ļ�������classpath(src)�ľ���·�������ڰ���·��
	 * @param tables ָ����Ҫ����JavaBean�ı���
	 */
	public void createJavaBean(String... tables);
	
	/**
	 * ���򹤳�����JavaBean,��Ҫ�������ļ����������ڰ���·��
	 * @param srcPath classpath(src)�ľ���·��
	 * @param tables ָ����Ҫ����JavaBean�ı���
	 */
	public void createJavaBean(String srcPath, String... tables);
	
	/**
	 * �����Զ�������ƽ�����Ҫ�������ļ���������Ҫ�����ʵ����İ�·��
	 */
	public void createTable();
	
	/**
	 * �ر���Դ
	 */
	public void close();
	
	/**
	 * idɾ��
	 * @param clazz
	 * ��������
	 * @param id
	 * idֵ
	 * @return
	 */
	public boolean delete(Class<?> clazz,Object id);
	
	/**
	 * Ԥ�������ɾ��
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean delete(String sql,Object...obj);
	
	/**
	 * ɾ������
	 * @param t
	 * ����ɾ����Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean delete(T t);
	
	/**
	 * ����ɾ��-����ģʽ
	 * @param obj
	 * ����ɾ����Ϣ�Ķ�������
	 * @return
	 */
	public boolean deleteArrayBatch(Object...obj);
	
	/**
	 * ������ɾ������
	 * @param sql
	 * ģ��Ԥ����SQL���
	 * @param obj
	 * ���ռλ����һ�������������ɵĶ�ά����
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean deleteBatch(String sql,Object[][] obj);
	
	
	/**
	 * ����IDɾ��
	 * @param clzz Ҫ�������Ӧ���Class
	 * @param ids Ҫɾ����id����ɵļ���
	 * @return
	 */
	public boolean deleteIDBatch(Class<?> clazz,Object...ids);
	
	/**
	 * ����ɾ��-����ģʽ
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean deleteListBatch(List<T> list);
	
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
	public <T> List<T> getFuzzyList(Class<T> c, String property, String info);
	
	/**
	 * SQL��ѯ
	 * @param c
	 * ��װ���Class
	 * @param sql
	 * Ԥ�����sql���
	 * @param obj
	 * @return
	 */
	public <T> List<T> getList(Class<T> c, String sql, Object... obj);
	
	
	/**
	 * �����ѯ
	 * @param c
	 * ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param t
	 * ����
	 * @return
	 */
	public <T> List<T> getList(T t);
	
	
	/**
	 * Ԥ����sql��ʽ��õ�һ����
	 * @param c
	 * @param sql
	 * @param obj
	 * @return
	 */
	public <T> T getObject(Class<T> c,String sql,Object...obj);
	
	/**
	 * ����ʽ��õ�������
	 * @param t
	 * @return
	 */
	public <T> T getObject(T t);
	
	/**
	 * ID��ѯ
	 * @param c
	 * ��װ���Class
	 * @param id
	 * @return
	 */
	public <T> T getOne(Class<T> c, Object id);
	
	/**
	 * ��ҳ��ѯ
	 * @param t
	 * ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param index
	 * ��һ�������ڱ��е�λ��
	 * @param size
	 * ÿҳ�ļ�¼��
	 * @return
	 */
	public <T> List<T> getPagList(T t, int index, int size) ;
	
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
	public <T> List<T> getSortList(T t, String property, int r);
	
	/**
	 * Ԥ������䱣��
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean save(String sql, Object... obj);
	
	/**
	 * �������
	 * @param t
	 * ���������Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean save(T t,boolean...addId);
	
	/**
	 * ��������-����ģʽ
	 * @param obj
	 * ����������Ϣ�Ķ�������
	 * @return
	 */
	public boolean saveArrayBatch(boolean addId,Object...obj);
	
	/**
	 * �����������
	 * @param sql
	 * ģ��Ԥ����SQL���
	 * @param obj
	 * ���ռλ����һ�������������ɵĶ�ά����
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean saveBatch(String sql,Object[][] obj);
	
	/**
	 * ��������-����ģʽ
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean saveListBatch(List<T> list);
	
	/**
	 * Ԥ��������޸�
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean update(String sql, Object... obj);
	
	/**
	 * �޸�����
	 * @param t
	 * �����޸���Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean update(T t);
	
	/**
	 * ��������-����ģʽ
	 * @param obj
	 * ����������Ϣ�Ķ�������
	 * @return
	 */
	public boolean updateArrayBatch(Object...obj);
	
	/**
	 * �������²���
	 * @param sql
	 * ģ��Ԥ����SQL���
	 * @param obj
	 * ���ռλ����һ�������������ɵĶ�ά����
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean updateBatch(String sql,Object[][] obj);
	
	/**
	 * ��������-����ģʽ
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean updateListBatch(List<T> list);
	
	/**
	 * Mapper�ӿ�ʽ����,���ظýӿڵĴ������
	 * @param clazz Mapper�ӿڵ�Class
	 * @return Mapper�ӿڵĴ������
	 */
	public <T> T getMapper(Class<T> clazz);
	

	/**
	 * ����ʽ�Ķ�����Ӳ���<br>
	 * 	1.ǿ����  ��ǰ���������֮�����ʹ��ǿ����<br>
	 * 		tab1-->tab2 [-->]   
	 * 		<br>��ʾtab2����������ڵ�tab1ʹ���������Ϊ����������������<br>
	 * 	2.������<br>
	 *      tab1-->tab2--tab3 [--] <br>
	 *      ��ʾtab2������������ڵ�tab1��tab1��ʹ���������Ϊ����������������<br>
	 *  3.ָ������<br>
	 *  	tab1-->tab2--tab3&lt2&gttab4 [&ltn&gt] <br>
	 *  	��ʾtab4��������ڵ�λ������������2�ű���tab1ʹ���������Ϊ����������������<br>
	 *  	--><==>&lt0&gt  --<==>&lt1&gt<br>
	 *  ��expressionȱʡʱ���ײ�������·�ʽ�Զ�����һ��expression<br>(queryObjTab1-->queryObjTab2-->...-->queryObjTabn)
	 * @param query ��ѯ��������Ҫ�������Ӳ����Ķ���+���ӷ�ʽ+ָ�����ص��У�
	 * @param expression ���ӱ��ʽ('-->'ǿ����,'--'������,'&ltn&gt'ָ������)
	 * @param resultClass ���ڽ��ܷ���ֵ�����Class
	 * @return
	 */
	public <T> List<T> query(QueryBuilder queryBuilder,Class<T> resultClass,String...expression);
	
	
	/**
	 * ��ջ���
	 */
	public void clear();
	

}
