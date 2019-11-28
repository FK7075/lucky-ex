package com.lucky.jacklamb.sqlcore.abstractionlayer;

import java.util.List;

import com.lucky.jacklamb.query.QueryBuilder;

/**
 * 各个关系型数据库特有的SQl语法对应的对象操作
 * @author DELL
 *
 */
public interface UniqueSqlCore {
	
	/**
	 * 逆向工程生成JavaBean,需要在配置文件中配置classpath(src)的绝对路径和所在包的路径
	 */
	public void createJavaBean();
	
	/**
	 * 逆向工程生成JavaBean,需要在配置文件中配置所在包的路径
	 * @param srcPath classpath(src)的绝对路径
	 */
	public void createJavaBean(String srcPath);
	
	/**
	 * 逆向工程生成JavaBean,需要在配置文件中配置classpath(src)的绝对路径和所在包的路径
	 * @param tables 指定需要生成JavaBean的表名
	 */
	public void createJavaBean(String... tables);
	
	/**
	 * 逆向工程生成JavaBean,需要在配置文件中配置所在包的路径
	 * @param srcPath classpath(src)的绝对路径
	 * @param tables 指定需要生成JavaBean的表名
	 */
	public void createJavaBean(String srcPath, String... tables);
	
	/**
	 * 启动自动建表机制建表，需要在配置文件中配置需要建表的实体类的包路径
	 */
	public void createTable();
	
	/**
	 * 简单模糊查询
	 * @param c
	 * 包装类的Class
	 * @param property
	 * 要查询的字段
	 * @param info
	 * 查询关键字
	 * @return
	 */
	public <T> List<T> getFuzzyList(Class<T> c, String property, String info);
	
	/**
	 * 分页查询
	 * @param t
	 * 包含查询信息的包装类的对象
	 * @param index
	 * 第一条数据在表中的位置
	 * @param size
	 * 每页的记录数
	 * @return
	 */
	public <T> List<T> getPagList(T t, int index, int size) ;
	
	/**
	 * 排序查询
	 * @param t
	 * 包含查询信息的包装类的对象
	 * @param property
	 * 排序关键字
	 * @param r
	 * 排序方式（0-升序 1-降序）
	 * @return
	 */
	public <T> List<T> getSortList(T t, String property, int r);
	
	/**
	 * 对象方式的多表连接操作<br>
	 * 	1.强链接  最前面的两个表之间必须使用强连接<br>
	 * 		tab1-->tab2 [-->]   
	 * 		<br>表示tab2表与左边相邻的tab1使用主外键作为连接条件进行连接<br>
	 * 	2.弱连接<br>
	 *      tab1-->tab2--tab3 [--] <br>
	 *      表示tab2表跳过左边相邻的tab1与tab1表使用主外键作为连接条件进行连接<br>
	 *  3.指定连接<br>
	 *  	tab1-->tab2--tab3&lt2&gttab4 [&ltn&gt] <br>
	 *  	表示tab4从左边相邻的位置起，向左跳过2张表与tab1使用主外键作为连接条件进行连接<br>
	 *  	--><==>&lt0&gt  --<==>&lt1&gt<br>
	 *  当expression缺省时，底层会以如下方式自动生成一个expression<br>(queryObjTab1-->queryObjTab2-->...-->queryObjTabn)
	 * @param query 查询条件（需要进行连接操作的对象+连接方式+指定返回的列）
	 * @param expression 连接表达式('-->'强连接,'--'弱连接,'&ltn&gt'指定连接)
	 * @param resultClass 用于接受返回值的类的Class
	 * @return
	 */
	public <T> List<T> query(QueryBuilder queryBuilder,Class<T> resultClass,String...expression);
	
	/**
	 * 清空缓存
	 */
	public void clear();

}
