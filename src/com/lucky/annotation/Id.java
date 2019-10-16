package com.lucky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lucky.enums.Type;

/**
 * 用于建立表与实体类的映射关系
 * 	value 标识主键
 * 	type 设置类型
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {
	String value() default "";
	Type type() default Type.DEFAULT;
}
