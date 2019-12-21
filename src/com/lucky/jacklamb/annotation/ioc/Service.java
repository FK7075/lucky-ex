package com.lucky.jacklamb.annotation.ioc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ����һ��Service���
 * @author fk-7075
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
	
	/**
	 * Ϊ��Service���ָ��һ��ΨһID��Ĭ�ϻ�ʹ��[����ĸСд����]��Ϊ�����ΨһID
	 * @return
	 */
	String value() default "";
}
