package xfl.fk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * �Զ������������ʹ�õ�ע�⣬�������ü�����Ϣ
 * 	delete:���ü���ɾ�����������
 * 	update:���ü������µ��������
 * @author fk-7075
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cascade {
	boolean delete() default false;
	boolean update() default false;
}
