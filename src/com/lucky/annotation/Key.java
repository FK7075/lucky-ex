package com.lucky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于建立表与实体类的映射关系
 * 	value 标识外键
 * 	pojo 外键对应的实体类Class
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Key {
	String value() default "";
	Class<?> pojo() default Void.class;
}
