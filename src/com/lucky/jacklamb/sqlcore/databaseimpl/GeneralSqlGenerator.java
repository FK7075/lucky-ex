package com.lucky.jacklamb.sqlcore.databaseimpl;

import com.lucky.jacklamb.query.QFilter;
import com.lucky.jacklamb.sqlcore.PojoManage;

/**
 * 通用SQL生成器
 * @author DELL
 *
 */
public class GeneralSqlGenerator {
	
	public String getOneSql(Class<?> c) {
		StringBuilder sql=new StringBuilder("SELECT ");
		sql.append(new QFilter(c).lines()).append(" FROM ")
		.append(PojoManage.getTable(c)).append(" WHERE ")
		.append(PojoManage.getIdString(c)).append(" =?");
		return sql.toString();
	}

}
