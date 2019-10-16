package com.lucky.sqldao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lucky.utils.LuckyManager;
import com.lucky.utils.ProperConfig;

/**
 * JDBC�����Ĺ�����
 * 
 * @author fk-7075
 *
 */
public class JdbcUtils {
	private static ProperConfig propCfg=LuckyManager.getPropCfg();
	static {
		try {
			Class.forName(propCfg.getDriver());
		} catch (ClassNotFoundException e) {
			System.err.println("xflfk:ȱʧmysql��������");
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return LuckyManager.getDBPool().getConnection();
	}
	public static Connection createConnection() {
		try {
			return DriverManager.getConnection(propCfg.getUrl(),propCfg.getUsername(),propCfg.getPassword());
		} catch (SQLException e) {
			System.err.println("xflfk�����ݿ�·����������ݿ��û����������,����lucky.xml�е����������Ϣ�Ƿ���ȷ��");
			e.printStackTrace();
			return null;
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
			System.out.println("xflfk:��Դ�رմ���");
			e.printStackTrace();
		}
	}

}
