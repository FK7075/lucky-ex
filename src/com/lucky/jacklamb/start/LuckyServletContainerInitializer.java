package com.lucky.jacklamb.start;

import java.util.EnumSet;
import java.util.EventListener;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import com.lucky.jacklamb.ioc.ApplicationBeans;
import com.lucky.jacklamb.ioc.config.Configuration;
import com.lucky.jacklamb.ioc.config.ServerConfig;

public class LuckyServletContainerInitializer implements ServletContainerInitializer {
	
	public final ServerConfig serverCfg=Configuration.getConfiguration().getServerConfig();
	
	public LuckyServletContainerInitializer() {
		
	}

	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		ApplicationBeans.createApplicationBeans();
		ServletRegistration.Dynamic servlet;
		FilterRegistration.Dynamic filter;
		String[] mapping;
		for(ServletMapping sm:serverCfg.getServletlist()) {
			servlet=ctx.addServlet(sm.getServletName(), sm.getServlet());
			mapping=new String[sm.getRequestMapping().size()];
			servlet.addMapping(sm.getRequestMapping().toArray(mapping));
		}
		
		for(FilterMapping fm:serverCfg.getFilterlist()) {
			filter=ctx.addFilter(fm.getFilterName(), fm.getFilter());
			mapping=new String[fm.getRequestMapping().size()];
			filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, fm.getRequestMapping().toArray(mapping));
		}
		
		for(EventListener l:serverCfg.getListeners()) {
			ctx.addListener(l);
		}

	}

}
