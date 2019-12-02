package com.lucky.jacklamb.annotation.db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lucky.jacklamb.enums.PrimaryType;

/**
 * ���ڽ�������ʵ�����ӳ���ϵ
 * 	value ��ʶ����������ӳ��
 * 	type �������͡�Type.DEFAULT����ͨ���� Type.AUTO_INT��������INT���� AUTO_UUID��������UUID������
 *  length �����ֶγ���
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {
	String value() default "";
	PrimaryType type() default PrimaryType.DEFAULT;
	int length() default 35;
}
