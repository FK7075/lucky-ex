package com.lucky.jacklamb.ioc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpoint;

import com.lucky.jacklamb.annotation.aop.Aspect;
import com.lucky.jacklamb.annotation.ioc.AppConfig;
import com.lucky.jacklamb.annotation.ioc.BeanFactory;
import com.lucky.jacklamb.annotation.ioc.Component;
import com.lucky.jacklamb.annotation.ioc.Controller;
import com.lucky.jacklamb.annotation.ioc.Repository;
import com.lucky.jacklamb.annotation.ioc.Service;
import com.lucky.jacklamb.annotation.mvc.ExceptionHander;
import com.lucky.jacklamb.annotation.orm.mapper.Mapper;
import com.lucky.jacklamb.aop.proxy.Point;
import com.lucky.jacklamb.ioc.config.ApplicationConfig;

public class JarScan extends Scan {
	
	private String jarpath;
	
	private String prefix;


	public JarScan(Class<?> clzz) {
		String allname=clzz.getName();
		String simpleName=clzz.getSimpleName();
		prefix=allname.substring(0, allname.length()-simpleName.length()).replaceAll("\\.", "/");
		jarpath=clzz.getResource("").getPath();
		jarpath=jarpath.substring(6, jarpath.indexOf(".jar!")+4);
	}

	public List<Class<?>> loadComponent(List<String> suffixs) {
		List<Class<?>> className = new ArrayList<>();
		JarFile jarFile = null;

		try {
			jarFile = new JarFile(jarpath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration<JarEntry> entrys = jarFile.entries();
		String cpath;
		while (entrys.hasMoreElements()) {
			JarEntry entry = entrys.nextElement();
			String name = entry.getName();
			if (name.endsWith(".class") && name.startsWith(prefix)) {
				name = name.substring(0, name.length() - 6);
				cpath = name.substring(0, name.lastIndexOf("/"));
				for (String suf : suffixs) {
					if (cpath.endsWith(suf)) {
						String clzzName = name.replaceAll("/", "\\.");
						try {
							className.add(Class.forName(clzzName));
						} catch (ClassNotFoundException e) {
							throw new RuntimeException("¿‡º”‘ÿ¥ÌŒÛ£¨¥ÌŒÛpath:" + clzzName, e);
						}
						break;
					}
				}
			}
		}
		return className;
	}

	@Override
	public void autoScan() {
		JarFile jarFile = null;

		try {
			jarFile = new JarFile(jarpath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration<JarEntry> entrys = jarFile.entries();
		try {
			while (entrys.hasMoreElements()) {
				JarEntry entry = entrys.nextElement();
				String name = entry.getName();
				if (name.endsWith(".class") && name.startsWith(prefix)) {
					name = name.substring(0, name.length() - 6);
					String clzzName = name.replaceAll("/", "\\.");
					Class<?> fileClass = Class.forName(clzzName);
					if (fileClass.isAnnotationPresent(Controller.class))
						controllerClass.add(fileClass);
					else if (fileClass.isAnnotationPresent(Service.class))
						serviceClass.add(fileClass);
					else if (fileClass.isAnnotationPresent(Repository.class)
							|| fileClass.isAnnotationPresent(Mapper.class))
						repositoryClass.add(fileClass);
					else if (fileClass.isAnnotationPresent(Component.class)
							|| fileClass.isAnnotationPresent(BeanFactory.class)
							|| fileClass.isAnnotationPresent(ExceptionHander.class))
						componentClass.add(fileClass);
					else if (fileClass.isAnnotationPresent(Aspect.class) || Point.class.isAssignableFrom(fileClass))
						aspectClass.add(fileClass);
					else {
						try {
							if(fileClass.isAnnotationPresent(ServerEndpoint.class)||ServerApplicationConfig.class.isAssignableFrom(fileClass)||Endpoint.class.isAssignableFrom(fileClass)) {
								webSocketClass.add(fileClass);
							}
						}catch(Exception e) {
							continue;
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void findAppConfig() {
		JarFile jarFile = null;

		try {
			jarFile = new JarFile(jarpath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration<JarEntry> entrys = jarFile.entries();
		try {
		while (entrys.hasMoreElements()) {
			JarEntry entry = entrys.nextElement();
			String name = entry.getName();
			if (name.endsWith(".class") && name.startsWith(prefix)) {
				name = name.substring(0, name.length() - 6);
				String clzzName = name.replaceAll("/", "\\.");
				Class<?> fileClass = Class.forName(clzzName);
				if(ApplicationConfig.class.isAssignableFrom(fileClass)&&fileClass.isAnnotationPresent(AppConfig.class)) {
					appConfig=(ApplicationConfig) fileClass.newInstance();
					break;
				}
			}
		}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
