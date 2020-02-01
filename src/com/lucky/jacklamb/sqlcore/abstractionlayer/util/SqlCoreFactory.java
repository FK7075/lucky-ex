package com.lucky.jacklamb.sqlcore.abstractionlayer.util;


import com.lucky.jacklamb.sqlcore.abstractionlayer.abstcore.SqlCore;
import com.lucky.jacklamb.sqlcore.abstractionlayer.dynamiccoreImpl.AccessSqlCore;
import com.lucky.jacklamb.sqlcore.abstractionlayer.dynamiccoreImpl.DB2Core;
import com.lucky.jacklamb.sqlcore.abstractionlayer.dynamiccoreImpl.MySqlCore;
import com.lucky.jacklamb.sqlcore.abstractionlayer.dynamiccoreImpl.OracleCore;
import com.lucky.jacklamb.sqlcore.abstractionlayer.dynamiccoreImpl.PostgreSqlCore;
import com.lucky.jacklamb.sqlcore.abstractionlayer.dynamiccoreImpl.SqlServerCore;
import com.lucky.jacklamb.sqlcore.abstractionlayer.dynamiccoreImpl.SybaseCore;
import com.lucky.jacklamb.sqlcore.c3p0.ReadIni;

public class SqlCoreFactory {
	
	public static SqlCore createSqlCore() {
		return createSqlCore("defaultDB");
	}
	
	public static SqlCore createSqlCore(String dbname) {
		String dbType=PojoManage.getDatabaseType(dbname);
		switch (dbType) {
		case "MySql":
			return new MySqlCore(dbname);
		case "DB2":
			return new DB2Core(dbname);
		case "Oracle":
			return new OracleCore(dbname);
		case "PostgreSql":
			return new PostgreSqlCore(dbname);
		case "Sql Server":
			return new SqlServerCore(dbname);
		case "Sybase":
			return new SybaseCore(dbname);
		case "Access":
			return new AccessSqlCore(dbname);
		default:
			throw new RuntimeException("Lucky目前还不支持该类型的数据库，我们正在拼命更新中！DatabaseType:"+ReadIni.getDataSource(dbname).getDriverClass());
		}
	}
}
