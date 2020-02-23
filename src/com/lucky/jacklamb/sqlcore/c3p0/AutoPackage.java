package com.lucky.jacklamb.sqlcore.c3p0;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lucky.jacklamb.exception.AutoPackageException;
import com.lucky.jacklamb.sqlcore.abstractionlayer.util.PojoManage;
import com.lucky.jacklamb.tcconversion.typechange.JavaConversion;
import com.lucky.jacklamb.utils.LuckyManager;

/**
 * ������Զ���װ��
 * 
 * @author fk-7075
 *
 */
public class AutoPackage {
	private ResultSet rs = null;
	private SqlOperation sqloperation;
	
	public AutoPackage(String dbname) {
		sqloperation = LuckyManager.getSqlOperation(dbname);
	}

	/**
	 * �Զ���������е����ݷ�װ����
	 * 
	 * @param c ��װ���Class����
	 * @param sql Ԥ�����sql���
	 * @param obj �滻ռλ��������
	 * @return ����һ�����͵�List����
	 */
	public List<?> autoPackageToList(Class<?> c, String sql, Object... obj) {
		List<Object> list = new ArrayList<Object>();
		return (List<?>) autoPackageToCollection(c,list,sql,obj);
	}
	
	/**
	 * �Զ���������е����ݷ�װ����
	 * 
	 * @param c ��װ���Class����
	 * @param sql Ԥ�����sql���
	 * @param obj �滻ռλ��������
	 * @return ����һ�����͵�Set����
	 */
	public Set<?> autoPackageToSet(Class<?> c, String sql, Object... obj) {
		Set<Object> list = new HashSet<Object>();
		return (Set<?>) autoPackageToCollection(c,list,sql,obj);
	}
	
	/**
	 * �Զ���װ
	 * @param c ��װ���Class����
	 * @param collection
	 * @param sql Ԥ�����sql���
	 * @param obj �滻ռλ��������
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Collection<T> autoPackageToCollection(Class<?> c,Collection<T> collection, String sql, Object... obj) {
		rs = sqloperation.getResultSet(sql, obj);
		if(c.getClassLoader()!=null) {
			Field[] fields = c.getDeclaredFields();
			Object object = null;
			try {
				while (rs.next()) {
					object = c.newInstance();
					for (Field f : fields) {
						if (f.getType().getClassLoader()!=null) {
							Class<?> cl=f.getType();
							Field[] fils=cl.getDeclaredFields();
							Object onfk=cl.newInstance();
							for (Field ff : fils) {
								String field_tab=PojoManage.getTableField(ff);
								if (isExistColumn(rs, field_tab)) {
									ff.setAccessible(true);
									ff.set(onfk, rs.getObject(field_tab));
								}
							}
							f.setAccessible(true);
							f.set(object, onfk);
						} else {
							String field_tab=PojoManage.getTableField(f);
							if (isExistColumn(rs, field_tab)) {
								f.setAccessible(true);
								f.set(object, rs.getObject(field_tab));
							}
						}
					}
					collection.add((T) object);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new AutoPackageException("����ӳ������޷��Զ���װ��ѯ�����������ӳ�����á���װ�ࣺClass:"+c.getName()+"   SQl:"+sql);
			}finally {
				sqloperation.close();
			}
		}else {
			try {
				while(rs.next()) {
					collection.add((T) JavaConversion.strToBasic(rs.getObject(1).toString(), c));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				sqloperation.close();
			}
		}
		return collection;
	}
	
	public boolean update(String sql,Object...obj) {
		return sqloperation.setSql(sql, obj);
	}
	
	public boolean updateBatch(String sql,Object[][] obj) {
		return sqloperation.setSqlBatch(sql, obj);
	}

	/**
	 * �жϽ�������Ƿ���ָ������
	 * 
	 * @param rs
	 *            ���������
	 * @param columnName
	 *            ����
	 * @return ���������ָ��������true
	 */
	public boolean isExistColumn(ResultSet rs, String columnName) {
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int size=metaData.getColumnCount();
			for(int i=1;i<=size;i++) {
				if(columnName.equalsIgnoreCase(metaData.getColumnLabel(i))) {
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
