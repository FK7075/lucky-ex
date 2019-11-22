package com.lucky.jacklamb.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.annotation.Mapper;
import com.lucky.jacklamb.annotation.Repository;
import com.lucky.jacklamb.exception.NotAddIOCComponent;
import com.lucky.jacklamb.exception.NotFindBeanException;
import com.lucky.jacklamb.sqldao.SqlCore;
import com.lucky.jacklamb.sqldao.SqlCoreFactory;
import com.lucky.jacklamb.utils.LuckyUtils;

public class RepositoryIOC {

	private Map<String, Object> repositoryMap;

	private List<String> repositoryIDS;

	private Map<String, Object> mapperMap;

	private List<String> mapperIDS;
	
	
	public RepositoryIOC() {
		repositoryMap=new HashMap<>();
		repositoryIDS=new ArrayList<>();
		mapperMap=new HashMap<>();
		mapperIDS=new ArrayList<>();
	}

	public Object getMaRepBean(String id) {
		if (containIdByMapper(id))
			return mapperMap.get(id);
		else if (containIdByRepository(id))
			return repositoryMap.get(id);
		else
			throw new NotFindBeanException("在Repository和Mapper(ioc)容器中找不到ID为--" + id + "--的Bean...");
	}

	public boolean containId(String id) {
		return containIdByMapper(id) || containIdByRepository(id);
	}

	public boolean containIdByMapper(String id) {
		return mapperIDS.contains(id);
	}

	public boolean containIdByRepository(String id) {
		return repositoryIDS.contains(id);
	}

	public Map<String, Object> getRepositoryMap() {
		return repositoryMap;
	}

	public void setRepositoryMap(Map<String, Object> repositoryMap) {
		this.repositoryMap = repositoryMap;
	}

	public void addRepositoryMap(String daoId, Object daoObj) {
		if(containId(daoId))
			throw new NotAddIOCComponent("Repository(ioc)容器中已存在ID为--"+daoId+"--的组件，无法重复添加......");
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
		repositoryIDS.add(repositoryID);
	}

	public Map<String, Object> getMapperMap() {
		return mapperMap;
	}

	public void setMapperMap(Map<String, Object> mapperMap) {
		this.mapperMap = mapperMap;
	}

	public void addMapperMap(String mapperID, Object mapperObj) {
		if(containId(mapperID))
			throw new NotAddIOCComponent("Mapper(ioc)容器中已存在ID为--"+mapperID+"--的组件，无法重复添加......");
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
		mapperIDS.add(mapperID);
	}

	/**
	 * 加载Repository组件
	 * 
	 * @param repositoryClass
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public RepositoryIOC initRepositoryIOC(List<String> repositoryClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		SqlCore sqlCore = null;
		boolean first = true;
		for (String clzz : repositoryClass) {
			Class<?> repository = Class.forName(clzz);
			if (repository.isAnnotationPresent(Repository.class)) {
				Repository rep = repository.getAnnotation(Repository.class);
				if (!"".equals(rep.value()))
					addRepositoryMap(rep.value(), repository.newInstance());
				else
					addRepositoryMap(LuckyUtils.TableToClass1(repository.getSimpleName()), repository.newInstance());
			} else if (repository.isAnnotationPresent(Mapper.class)) {
				if (first) {
					sqlCore = SqlCoreFactory.getSqlCore();
					addRepositoryMap("lucky#$jacklamb#$&58314@SqlCore", sqlCore);
					first = false;
				}
				Mapper mapper = repository.getAnnotation(Mapper.class);
				if (!"".equals(mapper.id()))
					addMapperMap(mapper.id(), sqlCore.getMapper(repository));
				else
					addMapperMap(LuckyUtils.TableToClass1(repository.getSimpleName()), sqlCore.getMapper(repository));
			}
		}
		return this;
	}
}
