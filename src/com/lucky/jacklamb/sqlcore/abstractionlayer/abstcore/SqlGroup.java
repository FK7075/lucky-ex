package com.lucky.jacklamb.sqlcore.abstractionlayer.abstcore;

/**
 * SQL×éºÏ
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


	public abstract String sqlGroup(String res,String onsql,String andsql,String like,String sort);

}
