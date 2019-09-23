package xfl.fk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动化建表机制中使用的注解，用于配置级联信息
 * 	delete:设置级联删除的外键属性
 * 	update:设置级联更新的外键属性
 * @author fk-7075
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cascade {
	boolean delete() default false;
	boolean update() default false;
}
