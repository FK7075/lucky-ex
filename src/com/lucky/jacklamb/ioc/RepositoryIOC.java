package com.lucky.jacklamb.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.annotation.db.Mapper;
import com.lucky.jacklamb.annotation.ioc.Repository;
import com.lucky.jacklamb.exception.NotAddIOCComponent;
import com.lucky.jacklamb.exception.NotFindBeanException;
import com.lucky.jacklamb.sqlcore.DataSource;
import com.lucky.jacklamb.sqlcore.ReadProperties;
import com.lucky.jacklamb.sqlcore.SqlCore;
import com.lucky.jacklamb.sqlcore.SqlCoreFactory;
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
			throw new NotFindBeanException("��Repository��Mapper(ioc)�������Ҳ���IDΪ--" + id + "--��Bean...");
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
			throw new NotAddIOCComponent("Repository(ioc)�������Ѵ���IDΪ--"+daoId+"--��������޷��ظ�����......");
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
			throw new NotAddIOCComponent("Mapper(ioc)�������Ѵ���IDΪ--"+mapperID+"--��������޷��ظ�����......");
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
	 * ����Repository���
	 * 
	 * @param repositoryClass
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public RepositoryIOC initRepositoryIOC(List<String> repositoryClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
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
					List<DataSource> datalist=ReadProperties.getAllDataSource();
					for(DataSource data:datalist) {
						SqlCore sqlCore=SqlCoreFactory.createSqlCore(data.getName());
						addRepositoryMap("lucky#$jacklamb#$&58314@SqlCore-"+data.getName(), sqlCore);
					}
					first = false;
				}
				Mapper mapper = repository.getAnnotation(Mapper.class);
				String id="lucky#$jacklamb#$&58314@SqlCore-"+mapper.dbname();
				SqlCore currSqlCore=(SqlCore) getMaRepBean(id);
				if (!"".equals(mapper.id()))
					addMapperMap(mapper.id(), currSqlCore.getMapper(repository));
				else
					addMapperMap(LuckyUtils.TableToClass1(repository.getSimpleName()), currSqlCore.getMapper(repository));
			}
		}
		return this;
	}
}