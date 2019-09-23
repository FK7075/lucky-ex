package xfl.fk.sqldao;

public class SqlCoreFactory {
	
	/**
	 * 获得SqlCore对象
	 * @return
	 */
	public static SqlCore getSqlCore() {
		return SqlControl.getSqlControl();
	}
	
	/**
	 * 获得SqlCore对象，并且启用逆向工程生成Pojo类(classpath需要配置在lucky.properties文件中，且不能解析中文)
	 * @return
	 */
	public static SqlCore getSqlCoreAddCreateBean() {
		return SqlControl.getSqlControlAddCJavaBean();
	}
	
	/**
	 * 获得SqlCore对象，并且启用逆向工程生成Pojo类
	 * @param srcPath 当前项目的classpath
	 * @return
	 */
	public static SqlCore getSqlCoreAddCreateBean(String srcPath) {
		return SqlControl.getSqlControlAddCJavaBean(srcPath);
	}
	
	/**
	 * 获得SqlCore对象，并且启用逆向工程生成Pojo类
	 * @param srcPath 当前项目的classpath
	 * @param tables 需要生成Pojo类的表名
	 * @return
	 */
	public static SqlCore getSqlCoreAddCreateBeanA(String srcPath,String...tables) {
		return SqlControl.getSqlControlAddCJavaBean(srcPath,tables);
	}
	
	/**
	 * 获得SqlCore对象，并且启用逆向工程生成Pojo类(classpath需要配置在lucky.properties文件中，且不能解析中文)
	 * @param tables 需要生成Pojo类的表名
	 * @return
	 */
	public static SqlCore getSqlCoreAddCreateBeanB(String...tables) {
		return SqlControl.getSqlControlAddCJavaBean(tables);
	}
	
	/**
	 * 获得SqlCore对象,并且选择是否启动自动建表机制
	 * @param isCreateTable 是否启动自动建表机制
	 * @return
	 */
	public static SqlCore getSqlCore(boolean isCreateTable) {
		return SqlControl.getSqlControl(isCreateTable);
	}

}
