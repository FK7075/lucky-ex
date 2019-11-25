package com.lucky.jacklamb.annotation.ioc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ����IOC�����ע��
 * 	���ȼ� id>value�����߶�ΪĬ��ֵʱ��ʹ��������Ϊ�����ID
 * 	value:����ʹ��ʱ���������ID
 * 	id:�ǵ���ʹ��ʱ���������ID
 *  fields:���������
 *  values�����ö�Ӧ���Ե�ֵ
 * @author fk-7075
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanFactory {
	String value() default "";
}