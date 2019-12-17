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
	 * ��ʹ��java -jar����jar��ʱ��Lucky��ʹ��JarFile�ķ���ȥ������ɸѡclasspath�����е�.class�ļ���Ѱ�������
	 * Ч�ʽϸߣ����ǹ涨applicationClass�����д�������İ���
	 * @param applicationClass
	 */
	public static void run(Class<?> applicationClass) {
		Configuration.applicationClass=applicationClass;
		run();
	}
	
	/**
	 * ��ʹ��java -jar����jar��ʱ��Lucky��ʹ��JarFile�ķ���ȥ����classpath�����е�.class�ļ���Ѱ�����
	 * Ч�ʽϵͣ�applicationClass���λ��û������
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
			System.err.println(LuckyUtils.showtime()+"[START-OK]->Embedded Tomcat�����ɹ�,��ʱ"+(end-start)+"ms!");
			ApplicationBeans.createApplicationBeans();
			tomcat.getServer().await();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
	}

}
