package xfl.fk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import xfl.fk.aop.Location;

/**
 * ��Aop��أ�����һ������ǿ����ͷ���
 * 	value����ע������ʱʹ�ã����øñ���ǿ����ʵ�ֵĽӿڵ�ȫ·��
 * 	classname����ʹ�õĵ���չ���ID
 * 	method����ʹ�õ���չ������ID
 * 	parameter����չ�����еĲ����б�����Ӧ��ֵ�����Ҫʹ�ñ���ǿ�����еĲ�������ʹ��"#��Ҫ����Ĳ�����"
 * 	location����ǿ��ʽ(ǰ�ã�����)
 * @author fk-7075
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Real {
	String value() default "";
	String classname() default "";
	String method() default "";
	String[] parameter() default {};
	Location location() default Location.BEFORE;
	
}
