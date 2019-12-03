package com.lucky.jacklamb.annotation.orm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ORM��������Ҫ��ע��
 * 	value ����ӳ��
 * 	alias ����,ֻ��ʹ��SqlCore��query()����ʱ��Ч
 * 	primary �����������
 *  index �����ͨ����
 *  unique ���Ψһֵ����
 *  fulltext ���ȫ������
 *  cascadeDelete �������ӱ���ɾ��
 *  cascadeUpdate �������ӱ�������
 * @author fk-7075
 *
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
	String value() default "";
	String alias() default "";
	String primary() default "";
	String[] index() default {};
	String[] unique() default {};
	String[] fulltext() default {};
	boolean cascadeDelete() default false;
	boolean cascadeUpdate() default false;
}
