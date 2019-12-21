package com.lucky.jacklamb.annotation.orm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ����ӳ��
 * @author fk-7075
 *
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
	
	/**
	 * ����ӳ��
	 * @return
	 */
	String value() default "";
	
	/**
	 * ����,ֻ��ʹ��SqlCore��query()����ʱ��Ч
	 * @return
	 */
	String alias() default "";
	
	/**
	 * �����������[@Table(primary="bid")]
	 * @return
	 */
	String primary() default "";
	
	/**
	 * �����ͨ����[@Table(index={"bname","price"})]
	 * @return
	 */
	String[] index() default {};
	
	/**
	 * ���Ψһֵ����[@Table(unique={"bname","price"})]
	 * @return
	 */
	String[] unique() default {};
	
	/**
	 * ���ȫ������[@Table(fulltext={"bname","price"})]
	 * @return
	 */
	String[] fulltext() default {};
	
	/**
	 * �ӱ���ɾ��,Ĭ��false
	 * @return
	 */
	boolean cascadeDelete() default false;
	
	/**
	 * �ӱ�������,Ĭ��false
	 * @return
	 */
	boolean cascadeUpdate() default false;
}
