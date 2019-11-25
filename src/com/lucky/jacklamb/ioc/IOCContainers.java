package com.lucky.jacklamb.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import com.lucky.jacklamb.annotation.ioc.Autowired;
import com.lucky.jacklamb.annotation.ioc.Value;
import com.lucky.jacklamb.aop.defaultexpand.CacheExpand;
import com.lucky.jacklamb.exception.InjectionPropertiesException;
import com.lucky.jacklamb.ioc.config.ScanConfig;
import com.lucky.jacklamb.servlet.Model;
import com.lucky.jacklamb.utils.ArrayCast;
import com.lucky.jacklamb.utils.LuckyUtils;

/**
 * 扫描所有配置包，将所有的IOC组件都加载到相应的IOC容器中
 * @author DELL
 *
 */
public class IOCContainers {
	
	private AgentIOC agentIOC;
	
	private RepositoryIOC repositoryIOC;
	
	private ServiceIOC serviceIOC;
	
	private ComponentIOC appIOC;
	
	private ControllerIOC controllerIOC;
	
	private ScanConfig scanConfig;
	
	public void init() {
		agentIOC=new AgentIOC();
		scanConfigToComponentIOC();
		inversionOfControl();
		dependencyInjection();
		CacheExpand cacheAgent=new CacheExpand();
		cacheAgent.cacheAgent();
	}
	
	/**
	 * 控制反转
	 */
	public void inversionOfControl() {
		try {
			initComponentIOC();
			initControllerIOC();
			initServiceIOC();
			initRepositoryIOC();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 依赖注入
	 */
	public void dependencyInjection() {
		try {
			injection(controllerIOC.getControllerMap());
			injection(serviceIOC.getServiceMap());
			injection(repositoryIOC.getRepositoryMap());
			injection(appIOC.getAppMap());
		} catch (IllegalArgumentException e) {
			throw new InjectionPropertiesException("属性注入异常，注入的属性与原属性类型不匹配....");
		} catch (IllegalAccessException e) {
			throw new InjectionPropertiesException("属性注入异常，没有权限访问该属性....");
		}
		
	}

	public AgentIOC getAgentIOC() {
		return agentIOC;
	}

	public void setAgentIOC(AgentIOC agentIOC) {
		this.agentIOC = agentIOC;
	}

	public RepositoryIOC getRepositoryIOC() {
		return repositoryIOC;
	}

	public void setRepositoryIOC(RepositoryIOC repositoryIOC) {
		this.repositoryIOC = repositoryIOC;
	}

	public ServiceIOC getServiceIOC() {
		return serviceIOC;
	}

	public void setServiceIOC(ServiceIOC serviceIOC) {
		this.serviceIOC = serviceIOC;
	}

	public ComponentIOC getAppIOC() {
		return appIOC;
	}

	public void setAppIOC(ComponentIOC appIOC) {
		this.appIOC = appIOC;
	}
	
	public void addComponent(String key,Object value) {
		this.appIOC.addAppMap(key, value);
	}

	public ControllerIOC getControllerIOC() {
		return controllerIOC;
	}

	public void setControllerIOC(ControllerIOC controllerIOC) {
		this.controllerIOC = controllerIOC;
	}

	/**
	 * 得到有关包扫描的配置信息
	 */
	public void scanConfigToComponentIOC() {
		scanConfig=ScanConfig.getScanConfig();
	}
	
	public void initComponentIOC() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		appIOC=new ComponentIOC();
		appIOC.initComponentIOC(PackageScan.getPackageScan().loadComponent(scanConfig.getComponentPackSuffix()));
	}
	
	public void initControllerIOC() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		controllerIOC=new ControllerIOC();
		controllerIOC.initControllerIOC(PackageScan.getPackageScan().loadComponent(scanConfig.getControllerPackSuffix())).methodHanderSetting();
	}
	
