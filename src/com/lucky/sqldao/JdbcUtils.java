package com.lucky.sqldao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lucky.ioc.config.DataSource;
import com.lucky.utils.LuckyManager;
import com.lucky.utils.LuckyUtils;

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
			throw new RuntimeException(LuckyUtils.showtime()+"JackLabm: ȱʧmysql��������");
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
			throw new RuntimeException(LuckyUtils.showtime()+"JackLabm: ���ݿ�·����������ݿ��û����������,����lucky.xml�е�������û�application���������Ϣ�Ƿ���ȷ��");
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
			throw new RuntimeException(LuckyUtils.showtime()+"JackLabm:  ��Դ�رմ���");
		}
	}

}
