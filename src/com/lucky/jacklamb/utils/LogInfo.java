package com.lucky.jacklamb.utils;

import static com.lucky.jacklamb.utils.LuckyUtils.*;

import com.lucky.jacklamb.sqlcore.abstractionlayer.util.PojoManage;
import com.lucky.jacklamb.sqlcore.c3p0.DataSource;
import com.lucky.jacklamb.sqlcore.c3p0.ReadIni;

/**
 * 日志管理类
 * 更具配置信息打印或不打印sql信息
 * @author fk-7075
 *
 */
public class LogInfo {
	
	private boolean log;
	private DataSource dataSource;
	private String dataName;
	private String dataType;
	private String ipPort;
	private SqlFormatUtil sqlFormatUtil;
	
	public LogInfo(String dbname) {
		dataSource=ReadIni.getDataSource(dbname);
		log=dataSource.isLog();
		dataName=PojoManage.getDatabaseName(dataSource.getName());
		dataType=PojoManage.getDatabaseType(dbname);
		ipPort=PojoManage.getIpPort(dbname);
		sqlFormatUtil=new SqlFormatUtil();
	}
	
	public void isShowLog(String sql, Object[] obj) {
		if(log)
			log(sql,obj);
	}
	
	public void isShowLog(String sql,Object obj[][]) {
		if(log)
			logBatch(sql,obj);
	}
	@Deprecated
	public void isShowLog(String object) {
		if(log)
			System.out.println("T-Obj: "+object);
	}
	
	
	private void log(String sql, Object[] obj) {
		System.out.println(showtime()+"["+dataType.toLowerCase()+":"+ipPort+dataName+"] SQL: " + formatSql(sql));
		if (obj == null)
			System.out.println(showtime()+"Parameters    : { }");
		else {
			StringBuilder out=new StringBuilder(showtime());
			out.append("Parameters    :{ ");
			for (Object o : obj) {
				out.append("(").append((o!=null?o.getClass().getSimpleName():"NULL")).append(")").append(o).append("   ");
			}
			out.append("}");
			System.out.println(out.toString());
		}
	}
	/**
	 * 
	 * @param sql
	 * @param obj
	 */
	private void logBatch(String sql,Object obj[][]) {
		System.out.println(showtime()+"["+dataType.toLowerCase()+":"+ipPort+dataName+"] SQL: " + formatSql(sql));
		if(obj==null||obj.length==0)
			System.out.println(showtime()+"Parameters    : { }");
		else {
			for(int i=0;i<obj.length;i++) {
				StringBuilder out=new StringBuilder(showtime());
				out.append("Parameters    :{ ");
				for(Object o:obj[i]) {
					out.append("(").append((o!=null?o.getClass().getSimpleName():"NULL")).append(")").append(o).append("   ");
				}
				out.append("}");
				System.out.println(out.toString());
			}
		}
	}
	
	private String formatSql(String sql) {
		if(dataSource.isFormatSqlLog())
			return "\n"+sqlFormatUtil.format(sql);
		return sql;
		
	}
}
