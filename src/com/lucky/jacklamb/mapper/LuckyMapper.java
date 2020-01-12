package com.lucky.jacklamb.mapper;

import java.util.List;

import com.lucky.jacklamb.annotation.orm.mapper.Delete;
import com.lucky.jacklamb.annotation.orm.mapper.Insert;
import com.lucky.jacklamb.annotation.orm.mapper.Query;
import com.lucky.jacklamb.annotation.orm.mapper.Select;
import com.lucky.jacklamb.annotation.orm.mapper.Update;
import com.lucky.jacklamb.query.QueryBuilder;

public interface LuckyMapper<T> {
	
	@Select(byid=true)
	public T selectById(Object id);
	
	@Delete(byid=true)
	public boolean deleteById(Object id);

	@Select
	public T select(T pojo);
	
	@Select
	public List<T> selectList(T pojo);
	
	@Update
	public boolean update(T pojo);
	
	@Insert(setautoId=true)
	public boolean insertAutoID(T pojo);
	
	@Insert
	public boolean insert(T pojo);
	
	@Insert(batch=true)
	public boolean batchInsert(List<T> pojos);
	
	@Query(limit=true)
	public List<T> selectLimit(T pojo,int page,int rows);
	
	@Query(queryBuilder=true)
	public List<?> query(QueryBuilder queryBuilder);
	
}
