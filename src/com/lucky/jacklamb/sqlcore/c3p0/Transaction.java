 package com.lucky.jacklamb.sqlcore.c3p0;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * Lucky����������
 * @author fk-7075
 *
 */
public class Transaction {
	private Connection conn;
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	//�ύ����
	public void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//����ع�
	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
