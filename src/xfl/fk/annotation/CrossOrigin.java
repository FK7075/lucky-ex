package xfl.fk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ���ڴ�����������
 * origins:����ɷ��ʵ����б�
 * maxAge:׼����Ӧǰ�Ļ�����������ʱ�䣨����Ϊ��λ��
 * @author fk-7075
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrossOrigin {

	String origins() default "*";
	
	String maxAge() default "1800";
	
	String allowCredentials() default "false";
	
}
