package com.lucky.jacklamb.rest;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ��������Json��Xmlӳ����
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Attr {
	
	/**
	 * ����Json��Xmlӳ����
	 * @return
	 */
	String value() default "";
	
}
