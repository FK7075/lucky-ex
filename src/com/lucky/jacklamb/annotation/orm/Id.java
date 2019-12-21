package com.lucky.jacklamb.annotation.orm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lucky.jacklamb.enums.PrimaryType;

/**
 * ����ӳ��
 * 
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {
	
	/**
	 * ��ʶ����������ӳ����
	 * @return
	 */
	String value() default "";
	
	/**
	 * ��������<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;
	 * Type.DEFAULT(Ĭ��):��ͨ����<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;
	 * Type.AUTO_INT:������INT���� <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;
	 * AUTO_UUID��������UUID����
	 * @return
	 */
	PrimaryType type() default PrimaryType.DEFAULT;
	
	/**
	 * ���ý���ʱ���ֶγ���,Ĭ��35
	 * @return
	 */
	int length() default 35;
}
