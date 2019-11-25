package com.lucky.jacklamb.sqldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lucky.jacklamb.c3p0.C3p0Util;
/**
 * JDBC��ز�����
 * @author fk-7075
 *
 */
public class SqlOperation {
	private Connection conn;
	private PreparedStatement ps;
	private LogInfo log;
	private ResultSet rs;
	private boolean isOk;

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public SqlOperation(String dbname) {
		conn=C3p0Util.getConnecion(dbname);
		log=new LogInfo(dbname);
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
			C3p0Util.release(rs, ps, conn);
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
			 C3p0Util.release(rs, ps, conn);
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
		C3p0Util.release(rs, ps, conn);
	}
	

}
