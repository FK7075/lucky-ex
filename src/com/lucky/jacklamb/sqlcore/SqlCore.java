package com.lucky.jacklamb.sqlcore;

import java.util.List;

import com.lucky.jacklamb.query.QueryBuilder;
import com.lucky.jacklamb.sqlcore.c3p0.Transaction;

public interface SqlCore {
	/**
	 * 开启事务
	 * @return
	 */
	public Transaction openTransaction();
	
	/**
	 * 得到当前SqlCore对象对应的数据源的dbname
	 * @return
	 */
	public String getDbName();
	
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
	 * 关闭资源
	 */
	public void close();
	
	/**
	 * id删除
	 * @param clazz
	 * 所操作类
	 * @param id
	 * id值
	 * @return
	 */
	public boolean delete(Class<?> clazz,Object id);
	
	/**
	 * 预编译语句删除
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean delete(String sql,Object...obj);
	
	/**
	 * 删除数据
	 * @param t
	 * 包含删除信息的包装类的对象
	 * @return
	 */
	public <T> boolean delete(T t);
	
	/**
	 * 批量删除-数组模式
	 * @param obj
	 * 包含删除信息的对象数组
	 * @return
	 */
	public boolean deleteArrayBatch(Object...obj);
	
	/**
	 * 批量更删除操作
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean deleteBatch(String sql,Object[][] obj);
	
	
	/**
	 * 批量ID删除
	 * @param clzz 要操作表对应类的Class
	 * @param ids 要删除的id所组成的集合
	 * @return
	 */
	public boolean deleteIDBatch(Class<?> clazz,Object...ids);
	
	/**
	 * 批量删除-集合模式
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean deleteListBatch(List<T> list);
	
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
	 * SQL查询
	 * @param c
	 * 包装类的Class
	 * @param sql
	 * 预编译的sql语句
	 * @param obj
	 * @return
	 */
	public <T> List<T> getList(Class<T> c, String sql, Object... obj);
	
	
	/**
	 * 对象查询
	 * @param c
	 * 包含查询信息的包装类的对象
	 * @param t
	 * 对象
	 * @return
	 */
	public <T> List<T> getList(T t);
	
	
	/**
	 * 预编译sql方式获得单一对象
	 * @param c
	 * @param sql
	 * @param obj
	 * @return
	 */
	public <T> T getObject(Class<T> c,String sql,Object...obj);
	
	/**
	 * 对象方式获得单个对象
	 * @param t
	 * @return
	 */
	public <T> T getObject(T t);
	
	/**
	 * ID查询
	 * @param c
	 * 包装类的Class
	 * @param id
	 * @return
	 */
	public <T> T getOne(Class<T> c, Object id);
	
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
	 * 预编译语句保存
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean save(String sql, Object... obj);
	
	/**
	 * 添加数据
	 * @param t
	 * 包含添加信息的包装类的对象
	 * @return
	 */
	public <T> boolean save(T t,boolean...addId);
	
	/**
	 * 批量保存-数组模式
	 * @param obj
	 * 包含保存信息的对象数组
	 * @return
	 */
	public boolean saveArrayBatch(boolean addId,Object...obj);
	
	/**
	 * 批量保存操作
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean saveBatch(String sql,Object[][] obj);
	
	/**
	 * 批量保存-集合模式
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean saveListBatch(List<T> list);
	
	/**
	 * 预编译语句修改
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean update(String sql, Object... obj);
	
	/**
	 * 修改数据
	 * @param t
	 * 包含修改信息的包装类的对象
	 * @return
	 */
	public <T> boolean update(T t);
	
	/**
	 * 批量更新-数组模式
	 * @param obj
	 * 包含更新信息的对象数组
	 * @return
	 */
	public boolean updateArrayBatch(Object...obj);
	
	/**
	 * 批量更新操作
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean updateBatch(String sql,Object[][] obj);
	
	/**
	 * 批量更新-集合模式
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean updateListBatch(List<T> list);
	
	/**
	 * Mapper接口式开发,返回该接口的代理对象
	 * @param clazz Mapper接口的Class
	 * @return Mapper接口的代理对象
	 */
	public <T> T getMapper(Class<T> clazz);
	

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
