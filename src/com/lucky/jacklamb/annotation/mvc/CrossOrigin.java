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
 * @author fk-7075
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrossOrigin {
	
	/**
	 * ��������ɷ��ʵ����б�(Ĭ��Ϊ������)
	 * @return
	 */
	String[] value() default {};

	/**
	 * ��������ɷ��ʵ����б�(Ĭ��Ϊ������)
	 * @return
	 */
	String[] origins() default {};
	
	/**
	 * ������Ӧͷ��������ʵ�header,Ĭ��Ϊ��
	 * @return
	 */
	String exposedHeaders() default "";
	
	/**
	 * ������������ͷ�ص�header��Ĭ�϶�֧��
	 * @return
	 */
	String allowedHeaders() default "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token";
	
	/**
	 * ����׼����Ӧǰ�Ļ�����������ʱ�䣨����Ϊ��λ��Ĭ��Ϊ1800s��
	 * @return
	 */
	int maxAge() default 1800;
	
	/**
	 * ��������֧�ֵķ���(Ĭ�ϣ�POST, GET, OPTIONS, DELETE)
	 * @return
	 */
	String method() default "POST, GET, OPTIONS, DELETE";
	
	/**
	 * �����Ƿ�����cookie��������(Ĭ��Ϊtrue)
	 * @return
	 */
	boolean allowCredentials() default true;
	
}
