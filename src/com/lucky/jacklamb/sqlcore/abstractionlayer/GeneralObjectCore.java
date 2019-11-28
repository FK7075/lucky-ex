package com.lucky.jacklamb.sqlcore.abstractionlayer;

import java.util.List;

/**
 * ��ϵ�����ݿ�ͨ�õĶ������
 * @author fk-7075
 *
 */
public interface GeneralObjectCore {
	
	/**
	 * ID��ѯ
	 * @param c
	 * ��װ���Class
	 * @param id
	 * @return
	 */
	public <T> T getOne(Class<T> c, Object id);
	
	/**
	 * ����ʽ��õ�������
	 * @param t
	 * @return
	 */
	public <T> T getObject(T t);
	
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
	 * ����IDɾ��
	 * @param clzz Ҫ�������Ӧ���Class
	 * @param ids Ҫɾ����id����ɵļ���
	 * @return
	 */
	public boolean deleteBatchByID(Class<?> clazz,Object...ids);
	
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
	 * ͳ������
	 * @param t
	 * @return
	 */
	public <T> int count(T t);
	
	
	/**
	 * ɾ������
	 * @param t
	 * ����ɾ����Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean delete(T t);
	
	/**
	 * �޸�����
	 * @param t
	 * �����޸���Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean update(T t);
	
	/**
	 * ����ɾ��-����ģʽ
	 * @param obj
	 * ����ɾ����Ϣ�Ķ�������
	 * @return
	 */
	public boolean deleteBatchByArray(Object...obj);
	
	
	/**
	 * ��������-����ģʽ
	 * @param obj
	 * ����������Ϣ�Ķ�������
	 * @return
	 */
	public boolean updateBatchByArray(Object...obj);
	
	/**
	 * ����ɾ��-����ģʽ
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean deleteBatchByList(List<T> list);
	
	/**
	 * ��������-����ģʽ
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean insertBatchByList(List<T> list);
	
	/**
	 * ��������-����ģʽ
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean updateBatchByList(List<T> list);

}
