package com.lucky.jacklamb.annotation.ioc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义IOC组件的注解
 * 	优先级 id>value在两者都为默认值时，使用类名作为组件的ID
 * 	value:单独使用时定义组件的ID
 * 	id:非单独使用时定义组件的ID
 *  fields:类的属性名
 *  values：设置对应属性的值
 * @author fk-7075
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanFactory {
	String value() default "";
}