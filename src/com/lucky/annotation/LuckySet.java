package com.lucky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DI���ע�⣬Ϊ���װ��һ��Set����
 * 	type:���͵�����
 * 	values�����ü����и���Ԫ�ص�ֵ
 * 	refs:���ü����и���Ԫ�ص�����
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LuckySet {
	String type() default "";
	String[] refs() default {};
	String[] values() default {};

}
