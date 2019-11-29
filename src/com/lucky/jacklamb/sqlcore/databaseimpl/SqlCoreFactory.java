package com.lucky.jacklamb.sqlcore.databaseimpl;


import com.lucky.jacklamb.sqlcore.ReadProperties;
import com.lucky.jacklamb.sqlcore.abstractionlayer.SqlCore;
import com.lucky.jacklamb.utils.LuckyUtils;

public class SqlCoreFactory {
	
	public static SqlCore createSqlCore(String dbname) {
		String dbType=LuckyUtils.getDatabaseType(dbname);
		switch (dbType) {
		case "MySql":
			return createMySqlCore(dbname);
		case "DB2":
			return createDB2Core(dbname);
		case "Oracle":
			return createOracleCore(dbname);
		case "PostgreSql":
			return createPostgreCore(dbname);
		case "Sql Server":
			return createSqlServerCore(dbname);
		case "Sybase":
			return createSyBaseCore(dbname);
		case "Access":
			return createAccessCore(dbname);
		default:
			throw new RuntimeException("Lucky目前还不支持该类型的数据库，我们正在拼命更新中！DatabaseType:"+ReadProperties.getDataSource(dbname).getDriverClass());
		}
	}
	
	public static SqlCore createSqlCore() {
		return createSqlCore("defaultDB");
	}
	
	private static SqlCore createMySqlCore(String dbname) {
		return new MySqlCore(dbname);
	}

	private static SqlCore createOracleCore(String dbname) {
		return new OracleCore(dbname);
	}
	
	private static SqlCore createPostgreCore(String dbname) {
		return new PostgreSqlCore(dbname);
	}
	
	private static SqlCore createSqlServerCore(String dbname) {
		return new SqlServerCore(dbname);
	}
	
	private static SqlCore createDB2Core(String dbname) {
		return new DB2Core(dbname);
	}
	
	private static SqlCore createSyBaseCore(String dbname) {
		return new SybaseCore(dbname);
	}
	
	private static SqlCore createAccessCore(String dbname) {
		return new AccessSqlCore(dbname);
	}
}
