package com.lucky.jacklamb.mapper;

import java.util.Collection;
import java.util.List;

import com.lucky.jacklamb.annotation.orm.mapper.Count;
import com.lucky.jacklamb.annotation.orm.mapper.Delete;
import com.lucky.jacklamb.annotation.orm.mapper.Insert;
import com.lucky.jacklamb.annotation.orm.mapper.Query;
import com.lucky.jacklamb.annotation.orm.mapper.Select;
import com.lucky.jacklamb.annotation.orm.mapper.Update;
import com.lucky.jacklamb.query.QueryBuilder;

/**
 * ���������Mapper�ӿ�ģ�壬����������Mapper�ӿڿ���
 * @author fk-7075
 * @param <T> ���Ӧ��ʵ����
 */
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
	 * ����ɾ��
	 * @param pojo
	 * @return
	 */
	@Delete
	public boolean delete(T pojo);

	/**
	 * ��ѯ����
	 * @param pojo ������ѯ��Ϣ��pojo����
	 * @return ��Ӧ���͵Ĳ�ѯ���
	 */
	@Select
	public T select(T pojo);
	
	/**
	 * ��ѯ����
	 * @param pojo ������ѯ��Ϣ��pojo����
	 * @return  ��Ӧ���ͼ��ϵĲ�ѯ���
	 */
	@Select
	public List<T> selectList(T pojo);
	
	/**
	 * ��ѯClass��Ӧ�����������
	 * @param pojoClass
	 * @return
	 */
	public List<T> selectList();
	
	/**
	 * ���²���
	 * @param pojo ����������Ϣ��pojo����
	 * @return 
	 */
	@Update
	public boolean update(T pojo);
	
	/**
	 * ��Ӳ��������Զ���ȡ����ID
	 * @param pojo ���������Ϣ��pojo����
	 * @return
	 */
	@Insert(setautoId=true)
	public boolean insertAutoID(T pojo);
	
	/**
	 * ��Ӳ���
	 * @param pojo ���������Ϣ��pojo����
	 * @return
	 */
	@Insert
	public boolean insert(T pojo);
	
	/**
	 * ������Ӳ���
	 * @param pojos ���������Ϣ��List[pojo]����
	 * @return
	 */
	@Insert(batch=true)
	public boolean batchInsert(Collection<T> pojos);
	
	/**
	 * ��ҳ����
	 * @param pojo ������ѯ��Ϣ��pojo����
	 * @param page ҳ��
	 * @param rows ÿҳ��ʾ������
	 * @return
	 */
	@Query(limit=true)
	public List<T> selectLimit(T pojo,int page,int rows);
	
	/**
	 * QueryBuilder��ѯģʽ
	 * @param queryBuilder QueryBuilder����
	 * @return
	 */
	@Query(queryBuilder=true)
	public List<T> query(QueryBuilder queryBuilder);
	
	/**
	 * Count����
	 * @param pojo ������ѯ��Ϣ��pojo����
	 * @return
	 */
	@Count
	public int count(T pojo);
	
	/**
	 * ����ͳ��
	 * @param pojoClass
	 * @return
	 */
	public int count();
	
}
