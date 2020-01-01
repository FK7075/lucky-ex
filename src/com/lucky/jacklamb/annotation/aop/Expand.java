package com.lucky.jacklamb.annotation.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lucky.jacklamb.enums.Location;

/**
 * 声明一个前置增强
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Expand {
	String value() default "";
	String[] mateClass() default {};
	String[] mateMethod() default {};
	String[] params() default {};
	Location location() default Location.BEFORE;
}
