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
 * ɨ���������ð��������е�IOC��������ص���Ӧ��IOC������
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
	 * ���Ʒ�ת
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
	 * ����ע��
	 */
	public void dependencyInjection() {
		try {
			injection(controllerIOC.getControllerMap());
			injection(serviceIOC.getServiceMap());
			injection(repositoryIOC.getRepositoryMap());
			injection(appIOC.getAppMap());
		} catch (IllegalArgumentException e) {
			throw new InjectionPropertiesException("����ע���쳣��ע���������ԭ�������Ͳ�ƥ��....");
		} catch (IllegalAccessException e) {
			throw new InjectionPropertiesException("����ע���쳣��û��Ȩ�޷��ʸ�����....");
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
	 * �õ��йذ�ɨ���������Ϣ
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
	 * ÿ�δ�������ʱΪControllerע��Model��Request��Response��Session��������
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
	 * ���������ע��
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
						if(one&&two) {//K-V�����ǻ�������
							for(String z:val) {
								String[] kv=z.split(":");
								map.put(beans.getBean(kv[0]), beans.getBean(kv[1]));
							}
						}else if(one&&!two) {//V�ǻ�������
							for(String z:val) {
								String[] kv=z.split(":");
								map.put(beans.getBean(kv[0]), LuckyUtils.typeCast(kv[1], fx[1]));
							}
						}else if(!one&&two) {//K�ǻ�������
							for(String z:val) {
								String[] kv=z.split(":");
								map.put(LuckyUtils.typeCast(kv[0], fx[0]),beans.getBean(kv[1]));
							}
						}else {//K-V���ǻ�������
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


