package com.lucky.jacklamb.annotation.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AOP���棬���ڷ����ϣ��������ķ���ֵ��ָ����key���뻺��
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cacheable {
	
	/**
	 * ��������������
	 * @return
	 */
	String value();
	
	/**
	 * ��������ڻ��������е�key
	 * @return
	 */
	String key();
	
	/**
	 * �����������������ִ�л���
	 * @return
	 */
	String condition() default "";
}
