package com.lucky.jacklamb.start;

import java.util.List;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import com.lucky.jacklamb.ioc.ApplicationBeans;
import com.lucky.jacklamb.ioc.config.Configuration;
import com.lucky.jacklamb.ioc.config.ScanConfig;
import com.lucky.jacklamb.ioc.config.ServerConfig;
import com.lucky.jacklamb.utils.LuckyUtils;

public class LuckyApplication {
	
	public static void run(Class<?> applicationClass) {
		ServerConfig serverCfg=Configuration.getServerConfig();
		long start= System.currentTimeMillis();
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(serverCfg.getPort());
		tomcat.getHost().setAutoDeploy(false);
        StandardContext context = new StandardContext();
        context.setPath(serverCfg.getContextPath());
        context.addLifecycleListener(new Tomcat.FixContextListener());
        tomcat.getHost().addChild(context);
        List<ServletMapping> servletlist = serverCfg.getServletlist();
        for(ServletMapping sm:servletlist) {
            tomcat.addServlet(serverCfg.getContextPath(),sm.getServletName(),sm.getServlet());
            for(String map:sm.getRequestMapping())
            	context.addServletMappingDecoded(map,sm.getServletName());
        }
		try {
			tomcat.init();
			tomcat.start();
			long end= System.currentTimeMillis();
			System.err.println(LuckyUtils.showtime()+"[START-OK]->Embedded Tomcat启动成功,用时"+(end-start)+"ms!");
			ScanConfig.defaultScanConfig().setApplication(applicationClass);
			System.out.println(Configuration.getScanConfig());
			ApplicationBeans.createApplicationBeans();
			tomcat.getServer().await();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
		
	}

}
