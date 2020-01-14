package com.lucky.jacklamb.mapper;

import java.util.List;

import com.lucky.jacklamb.annotation.orm.mapper.Count;
import com.lucky.jacklamb.annotation.orm.mapper.Delete;
import com.lucky.jacklamb.annotation.orm.mapper.Insert;
import com.lucky.jacklamb.annotation.orm.mapper.Query;
import com.lucky.jacklamb.annotation.orm.mapper.Select;
import com.lucky.jacklamb.annotation.orm.mapper.Update;
import com.lucky.jacklamb.query.QueryBuilder;

public interface LuckyMapper<T> {
	
	/**
	 * 根据ID得到一条记录
	 * @param id 主键id
	 * @return T
	 */
	@Select(byid=true)
	public T selectById(Object id);
	
	/**
	 * 根据ID删除一条记录
	 * @param id 主键id
	 * @return
	 */
	@Delete(byid=true)
	public boolean deleteById(Object id);

	/**
	 * 查询操作
	 * @param pojo
	 * @return
	 */
	@Select
	public T select(T pojo);
	
	/**
	 * 查询操作
	 * @param pojo
	 * @return
	 */
	@Select
	public List<T> selectList(T pojo);
	
	/**
	 * 更新操作
	 * @param pojo
	 * @return
	 */
	@Update
	public boolean update(T pojo);
	
	/**
	 * 添加操作，并自动获取自增ID
	 * @param pojo
	 * @return
	 */
	@Insert(setautoId=true)
	public boolean insertAutoID(T pojo);
	
	/**
	 * 添加操作
	 * @param pojo
	 * @return
	 */
	@Insert
	public boolean insert(T pojo);
	
	/**
	 * 批量添加操作
	 * @param pojos
	 * @return
	 */
	@Insert(batch=true)
	public boolean batchInsert(List<T> pojos);
	
	/**
	 * 分页操作
	 * @param pojo
	 * @param page
	 * @param rows
	 * @return
	 */
	@Query(limit=true)
	public List<T> selectLimit(T pojo,int page,int rows);
	
	/**
	 * QueryBuilder查询模式
	 * @param queryBuilder
	 * @return
	 */
	@Query(queryBuilder=true)
	public List<T> query(QueryBuilder queryBuilder);
	
	/**
	 * Count操作
	 * @param t
	 * @return
	 */
	@Count
	public int count(T t);
	
}
