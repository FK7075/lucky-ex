package com.lucky.jacklamb.annotation.mvc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MVC中定义一个文件下载的操作，只能使用在Controller的方法映射方法上
 * name：要下载的文件的文件名
 * filePath：项目中文件所在的文件夹
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
