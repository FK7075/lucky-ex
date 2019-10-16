package com.lucky.sqldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * JDBC��ز�����
 * @author fk-7075
 *
 */
@SuppressWarnings("all")
public class SqlOperation {
	private static SqlOperation sqloper=null;
	private Connection conn = null;
	private PreparedStatement ps = null;
	private LogInfo log=null;
	private ResultSet rs = null;
	private boolean isOk = false;

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public SqlOperation() {
		conn=JdbcUtils.getConnection();
		log=new LogInfo();
	}
	/**
	 * ʵ�ֶԱ�����h�Ĳ���
	 * @param sql��Ԥ�����sql��䣩
	 * @param obj���滻ռλ�������飩
	 * @return boolean
	 */
	public boolean setSql(String sql, Object...obj) {
		try {
			log.isShowLog(sql, obj);
			ps = conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					ps.setObject(i + 1, obj[i]);
				}
			}
			int i = ps.executeUpdate();
			if (i != 0)
				isOk = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(rs, ps, conn);
		}
		return isOk;
	}
	
	/**
	 * ��ɾ�Ĳ���������
	 * @param sql
	 * Ԥ�����SQL���
	 * @param obj
	 * ���ռλ����һ����һ��������ɵĶ�ά����
	 * @return
	 */
	public boolean setSqlBatch(String sql,Object[]... obj) {
		try {
			log.isShowLog(sql, obj);
			ps = conn.prepareStatement(sql);
			if(obj==null||obj.length==0) {
				ps.executeUpdate();
				isOk=true;
			}else {
				for(int i=0;i<obj.length;i++) {
					for(int j=0;j<obj[i].length;j++) {
						ps.setObject(j+1, obj[i][j]);
					}
					ps.addBatch();
				}
				ps.executeBatch();
				isOk=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 finally {
				JdbcUtils.release(rs, ps, conn);
			}
		return isOk;
	}

	/**
	 * ���ؽ����
	 * @param sql
	 * @param obj
	 * @return
	 */
	public ResultSet getResultSet(String sql, Object...obj) {
		try {
			log.isShowLog(sql, obj);
			ps = conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					ps.setObject(i + 1, obj[i]);
				}
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			return null;
		}
		return rs;
	}
	/**
	 * �ر���Դ
	 */
	public void close() {
		JdbcUtils.release(rs, ps, conn);
	}
	

}
