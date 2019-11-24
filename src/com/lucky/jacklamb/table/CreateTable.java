package com.lucky.jacklamb.table;

import java.util.List;

import com.lucky.jacklamb.exception.NoDataSourceException;
import com.lucky.jacklamb.sqldao.SqlOperation;
import com.lucky.jacklamb.utils.LuckyManager;
import com.lucky.jacklamb.utils.ReadProperties;

public class CreateTable {
	private SqlOperation sqlop;
	private List<String> classlist;
	private String dbname;
	
	public CreateTable(String dbname) {
		this.dbname=dbname;
		classlist=ReadProperties.getDataSource(dbname).getCaeateTable();
		sqlop = LuckyManager.getSqlOperation(dbname);
	}

	public void creatTable() {
		DeleteKeySql dtlkeysql = new DeleteKeySql(dbname,classlist);
		try {
			for (String str : classlist) {
				String sql = CreateTableSql.getCreateTable(Class.forName(str));
				sqlop.setSql(sql);
			}
			dtlkeysql.deleteKey();// ɾ���������е����
			for (String str : classlist) {
				List<String> sqls = CreateTableSql.getForeignKey(Class.forName(str));
				if (sqls != null) {
					for (String st : sqls) {
						sqlop.setSql(st);
					}
				}
				List<String> indexKey = CreateTableSql.getIndexKey(Class.forName(str));
				for(String index:indexKey) {
					sqlop.setSql(index);
				}
			}
		} catch (ClassNotFoundException e) {
			throw new NoDataSourceException("����ȷ���Զ�����������Ϣ���޷�ִ�н����������classpath�µ�db.properties�����ļ��е�'create.table'���Ե�������Ϣ�����߼��appconfig����������[ApplicationConfig����]��setDataSource�����Ƿ���ȷ��");
		}
	}
}
