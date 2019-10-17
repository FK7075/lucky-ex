package com.lucky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ORM操作中重要的注解
 * 	id:用于标注主键属性
 * 	auto:(默认为true:自动建表机制会设置主键自增，insert操作时会自动注入主键值)
 * 	table:类与表之间的关系映射
 * 	key：与自动建表机制中生成外键有关,指定子表中的外键属性
 * 	url: 与自动建表机制中生成外键有关,指定子表所关联的父表
 * @author fk-7075
 *
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
	String value() default "";
	String primary() default "";
	String[] index() default {};
	String[] unique() default {};
	String[] fulltext() default {};
	boolean cascadeDelete() default false;
	boolean cascadeUpdate() default false;
}
