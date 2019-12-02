package com.lucky.jacklamb.annotation.db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lucky.jacklamb.enums.PrimaryType;

/**
 * 用于建立表与实体类的映射关系
 * 	value 标识主键，主键映射
 * 	type 设置类型【Type.DEFAULT：普通主键 Type.AUTO_INT：自增的INT主键 AUTO_UUID：自增的UUID主键】
 *  length 设置字段长度
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
