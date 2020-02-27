package com.lucky.jacklamb.sqlcore.abstractionlayer.abstcore;

import java.util.Collection;
import java.util.List;

import com.lucky.jacklamb.query.QueryBuilder;

/**
 * ������ϵ�����ݿ����е�SQl�﷨��Ӧ�Ķ������
 * @author DELL
 *
 */
public interface UniqueSqlCore {
	
	/**
	 * ���򹤳�����JavaBean,��Ҫ�������ļ�������classpath(src)�ľ���·�������ڰ���·��
	 */
	void createJavaBean();
	
	/**
	 * ���򹤳�����JavaBean,��Ҫ�������ļ����������ڰ���·��
	 * @param srcPath classpath(src)�ľ���·��
	 */
	void createJavaBean(String srcPath);
	
	/**
	 * ���򹤳�����JavaBean,��Ҫ�������ļ�������classpath(src)�ľ���·�������ڰ���·��
	 * @param tables ָ����Ҫ����JavaBean�ı���
	 */
	void createJavaBean(String... tables);
	
	/**
	 * ���򹤳�����JavaBean,��Ҫ�������ļ����������ڰ���·��
	 * @param srcPath classpath(src)�ľ���·��
	 * @param tables ָ����Ҫ����JavaBean�ı���
	 */
	void createJavaBean(String srcPath, String... tables);
	
	/**
	 * �����Զ�������ƽ�����Ҫ�������ļ���������Ҫ�����ʵ����İ�·��
	 */
	void createTable();
	

	/**
	 * ��ҳ��ѯ
	 * @param t
	 * ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param page
	 * ��һ�������ڱ��е�λ��
	 * @param size
	 * ÿҳ�ļ�¼��
	 * @return
	 */
	<T> List<T> getPageList(T t, int page, int size) ;
	
	/**
	 * �������
	 * @param t ���������Ϣ�İ�װ��Ķ���
	 * @return
	 */
	<T> boolean insert(T t);
	
	/**
	 * �������,��Ϊÿ������������������
	 * @param t
	 * @return
	 */
	<T> boolean insertSetId(T t);
	
	/**
	 * ��������-����ģʽ,��Ϊÿ������������������
	 * @param obj ��Ҫ��ӵ����ݿ��ʵ�������
	 * @return
	 */
	<T> boolean insertSetIdBatchByArray(Object... obj);
	
	/**
	 * ��������-����ģʽ
	 * @param collection Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	<T> boolean insertBatchByCollection(Collection<T> collection);
	

	/**
	 * ��������-����ģʽ
	 * @param obj ��Ҫ��ӵ����ݿ��ʵ�������
	 * @return
	 */
	boolean insertBatchByArray(Object...obj);
	
	void setNextId(Object pojo);
	
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
	 * @param queryBuilder ��ѯ��������Ҫ�������Ӳ����Ķ���+���ӷ�ʽ+ָ�����ص��У�
	 * @param expression ���ӱ��ʽ('-->'ǿ����,'--'������,'&ltn&gt'ָ������)
	 * @param resultClass ���ڽ��ܷ���ֵ�����Class
	 * @return
	 */
	<T> List<T> query(QueryBuilder queryBuilder,Class<T> resultClass,String...expression);
	
	/**
	 * ��ջ���
	 */
	void clear();

}
