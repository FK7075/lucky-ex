package xfl.fk.join;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ��ʼ����Ϣ����
 * @author fk7075
 *
 */
public class InitializePoJo {
	
	/**
	 * ��ҳ��ѯ�İ�װ��
	 */
	private Class<?> clzz;
	/**
	 * ������ѯ��Ϣ��pojo
	 */
	private List<Object> pojos=new ArrayList<>();
	/**
	 * ��ѯ��Ԥ����SQL
	 */
	private String sql;
	/**
	 * ���ռλ���Ķ���
	 */
	private List<Object> sqlobj=new ArrayList<>();
	
	
	/**
	 * �õ�һ���յĳ�ʼ����Ϣ����
	 */
	public InitializePoJo() {
		
	}

	/**
	 * �õ�һ��������ҳ��װ���ĳ�ʼ����Ϣ����
	 * @param clzz ��ҳ��װ��
	 */
	public InitializePoJo(Class<?> clzz) {
		this.clzz = clzz;
	}
	
	/**
	 * �õ�һ��������ҳ��װ���Ͱ�����ѯ��Ϣ��pojo�ĳ�ʼ����Ϣ����
	 * @param clzz ��ҳ��װ��
	 * @param pojos ������ҳ��ѯ��Ϣ��pojos
	 */
	public InitializePoJo(Class<?> clzz, Object...pojos) {
		this.clzz = clzz;
		this.pojos = Arrays.asList(pojos);
	}
	
	/**
	 * 
	 * @param clzz ��ҳ��װ��
	 * @param sql ��ҳǰ��Ԥ����SQL
	 * @param sqlobj ���ռλ���Ķ���
	 */
	public InitializePoJo(Class<?> clzz, String sql, Object...sqlobj) {
		this.clzz = clzz;
		this.sql = sql;
		this.sqlobj = Arrays.asList(sqlobj);
	}


	public Class<?> getClzz() {
		return clzz;
	}
	public void setClzz(Class<?> clzz) {
		this.clzz = clzz;
	}
	public List<Object> getPojos() {
		return pojos;
	}
	public void setPojos(Object...pojos) {
		this.pojos = Arrays.asList(pojos);
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<Object> getSqlobj() {
		return sqlobj;
	}
	public void setSqlobj(Object...sqlobj) {
		this.sqlobj = Arrays.asList(sqlobj);
	}
	
	
	

}
