package com.lucky.jacklamb.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MVC�ж���һ���ļ��ϴ�����
 * 	names������<form enctype="multipart/form-data">---<input type='file'>��name����ֵ����ɵ�����
 * 	filePath����Ӧ�ϴ�����Ŀ�ļ��е�λ��
 * 	type�������ϴ����ļ�����(eg:  .jpg,.jpeg,.png)
 * 	maxSize:�����ϴ�������ļ���С(��λ:kb)
 * @author fk-7075
 *
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Upload {
	String[] names() default "";
	String[] filePath() default "";
	String type() default "";
	int maxSize() default 0;
}
