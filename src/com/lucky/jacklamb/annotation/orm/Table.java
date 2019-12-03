package com.lucky.jacklamb.annotation.orm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ORM操作中重要的注解
 * 	value 表名映射
 * 	alias 别名,只在使用SqlCore的query()方法时有效
 * 	primary 添加主键索引
 *  index 添加普通索引
 *  unique 添加唯一值索引
 *  fulltext 添加全文索引
 *  cascadeDelete 设置与子表级联删除
 *  cascadeUpdate 设置与子表级联更新
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
