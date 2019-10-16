package com.lucky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lucky.enums.Type;

/**
 * ���ڽ�������ʵ�����ӳ���ϵ
 * 	value ��ʶ����
 * 	type ��������
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {
	String value() default "";
	Type type() default Type.DEFAULT;
}
