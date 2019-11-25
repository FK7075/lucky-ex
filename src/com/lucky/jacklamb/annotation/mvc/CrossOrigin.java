package com.lucky.jacklamb.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * ���ڴ�����������
 * ǰ��ҳ�������������ajax����������� xhrFields: {withCredentials:true}������ԣ���ʾ�ṩcookie��Ϣ,����ʵ�ֿ����session��������.
 * origins:����ɷ��ʵ����б�(Ĭ��Ϊ������)
 * maxAge:׼����Ӧǰ�Ļ�����������ʱ�䣨����Ϊ��λ��Ĭ��Ϊ1800s��
 * allowCredentials:�Ƿ�����cookie��������(Ĭ��Ϊtrue)
 * method:����֧�ֵķ���(Ĭ�ϣ�POST, GET, OPTIONS, DELETE)
 * allowedHeaders:��������ͷ�ص�header��Ĭ�϶�֧��
 * exposedHeaders:��Ӧͷ��������ʵ�header,Ĭ��Ϊ��
 * @author fk-7075
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrossOrigin {
	
	String[] value() default {};

	String[] origins() default {};
	
	String exposedHeaders() default "";
	
	String allowedHeaders() default "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token";
	
	int maxAge() default 1800;
	
	String method() default "POST, GET, OPTIONS, DELETE";
	
	boolean allowCredentials() default true;
	
}
