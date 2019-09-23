package xfl.fk.sqldao;

import java.util.List;

public interface SqlCore {
	/**
	 * 开启事务
	 * @return
	 */
	public Transaction openTransaction();
	
	/**
	 * 关闭资源
	 */
	public void close();
	
	/**
	 * id删除(注解方式)
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
	 * 
	 * @param c
	 * 包装类的Class
	 * @param sql
	 * 预编译的sql语句
	 * @param obj
	 * @return
	 */
	public <T> List<T> getList(Class<T> c, String sql, Object... obj);
	
	/**
	 * 查询数据
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
	public <T> boolean save(T t);
	
	/**
	 * 批量保存-数组模式
	 * @param obj
	 * 包含保存信息的对象数组
	 * @return
	 */
	public boolean saveArrayBatch(Object...obj);
	
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
	 * 内连接查询的对象操作方式
	 * @param c 接收结果的包装类的Class
	 * @param pojos 需要进行联合操作的表对应的实体类对象
	 * @return List集合
	 */
	public <T> List<T> getListJoin(Class<T> c,Object...pojos);
	
	
	/**
	 * 左外连接查询的对象操作方式
	 * @param c 接收结果的包装类的Class
	 * @param pojos 需要进行联合操作的表对应的实体类对象
	 * @return List集合
	 */
	public <T> List<T> getListJoinLeft(Class<T> c,Object...pojos);
	
	/**
	 * 右外连接查询的对象操作方式
	 * @param c 接收结果的包装类的Class
	 * @param pojos 需要进行联合操作的表对应的实体类对象
	 * @return List集合
	 */
	public <T> List<T> getListJoinRight(Class<T> c,Object...pojos);

	/**
	 * 内连接查询指定返回列的的对象操作方式（不建议使用此方法，使用不当会造成编译错误，推荐使用Mapper接口的开发方式）
	 * @param c 接收结果的包装类的Class
	 * @param result 代替查询语句中 "*"的合法SQL语法的SQl片段
	 * @param pojos 需要进行联合操作的表对应的实体类对象
	 * @return List集合
	 */
	public <T> List<T> getListJoinResult(Class<T> c, String result, Object[] pojos);

	/**
	 * 左外连接查询指定返回列的的对象操作方式（不建议使用此方法，使用不当会造成编译错误，推荐使用Mapper接口的开发方式）
	 * @param c 接收结果的包装类的Class
	 * @param result 代替查询语句中 "*"的合法SQL语法的SQl片段
	 * @param pojos 需要进行联合操作的表对应的实体类对象
	 * @return List集合
	 */
	public <T> List<T> getListJoinLeftResult(Class<T> c, String result, Object[] pojos);

	/**
	 * 右外连接查询指定返回列的的对象操作方式（不建议使用此方法，使用不当会造成编译错误，推荐使用Mapper接口的开发方式）
	 * @param c 接收结果的包装类的Class
	 * @param result 代替查询语句中 "*"的合法SQL语法的SQl片段
	 * @param pojos 需要进行联合操作的表对应的实体类对象
	 * @return List集合
	 */
	public <T> List<T> getListJoinRightResult(Class<T> c, String result, Object[] pojos);
	
	/**
	 * 清空缓存
	 */
	public void clear();
	

}
