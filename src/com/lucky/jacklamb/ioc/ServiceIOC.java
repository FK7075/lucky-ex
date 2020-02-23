package com.lucky.jacklamb.ioc;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lucky.jacklamb.annotation.ioc.Service;
import com.lucky.jacklamb.aop.util.PointRunFactory;
import com.lucky.jacklamb.exception.NotAddIOCComponent;
import com.lucky.jacklamb.exception.NotFindBeanException;
import com.lucky.jacklamb.utils.LuckyUtils;

public class ServiceIOC extends ComponentFactory{
	
	private static Logger log=Logger.getLogger(ServiceIOC.class);

	private Map<String, Object> serviceMap;

	private List<String> serviceIDS;
	
	public ServiceIOC() {
		serviceMap=new HashMap<>();
		serviceIDS=new ArrayList<>();
	}

	public boolean containId(String id) {
		return serviceIDS.contains(id);
	}

	public Object getServiceBean(String id) {
		if (!containId(id))
			throw new NotFindBeanException("��Service(ioc)�������Ҳ���IDΪ--" + id + "--��Bean...");
		return serviceMap.get(id);
	}

	public Map<String, Object> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<String, Object> serviceMap) {
		this.serviceMap = serviceMap;
	}

	public void addServiceMap(String id, Object object) {
		if(containId(id))
			throw new NotAddIOCComponent("Service(ioc)�������Ѵ���IDΪ--"+id+"--��������޷��ظ���ӣ�������������ͬ����@Service������⽫�ᵼ���쳣�ķ�������......");
		serviceMap.put(id, object);
		addServiceIDS(id);
	}

	public List<String> getServiceIDS() {
		return serviceIDS;
	}

	public void setServiceIDS(List<String> serviceIDS) {
		this.serviceIDS = serviceIDS;
	}

	public void addServiceIDS(String id) {
		serviceIDS.add(id);
	}

	/**
	 * ����Service�����ServiceIOC����
	 * 
	 * @param serviceClass
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public void initServiceIOC(List<Class<?>> serviceClass) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		String beanID;
		for (Class<?> service : serviceClass) {
			if (service.isAnnotationPresent(Service.class)) {
				Service ser = service.getAnnotation(Service.class);
				if (!"".equals(ser.value()))
					beanID=ser.value();
				else
					beanID=LuckyUtils.TableToClass1(service.getSimpleName());
				Object aspect = PointRunFactory.Aspect(AspectAOP.getAspectIOC().getAspectMap(), "service", beanID, service);
				addServiceMap(beanID, aspect);
				log.info("@Service         =>   [id="+beanID+" class="+aspect+"]");
			}
		}
	}
}
