package com.lucky.jacklamb.annotation.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ����һ��������ǿ
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Around {
	
	/**
	 * ������ǿ������Ψһ���(Ĭ��ֵ��������)
	 * @return
	 */
	String value() default "";
	
	/**
	 * ��������(Class)����ǿ����ִ�еķ�Χ��������λ��Ҫ�������ʵ��<br>
	 * pointCutClass��ֵ����������ǰ׺��ʼ,���ֵʹ��","�ָ�:<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;
	 * ioc:��ʾ��ǿһ�ֻ�������͵��������,��ѡֵ��:[controller,service,repository,component] eg:mateClass="ioc:component,service"<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;
	 * id:��ʾ��ǿһ������ָ��ID��IOC���,eg:mateClass="id:beanId1,beanId2"<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;
	 * path:��ʾ��ǿĳ��·���µ�����IOC�����eg:mateClass="path:com.lucky.*" OR mateClass="path:com.lucky.User"<br>
	 * @return
	 */
	String pointCutClass();
	
	/**
	 * �����е�(Method)�� ��ǿ����ִ�еķ�Χ��������λ��Ҫ�������ʵ���һЩ���巽��<br>
	 * ���ֵʹ��","�ָ�,֧��"*"��"!"<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;
	 * ��������λ����Ҫ��ǿ�ķ�������eg:mateMethod="method1,method2"<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;
	 * ������+�������Ͷ�λ����Ҫ��ǿ�ķ�����+�����б�eg:mateMethod="method1(String,int),method2(User,Double)"<br>
	 * @return
	 */
	String pointCutMethod();
	
}
