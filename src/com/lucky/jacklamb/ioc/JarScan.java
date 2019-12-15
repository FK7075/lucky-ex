package com.lucky.jacklamb.ioc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarScan implements Scan {
	
//	private Class<?> clzz;
//
//	public JarScan(Class<?> clzz) {
//		this.clzz=clzz;
//	}
	
	@Override
	public List<String> loadComponent(List<String> suffixs) {
		List<String> className=new ArrayList<>();
//		String allname=clzz.getName();
//		String simpleName=clzz.getSimpleName();
//		String prefix=allname.substring(0, allname.length()-simpleName.length()).replaceAll("\\.", "/");
		String jarpath = JarScan.class.getResource("").getPath();
		jarpath=jarpath.substring(6, jarpath.indexOf("!"));
		JarFile jarFile=null;
		
		try {
			jarFile=new JarFile(jarpath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration<JarEntry> entrys = jarFile.entries();
		String cpath;
		while(entrys.hasMoreElements()) {
			JarEntry entry = entrys.nextElement();
			String name=entry.getName();
			//&&name.startsWith(prefix)
			if(name.endsWith(".class")) {
				name=name.substring(0, name.length()-6);
				cpath=name.substring(0,name.lastIndexOf("/"));
				for(String suf:suffixs) {
					if(cpath.endsWith(suf)) {
						className.add(name.replaceAll("/", "\\."));
						break;
					}
				}
			}
		}
		return className;
	}
	

	public static void main(String[] args) {
		String allname=JarScan.class.getName();
		String simpleName=JarScan.class.getSimpleName();
		String prefix=allname.substring(0, allname.length()-simpleName.length()).replaceAll("/", "\\.");
		System.out.println(prefix);
	}

}
