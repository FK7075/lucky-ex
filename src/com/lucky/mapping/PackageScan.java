package com.lucky.mapping;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 不做配置时的默认包扫描
 * @author fk-7075
 *
 */
public class PackageScan {

	private String projectPath;
	
	public PackageScan() {
		projectPath=this.getClass().getClassLoader().getResource("").getPath();
		projectPath=projectPath.replaceAll("\\\\", "/").substring(1,projectPath.length()-1);
	}
	
	/**
	 * 找到所有Ioc组件所在文件夹的包路径，并存入到的集合中
	 * @param components 普通Ioc组件
	 */
	public void loadComponent(List<String> components,String...suffix) {
		List<String> clist=new ArrayList<>();
		findDafaultFolder(clist,projectPath,suffix);
		addClassPath(components,clist);
	}
	
	/**
	 * 找到所有Mapper组件所在文件夹的绝对路径，并存入到的集合中
	 * @param mappers mapper接口组件
	 */
	public void loadMapper(List<String> mappers) {
		List<String> mlist=new ArrayList<>();
		findDafaultFolder(mlist,projectPath,"mapper");
		addClassPath(mappers,mlist);
	}
	
	/**
	 * 得到所有组件的包路径,放入容器中
	 * @param components 容器
	 * @param paths 组件所在文件夹的绝对路径
	 */
	private void addClassPath(List<String> components,List<String> paths) {
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
					components.add(clzzpath);
				}
			}
				
		}
	}
	
	
	/**
	 * 找到文件名为suffix的文件夹
	 * @param defaultPaths 用于存储目标文件夹绝对路径的集合
	 * @param path 开始文件夹的绝对路径
	 * @param suffix 需要得到的文件夹名
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
	 * 判断str是否以suffixs中的某个元素结尾
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
}


