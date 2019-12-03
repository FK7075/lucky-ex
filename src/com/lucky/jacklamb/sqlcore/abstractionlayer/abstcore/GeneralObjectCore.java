package com.lucky.jacklamb.sqlcore.abstractionlayer.abstcore;

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
	<T> T getOne(Class<T> c, Object id);
	
	/**
	 * ����ʽ��õ�������
	 * @param t
	 * @return
	 */
	<T> T getObject(T t);
	
	/**
	 * idɾ��
	 * @param clazz
	 * ��������
	 * @param id
	 * idֵ
	 * @return
	 */
	boolean delete(Class<?> clazz,Object id);
	
	/**
	 * ����IDɾ��
	 * @param clazz Ҫ�������Ӧ���Class
	 * @param ids Ҫɾ����id����ɵļ���
	 * @return
	 */
	boolean deleteBatchByID(Class<?> clazz,Object...ids);
	
	/**
	 * �����ѯ
	 * @param t
	 * ����
	 * @return
	 */
	<T> List<T> getList(T t);
	
	/**
	 * ͳ������
	 * @param t
	 * @return
	 */
	<T> int count(T t);
	
	
	/**
	 * ɾ������
	 * @param t
	 * ����ɾ����Ϣ�İ�װ��Ķ���
	 * @return
	 */
	<T> boolean delete(T t);
	
	/**
	 * �޸�����
	 * @param t
	 * �����޸���Ϣ�İ�װ��Ķ���
	 * @return
	 */
	<T> boolean update(T t,String...conditions);
	
	/**
	 * ����ɾ��-����ģʽ
	 * @param obj
	 * ����ɾ����Ϣ�Ķ�������
	 * @return
	 */
	boolean deleteBatchByArray(Object...obj);
	
	
	/**
	 * ��������-����ģʽ
	 * @param obj
	 * ����������Ϣ�Ķ�������
	 * @return
	 */
	boolean updateBatchByArray(Object...obj);
	
	/**
	 * ����ɾ��-����ģʽ
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	<T> boolean deleteBatchByList(List<T> list);
	
	
	/**
	 * ��������-����ģʽ
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	<T> boolean updateBatchByList(List<T> list);
	

	/**
	 * ��Ӳ���
	 * @param pojo ʵ�������
	 * @return
	 */
	<T> boolean insert(T pojo);

}
