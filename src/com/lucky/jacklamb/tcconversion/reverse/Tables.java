package com.lucky.jacklamb.tcconversion.reverse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.utils.LuckyUtils;
/**
 * �õ����ݿ������еı������
 * @author fk-7075
 *
 */
public class Tables {
	private List<String> tablenames=new ArrayList<String>();//���ݿ��б������

	public List<String> getTablenames() {
		return tablenames;
	}

	public void setTablenames(List<String> tablenames) {
		this.tablenames = tablenames;
	}
	/**
	 * �õ����ݿ������б������
	 */
	public Tables(String dbname) {
		ResultSet rs=LuckyUtils.getResultSet(dbname,"show tables;");
			try {
					while(rs.next()) {
						this.tablenames.add(LuckyUtils.TableToClass(rs.getString(1)));
				}
			} catch (SQLException e) {
				System.err.println("xflfk:������ȡʧ�ܣ�");
				e.printStackTrace();
		}
	}
}
