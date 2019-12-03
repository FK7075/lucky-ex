package com.lucky.jacklamb.annotation.orm.mapper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ʹ����Mapper�ӿڷ����ϣ��������ڷǿռ��Ķ�̬SQL����
 * ���@Select,@Update,@Deleteʹ��
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Change {
	String value() default "";
}
