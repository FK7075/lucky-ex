package com.lucky.jacklamb.sqlcore;

import static com.lucky.jacklamb.utils.LuckyUtils.*;

/**
 * ��־������
 * ����������Ϣ��ӡ�򲻴�ӡsql��Ϣ
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
		dataName=getDatabaseName(dataSource.getName());
		dataType=getDatabaseType(dbname);
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
		System.out.println(showtime()+"["+dataType+":"+dataName+"]SQL: " + sql);
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
		System.out.println(showtime()+"["+dataType+":"+dataName+"]SQL: " + sql);
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
}
