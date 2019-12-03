package com.lucky.jacklamb.annotation.orm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于建立表与实体类的映射关系
 * 	value 标识外键
 * 	pojo 外键对应的实体类Class
 *  length 建表时该字段的长度
 *  allownull 建表时是否允许该字段为NULL(默认允许true)
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Key {
	String value() default "";
	int length() default 35;
	boolean allownull() default true;
	Class<?> pojo();
}
