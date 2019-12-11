package com.lucky.jacklamb.junit;

import java.lang.reflect.Field;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.lucky.jacklamb.annotation.ioc.Autowired;
import com.lucky.jacklamb.ioc.ApplicationBeans;

public class LuckyJUnit4ClassRunner extends BlockJUnit4ClassRunner{
	
	private ApplicationBeans applicationBeans;
	
	private Class<?> testClass;

	public LuckyJUnit4ClassRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
		this.testClass=testClass;
	}

	@Override
	protected Object createTest() throws Exception {
		applicationBeans=ApplicationBeans.createApplicationBeans();
		applicationBeans.printBeans();
		return createTestObject(applicationBeans);
	}
	
	private final Object createTestObject(ApplicationBeans applicationBeans) throws InstantiationException, IllegalAccessException {
		Object testObject = testClass.newInstance();
		Field[] allFields = testClass.getDeclaredFields();
		for(int i=0, l=allFields.length;i<l;i++) {
			if(allFields[i].isAnnotationPresent(Autowired.class)) {
				Autowired auto=allFields[i].getAnnotation(Autowired.class);
				allFields[i].setAccessible(true);
				String id=auto.value();
				if("".equals(id)) {
					allFields[i].set(testObject, applicationBeans.getBean(allFields[i].getType()));
				}else {
					allFields[i].set(testObject,applicationBeans.getBean(id));
				}
			}
		}
		return testObject;
	}

}
