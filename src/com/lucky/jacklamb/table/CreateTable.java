package com.lucky.jacklamb.table;

import java.util.List;

import com.lucky.jacklamb.exception.NoDataSourceException;
import com.lucky.jacklamb.ioc.config.DataSource;
import com.lucky.jacklamb.sqldao.SqlOperation;
import com.lucky.jacklamb.utils.LuckyManager;

public class CreateTable {
	private SqlOperation sqlop = LuckyManager.getSqlOperation();
	private List<String> classlist = DataSource.getDataSource().getCaeateTable();

	public void creatTable() {
		DeleteKeySql dtlkeysql = new DeleteKeySql();
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
