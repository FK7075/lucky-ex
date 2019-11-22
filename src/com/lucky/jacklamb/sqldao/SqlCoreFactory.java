package com.lucky.jacklamb.sqldao;

import com.lucky.jacklamb.utils.Jacklabm;

public class SqlCoreFactory {
	
	/**
	 * ���SqlCore����
	 * @return
	 */
	public static SqlCore getSqlCore() {
		Jacklabm.welcome();
		return SqlControl.getSqlControl();
	}
	
	/**
	 * ���SqlCore���󣬲����������򹤳�����Pojo��(classpath��Ҫ������lucky.properties�ļ��У��Ҳ��ܽ�������)
	 * @return
	 */
	public static SqlCore getSqlCoreAddCreateBean() {
		Jacklabm.welcome();
		return SqlControl.getSqlControlAddCJavaBean();
	}
	
	/**
	 * ���SqlCore���󣬲����������򹤳�����Pojo��
	 * @param srcPath ��ǰ��Ŀ��classpath
	 * @return
	 */
	public static SqlCore getSqlCoreAddCreateBean(String srcPath) {
		Jacklabm.welcome();
		return SqlControl.getSqlControlAddCJavaBean(srcPath);
	}
	
	/**
	 * ���SqlCore���󣬲����������򹤳�����Pojo��
	 * @param srcPath ��ǰ��Ŀ��classpath
	 * @param tables ��Ҫ����Pojo��ı���
	 * @return
	 */
	public static SqlCore getSqlCoreAddCreateBeanA(String srcPath,String...tables) {
		Jacklabm.welcome();
		return SqlControl.getSqlControlAddCJavaBean(srcPath,tables);
	}
	
	/**
	 * ���SqlCore���󣬲����������򹤳�����Pojo��(classpath��Ҫ������lucky.properties�ļ��У��Ҳ��ܽ�������)
	 * @param tables ��Ҫ����Pojo��ı���
	 * @return
	 */
	public static SqlCore getSqlCoreAddCreateBeanB(String...tables) {
		Jacklabm.welcome();
		return SqlControl.getSqlControlAddCJavaBean(tables);
	}
	
	/**
	 * ���SqlCore����,����ѡ���Ƿ������Զ��������
	 * @param isCreateTable �Ƿ������Զ��������
	 * @return
	 */
	public static SqlCore getSqlCore(boolean isCreateTable) {
		Jacklabm.welcome();
		return SqlControl.getSqlControl(isCreateTable);
	}

}
