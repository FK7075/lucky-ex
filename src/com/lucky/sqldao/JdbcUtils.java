package com.lucky.sqldao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lucky.utils.LuckyManager;
import com.lucky.utils.LuckyUtils;
import com.lucky.utils.ProperConfig;

/**
 * JDBC�����Ĺ�����
 * 
 * @author fk-7075
 *
 */
public class JdbcUtils {
	private static ProperConfig propCfg;
	static {
		try {
			propCfg=LuckyManager.getPropCfg();
			Class.forName(propCfg.getDriver());
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
			return DriverManager.getConnection(propCfg.getUrl(),propCfg.getUsername(),propCfg.getPassword());
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
