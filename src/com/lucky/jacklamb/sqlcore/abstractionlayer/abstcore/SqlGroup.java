package com.lucky.jacklamb.sqlcore.abstractionlayer.abstcore;

/**
 * SQL���
 * @author DELL
 *
 */
public abstract class SqlGroup {
	
	protected Integer page;
	
	protected Integer rows;
	
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/**
	 * ��ѯ��������Ϸ�ʽ
	 * @param res ������
	 * @param onsql Join��ON����
	 * @param andsql Join��And����
	 * @param like ģ����ѯ����
	 * @param sort �����ѯ����
	 * @return
	 */
	public abstract String sqlGroup(String res,String onsql,String andsql,String like,String sort);

}
