package com.lucky.jacklamb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ORM中用来确定类属性与表字段的映射关系
 * 	value:属性对应数据库中的字段名
 *  length 建表时该字段的长度
 *  allownull 建表时是否允许该字段为NULL(默认允许true)
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
	String value() default "";
	int length() default 35;
	boolean allownull() default true;
}
