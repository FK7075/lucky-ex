package xfl.fk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AOP��ز�����ע�⣬���ڶ���һ����չ��
 * 	value:������������ڶ������IOC�����ID,����ڷ����϶���һ����չ������
 * 		  "���ID/����ID"Ψһȷ��һ����չ����
 * @author fk-7075
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Expand {
	String value() default "";
}
