package com.lucky.jacklamb.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MVC�ж���һ���ļ����صĲ�����ֻ��ʹ����Controller�ķ���ӳ�䷽����
 * name��Ҫ���ص��ļ����ļ���
 * filePath����Ŀ���ļ����ڵ��ļ���
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Download {
	String name() default "";
	String filePath() default "";
}
