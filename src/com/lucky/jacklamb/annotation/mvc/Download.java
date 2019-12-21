package com.lucky.jacklamb.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MVC�ж���һ���ļ����صĲ�����ֻ��ʹ����Controller�ķ���ӳ�䷽����
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Download {
	
	/**
	 * ����URL�����а������ļ����docBase�ļ��е����·���Ĳ���ֵ<br>
	 * eg:http://localhost:8080/download?file="image/1.jpg"<br>
	 * -@Download(name="file")
	 * @return
	 */
	String name() default "";
	
	/**
	 * Ҫ�����ļ��ľ���·��
	 * @return
	 */
	String path() default "";
	
	/**
	 * Ҫ�����ļ����docBase�ļ��е����·�� 
	 * @return
	 */
	String docPath() default "";
	
	/**
	 * �ļ������ļ������docdocBase��λ��
	 * @return
	 */
	String folder() default "";
}
