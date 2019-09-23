package xfl.fk.sqldao;

/**
 * 预编译语句和填充对象数组的包装类
 * @author fk-7075
 *
 */
public class SqlInfo {
	private String sql;
	private Object[] obj;
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Object[] getObj() {
		return obj;
	}
	public void setObj(Object[] obj) {
		this.obj = obj;
	}
	

}
