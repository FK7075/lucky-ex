package com.lucky.jacklamb.start;

import java.util.List;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import com.lucky.jacklamb.ioc.config.ServerConfig;
import com.lucky.jacklamb.utils.LuckyUtils;

public class LuckyApplication {
	
	public static ServerConfig serverCfg=ServerConfig.getServerConfig();
	
	public static void run() {
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
            context.addServletMappingDecoded(sm.getRequestMapping(),sm.getServletName());
        }
		try {
			tomcat.init();
			tomcat.start();
			long end= System.currentTimeMillis();
			System.out.println(LuckyUtils.showtime()+" Embedded Tomcat启动成功，用时:"+(end-start)+"ms!");
			tomcat.getServer().await();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
		
	}

}
