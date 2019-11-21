package com.lucky.jacklamb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ORM������ȷ������������ֶε�ӳ���ϵ
 * 	value:���Զ�Ӧ���ݿ��е��ֶ���
 *  length ����ʱ���ֶεĳ���
 *  allownull ����ʱ�Ƿ�������ֶ�ΪNULL(Ĭ������true)
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
	String value() default "";
	int length() default 35;
	boolean allownull() default true;
}
