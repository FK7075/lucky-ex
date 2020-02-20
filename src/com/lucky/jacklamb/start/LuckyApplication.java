package com.lucky.jacklamb.start;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.websocket.server.WsSci;

import com.lucky.jacklamb.ioc.ApplicationBeans;
import com.lucky.jacklamb.ioc.config.Configuration;
import com.lucky.jacklamb.ioc.config.ServerConfig;
import com.lucky.jacklamb.utils.LuckyUtils;

public class LuckyApplication {
	
	/**
	 * 在使用java -jar运行jar包时，Lucky会使用JarFile的方法去遍历并筛选classpath下所有的.class文件来寻找组件。
	 * 效率较高，但是规定applicationClass类必须写在最外层的包下
	 * @param applicationClass
	 */
	public static void run(Class<?> applicationClass) {
		Configuration.applicationClass=applicationClass;
		run();
	}
	
	private static void run() {
		ServerConfig serverCfg=Configuration.getConfiguration().getServerConfig();
		long start= System.currentTimeMillis();
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(serverCfg.getPort());
		tomcat.setBaseDir(serverCfg.getBaseDir());
		tomcat.getHost().setAutoDeploy(false);
		tomcat.getServer().setPort(serverCfg.getClosePort());
		tomcat.getServer().setShutdown(serverCfg.getShutdown());
        StandardContext context =new StandardContext();
        context.setSessionTimeout(serverCfg.getSessionTimeout());
        context.setPath(serverCfg.getContextPath());
        String docBase = serverCfg.getDocBase();
		File doc=new File(docBase);
		if(!doc.isDirectory())
			doc.mkdirs();
        context.setDocBase(docBase);
        context.setSessionCookieName("Lucky-Tomcat");
        context.addLifecycleListener(new Tomcat.DefaultWebXmlListener());
        context.addLifecycleListener(new Tomcat.FixContextListener());
        context.addServletContainerInitializer(new WsSci(), ApplicationBeans.createApplicationBeans().getWebSocketSet());
        context.addServletContainerInitializer(new LuckyServletContainerInitializer(), null);
        tomcat.getHost().addChild(context);
		try {
			tomcat.init();
			tomcat.start();
			System.err.println(LuckyUtils.showtime()+"[ EMBEDDED-TOMCAT-START-OK  S ]  Starting sessionTimeout ["+serverCfg.getSessionTimeout()+"min]");
			System.err.println(LuckyUtils.showtime()+"[ EMBEDDED-TOMCAT-START-OK  S ]  Starting shutdown-port ["+serverCfg.getClosePort()+"]");
			System.err.println(LuckyUtils.showtime()+"[ EMBEDDED-TOMCAT-START-OK  C ]  Starting shutdown-command [\""+serverCfg.getShutdown()+"\"]");
			System.err.println(LuckyUtils.showtime()+"[ EMBEDDED-TOMCAT-START-OK  D ]  Starting BaseDir [path: "+serverCfg.getBaseDir().substring(1)+"]");
			System.err.println(LuckyUtils.showtime()+"[ EMBEDDED-TOMCAT-START-OK  D ]  Starting DocBase [path: "+docBase.substring(1)+"]");
			System.err.println(LuckyUtils.showtime()+"[ EMBEDDED-TOMCAT-START-OK  C ]  Starting contextPath [\""+serverCfg.getContextPath()+"\"]");
			System.err.println(LuckyUtils.showtime()+"[ EMBEDDED-TOMCAT-START-OK  P ]  Starting ProtocolHandler [http-nio-"+serverCfg.getPort()+"]");
			long end= System.currentTimeMillis();
			System.err.println(LuckyUtils.showtime()+"[ EMBEDDED-TOMCAT-START-OK  - ]  Tomcat启动成功！用时"+(end-start)+"ms!");
			tomcat.getServer().await();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
	}

}
