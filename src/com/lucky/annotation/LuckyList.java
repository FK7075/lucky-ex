package com.lucky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DI相关注解，为组件装配一个List集合
 * 	type:泛型的类型
 * 	values：设置集合中各个元素的值
 * 	refs:设置集合中各个元素的依赖
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LuckyList {
	String type() default "";
	String[] values() default "";
	String[] refs() default {};

}
