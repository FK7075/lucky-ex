package com.lucky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DI相关注解，为组件装配一个Set集合
 * 	type:泛型的类型
 * 	values：设置集合中各个元素的值
 * 	refs:设置集合中各个元素的依赖
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LuckySet {
	String type() default "";
	String[] refs() default {};
	String[] values() default {};

}
