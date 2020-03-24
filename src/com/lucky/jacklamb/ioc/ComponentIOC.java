package com.lucky.jacklamb.ioc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lucky.jacklamb.annotation.ioc.Bean;
import com.lucky.jacklamb.annotation.ioc.BeanFactory;
import com.lucky.jacklamb.annotation.ioc.Component;
import com.lucky.jacklamb.annotation.mvc.ExceptionHander;
import com.lucky.jacklamb.aop.util.PointRunFactory;
import com.lucky.jacklamb.exception.NotAddIOCComponent;
import com.lucky.jacklamb.exception.NotFindBeanException;
import com.lucky.jacklamb.utils.LuckyUtils;

/**
 * ��ͨIOC�������
 * 
 * @author DELL
 *
 */
public class ComponentIOC extends ComponentFactory {
	
	private static Logger log=Logger.getLogger(ComponentIOC.class);

	private Map<String, Object> appMap;

	private List<String> appIDS;
	
	public ComponentIOC() {
		appMap=new HashMap<>();
		appIDS=new ArrayList<>();
	}

	public boolean containId(String id) {
		return appIDS.contains(id);
	}

	public Object getComponentBean(String id) {
		if (!containId(id))
			throw new NotFindBeanException("��Component(ioc)�������Ҳ���IDΪ--" + id + "--��Bean...");
		return appMap.get(id);

	}

	public Map<String, Object> getAppMap() {
		return appMap;
	}

	public void setAppMap(Map<String, Object> appMap) {
		this.appMap = appMap;
	}

	public void addAppMap(String id, Object object) {
		if(containId(id))
			throw new NotAddIOCComponent("Component(ioc)�������Ѵ���IDΪ--"+id+"--��������޷��ظ���ӣ�������������ͬ����@Component������⽫�ᵼ���쳣�ķ�������......");
		appMap.put(id, object);
		addAppIDS(id);
	}

	public List<String> getAppIDS() {
		return appIDS;
	}

	public void setAppIDS(List<String> appIDS) {
		this.appIDS = appIDS;
	}

	public void addAppIDS(String id) {
		appIDS.add(id);
	}

	/**
	 * ����Component���
	 * 
	 * @param componentClass
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public void initComponentIOC(List<Class<?>> componentClass)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String beanID;
		for (Class<?> component : componentClass) {
			if (component.isAnnotationPresent(Component.class)) {
				Component com = component.getAnnotation(Component.class);
				if (!"".equals(com.value())) {
					beanID=com.value();
				} else {
					beanID=LuckyUtils.TableToClass1(component.getSimpleName());
				}
				Object aspect = PointRunFactory.Aspect(AspectAOP.getAspectIOC().getAspectMap(), "component", beanID, component);
				addAppMap(beanID, aspect);
				log.info("@Component       =>   [id="+beanID+" class="+aspect+"]");
			} else if (component.isAnnotationPresent(BeanFactory.class)) {
				Object obj = component.newInstance();
				Method[] methods=component.getDeclaredMethods();
				for(Method met:methods) {
					if(met.isAnnotationPresent(Bean.class)) {
						Object invoke = met.invoke(obj);
						Bean bean=met.getAnnotation(Bean.class);
						if("".equals(bean.value())) {
							String Id=component.getSimpleName()+"."+met.getName();
							addAppMap(Id, invoke);
							log.info("@Bean            =>   [id="+Id+" class="+invoke+"]");
						}else {
							addAppMap(bean.value(),invoke);
							log.info("@Bean            =>   [id="+bean.value()+" class="+invoke+"]");
						}
						
					}
				}
			}else if(component.isAnnotationPresent(ExceptionHander.class)) {
				Object aspect = PointRunFactory.Aspect(AspectAOP.getAspectIOC().getAspectMap(), "component", "exceptionHand", component);
				ExceptionHander annotation = component.getAnnotation(ExceptionHander.class);
				if (!"".equals(annotation.id())) {
					beanID=annotation.id();
				} else {
					beanID=LuckyUtils.TableToClass1(component.getSimpleName());
				}
				addAppMap(beanID,aspect);
				log.info("@ExceptionHander =>   [id="+beanID+" ,class="+aspect+"]");
			}else {
				continue;
			}
		}
	}

}
