package com.lucky.jacklamb.sqlcore.abstractionlayer;

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
