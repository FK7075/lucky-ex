package com.lucky.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryIOC {
	
	private Map<String,Object> repositoryMap;
	
	private List<String> repositoryIDS;
	
	private Map<String,Object> mapperMap;
	
	private List<String> mapperIDS;

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
	

}
