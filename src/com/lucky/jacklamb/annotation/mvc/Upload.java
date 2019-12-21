package com.lucky.jacklamb.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ����һ���ļ��ϴ�������ִ�����ϴ�������᷵�������ļ����ļ��������Ҫ���գ������ڷ����Ĳ����б���ʹ����names��Ӧ��ͬ����String���Ͳ�������
 * 
 * @author fk-7075
 *
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Upload {
	
	/**
	 * ����<form enctype="multipart/form-data">---<input type='file'>��name����ֵ����Ajax������{formData.append("name",$('#crowd_file')[0].files[0]);data:formData]name��������ɵ�����
	 * @return
	 */
	String[] names() default "";
	
	/**
	 * �ϴ�����Ŀ�ļ��е�λ��(��Ӧnames)
	 * @return
	 */
	String[] filePath() default "";
	
	/**
	 * �����ϴ����ļ�����(eg:  .jpg,.jpeg,.png),Ĭ�ϲ�������
	 * @return
	 */
	String type() default "";
	
	/**
	 * �����ϴ�������ļ���С(��λ:kb)��Ĭ�ϲ�������
	 * @return
	 */
	int maxSize() default 0;
}
