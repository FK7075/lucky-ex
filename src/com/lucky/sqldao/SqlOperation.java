package com.lucky.sqldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * JDBC相关操作类
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
	 * 实现对表的曾刪改操作
	 * @param sql（预编译的sql语句）
	 * @param obj（替换占位符的数组）
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
	 * 增删改操作批处理
	 * @param sql
	 * 预编译的SQL语句
	 * @param obj
	 * 填充占位符的一个有一个数组组成的二维数组
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
	 * 返回结果集
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
	 * 关闭资源
	 */
	public void close() {
		JdbcUtils.release(rs, ps, conn);
	}
	

}
