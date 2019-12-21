package com.lucky.jacklamb.annotation.ioc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DI��ص�ע��
 * @author fk-7075
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
	
	/**
	 * ָ��Ҫע������ID����ָ�������������ɨ����ƽ����Զ�ע��
	 * @return
	 */
	String value() default "";
}
