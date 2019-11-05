package com.lucky.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.annotation.Mapper;
import com.lucky.annotation.Repository;
import com.lucky.exception.CreateBeanException;
import com.lucky.exception.NoDataSourceException;
import com.lucky.exception.NotFindBeanException;
import com.lucky.sqldao.SqlCore;
import com.lucky.sqldao.SqlCoreFactory;
import com.lucky.utils.LuckyUtils;

public class RepositoryIOC {
	
	private Map<String,Object> repositoryMap;
	
	private List<String> repositoryIDS;
	
	private Map<String,Object> mapperMap;
	
	private List<String> mapperIDS;
	
	public Object getMaRepBean(String id) {
		if(containIdByMapper(id))
			return mapperMap.get(id);
		else if(containIdByRepository(id))
			return repositoryMap.get(id);
		else
			throw new NotFindBeanException("在Repository和Mapper(ioc)容器中找不到ID为--"+id+"--的Bean...");
	}
	
	public boolean containId(String id) {
		return containIdByMapper(id)||containIdByRepository(id);
	}
	
	public boolean containIdByMapper(String id) {
		if(mapperIDS==null)
			return false;
		return mapperIDS.contains(id);
	}
	
	public boolean containIdByRepository(String id) {
		if(repositoryIDS==null)
			return false;
		return repositoryIDS.contains(id);
	}
	public Map<String, Object> getRepositoryMap() {
		return repositoryMap;
	}

	public void setRepositoryMap(Map<String, Object> repositoryMap) {
		this.repositoryMap = repositoryMap;
	}
	
	public void addRepositoryMap(String daoId,Object daoObj) {
		if(repositoryMap==null)
			repositoryMap=new HashMap<>();
		repositoryMap.put(daoId, daoObj);
		addRepositoryIDS(daoId);
	}

	public List<String> getRepositoryIDS() {
		return repositoryIDS;
	}

	public void setRepositoryIDS(List<String> repositoryIDS) {
		this.repositoryIDS = repositoryIDS;
	}

	public void addRepositoryIDS(String repositoryID) {
		if(repositoryIDS==null)
			repositoryIDS=new ArrayList<>();
		repositoryIDS.add(repositoryID);
	}
	public Map<String, Object> getMapperMap() {
		return mapperMap;
	}

	public void setMapperMap(Map<String, Object> mapperMap) {
		this.mapperMap = mapperMap;
	}
	
	public void addMapperMap(String mapperID,Object mapperObj) {
		if(mapperMap==null)
			mapperMap=new HashMap<>();
		mapperMap.put(mapperID, mapperObj);
		addMapperIDS(mapperID);
	}

	public List<String> getMapperIDS() {
		return mapperIDS;
	}

	public void setMapperIDS(List<String> mapperIDS) {
		this.mapperIDS = mapperIDS;
	}
	
	public void addMapperIDS(String mapperID) {
		if(mapperIDS==null)
			mapperIDS=new ArrayList<>();
		mapperIDS.add(mapperID);
	}
	
	/**
	 * 加载Repository组件
	 * @param repositoryClass
	 * @return
	 */
	public RepositoryIOC initRepositoryIOC(List<String> repositoryClass) {
		SqlCore sqlCore = null;
		boolean first=true;
		for(String clzz:repositoryClass) {
			Class<?> repository = null;
			try {
				repository=Class.forName(clzz);
				if(repository.isAnnotationPresent(Repository.class)) {
					Repository rep=repository.getAnnotation(Repository.class);
					if(!"".equals(rep.value())) 
						addRepositoryMap(rep.value(), repository.newInstance());
					else
						addRepositoryMap(LuckyUtils.TableToClass1(repository.getSimpleName()), repository.newInstance());
				}else if(repository.isAnnotationPresent(Mapper.class)) {
					if(first) {
						sqlCore=SqlCoreFactory.getSqlCore();
						addRepositoryMap("jacklamb->lucky->SqlCore", sqlCore);
						first=false;
					}
					Mapper mapper=repository.getAnnotation(Mapper.class);
					if(!"".equals(mapper.id()))
						addMapperMap(mapper.id(), sqlCore.getMapper(repository));
					else
						addMapperMap(LuckyUtils.TableToClass1(repository.getSimpleName()), sqlCore.getMapper(repository));
				}
			} catch (ClassNotFoundException e) {
				continue;	
			} catch (InstantiationException e) {
				throw new NoDataSourceException();
			} catch (IllegalAccessException e) {
				throw new CreateBeanException("没有发现"+repository.getName()+"的无参构造器，无法创建对象...");
			}
		}
		return this;
	}
}
