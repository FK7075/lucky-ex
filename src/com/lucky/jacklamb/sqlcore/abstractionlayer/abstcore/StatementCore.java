package com.lucky.jacklamb.sqlcore.abstractionlayer.abstcore;

import java.util.List;

/**
 * ��ϵ�����ݿ�ͨ�õ�Sql������
 * @author fk-7075
 *
 */
public interface StatementCore {
	
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
	 * Ԥ����SQL��ʽ��õ�һ����
	 * @param c
	 * @param sql
	 * @param obj
	 * @return
	 */
	public <T> T getObject(Class<T> c,String sql,Object...obj);
	
	/**
	 * Ԥ����SQL�ǲ�ѯ����
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean update(String sql,Object...obj);

	
	
	/**
	 * ����SQL�ǲ�ѯ����
	 * @param sql
	 * ģ��Ԥ����SQL���
	 * @param obj
	 * ���ռλ����һ�������������ɵĶ�ά����
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean updateBatch(String sql,Object[][] obj);
	
	/**
	 * ��ջ���
	 */
	public void clear();
	

	

}
