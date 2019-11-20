package com.lucky.sqldao;

import com.lucky.ioc.config.DataSource;
import com.lucky.utils.LuckyUtils;

/**
 * 日志管理类
 * 更具配置信息打印或不打印sql信息
 * @author fk-7075
 *
 */
public class LogInfo {
	private DataSource dataSource=DataSource.getDataSource();
	
	public void isShowLog(String sql, Object[] obj) {
		if(dataSource.isLog())
			log(sql,obj);
	}
	
	public void isShowLog(String sql,Object obj[][]) {
		if(dataSource.isLog())
			logBatch(sql,obj);
	}
	@Deprecated
	public void isShowLog(String object) {
		if(dataSource.isLog())
			System.out.println("T-Obj: "+object);
	}
	
	
	private void log(String sql, Object[] obj) {
		System.out.println(LuckyUtils.showtime()+"SQL:   " + sql);
		if (obj == null)
			System.out.println(LuckyUtils.showtime()+"Parameters: { }");
		else {
			System.out.print(LuckyUtils.showtime()+"Parameters: {");
			for (Object o : obj) {
				System.out.print(o + "   ");
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
		System.out.println(LuckyUtils.showtime()+"SQL:   " + sql);
		if(obj==null||obj.length==0)
			System.out.println("Parameters: { }");
		else {
			for(int i=0;i<obj.length;i++) {
				System.out.print("Parameters: {");
				for(Object o:obj[i])
					System.out.print(o + "   ");
				System.out.println("}");
			}
		}
	}
}
