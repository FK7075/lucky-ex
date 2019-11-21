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
			dtlkeysql.deleteKey();// 删除所有现有的外键
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
			throw new NoDataSourceException("不正确的自动建表配置信息，无法执行建表程序，请检查classpath下的db.properties配置文件中的'create.table'属性的配置信息，或者检查appconfig包下配置类[ApplicationConfig子类]的setDataSource方法是否正确。");
		}
	}
}
