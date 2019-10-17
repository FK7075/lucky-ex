package com.lucky.table;

import java.util.List;

import com.lucky.sqldao.SqlOperation;
import com.lucky.utils.LuckyManager;

public class CreateTable {
	private SqlOperation sqlop = LuckyManager.getSqlOperation();
	private List<String> classlist = LuckyManager.getPropCfg().getClaurl();

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
			System.err.println("xflfk:  ���������ļ�Class.Url�����Ƿ���д��ȷ");
			e.printStackTrace();
		}
	}
}
