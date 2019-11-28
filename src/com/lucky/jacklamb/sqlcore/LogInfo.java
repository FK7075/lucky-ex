package com.lucky.jacklamb.sqlcore;

import com.lucky.jacklamb.utils.LuckyUtils;

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
	
	LogInfo(String dbname) {
		dataSource=ReadProperties.getDataSource(dbname);
		log=dataSource.isLog();
		dataName=LuckyUtils.getDatabaseName(dataSource.getName());
		dataType=LuckyUtils.getDatabaseType(dbname);
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
		System.out.println(LuckyUtils.showtime()+"["+dataType+":"+dataName+"]SQL: " + sql);
		if (obj == null)
			System.out.println(LuckyUtils.showtime()+"Parameters    : { }");
		else {
			StringBuilder out=new StringBuilder(LuckyUtils.showtime());
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
		System.out.println(LuckyUtils.showtime()+"["+dataType+":"+dataName+"]SQL: " + sql);
		if(obj==null||obj.length==0)
			System.out.println(LuckyUtils.showtime()+"Parameters    : { }");
		else {
			for(int i=0;i<obj.length;i++) {
				StringBuilder out=new StringBuilder(LuckyUtils.showtime());
				out.append("Parameters    :{ ");
				for(Object o:obj[i]) {
					out.append("(").append((o!=null?o.getClass().getSimpleName():"NULL")).append(")").append(o).append("   ");
				}
				out.append("}");
				System.out.println(out.toString());
			}
		}
	}
}
