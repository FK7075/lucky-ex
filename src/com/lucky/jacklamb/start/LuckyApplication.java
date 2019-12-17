package com.lucky.jacklamb.start;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

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
	
	/**
	 * 在使用java -jar运行jar包时，Lucky会使用JarFile的方法去遍历classpath下所有的.class文件来寻找组件
	 * 效率较低，applicationClass类的位置没有限制
	 */
	public static void run() {
		ServerConfig serverCfg=Configuration.getConfiguration().getServerConfig();
		long start= System.currentTimeMillis();
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(serverCfg.getPort());
		tomcat.getHost().setAutoDeploy(false);
        StandardContext context = new StandardContext();
        context.setPath(serverCfg.getContextPath());
        context.setDocBase(serverCfg.getDocBase());
        context.addLifecycleListener(new Tomcat.FixContextListener());
        tomcat.getHost().addChild(context);
        List<ServletMapping> servletlist = serverCfg.getServletlist();
        for(ServletMapping sm:servletlist) {
            tomcat.addServlet(serverCfg.getContextPath(),sm.getServletName(),sm.getServlet());
            for(String map:sm.getRequestMapping()) {
            	context.addServletMappingDecoded(map,sm.getServletName());
            }
        }
        FilterDef filterDef;
        FilterMap filterMap;
        List<FilterMapping> filterlist = serverCfg.getFilterlist();
        for(FilterMapping fm:filterlist) {
        	filterDef=new FilterDef();
        	filterMap=new FilterMap();
        	filterDef.setFilter(fm.getFilter());
        	filterDef.setFilterName(fm.getFilterName());
        	filterMap.setFilterName(fm.getFilterName());
        	for(String filterurl:fm.getRequestMapping())
        		filterMap.addURLPatternDecoded(filterurl);
        	 filterMap.setCharset(Charset.forName("UTF-8"));
        	 context.addFilterDef(filterDef);
        	 context.addFilterMap(filterMap);
        }
		try {
			tomcat.init();
			tomcat.start();
			long end= System.currentTimeMillis();
			System.err.println(LuckyUtils.showtime()+"[START-OK]->Embedded Tomcat启动成功,用时"+(end-start)+"ms!");
			ApplicationBeans.createApplicationBeans();
			tomcat.getServer().await();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
	}

}
