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
 * JDBC操作的工具类
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
			throw new RuntimeException("缺失数据库的驱动包[your driver="+dataSource.getDriver()+"]");
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
			throw new NoDataSourceException("错误的数据库连接[databaseURL:"+dataSource.getUrl()+"] 或 错误用户名和密码[username:"+dataSource.getUsername()+"  password="+dataSource.getPassword()+"],请检查db.properties中的相关配置或application类的配置信息是否正确！");
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
			throw new RuntimeException("资源关闭错误！");
		}
	}

}
