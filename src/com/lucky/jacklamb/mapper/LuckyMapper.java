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
	 * ����ID�õ�һ����¼
	 * @param id ����id
	 * @return T
	 */
	@Select(byid=true)
	public T selectById(Object id);
	
	/**
	 * ����IDɾ��һ����¼
	 * @param id ����id
	 * @return
	 */
	@Delete(byid=true)
	public boolean deleteById(Object id);

	/**
	 * ��ѯ����
	 * @param pojo
	 * @return
	 */
	@Select
	public T select(T pojo);
	
	/**
	 * ��ѯ����
	 * @param pojo
	 * @return
	 */
	@Select
	public List<T> selectList(T pojo);
	
	/**
	 * ���²���
	 * @param pojo
	 * @return
	 */
	@Update
	public boolean update(T pojo);
	
	/**
	 * ��Ӳ��������Զ���ȡ����ID
	 * @param pojo
	 * @return
	 */
	@Insert(setautoId=true)
	public boolean insertAutoID(T pojo);
	
	/**
	 * ��Ӳ���
	 * @param pojo
	 * @return
	 */
	@Insert
	public boolean insert(T pojo);
	
	/**
	 * ������Ӳ���
	 * @param pojos
	 * @return
	 */
	@Insert(batch=true)
	public boolean batchInsert(List<T> pojos);
	
	/**
	 * ��ҳ����
	 * @param pojo
	 * @param page
	 * @param rows
	 * @return
	 */
	@Query(limit=true)
	public List<T> selectLimit(T pojo,int page,int rows);
	
	/**
	 * QueryBuilder��ѯģʽ
	 * @param queryBuilder
	 * @return
	 */
	@Query(queryBuilder=true)
	public List<T> query(QueryBuilder queryBuilder);
	
	/**
	 * Count����
	 * @param t
	 * @return
	 */
	@Count
	public int count(T t);
	
}
