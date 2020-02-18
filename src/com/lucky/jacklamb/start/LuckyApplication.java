package com.lucky.jacklamb.start;

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
	 * ��ʹ��java -jar����jar��ʱ��Lucky��ʹ��JarFile�ķ���ȥ������ɸѡclasspath�����е�.class�ļ���Ѱ�������
	 * Ч�ʽϸߣ����ǹ涨applicationClass�����д�������İ���
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
		tomcat.getHost().setAutoDeploy(false);
        StandardContext context =new StandardContext();
        context.setSessionTimeout(serverCfg.getSessionTimeout());
        context.setPath(serverCfg.getContextPath());
        context.setDocBase(serverCfg.getDocBase());
        context.addLifecycleListener(new Tomcat.DefaultWebXmlListener());
        context.addLifecycleListener(new Tomcat.FixContextListener());
        context.addServletContainerInitializer(new WsSci(), ApplicationBeans.createApplicationBeans().getWebSocketSet());
        context.addServletContainerInitializer(new LuckyServletContainerInitializer(), null);
        tomcat.getHost().addChild(context);
		try {
			tomcat.init();
			tomcat.start();
			long end= System.currentTimeMillis();
			System.err.println(LuckyUtils.showtime()+"[ EMBEDDED-TOMCAT-START-OK  D ]  Starting DocBase [path: "+serverCfg.getDocBase().substring(1)+"]");
			System.err.println(LuckyUtils.showtime()+"[ EMBEDDED-TOMCAT-START-OK  P ]  Starting ProtocolHandler [http-nio-"+serverCfg.getPort()+"]");
			System.err.println(LuckyUtils.showtime()+"[ EMBEDDED-TOMCAT-START-OK  - ]  Tomcat�����ɹ�����ʱ"+(end-start)+"ms!");
			tomcat.getServer().await();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
	}

}
