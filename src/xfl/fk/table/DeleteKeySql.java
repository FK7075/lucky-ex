package xfl.fk.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xfl.fk.annotation.Lucky;
import xfl.fk.sqldao.SqlOperation;
import xfl.fk.utils.LuckyManager;

public class DeleteKeySql {
	private SqlOperation sqlop = LuckyManager.getSqlOperation();
	private String databasename;
	private List<String> delkeysql = new ArrayList<String>();
	private List<String> classlist=LuckyManager.getPropCfg().getClaurl();

	public String getDatabasename() {
		return databasename;
	}

	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}

	public List<String> getDelkeysql() {
		return delkeysql;
	}

	public void setDelkeysql(List<String> delkeysql) {
		this.delkeysql = delkeysql;
	}

	/**
	 * ����ʱɾ��
	 */
	public void deleteKey() {
		for (String str : classlist) {
			try {
				String table = "";
				Class<?> clazz = Class.forName(str);
				Lucky lucky = clazz.getAnnotation(Lucky.class);
				if ("".equals(lucky.table()))
					table = clazz.getSimpleName();
				else
					table = lucky.table();
				String sql = "show create table " + table;
				ResultSet rs = sqlop.getResultSet(sql);
				List<String> keyList=new ArrayList<>();
				if (rs != null) {
					while (rs.next()) {
						String info = rs.getString(2);
						while (info.contains("CONSTRAINT")) {
							int index = info.indexOf("CONSTRAINT");
							int end = info.indexOf("FOREIGN");
							keyList.add(info.substring(index + 12, end - 2));
							info = info.replaceFirst("CONSTRAINT", "");
							info = info.replaceFirst("FOREIGN", "");
						}
					}
				}
				for (String wkey : keyList) {
					String sqlStr = "alter table " + table + " drop foreign key " + wkey;
					sqlop.setSql(sqlStr);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * �õ����ݿ�����ֺ�ɾ�����ݿ����б������sql��伯�ϲ���װ��������
	 */
	public DeleteKeySql() {
//		String url = propCfg.getUrl();
//		this.databasename = url.substring((url.lastIndexOf("/") + 1), url.length());
//		String sql = "SELECT CONCAT('ALTER TABLE ',TABLE_SCHEMA,'.',TABLE_NAME,' DROP FOREIGN KEY ',CONSTRAINT_NAME,' ;') "
//				+ "FROM information_schema.TABLE_CONSTRAINTS c "
//				+ "WHERE c.TABLE_SCHEMA=? AND c.CONSTRAINT_TYPE='FOREIGN KEY';";
//		ResultSet rs = sqlop.getResultSet(sql, this.databasename);
//		try {
//			while (rs.next()) {
//				this.delkeysql.add(rs.getString(1));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * ɾ���������
	 */
	public void deleteKey1() {
		for (String sql : this.delkeysql) {
			sqlop.setSql(sql);
		}
	}

}
