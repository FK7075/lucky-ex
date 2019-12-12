package com.lucky.jacklamb.junit;

import java.lang.reflect.Field;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.lucky.jacklamb.annotation.ioc.Autowired;
import com.lucky.jacklamb.ioc.ApplicationBeans;

public class LuckyJUnit4ClassRunner extends BlockJUnit4ClassRunner{
	
	private ApplicationBeans applicationBeans;
	
	public LuckyJUnit4ClassRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
	}

	@Override
	protected Object createTest() throws Exception {
		Object createTest = super.createTest();
		applicationBeans=ApplicationBeans.createApplicationBeans();
		applicationBeans.printBeans();
		return createTestObject(applicationBeans,createTest);
	}
	
	private final Object createTestObject(ApplicationBeans applicationBeans,Object testObject) throws InstantiationException, IllegalAccessException {
		Field[] allFields = testObject.getClass().getDeclaredFields();
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
