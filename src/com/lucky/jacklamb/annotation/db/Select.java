package com.lucky.jacklamb.annotation.db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ORM的Mapper接口中使用，定义一个查询的数据库操作
 * 	value：设置预编译的SQL (eg: SELECT * FROM book WHERE bid=?)
 * 	columns:设置所要查询的字段
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Select {
	String value() default "";
	String[] columns() default {};
	boolean byid() default false;
}
