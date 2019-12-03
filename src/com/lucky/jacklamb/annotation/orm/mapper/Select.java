package com.lucky.jacklamb.annotation.orm.mapper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ORM��Mapper�ӿ���ʹ�ã�����һ����ѯ�����ݿ����
 * 	value������Ԥ�����SQL (eg: SELECT * FROM book WHERE bid=?)
 * 	sResults:(showResults)������Ҫ��ѯ���ֶ�
 * 	hResults:(hiddenResults)����Ҫ���ص��ֶ�
 * 	ע��sResults��hResults����ͬʱ��ʹ��
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Select {
	String value() default "";
	String[] sResults() default {};
	String[] hResults() default {};
	boolean byid() default false;
}
