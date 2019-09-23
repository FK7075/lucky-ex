package xfl.fk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ��������ģʽ�µ� RIGHT JOIN ��������
 * Lucky��ɨ�贫�������������ϵ����ʹ�ô˹�ϵ�������������
 * ����ӱ������ֶ���������������ֶ�����ͬ����ᱻLucky��Ϊ�����������ϵ
 * 	value:������Ҫ��ѯ���ֶ�
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RightJoin {
	String value() default "";
}
