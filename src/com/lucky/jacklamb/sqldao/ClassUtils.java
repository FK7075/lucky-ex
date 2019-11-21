package com.lucky.jacklamb.sqldao;

import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.utils.TypeChange;

/**
 * ���Ͳ���������Ϣ��ץȡ��sql���ƴ����
 * 
 * @author fk-7075
 *
 */
@SuppressWarnings("all")
public class ClassUtils {
	private ClassInfo classInfo = null;
	private List<String> names;
	private List<Object> values;
	private TypeChange tych;
	private LogInfo log;

	public ClassUtils() {
		tych = new TypeChange();
		names = new ArrayList<String>();
		values = new ArrayList<Object>();
		log = new LogInfo();
		
	}
	
	
	/**
	 * ����һ������Ԥ�����sql�������ռλ����Object[]�Ķ���SqlInfo
	 * @param cs
	 * ClassInfo����
	 * @param operation
	 * �����ؼ���
	 * @return SqlInfo
	 */
	public SqlInfo getSqlInfo(ClassInfo cs2, String operation) {
		String sql = null;
		SqlInfo sqlInfo = new SqlInfo();
		Object[] valueY = new Object[cs2.getValues().size()];
		cs2.getValues().toArray(valueY);
		/// ��������������ֵƴ��sql���
		if ("SELECT".equalsIgnoreCase(operation)) {
			sql = "SELECT * FROM " + cs2.getClassName();
			for (int i = 0; i < cs2.getNames().size(); i++) {
				if(i==0)
					sql+=" WHERE "+ cs2.getNames().get(i) + "=? ";
				else
					sql+= "AND " + cs2.getNames().get(i) + "=? ";
			}
		}
		if ("DELETE".equalsIgnoreCase(operation)) {
			sql = "DELETE FROM " + cs2.getClassName();
			for (int i = 0; i < cs2.getNames().size(); i++) {
				if(i==0)
					sql+=" WHERE "+ cs2.getNames().get(i) + "=? ";
				else
					sql += "AND " + cs2.getNames().get(i) + "=? ";
			}
		}
		if ("INSERT".equalsIgnoreCase(operation)) {
			String sql2 = ") VALUES(";
			sql = "INSERT INTO " + cs2.getClassName() + "(";
			for (int i = 0; i < cs2.getNames().size(); i++) {
				if (i == cs2.getNames().size() - 1) {
					sql = sql + cs2.getNames().get(i);
					sql2 = sql2 + "?)";
				} else {
					sql = sql + cs2.getNames().get(i) + ",";
					sql2 = sql2 + "?,";
				}
			}
			sql = sql + sql2;
		}
		if ("UPDATE".equalsIgnoreCase(operation)) {
			IDAndLocation id_loca=new IDAndLocation(cs2);
			//////���ID��λ�ò������,���ID��λ�ÿ�ʼ�����ν�֮���Ԫ����ǰ�ƶ�һλ�����ID�ŵ����
			if(id_loca.getLocation()!=valueY.length-1) {
				for(int i=id_loca.getLocation();i<valueY.length-1;i++) {
					valueY[i]=valueY[i+1];
				}
				valueY[valueY.length-1]=id_loca.getId();
			}
			String sql2 = " WHERE " + PojoManage.getIdString(cs2.getClzz()) + "=?";
			sql = "UPDATE " + cs2.getClassName() + " SET ";
			if( cs2.getNames().size()==(id_loca.getLocation()+1)) {
				for (int i = 0; i < cs2.getNames().size()-1; i++) {
					if ((i != cs2.getNames().size() - 2))
						sql = sql + cs2.getNames().get(i) + "=?,";
					else
						sql = sql + cs2.getNames().get(i) + "=?";
				}
			}else {
				for (int i = 0; i < cs2.getNames().size(); i++) {
					if ((i != cs2.getNames().size() - 1)&&(i!=id_loca.getLocation()))
						sql = sql + cs2.getNames().get(i) + "=?,";
					else if((i!=id_loca.getLocation()))
						sql = sql + cs2.getNames().get(i) + "=?";
				}
			}
			sql = sql + sql2;
		}
		sqlInfo.setSql(sql);
		sqlInfo.setObj(valueY);
		return sqlInfo;
	}

	/**
	 * ���ط�ҳ��ѯ��ClassInfo����
	 * @param cs
	 * ���˺��ClassInfo����
	 * @param index
	 * ��һ�������ڱ��е�λ��
	 * @param size
	 * ÿҳ��¼��
	 * @return Ԥ������������������İ�װ��
	 */
	public SqlInfo getSqlInfo(ClassInfo cs2, int index, int size) {
		SqlInfo sqlInfo = new SqlInfo();
		Object[] valueY = new Object[cs2.getValues().size()];
		cs2.getValues().toArray(valueY);
		String sql = "SELECT * FROM " + cs2.getClassName();
		String sql2 = " LIMIT " + index + "," + size;
		for (int i = 0; i < cs2.getNames().size(); i++) {
			if(i==0)
				sql+=" WHERE "+ cs2.getNames().get(i) + "=? ";
			else
				sql += "AND " + cs2.getNames().get(i) + "=? ";
		}
		sql = sql + sql2;
		sqlInfo.setObj(valueY);
		sqlInfo.setSql(sql);
		return sqlInfo;
	}

	/**
	 * ���������ѯ��ClassInfo����
	 * @param cs
	 * ���˺��ClassInfo����
	 * @param property
	 * Ҫ�����������
	 * @param r
	 * ����ʽ��0-���� 1-����
	 * @return
	 */
	public SqlInfo getSqlInfo(ClassInfo cs2, String property, int r) {
		String ronk = null;
		if (r == 0)
			ronk = "ASC";
		if (r == 1)
			ronk = "DESC";
		SqlInfo sqlInfo = new SqlInfo();
		Object[] valueY = new Object[cs2.getValues().size()];
		cs2.getValues().toArray(valueY);
		String sql = "SELECT * FROM " + cs2.getClassName();
		String sql2 = " ORDER BY " + property + " " + ronk;
		for (int i = 0; i < cs2.getNames().size(); i++) {
			if(i==0)
				sql+=" WHERE "+ cs2.getNames().get(i) + "=? ";
			else
				sql += "AND " + cs2.getNames().get(i) + "=? ";
		}
		sql = sql + sql2;
		sqlInfo.setObj(valueY);
		sqlInfo.setSql(sql);
		return sqlInfo;
	}

	/**
	 * ���ؼ�ģ����ѯ��ClassInfo����
	 * @param c
	 * ��װ���Class
	 * @param property
	 * Ҫ��ѯ���ֶ�
	 * @param info
	 * ��ѯ�ؼ���
	 * @return Ԥ������������������İ�װ��
	 */
	public SqlInfo getSqlInfo(Class<?> c, String property, String info) {
		SqlInfo sqlInfo = new SqlInfo();
		sqlInfo.setSql("SELECT * FROM " + PojoManage.getTable(c) + " WHERE " + property + " LIKE ?");
		Object[] obj = { info };
		sqlInfo.setObj(obj);
		return sqlInfo;
	}

}
