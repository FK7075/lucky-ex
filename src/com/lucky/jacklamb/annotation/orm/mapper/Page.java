package com.lucky.jacklamb.annotation.orm.mapper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ����һ����ҳ������ʹ��@Page��ע��ҳ��������Զ�ת��Ϊ��ʼλ�ò���[ע�⣺������ҳ��������д�ڲ����б�����]
 * @author fk-7075
 *
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Page {
	String value() default "";
}
