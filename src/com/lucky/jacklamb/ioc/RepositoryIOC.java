package com.lucky.jacklamb.ioc;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.annotation.ioc.Repository;
import com.lucky.jacklamb.annotation.orm.mapper.Mapper;
import com.lucky.jacklamb.aop.util.PointRunFactory;
import com.lucky.jacklamb.exception.NotAddIOCComponent;
import com.lucky.jacklamb.exception.NotFindBeanException;
import com.lucky.jacklamb.sqlcore.abstractionlayer.abstcore.SqlCore;
import com.lucky.jacklamb.sqlcore.abstractionlayer.util.SqlCoreFactory;
import com.lucky.jacklamb.sqlcore.c3p0.DataSource;
import com.lucky.jacklamb.sqlcore.c3p0.ReadProperties;
import com.lucky.jacklamb.utils.LuckyUtils;

public class RepositoryIOC extends ComponentFactory {

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
			throw new NotAddIOCComponent("Repository(ioc)容器中已存在ID为--"+daoId+"--的组件，无法重复添加（您可能配置了同名的@Repository组件，这将会导致异常的发生！）......");
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
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public RepositoryIOC initRepositoryIOC(List<String> repositoryClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		boolean first = true;
		String beanID;
		for (String clzz : repositoryClass) {
			Class<?> repository = Class.forName(clzz);
			if (repository.isAnnotationPresent(Repository.class)) {
				Repository rep = repository.getAnnotation(Repository.class);
				if (!"".equals(rep.value()))
					beanID=rep.value();
				else
					beanID=LuckyUtils.TableToClass1(repository.getSimpleName());
				addRepositoryMap(beanID, PointRunFactory.agent(AgentIOC.getAgentIOC().getAgentMap(), "repository", beanID, repository));
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
