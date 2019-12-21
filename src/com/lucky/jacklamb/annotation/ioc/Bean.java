package com.lucky.jacklamb.annotation.ioc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ע��һ��Component�������Ҫ���@BeanFactoryע��ʹ��
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
	
	/**
	 * Ϊ��Component���ָ��һ��ΨһID��Ĭ�ϻ�ʹ��[����.������]��Ϊ�����ΨһID
	 * @return
	 */
	String value() default "";
}
