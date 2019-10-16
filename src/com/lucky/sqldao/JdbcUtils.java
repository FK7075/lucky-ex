package com.lucky.sqldao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lucky.utils.LuckyManager;
import com.lucky.utils.ProperConfig;

/**
 * JDBC操作的工具类
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
			System.err.println("xflfk:缺失mysql的驱动包");
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
			System.err.println("xflfk：数据库路径错误或数据库用户名密码错误,请检查lucky.xml中的相关配置信息是否正确！");
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
			System.out.println("xflfk:资源关闭错误！");
			e.printStackTrace();
		}
	}

}
