package com.lucky.jacklamb.annotation.orm.mapper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ORM��Mapper�ӿ���ʹ�ã�����һ�����ӵ����ݿ����
 * 	value������Ԥ�����SQL ��eg: INSERT INTO book(bname,price,author) VALUES(?,?,?)��
 * 	batch: ��������ģʽ����������(��������α���ΪList<T>)
 *  setautoId:  �����������Զ���ֵ(Ĭ��Ϊfalse)
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Insert {
	String value() default "";
	boolean batch() default false;
	boolean setautoId() default false;
}
