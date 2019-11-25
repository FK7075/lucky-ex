package com.lucky.jacklamb.annotation.db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ORM��Mapper�ӿ���ʹ�ã�����һ��ɾ�������ݿ����
 * 	value������Ԥ�����SQL ��eg: DELETE * FROM book WHERE bname=? AND price>?��
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Delete {
	String value() default "";
	boolean byid() default false;
}
