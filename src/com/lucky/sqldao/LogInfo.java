package com.lucky.sqldao;

import com.lucky.utils.LuckyManager;
import com.lucky.utils.LuckyUtils;
import com.lucky.utils.ProperConfig;

/**
 * 日志管理类
 * 更具配置信息打印或不打印sql信息
 * @author fk-7075
 *
 */
public class LogInfo {
	private ProperConfig proper=LuckyManager.getPropCfg();
	
	public void isShowLog(String sql, Object[] obj) {
		if(proper.isLog())
			log(sql,obj);
	}
	
	public void isShowLog(String sql,Object obj[][]) {
		if(proper.isLog())
			logBatch(sql,obj);
	}
	@Deprecated
	public void isShowLog(String object) {
		if(proper.isLog())
			System.out.println("T-Obj: "+object);
	}
	
	
	private void log(String sql, Object[] obj) {
		System.out.println(LuckyUtils.showtime()+"SQL:   " + sql);
		if (obj == null)
			System.out.println(LuckyUtils.showtime()+"Object[]: { }");
		else {
			System.out.print(LuckyUtils.showtime()+"Object[]: {");
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
			System.out.println("Object[]: { }");
		else {
			for(int i=0;i<obj.length;i++) {
				System.out.print("Object[]: {");
				for(Object o:obj[i])
					System.out.print(o + "   ");
				System.out.println("}");
			}
		}
	}
}
