package com.lucky.jacklamb.sqldao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.ioc.config.DataSource;
import com.lucky.jacklamb.utils.LuckyUtils;

/**
 * Connection连接池管理
 * @author fk-7075
 *
 */
public class DBConnectionPool {
	private List<Connection> pool;//连接池
	private final int POOL_MIN=DataSource.getDataSource().getPoolmin();//最小链接数量
	private final int POOL_MAX=DataSource.getDataSource().getPoolmax();//最大链接数量
	
	public DBConnectionPool() {
		initPool();
		System.out.println(LuckyUtils.showtime()+"JackLabm:  成功连接到数据库 #"+LuckyUtils.getDatabaseName()+"#,连接池初始化成功，当前池中共有"+POOL_MIN+"个Connection对象。");
	}
	
	/**
	 * 初始化连接池，创建最小链接数量的链接对象
	 */
	public void initPool() {
		if(pool==null)
			pool=new ArrayList<Connection>();
		while(pool.size()<this.POOL_MIN) {
			pool.add(JdbcUtils.createConnection());
		}
	}
	/**
	 * 返回连接池中的最后一个对象，并从连接池中移除这个对象
	 * @return 返回一个连接对象
	 */
	public synchronized Connection getConnection() {
		if(pool.size()==0) {
			return JdbcUtils.createConnection();
		}else {
			int last_index=pool.size()-1;
			Connection conn=pool.get(last_index);
			pool.remove(last_index);
			return conn;
		}
	}
	/**
	 * 关闭链接，当连接池中的链接对象超过最大值时才真正的关闭
	 * @param conn 连接对象
	 */
	public synchronized void closeConection(Connection conn) {
		if(pool.size()>this.POOL_MAX) {
				try {
					if(conn!=null) {
						conn.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}else {
			pool.add(conn);
		}
			
	}

}
