package com.lucky.jacklamb.annotation.orm.mapper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ORM��Mapper�ӿ���ʹ�ã�����һ�����ӵ����ݿ����
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Insert {
	
	/**
	 * ����Ԥ�����SQL ��eg: INSERT INTO book(bname,price,author) VALUES(?,?,?)��
	 * @return
	 */
	String value() default "";
	
	/**
	 * ��������ģʽ����������(��������α���ΪList<T>)
	 * @return
	 */
	boolean batch() default false;
	
	/**
	 * �����������Զ���ֵ,Ĭ��Ϊfalse
	 * @return
	 */
	boolean setautoId() default false;
}
