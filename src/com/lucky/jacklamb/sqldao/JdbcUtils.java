package com.lucky.jacklamb.sqldao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lucky.jacklamb.exception.NoDataSourceException;
import com.lucky.jacklamb.ioc.config.DataSource;
import com.lucky.jacklamb.utils.LuckyManager;

/**
 * JDBC�����Ĺ�����
 * 
 * @author fk-7075
 *
 */
public class JdbcUtils {
	private static DataSource dataSource;
	static {
		try {
			dataSource=DataSource.getDataSource();
			Class.forName(dataSource.getDriver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("ȱʧ���ݿ��������[your driver="+dataSource.getDriver()+"]");
		}
	}

	public static Connection getConnection() {
		return LuckyManager.getDBPool().getConnection();
	}
	public static Connection createConnection() {
		try {
			return DriverManager.getConnection(dataSource.getUrl(),dataSource.getUsername(),dataSource.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NoDataSourceException("��������ݿ�����[databaseURL:"+dataSource.getUrl()+"] �� �����û���������[username:"+dataSource.getUsername()+"  password="+dataSource.getPassword()+"],����db.properties�е�������û�application���������Ϣ�Ƿ���ȷ��");
		}
	}
	public static void release(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				LuckyManager.getDBPool().closeConection(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("��Դ�رմ���");
		}
	}

}
