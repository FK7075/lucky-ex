package com.lucky.jacklamb.annotation.orm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ���ڽ�������ʵ�����ӳ���ϵ
 * 	value ��ʶ���
 * 	pojo �����Ӧ��ʵ����Class
 *  length ����ʱ���ֶεĳ���
 *  allownull ����ʱ�Ƿ�������ֶ�ΪNULL(Ĭ������true)
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Key {
	String value() default "";
	int length() default 35;
	boolean allownull() default true;
	Class<?> pojo();
}
