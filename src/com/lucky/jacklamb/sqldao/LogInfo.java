package com.lucky.jacklamb.sqldao;

import com.lucky.jacklamb.ioc.config.DataSource;
import com.lucky.jacklamb.utils.LuckyUtils;

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
	
	LogInfo(String dbname) {
		dataSource=ReadProperties.getDataSource(dbname);
		log=dataSource.isLog();
		dataName=LuckyUtils.getDatabaseName(dataSource.getName());
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
		System.out.println(LuckyUtils.showtime()+"PrecompiledSql("+dataName+"):" + sql);
		if (obj == null)
			System.out.println(LuckyUtils.showtime()+"Parameters    : { }");
		else {
			System.out.print(LuckyUtils.showtime()+"Parameters    :{ ");
			for (Object o : obj) {
				System.out.print("("+(o!=null?o.getClass().getSimpleName():"NULL")+")"+o+"   ");
			}
			System.out.println("}");
		}
	}
	/**
	 * 
	 * @param sql
	 * @param obj
	 */
	private void logBatch(String sql,Object obj[][]) {
		System.out.println(LuckyUtils.showtime()+"PrecompiledSql("+dataName+"): " + sql);
		if(obj==null||obj.length==0)
			System.out.println(LuckyUtils.showtime()+"Parameters    : { }");
		else {
			for(int i=0;i<obj.length;i++) {
				System.out.print(LuckyUtils.showtime()+"Parameters    :{");
				for(Object o:obj[i])
					System.out.print("("+(o!=null?o.getClass().getSimpleName():"NULL")+")"+o+"   ");
				System.out.println(" }");
			}
		}
	}
}
