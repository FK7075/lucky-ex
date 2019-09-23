package xfl.fk.cache;

import xfl.fk.sqldao.SqlInfo;

public class CreateSql {
	private String sql=null;
	/**
	 * 获得缓存的key值(sql语句)
	 * @param info
	 * @return
	 */
	public String getSqlString(SqlInfo info) {
		if(info.getObj()==null) {
			sql=info.getSql();
		}else {
			String incompleteSql=info.getSql();
			for (Object value : info.getObj()) {
				int i =incompleteSql.indexOf("?");
				StringBuffer buf=new StringBuffer(incompleteSql);
				buf.replace(i, i+1, value+"");
				incompleteSql=buf.toString();
			}
			sql=incompleteSql;
		}
		return sql;
	}
	/**
	 * 获得缓存的key值(sql语句)
	 * @param sql1
	 * @param obj
	 * @return
	 */
	public String getSqlString(String sql1,Object... obj) {
		if(obj==null) {
			sql=sql1;
		}else {
			String incompleteSql=sql1;
			for (Object value :obj) {
				int i =incompleteSql.indexOf("?");
				StringBuffer buf=new StringBuffer(incompleteSql);
				buf.replace(i, i+1, value+"");
				incompleteSql=buf.toString();
			}
			sql=incompleteSql;
		}
		return sql;
	}

}
