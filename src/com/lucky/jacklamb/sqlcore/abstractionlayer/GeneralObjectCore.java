package com.lucky.jacklamb.sqlcore.abstractionlayer;

import java.util.List;

/**
 * 关系型数据库通用的对象操作
 * @author fk-7075
 *
 */
public interface GeneralObjectCore {
	
	/**
	 * ID查询
	 * @param c
	 * 包装类的Class
	 * @param id
	 * @return
	 */
	public <T> T getOne(Class<T> c, Object id);
	
	/**
	 * 对象方式获得单个对象
	 * @param t
	 * @return
	 */
	public <T> T getObject(T t);
	
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
	 * 批量ID删除
	 * @param clzz 要操作表对应类的Class
	 * @param ids 要删除的id所组成的集合
	 * @return
	 */
	public boolean deleteBatchByID(Class<?> clazz,Object...ids);
	
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
	 * 统计总数
	 * @param t
	 * @return
	 */
	public <T> int count(T t);
	
	
	/**
	 * 删除数据
	 * @param t
	 * 包含删除信息的包装类的对象
	 * @return
	 */
	public <T> boolean delete(T t);
	
	/**
	 * 修改数据
	 * @param t
	 * 包含修改信息的包装类的对象
	 * @return
	 */
	public <T> boolean update(T t);
	
	/**
	 * 批量删除-数组模式
	 * @param obj
	 * 包含删除信息的对象数组
	 * @return
	 */
	public boolean deleteBatchByArray(Object...obj);
	
	
	/**
	 * 批量更新-数组模式
	 * @param obj
	 * 包含更新信息的对象数组
	 * @return
	 */
	public boolean updateBatchByArray(Object...obj);
	
	/**
	 * 批量删除-集合模式
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean deleteBatchByList(List<T> list);
	
	/**
	 * 批量保存-集合模式
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean insertBatchByList(List<T> list);
	
	/**
	 * 批量更新-集合模式
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean updateBatchByList(List<T> list);

}
