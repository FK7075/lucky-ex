package xfl.fk.sqldao;

import java.util.List;

public interface SqlCore {
	/**
	 * ��������
	 * @return
	 */
	public Transaction openTransaction();
	
	/**
	 * �ر���Դ
	 */
	public void close();
	
	/**
	 * idɾ��(ע�ⷽʽ)
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
	 * 
	 * @param c
	 * ��װ���Class
	 * @param sql
	 * Ԥ�����sql���
	 * @param obj
	 * @return
	 */
	public <T> List<T> getList(Class<T> c, String sql, Object... obj);
	
	/**
	 * ��ѯ����
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
	public <T> boolean save(T t);
	
	/**
	 * ��������-����ģʽ
	 * @param obj
	 * ����������Ϣ�Ķ�������
	 * @return
	 */
	public boolean saveArrayBatch(Object...obj);
	
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
	 * �����Ӳ�ѯ�Ķ��������ʽ
	 * @param c ���ս���İ�װ���Class
	 * @param pojos ��Ҫ�������ϲ����ı��Ӧ��ʵ�������
	 * @return List����
	 */
	public <T> List<T> getListJoin(Class<T> c,Object...pojos);
	
	
	/**
	 * �������Ӳ�ѯ�Ķ��������ʽ
	 * @param c ���ս���İ�װ���Class
	 * @param pojos ��Ҫ�������ϲ����ı��Ӧ��ʵ�������
	 * @return List����
	 */
	public <T> List<T> getListJoinLeft(Class<T> c,Object...pojos);
	
	/**
	 * �������Ӳ�ѯ�Ķ��������ʽ
	 * @param c ���ս���İ�װ���Class
	 * @param pojos ��Ҫ�������ϲ����ı��Ӧ��ʵ�������
	 * @return List����
	 */
	public <T> List<T> getListJoinRight(Class<T> c,Object...pojos);

	/**
	 * �����Ӳ�ѯָ�������еĵĶ��������ʽ��������ʹ�ô˷�����ʹ�ò�������ɱ�������Ƽ�ʹ��Mapper�ӿڵĿ�����ʽ��
	 * @param c ���ս���İ�װ���Class
	 * @param result �����ѯ����� "*"�ĺϷ�SQL�﷨��SQlƬ��
	 * @param pojos ��Ҫ�������ϲ����ı��Ӧ��ʵ�������
	 * @return List����
	 */
	public <T> List<T> getListJoinResult(Class<T> c, String result, Object[] pojos);

	/**
	 * �������Ӳ�ѯָ�������еĵĶ��������ʽ��������ʹ�ô˷�����ʹ�ò�������ɱ�������Ƽ�ʹ��Mapper�ӿڵĿ�����ʽ��
	 * @param c ���ս���İ�װ���Class
	 * @param result �����ѯ����� "*"�ĺϷ�SQL�﷨��SQlƬ��
	 * @param pojos ��Ҫ�������ϲ����ı��Ӧ��ʵ�������
	 * @return List����
	 */
	public <T> List<T> getListJoinLeftResult(Class<T> c, String result, Object[] pojos);

	/**
	 * �������Ӳ�ѯָ�������еĵĶ��������ʽ��������ʹ�ô˷�����ʹ�ò�������ɱ�������Ƽ�ʹ��Mapper�ӿڵĿ�����ʽ��
	 * @param c ���ս���İ�װ���Class
	 * @param result �����ѯ����� "*"�ĺϷ�SQL�﷨��SQlƬ��
	 * @param pojos ��Ҫ�������ϲ����ı��Ӧ��ʵ�������
	 * @return List����
	 */
	public <T> List<T> getListJoinRightResult(Class<T> c, String result, Object[] pojos);
	
	/**
	 * ��ջ���
	 */
	public void clear();
	

}