	public void initServiceIOC() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		serviceIOC=new ServiceIOC();
		serviceIOC.initServiceIOC(PackageScan.getPackageScan().loadComponent(scanConfig.getServicePackSuffix()));
	}
	public void initRepositoryIOC() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		repositoryIOC=new RepositoryIOC();
		repositoryIOC.initRepositoryIOC(PackageScan.getPackageScan().loadComponent(scanConfig.getRepositoryPackSuffix()));
	}
	
	/**
	 * 每次处理请求时为Controller注入Model、Request、Response和Session对象属性
	 * @param object
	 * @param model
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void autowReqAdnResp(Object object,Model model) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields=object.getClass().getDeclaredFields();
		for(Field field:fields) {
			field.setAccessible(true);
			if(Model.class.isAssignableFrom(field.getType())) {
				field.set(object, model);
			}else if(HttpSession.class.isAssignableFrom(field.getType())) {
				field.set(object, model.getSession());
			}else if(ServletRequest.class.isAssignableFrom(field.getType())) {
				field.set(object, model.getRequest());
			}else if(ServletResponse.class.isAssignableFrom(field.getType())) {
				field.set(object, model.getResponse());
			}else {
				continue;
			}
		}
	}
	
	/**
	 * 组件的属性注入
	 * @param componentMap
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void injection(Map<String,Object> componentMap) throws IllegalArgumentException, IllegalAccessException {
		ApplicationBeans beans=ApplicationBeans.createApplicationBeans();
		for(Entry<String,Object> entry:componentMap.entrySet()) {
			Object component=entry.getValue();
			Class<?> componentClass=component.getClass();
			Field[] fields=componentClass.getDeclaredFields();
			for(Field field:fields) {
				field.setAccessible(true);
				Class<?> fieldClass=field.getType();
				if(field.isAnnotationPresent(Autowired.class)) {
					Autowired auto=field.getAnnotation(Autowired.class);
					if("".equals(auto.value())) {
						field.set(component, beans.getBean(fieldClass));
					}else {
						field.set(component, beans.getBean(auto.value()));
					}
				}else if(field.isAnnotationPresent(Value.class)) {
					Value value=field.getAnnotation(Value.class);
					String[] val = value.value();
					if(fieldClass.isArray()) {
						field.set(component,ArrayCast.strArrayChange(val, fieldClass));
					}else if(List.class.isAssignableFrom(fieldClass)) {
						List<Object> list=new ArrayList<>();
						String fx=ArrayCast.getFieldGenericType(field)[0];
						if(fx.endsWith("$ref")) {
							for(String z:val) {
								list.add(beans.getBean(z));
							}
						}else {
							for(String z:val) {
								list.add(LuckyUtils.typeCast(z, fx));
							}
						}
						field.set(component, list);
					}else if(Set.class.isAssignableFrom(fieldClass)) {
						Set<Object> set=new HashSet<>();
						String fx=ArrayCast.getFieldGenericType(field)[0];
						if(fx.endsWith("$ref")) {
							for(String z:val) {
								set.add(beans.getBean(z));
							}
						}else {
							for(String z:val) {
								set.add(LuckyUtils.typeCast(z, fx));
							}
						}
						field.set(component, set);
					}else if(Map.class.isAssignableFrom(fieldClass)) {
						Map<Object,Object> map=new HashMap<>();
						String[] fx=ArrayCast.getFieldGenericType(field);
						boolean one=fx[0].endsWith("$ref");
						boolean two=fx[1].endsWith("$ref");
						if(one&&two) {//K-V都不是基本类型
							for(String z:val) {
								String[] kv=z.split(":");
								map.put(beans.getBean(kv[0]), beans.getBean(kv[1]));
							}
						}else if(one&&!two) {//V是基本类型
							for(String z:val) {
								String[] kv=z.split(":");
								map.put(beans.getBean(kv[0]), LuckyUtils.typeCast(kv[1], fx[1]));
							}
						}else if(!one&&two) {//K是基本类型
							for(String z:val) {
								String[] kv=z.split(":");
								map.put(LuckyUtils.typeCast(kv[0], fx[0]),beans.getBean(kv[1]));
							}
						}else {//K-V都是基本类型
							for(String z:val) {
								String[] kv=z.split(":");
								map.put(LuckyUtils.typeCast(kv[0], fx[0]), LuckyUtils.typeCast(kv[1], fx[1]));
							}
						}
						field.set(component, map);
					}else {
						field.set(component, LuckyUtils.typeCast(val[0], fieldClass.getSimpleName()));
					}
					
				}
			}
		}
	}
}


