package com.lucky.jacklamb.ioc;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lucky.jacklamb.annotation.aop.Aspect;
import com.lucky.jacklamb.annotation.ioc.BeanFactory;
import com.lucky.jacklamb.annotation.ioc.Component;
import com.lucky.jacklamb.annotation.ioc.Controller;
import com.lucky.jacklamb.annotation.ioc.Repository;
import com.lucky.jacklamb.annotation.ioc.Service;
import com.lucky.jacklamb.annotation.orm.mapper.Mapper;
import com.lucky.jacklamb.aop.proxy.Point;

/**
 * ��������ʱ��Ĭ�ϰ�ɨ��
 * @author fk-7075
 *
 */
public class PackageScan extends Scan {
	
	private String projectPath;
	
	private String fileProjectPath;
	
	public PackageScan() {
		super();
		projectPath=PackageScan.class.getClassLoader().getResource("").getPath();
		fileProjectPath=projectPath.substring(1);
		projectPath=projectPath.replaceAll("\\\\", "/").substring(1,projectPath.length()-1);
	}
	
	/**
	 * �ҵ�����Ioc��������ļ��еİ�·���������뵽�ļ�����
	 * @param components
	 * @param suffix
	 */
	public void loadComponent(List<Class<?>> components,String...suffix) {
		List<String> clist=new ArrayList<>();
		findDafaultFolder(clist,projectPath,suffix);
		addClassPath(components,clist);
	}
	
	/**
	 * �ҵ�����Ioc��������ļ��еİ�·���������뵽�ļ�����
	 * @param suffix �Զ���İ���׺������
	 * @return
	 */
	public List<Class<?>> loadComponent(List<String> suffixlist) {
		String[] suffix=new String[suffixlist.size()];
		suffixlist.toArray(suffix);
		List<Class<?>> components=new ArrayList<>();
		List<String> clist=new ArrayList<>();
		findDafaultFolder(clist,projectPath,suffix);
		addClassPath(components,clist);
		return components;
	}
	
	
	/**
	 * �ҵ�����Mapper��������ļ��еľ���·���������뵽�ļ�����
	 * @param mappers mapper�ӿ����
	 */
	public void loadMapper(List<Class<?>> mappers,String...suffix) {
		List<String> mlist=new ArrayList<>();
		findDafaultFolder(mlist,projectPath,suffix);
		addClassPath(mappers,mlist);
	}
	
	/**
	 * �õ���������İ�·��,����������
	 * @param components ����
	 * @param paths ��������ļ��еľ���·��
	 */
	private void addClassPath(List<Class<?>> components,List<String> paths) {
		for(String path:paths) {
			File file=new File(path);
			File[] listFiles = file.listFiles();
			for(File f:listFiles) {
				if(f.getName().endsWith(".class")) {
					String clzzpath=f.getAbsolutePath();
					clzzpath.substring(0, clzzpath.length()-6);
					String u=(projectPath+"/").replaceAll("/", "\\\\");
					clzzpath=clzzpath.replace(u, "");
					clzzpath=clzzpath.replaceAll("\\\\", "\\.");
					clzzpath=clzzpath.substring(0, clzzpath.length()-6);
					try {
						components.add(Class.forName(clzzpath));
					} catch (ClassNotFoundException e) {
						throw new RuntimeException("����ش��󣬴���path:"+clzzpath, e);
					}
				}
			}
				
		}
	}
	
	
	/**
	 * �ҵ��ļ���Ϊsuffix���ļ���
	 * @param defaultPaths ���ڴ洢Ŀ���ļ��о���·���ļ���
	 * @param path ��ʼ�ļ��еľ���·��
	 * @param suffix ��Ҫ�õ����ļ�����
	 */
	private void findDafaultFolder(List<String> defaultPaths,String path,String...suffixs){
		List<String> suffixList=Arrays.asList(suffixs);
		File file=new File(path);
		File[] listFiles = file.listFiles();
		for(File f:listFiles) {
			if(f.isDirectory()) {
				if(isSuffix(suffixList,f.getName().toLowerCase()))
					defaultPaths.add(f.getAbsolutePath());
				else
					findDafaultFolder(defaultPaths,path+"/"+f.getName(),suffixs);
			}
		}
	}
	
	/**
	 * �ж�str�Ƿ���suffixs�е�ĳ��Ԫ�ؽ�β
	 * @param suffixs
	 * @param str
	 * @return
	 */
	private boolean isSuffix(List<String> suffixs,String str) {
		for(String suffix:suffixs) {
			if(str.endsWith(suffix))
				return true;
		}
		return false;
	}
	
	@Override
	public void autoScan() {
		try {
			fileScan(fileProjectPath);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void fileScan(String path) throws ClassNotFoundException {
		File bin=new File(path);
		File[] listFiles = bin.listFiles();
		for(File file:listFiles) {
			if(file.isDirectory()) {
				fileScan(path+"/"+file.getName());
			}else if(file.getAbsolutePath().endsWith(".class")) {
				file.getAbsolutePath();
				String className=file.getAbsolutePath().replaceAll("\\\\", "/").replaceAll(fileProjectPath, "").replaceAll("/", "\\.");
				className=className.substring(0,className.length()-6);
				Class<?> fileClass=Class.forName(className);
				if(fileClass.isAnnotationPresent(Controller.class))
					controllerClass.add(fileClass);
				else if(fileClass.isAnnotationPresent(Service.class))
					serviceClass.add(fileClass);
				else if(fileClass.isAnnotationPresent(Repository.class)||fileClass.isAnnotationPresent(Mapper.class))
					repositoryClass.add(fileClass);
				else if(fileClass.isAnnotationPresent(Component.class)||fileClass.isAnnotationPresent(BeanFactory.class))
					componentClass.add(fileClass);
				else if(fileClass.isAnnotationPresent(Aspect.class)||Point.class.isAssignableFrom(fileClass))
					aspectClass.add(fileClass);
				else
					continue;
			}
		}
		
	} 
}


