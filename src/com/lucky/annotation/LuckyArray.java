package com.lucky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DI相关注解，为组件装配一个Object数组，属性的类型也必须为Object[]
 * 	types:数组中各个元素的类型
 * 	values：设置数组中各个元素的值
 * 	refs:设置数组中各个元素的依赖
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LuckyArray {
	String[] types() default {};
	String[] values() default {};
	String[] refs() default {};

}
